package ccrpack.repo;

import org.springframework.data.jpa.repository.JpaRepository;


import ccrpack.entity.RatingForm;

public interface RatingInter extends JpaRepository<RatingForm, Integer> {

}
