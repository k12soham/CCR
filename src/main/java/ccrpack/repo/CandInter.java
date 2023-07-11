package ccrpack.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import ccrpack.entity.Candidate;

public interface CandInter extends JpaRepository<Candidate, Integer> {

}
