package ch.tbmelabs.tv.core.authorizationserver.service.domain;

import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.UserDTO;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.mapper.UserMapper;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserRoleAssociationCRUDRepository;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private UserMapper userMapper;

  private UserCRUDRepository userRepository;

  private UserRoleAssociationCRUDRepository userRoleRepository;

  public UserService(UserMapper userMapper, UserCRUDRepository userCRUDRepository,
      UserRoleAssociationCRUDRepository userRoleAssociationCRUDRepository) {
    this.userMapper = userMapper;
    this.userRepository = userCRUDRepository;
    this.userRoleRepository = userRoleAssociationCRUDRepository;
  }

  @Transactional
  public User save(UserDTO userDTO) {
    if (userDTO.getId() != null) {
      throw new IllegalArgumentException("You can only create a new User without an id!");
    }

    User user = userMapper.toEntity(userDTO);
    user = userRepository.save(user);

    userMapper.rolesToAssociations(userDTO.getRoles(), user).forEach(userRoleRepository::save);

    return userRepository.findById(user.getId()).get();
  }

  public Page<UserDTO> findAll(Pageable pageable) {
    return userRepository.findAll(pageable).map(userMapper::toDto);
  }

  public Optional<User> findOneById(Long id) {
    return userRepository.findById(id);
  }

  public User update(UserDTO userDTO) {
    Optional<User> existing;
    if (userDTO.getId() == null || (existing = userRepository.findById(userDTO.getId())) == null) {
      throw new IllegalArgumentException("You can only update an existing User!");
    }

    User user = userMapper.updateUserFromUserDTO(userDTO, existing.get());
    if (user.getPassword() == null || user.getPassword().isEmpty()) {
      user.setPassword(existing.get().getPassword());
    }

    user = userRepository.save(user);

    userMapper.rolesToAssociations(userDTO.getRoles(), user).forEach(userRoleRepository::save);

    return userRepository.findById(user.getId()).get();
  }

  public void delete(Long id) {
    userRepository.deleteById(id);
  }
}
