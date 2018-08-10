package ch.tbmelabs.tv.core.authorizationserver.ssr;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RenderRequest extends CompletableFuture<String> {

  private String uuid;
  private String uri;

  public RenderRequest(String uri) {
    setUri(uri);

    uuid = UUID.randomUUID().toString();
  }
}
