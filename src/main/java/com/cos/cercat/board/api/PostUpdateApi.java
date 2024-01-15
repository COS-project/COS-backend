package com.cos.cercat.board.api;

import com.cos.cercat.board.app.PostUpdateService;
import com.cos.cercat.board.dto.request.PostType;
import com.cos.cercat.board.dto.request.PostUpdateRequest;
import com.cos.cercat.global.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "게시글 수정 API")
public class PostUpdateApi {

    private final PostUpdateService postUpdateService;

    @PutMapping(value = "/{certificateId}/{postType}/posts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "게시글 수정")
    public Response<Void> updateCommentaryPost(@PathVariable Long certificateId,
                                               @PathVariable PostType postType,
                                               @RequestPart PostUpdateRequest request,
                                               @RequestPart List<MultipartFile> images) {

        postUpdateService.updatePost(certificateId, postType, request, images, 1L);

        return Response.success("게시글 수정 완료");
    }

}