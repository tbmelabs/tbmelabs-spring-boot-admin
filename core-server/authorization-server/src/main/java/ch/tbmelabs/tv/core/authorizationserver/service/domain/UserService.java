package ch.tbmelabs.tv.core.authorizationserver.service.domain;

import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.UserDTO;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.mapper.UserMapper;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserCRUDRepository;
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

  @Transactional
  public User save(UserDTO userDTO) {
    if (userDTO.getId() != null) {
      throw new IllegalArgumentException("You can only create a new User without an id!");
    }

    User user = userMapper.toEntity(userDTO);
    user = userRepository.save(user);

    user.setRoles(userMapper.rolesToAssociations(userDTO.getRoles(), user));

    return userRepository.save(user);
  }

  public Page<UserDTO> findAll(Pageable pageable) {
    return userRepository.findAll(pageable).map(userMapper::toDto);
  }

  public User update(UserDTO userDTO) {
    if (userDTO.getId() == null || userRepository.findOne(userDTO.getId()) == null) {
      throw new IllegalArgumentException("You can only update an existing User!");
    }

    return save(userDTO);
  }

  public void delete(UserDTO userDTO) {
    if (userDTO.getId() == null) {
      throw new IllegalArgumentException("You can only delete an existing User!");
    }

    userRepository.delete(userMapper.toEntity(userDTO));
  }
}
