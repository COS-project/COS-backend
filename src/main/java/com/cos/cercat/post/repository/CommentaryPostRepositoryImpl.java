package com.cos.cercat.post.repository;

import com.cos.cercat.post.domain.CommentaryPost;
import com.cos.cercat.global.util.PagingUtil;
import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.certificate.domain.QCertificate;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.cos.cercat.post.domain.QCommentaryPost.*;
import static com.cos.cercat.mockExam.domain.QMockExam.mockExam;
import static com.cos.cercat.mockExam.domain.QQuestion.question;
import static com.cos.cercat.user.domain.QUser.user;


@Repository
@RequiredArgsConstructor
public class CommentaryPostRepositoryImpl implements PostRepositoryCustom<CommentaryPost> {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<CommentaryPost> searchPosts(Pageable pageable, Certificate certificate, String keyword) {
        List<CommentaryPost> contents = queryFactory
                .selectFrom(commentaryPost)
                .leftJoin(commentaryPost.user, user)
                .fetchJoin()
                .leftJoin(commentaryPost.question, question)
                .fetchJoin()
                .leftJoin(commentaryPost.question.mockExam, mockExam)
                .fetchJoin()
                .leftJoin(commentaryPost.certificate, QCertificate.certificate)
                .fetchJoin()
                .where(
                        containKeyword(keyword),
                        commentaryPost.certificate.eq(certificate)
                )
                .orderBy(postSort(pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = queryFactory.select(commentaryPost.count())
                .from(commentaryPost);

        return PageableExecutionUtils.getPage(contents, pageable, count::fetchOne);
    }

    private BooleanExpression containKeyword(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return null;
        }

        BooleanExpression keywordExpression = null;

        try {
            Integer keywordAsInteger = Integer.parseInt(keyword);
            keywordExpression = commentaryPost.question.questionSeq.eq(keywordAsInteger);
        } catch (NumberFormatException e) {
            // Ignore the exception if parsing fails
        }

        return commentaryPost.title.containsIgnoreCase(keyword)
                .or(commentaryPost.content.containsIgnoreCase(keyword))
                .or(keywordExpression);
    }

    public static OrderSpecifier<?> postSort(Pageable pageable) {
        return PagingUtil.getOrderSpecifier(pageable, commentaryPost.createdAt, commentaryPost.likeCount);
    }
}
