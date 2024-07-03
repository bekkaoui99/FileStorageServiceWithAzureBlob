package com.learning.filestorageazureblob.dto.request;

import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record UserRequest(
        String firstName,
        String lastName,
        String userName,
        String email,
        String password,
        MultipartFile file
) {
}
