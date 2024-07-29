package com.second.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
// DTO(Data Transfer Object)란? 데이터 전송 객체로, 서로 다른 시스템 또는 계층 간에 데이터를 전송하기 위해 사용되는 객체입니다. 일반적으로 DTO는 데이터베이스의 엔티티와는 별개로 설계됩니다.
@Getter
@Setter
@Builder
@AllArgsConstructor

public class ProductResponse {
    private Integer id;
    private String name;
    private String color;
    private Integer price;
    private String fileUrl;
}
