package com.assignmentsmanager.service;

import com.assignmentsmanager.domain.Comment;
import com.assignmentsmanager.domain.User;
import com.assignmentsmanager.dto.CommentDto;

import java.util.Set;

public interface CommentService {
    Comment save(CommentDto commentDto, User user);

    Set<Comment> getCommentsByAssignmentId(Long assignmentId);
}
