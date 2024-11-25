package btongtong.btongtalkback.controller;

import btongtong.btongtalkback.constant.RecordStatus;
import btongtong.btongtalkback.dto.auth.AuthDto;
import btongtong.btongtalkback.dto.record.request.FlashcardIdAndStatusDto;
import btongtong.btongtalkback.dto.record.request.CategoryIdAndProgressDto;
import btongtong.btongtalkback.dto.record.response.RecordStatisticsByFlashcardDto;
import btongtong.btongtalkback.dto.record.response.RecordStatisticsDto;
import btongtong.btongtalkback.dto.record.response.RecordsByStatusWithTotalPages;
import btongtong.btongtalkback.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RecordController {
    private final RecordService recordService;

    @GetMapping("/records")
    public ResponseEntity<?> recordsByStatus(@RequestParam("status") RecordStatus status,
                                             @AuthenticationPrincipal AuthDto authDto,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);

        RecordsByStatusWithTotalPages response = recordService.getRecordsByStatus(authDto.getId(), status, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/records/statistics")
    public ResponseEntity<?> recordStatistics(@RequestParam("status") RecordStatus status,
                                              @AuthenticationPrincipal AuthDto authDto) {
        List<RecordStatisticsDto> response = recordService.getStatistics(authDto.getId(), status);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/records/statistics/status")
    public ResponseEntity<?> recordStatisticsByStatus(@RequestParam("categoryId") Long categoryId,
                                                      @AuthenticationPrincipal AuthDto authDto) {
        RecordStatisticsByFlashcardDto response = recordService.getStatisticsByStatus(authDto.getId(), categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/records/record")
    public ResponseEntity<?> updateRecordStatus (@RequestBody FlashcardIdAndStatusDto dto,
                                                 @AuthenticationPrincipal AuthDto authDto) {
        recordService.postRecordStatus(dto, authDto.getId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/records")
    public ResponseEntity<?> updateRecordProgress(@RequestBody CategoryIdAndProgressDto dto,
                                                  @AuthenticationPrincipal AuthDto authDto) {
        recordService.updateProgress(dto, authDto.getId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
