package web.security;

import web.errors.NotFoundException;
import web.users.Email;
import web.users.Role;
import web.users.User;
import web.users.UserService;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.apache.commons.lang3.ClassUtils.isAssignable;
import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;

    public JwtAuthenticationProvider(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        com.github.prgrms.security.JwtAuthenticationToken authenticationToken = (com.github.prgrms.security.JwtAuthenticationToken) authentication;
        return processUserAuthentication(
                Email.of(String.valueOf(authenticationToken.getPrincipal())),
                authenticationToken.getCredentials()
        );
    }

    private Authentication processUserAuthentication(Email email, String password) {
        try {
            User user = userService.login(email, password);
            com.github.prgrms.security.JwtAuthenticationToken authenticated =
                    new com.github.prgrms.security.JwtAuthenticationToken(
                            new com.github.prgrms.security.JwtAuthentication(user.getSeq(), user.getName()),
                            null,
                            createAuthorityList(Role.USER.value())
                    );
            authenticated.setDetails(user);
            return authenticated;
        } catch (NotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException(e.getMessage());
        } catch (DataAccessException e) {
            throw new AuthenticationServiceException(e.getMessage(), e);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return isAssignable(com.github.prgrms.security.JwtAuthenticationToken.class, authentication);
    }

}