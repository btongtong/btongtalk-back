package btongtong.btongtalkback.service;

import btongtong.btongtalkback.domain.Member;
import btongtong.btongtalkback.dto.auth.OauthAttributes;
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

import static btongtong.btongtalkback.constant.Token.*;

@Service
@RequiredArgsConstructor
public class Oauth2UserService extends DefaultOAuth2UserService {
    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        OauthAttributes attributes = OauthAttributes.of(getRegistrationId(userRequest), oAuth2User.getAttributes());
        updateOrSaveMember(attributes, getOauthAccessToken(userRequest));

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

    private static String getOauthAccessToken(OAuth2UserRequest userRequest) {
        return userRequest.getAccessToken().getTokenValue();
    }

    @Transactional
    public void updateOrSaveMember(OauthAttributes attributes, String oauthAccessToken) {
        Member member = findOrCreateMember(attributes);
        String refreshToken = getRefreshToken(member);

        updateMember(member, attributes, oauthAccessToken, refreshToken);
        memberRepository.save(member);

        attributes.updateAttributes(REFRESH.getType(), refreshToken);
    }

    private Member findOrCreateMember(OauthAttributes attributes) {
        return memberRepository.findByEmailAndProvider(attributes.getEmail(), attributes.getProvider())
                .orElseGet(() -> memberRepository.save(attributes.toEntity()));
    }

    private String getRefreshToken(Member member) {
        return jwtUtil.createRefreshToken(String.valueOf(member.getId()), String.valueOf(member.getRole()));
    }

    private static void updateMember(Member member, OauthAttributes attributes, String oauthAccessToken, String refreshToken) {
        member.updateOauthAccessToken(oauthAccessToken);
        member.updateRefreshToken(refreshToken);
        member.updateProfile(attributes.toEntity());
    }
}
