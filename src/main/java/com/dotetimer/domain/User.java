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
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 전략 종류 Identity, Sequence, Table
    @Column(name = "id") // 테이블 Key
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

    @Column(name = "user_img")
    private String profileImage;

    @ColumnDefault("false")
    private boolean premium;

    @ColumnDefault("0")
    private int coinCount;

    @Setter
    private String refreshToken;

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
        return email;
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
}
