package btongtong.btongtalkback.controller;

import btongtong.btongtalkback.domain.RecordStatus;
import btongtong.btongtalkback.dto.MemberDto;
import btongtong.btongtalkback.dto.RecordDto;
import btongtong.btongtalkback.dto.UpdateRecordStatusDto;
import btongtong.btongtalkback.repository.FlashCardRepository;
import btongtong.btongtalkback.repository.RecordRepository;
import btongtong.btongtalkback.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final TokenService tokenService;
    private final WithdrawService withdrawService;
    private final MemberService memberService;
    private final CategoryService categoryService;
    private final FlashcardService flashcardService;
    private final RecordService recordService;

    @GetMapping("/withdraw")
    public ResponseEntity<?> withdraw(@AuthenticationPrincipal MemberDto memberDto) {
        return withdrawService.withdraw(memberDto.getId());
    }

    @GetMapping("/signout")
    public ResponseEntity<?> logout(@AuthenticationPrincipal MemberDto memberDto) {
        return memberService.logout(memberDto.getId());
    }

    @GetMapping("/my")
    public String my() {
        return "ok";
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> resissue(@CookieValue("Authorization") String refresh) {
        return tokenService.reissue(refresh);
    }

    @GetMapping("/categories")
    public ResponseEntity<?> rootCategories() {
        return categoryService.findRootCategories();
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<?> categories(@PathVariable("categoryId") Long categoryId) {
        return categoryService.findCategories(categoryId);
    }

    @GetMapping("/categories/{categoryId}/flashcard")
    public ResponseEntity<?> flashcards(@PathVariable("categoryId") Long categoryId, @AuthenticationPrincipal MemberDto memberDto) {
        return flashcardService.getFlashcardList(categoryId, memberDto.getId());
    }

    @GetMapping("/flashcard")
    public ResponseEntity<?> searchFlashcard(@RequestParam("question") String question,
                                             @AuthenticationPrincipal MemberDto memberDto,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "2") int size) {
        Long memberId = (memberDto != null) ? memberDto.getId() : null;
        Pageable pageable = PageRequest.of(page, size);
        return flashcardService.searchFlashcard(memberId, question, pageable);
    }

    @GetMapping("/record")
    public ResponseEntity<?> recordByStatus(@RequestParam("status") RecordStatus status,
                                            @AuthenticationPrincipal MemberDto memberDto,
                                            @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "2") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return recordService.findRecordByStatus(memberDto.getId(), status, pageable);
    }

    @GetMapping("/record/statistics")
    public ResponseEntity<?> recordStatistics(@RequestParam("status") RecordStatus status, @AuthenticationPrincipal MemberDto memberDto) {
        return recordService.statistics(memberDto.getId(), status);
    }

    @PostMapping("/record")
    public ResponseEntity<?> createRecord(@RequestBody RecordDto dto, @AuthenticationPrincipal MemberDto memberDto) {
        return recordService.saveRecord(dto, memberDto.getId());
    }

    @PatchMapping("/record")
    public ResponseEntity<?> createRecord(@RequestBody UpdateRecordStatusDto dto, @AuthenticationPrincipal MemberDto memberDto) {
        return recordService.updateRecord(dto, memberDto.getId());
    }
}
