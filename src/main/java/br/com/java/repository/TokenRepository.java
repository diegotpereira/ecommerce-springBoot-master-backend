package br.com.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.java.model.AuthenticationToken;
import br.com.java.model.User;

@Repository
public interface TokenRepository extends JpaRepository<AuthenticationToken, Integer>{

    AuthenticationToken findTokenByUser(User user);
    AuthenticationToken findTokenByToken(String Token);   
}
