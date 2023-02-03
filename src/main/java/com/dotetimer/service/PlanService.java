package com.dotetimer.service;

import com.dotetimer.domain.*;
import com.dotetimer.dto.PlanDto.*;
import com.dotetimer.infra.exception.CustomException;
import com.dotetimer.infra.mapper.PlanMapper;
import com.dotetimer.repository.CoinRepository;
import com.dotetimer.repository.PlanInfoRepository;
import com.dotetimer.repository.PlanRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.dotetimer.infra.exception.ErrorCode.INVALID_DATA;
import static com.dotetimer.infra.exception.ErrorCode.MEMBER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
public class PlanService {
    private final CoinRepository coinRepository;
    private final PlanRepository planRepository;
    private final PlanInfoRepository planInfoRepository;

    @Transactional
    public PlanInfo createPlanInfo(User user, PlanInfoReqDto planInfoReqDto) {
        // Null 및 유효성 확인
        if (!checkValidPlanInfo(planInfoReqDto)) throw new CustomException(INVALID_DATA);

        // 코인 찾기 및 생성
        Coin coin = coinRepository.findByUserIdAndStudiedAt(user.getId(), LocalDate.now()).orElse(null); // Optional 클래스 : orElse, orElseGet, of, ofNulluable 등
        if (coin == null) {
            coin = Coin.builder()
                    .user(user)
                    .coinCount(0)
                    .studiedAt(LocalDate.now())
                    .build();
            coinRepository.save(coin);
        }

        // 할일정보 생성
        PlanInfo planInfo = PlanMapper.INSTANCE.toPlanInfo(planInfoReqDto);

        // DB 저장 (순서 조심 : 기본키 테이블 먼저)
        planInfoRepository.save(planInfo);

        return planInfo;
    }

