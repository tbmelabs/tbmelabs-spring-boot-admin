package ch.tbmelabs.tv.core.authorizationserver.web.rest;

import ch.tbmelabs.tv.core.authorizationserver.domain.dto.UserDTO;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.mapper.UserMapper;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserCRUDRepository;
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
  private UserCRUDRepository userRepository;

  @Autowired
  private UserMapper userMapper;

  @GetMapping
  public Page<UserDTO> getAllUsers(Pageable pageable) {
    return userRepository.findAll(pageable).map(userMapper::toDto);
  }

  @PutMapping
  public UserDTO updateUser(@RequestBody(required = true) UserDTO userDTO) {
    if (userDTO.getId() == null) {
      throw new IllegalArgumentException("You can only update an existing user!");
    }

    // TODO: Is this still required?
    // User updatedUser = userMapper.toUser(userDTO);
    // if (updatedUser.getPassword() == null) {
    // updatedUser.setPassword(userRepository.findOne(updatedUser.getId()).getPassword());
    // }

    return userMapper.toDto(userRepository.save(userMapper.toEntity(userDTO)));
  }

  @DeleteMapping
  public void deleteUser(@RequestBody(required = true) UserDTO userDTO) {
    if (userDTO.getId() == null) {
      throw new IllegalArgumentException("You can only delete an existing user!");
    }

    userRepository.delete(userMapper.toEntity(userDTO));
  }
}
