package com.greatlaboratory.board.config.auth;

import com.greatlaboratory.board.config.auth.dto.OAuthAttributes;
import com.greatlaboratory.board.config.auth.dto.SessionUser;
import com.greatlaboratory.board.domain.user.User;
import com.greatlaboratory.board.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

// sns 로그인 이후 가져온 사용자의 정보들을 기반으로 가입 및 정보수정, 세션 저장 등의 기능을 담당
// OAuth2UserService<OAuth2UserRequest, OAuth2User> 인터페이스를 꼭 구현해야 한다.
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // registrationId : 현재 로그인 진행 중인 서비스를 구분하는 코드
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // userNameAttributeName : OAuth 로그인 진행 시 키가 되는 필드값 (primary key)
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        // 구글에게 request 보내서 얻은 사용자 정보인 oAuth2User.getAttributes()은 Map<String, Object> 타입이다.
        // OAuthAttributes은 여기서 받은 사용자 정보를 담을 클래스이다. (Dto 느낌의 클래스다.)
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        // 구글에서 받은 정보로 user entity를 만들어서 데이터베이스에 저장 (회원가입)
        User user = saveOrUpdate(attributes);

        // 세션에 저장 또는 업데이트된 사용자를 등록
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey()
        );
    }

    // 구글로 받은 정보 중 unique한 email로 데이터베이스에 조회해서 조회한 정보가 존재한다면 업데이트를, 없다면 새롭게 만들어서 리턴
    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                                    .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                                    .orElse(attributes.toEntity());
        return userRepository.save(user);
    }
}
