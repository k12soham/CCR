package ccrpack.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ccrpack.entity.Answer;
import ccrpack.entity.Candidate;
import ccrpack.entity.Question;
@Repository
public interface AnswerRepo extends JpaRepository<Answer, Integer> {



	Answer findByQuestionAndCandidate(Question question, Candidate candidate);

	List<Answer> findByCandidate(Candidate candidate);

}
