package me.inrush.italker.service;

import me.inrush.italker.bean.db.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @author inrush
 */
// 127.0.0.1/api/account
@Path("/account")
public class AccountService {

    @GET
    @Path("/login")
    public String get() {
        return "you get then login";
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User post(){
        User user = new User();
        user.setName("帅哥");
        user.setSex(1);
        return user;
    }
}
