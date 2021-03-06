package fr.thibaultd.myvotingsystem.backend.configuration;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;
import fr.thibaultd.myvotingsystem.backend.api.ElectionResource;
import fr.thibaultd.myvotingsystem.backend.api.VoteResource;

@Component
public class JerseyConfig extends ResourceConfig {

  public JerseyConfig() {
    // packages("fr.thibaultd.myvotingsystem.backend.configuration"); // doesn't work with fat jar
    register(RequestFilter.class);
    register(CorsFilter.class);

    register(ElectionResource.class);
    register(VoteResource.class);

  }
}
