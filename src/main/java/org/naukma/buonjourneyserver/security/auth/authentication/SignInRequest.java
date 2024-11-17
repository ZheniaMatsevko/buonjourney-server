package org.naukma.buonjourneyserver.security.auth.authentication;

import lombok.Data;

@Data
public class SignInRequest {

    private String username;
    private String password;
}
