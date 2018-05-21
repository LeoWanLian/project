package com.leowan.pss.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.leowan.pss.domain.Supplier;

/**
 * 持久层Dao层由Spring Data JPA代替，现在叫Repository
 * 
 * @author LeoWan
 *
 */
// 传入的泛型参数是domain类型和主键类型
public interface SupplierRepository extends BaseRepository<Supplier, Long> {
	
}
