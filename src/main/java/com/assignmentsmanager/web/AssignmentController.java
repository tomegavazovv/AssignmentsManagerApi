package com.assignmentsmanager.web;

import com.assignmentsmanager.domain.Assignment;
import com.assignmentsmanager.domain.User;
import com.assignmentsmanager.dto.AssignmentResponseDto;
import com.assignmentsmanager.enums.AuthorityEnum;
import com.assignmentsmanager.service.AssignmentService;
import com.assignmentsmanager.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @PostMapping("")
    public ResponseEntity<?> createAssignment(@AuthenticationPrincipal User user) {
        Assignment newAssignment = assignmentService.save(user);
        return ResponseEntity.ok(newAssignment);
    }

    @GetMapping("")
    public ResponseEntity<?> getAssignments(@AuthenticationPrincipal User user) {
        Set<Assignment> assignmentsByUser = assignmentService.findByUser(user);
        return ResponseEntity.ok(assignmentsByUser);
    }

    @GetMapping("{assignmentId}")
    public ResponseEntity<?> getAssignment(@PathVariable Long assignmentId, @AuthenticationPrincipal User user) {
        Optional<Assignment> assignmentOpt = assignmentService.findById(assignmentId);
        if (assignmentOpt.isPresent()) {
            return ResponseEntity.ok(new AssignmentResponseDto(assignmentOpt.get()));
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("")
    public ResponseEntity<?> updateAssignment(@RequestBody Assignment assignment) {
        Assignment updatedAssignment = assignmentService.save(assignment);
        return ResponseEntity.ok(updatedAssignment);
    }

    @PutMapping("{assignmentId}")
    public ResponseEntity<?> claimAssignment(@PathVariable Long assignmentId, @AuthenticationPrincipal User user) {
        if(AuthUtil.hasRole(AuthorityEnum.ROLE_CODE_REVIEWER, user)){
            Optional<Assignment> claimedAssignmentOpt = assignmentService.claimAssignment(assignmentId, user);

            if (claimedAssignmentOpt.isPresent())
                return ResponseEntity.ok(claimedAssignmentOpt.get());
        }
        return ResponseEntity.badRequest().body("Bad Request");
    }
}
