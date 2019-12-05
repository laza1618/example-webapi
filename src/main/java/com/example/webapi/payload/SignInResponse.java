package com.example.webapi.payload;

public class SignInResponse {
	private String accessToken;
	private String tokenType;
	
	
	public SignInResponse(String accessToken) {
		super();
		this.accessToken = accessToken;
		this.tokenType = "Bearer";
	}
	
	public String getAccessToken() {
		return accessToken;
	}
	public String getTokenType() {
		return tokenType;
	}
	
	

}
