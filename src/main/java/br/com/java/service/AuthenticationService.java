package br.com.java.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.java.config.MessageStrings;
import br.com.java.exception.AuthenticationFailException;
import br.com.java.model.AuthenticationToken;
import br.com.java.model.User;
import br.com.java.repository.TokenRepository;
import br.com.java.utils.Helper;

@Service
public class AuthenticationService {
    

    @Autowired
    TokenRepository repository;

    public void saveConfirmationToken(AuthenticationToken authenticationToken) {
        repository.save(authenticationToken);
    }

    public AuthenticationToken getToken(User user) {
        return repository.findTokenByUser(user);
    }

    public User getUser(String token){
        AuthenticationToken authenticationToken = repository.findTokenByToken(token);

        if (Helper.notNull(authenticationToken)) {
            if (Helper.notNull(authenticationToken.getUser())) {
                
                return authenticationToken.getUser();
            }
        }

        return null;
    }

    public void authenticate(String token) throws AuthenticationFailException {

        if (!Helper.notNull(token)) {
            throw new AuthenticationFailException(MessageStrings.AUTH_TOEKN_NOT_PRESENT) ;
        }

        if (!Helper.notNull(getUser(token))) {
            throw new AuthenticationFailException(MessageStrings.AUTH_TOEKN_NOT_VALID);
        }
    }
}
