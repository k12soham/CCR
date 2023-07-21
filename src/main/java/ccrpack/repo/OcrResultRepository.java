package ccrpack.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import ccrpack.entity.OcrResult;

public interface OcrResultRepository extends JpaRepository<OcrResult, Long>{

}
