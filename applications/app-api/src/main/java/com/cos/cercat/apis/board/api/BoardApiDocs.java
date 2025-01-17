package com.cos.cercat.apis.board.api;

import com.cos.cercat.apis.board.response.BoardResponse;
import com.cos.cercat.web.Response;
import com.cos.cercat.domain.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "게시판 API")
public interface BoardApiDocs {

    @Operation(summary = "게시판 즐겨찾기 및 해제")
    Response<Void> flipFavoriteBoard(Long certificateId, User currentUser);

    @Operation(summary = "게시판 목록 조회")
    Response<List<BoardResponse>> readBoards(User currentUser);
}
