package success.planfit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import success.planfit.controller.utils.ControllerUtil;
import success.planfit.domain.user.UserDto;
import success.planfit.service.UserService;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final ControllerUtil controllerUtil;
    private final UserService userService;



    // 회원 정보 조회 API (기본키로 조회)
    @GetMapping
    public ResponseEntity<UserDto> getUserInfo(Principal principal) {
        Long userId = controllerUtil.findUserIdByPrincipal(principal);
        UserDto userInfo = userService.getUserInfo(userId);
        return ResponseEntity.ok(userInfo);
    }

    // 회원 정보 수정 API
    @PatchMapping("/update")
    public ResponseEntity<Void> updateUserInfo(Principal principal, @RequestBody UserDto userDto) {
        Long userId = controllerUtil.findUserIdByPrincipal(principal);
        userService.updateUserInfo(userId,userDto);
        return ResponseEntity.ok().build();
    }
}
