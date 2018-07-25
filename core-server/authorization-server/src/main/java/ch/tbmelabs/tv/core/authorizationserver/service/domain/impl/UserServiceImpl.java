package ch.tbmelabs.tv.core.authorizationserver.service.domain.impl;

import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.UserDTO;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.mapper.UserMapper;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.service.domain.UserService;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private UserMapper userMapper;

  private UserCRUDRepository userRepository;

  public UserServiceImpl(UserMapper userMapper, UserCRUDRepository userCRUDRepository) {
    this.userMapper = userMapper;
    this.userRepository = userCRUDRepository;
  }

  @Transactional
  public User save(UserDTO userDTO) {
    if (userDTO.getId() != null) {
      throw new IllegalArgumentException("You can only create a new User without an id!");
    }
      
    return userRepository.save(userMapper.toEntity(userDTO));
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
