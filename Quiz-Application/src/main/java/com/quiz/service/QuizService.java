package com.quiz.service;

import java.util.List;

import com.quiz.exception.QuizNotFoundException;
import com.quiz.exception.ResultNotAvailableException;
import com.quiz.exception.UserNotFoundException;
import com.quiz.model.Quiz;
import com.quiz.model.QuizResult;
import com.quiz.model.User;

public interface QuizService {
	
	public User createUser(User user);

	public Quiz createQuiz(Quiz quiz,Integer userId)throws UserNotFoundException;

	public List<Quiz> getAllQuizzes();

	public Quiz getQuizById(Integer quizId)throws QuizNotFoundException;

	public Quiz getActiveQuiz() throws QuizNotFoundException;

	public QuizResult getQuizResult(Integer quizId) throws QuizNotFoundException,ResultNotAvailableException;
}
