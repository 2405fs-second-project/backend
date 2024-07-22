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
    private String phone_num;

    @Column(name = "address", nullable = false, length = 100)
    private String address;

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "profile_picture_url", columnDefinition = "TEXT DEFAULT ''")
    private String profile_picture_url;

    @Column(name = "about_me", length = 100, columnDefinition = "VARCHAR(100) DEFAULT ''")
    @Builder.Default
    private String about_me = "";

    @Column(name = "update_name", length = 30)
    private String update_name;

    @Column(name = "update_address", length = 500)
    private String update_address;

    @Column(name = "update_phone", length = 50)
    private String update_phone;

    @Column(name = "shipping_info", length = 500)
    private String shipping_info;

    public enum Gender {
        MALE,
        FEMALE
    }
}
