package com.coderbd.repository;

import ch.qos.logback.core.boolex.EvaluationException;
import com.coderbd.entity.Employee;
import com.coderbd.enums.EmployeeSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Set;

/**
 * @author Mohammad Rajaul Islam
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query(value = "SELECT e FROM Employee e WHERE e.skills IN :skills")
    List<Employee> findEmployeeBySkills(@Param("skills") Set<EmployeeSkill> skills);

}
