package com.dotetimer.controller;

import com.dotetimer.domain.User;
import com.dotetimer.dto.PlanDto.*;
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

    @PostMapping(path = "/info")
    public ResponseEntity<?> createPlanInfo(@AuthenticationPrincipal User user, @Valid @RequestBody PlanInfoReqDto planInfoReqDto) {
        planService.createPlanInfo(user, planInfoReqDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "/info/{planInfoId}")
    public ResponseEntity<?> updatePlanInfo(@PathVariable int planInfoId, @Valid @RequestBody PlanInfoReqDto planInfoReqDto) {
        planService.updatePlanInfo(planInfoReqDto, planInfoId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/{planInfoId}")
    public ResponseEntity<?> createPlanOrRecord(@AuthenticationPrincipal User user, @PathVariable int planInfoId, @RequestParam(name = "record") boolean record, @Valid @RequestBody PlanReqDto planReqDto) {
        planService.createPlanOrRecord(user, planReqDto, planInfoId, record);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/{planId}")
    public ResponseEntity<?> getPlan(@PathVariable int planId) {
        return new ResponseEntity<>(planService.getPlan(planId), HttpStatus.OK);
    }

    @PutMapping(path = "/{planId}")
    public ResponseEntity<?> updatePlanOrRecord(@AuthenticationPrincipal User user, @PathVariable int planId, @RequestParam(name = "record") boolean record, @Valid @RequestBody PlanReqDto planReqDto) {
        planService.updatePlanOrRecord(user, planReqDto, planId, record);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{planId}")
    public ResponseEntity<?> deletePlan(@AuthenticationPrincipal User user, @PathVariable int planId) {
        planService.deletePlan(user, planId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/list") // studiedAt Null 허용
    public ResponseEntity<?> getPlanList(@AuthenticationPrincipal User user, @RequestParam(name = "record") boolean record, @RequestParam(name = "studiedAt", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate studiedAt) {
        return new ResponseEntity<>(planService.getPlanList(user, record, studiedAt), HttpStatus.OK);
    }
}
