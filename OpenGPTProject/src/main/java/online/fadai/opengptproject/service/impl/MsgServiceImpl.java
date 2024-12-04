package online.fadai.opengptproject.service.impl;

import jakarta.annotation.Resource;
import online.fadai.opengptproject.enums.RoleType;
import online.fadai.opengptproject.repository.es.dao.MsgIndexRepository;
import online.fadai.opengptproject.repository.es.pojo.MsgIndex;
import online.fadai.opengptproject.service.MsgService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MsgServiceImpl implements MsgService {
    @Resource
    private MsgIndexRepository msgIndexRepository;

    @Override
    public void save(String uuid, RoleType roleType, String content) {
        msgIndexRepository.save(new MsgIndex(UUID.randomUUID().toString(), uuid, roleType.getRoleType(),
                content, System.currentTimeMillis()));
    }

    @Override
    public List<MsgIndex> findByUuid(String uuid) {
        List<MsgIndex> msgs = msgIndexRepository.findByUuid(uuid);
        return msgs.stream().sorted((o1, o2) -> (int) (o1.getTime() - o2.getTime()))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
