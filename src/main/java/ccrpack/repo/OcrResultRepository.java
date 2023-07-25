package ccrpack.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import ccrpack.entity.OcrResult;
@Repository
public interface OcrResultRepository extends JpaRepository<OcrResult, Long>{

}
