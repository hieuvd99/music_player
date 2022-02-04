package com.example.musicplayer.controller;

import com.example.musicplayer.dto.request.LoginForm;
import com.example.musicplayer.dto.request.SignUpForm;
import com.example.musicplayer.dto.response.JwtResponse;
import com.example.musicplayer.dto.response.ResponseMessage;
import com.example.musicplayer.model.Role;
import com.example.musicplayer.model.RoleName;
import com.example.musicplayer.model.User;
import com.example.musicplayer.security.jwt.JwtProvider;
import com.example.musicplayer.security.serviceImpl.UserPrinciple;
import com.example.musicplayer.service.RoleService;
import com.example.musicplayer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthRestAPIs {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);	//tạo token
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt, userPrinciple.getUsername(),userPrinciple.getAuthorities()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
    	//check username nếu trùng
        if (userService.existByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<>(new ResponseMessage("nouser"),	//trả về no user
                    HttpStatus.OK);
        }
      //check email nếu trùng
        if (userService.existByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>(new ResponseMessage("noemail"),	//trả về no email
                    HttpStatus.OK);
        }

        // Tạo tài khoản người dùng
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
                passwordEncoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();	//set role đăng kí

        //check-case theo role 
        strRoles.forEach(role -> {
            switch (role) {
                case "admin":
                    Role adminRole = roleService.findByName(RoleName.ADMIN)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(adminRole);

                    break;
                case "pm":
                    Role pmRole = roleService.findByName(RoleName.PM)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(pmRole);

                    break;
                default:
                    Role userRole = roleService.findByName(RoleName.USER)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(userRole);
            }
        });

        user.setRoles(roles);
        userService.save(user);

        return new ResponseEntity<>(new ResponseMessage("yes"), HttpStatus.OK);
    }
}
