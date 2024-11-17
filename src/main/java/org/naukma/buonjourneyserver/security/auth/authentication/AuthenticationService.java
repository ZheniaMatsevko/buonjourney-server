package org.naukma.buonjourneyserver.security.auth.authentication;

import org.springframework.http.ResponseEntity;

public interface AuthenticationService {

    ResponseEntity<String> signin(SignInRequest signInRequest);
}
