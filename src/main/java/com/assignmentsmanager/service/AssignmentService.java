package com.assignmentsmanager.service;

import com.assignmentsmanager.domain.Assignment;
import com.assignmentsmanager.domain.User;
import com.assignmentsmanager.enums.AssignmentStatusEnum;
import com.assignmentsmanager.enums.AuthorityEnum;
import com.assignmentsmanager.repository.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepo;

    public Assignment save(User user) {
        Assignment assignment = new Assignment();
        assignment.setStatus(AssignmentStatusEnum.PENDING_SUBMISSION.getStatus());
        assignment.setUser(user);
        Optional<Integer> latestAssignmentNumberOpt = findLatestAssignmentNumber(user);
        latestAssignmentNumberOpt.ifPresentOrElse(
                latestNum -> assignment.setNumber(latestNum + 1),
                () -> assignment.setNumber(1));
        return assignmentRepo.save(assignment);
    }

    public Set<Assignment> findByUser(User user) {
        // code reviewer
        boolean isCodeReviewer = user.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals(AuthorityEnum.ROLE_CODE_REVIEWER.name()));
        if(isCodeReviewer)
            return assignmentRepo.findByCodeReviewer(user.getId());

        // student
        return assignmentRepo.findByUser(user);

    }

    public Optional<Assignment> findById(Long assignmentId) {
        return assignmentRepo.findById(assignmentId);
    }

    public Assignment save(Assignment assignment) {
        return assignmentRepo.save(assignment);
    }

    private Optional<Integer> findLatestAssignmentNumber(User user) {
        return assignmentRepo.findLatestAssignment(user.getId());
    }

    public Optional<Assignment> claimAssignment(Long assignmentId, User user) {
        Optional<Assignment> assignmentToClaim = assignmentRepo.findById(assignmentId);
        assignmentToClaim.ifPresent(a -> {
            a.setCodeReviewer(user);
            a.setStatus(AssignmentStatusEnum.IN_REVIEW.getStatus());
            assignmentRepo.save(a);
        });
        return assignmentToClaim;
    }
}
