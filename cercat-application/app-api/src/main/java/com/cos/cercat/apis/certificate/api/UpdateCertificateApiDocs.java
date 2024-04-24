package com.cos.cercat.apis.certificate.api;

import com.cos.cercat.apis.certificate.request.InterestCertificateUpdateRequest;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "자격증 수정 API")
public interface UpdateCertificateApiDocs {

    @Operation(summary = "관심 자격증 수정")
    Response<Void> updateInterestCertificates(@RequestBody InterestCertificateUpdateRequest request,
                                              @AuthenticationPrincipal UserDTO currentUser);

}
