package online.fadai.opengptproject.controller;

import jakarta.annotation.Resource;
import online.fadai.opengptproject.dto.UserInfoRequest;
import online.fadai.opengptproject.service.UserService;
import online.fadai.opengptproject.utils.Result;
import online.fadai.opengptproject.utils.ResultUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notAuth")
public class NotAuthController {
    @Resource
    private UserService userService;

    @PostMapping("/reg")
    public Result<Void> reg(@RequestBody UserInfoRequest userInfoRequest) {
        userService.insertUser(userInfoRequest.getUsername(), userInfoRequest.getPassword(), userInfoRequest.getEmail());
        return ResultUtil.success();
    }

    @PostMapping("/login")
    public Result<String> login(@RequestBody UserInfoRequest userInfoRequest) {
        String token = userService.selectUser(userInfoRequest.getUsername(), userInfoRequest.getPassword());
        return ResultUtil.success(token);
    }
}
