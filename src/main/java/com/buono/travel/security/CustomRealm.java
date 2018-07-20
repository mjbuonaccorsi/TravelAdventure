package com.buono.travel.security;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.JdbcUtils;

public class CustomRealm  extends JdbcRealm {

	private Map<String, String> credentials = new HashMap<>();
	private Map<String, Set<String>> roles = new HashMap<>();
	private Map<String, Set<String>> perm = new HashMap<>();
	 
	{
	    credentials.put("user", "password");
	    credentials.put("user2", "password2");
	    credentials.put("user3", "password3");
	                                           
	    roles.put("user", new HashSet<>(Arrays.asList("admin")));
	    roles.put("user2", new HashSet<>(Arrays.asList("editor")));
	    roles.put("user3", new HashSet<>(Arrays.asList("author")));
	                                                              
	    perm.put("admin", new HashSet<>(Arrays.asList("*")));
	    perm.put("editor", new HashSet<>(Arrays.asList("articles:*")));
	    perm.put("author", 
	      new HashSet<>(Arrays.asList("articles:compose", 
	      "articles:save")));
	}

	protected AuthenticationInfo 
	  doGetAuthenticationInfo(AuthenticationToken token)
	  throws AuthenticationException {
	                                                                  
	    UsernamePasswordToken uToken = (UsernamePasswordToken) token;
	                                                                 
	    if(uToken.getUsername() == null
	      || uToken.getUsername().isEmpty()
	      || !credentials.containsKey(uToken.getUsername())) {
	          throw new UnknownAccountException("username not found!");
	    }
	                                         
	    return new SimpleAuthenticationInfo(
	      uToken.getUsername(), 
	      credentials.get(uToken.getUsername()), 
	      getName()); 
	}
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        //null usernames are invalid
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }

        String username = (String) getAvailablePrincipal(principals);

        Set<String> roleNames = null;
        Set<String> permissions = null;
    
   
            // Retrieve roles and permissions from database
            roleNames = roles.get(username);

 
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
        info.setStringPermissions(permissions);
        return info;

    }

}
