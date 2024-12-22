package com.github.clovisgargione.controller;

import com.github.clovisgargione.dto.UserDto;
import com.github.clovisgargione.model.User;
import com.github.clovisgargione.repository.UserRepository;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Path("/api/user")
public class UserController {

	@Inject
	private UserRepository userRepository;
	
	@POST
	@Path("signup")
    @PermitAll
    @Transactional
    public Response publicResource(UserDto userDto) {
		User user = User.add(userDto.getUsername(), userDto.getPassword(), userDto.getRole());
		userRepository.persist(user);
        return Response.ok(user).build();
   }
	
	@GET
    @RolesAllowed("user")
    @Path("/me")
    public Response me(@Context SecurityContext securityContext) {
        return Response.ok(securityContext.getUserPrincipal()).build();
    }
	
	@GET
    @RolesAllowed("admin")
    @Produces(MediaType.TEXT_PLAIN)
    public String adminResource() {
         return "admin";
    }
}
