package com.testmanagement_api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.testmanagement_api.entity.TestModel;

@Repository
public interface TestManagementRepository extends JpaRepository<TestModel , Long>{

}
