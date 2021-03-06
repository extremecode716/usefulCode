package web.users;

import web.errors.NotFoundException;
import web.errors.UnauthorizedException;
import web.security.Jwt;
import web.security.JwtAuthentication;
import web.security.JwtAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static web.ApiUtils.ApiResult;
import static web.ApiUtils.success;

@RestController
@RequestMapping("api/web.users")
public class UserRestController {

    private final Jwt jwt;

    private final AuthenticationManager authenticationManager;

    private final com.github.prgrms.users.UserService userService;

    public UserRestController(Jwt jwt, AuthenticationManager authenticationManager, com.github.prgrms.users.UserService userService) {
        this.jwt = jwt;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping(path = "login")
    public ApiResult<com.github.prgrms.users.LoginResult> login(
            @Valid @RequestBody com.github.prgrms.users.LoginRequest request
    ) throws UnauthorizedException {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new JwtAuthenticationToken(request.getPrincipal(), request.getCredentials())
            );
            final com.github.prgrms.users.User user = (com.github.prgrms.users.User) authentication.getDetails();
            final String token = user.newJwt(
                    jwt,
                    authentication.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .toArray(String[]::new)
            );
            return success(new com.github.prgrms.users.LoginResult(token, user));
        } catch (AuthenticationException e) {
            throw new UnauthorizedException(e.getMessage(), e);
        }
    }

    @GetMapping(path = "me")
    public ApiResult<UserDto> me(
            // JwtAuthenticationTokenFilter ?????? JWT ?????? ?????? ???????????? ????????????.
            // ????????? ????????? ???????????? ??????????????? @AuthenticationPrincipal ?????????????????? ???????????? ????????? ????????? ??????(JwtAuthentication)??? ????????? ??? ??????.
            @AuthenticationPrincipal JwtAuthentication authentication
    ) {
        return success(
                userService.findById(authentication.id)
                        .map(UserDto::new)
                        .orElseThrow(() -> new NotFoundException("Could nof found user for " + authentication.id))
        );
    }

}