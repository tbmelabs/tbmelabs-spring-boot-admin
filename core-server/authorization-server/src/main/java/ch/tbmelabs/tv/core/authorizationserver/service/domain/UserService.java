package ch.tbmelabs.tv.core.authorizationserver.service.domain;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.UserDTO;

public interface UserService {
  User save(UserDTO userDTO);

  Page<UserDTO> findAll(Pageable pageable);

  Optional<User> findOneById(Long id);

  User update(UserDTO userDTO);

  void delete(Long id);
}
