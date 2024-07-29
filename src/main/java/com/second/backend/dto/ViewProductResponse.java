package com.second.backend.dto;

import com.second.backend.model.Product;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ViewProductResponse {

    private Integer id;
    private String gender;
    private String kind;
    private String name;
    private String color;
    private String fullName;
    private String code;
    private Integer price;
    private String fileUrl;
    private String description;
    private LocalDate listedDate;

}
