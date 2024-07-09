package com.testmanagementapi.entity;

import org.springframework.stereotype.Component;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
@Table(name = "mcq_question")
public class QuestionModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private long questionId;

    @Valid
    @ManyToOne
    @JoinColumn(name = "subcategory_id", referencedColumnName = "subcategory_id")
    private Subcategory subcategory;

    @NotNull(message = "Question must not be blank")
    @Size(max = 255, message = "Question must be less than or equal to 255 characters")
    @Column(name = "question")
    private String question;

    @NotBlank(message = "Option one must not be blank")
    @Size(max = 100, message = "Option one must be less than or equal to 100 characters")
    @Column(name = "option_one")
    private String optionOne;

    @NotBlank(message = "Option two must not be blank")
    @Size(max = 100, message = "Option two must be less than or equal to 100 characters")
    @Column(name = "option_two")
    private String optionTwo;

    @NotBlank(message = "Option three must not be blank")
    @Size(max = 100, message = "Option three must be less than or equal to 100 characters")
    @Column(name = "option_three")
    private String optionThree;

    @NotBlank(message = "Option four must not be blank")
    @Size(max = 100, message = "Option four must be less than or equal to 100 characters")
    @Column(name = "option_four")
    private String optionFour;

    @NotBlank(message = "Correct option must not be blank")
    @Column(name = "correct_option")
    private String correctOption;

    @NotNull(message = "Positive mark must not be null")
    @Column(name = "positive_mark")
    private String positiveMark;

    @NotNull(message = "Negative mark must not be null")
    @Column(name = "negative_mark")
    private String negativeMark;
    
}
