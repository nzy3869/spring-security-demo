package top.happubull.happybullspringsecurity.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import top.happubull.happybullspringsecurity.model.User;

@Component
public interface UserMapper {

    @Select("select * from custom_users where username= #{username}")
    User findByUsername(@Param("username") String username);

}
