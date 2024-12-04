package online.fadai.opengptproject.repository.mysql.mapper;


import online.fadai.opengptproject.repository.mysql.pojo.MsgTopic;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface TopicDAO {
    @Select("""
            select uuid,topic,type,time from msg_topic where user = #{user} and see = 1 order by time desc
            """)
    @Results(id = "topicRes", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "uuid", property = "uuid"),
            @Result(column = "topic", property = "topic"),
            @Result(column = "type", property = "type"),
            @Result(column = "user", property = "user"),
            @Result(column = "see", property = "see"),
            @Result(column = "time", property = "time")})
    List<MsgTopic> selectUserTopic(String username);

    @Insert("""
            insert into msg_topic(uuid, topic, type , user, see, time) value (#{uuid},#{topic},#{type},#{user},1,#{time})
            """)
    int insertMsgTopic(String uuid, String topic, Integer type, String user, LocalDateTime time);

    @Update("""
            update msg_topic set see = 0 where uuid = #{uuid} and user = #{username}
            """)
    int updateSee(String username, String uuid);
}
