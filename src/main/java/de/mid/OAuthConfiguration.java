package de.mid;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@ConfigurationProperties(prefix = "oauth2.client.registration")
@EnableWebSecurity(debug = true)
public class OAuthConfiguration {

  @NestedConfigurationProperty
  private ClientData google;

  @NestedConfigurationProperty
  private ClientData github;

  public ClientData getGoogle() {
    return google;
  }

  public void setGoogle(ClientData google) {
    this.google = google;
  }

  public ClientData getGithub() {
    return github;
  }

  public void setGithub(ClientData github) {
    this.github = github;
  }

  @Bean
  @Order(1)
  public SecurityFilterChain googleSecurityFlterChain(final HttpSecurity http) throws Exception {
    return http
        .securityMatcher("/foo", "/oauth2/authorization/google")
        .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
        .oauth2Login(oauth2 ->
            oauth2.clientRegistrationRepository(this.googleClientRegistrationRepository()))
        .build();
  }

  @Bean
  public SecurityFilterChain githubSecurityfilterChain(final HttpSecurity http) throws Exception {
    return http
        .securityMatcher("/bar", "/oauth2/authorization/github")
        .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
        .oauth2Login(oauth2 ->
            oauth2.clientRegistrationRepository(this.githubClientRegistrationRepository()))
        .build();
  }

  public ClientRegistrationRepository googleClientRegistrationRepository() {
    return new InMemoryClientRegistrationRepository(
        CommonOAuth2Provider.GOOGLE.getBuilder("google")
            .clientId(this.google.clientId)
            .clientSecret(this.google.clientSecret)
            .build()
    );
  }

  public ClientRegistrationRepository githubClientRegistrationRepository() {
    return new InMemoryClientRegistrationRepository(
        CommonOAuth2Provider.GITHUB.getBuilder("github")
            .clientId(this.github.clientId)
            .clientSecret(this.github.clientSecret)
            .redirectUri("{baseUrl}/{action}/oauth2/code/{registrationId}")
            .build()
    );
  }

  public record ClientData(String clientId, String clientSecret) {

  }
}
