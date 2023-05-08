package com.assignmentsmanager.repository;

import com.assignmentsmanager.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Set<Comment> findByAssignmentIdOrderByCreatedDateAsc(Long assignmentId);

}
