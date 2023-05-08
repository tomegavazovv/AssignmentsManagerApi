package com.assignmentsmanager.web;

import com.assignmentsmanager.domain.Comment;
import com.assignmentsmanager.domain.User;
import com.assignmentsmanager.dto.CommentDto;
import com.assignmentsmanager.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("{assignmentId}")
    public ResponseEntity<?> getCommentsByAssignment(@PathVariable Long assignmentId){
        Set<Comment> comments = commentService.getCommentsByAssignmentId(assignmentId);
        return ResponseEntity.ok(comments);
    }

    @PostMapping
    public ResponseEntity<?> createComment(@RequestBody CommentDto commentDto, @AuthenticationPrincipal User user){
        Comment comment = commentService.save(commentDto, user);
        return ResponseEntity.ok(comment);
    }

    @PutMapping
    public ResponseEntity<?> updateComment(@RequestBody CommentDto commentDto, @AuthenticationPrincipal User user){
        Comment comment = commentService.save(commentDto, user);
        return ResponseEntity.ok(comment);
    }

}
