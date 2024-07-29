package btongtong.btongtalkback.service;

import btongtong.btongtalkback.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class CustomOauth2AuthorizedClientService implements OAuth2AuthorizedClientService {
    private final MemberRepository memberRepository;

    @Override
    public <T extends OAuth2AuthorizedClient> T loadAuthorizedClient(String clientRegistrationId, String principalName) {
        return null;
    }

    @Override
    @Transactional
    public void saveAuthorizedClient(OAuth2AuthorizedClient authorizedClient, Authentication principal) {
        memberRepository.findById(Long.parseLong(principal.getName())).ifPresent(member -> {
            String accessToken = authorizedClient.getAccessToken().getTokenValue();
            String refreshToken = authorizedClient.getRefreshToken() != null ? authorizedClient.getRefreshToken().getTokenValue() : null;
            member.updateOauthToken(accessToken, refreshToken);
        });
    }

    @Override
    public void removeAuthorizedClient(String clientRegistrationId, String principalName) {

    }
}
