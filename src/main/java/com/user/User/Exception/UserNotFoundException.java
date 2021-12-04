package com.user.User.Exception;

import com.user.User.Model.User;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(int id){
        super("user not found with user id: " + id);
    }
}
