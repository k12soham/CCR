package ccrpack.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ccrpack.entity.Question;
@Repository
public interface QuestionRepo extends JpaRepository<Question, Integer> {

}
