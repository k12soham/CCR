package ccrpack.repo;

import org.springframework.data.jpa.repository.JpaRepository;


import ccrpack.entity.RatingForm;

public interface RatingRepo extends JpaRepository<RatingForm, Integer> {

}
