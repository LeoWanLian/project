package com.leowan.pss.repository;

import org.springframework.data.jpa.repository.Query;

import com.leowan.pss.domain.Department;
import com.leowan.pss.domain.Employee;

/**
 * 持久层Dao层由Spring Data JPA代替，现在叫Repository
 * 
 * @author LeoWan
 *
 */
// 传入的泛型参数是domain类型和主键类型
public interface DepartmentRepository extends BaseRepository<Department, Long> {


}
