package ch.tbmelabs.tv.core.entryserver.security.urlconnection;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.http.HttpMethod;

import sun.misc.BASE64Encoder;

@SuppressWarnings("unchecked")
public class FormDataURLConnection<T extends HttpURLConnection> {
  private static final Charset CHARSET = StandardCharsets.UTF_8;

  private Map<String, String> requestParams = new HashMap<>();

  private T connection;

  public FormDataURLConnection(String url, String username, String password) throws MalformedURLException, IOException {
    connection = configureConnection((T) new URL(url).openConnection(), username, password);
  }

  private T configureConnection(T connection, String username, String password) throws ProtocolException {
    connection.setRequestMethod(HttpMethod.POST.toString());
    connection.setRequestProperty("Authorization", getBase64EncodedAuthorization(username, password));
    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
    connection.setRequestProperty("charset", FormDataURLConnection.CHARSET.toString());
    connection.setDoOutput(true);
    connection.setDoInput(true);

    return connection;
  }

  private String getBase64EncodedAuthorization(String username, String password) {
    return "Basic " + new BASE64Encoder().encode(((username + ":" + password).getBytes()))
        .replaceAll("(?:\\r\\n|\\n\\r|\\n|\\r)", "");
  }

  public FormDataURLConnection<T> addFormField(String name, String value) throws IOException {
    requestParams.put(name, value);

    return this;
  }

  public T connect() throws IOException {
    connection.setRequestProperty("Content-Length", Integer.toString(getFormatedRequestParams().length()));

    try (DataOutputStream writer = new DataOutputStream(connection.getOutputStream())) {
      writer.write(getFormatedRequestParams().getBytes());
    }

    connection.connect();

    return connection;
  }

  private String getFormatedRequestParams() throws UnsupportedEncodingException {
    StringBuilder result = new StringBuilder();

    boolean first = true;
    for (Entry<String, String> entry : requestParams.entrySet()) {
      if (first) {
        first = false;
      } else {
        result.append("&");
      }

      result.append(URLEncoder.encode(entry.getKey(), FormDataURLConnection.CHARSET.toString()));
      result.append("=");
      result.append(URLEncoder.encode(entry.getValue(), FormDataURLConnection.CHARSET.toString()));
    }

    return result.toString();
  }
}