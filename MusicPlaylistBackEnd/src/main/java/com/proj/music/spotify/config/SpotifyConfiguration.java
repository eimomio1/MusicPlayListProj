package com.proj.music.spotify.config;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;

@Service
public class SpotifyConfiguration {
	
	public static String url = "";
	
	@Value("${redirect.server.ip}")
	private String customIp;
	
	@Bean
	public SpotifyApi getSpotifyObject() {
		URI redirectedURL = SpotifyHttpManager.makeUri(customIp + "/api/get-user-code/");

		return new SpotifyApi
				.Builder()
				.setClientId("0e0599852beb49b182ccf08ce7b81277")
				.setClientSecret("a82782ff30334cef92132010eec53315")
				.setRedirectUri(redirectedURL)
				.build();
	}
}