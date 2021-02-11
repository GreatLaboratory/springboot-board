package com.greatlaboratory.board.config.auth.dto;

import com.greatlaboratory.board.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

// Serializable 직렬화
@Getter
public class SessionUser implements Serializable {

    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
