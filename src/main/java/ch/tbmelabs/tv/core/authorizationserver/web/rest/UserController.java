package ch.tbmelabs.tv.core.authorizationserver.web.rest;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ch.tbmelabs.serverconstants.security.UserRoleConstants;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.UserDTO;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.mapper.UserMapper;
import ch.tbmelabs.tv.core.authorizationserver.service.domain.UserService;

@RestController
@RequestMapping({"${spring.data.rest.base-path}/users"})
@PreAuthorize("hasAuthority('" + UserRoleConstants.SERVER_SUPPORT + "')")
public class UserController {

  private UserService userService;

  private UserMapper userMapper;

  public UserController(UserService userService, UserMapper userMapper) {
    this.userService = userService;
    this.userMapper = userMapper;
  }

  @GetMapping
  public Page<UserDTO> getAllUsers(Pageable pageable) {
    return userService.findAll(pageable);
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDTO> getOneUser(@PathVariable Long id) {
    Optional<UserDTO> optionalUser = userService.findById(id).map(userMapper::toDto);

    if (!optionalUser.isPresent()) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(optionalUser.get());
  }

  @PutMapping
  public UserDTO updateUser(@RequestBody UserDTO userDTO) {
    return userMapper.toDto(userService.update(userDTO));
  }

  @DeleteMapping("/{id}")
  public void deleteUser(@PathVariable Long id) {
    userService.delete(id);
  }
}
