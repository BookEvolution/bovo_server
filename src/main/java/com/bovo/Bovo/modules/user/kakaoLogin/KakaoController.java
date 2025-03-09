package com.bovo.Bovo.modules.user.kakaoLogin;

import com.bovo.Bovo.modules.user.kakaoLogin.kakao_dto.request.AuthorizationCodeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class KakaoController {
    private final KakaoService kakaoService;

    @PostMapping("/bovocallback")
    public String getAuthorizationCode(@RequestBody AuthorizationCodeDto authorizationCodeDto) {

    }
}
