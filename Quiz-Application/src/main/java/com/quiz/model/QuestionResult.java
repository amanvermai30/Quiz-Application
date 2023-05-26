package com.quiz.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionResult {

	private String questionText;
	private AnswerOption options;
	private int correctAnswerIndex;
}
