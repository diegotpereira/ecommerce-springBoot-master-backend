package br.com.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.java.model.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long>{
    
}
