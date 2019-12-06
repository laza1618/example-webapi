package com.example.webapi.controller;

import java.net.URI;
import java.util.Collections;

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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.webapi.model.Role;
import com.example.webapi.model.RoleName;
import com.example.webapi.model.User;
import com.example.webapi.payload.SignInRequest;
import com.example.webapi.payload.SignInResponse;
import com.example.webapi.payload.SignUpResquest;
import com.example.webapi.repository.RoleRepository;
import com.example.webapi.repository.UserRepository;
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
	
	@PostMapping("/signup")
	@ApiOperation(value = "Crée un utilisateur", response = String.class)
	public ResponseEntity<?> signup(@Valid @RequestBody SignUpResquest data) {
		if (repoUser.existsByUsername(data.getUsername())) {
			return ResponseEntity.badRequest().body("l'utilisateur existe déjà");
		} else {
			User user = new User(data.getName(), data.getEmail(), data.getUsername());
			user.setPassword(passwordEncoder.encode(data.getPassword()));
			Role role = repoRole.findRoleByName(RoleName.ROLE_ADMIN)
				.orElseGet((() -> {
					Role r = new Role(RoleName.ROLE_ADMIN);
					return repoRole.save(r);
				}));
			user.setRoles(Collections.singleton(role));
			repoUser.save(user);
			URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/auth/signin").build().toUri();
			return ResponseEntity.created(location).body("Utilisateur crée");
		}
		
	}

}
