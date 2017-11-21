package ch.tbmelabs.tv.core.authenticationserver.web;

import java.io.IOException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class ApplicationResourcesController {
  private static final String APPLICATIONS_FILE_NAME = "known-applications.json";

  @RequestMapping(value = { "/api/known-applications" }, method = RequestMethod.GET)
  public JsonNode getKnownApplications() throws JsonParseException, JsonMappingException, IOException {
    return new ObjectMapper().readValue(getClass().getClassLoader().getResourceAsStream(APPLICATIONS_FILE_NAME),
        JsonNode.class);
  }
}