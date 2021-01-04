package com.nikulin.MNikulinJarSoftTest.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bannerId;
    @NotNull(message = "The name cannot be empty.")
    @NotBlank(message = "The name cannot be empty.")
    @Size(max = 255, message = "The name must not exceed 255 characters.")
    @Column(nullable = false, unique = true)
    private String name;
    @NotNull(message = "The price cannot be empty.")
    @DecimalMin(value = "0.00", message = "The price must be positive.")
    @DecimalMax(value = "999999.99", message = "The price must not exceed 999999.99.")
    @Column(columnDefinition = "decimal(8,2)", nullable = false)
    private Double price;
    @NotNull(message = "The content cannot be empty.")
    @NotBlank(message = "The content cannot be empty.")
    @Size(max = 65535, message = "The content must not exceed 65535 characters.")
    @Column(columnDefinition = "text", nullable = false)
    private String content;
    @Column(nullable = false)
    private Boolean deleted;

    @NotNull(message = "The category cannot be empty.")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;
}
