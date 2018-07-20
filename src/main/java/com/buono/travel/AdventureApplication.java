package com.buono.travel;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

import com.buono.travel.security.CustomRealm;

@SpringBootApplication
public class AdventureApplication {

	private static Logger log = LoggerFactory.getLogger(AdventureApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(AdventureApplication.class, args);
	}

	@Bean
	public Realm realm() {
	    return new CustomRealm();
	}

	@Bean(name = "securityManager")
	public DefaultWebSecurityManager securityManager() {
	    DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
	    securityManager.setRealm(realm());
	    return securityManager;
	}
	
	
	@Bean(name = "shiroFilterFactoryBean")
	@DependsOn("securityManager")
	@ConditionalOnMissingBean
	public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultSecurityManager securityManager, Realm realm) {
		securityManager.setRealm(realm);

		ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
		shiroFilter.setSecurityManager(securityManager);
		shiroFilter.setLoginUrl("/index.html");
		shiroFilter.setUnauthorizedUrl("/api/session/");
		//shiroFilter.setSuccessUrl(properties.getSuccessUrl());
		//shiroFilter.setUnauthorizedUrl(properties.getUnauthorizedUrl());
		Map<String,String> filter = new HashMap<>();
		filter.put("/api/secure", "authc");
		filter.put("/api/**", "anon");
		shiroFilter.setFilterChainDefinitionMap(filter);
		return shiroFilter;
	}

//	@Bean
//	public ShiroFilterChainDefinition shiroFilterChainDefinition() {
//	    DefaultShiroFilterChainDefinition filter
//	      = new DefaultShiroFilterChainDefinition();
//	 
//	    filter.addPathDefinition("/secure", "authc");
//	    filter.addPathDefinition("/**", "anon");
//	    
//	    return filter;
//	}	
}
