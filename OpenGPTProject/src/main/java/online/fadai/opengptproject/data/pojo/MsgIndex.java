package online.fadai.opengptproject.data.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(indexName = "gpt_msg")
public class MsgIndex implements Serializable {
    @Serial
    private static final long serialVersionUID=1L;
    @Field(type = FieldType.Text)
    private String id;
    @Field(type = FieldType.Text)
    private String uuid;
    @Field(type = FieldType.Integer)
    private Integer role;
    @Field(type = FieldType.Text)
    private String content;
    @Field(type = FieldType.Long)
    private Long time;
}
