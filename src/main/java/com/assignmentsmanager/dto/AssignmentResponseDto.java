package com.assignmentsmanager.dto;

import com.assignmentsmanager.domain.Assignment;
import com.assignmentsmanager.enums.AssignmentEnum;
import com.assignmentsmanager.enums.AssignmentStatusEnum;

public class AssignmentResponseDto {
    private Assignment assignment;
    private final AssignmentEnum[] assignmentEnums = AssignmentEnum.values();
    private AssignmentStatusEnum[] statusEnums = AssignmentStatusEnum.values();

    public AssignmentResponseDto(Assignment assignment) {
        this.assignment = assignment;

    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public AssignmentEnum[] getAssignmentEnums() {
        return assignmentEnums;
    }

    public AssignmentStatusEnum[] getStatusEnums() {
        return statusEnums;
    }
}
