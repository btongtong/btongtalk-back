package btongtong.btongtalkback.repository;

import btongtong.btongtalkback.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByOauthKeyAndProvider(String oauthKey, String provider);
    Optional<Member> findByEmail(String email);
}
