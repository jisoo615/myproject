package com.example.myproject.config.oauth;

import com.example.myproject.config.auth.PrincipalDetails;
import com.example.myproject.config.oauth.provider.GoogleUserInfo;
import com.example.myproject.config.oauth.provider.KakaoUserInfo;
import com.example.myproject.config.oauth.provider.OAuth2UserInfo;
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
        OAuth2User oauth2User = super.loadUser(userRequest);// 로그인한 계정 정보
        String provider = userRequest.getClientRegistration().getRegistrationId();
        String providerId = oauth2User.getName();
        String username = provider+"_"+providerId;
        System.out.println(username);

        OAuth2UserInfo info;
        if(provider.equals("kakao")){
            info = new KakaoUserInfo(oauth2User.getAttributes());
        }
        else if(provider.equals("google")){
            info = new GoogleUserInfo(oauth2User.getAttributes());
        }else{
            System.out.println("지원하지 않는 로그인"); return null;}// 가입 거부?

        // 기존 유저 아니면 새로 생성, 가입
        User userEntity = userRepo.findByUsername(username);
        if(userEntity==null){
           userEntity = User.builder()
                    .provider(provider)
                    .providerId(providerId)
                    .username(username)
                    .email(info.getEmail())
                    .password(null)
                    .role("ROLE_USER")
                    .build();
            userRepo.save(userEntity);
        }

        return new PrincipalDetails(userEntity, oauth2User.getAttributes());
    }

 /* OAuth2User 의 내부
 구글
   User Attributes: [{sub=12314352234234, name=kevin oh, given_name=kevin, family_name=oh, picture=https://lh3.googleusercontent.com/a/ALm5wu1t6MnDLqiRHEZq2TVqD7UCOIDPQ=s96-c, email=user1@gmail.com, email_verified=true, locale=ko}]
  * AccessToken:엑세스토큰값
  */
    /*
    카카오
   User Attributes: [{id=123124423, connected_at=2022-10-22T14:12:58Z,properties={nickname=케빈},kakao_account={profile_nickname_needs_agreement=false, profile={nickname=케빈}, has_email=true, email_needs_agreement=true}}]
     */
}
