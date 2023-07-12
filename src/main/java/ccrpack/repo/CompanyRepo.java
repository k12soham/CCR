package ccrpack.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import ccrpack.entity.Company;

public interface CompanyRepo   extends JpaRepository<Company, Integer> {

}
