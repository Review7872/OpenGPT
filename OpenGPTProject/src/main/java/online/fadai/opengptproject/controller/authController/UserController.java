package online.fadai.opengptproject.controller.authController;

import jakarta.annotation.Resource;
import online.fadai.opengptproject.dto.UserInfoRequest;
import online.fadai.opengptproject.service.UserService;
import online.fadai.opengptproject.utils.Result;
import online.fadai.opengptproject.utils.ResultUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/reg")
    public Result<Void> updatePassword(ServerWebExchange exchange, @RequestBody UserInfoRequest userInfoRequest) {
        UserInfoRequest userinfo = (UserInfoRequest) exchange.getAttributes().get("userData");
        userService.updatePassword(userinfo.getEmail(), userInfoRequest.getPassword());
        return ResultUtil.success();
    }
}
