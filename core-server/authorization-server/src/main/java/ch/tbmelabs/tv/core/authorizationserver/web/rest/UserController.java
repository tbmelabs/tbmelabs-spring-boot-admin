package ch.tbmelabs.tv.core.authorizationserver.web.rest;

import ch.tbmelabs.tv.core.authorizationserver.domain.dto.UserDTO;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.mapper.UserMapper;
import ch.tbmelabs.tv.core.authorizationserver.service.domain.UserService;
import ch.tbmelabs.tv.shared.constants.security.UserAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"${spring.data.rest.base-path}/users"})
@PreAuthorize("hasAuthority('" + UserAuthority.SERVER_SUPPORT + "')")
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private UserMapper userMapper;

  @GetMapping
  public Page<UserDTO> getAllUsers(Pageable pageable) {
    return userService.findAll(pageable);
  }

  @PutMapping
  public UserDTO updateUser(@RequestBody UserDTO userDTO) {
    return userMapper.toDto(userService.update(userDTO));
  }

  @DeleteMapping
  public void deleteUser(@RequestBody UserDTO userDTO) {
    userService.delete(userDTO);
  }
}
