package com.second.backend.model;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import java.time.LocalDate;

import lombok.*;

@Setter
@Getter
@Entity //JPA Entity 클래스임을 나타내는 어노테이션입니다.
@Table(name = "product") //데이터베이스에서 매핑할 테이블 이름을 지정합니다.
@Data // 롬복 애노테이션: Getter, Setter, toString, hashCode 등을 자동 생성합니다.
@NoArgsConstructor // 롬복 애노테이션: 매개변수 없는 생성자를 자동 생성합니다.
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "SellerId")
    private Users users;

    @Column(name = "CategoryGender", length = 10, nullable = false)
    private String gender;

    @Column(name = "CategoryKind", length = 20, nullable = false)
    private String kind;

    @Column(name = "Name", length = 50, nullable = false)
    private String name;

    @Column(name = "Color", columnDefinition = "text", nullable = false)
    private String color;

    @Column(name = "FullName", columnDefinition = "text", nullable = false)
    private String fullName;

    @Column(name = "Code", columnDefinition = "text", nullable = false)
    private String code;

    @Column(name = "File", length = 255, nullable = false)
    private String fileUrl;// 이미지 URL 저장

    @Column(name = "DelistedDate")
    private LocalDate DelistedDate;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "Price", nullable = false)
    private Integer price;

    @Column(name = "ListedDate")
    private LocalDate listedDate;


    @PrePersist
    public void prePersist() {
        this.listedDate = LocalDate.now();
    }
}