package fr.thibaultd.myvotingsystem.backend.configuration;

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
@PreMatching
public class RequestFilter implements ContainerRequestFilter {

  @Override
  public void filter(ContainerRequestContext ctx) throws IOException {
    Logger logger = LoggerFactory.getLogger(RequestFilter.class);
    logger.info(ctx.getUriInfo().getRequestUri().getPath());
    logger.info(ctx.getHeaders().toString());
  }
}

