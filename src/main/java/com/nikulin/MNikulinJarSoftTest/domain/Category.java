package com.nikulin.MNikulinJarSoftTest.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;
    @NotNull(message = "The name cannot be empty.")
    @NotBlank(message = "The name cannot be empty.")
    @Size(max = 255, message = "The name must not exceed 255 characters.")
    @Column(nullable = false, unique = true)
    private String name;
    @NotNull(message = "The name cannot be empty.")
    @NotBlank(message = "The request name cannot be empty.")
    @Size(max = 255, message = "The request name must not exceed 255 characters.")
    @Column(nullable = false, unique = true)
    private String reqName;
    @Column(nullable = false)
    private Boolean deleted;
}
