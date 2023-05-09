package com.assignmentsmanager.service;

import com.assignmentsmanager.domain.Assignment;
import com.assignmentsmanager.domain.User;
import com.assignmentsmanager.enums.AssignmentStatusEnum;
import com.assignmentsmanager.enums.AuthorityEnum;

import java.util.Optional;
import java.util.Set;

public interface AssignmentService {
    Assignment save(User user);

    Set<Assignment> findByUser(User user);

    Optional<Assignment> findById(Long assignmentId);

    Assignment save(Assignment assignment);

    Optional<Assignment> claimAssignment(Long assignmentId, User user);

}
