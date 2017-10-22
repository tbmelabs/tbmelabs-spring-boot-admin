package ch.tbmelabs.tv.loginservice.utils;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpMethod;

import sun.misc.BASE64Encoder;

@SuppressWarnings("unchecked")
public class FormDataURLConnector<T extends HttpURLConnection> {
  private static final Charset CHARSET = StandardCharsets.UTF_8;
  private static final String LINE_FEED = "\n\r";

  private URLConnection connection;

  private String boundary;
  private PrintWriter bodyWriter;

  public FormDataURLConnector(String url) throws MalformedURLException, IOException {
    connection = (T) new URL(url).openConnection();

    boundary = "===" + System.currentTimeMillis() + "===";

    addFormDataParameters();
  }

  private void addFormDataParameters() throws ProtocolException {
    ((T) connection).setRequestMethod(HttpMethod.POST.toString());
    connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
    connection.setDoOutput(true);
    connection.setDoInput(true);
  }

  public FormDataURLConnector<T> setBasicAuthorizationHeader(String username, String password) {
    connection.setRequestProperty("Authorization",
        "Basic " + new BASE64Encoder().encode(((String) username + ":" + password).getBytes()));

    return this;
  }

  public FormDataURLConnector<T> addFormField(String name, String value) throws IOException {
    if (bodyWriter == null) {
      initializeBodyStream();
    }

    bodyWriter.append("--" + boundary).append(LINE_FEED);
    bodyWriter.append("Content-Disposition: form-data; name=\"" + name + "\"").append(LINE_FEED);
    bodyWriter.append("Content-Type: text/plain; charset=" + FormDataURLConnector.CHARSET).append(LINE_FEED);
    bodyWriter.append(LINE_FEED);
    bodyWriter.append(value).append(LINE_FEED);
    bodyWriter.flush();

    return this;
  }

  private void initializeBodyStream() throws IOException {
    bodyWriter = new PrintWriter(new OutputStreamWriter(connection.getOutputStream(), FormDataURLConnector.CHARSET),
        true);
  }

  public FormDataURLConnector<T> connect() throws IOException {
    bodyWriter.append(LINE_FEED).flush();
    bodyWriter.append("--" + boundary + "--").append(LINE_FEED);
    bodyWriter.close();

    connection.connect();

    return this;
  }

  public T getResponse() {
    return (T) connection;
  }
}