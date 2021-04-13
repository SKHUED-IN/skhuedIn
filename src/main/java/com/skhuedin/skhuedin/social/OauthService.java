package com.skhuedin.skhuedin.social;

import com.skhuedin.skhuedin.domain.Provider;
import com.skhuedin.skhuedin.social.google.GoogleOauth;
import com.skhuedin.skhuedin.social.kakao.KakaoOauth;
import com.skhuedin.skhuedin.social.naver.NaverOauth;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class OauthService {
    private final GoogleOauth googleOauth;
    private final KakaoOauth kakaoOauth;
    private final NaverOauth naverOauth;
    private final HttpServletResponse response;

    public void request(Provider socialLoginType) {
        String redirectURL;
        switch (socialLoginType) {
            case GOOGLE: {
                redirectURL = googleOauth.getOauthRedirectURL();
            }
            break;
            case KAKAO: {
                redirectURL = kakaoOauth.getOauthRedirectURL();
            }
            break;
            case NAVER: {
                redirectURL = naverOauth.getOauthRedirectURL();
            }
            break;
            default: {
                throw new IllegalArgumentException("알 수 없는 소셜 로그인 형식입니다.");
            }
        }
        try {
            response.sendRedirect(redirectURL);
        } catch (IOException e) {
            log.info("보내시려는 redirectURL 값을 확인하세요.");
            e.printStackTrace();
        }
    }
}