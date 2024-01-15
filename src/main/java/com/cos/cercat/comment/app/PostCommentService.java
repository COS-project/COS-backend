package com.cos.cercat.comment.app;

import com.cos.cercat.comment.domain.PostComment;
import com.cos.cercat.comment.repository.PostCommentRepository;
import com.cos.cercat.global.exception.CustomException;
import com.cos.cercat.global.exception.ErrorCode;
import com.cos.cercat.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostCommentService {

    private final PostCommentRepository postCommentRepository;

    public PostComment getPostComment(Long commentId) {
        return postCommentRepository.findById(commentId).orElseThrow(() ->
                new CustomException(ErrorCode.COMMENT_NOT_FOUND));
    }

    public void deletePostComment(Long commentId, User user) {

        PostComment postComment = getPostComment(commentId);

        if (postComment.isAuthorized(user)) {
            postCommentRepository.delete(postComment);
        }
    }
}
