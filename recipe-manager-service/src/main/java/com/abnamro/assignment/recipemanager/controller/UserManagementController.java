package com.abnamro.assignment.recipemanager.controller;

import com.abnamro.assignment.api.AccountManagementApi;
import com.abnamro.assignment.model.AccountDetail;
import com.abnamro.assignment.recipemanager.exception.BadRequestException;
import com.abnamro.assignment.recipemanager.exception.UnAuthorizedException;
import com.abnamro.assignment.recipemanager.security.CustomUserDetailService;
import com.abnamro.assignment.recipemanager.security.JwtTokenUtil;
import com.abnamro.assignment.recipemanager.service.AccountManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

import static com.abnamro.assignment.recipemanager.constant.RecipeManagerConstants.INVALID_CREDENTIALS;
import static com.abnamro.assignment.recipemanager.constant.RecipeManagerConstants.USER_DISABLED;

@RestController
@RequiredArgsConstructor
public class UserManagementController implements AccountManagementApi {
    private final AccountManager accountManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailService customUserDetailService;

    @Override
    public ResponseEntity<Void> addAccount(AccountDetail accountDetail) {
        accountManager.createAccount(accountDetail.getUserId(), accountDetail.getPasscode());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<Void> authenticateUser(AccountDetail request) {
        authenticate(request.getUserId(), request.getPasscode());

        final UserDetails userDetails = customUserDetailService
                .loadUserByUsername(request.getUserId());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.AUTHORIZATION,
                        token
                ).build();
    }

    private void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new UnAuthorizedException(USER_DISABLED);
        } catch (BadCredentialsException e) {
            throw new BadRequestException(INVALID_CREDENTIALS);
        }
    }
}
