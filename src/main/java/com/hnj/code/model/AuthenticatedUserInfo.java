package com.hnj.code.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AuthenticatedUserInfo {
    private String email;
    private boolean authenticated;
    private String message;
}
