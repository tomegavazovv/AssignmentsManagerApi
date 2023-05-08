package com.assignmentsmanager.web;

import ch.qos.logback.core.util.Duration;
import com.assignmentsmanager.domain.User;
import com.assignmentsmanager.dto.AuthCredentialsRequest;
import com.assignmentsmanager.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Value("${cookies.domain}")
    private String domain;

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody AuthCredentialsRequest req) {
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));

            User user = (User) authenticate.getPrincipal();
            user.setPassword(null);

            String token = jwtUtil.generateToken(user);
            ResponseCookie cookie = ResponseCookie
                    .from("jwt", token).domain(domain).path("/")
                    .maxAge(Duration.buildByDays(365).getMilliseconds()).build();
            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(token);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestParam String token,
                                           @AuthenticationPrincipal User user){

        try{
            Boolean isValid = jwtUtil.validateToken(token, user);
            return ResponseEntity.ok(isValid);
        }catch (ExpiredJwtException e){
            return ResponseEntity.ok(false);
        }
    }

}
