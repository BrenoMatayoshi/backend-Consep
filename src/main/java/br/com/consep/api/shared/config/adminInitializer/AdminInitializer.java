package br.com.consep.api.shared.config.adminInitializer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.consep.api.shared.enums.Role;
import br.com.consep.api.user.entity.User;
import br.com.consep.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Value("${api.user.name}")
  private String name;

  @Value("${api.user.login}")
  private String login;

  @Value("${api.user.password}")
  private String password;

  @Override
  public void run(String... args) {

    if (userRepository.findByLogin(login).isPresent()) {
      return;
    }

    User admin = new User();
    admin.setUsername(name);
    admin.setPassword(passwordEncoder.encode(password));
    admin.setRole(Role.ADMIN);

    userRepository.save(admin);
  }
}