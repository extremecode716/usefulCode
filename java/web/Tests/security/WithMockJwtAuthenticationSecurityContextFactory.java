package web.Tests.security;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

public class WithMockJwtAuthenticationSecurityContextFactory implements WithSecurityContextFactory<com.github.prgrms.security.WithMockJwtAuthentication> {

    @Override
    public SecurityContext createSecurityContext(com.github.prgrms.security.WithMockJwtAuthentication annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        JwtAuthenticationToken authentication =
                new JwtAuthenticationToken(
                        new JwtAuthentication(annotation.id(), annotation.name()),
                        null,
                        createAuthorityList(annotation.role())
                );
        context.setAuthentication(authentication);
        return context;
    }

}