package btongtong.btongtalkback.repository;

import btongtong.btongtalkback.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
