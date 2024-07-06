package com.testmanagementapi.entity;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private long categoryId;

    @NotBlank(message = "Category name cannot be blank")
    @Size(max = 100, message = "Category name must be less than or equal to 100 characters")
    @Column(name = "category_name")
    private String categoryName;

    @Size(max = 255, message = "Category description must be less than or equal to 255 characters")
    @Column(name = "category_description")
    private String categoryDescription;

}
