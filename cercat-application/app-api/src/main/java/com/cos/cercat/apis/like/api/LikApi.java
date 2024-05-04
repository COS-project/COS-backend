package com.cos.cercat.apis.like.api;

import com.cos.cercat.apis.like.request.LikeRequest;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.like.LikeService;
import com.cos.cercat.user.TargetUser;
import com.cos.cercat.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2")
public class LikApi implements LikeApiDocs {

    private final LikeService likeService;

    @PostMapping("/likes")
    public Response<Void> flipPostLike(LikeRequest request,
                                       @AuthenticationPrincipal User currentUser) {
        likeService.flipLike(TargetUser.from(currentUser.getId()), request.toLike());
        return Response.success("게시글 좋아요/취소 성공");
    }

    @GetMapping("likes/status")
    public Response<Boolean> isLiked(LikeRequest request,
                                     @AuthenticationPrincipal User currentUser) {
        return Response.success(likeService.isLiked(TargetUser.from(currentUser.getId()), request.toLike()));
    }

}
