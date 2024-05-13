package online.fadai.opengptproject.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import online.fadai.opengptproject.data.pojo.UserInfo;
import online.fadai.opengptproject.data.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private HttpServletRequest request;

    @PostMapping("/reReg")
    public int updatePassword(@RequestBody UserInfo userInfo) {
        return userService.updatePassword(userInfo.getEmail(), userInfo.getPassword());
    }

    @PostMapping("/reg")
    public int insertUser(@RequestBody UserInfo userInfo) {
        return userService.insertUser(userInfo.getUsername(), userInfo.getPassword(), userInfo.getEmail());
    }

    @PostMapping("/login")
    public int selectUser(@RequestBody UserInfo userInfo) {
        HttpSession session = request.getSession();
        session.setAttribute("user",userInfo.getUsername());
        return userService.selectUser(userInfo.getUsername(), userInfo.getPassword());
    }
}

