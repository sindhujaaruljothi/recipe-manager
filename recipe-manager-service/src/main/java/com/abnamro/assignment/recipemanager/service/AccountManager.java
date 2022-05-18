package com.abnamro.assignment.recipemanager.service;

import com.abnamro.assignment.recipemanager.exception.BadRequestException;
import com.abnamro.assignment.recipemanager.model.UserProfile;
import com.abnamro.assignment.recipemanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.abnamro.assignment.recipemanager.constant.RecipeManagerConstants.PASSWORD_NOT_VALID;
import static com.abnamro.assignment.recipemanager.constant.RecipeManagerConstants.PASSWORD_REGEX;
import static com.abnamro.assignment.recipemanager.constant.RecipeManagerConstants.USERID_EXIST;
import static com.abnamro.assignment.recipemanager.constant.RecipeManagerConstants.USERID_NOT_VALID;
import static com.abnamro.assignment.recipemanager.constant.RecipeManagerConstants.USERID_REGEX;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountManager {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public void createAccount(String userId, String password) {
        validate(userId, USERID_REGEX, USERID_NOT_VALID);
        validate(password, PASSWORD_REGEX, PASSWORD_NOT_VALID);
        Optional<UserProfile> user = userRepository.findById(userId);
        if (user.isPresent()) {
            log.error("user is already present");
            throw new BadRequestException(USERID_EXIST);
        }
        userRepository.save(getUserManagement(userId, password));
    }

    private void validate(String textCheck, String regex, String errorMessage) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(textCheck);
        if (!matcher.matches()) {
            throw new BadRequestException(errorMessage);
        }
    }

    private UserProfile getUserManagement(String userId, String password) {
        return new UserProfile(userId, passwordEncoder.encode(password));
    }

}
