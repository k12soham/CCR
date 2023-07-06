package ccrpack.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import ccrpack.entity.Company;

public interface CompanyInter   extends JpaRepository<Company, Integer> {

}
