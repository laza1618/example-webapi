package com.example.webapi.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.webapi.exceptions.RessourceNotFoundException;
import com.example.webapi.model.User;
import com.example.webapi.repository.UserRepository;

import io.jsonwebtoken.Jwts;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	private UserRepository repo;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String jwt = getJwtFromRequest(request);
		if (StringUtils.hasText(jwt) && tokenProvider.isValidateToken("jwt")) {
			User user = repo.findById(tokenProvider.getUserIdFromJwt(jwt)).orElseThrow(
					() -> new RessourceNotFoundException("User", "id", 0)
			);
			
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null);
			auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			
			SecurityContextHolder.getContext().setAuthentication(auth);
		}
		filterChain.doFilter(request, response);

	}
	
	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.split(" ")[1];
		}
		return null;
	}

}
