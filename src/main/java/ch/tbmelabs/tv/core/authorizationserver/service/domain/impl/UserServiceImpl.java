package ch.tbmelabs.tv.core.authorizationserver.service.domain.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ch.tbmelabs.serverconstants.security.UserRoleEnum;
import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.userrole.UserRoleAssociation;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.UserDTO;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.mapper.UserMapper;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.RoleCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.service.domain.UserService;

@Service
public class UserServiceImpl implements UserService {

  private UserMapper userMapper;

  private UserCRUDRepository userRepository;

  private RoleCRUDRepository roleRepository;

  public UserServiceImpl(UserMapper userMapper, UserCRUDRepository userCRUDRepository,
      RoleCRUDRepository roleRepository) {
    this.userMapper = userMapper;
    this.userRepository = userCRUDRepository;
    this.roleRepository = roleRepository;
  }

  @Transactional
  public User save(UserDTO userDTO) {
    if (userDTO.getId() != null) {
      throw new IllegalArgumentException("You can only create a new User without an id!");
    }

    User userToPersist = userMapper.toEntity(userDTO);
    setDefaultRolesIfNonePresent(userToPersist);

    return userRepository.save(userToPersist);
  }

  private void setDefaultRolesIfNonePresent(User newUser) {
    newUser.setRoles(new HashSet<>(Collections.singletonList(new UserRoleAssociation(newUser,
        roleRepository.findByName(UserRoleEnum.USER.getAuthority())
            .orElseThrow(() -> new IllegalArgumentException("Unable to find default "
                + UserRoleEnum.class + "'" + UserRoleEnum.USER.getAuthority() + "'"))))));
  }

  public Page<UserDTO> findAll(Pageable pageable) {
    return userRepository.findAll(pageable).map(userMapper::toDto);
  }

  public Optional<User> findById(Long id) {
    return userRepository.findById(id);
  }

  @Transactional
  public User update(UserDTO userDTO) {
    Optional<User> existing;
    if (userDTO.getId() == null || (existing = userRepository.findById(userDTO.getId())) == null) {
      throw new IllegalArgumentException("You can only update an existing User!");
    }

    return userRepository.save(userMapper.updateUserFromUserDTO(userDTO, existing.get()));
  }

  public void delete(Long id) {
    userRepository.deleteById(id);
  }
}
