package com.testmanagementapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.testmanagementapi.entity.Category;

public interface CategoryRepository extends JpaRepository<Category , Long>{
    boolean existsByCategoryName(String categoryName);
    
    @Query("SELECT c FROM Category c WHERE c.categoryName = :categoryName")
    Category getOneByName(String categoryName);
}
