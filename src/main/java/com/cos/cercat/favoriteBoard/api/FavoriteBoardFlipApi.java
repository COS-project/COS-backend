package com.cos.cercat.favoriteBoard.api;

import com.cos.cercat.favoriteBoard.app.FavoriteBoardFlipService;
import com.cos.cercat.global.Response;
import com.cos.cercat.user.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "게시판 즐겨찾기 생성/취소 API")
public class FavoriteBoardFlipApi {

    private final FavoriteBoardFlipService favoriteBoardFlipService;

    @PostMapping("/favorite-boards/{certificateId}")
    @Operation(summary = "게시판 즐겨찾기 및 해제")
    public Response<Void> flipFavoriteBoard(@PathVariable Long certificateId,
                                            @AuthenticationPrincipal UserDTO user) {
        favoriteBoardFlipService.flipFavoriteBoard(user.getId(), certificateId);
        return Response.success("게시판 즐겨찾기/해제 성공");
    }


}
