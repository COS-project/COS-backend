package com.cos.cercat.domain;

import com.cos.cercat.common.domain.Image;
import com.cos.cercat.domain.post.*;
import com.cos.cercat.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DiscriminatorColumn
@Table(name = "post")
public class PostEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String title;

    protected String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    protected UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "certificate_id")
    protected CertificateEntity certificateEntity;

    @Enumerated(EnumType.STRING)
    protected PostType postType;

    @ColumnDefault("0")
    protected Integer likeCount = 0;

    public PostEntity(Long id,
                      String title,
                      String content,
                      UserEntity userEntity,
                      CertificateEntity certificateEntity,
                      PostType postType,
                      Integer likeCount,
                      LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userEntity = userEntity;
        this.certificateEntity = certificateEntity;
        this.postType = postType;
        this.likeCount = likeCount;
        this.createdAt = createdAt;
    }

    public PostEntity(String title, String content, UserEntity userEntity, CertificateEntity certificateEntity, PostType postType) {
        this.title = title;
        this.content = content;
        this.userEntity = userEntity;
        this.certificateEntity = certificateEntity;
        this.postType = postType;
    }

    public Post toDomain(List<Image> images, int commentCount, Set<RecommendTag> recommendTags) {
        return switch (postType) {
            case COMMENTARY ->
                new CommentaryPost(
                    id,
                    userEntity.toDomain(),
                    certificateEntity.toDomain(),
                    new PostContent(title, content, images),
                    new PostStatus(likeCount, commentCount, postType),
                    ((CommentaryPostEntity) this).getQuestionEntity().toDomain(),
                    new DateTime(createdAt, modifiedAt)
                );
            case TIP -> new TipPost(
                    id,
                    userEntity.toDomain(),
                    certificateEntity.toDomain(),
                    new PostContent(title, content, images),
                    new PostStatus(likeCount, commentCount, postType),
                    new DateTime(createdAt, modifiedAt),
                    recommendTags
            );
            default -> new Post(
                    id,
                    userEntity.toDomain(),
                    certificateEntity.toDomain(),
                    new PostContent(title, content, images),
                    new PostStatus(likeCount, commentCount, postType),
                    new DateTime(createdAt, modifiedAt)
            );
        };
    }

    public static PostEntity from(Post post) {
        return switch (post.getPostStatus().getPostType()) {
            case COMMENTARY -> new CommentaryPostEntity(
                    post.getId(),
                    post.getPostContent().getTitle(),
                    post.getPostContent().getContent(),
                    UserEntity.from(post.getUser()),
                    CertificateEntity.from(post.getCertificate()),
                    post.getPostStatus().getPostType(),
                    post.getPostStatus().getLikeCount(),
                    QuestionEntity.from(((CommentaryPost) post).getQuestion()),
                    post.getDateTime().createdAt()
            );
            case TIP -> new TipPostEntity(
                    post.getId(),
                    post.getPostContent().getTitle(),
                    post.getPostContent().getContent(),
                    UserEntity.from(post.getUser()),
                    CertificateEntity.from(post.getCertificate()),
                    post.getPostStatus().getPostType(),
                    post.getPostStatus().getLikeCount(),
                    post.getDateTime().createdAt()
            );
            case NORMAL -> new NormalPostEntity(
                    post.getId(),
                    post.getPostContent().getTitle(),
                    post.getPostContent().getContent(),
                    UserEntity.from(post.getUser()),
                    CertificateEntity.from(post.getCertificate()),
                    post.getPostStatus().getPostType(),
                    post.getPostStatus().getLikeCount(),
                    post.getDateTime().createdAt()
            );
        };
    }
}
