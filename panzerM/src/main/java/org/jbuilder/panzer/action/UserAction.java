package org.jbuilder.panzer.action;

import org.jbuilder.panzer.entity.User;
import org.jbuilder.panzer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.util.List;

@Path("users")
@Provider
public class UserAction {
    @Autowired
    private UserService userService;

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public User getUser(@PathParam("id") Integer id) throws Exception {
        return userService.getUserById(id);
    }

    @GET
    @RolesAllowed("ADMIN")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public List<User> getUsers() throws Exception {
        return userService.getUsers();
    }

    @POST
    @RolesAllowed("ADMIN")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response addUser(User user) {
        long result = userService.addUser(user);
        long ret = (result > 0) ? user.getId() : 0;
        return Response.status(200).entity(user).type(MediaType.APPLICATION_JSON).build();
    }

    @DELETE
    @Path("{id}")
    @RolesAllowed("ADMIN")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response deleteUser(@PathParam("id") Integer id) {
        long ret = userService.deleteUser(id);
        return Response.status(200).entity(ret).type(MediaType.APPLICATION_JSON).build();
    }
}
