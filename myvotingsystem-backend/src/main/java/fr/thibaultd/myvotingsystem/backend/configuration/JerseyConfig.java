package fr.thibaultd.myvotingsystem.backend.configuration;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {

  public JerseyConfig() {
    packages("fr.thibaultd.myvotingsystem.backend");
  }
}
