package com.kai.pthings.entity;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AuthenticateRequest {
    private String uname;
    String password;
}
