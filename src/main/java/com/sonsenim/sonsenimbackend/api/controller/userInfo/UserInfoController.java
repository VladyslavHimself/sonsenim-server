package com.sonsenim.sonsenimbackend.api.controller.userInfo;

import com.sonsenim.sonsenimbackend.model.LocalUser;
import com.sonsenim.sonsenimbackend.model.dto.LocalUserDTO;
import com.sonsenim.sonsenimbackend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-info")
public class UserInfoController {

    private final UserService userService;

    public UserInfoController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<LocalUserDTO> getLoggedInUserProfile(@AuthenticationPrincipal LocalUser user) {
        LocalUserDTO dto = userService.getUserWithDTO(user.getId());
        return ResponseEntity.ok(dto);
    }
}
