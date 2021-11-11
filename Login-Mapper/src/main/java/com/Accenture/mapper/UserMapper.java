package com.Accenture.mapper;

import com.Accenture.pojo.User;
import org.apache.ibatis.annotations.Param;


public interface UserMapper {

    public int registerInsert(@Param("userName") String userName,@Param("password") String password);

    public User loginSelect(@Param("userName") String userName,@Param("password") String password);

}
