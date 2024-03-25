package com.cos.cercat.mockExam.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QQuestionOption is a Querydsl query type for QuestionOption
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQuestionOption extends EntityPathBase<QuestionOption> {

    private static final long serialVersionUID = 106223924L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QQuestionOption questionOption = new QQuestionOption("questionOption");

    public final StringPath optionContent = createString("optionContent");

    public final com.cos.cercat.common.domain.QImage optionImage;

    public final QQuestion question;

    public final com.cos.cercat.mockExam.domain.embededId.QQuestionOptionPK questionOptionPK;

    public QQuestionOption(String variable) {
        this(QuestionOption.class, forVariable(variable), INITS);
    }

    public QQuestionOption(Path<? extends QuestionOption> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QQuestionOption(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QQuestionOption(PathMetadata metadata, PathInits inits) {
        this(QuestionOption.class, metadata, inits);
    }

    public QQuestionOption(Class<? extends QuestionOption> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.optionImage = inits.isInitialized("optionImage") ? new com.cos.cercat.common.domain.QImage(forProperty("optionImage")) : null;
        this.question = inits.isInitialized("question") ? new QQuestion(forProperty("question"), inits.get("question")) : null;
        this.questionOptionPK = inits.isInitialized("questionOptionPK") ? new com.cos.cercat.mockExam.domain.embededId.QQuestionOptionPK(forProperty("questionOptionPK")) : null;
    }

}

