package com.abnamro.assignment.recipemanager.service;

import com.abnamro.assignment.recipemanager.exception.BadRequestException;
import com.abnamro.assignment.recipemanager.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.abnamro.assignment.recipemanager.TestDataLoader.USER_ID;
import static com.abnamro.assignment.recipemanager.TestDataLoader.VALID_PASSWORD;
import static com.abnamro.assignment.recipemanager.TestDataLoader.VALID_USER_ID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AccountManagerTest {

    @InjectMocks
    private AccountManager accountManager;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void test_whenUserIdIsNotValid_thenBadRequestExceptionIsThrown() {
        Assertions.assertThrows(BadRequestException.class, () -> accountManager.createAccount(USER_ID, USER_ID));
    }

    @Test
    void test_whenPasswordIsNotValid_thenBadRequestExceptionIsThrown() {
        Assertions.assertThrows(BadRequestException.class, () -> accountManager.createAccount(VALID_USER_ID, USER_ID));
    }

    @Test
    void test_whenUserIdAndPasswordIsValid_thenAccountISCreatedSuccessfully() {
        accountManager.createAccount(VALID_USER_ID, VALID_PASSWORD);
        verify(userRepository, times(1)).save(any());

    }

}