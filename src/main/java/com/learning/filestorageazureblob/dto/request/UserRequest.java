package com.learning.filestorageazureblob.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record UserRequest(
        String userName,
        MultipartFile file
) {
}
