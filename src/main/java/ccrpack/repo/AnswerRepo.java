package ccrpack.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import ccrpack.entity.Answer;
import ccrpack.entity.Candidate;
import ccrpack.entity.Question;

public interface AnswerRepo extends JpaRepository<Answer, Integer> {



	Answer findByQuestionAndCandidate(Question question, Candidate candidate);

	List<Answer> findByCandidate(Candidate candidate);

}
