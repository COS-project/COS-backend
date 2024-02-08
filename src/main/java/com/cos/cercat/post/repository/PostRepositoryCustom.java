package com.cos.cercat.post.repository;

import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.post.dto.request.PostSearchCond;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface PostRepositoryCustom<T> {
    Slice<T> searchPosts(Pageable pageable, Certificate certificate, PostSearchCond cond);
}