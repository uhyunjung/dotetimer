package com.dotetimer.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="plan_info")
public class PlanInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    private String title;
    private String category;
    private String color;
    private String repeatDay;
    private LocalDate completedAt;

    @Builder.Default
    @OneToMany(mappedBy="planInfo", cascade=CascadeType.ALL)
    private List<Plan> plans = new ArrayList<>();

    public void updatePlanInfo(String title, String category, String color, String repeatDay, LocalDate completedAt) {
        this.title=title;
        this.category=category;
        this.color=color;
        this.repeatDay=repeatDay;
        this.completedAt=completedAt;
    }
}
