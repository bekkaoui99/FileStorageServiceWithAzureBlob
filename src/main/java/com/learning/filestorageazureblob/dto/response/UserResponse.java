package com.learning.filestorageazureblob.dto.response;

import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record UserResponse(
        String firstName,
        String lastName,
        String userName,
        String email,
        String imageUrl
) {
}
