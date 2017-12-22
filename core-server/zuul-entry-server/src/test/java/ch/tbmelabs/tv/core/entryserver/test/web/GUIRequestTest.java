package ch.tbmelabs.tv.core.entryserver.test.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import ch.tbmelabs.tv.core.entryserver.test.AbstractZuulApplicationContextAwareJunitTest;

public class GUIRequestTest extends AbstractZuulApplicationContextAwareJunitTest {
  @Autowired
  private MockMvc mockMvc;

  @Test
  public void requestToRootURLShouldSucceed() throws Exception {
    mockMvc.perform(get("/")).andDo(print()).andExpect(status().is2xxSuccessful());
  }

  @Test
  public void requestToPublicURLShouldSucceed() throws Exception {
    mockMvc.perform(get("/public")).andDo(print()).andExpect(status().is2xxSuccessful());
  }
}