package com.testmanagement_api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.testmanagement_api.entity.Subcategory;

public interface SubCategoryRepository extends JpaRepository<Subcategory , Long>{
    @Query("SELECT c FROM Subcategory c WHERE c.subCategoryName = :subcatgeoryName")
    Subcategory getOneBysubCategoryName(String subcatgeoryName);
}
