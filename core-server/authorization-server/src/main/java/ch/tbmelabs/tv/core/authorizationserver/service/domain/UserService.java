package ch.tbmelabs.tv.core.authorizationserver.service.domain;

import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.UserDTO;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.mapper.UserMapper;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserRoleAssociationCRUDRepository;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private UserCRUDRepository userRepository;

  @Autowired
  private UserRoleAssociationCRUDRepository userRoleRepository;

  @Transactional
  public User save(UserDTO userDTO) {
    if (userDTO.getId() != null) {
      throw new IllegalArgumentException("You can only create a new User without an id!");
    }

    User user = userMapper.toEntity(userDTO);
    user = userRepository.save(user);

    userMapper.rolesToAssociations(userDTO.getRoles(), user).forEach(userRoleRepository::save);

    return userRepository.findOne(user.getId());
  }

  public Page<UserDTO> findAll(Pageable pageable) {
    return userRepository.findAll(pageable).map(userMapper::toDto);
  }

  public Optional<User> findOneById(Long id) {
    return Optional.ofNullable(userRepository.findOne(id));
  }

  public User update(UserDTO userDTO) {
    if (userDTO.getId() == null || userRepository.findOne(userDTO.getId()) == null) {
      throw new IllegalArgumentException("You can only update an existing User!");
    }

    User user = userMapper.toEntity(userDTO);
    if (user.getPassword() == null || user.getPassword().isEmpty()) {
      user.setPassword(userRepository.findOne(user.getId()).getPassword());
    }

    user = userRepository.save(user);

    userMapper.rolesToAssociations(userDTO.getRoles(), user).forEach(userRoleRepository::save);

    return userRepository.findOne(user.getId());
  }

  public void delete(Long id) {
    userRepository.delete(id);
  }
}
