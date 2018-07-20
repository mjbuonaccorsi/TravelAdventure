package com.buono.travel.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.buono.travel.model.AuthSession;

@RestController
@RequestMapping(value = "/api/session")
public class AuthController {

	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Autowired
	Realm realm;
	
	@Autowired
	DefaultWebSecurityManager securityManager;

	@RequestMapping(method = RequestMethod.GET)
	public AuthSession getValidSession( @RequestHeader HttpHeaders headers) {
		LOG.info("Calling getValidSession");
		return null;
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public AuthSession login(@RequestBody AuthSession usr) {

		//IniRealm iniRealm = new IniRealm("classpath:shiro.ini");
		//DefaultSecurityManager securityManager = new DefaultSecurityManager(realm);
		 
		SecurityUtils.setSecurityManager(securityManager);
		Subject currentUser = SecurityUtils.getSubject();
		
		if (currentUser.getPrincipals() !=null) {
		for (Object prin : currentUser.getPrincipals()) {
			if (prin.getClass().getName().endsWith("String")) {
				if (!((String)prin).equalsIgnoreCase(usr.getUserId())) {
					currentUser.logout();
					currentUser = SecurityUtils.getSubject();
				}
			}
		}
		}
		
		// Verify Username is passed
		
		//Verify Password was passed
		AuthSession retSession = new AuthSession();
		
		if (!currentUser.isAuthenticated()) {               
			  UsernamePasswordToken token                       
			    = new UsernamePasswordToken(usr.getUserId(), usr.getPassword());
			  token.setRememberMe(true);                        
			  try {                                             
			      currentUser.login(token);  
			      if (currentUser.isAuthenticated()) {
			    	  retSession.setSession("LOGGEDIN");
			      }
			  } catch (UnknownAccountException uae) {           
				  LOG.error("Username Not Found!", uae);  
				  retSession.setSession("Username Not Found!");
			  } catch (IncorrectCredentialsException ice) {     
				  LOG.error("Invalid Credentials!", ice);
				  retSession.setSession("Invalid Credentials!");
			  } catch (LockedAccountException lae) {            
				  LOG.error("Your Account is Locked!", lae);
				  retSession.setSession("Your Account is Locked!");
			  } catch (AuthenticationException ae) {            
				  LOG.error("Unexpected Error!", ae);           
				  retSession.setSession("Unexpected Error!");
			  }                                                 
			}		
		return retSession;
	}
	
	@RequestMapping(method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public AuthSession logout() {

		//IniRealm iniRealm = new IniRealm("classpath:shiro.ini");		 
		SecurityUtils.setSecurityManager(securityManager);
		Subject currentUser = SecurityUtils.getSubject();
		
		AuthSession retSession = new AuthSession();
		
		if (currentUser.isAuthenticated()) {               
				  try {                                             
			      currentUser.logout();  
			    	  retSession.setSession("LOGGED OUT");
			      
			  } catch (UnknownAccountException uae) {           
				  LOG.error("Username Not Found!", uae);  
				  retSession.setSession("Username Not Found!");
			  } catch (IncorrectCredentialsException ice) {     
				  LOG.error("Invalid Credentials!", ice);
				  retSession.setSession("Invalid Credentials!");
			  } catch (LockedAccountException lae) {            
				  LOG.error("Your Account is Locked!", lae);
				  retSession.setSession("Your Account is Locked!");
			  } catch (AuthenticationException ae) {            
				  LOG.error("Unexpected Error!", ae);           
				  retSession.setSession("Unexpected Error!");
			  }                                                 
			}		
		return retSession;
	}

	
}
