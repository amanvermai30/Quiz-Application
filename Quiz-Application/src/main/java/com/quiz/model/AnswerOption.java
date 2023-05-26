package com.quiz.model;

import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class AnswerOption {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer ansOptionId;

	@ElementCollection
	private List<String> options;

	public AnswerOption(List<String> options) {
		if (options.size() != 4) {
			throw new IllegalArgumentException("Answer option must contain exactly 4 elements");
		}
		this.options = options;
	}

}
