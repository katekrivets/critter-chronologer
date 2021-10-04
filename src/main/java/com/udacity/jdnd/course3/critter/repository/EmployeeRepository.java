package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.common.EmployeeSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
//    @Query(value = "SELECT c FROM Conversation c LEFT JOIN c.usersIds cu GROUP BY c "
//            + "HAVING SUM(CASE WHEN cu IN (:userIds) THEN 1 ELSE -1 END) = :userIdsCount")
//     select
//        employee0_.id as id1_5_,
//        employee0_1_.name as name2_5_
//    from
//        employee employee0_
//    inner join
//        user employee0_1_
//            on employee0_.id=employee0_1_.id
//    left outer join
//        employee_skills skills1_
//            on employee0_.id=skills1_.employee_id
//    where
//        (
//            ? in (
//                select
//                    daysavaila2_.days_available
//                from
//                    employee_days_available daysavaila2_
//                where
//                    employee0_.id=daysavaila2_.employee_id
//            )
//        )
//        and (
//            skills1_.skills in (
//                ? , ?
//            )
//        )
    @Query("select e from Employee e left join e.skills sk where :dayOfWeek member of e.daysAvailable GROUP BY e HAVING sum(case when sk in :skills then 1 else 0 end)=:skillCount")
    List<Employee> findByDaysAvailableContainingAndSkillsIn(DayOfWeek dayOfWeek, Set<EmployeeSkill> skills, long skillCount);
}
