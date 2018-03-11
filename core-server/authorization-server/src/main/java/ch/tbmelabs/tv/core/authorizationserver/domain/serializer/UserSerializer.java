package ch.tbmelabs.tv.core.authorizationserver.domain.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import ch.tbmelabs.tv.core.authorizationserver.domain.User;

public class UserSerializer extends StdSerializer<User> {
  public UserSerializer() {
    this(null);
  }

  protected UserSerializer(Class<User> t) {
    super(t);
  }

  private static final long serialVersionUID = 1L;

  @Override
  public void serialize(User user, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
      throws IOException {
    jsonGenerator.writeStartObject();

    jsonGenerator.writeNumberField("created", user.getCreated().getTime());
    jsonGenerator.writeNumberField("lastUpdated", user.getLastUpdated().getTime());

    if (user.getId() != null) {
      jsonGenerator.writeNumberField("id", user.getId());
    }

    jsonGenerator.writeStringField("username", user.getUsername());
    jsonGenerator.writeStringField("email", user.getEmail());
    jsonGenerator.writeBooleanField("isEnabled", user.getIsEnabled());
    jsonGenerator.writeBooleanField("isBlocked", user.getIsBlocked());

    if (user.getRoles() != null) {
      jsonGenerator.writeArrayFieldStart("roles");
      user.getRoles().forEach(roleAssociation -> {
        try {
          jsonGenerator.writeString(roleAssociation.getUserRole().getName());
        } catch (IOException e) {
          throw new IllegalArgumentException(e);
        }
      });
      jsonGenerator.writeEndArray();
    }

    jsonGenerator.writeEndObject();
  }
}