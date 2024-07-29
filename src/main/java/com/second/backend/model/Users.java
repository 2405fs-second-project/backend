package com.second.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Integer id;

    @Column(name = "email", nullable = false, length = 40, unique = true)
    private String email;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "phone_num", nullable = false, length = 20)
    private String phoneNum;

    @Column(name = "address", nullable = true, length = 100) // nullable 설정 및 기본값 설정
    @Builder.Default
    private String address = "";

    @Column(name = "gender", nullable = true) // 성별은 nullable 설정 및 기본값 설정
    @Enumerated(EnumType.STRING)
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
}
