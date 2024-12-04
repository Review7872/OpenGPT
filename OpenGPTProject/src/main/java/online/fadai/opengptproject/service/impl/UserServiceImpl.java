package online.fadai.opengptproject.service.impl;

import jakarta.annotation.Resource;
import online.fadai.opengptproject.exception.NotUniqueException;
import online.fadai.opengptproject.exception.PasswordException;
import online.fadai.opengptproject.exception.UsernameException;
import online.fadai.opengptproject.repository.mysql.mapper.UserDao;
import online.fadai.opengptproject.repository.mysql.pojo.UserInfo;
import online.fadai.opengptproject.service.UserService;
import online.fadai.opengptproject.utils.JWTUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    @Override
    public void updatePassword(String email, String password) {
        int i = userDao.updatePassword(email, password);
        if (i != 1) {
            throw new RuntimeException("更新密码失败");
        }
    }

    @Override
    public void insertUser(String username, String password, String email) {
        int i = userDao.insertUser(username, password, email);
        if (i != 1) {
            throw new NotUniqueException();
        }
    }

    @Override
    public String selectUser(String username, String password) {
        UserInfo userInfo = userDao.selectUser(username);
        if (userInfo == null) {
            throw new UsernameException();
        }
        if (!userInfo.getPassword().equals(password)) {
            throw new PasswordException();
        }
        return JWTUtil.generateJwtToken(username, userInfo.getEmail());
    }
}
