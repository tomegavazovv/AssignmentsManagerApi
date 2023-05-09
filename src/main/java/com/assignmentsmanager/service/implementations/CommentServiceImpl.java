package com.assignmentsmanager.service.implementations;

import com.assignmentsmanager.domain.Comment;
import com.assignmentsmanager.domain.User;
import com.assignmentsmanager.dto.CommentDto;
import com.assignmentsmanager.repository.AssignmentRepository;
import com.assignmentsmanager.repository.CommentRepository;
import com.assignmentsmanager.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepo;
    @Autowired
    private AssignmentRepository assignmentRepo;

    public Comment save(CommentDto commentDto, User user) {
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setText(commentDto.getText());
        comment.setCreatedBy(user);
        comment.setAssignment(assignmentRepo.getById(commentDto.getAssignmentId()));
        if(comment.getId() == null)
            comment.setCreatedDate(LocalDateTime.now());
        else
            comment.setCreatedDate(commentDto.getCreatedDate());

        return commentRepo.save(comment);
    }

    public Set<Comment> getCommentsByAssignmentId(Long assignmentId) {
        return commentRepo.findByAssignmentIdOrderByCreatedDateAsc(assignmentId);
    }
}
