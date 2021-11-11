package com.Accenture.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {

    private Integer userId;

    private String userName;

    private String password;

    private LocalDateTime createDateTime;

}
