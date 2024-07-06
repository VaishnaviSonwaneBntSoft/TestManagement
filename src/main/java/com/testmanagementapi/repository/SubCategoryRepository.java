package com.testmanagementapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.testmanagementapi.entity.Subcategory;

public interface SubCategoryRepository extends JpaRepository<Subcategory , Long>{
    
    boolean existsBySubCategoryName(String subCategoryName);

    @Query("SELECT c FROM Subcategory c WHERE c.subCategoryName = :subCategoryName")
    Subcategory getOneBySubCategoryName(String subCategoryName);
}
