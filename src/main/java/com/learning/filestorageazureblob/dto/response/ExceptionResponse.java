package com.learning.filestorageazureblob.dto.response;

import lombok.Builder;

@Builder
public record ExceptionResponse(
        String message,
        String status,
        int code
) {
}
