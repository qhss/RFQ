package com.rfq.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 
 */
@NoRepositoryBean
public interface BaseRepository<T,ID> extends JpaRepository<T,ID>, JpaSpecificationExecutor<T> {

	@Query(nativeQuery=true, value="SELECT CONCAT(SUBSTRING('ABCDEFGHIJKLMNOPQRSTUVWXYZ',CEIL(RAND()*26)-1,1),DATE_FORMAT(NOW(),'%Y%m%d%H%i%s'),LPAD(CEIL(RAND()*999),3,'0')) ")
	String createOrderNo();
	
}
