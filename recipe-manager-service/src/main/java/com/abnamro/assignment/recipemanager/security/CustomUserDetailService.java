package com.abnamro.assignment.recipemanager.security;

import com.abnamro.assignment.recipemanager.exception.NotFoundException;
import com.abnamro.assignment.recipemanager.model.UserProfile;
import com.abnamro.assignment.recipemanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.abnamro.assignment.recipemanager.constant.RecipeManagerConstants.USER_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetails loadUserByUsername(String email) throws NotFoundException {
        Optional<UserProfile> user = userRepository.findById(email);

        return new CustomUserDetail(user.orElseThrow(() -> new NotFoundException(USER_NOT_FOUND))
        );
    }

}