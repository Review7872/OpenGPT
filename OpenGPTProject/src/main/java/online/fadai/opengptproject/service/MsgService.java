package online.fadai.opengptproject.service;

import online.fadai.opengptproject.enums.RoleType;
import online.fadai.opengptproject.repository.es.pojo.MsgIndex;

import java.util.List;

public interface MsgService {
    void save(String uuid, RoleType roleType, String content);

    List<MsgIndex> findByUuid(String uuid);
}
