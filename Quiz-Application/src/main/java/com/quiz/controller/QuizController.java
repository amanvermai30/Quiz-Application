package com.quiz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.quiz.exception.QuizNotFoundException;
import com.quiz.exception.ResultNotAvailableException;
import com.quiz.exception.UserNotFoundException;
import com.quiz.model.Quiz;
import com.quiz.model.QuizResult;
import com.quiz.model.User;
import com.quiz.service.QuizService;

@RestController
public class QuizController {

	@Autowired
	private QuizService quizService;

	@PostMapping("/user")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		User output = quizService.createUser(user);
		return new ResponseEntity<User>(output, HttpStatus.CREATED);
	}

	@PostMapping("/quizzes/{userId}")
	public ResponseEntity<Quiz> createQuiz(@RequestBody Quiz quiz, @PathVariable("userId") Integer userId)
			throws UserNotFoundException {
		Quiz createdQuiz = quizService.createQuiz(quiz, userId);
		return new ResponseEntity<Quiz>(createdQuiz, HttpStatus.CREATED);
	}

	@GetMapping("/quizzes/all")
	public ResponseEntity<List<Quiz>> getAllQuizzes() {
		List<Quiz> quizzes = quizService.getAllQuizzes();
		return new ResponseEntity<List<Quiz>>(quizzes, HttpStatus.OK);
	}

	@GetMapping("/quizzes/{id}/result")
	public ResponseEntity<QuizResult> getQuizResult(@PathVariable("id") Integer id)
			throws QuizNotFoundException, ResultNotAvailableException {
		QuizResult quizResult = quizService.getQuizResult(id);

		return new ResponseEntity<QuizResult>(quizResult, HttpStatus.OK);
	}

	@GetMapping("/quizzes/active")
	public ResponseEntity<Quiz> getActiveQuiz() throws QuizNotFoundException {
		Quiz activeQuiz = quizService.getActiveQuiz();
		return new ResponseEntity<Quiz>(activeQuiz, HttpStatus.OK);
	}
}
