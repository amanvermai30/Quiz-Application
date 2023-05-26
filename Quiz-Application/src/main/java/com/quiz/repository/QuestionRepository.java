package com.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quiz.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Integer>{

	
}
