package com.ciusyan.crm.common.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

@Slf4j
public class TokenMatcher implements CredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
        // 这里不需要验证密码，放行就行【经过了token的验证：肯定是登录成功的】
        log.debug("TokenMatcher - doCredentialsMatch - {} {}", authenticationToken, authenticationInfo);
        return true;
    }
}
