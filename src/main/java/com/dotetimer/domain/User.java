package com.dotetimer.domain;

import jakarta.persistence.*; // javax에서 바뀜
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 매핑 전략 종류 Identity, Sequence, Table
    @Column(name = "id") // 테이블 Key vs joinColumn
    private int id;

    @Column(unique = true)
    @NotNull
    private String email;

    @Column(length = 300)
    @NotNull
    private String password;

    private String name;

    private String introduction;

    @ColumnDefault("false")
    private boolean opened;

    private String img;

    @ColumnDefault("false")
    private boolean premium;

    @ColumnDefault("0")
    private int coinCount;

    @Setter
    private String refreshToken;

    @Builder.Default
    @OneToMany(mappedBy="user", cascade=CascadeType.ALL) // 지연로딩, 외래키 있는 도메인의 필드명
    private List<StudyGroup> studyGroups = new ArrayList<>(); // 외래키 있는 테이블의 객체 리스트

    @Builder.Default
    @OneToMany(mappedBy="user", cascade=CascadeType.ALL)
    private List<GroupJoin> groupJoins = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy="follower", cascade=CascadeType.ALL)
    private List<Follow> followers = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy="following", cascade=CascadeType.ALL)
    private List<Follow> followings = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy="user", cascade=CascadeType.ALL)
    private List<Coin> coins = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy="user", cascade=CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy="user", cascade=CascadeType.ALL)
    private List<ReviewLike> reviewLikes = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy="user", cascade=CascadeType.ALL)
    private List<Donate> donates = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER) // 즉시로딩
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    // security
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email; // 기본키
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void updateInfo(String name, String introduction, boolean opened, String img) {
        this.name = name;
        this.introduction = introduction;
        this.opened = opened;
        this.img = img;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void updateCoin(int coinCount) {
        this.coinCount = coinCount;
    }

    public void updatPremium(boolean premium) {
        this.premium = premium;
    }
}
