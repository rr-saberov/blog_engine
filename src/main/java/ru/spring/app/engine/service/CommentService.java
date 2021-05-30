package ru.spring.app.engine.service;

import org.springframework.stereotype.Service;
import ru.spring.app.engine.api.request.CommentRequest;
import ru.spring.app.engine.api.response.AddCommentResponse;
import ru.spring.app.engine.api.response.errors.AddCommentErrors;
import ru.spring.app.engine.entity.PostComments;
import ru.spring.app.engine.repository.CommentRepository;
import ru.spring.app.engine.repository.PostRepository;
import ru.spring.app.engine.repository.UserRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentsRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentsRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentsRepository = commentsRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public AddCommentResponse addComment(CommentRequest comment, String email) {
        AddCommentResponse response = new AddCommentResponse();
        if (commentsRepository.findById(comment.getParentId()).isPresent()
                || postRepository.findById(comment.getPostId()).isPresent() && comment.getText().length() > 10) {
            response.setId(commentsRepository.findAll().size() + 1);
            commentsRepository.save(convertRequestToPostComments(comment, email));
        } else {
            response.setResult(false);
            response.setErrors(addErrorsToList(comment));
        }
        return response;
    }

    private PostComments convertRequestToPostComments(CommentRequest comment, String email) {
        PostComments postComments = new PostComments();
        postComments.setParentId(comment.getParentId());
        postComments.setPostId(comment.getPostId());
        postComments.setText(comment.getText());
        postComments.setTime(new Date());
        postComments.setId(commentsRepository.findAll().size() + 1);
        postComments.setUserId(userRepository.getUserIdByEmail(email));
        return postComments;
    }

    private List<AddCommentErrors> addErrorsToList(CommentRequest comment) {
        List<AddCommentErrors> errors = new ArrayList<>();
        if (commentsRepository.findById(comment.getParentId()).isEmpty()) {
            errors.add(new AddCommentErrors("add fail, no comment with such id"));
        }
        if (postRepository.findById(comment.getPostId()).isEmpty()) {
            errors.add(new AddCommentErrors("add fail, no post with such id"));
        }
        if (comment.getText().length() <= 10) {
            errors.add(new AddCommentErrors("add fail, the text is too short"));
        }

        return errors;
    }
}
