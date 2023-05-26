package com.quiz.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class QuizResult {

	private Integer quizId;
    private List<QuestionResult> questionResults;
}
