package online.fadai.opengptproject.data.service.impl;

import jakarta.annotation.Resource;
import online.fadai.opengptproject.data.mysql.UserDao;
import online.fadai.opengptproject.data.pojo.UserInfo;
import online.fadai.opengptproject.data.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;
    @Override
    public int updatePassword(String email, String password) {
        return userDao.updatePassword(email,password);
    }

    @Override
    public int insertUser(String username, String password, String email) {
        return userDao.insertUser(username,password,email);
    }

    @Override
    public int selectUser(String username,String password) {
        UserInfo userInfo = userDao.selectUser(username);
        if (userInfo != null && userInfo.getPassword().equals(password)){
            return 1;
        }
        return 0;
    }
}
