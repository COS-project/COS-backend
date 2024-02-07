package com.cos.cercat.post.repository;

import com.cos.cercat.post.domain.CommentaryPost;
import com.cos.cercat.global.util.PagingUtil;
import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.certificate.domain.QCertificate;
import com.cos.cercat.post.dto.request.PostSearchCond;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
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
    public Slice<CommentaryPost> searchPosts(Pageable pageable, Certificate certificate, PostSearchCond cond) {

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
                        containKeyword(cond.keyword()),
                        eqExamYear(cond.examYear()),
                        eqRound(cond.round()),
                        eqQuestionSequence(cond.questionSequence()),
                        commentaryPost.certificate.eq(certificate)
                )
                .orderBy(postSort(pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = false;
        if (contents.size() > pageable.getPageSize()) {
            contents.remove(pageable.getPageSize() + 1);
            hasNext = true;
        }

        return new SliceImpl<>(contents, pageable, hasNext);
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

    private BooleanExpression eqExamYear(Integer examYear) {
        if (examYear == null) return null;


        return commentaryPost.question.mockExam.examYear.eq(examYear);
    }

    private BooleanExpression eqRound(Integer round) {
        if (round == null) return null;

        return commentaryPost.question.mockExam.round.eq(round);
    }

    private BooleanExpression eqQuestionSequence(Integer questionSequence) {
        if (questionSequence == null) return null;

        return commentaryPost.question.questionSeq.eq(questionSequence);
    }

    public static OrderSpecifier<?> postSort(Pageable pageable) {
        return PagingUtil.getOrderSpecifier(pageable, commentaryPost.createdAt, commentaryPost.likeCount);
    }
}
