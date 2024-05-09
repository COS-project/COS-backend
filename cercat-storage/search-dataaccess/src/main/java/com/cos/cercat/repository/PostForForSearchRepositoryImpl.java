package com.cos.cercat.repository;

import com.cos.cercat.certificate.Certificate;
import com.cos.cercat.certificate.TargetCertificate;
import com.cos.cercat.common.domain.Cursor;
import com.cos.cercat.common.domain.SliceResult;
import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import com.cos.cercat.domain.PostDocument;
import com.cos.cercat.post.TargetPost;
import com.cos.cercat.search.PostForSearch;
import com.cos.cercat.search.PostForSearchRepository;
import com.cos.cercat.search.SearchCond;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostForForSearchRepositoryImpl implements PostForSearchRepository {
    
    private final PostSearchElasticRepository postSearchElasticRepository;

    @Override
    public void save(PostForSearch post) {
        postSearchElasticRepository.save(PostDocument.from(post));
    }

    @Override
    public PostForSearch find(TargetPost targetPost) {
        PostDocument postDocument = postSearchElasticRepository.findById(targetPost.postId()).orElseThrow(
                () -> new CustomException(ErrorCode.POST_NOT_FOUND));
        return postDocument.toDomain();
    }

    @Override
    public void delete(TargetPost targetPost) {
        postSearchElasticRepository.deleteById(targetPost.postId());
    }

    @Override
    public SliceResult<PostForSearch> search(SearchCond cond,
                                             Certificate certificate,
                                             Cursor cursor) {
        Slice<PostDocument> search = postSearchElasticRepository.search(cond, certificate.id(), toPageRequest(cursor));
        List<PostForSearch> postForSearches = search.getContent().stream()
                .map(PostDocument::toDomain)
                .toList();
        return SliceResult.of(postForSearches, search.hasNext());
    }

    @Override
    public List<String> findAutoCompletedKeywords(Certificate certificate, String searchText) {
        return postSearchElasticRepository.getAutoCompletedKeywords(certificate.id(), searchText);
    }

    @Override
    public List<String> findRecentTop10Keywords(Certificate certificate) {
        return postSearchElasticRepository.getRecentTop10Keywords(certificate.id());
    }

    private PageRequest toPageRequest(Cursor cursor) {
        // 여러 정렬 기준을 처리할 Sort 객체 생성
        Sort sort = Sort.by(cursor.sortOrders().stream()
                .map(order -> new Sort.Order(
                        Sort.Direction.fromOptionalString(order.direction().name()).orElse(Sort.Direction.ASC),
                        order.key()
                ))
                .toList());

        return PageRequest.of(cursor.page(), cursor.size(), sort);
    }
}
