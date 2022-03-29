package com.example.myproject.config.oauth;

import com.example.myproject.config.auth.PrincipalDetails;
import com.example.myproject.entity.User;
import com.example.myproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

/**
 * google, kakao, naver 로 로그인시 google,... 에서 받아온 request 가공해 Authentication 객체에 넣고 반환
 * -> 사용자 프로필 받을 수 있음
 */

@Service
@RequiredArgsConstructor
public class PrincipalOAuth2UserService extends DefaultOAuth2UserService {
    final private UserRepository userRepo;

    /**
     * 자동 로그인 + 자동 회원가입 기능
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);

        String provider = oauth2User.getAttribute("registrationId");
        String providerId = oauth2User.getAttribute("sub");
        String username = provider+"_"+providerId;
        String email = oauth2User.getAttribute("email");
        String password = null;
        String role = "ROLE_USER";

        // 기존 유저 아니면 새로 생성, 가입
        User userEntity = userRepo.findByUsername(username);
        if(userEntity==null){
           userEntity = User.builder()
                    .provider(provider)
                    .providerId(providerId)
                    .username(username)
                    .email(email)
                    .password(password)
                    .role(role)
                    .build();
            userRepo.save(userEntity);
        }

        return new PrincipalDetails(userEntity, oauth2User.getAttributes());
    }

 /* OAuth2User 의 내부
   ClientRegistration{ registrationId='google', clientId='...', clientSecret='...',
            clientAuthenticationMethod=..., authorizationGrantType=org.springframework.security.oauth2.core.AuthorizationGrantType@5da5e9f3,
            redirectUri='{baseUrl}/{action}/oauth2/code/{registrationId}', scopes=[email, profile],
        providerDetails=..., clientName='Google'
    }

  * AccessToken:엑세스토큰값
Attributes:{ sub=3047387102293948, name=..., given_name=.., family_name=..,
		picture=https://lh3.googleuser..., email=..@google.com., email_verified=true, locale=ko
		}
  */
}
