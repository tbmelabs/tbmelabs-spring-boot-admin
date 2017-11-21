package ch.tbmelabs.tv.core.entryserver.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class AuthenticationRequestUtils {
  private static final String ACCESS_TOKEN = "access_token";
  private static final String TOKEN_TYPE = "token_type";
  private static final String REFRESH_TOKEN = "refresh_token";
  private static final String EXPIRES_IN = "expires_in";
  private static final String SCOPE = "scope";

  public static void transformJSONBodyToHeaderValues(InputStream in, HttpServletResponse out) throws IOException {
    JsonObject json = new Gson().fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), JsonObject.class);

    out.setHeader(TOKEN_TYPE, json.get(TOKEN_TYPE).getAsString());
    out.setHeader(ACCESS_TOKEN, json.get(ACCESS_TOKEN).getAsString());
    out.setHeader(REFRESH_TOKEN, json.get(REFRESH_TOKEN).getAsString());
    out.setHeader(SCOPE, json.get(SCOPE).getAsString());
    out.setHeader(EXPIRES_IN, json.get(EXPIRES_IN).getAsString());
  }
}