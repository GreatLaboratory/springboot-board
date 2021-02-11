package com.greatlaboratory.board.config.auth.dto;

import com.greatlaboratory.board.domain.user.Role;
import com.greatlaboratory.board.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if ("naver".equals(registrationId)) {
            return ofNaver("id", attributes);
        }
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                                    .name((String) attributes.get("name"))
                                    .email((String) attributes.get("email"))
                                    .picture((String) attributes.get("picture"))
                                    .attributes(attributes)
                                    .nameAttributeKey(userNameAttributeName)
                                    .build();

    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return OAuthAttributes.builder()
                                    .name((String) response.get("name"))
                                    .email((String) response.get("email"))
                                    .picture((String) response.get("profile_image"))
                                    .attributes(response)
                                    .nameAttributeKey(userNameAttributeName)
                                    .build();

    }

    public User toEntity(){
        return User.builder()
                        .name(name)
                        .email(email)
                        .picture(picture)
                        .role(Role.GUEST) // oauth 로그인으로 사용자가 만들어질 때 무조건 guest role로 생성
                        .build();
    }
}
