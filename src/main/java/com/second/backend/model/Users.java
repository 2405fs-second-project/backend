package com.second.backend.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class Users implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(name = "phone_num", nullable = false, length = 20)
    private String phoneNum;

    @Column(name = "address", nullable = true, length = 100) // nullable 설정 및 기본값 설정
    @Builder.Default
    private String address = "";

    @Column(name = "gender", nullable = true) // 성별은 nullable 설정 및 기본값 설정
    @Builder.Default
    private Gender gender = Gender.UNKNOWN;

    @Column(name = "profile_picture_url", columnDefinition = "TEXT DEFAULT ''")
    @Builder.Default
    private String profilePictureUrl = "";

    @Column(name = "about_me", length = 100, columnDefinition = "VARCHAR(100) DEFAULT ''")
    @Builder.Default
    private String aboutMe = "";

    @Column(name = "update_name", length = 30)
    private String updateName;

    @Column(name = "update_address", length = 500)
    private String updateAddress;

    @Column(name = "update_phone", length = 50)
    private String updatePhone;

    @Column(name = "shipping_info", length = 500)
    private String shippingInfo;

    public enum Gender {
        MALE,
        FEMALE,
        UNKNOWN // 기본값으로 추가
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
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