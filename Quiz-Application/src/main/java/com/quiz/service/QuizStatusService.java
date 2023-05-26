package com.quiz.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.quiz.model.Quiz;
import com.quiz.model.Status;
import com.quiz.repository.QuizRepository;

@Component
public class QuizStatusService {

	@Autowired
	private QuizRepository quizRepository;

	@Scheduled(cron = "0 0/1 * * * ?")
	public void updateQuizStatus() {

		LocalDateTime now = LocalDateTime.now();
		LocalDateTime fiveMinutesAgo = now.minusMinutes(5);
		List<Quiz> quizzes = quizRepository.findAll();

		for (Quiz quiz : quizzes) {

			if (now.isBefore(quiz.getStartDate())) {
				quiz.setStatus(Status.INACTIVE);
			} else if (now.isAfter(quiz.getEndDate())) {
				quiz.setStatus(Status.FINISHED);
			} else if (now.isAfter(fiveMinutesAgo)) {
				quiz.setStatus(Status.ACTIVE);
			} else {
				quiz.setStatus(Status.INACTIVE);
			}
		}

		quizRepository.saveAll(quizzes);

	}

}
