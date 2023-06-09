package com.example.toxicapplication.appUser.userDetails;

import com.example.toxicapplication.registration.token.ConfirmationTokenEntity;
import com.example.toxicapplication.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG =
            "user with email %s not found";
    private final static Integer emailSendMinut = 15;
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Transactional(readOnly = true)
    public long getIdUser(String userName) {
        AppUser appUser = appUserRepository.findByUserName(userName).get();
        return appUser.getId();
    }
    @Override
    public UserDetails loadUserByUsername(String userName)
            throws UsernameNotFoundException {
        return appUserRepository.findByUserName(userName)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                String.format(USER_NOT_FOUND_MSG, userName)));
    }

    public String signUpUser(AppUser appUser) {
        boolean userExists = appUserRepository
                .findByUserName(appUser.getUsername())
                .isPresent();

        if (userExists) {
            // TODO check of attributes are the same and
            // TODO if email not confirmed send confirmation email.

            throw new IllegalStateException("email already taken");
        }
//appUserRepository.findByEmail("enail").ifPresentOrElse(AppUser::getEmail);
        String encodedPassword = bCryptPasswordEncoder
                .encode(appUser.getPassword());

        appUser.setPassword(encodedPassword);

        appUserRepository.save(appUser);

        String token = UUID.randomUUID().toString();

        ConfirmationTokenEntity confirmationTokenEntity = new ConfirmationTokenEntity(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(emailSendMinut),
                appUser
        );

        confirmationTokenService.saveConfirmationToken(
                confirmationTokenEntity);

//        TODO: SEND EMAIL

        return token;
    }
    public void enableAppUser(String email) {
        appUserRepository.enableAppUser(email);
    }

}
