package ch.tbmelabs.tv.core.authorizationserver.test.web.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Random;
import java.util.stream.Collectors;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientauthority.ClientAuthorityAssociation;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientgranttype.ClientGrantTypeAssociation;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientscope.ClientScopeAssociation;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.ClientDTO;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.mapper.ClientDTOMapper;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.ClientCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAware;
import ch.tbmelabs.tv.core.authorizationserver.test.domain.dto.ClientDTOTest;
import ch.tbmelabs.tv.shared.constants.security.UserAuthority;

public class ClientControllerIntTest extends AbstractOAuth2AuthorizationApplicationContextAware {
  @Value("${spring.data.rest.base-path}/clients")
  private String clientsEndpoint;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ClientDTOMapper clientMapper;

  @Autowired
  private ClientCRUDRepository clientRepository;

  private final ClientDTO testClientDTO = createTestClientDTO();

  public static ClientDTO createTestClientDTO() {
    Client client = ClientDTOTest.createTestClient();

    return new ClientDTO(client,
        client.getGrantTypes().stream().map(ClientGrantTypeAssociation::getClientGrantType)
            .collect(Collectors.toList()),
        client.getGrantedAuthorities().stream().map(ClientAuthorityAssociation::getClientAuthority)
            .collect(Collectors.toList()),
        client.getScopes().stream().map(ClientScopeAssociation::getClientScope).collect(Collectors.toList()));
  }

  @Test
  @WithMockUser(username = "ClientControllerIntTestUser", authorities = { UserAuthority.SERVER_ADMIN })
  public void postClientEndpointIsAccessibleToServerAdmins() throws Exception {
    mockMvc
        .perform(post(clientsEndpoint).contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(testClientDTO)))
        .andDo(print()).andExpect(status().is(HttpStatus.OK.value()));
  }

  @Test
  @WithMockUser(username = "ClientControllerIntTestUser", authorities = { UserAuthority.SERVER_SUPPORT })
  public void postClientEndpointIsNotAccessibleToNonServerAdmins() throws Exception {
    mockMvc
        .perform(post(clientsEndpoint).contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(testClientDTO)))
        .andDo(print()).andExpect(status().is(HttpStatus.FORBIDDEN.value()));
  }

  @Test
  @WithMockUser(username = "ClientControllerIntTestUser", authorities = { UserAuthority.SERVER_ADMIN })
  public void getClientsEndpointIsAccessibleToServerAdmins() throws Exception {
    mockMvc.perform(get(clientsEndpoint)).andDo(print()).andExpect(status().is(HttpStatus.OK.value()));
  }

  @Test
  @WithMockUser(username = "ClientControllerIntTestUser", authorities = { UserAuthority.SERVER_SUPPORT })
  public void getClientsEndpointIsNotAccessibleToNonServerAdmins() throws Exception {
    mockMvc.perform(get(clientsEndpoint)).andDo(print()).andExpect(status().is(HttpStatus.FORBIDDEN.value()));
  }

  @Test
  @WithMockUser(username = "ClientControllerIntTestUser", authorities = { UserAuthority.SERVER_ADMIN })
  public void putClientEndpointIsAccessibleToServerAdmins() throws Exception {
    testClientDTO.setId(clientMapper.toClientDTO(clientRepository.save(clientMapper.toClient(testClientDTO))).getId());

    mockMvc
        .perform(put(clientsEndpoint).contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(testClientDTO)))
        .andDo(print()).andExpect(status().is(HttpStatus.OK.value()));
  }

  @Test
  @WithMockUser(username = "ClientControllerIntTestUser", authorities = { UserAuthority.SERVER_SUPPORT })
  public void putClientEndpointIsNotAccessibleToNonServerAdmins() throws Exception {
    mockMvc
        .perform(put(clientsEndpoint).contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(testClientDTO)))
        .andDo(print()).andExpect(status().is(HttpStatus.FORBIDDEN.value()));
  }

  @Test
  @WithMockUser(username = "ClientControllerIntTestUser", authorities = { UserAuthority.SERVER_ADMIN })
  public void deleteClientEndpointIsAccessibleToServerAdmins() throws Exception {
    testClientDTO.setId(new Random().nextLong());

    mockMvc
        .perform(delete(clientsEndpoint).contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(testClientDTO)))
        .andDo(print()).andExpect(status().is(HttpStatus.OK.value()));
  }

  @Test
  @WithMockUser(username = "ClientControllerIntTestUser", authorities = { UserAuthority.SERVER_SUPPORT })
  public void deleteClientEndpointIsNotAccessibleToNonServerAdmins() throws Exception {
    mockMvc
        .perform(delete(clientsEndpoint).contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(testClientDTO)))
        .andDo(print()).andExpect(status().is(HttpStatus.FORBIDDEN.value()));
  }
}