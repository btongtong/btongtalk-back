package btongtong.btongtalkback.service;

import btongtong.btongtalkback.constant.ErrorCode;
import btongtong.btongtalkback.domain.Flashcard;
import btongtong.btongtalkback.domain.Member;
import btongtong.btongtalkback.domain.Record;
import btongtong.btongtalkback.constant.RecordStatus;
import btongtong.btongtalkback.dto.record.request.FlashcardIdAndStatusDto;
import btongtong.btongtalkback.dto.record.request.CategoryIdAndProgressDto;
import btongtong.btongtalkback.dto.record.response.RecordStatisticsByFlashcardDto;
import btongtong.btongtalkback.dto.record.response.RecordsByStatusWithTotalPages;
import btongtong.btongtalkback.dto.record.response.RecordDto;
import btongtong.btongtalkback.dto.record.response.RecordStatisticsDto;
import btongtong.btongtalkback.handler.exception.CustomException;
import btongtong.btongtalkback.repository.FlashCardRepository;
import btongtong.btongtalkback.repository.MemberRepository;
import btongtong.btongtalkback.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecordService {
    private final MemberRepository memberRepository;
    private final RecordRepository recordRepository;
    private final FlashCardRepository flashCardRepository;

    @Transactional
    public RecordsByStatusWithTotalPages getRecordsByStatus(Long memberId, RecordStatus status, Pageable pageable) {
        Page<RecordDto> recordByStatus = recordRepository.findRecordsByStatus(memberId, status, pageable);
        return new RecordsByStatusWithTotalPages(recordByStatus.getContent(), recordByStatus.getTotalPages());
    }

    @Transactional
    public void postRecordStatus (FlashcardIdAndStatusDto dto, Long memberId) {
        Long flashcardId = dto.getFlashcardId();
        RecordStatus status = dto.getStatus();

        Record record = recordRepository.findByMemberIdAndFlashcardId(memberId, flashcardId)
                .orElseGet(() -> recordRepository.save(createRecord(flashcardId, memberId, status)));
        record.updateStatus(status);
    }

    private Record createRecord(Long flashcardId, Long memberId, RecordStatus status) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.CONTENT_NOT_FOUND));
        Flashcard flashcard = flashCardRepository.findById(flashcardId)
                .orElseThrow(() -> new CustomException(ErrorCode.CONTENT_NOT_FOUND));

        return Record.builder()
                .member(member)
                .status(status)
                .flashcard(flashcard)
                .progress(true)
                .build();
    }

    @Transactional
    public void updateProgress(CategoryIdAndProgressDto dto, Long memberId) {
        recordRepository.updateProgress(memberId, dto.getCategoryId(), dto.getProgress());
    }

    @Transactional
    public List<RecordStatisticsDto> getStatistics(Long memberId, RecordStatus status) {
        return recordRepository.findRecordStatistics(memberId, status);
    }

    @Transactional
    public RecordStatisticsByFlashcardDto getStatisticsByStatus(Long memberId, Long categoryId) {
        return recordRepository.findRecordStatisticsByStatus(memberId, categoryId);
    }
}
