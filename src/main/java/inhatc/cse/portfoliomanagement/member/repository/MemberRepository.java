package inhatc.cse.portfoliomanagement.member.repository;

import inhatc.cse.portfoliomanagement.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
}
