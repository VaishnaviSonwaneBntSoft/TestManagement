package com.testmanagement_api.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.testmanagement_api.entity.Category;

public interface CategoryRepository extends JpaRepository<Category , Long>{

}
