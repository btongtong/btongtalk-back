package btongtong.btongtalkback.service.unnlink;

import btongtong.btongtalkback.constant.Provider;

public interface OauthUnlinkService {
    void unlink(String token);
    Provider getProvider();
}
