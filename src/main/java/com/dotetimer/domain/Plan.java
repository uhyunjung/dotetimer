package com.dotetimer.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="plan")
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coin_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Coin coin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_info_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private PlanInfo planInfo;

    private LocalTime startTime;
    private LocalTime endTime;
    private boolean recorded;

    public void updatePlan(LocalTime startTime, LocalTime endTime) {
        this.startTime=startTime;
        this.endTime=endTime;
    }

    public int calCoin() { // 코인 개수 * 10 = 분 단위, 코인 개수 / 6 = 시 단위
        return ((endTime.getHour()*60+endTime.getMinute())-(startTime.getHour()*60+startTime.getMinute()))/10;
    }
}
