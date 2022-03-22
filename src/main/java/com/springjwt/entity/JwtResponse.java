package com.springjwt.entity;

public class JwtResponse {
	private String token;
	private String expiryTime;
	private boolean isTokenExpired;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(String expiryTime) {
		this.expiryTime = expiryTime;
	}

	public boolean isTokenExpired() {
		return isTokenExpired;
	}

	public void setTokenExpired(boolean isTokenExpired) {
		this.isTokenExpired = isTokenExpired;
	}

}
