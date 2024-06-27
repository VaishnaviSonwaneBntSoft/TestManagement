package com.testmanagement_api.entity;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
@Table(name = "testinfo")
public class TestModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "testinfo_seq")
    @SequenceGenerator(name = "testinfo_seq", sequenceName = "testinfo_seq", allocationSize = 1)
    @Column(name = "question_id")
    private long Question_id;
    @Column(name = "category")
    private String Category;
    @Column(name = "question")
    private String Question;
    @Column(name = "option_one")
    private String Option_one;
    @Column(name = "option_two")
    private String Option_two;
    @Column(name = "option_three")
    private String Option_three;
    @Column(name = "option_four")
    private String Option_four;
    @Column(name = "correct_option")
    private String Correct_option;
    @Column(name = "positive_mark")
    private String Positive_mark;
    @Column(name = "negative_mark")
    private String Negative_mark;
    
    
}
