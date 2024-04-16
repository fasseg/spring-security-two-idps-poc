package de.mid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringSecurityTwoIdpPoC {


  public static void main(final String[] args) {
    SpringApplication.run(new Class[]{SpringSecurityTwoIdpPoC.class, OAuthConfiguration.class},
        args);
  }

}
