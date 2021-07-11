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
            // JwtAuthenticationTokenFilter 에서 JWT 값을 통해 사용자를 인증한다.
            // 사용자 인증이 정상으로 완료됐다면 @AuthenticationPrincipal 어노테이션을 사용하여 인증된 사용자 정보(JwtAuthentication)에 접근할 수 있다.
            @AuthenticationPrincipal JwtAuthentication authentication
    ) {
        return success(
                userService.findById(authentication.id)
                        .map(UserDto::new)
                        .orElseThrow(() -> new NotFoundException("Could nof found user for " + authentication.id))
        );
    }

}