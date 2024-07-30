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
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class Oauth2UserService extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String accessToken = userRequest.getAccessToken().getTokenValue();

        OauthAttributes attributes = OauthAttributes.of(registrationId, oAuth2User.getAttributes(), accessToken);

        // 멤버 create or update(토큰)
        Long memberId = updateOrSave(attributes);

        // 멤버 id attributes에 넣기
        attributes.updateAttributes(memberId);

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(attributes.getRole())), attributes.getAttributes(), "id");
    }

    @Transactional
    public Long updateOrSave(OauthAttributes attributes) {
        Member member = memberRepository.findByOauthKeyAndProvider(attributes.getOauthKey(), attributes.getProvider());

        if(member == null) {
            member = memberRepository.save(attributes.toEntity());
        }

        member.updateOauthAccessToken(attributes.getAccessToken());

        return member.getId();
    }
}
