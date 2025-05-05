package com.williamm56i.bertolt.controller;

import com.williamm56i.bertolt.controller.dto.UserDto;
import com.williamm56i.bertolt.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/jwt")
public class JwtController {

    @Autowired
    JwtService jwtService;

    @PostMapping(value = "/generateToken")
    public ResponseEntity<String> generateToken(@RequestBody UserDto dto) {
        return ResponseEntity.ok(jwtService.generateToken(dto.getUsername()));
    }
}
