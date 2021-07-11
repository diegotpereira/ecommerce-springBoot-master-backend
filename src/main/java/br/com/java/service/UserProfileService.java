package br.com.java.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.java.model.UserProfile;
import br.com.java.repository.UserProfileRepository;

@Service
public class UserProfileService {
    @Autowired
    private UserProfileRepository userRepo;

    public void addProfile(UserProfile UserProfile) {
        userRepo.save(UserProfile);
    }

    public List<UserProfile> listProfiles() {
        return userRepo.findAll();
    }
}
