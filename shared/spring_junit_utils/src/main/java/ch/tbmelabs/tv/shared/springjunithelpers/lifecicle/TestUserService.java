package ch.tbmelabs.tv.shared.springjunithelpers.lifecicle;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

@Service
public class TestUserService {
  @PostConstruct
  public void initBean() {
    System.out.println("INIT BEAN!");
  }
}