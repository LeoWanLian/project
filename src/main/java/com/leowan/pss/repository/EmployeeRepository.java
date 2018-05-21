package com.leowan.pss.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.leowan.pss.domain.Employee;

/**
 * 持久层Dao层由Spring Data JPA代替，现在叫Repository
 * 
 * @author LeoWan
 *
 */
// 传入的泛型参数是domain类型和主键类型
public interface EmployeeRepository extends BaseRepository<Employee, Long> {
	// 按照名称查询
	@Query("select o from Employee o where o.username=?")
	Employee findByUsername(String username);

	// 按照名称和密码查询
	Employee findByUsernameAndPassword(String username, String password);

	// 按照部门名称查询
	List<Employee> findByDepartmentName(String name);
}
