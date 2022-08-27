package com.ciusyan.crm.common.shiro;


import com.ciusyan.crm.common.cache.Caches;
import com.ciusyan.crm.pojo.dto.UserPermissionDto;
import com.ciusyan.crm.pojo.po.Resource;
import com.ciusyan.crm.pojo.po.Role;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
public class TokenRealm extends AuthorizingRealm {

    public TokenRealm(TokenMatcher matcher) {
        super(matcher);
    }

    /**
     * 用于认证器所需要的Token
     * @param token : 认证器的那个token
     * @return ：是否符合要求
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        // 我这里是只认我自定义的Token
        log.debug("supports--------{}", token);
        return token instanceof Token;
    }

    /**
     * 授权器
     * @param principals ：认证器认证成功传过来的 信息【Shiro的用户名和密码】
     * @return : 用户所拥有的权限和角色信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.debug("AuthorizationInfo----{}", principals);
        // 认证器传过来的信息
        String token = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        // 获取缓存中的对象
        UserPermissionDto permissionDto = Caches.getToken(token);

        // 添加【角色】
        List<Role> roles = permissionDto.getRoles();
        if (CollectionUtils.isEmpty(roles)) return info;
        for (Role role : roles) {
            info.addRole(role.getName());
        }

        // 添加【资源】权限
        List<Resource> resources = permissionDto.getResources();
        if (CollectionUtils.isEmpty(resources)) return info;
        for (Resource resource : resources) {
            info.addStringPermission(resource.getPermission());
        }

        return info;
    }

    /**
     * 认证器 【SecurityUtils.getSubject().login(new Token(token)) 会触发此认证器】
     * @param authenticationToken：token
     * @return ：认证成功传出去的 信息【Shiro的用户名和密码】
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String tk = ((Token) authenticationToken).getToken();
        log.debug("AuthenticationInfo-----{}", tk);
        // 这里通过会去验证Shiro的密码【TokenMatcher】
        return new SimpleAuthenticationInfo(tk, tk, getName());
    }
}
