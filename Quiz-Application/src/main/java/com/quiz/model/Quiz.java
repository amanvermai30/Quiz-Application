package com.quiz.model;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Quiz {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer quizId;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "quizID")
	private Set<Question> questions = new LinkedHashSet<>();
	
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	
	@Enumerated(EnumType.STRING)
	private Status status = Status.INACTIVE;
	
	@ManyToOne
	private User user;

}
