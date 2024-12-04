package online.fadai.opengptproject.repository.es.dao;


import online.fadai.opengptproject.repository.es.pojo.MsgIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MsgIndexRepository extends ElasticsearchRepository<MsgIndex, String> {
    List<MsgIndex> findByUuid(String uuid);
}
