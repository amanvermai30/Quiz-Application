package com.quiz.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quiz.exception.QuizNotFoundException;
import com.quiz.exception.ResultNotAvailableException;
import com.quiz.exception.UserNotFoundException;
import com.quiz.model.AnswerOption;
import com.quiz.model.Question;
import com.quiz.model.QuestionResult;
import com.quiz.model.Quiz;
import com.quiz.model.QuizResult;
import com.quiz.model.Status;
import com.quiz.model.User;
import com.quiz.repository.QuestionRepository;
import com.quiz.repository.QuizRepository;
import com.quiz.repository.UserRepository;

@Service
public class QuizServiceImpl implements QuizService {

	@Autowired
	private QuizRepository quizRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private QuizStatusService quizStatusService;

	public User createUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public Quiz createQuiz(Quiz quiz, Integer userId) throws UserNotFoundException {
		// TODO Auto-generated method stub
		LocalDateTime now = LocalDateTime.now();

		// Set the end time to 5 minutes from now
		LocalDateTime endDate = now.plusMinutes(5);

		quiz.setStartDate(now);
		quiz.setEndDate(endDate);
		quiz.setStatus(Status.INACTIVE); // Initially we have to set the status to INACTIVE

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("Invalid user ID: " + userId));
		quiz.setUser(user); // Set the user associated with the quiz

		Quiz createdQuiz = quizRepository.save(quiz);

		// Schedule the quiz status update after saving the quiz
		quizStatusService.updateQuizStatus();

		return createdQuiz;
	}

	@Override
	public List<Quiz> getAllQuizzes() {
		// TODO Auto-generated method stub
		return quizRepository.findAll();
	}

	@Override
	public Quiz getQuizById(Integer quizId) throws QuizNotFoundException {
		// TODO Auto-generated method stub
		return quizRepository.findById(quizId).orElseThrow(() -> new QuizNotFoundException("Quiz not found"));
	}

	@Override
	public Quiz getActiveQuiz() throws QuizNotFoundException {
		// TODO Auto-generated method stub

		LocalDateTime currentTime = LocalDateTime.now();
		Optional<Quiz> activeQuiz = quizRepository.findActiveQuiz(currentTime);

		return activeQuiz.orElseThrow(() -> new QuizNotFoundException("No active quiz found."));
	}

	@Override
	public QuizResult getQuizResult(Integer quizId) throws QuizNotFoundException, ResultNotAvailableException {
		// TODO Auto-generated method stub
		Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new QuizNotFoundException("Quiz not found."));

		// Check if the quiz is finished (5 minutes after end time)
		LocalDateTime currentTime = LocalDateTime.now();
		LocalDateTime quizEndTime = quiz.getEndDate().plusMinutes(5);
		if (currentTime.isBefore(quizEndTime)) {
			throw new ResultNotAvailableException("Quiz result is not available yet.");
		}

		// Retrieve the result (right answers)
	    Set<Question> questions = quiz.getQuestions();
	    List<QuestionResult> questionResults = new ArrayList<>();

	    for (Question question : questions) {
	        int correctAnswerIndex = question.getCorrectAnswerIndex();
	        String questionText = question.getQuestionText();
	        AnswerOption options = question.getAnswerOption();
	        QuestionResult questionResult = new QuestionResult(questionText, options, correctAnswerIndex);
	        questionResults.add(questionResult);
	    }

	    return new QuizResult(quizId, questionResults);
	}

}
