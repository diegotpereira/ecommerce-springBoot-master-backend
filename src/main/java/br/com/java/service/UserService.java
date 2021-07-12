package br.com.java.service;

import java.security.NoSuchAlgorithmException;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.java.config.MessageStrings;
import br.com.java.dto.ResponseDto;
import br.com.java.dto.user.SignInDto;
import br.com.java.dto.user.SignupDto;
import br.com.java.dto.user.UserCreateDto;
import br.com.java.enums.Role;
import br.com.java.exception.AuthenticationFailException;
import br.com.java.exception.CustomException;
import br.com.java.model.AuthenticationToken;
import br.com.java.model.User;
import br.com.java.repository.UserRepository;
import br.com.java.utils.Helper;

@Service
public class UserService {
    
    @Autowired
    UserRepository repository;

    @Autowired
    AuthenticationService authenticationService;

    Logger logger = LoggerFactory.getLogger(UserService.class);

    public ResponseDto signUp(SignupDto signupDto) throws CustomException {
        
        if (Helper.notNull(repository.findByEmail(signupDto.getEmail()))) {
            throw new CustomException("Usuário já existe");
        }

        String encryptedPassword = signupDto.getPassword();

        try {
            encryptedPassword = hashPassword(signupDto.getPassword());
        } catch (NoSuchAlgorithmException e) {
            //TODO: handle exception
            e.printStackTrace();
            logger.error("hash de senha falhou {}", e.getMessage());
        }

        User user = new User(signupDto.getFirstName(), signupDto.getLastName(), signupDto.getEmail(), Role.user, encryptedPassword);

        User createdUser;

        try {
            createdUser = repository.save(user);

            final AuthenticationToken authenticationToken = new AuthenticationToken(createdUser);

            authenticationService.saveConfirmationToken(authenticationToken);

            return new ResponseDto(ResponseStatus.success.toString(), USER_CREATED);
        } catch (Exception e) {
            //TODO: handle exception

            throw new CustomException(e.getMessage());
        }
    }

    public SignInResponseDto signIn(SignInDto signInDto) throws CustomException {
        User user = repository.findByEmail(signInDto.getEmail());

        if (!Helper.notNull(user)) {
            throw new AuthenticationFailException("Usuário não presente");
        }

        try {
            if (!user.getPassword().equals(hashPassword(signInDto.getPassword()))) {
                throw new AuthenticationFailException(MessageStrings.WRONG_PASSWORD);
            }
        } catch (NoSuchAlgorithmException e) {
            //TODO: handle exception
            e.printStackTrace();
            logger.error("hash de senha falhou {}", e.getMessage());

            throw new CustomException(e.getMessage());
        }

        AuthenticationToken token = authenticationService.getToken(user);

        if (!Helper.notNull(token)) {
            throw new CustomException("token não presente");
        }

        return new SignInResponseDto("success", token.getToken());
    }

    String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();

        String myHAsh = DatatypeConverter.printHexBinary(digest).toUpperCase();

        return myHash;
    }

    public ResponseDto createUser(String token, UserCreateDto userCreatedDto) throws CustomException, AuthenticationFailException {
        User creatingUser = authenticationService.getUser(token);

        if (!canCrudUser(creatingUser.getRole())) {
            
            throw new AuthenticationFailException(MessageStrings.USER_NOT_PERMITTED);
        }

        String encry
    }
}
