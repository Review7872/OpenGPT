package online.fadai.opengptproject.data.mysql;

import online.fadai.opengptproject.data.pojo.UserInfo;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserDao {
    @Select("""
            select id,username,password from user_info where username = #{username}
            """)
    @Results(id = "userRes",
            value = {@Result(id = true, column = "id", property = "id"),
                    @Result(column = "username", property = "username"),
                    @Result(column = "password", property = "password"),
                    @Result(column = "email", property = "email")})
    UserInfo selectUser(String username);
    @Insert("""
            insert into user_info(username, password, email) value (#{username},#{password},#{email})
            """)
    int insertUser(String username, String password, String email);
    @Update("""
            update user_info set password = #{password} where email = #{email}
            """)
    int updatePassword(String email,String password);
}
