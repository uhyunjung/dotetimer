package com.dotetimer.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name="study_group")
public class StudyGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY) // 다대일 양방향
    @JoinColumn(name = "user") // 외래키 있는 테이블의 객체의 필드명
    private User user;
    private String name;
    private String category;

    @Enumerated(EnumType.STRING)
    private Theme theme;
    private int joinCount;
    // @Lob // Large Object // 한글 깨짐
    private String details;
    private String password;
    private LocalDate createdAt;

    @Builder.Default
    @OneToMany(mappedBy="studyGroup", cascade=CascadeType.ALL)
    private List<GroupJoin> groupJoin = new ArrayList<>();

    public void updateStudyGroup(String name, String category, Theme theme, int joinCount, String details, String password) {
        this.name=name;
        this.category=category;
        this.theme=theme;
        this.joinCount=joinCount;
        this.details=details;
        this.password=password;
    }
}