    @Transactional
    public PlanInfo updatePlanInfo(PlanInfoReqDto planInfoReqDto, int planInfoId) {
        // Null 및 유효성 확인
        if (!checkValidPlanInfo(planInfoReqDto)) throw new CustomException(INVALID_DATA);

        // 할일정보 찾기
        PlanInfo planInfo = planInfoRepository.findById(planInfoId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        // Entity 수정
        planInfo.updatePlanInfo(planInfoReqDto.getTitle(), planInfoReqDto.getCategory(), planInfoReqDto.getColor(), planInfoReqDto.getRepeatDay(), planInfoReqDto.getCompletedAt());

        // DB 저장
        planInfoRepository.save(planInfo);

        return planInfo;
    }

    @Transactional
    public Plan createPlanOrRecord(User user, PlanReqDto planReqDto, int planInfoId, boolean record) {
        // Null 및 유효성 확인
        if (!checkValidPlanRecord(planReqDto)) throw new CustomException(INVALID_DATA);

        // 할일정보 찾기(계획이 존재해야 기록 등록 가능)
        PlanInfo planInfo = planInfoRepository.findById(planInfoId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        // 코인 찾기 및 생성
        Coin coin = coinRepository.findByUserIdAndStudiedAt(user.getId(), LocalDate.now()).orElse(null); // Optional 클래스 : orElse, orElseGet, of, ofNulluable 등
        if (coin == null) {
            coin = Coin.builder()
                    .user(user)
                    .coinCount(0)
                    .studiedAt(LocalDate.now())
                    .build();
            coinRepository.save(coin);
        }

        // 할일 생성(한일 기록 : recorded = true)
        Plan plan = Plan.builder()
                .coin(coin) // Coin에 coin 추가
                .planInfo(planInfo) // Plan에 planInfo 추가
                .startTime(planReqDto.getStartTime())
                .endTime(planReqDto.getEndTime())
                .recorded(record)
                .build();

        // 한일
        if (record) {
            // 코인 개수 추가 // plan.getCoin().updateCoin(plan.calCoin());
            coin.updateCoin(coin.getCoinCount() + plan.calCoin());
            user.updateCoin(user.getCoinCount() + plan.calCoin());

            // DB 저장
            coinRepository.save(coin);
        }

        // DB 저장
        planRepository.save(plan);

        return plan;
    }

    public PlanResDto getPlan(int planId) {
        // 할일 및 할일정보 찾기
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
        PlanInfo planInfo = plan.getPlanInfo();

        // Entity -> DTO
        return PlanMapper.INSTANCE.toPlanResDto(plan, planInfo);
    }

    @Transactional
    public Plan updatePlanOrRecord(User user, PlanReqDto planReqDto, int planId, boolean record) {
        // Null 및 유효성 확인
        if (!checkValidPlanRecord(planReqDto)) throw new CustomException(INVALID_DATA);

        // 할일 및 할일정보 찾기
        Plan plan = planRepository.findByIdAndRecorded(planId, record)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
        PlanInfo planInfo = planInfoRepository.findById(plan.getPlanInfo().getId())
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        // 코인 이전 개수 차감
        if (record) {
            plan.getCoin().updateCoin(plan.getCoin().getCoinCount() - plan.calCoin());
            user.updateCoin(user.getCoinCount() - plan.calCoin());
        }

        // Entity 수정
        plan.updatePlan(planReqDto.getStartTime(), planReqDto.getEndTime());

        // 코인 이후 개수 가산
        if (record) {
            plan.getCoin().updateCoin(plan.getCoin().getCoinCount() + plan.calCoin());
            user.updateCoin(user.getCoinCount() + plan.calCoin());
        }

        // DB 저장
        planRepository.save(plan); // DB의 coin 바뀌는지

        return plan;
    }

    @Transactional
    public Plan deletePlan(User user, int planId) {
        // 코인 찾기
        Coin coin = coinRepository.findByUserIdAndStudiedAt(user.getId(), LocalDate.now())
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        // 할일 찾기
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        // DB 삭제
        // Plan 삭제 : Plan, PlanInfo, Record 전부 삭제 (Coin->Plan/PlanInfo->Plan)
        if (!plan.isRecorded()) {
            // DB 삭제 :  Cascade(PlanInfo 삭제 시 Plan, Record 삭제)
            planInfoRepository.delete(plan.getPlanInfo());

            // Coin에서 PlanInfo와 연결된 Record 삭제
            plan.getPlanInfo().getPlans()
                    .forEach(o -> {
                        if (o.isRecorded()) {
                            // 코인 개수 감소
                            coin.updateCoin(coin.getCoinCount() - o.calCoin());
                            user.updateCoin(user.getCoinCount() - o.calCoin());
                            coin.getPlans().remove(o);
                        }
                    });

            // Coin에서 Plan 삭제
            coin.getPlans().remove(plan);

            // Plan 하나도 없는 날의 Coin 삭제
            if (coin.getPlans().isEmpty()) coinRepository.delete(coin);
        }
        // Record 삭제
        else {
            // 코인 개수 감소
            coin.updateCoin(coin.getCoinCount() - plan.calCoin());
            user.updateCoin(user.getCoinCount() - plan.calCoin());

            // Record만 삭제
            planRepository.delete(plan);
        }

        return plan;
    }

    public List<PlanResDto> getPlanList(User user, boolean record, LocalDate studiedAt) {
        // 할일 및 한일 찾기
        List<PlanResDto> planResDtos = new ArrayList<>();
        Coin coin = user.getCoins().stream()
                .filter(o -> o.getStudiedAt().equals(studiedAt))
                .findFirst()
                .orElse(null);
        
        // studiedAt 날짜의 데이터 존재하는 경우
        if (coin != null) {
            coin.getPlans().stream()
                    .filter(o -> o.isRecorded() == record)
                    .forEach(o -> {
                        // Entity -> DTO
                        planResDtos.add(PlanMapper.INSTANCE.toPlanResDto(o, o.getPlanInfo()));
                    }
            );
        }
//        List<Plan> plans = user.getCoins().getPlans().stream()
//                .filter(o -> o.getStudiedAt().equals(studiedAt))
//                .collect(Collectors.toList()); // planRepository.findPlansByUserAndDate(record, user.getId(), studiedAt);
        return planResDtos;
    }

    // Null 및 유효성 확인
    private boolean checkValidPlanInfo(PlanInfoReqDto planInfoReqDto) {
        if ((planInfoReqDto.getTitle() == null) || (planInfoReqDto.getCategory() == null) || (planInfoReqDto.getColor() == null) || (planInfoReqDto.getRepeatDay() == null) || (planInfoReqDto.getCompletedAt() == null))
            return false;
        if (planInfoReqDto.getCompletedAt().isAfter(LocalDate.now())) return false; // 과거 시간
        return true;
    }

    private boolean checkValidPlanRecord(PlanReqDto planReqDto) {
        if ((planReqDto.getStartTime() == null) || (planReqDto.getEndTime() == null))
            return false;
        return !planReqDto.getStartTime().isAfter(planReqDto.getEndTime());
    }
}
