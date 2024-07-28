package btongtong.btongtalkback.service;

import btongtong.btongtalkback.domain.Member;
import btongtong.btongtalkback.dto.*;
import btongtong.btongtalkback.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class Oauth2UserService extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        String accessToken = userRequest.getAccessToken().getTokenValue();

        OauthAttributes attributes = OauthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes(), accessToken);

        // 멤버 create or update(토큰)
        Member member = updateOrSave(attributes);

        // 멤버 id attributes에 넣기
        attributes.updateAttributes(member.getId());

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(member.getRole())), attributes.getAttributes(), "id");
    }

    private Member updateOrSave(OauthAttributes attributes) {
        Member member = memberRepository.findByEmail(attributes.getEmail())
                .orElseGet(attributes::toEntity)
                .updateOauthAccessToken(attributes.getAccessToken());
        return memberRepository.save(member);
    }
}
