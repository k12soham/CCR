package ccrpack.repo;

import org.springframework.data.jpa.repository.JpaRepository;


import ccrpack.entity.CcrAdmin;

public interface CcrRepo  extends JpaRepository<CcrAdmin, Integer> {

}
