package org.spaghetti.chatbackend;

import org.spaghetti.chatbackend.model.Role;
import org.spaghetti.chatbackend.model.User;
import org.spaghetti.chatbackend.repository.RoleRepository;
import org.spaghetti.chatbackend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class ChatBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(UserRepository userRepository, RoleRepository roleRepository,
										PasswordEncoder passwordEncoder){
		return args -> {
			if (roleRepository.findByAuthority("ADMIN").isPresent()) return;
			Role adminRole = roleRepository.save(new Role(0L,"ADMIN"));
			roleRepository.save(new Role(1L,"USER"));

			Set<Role> roles = new HashSet<>(Set.of(adminRole));
			User user = new User();
			user.setUsername("admin");
			user.setPassword(passwordEncoder.encode("admin"));
			user.setAuthorities(roles);
			userRepository.save(user);
		};
	}
}
