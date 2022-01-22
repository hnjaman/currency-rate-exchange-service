package com.hnj.code.model.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class RegistrationResponse {
    private boolean status;
    private String email;
    private String message;
}
