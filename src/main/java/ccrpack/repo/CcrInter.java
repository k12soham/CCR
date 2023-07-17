package ccrpack.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import ccrpack.entity.CcrAdmin;

public interface CcrInter extends JpaRepository<CcrAdmin, Integer> {

}
