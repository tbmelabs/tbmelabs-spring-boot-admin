package ch.tbmelabs.tv.core.authorizationserver.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.tbmelabs.tv.core.authorizationserver.domain.dto.UserProfile;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.mapper.UserProfileMapper;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserCRUDRepository;
import ch.tbmelabs.tv.shared.constants.security.UserAuthority;

@RestController
@RequestMapping({ "${spring.data.rest.base-path}/users" })
@PreAuthorize("hasAuthority('" + UserAuthority.SERVER_SUPPORT + "')")
public class UserController {
  @Autowired
  private UserCRUDRepository userRepository;

  @Autowired
  private UserProfileMapper userMapper;

  @PostMapping
  public UserProfile createUser(@RequestBody(required = true) UserProfile userProfile) {
    if (userProfile.getId() != null) {
      throw new IllegalArgumentException("You can only create a new user without an id!");
    }

    return userMapper.toUserProfile(userRepository.save(userMapper.toUser(userProfile)));
  }

  @GetMapping
  public Page<UserProfile> getAllUsers(Pageable pageable) {
    return userRepository.findAll(pageable).map(userMapper::toUserProfile);
  }

  @PutMapping
  public UserProfile updateUser(@RequestBody(required = true) UserProfile userProfile) {
    if (userProfile.getId() == null) {
      throw new IllegalArgumentException("You can only update an existing user!");
    }

    return userMapper.toUserProfile(userRepository.save(userMapper.toUser(userProfile)));
  }

  @DeleteMapping
  public void deleteUser(@RequestBody(required = true) UserProfile userProfile) {
    if (userProfile.getId() == null) {
      throw new IllegalArgumentException("You can only delete an existing user!");
    }

    userRepository.delete(userMapper.toUser(userProfile));
  }
}