package com.assignmentsmanager.repository;

import com.assignmentsmanager.domain.Assignment;
import com.assignmentsmanager.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    Set<Assignment> findByUser(User user);

    @Query(value = "select max(number) from assignment where user_id = ?1", nativeQuery = true)
    Optional<Integer> findLatestAssignment(Long id);

    @Query(value = "select * from assignment where (status like '%submitted%' and code_reviewer_id is null) or code_reviewer_id = ?1", nativeQuery = true)
    Set<Assignment> findByCodeReviewer(Long userId);
}
