package btongtong.btongtalkback.service;

import btongtong.btongtalkback.domain.Flashcard;
import btongtong.btongtalkback.domain.Member;
import btongtong.btongtalkback.domain.Record;
import btongtong.btongtalkback.constant.RecordStatus;
import btongtong.btongtalkback.dto.record.request.FlashcardIdAndStatusDto;
import btongtong.btongtalkback.dto.record.request.CategoryIdAndProgressDto;
import btongtong.btongtalkback.dto.record.response.RecordsByStatusWithTotalPages;
import btongtong.btongtalkback.dto.record.response.RecordDto;
import btongtong.btongtalkback.dto.record.response.RecordStatisticsDto;
import btongtong.btongtalkback.repository.FlashCardRepository;
import btongtong.btongtalkback.repository.MemberRepository;
import btongtong.btongtalkback.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecordService {
    private final MemberRepository memberRepository;
    private final RecordRepository recordRepository;
    private final FlashCardRepository flashCardRepository;

    @Transactional
    public ResponseEntity findRecordsByStatus(Long memberId, RecordStatus status, Pageable pageable) {
        Page<RecordDto> recordByStatus = recordRepository.findRecordsByStatus(memberId, status, pageable);
        RecordsByStatusWithTotalPages recordByStatusCombiDto = new RecordsByStatusWithTotalPages(recordByStatus.getContent(), recordByStatus.getTotalPages());
        return ResponseEntity.status(HttpStatus.OK).body(recordByStatusCombiDto);
    }

    @Transactional
    public ResponseEntity saveRecord(FlashcardIdAndStatusDto dto, Long memberId) {
        Long flashcardId = dto.getFlashcardId();
        RecordStatus status = dto.getStatus();

        Optional<Record> optionalRecord = recordRepository.findByMemberIdAndFlashcardId(memberId, flashcardId);
        Record record;

        if(optionalRecord.isPresent()) {
            record = optionalRecord.get();
            record.updateStatus(status);
        } else {
            Member member = memberRepository
                    .findById(memberId)
                    .orElseThrow(IllegalArgumentException::new);
            Flashcard flashcard = flashCardRepository
                    .findById(dto.getFlashcardId())
                    .orElseThrow(IllegalArgumentException::new);
            record = Record.builder()
                    .member(member)
                    .status(status)
                    .flashcard(flashcard)
                    .progress(true)
                    .build();
        }

        recordRepository.save(record);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Transactional
    public ResponseEntity updateProgress(CategoryIdAndProgressDto dto, Long memberId) {
        recordRepository.updateProgress(memberId, dto.getCategoryId(), dto.getProgress());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public ResponseEntity statistics(Long memberId, RecordStatus status) {
        List<RecordStatisticsDto> statistics = recordRepository.findRecordStatistics(memberId, status);
        return ResponseEntity.status(HttpStatus.OK).body(statistics);
    }
}
