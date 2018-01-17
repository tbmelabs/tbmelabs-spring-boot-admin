package ch.tbmelabs.tv.core.authorizationserver.test.web.oauth2;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAware;
import ch.tbmelabs.tv.shared.constants.security.SecurityRole;

@WithMockUser(username = "Testuser", password = "Password99$")
public class PrincipalEndpointTest extends AbstractOAuth2AuthorizationApplicationContextAware {
  private static final String ME_ENDPOINT = "/me";
  private static final String USER_ENDPOINT = "/user";

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void meEndpointShouldReturnCorrectAuthentication() throws Exception {
    runJsonCredentialAssertChain(new JSONObject(mockMvc.perform(get(ME_ENDPOINT)).andDo(print())
        .andExpect(status().is(HttpStatus.OK.value())).andReturn().getResponse().getContentAsString()));
  }

  @Test
  public void userEndpointShouldReturnCorrectAuthentication() throws Exception {
    runJsonCredentialAssertChain(new JSONObject(mockMvc.perform(get(USER_ENDPOINT)).andDo(print())
        .andExpect(status().is(HttpStatus.OK.value())).andReturn().getResponse().getContentAsString()));
  }

  private void runJsonCredentialAssertChain(JSONObject jsonCredential) throws JSONException {
    JSONArray authorities = jsonCredential.getJSONArray("authorities");
    assertThat(authorities).isNotNull();
    assertThat(authorities.length()).isEqualTo(1);
    assertThat(authorities.getJSONObject(0).get("authority")).isEqualTo("ROLE_" + SecurityRole.USER);

    Boolean authenticated = jsonCredential.getBoolean("authenticated");
    assertThat(authenticated).isNotNull().isTrue();

    String credentials = jsonCredential.getString("credentials");
    assertThat(credentials).isNotNull().isEqualTo("Password99$");

    String name = jsonCredential.getString("name");
    assertThat(name).isNotNull().isEqualTo("Testuser");

    JSONObject principal = jsonCredential.getJSONObject("principal");
    assertThat(principal).isNotNull();
    assertThat(principal.getBoolean("accountNonExpired")).isNotNull().isTrue();
    assertThat(principal.getBoolean("accountNonLocked")).isNotNull().isTrue();
    assertThat(principal.getJSONArray("authorities").toString()).isNotNull().isEqualTo(authorities.toString());
    assertThat(principal.getBoolean("credentialsNonExpired")).isNotNull().isTrue();
    assertThat(principal.getString("password")).isNotNull().isEqualTo("Password99$");
    assertThat(principal.getString("username")).isNotNull().isEqualTo("Testuser");
  }
}