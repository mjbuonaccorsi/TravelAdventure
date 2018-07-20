package com.buono.travel.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.buono.travel.model.AuthSession;

@RestController
@RequestMapping(value = "/api/secure")
public class AdventureController {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

    @RequiresAuthentication
    @RequiresRoles("admin")
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public AuthSession getValidSession( @RequestHeader HttpHeaders headers) {
		LOG.info("Calling getValidSession");
		AuthSession retSession=new AuthSession();
		retSession.setSession("LOGGEDIN");
		return retSession;
	}
	
	
}
