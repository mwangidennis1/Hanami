package com.example.hanami.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
/* usage depends on which http will be used */

@Data
@Builder
@AllArgsConstructor
public class EmailRequest {
    private String email;
}
