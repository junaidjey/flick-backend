package com.project.flik.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.flik.model.LastScrapped;

@Repository
public interface LastScrappedRepository extends JpaRepository<LastScrapped, Long> {

	@Query(nativeQuery = true,value= "select * from last_scrapped order by id desc limit 1")
	LastScrapped findFirstByOrderByIdDesc();

}