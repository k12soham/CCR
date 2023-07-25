package ccrpack.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import ccrpack.entity.OcrResult;


public interface OcrResultRepo extends JpaRepository<OcrResult, Long>{

}
