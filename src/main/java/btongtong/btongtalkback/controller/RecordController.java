package btongtong.btongtalkback.controller;

import btongtong.btongtalkback.constant.RecordStatus;
import btongtong.btongtalkback.dto.auth.AuthDto;
import btongtong.btongtalkback.dto.record.request.FlashcardIdAndStatusDto;
import btongtong.btongtalkback.dto.record.request.CategoryIdAndProgressDto;
import btongtong.btongtalkback.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RecordController {
    private final RecordService recordService;

    @GetMapping("/records")
    public ResponseEntity<?> recordsByStatus(@RequestParam("status") RecordStatus status,
                                            @AuthenticationPrincipal AuthDto authDto,
                                            @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "2") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return recordService.findRecordsByStatus(authDto.getId(), status, pageable);
    }

    @GetMapping("/records/statistics")
    public ResponseEntity<?> recordStatistics(@RequestParam("status") RecordStatus status, @AuthenticationPrincipal AuthDto authDto) {
        return recordService.statistics(authDto.getId(), status);
    }

    @PostMapping("/records")
    public ResponseEntity<?> updateRecordStatus (@RequestBody FlashcardIdAndStatusDto dto, @AuthenticationPrincipal AuthDto authDto) {
        return recordService.saveRecord(dto, authDto.getId());
    }

    @PatchMapping("/records")
    public ResponseEntity<?> updateRecordProgress(@RequestBody CategoryIdAndProgressDto dto, @AuthenticationPrincipal AuthDto authDto) {
        return recordService.updateProgress(dto, authDto.getId());
    }
}
