package online.fadai.opengptproject.data.mysql;

import online.fadai.opengptproject.data.pojo.MsgTopic;
import org.apache.ibatis.annotations.*;

import java.sql.Date;
import java.util.List;

@Mapper
public interface TopicDAO {
    @Select("""
            select uuid,topic from msg_topic where user = #{user} and see = 1 order by time desc
            """)
    @Results(id = "topicRes", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "uuid", property = "uuid"),
            @Result(column = "topic", property = "topic"),
            @Result(column = "user", property = "user"),
            @Result(column = "see", property = "see")})
    List<MsgTopic> selectUserTopic(String username);
    @Insert("""
    insert into msg_topic(uuid, topic, user, see, time) value (#{uuid},#{topic},#{user},1,#{time})
    """)
    int insertMsgTopic(String uuid, String topic, String user, Date time);
    @Update("""
    update msg_topic set see = 0 where uuid = #{uuid}
    """)
    int updateSee(String uuid);
}
