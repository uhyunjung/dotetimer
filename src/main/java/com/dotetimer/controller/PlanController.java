package com.dotetimer.controller;

import com.dotetimer.domain.User;
import com.dotetimer.dto.PlanDto.PlanReqDto;
import com.dotetimer.dto.PlanDto.RecordReqDto;
import com.dotetimer.service.PlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/plan")
public class PlanController {
    private final PlanService planService;

    @PostMapping(path = "")
    public ResponseEntity<?> createPlan(@AuthenticationPrincipal User user, @Valid @RequestBody PlanReqDto planReqDto) {
        planService.createPlan(user, planReqDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/record/{planInfoId}")
    public ResponseEntity<?> createRecord(@AuthenticationPrincipal User user, @PathVariable int planInfoId, @Valid @RequestBody RecordReqDto recordReqDto) {
        planService.createRecord(user, recordReqDto, planInfoId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "{planId}")
    public ResponseEntity<?> getPlan(@PathVariable int planId) {
        return new ResponseEntity<>(planService.getPlan(planId), HttpStatus.OK);
    }

    @PutMapping(path = "{planId}")
    public ResponseEntity<?> updatePlan(@PathVariable int planId, @Valid @RequestBody PlanReqDto planReqDto) {
        planService.updatePlan(planId, planReqDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "/record/{planId}")
    public ResponseEntity<?> updateRecord(@PathVariable int planId, @Valid @RequestBody RecordReqDto recordReqDto) {
        planService.updateRecord(planId, recordReqDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "{planId}")
    public ResponseEntity<?> deletePlan(@AuthenticationPrincipal User user, @PathVariable int planId) {
        planService.deletePlan(user, planId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/list") // studiedAt Null 허용
    public ResponseEntity<?> getPlanList(@AuthenticationPrincipal User user, @RequestParam(name = "record") boolean record, @RequestParam(name = "studiedAt", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate studiedAt) {
        return new ResponseEntity<>(planService.getPlanList(user, record, studiedAt), HttpStatus.OK);
    }
}
