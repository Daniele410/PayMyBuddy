package com.danozzo.paymybuddy.web.dto;

import com.danozzo.paymybuddy.model.User;

import java.util.List;

public class UserFriendDto {

    private List<User> friends;

    public void addFriend(User user) {
       this.friends.add(user);
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }
}
