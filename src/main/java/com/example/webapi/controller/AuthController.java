package com.example.webapi.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.webapi.model.User;
import com.example.webapi.payload.SignInResponse;
import com.example.webapi.payload.SignInRequest;
import com.example.webapi.repository.RoleRepository;
import com.example.webapi.repository.UserRepository;
import com.example.webapi.security.CurrentUser;
import com.example.webapi.security.JwtTokenProvider;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/auth")
@Api(value = "Authentification")
public class AuthController {
	
	@Autowired
	AuthenticationManager authManager;
	
	@Autowired
	UserRepository repoUser;
	
	@Autowired
	RoleRepository repoRole;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	JwtTokenProvider tokenProvider;
	
	@PostMapping("/signin")
	@ApiOperation(value = "Authentification, retourne un token signé", response = String.class)
	@ApiResponses(value= {
			@ApiResponse(code = 404, message="Erreur login mdp")
	})
	public ResponseEntity<?> signIn(@Valid @RequestBody SignInRequest data) {
		Authentication auth = authManager.authenticate(
			new UsernamePasswordAuthenticationToken(data.getUsername(), data.getPassword())
		);
		
		SecurityContextHolder.getContext().setAuthentication(auth);
		String jwt = tokenProvider.GenerateToken(auth);
		
		return ResponseEntity.ok(new SignInResponse(jwt));
	}
	
	@GetMapping("/signout")
	@ApiOperation(value = "Déconnecte l'utilisateur")
	public ResponseEntity<?> signout(HttpServletRequest req, HttpServletResponse resp) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		new SecurityContextLogoutHandler().logout(req, resp, auth);
		return ResponseEntity.ok().build();
	}

}
