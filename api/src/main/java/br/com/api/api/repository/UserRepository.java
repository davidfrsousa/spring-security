package br.com.api.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.api.api.model.user.User;

public interface UserRepository extends JpaRepository<User,Integer>{

    UserDetails findByLogin(String login);

}
