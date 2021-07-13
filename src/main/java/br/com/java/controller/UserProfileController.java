package br.com.java.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.java.common.ApiResponse;
import br.com.java.model.UserProfile;
import br.com.java.service.UserProfileService;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserProfileController {
    @Autowired
    private UserProfileService userProfileService;
    
    @GetMapping("/")
    public ResponseEntity<List<UserProfile>> getUsers() {
        List<UserProfile> dtos = userProfileService.listProfiles();

        return new ResponseEntity<List<UserProfile>> (dtos,HttpStatus.OK);
    }

    @PostMapping("/add")
	public ResponseEntity<ApiResponse> addSurvey(@RequestBody @Valid UserProfile profile) {
		userProfileService.addProfile(profile);
		return new ResponseEntity<>(new ApiResponse(true, "O Perfil foi criado."), HttpStatus.CREATED);
	}
}
