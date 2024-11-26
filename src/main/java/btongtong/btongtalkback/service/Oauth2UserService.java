package btongtong.btongtalkback.service;

import btongtong.btongtalkback.constant.ErrorCode;
import btongtong.btongtalkback.constant.Token;
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

import static btongtong.btongtalkback.constant.ErrorCode.*;
import static btongtong.btongtalkback.constant.Token.*;

@Service
@RequiredArgsConstructor
public class Oauth2UserService extends DefaultOAuth2UserService {
    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        OauthAttributes attributes = OauthAttributes.of(getRegistrationId(userRequest), oAuth2User.getAttributes());
        updateOrSaveMember(attributes, getOauthToken(userRequest));

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(attributes.getRole().name())),
                attributes.getAttributes(),
                getUserNameAttributeName(userRequest));
    }

    private static String getRegistrationId(OAuth2UserRequest userRequest) {
        return userRequest.getClientRegistration().getRegistrationId();
    }

    private static String getUserNameAttributeName(OAuth2UserRequest userRequest) {
        return userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
    }

    private static String getOauthToken(OAuth2UserRequest userRequest) {
        return userRequest.getAccessToken().getTokenValue();
    }

    @Transactional
    public void updateOrSaveMember(OauthAttributes attributes, String oauthToken) {
        Member member = findOrCreateMember(attributes);
        validateDuplication(attributes, member);

        String refreshToken = getRefreshToken(member);
        updateMember(member, attributes, oauthToken, refreshToken);
        memberRepository.save(member);

        attributes.updateAttributes(REFRESH.getType(), refreshToken);
    }

    private Member findOrCreateMember(OauthAttributes attributes) {
        return memberRepository.findByEmail(attributes.getEmail())
                .orElseGet(() -> memberRepository.save(attributes.toEntity()));
    }

    private static void validateDuplication(OauthAttributes attributes, Member member) {
        boolean isSameProvider = Objects.equals(member.getProvider(), attributes.getProvider());
        boolean isSameOauthKey = Objects.equals(member.getOauthKey(), attributes.getOauthKey());
        if(!isSameProvider || !isSameOauthKey) {
            throw new OAuth2AuthenticationException(DUPLICATE_CONTENT.getCode());
        }
    }

    private String getRefreshToken(Member member) {
        return jwtUtil.createRefreshToken(String.valueOf(member.getId()), String.valueOf(member.getRole()));
    }

    private static void updateMember(Member member, OauthAttributes attributes, String oauthToken, String refreshToken) {
        member.updateOauthAccessToken(oauthToken);
        member.updateRefreshToken(refreshToken);
        member.updateProfile(attributes.toEntity());
    }
}
