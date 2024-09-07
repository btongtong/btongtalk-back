package btongtong.btongtalkback.service;

import btongtong.btongtalkback.constant.ErrorCode;
import btongtong.btongtalkback.domain.Member;
import btongtong.btongtalkback.dto.auth.OauthAttributes;
import btongtong.btongtalkback.handler.exception.CustomException;
import btongtong.btongtalkback.util.JwtUtil;
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
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class Oauth2UserService extends DefaultOAuth2UserService {
    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        String oauthToken = userRequest.getAccessToken().getTokenValue();

        OauthAttributes attributes = OauthAttributes.of(registrationId, oAuth2User.getAttributes());

        // 멤버 create or update(토큰)
        updateOrSave(attributes, oauthToken);

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(attributes.getRole().name())), attributes.getAttributes(), userNameAttributeName);
    }

    @Transactional
    public void updateOrSave(OauthAttributes attributes, String accessToken) {
        Member member = memberRepository.findByEmail(attributes.getEmail()).orElseGet(() -> memberRepository.save(attributes.toEntity()));
        if(!Objects.equals(member.getProvider(), attributes.getProvider()) || !Objects.equals(member.getOauthKey(), attributes.getOauthKey())) {
            throw new OAuth2AuthenticationException(ErrorCode.DUPLICATE_CONTENT.getMessage());
        }
        String refreshToken = jwtUtil.createRefreshToken(String.valueOf(member.getId()), String.valueOf(member.getRole()));

        member.updateOauthAccessToken(accessToken);
        member.updateRefreshToken(refreshToken);
        member.updateProfile(attributes.toEntity());

        memberRepository.save(member);

        attributes.updateAttributes("refresh", refreshToken);
    }
}
