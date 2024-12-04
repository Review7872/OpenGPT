package online.fadai.opengptproject.repository.es.mapper;


import online.fadai.opengptproject.repository.es.pojo.MsgIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MsgIndexRepository extends ElasticsearchRepository<MsgIndex, String> {
    List<MsgIndex> findByUuid(String uuid);

    // 使用@Query注解来按time字段降序排序，限制5个结果，可以使用这个方法查询es可以防止搜索结果过多造成请求 ollma 消耗过度token
    //@Query("{\"bool\": {\"must\": [{\"match\": {\"uuid\": \"?0\"}}]}}")
    //List<MsgIndex> findTop5ByUuidOrderByTimeDesc(String uuid, Pageable pageable);
    // 创建分页请求，按time字段降序排序，每页最多5个结果
    //PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Order.desc("time")));
    // 查询UUID匹配并按time降序排列的前5个消息
    //return msgIndexRepository.findTop5ByUuidOrderByTimeDesc(uuid, pageRequest);
}
