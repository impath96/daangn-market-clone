package com.zerobase.daangnmarketclone.controller;

import com.zerobase.daangnmarketclone.dto.SignUpRequestDto;
import com.zerobase.daangnmarketclone.service.SignUpService;
import java.net.URI;
import java.net.URISyntaxException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SignUpController {

    private final SignUpService signUpService;

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody SignUpRequestDto signUpRequestDto)
        throws URISyntaxException {

        Long userId = signUpService.signUp(signUpRequestDto);

        return ResponseEntity
            .created(createURI(userId))
            .build();
    }

    private URI createURI(Long userId) throws URISyntaxException {
        return new URI("/users/" + userId);
    }

}
