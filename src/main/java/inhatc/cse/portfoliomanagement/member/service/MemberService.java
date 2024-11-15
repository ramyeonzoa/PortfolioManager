package inhatc.cse.portfoliomanagement.member.service;

import inhatc.cse.portfoliomanagement.member.entity.Member;
import inhatc.cse.portfoliomanagement.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public Member saveMember(Member member) {
        duplicateCheck(member);
        // TODO : 회원 가입 성공 메시지
        return memberRepository.save(member);
    }

    private void duplicateCheck(Member member) {
        memberRepository.findByEmail(member.getEmail())
                .ifPresent(existingMember -> {
                    throw new IllegalStateException("이미 가입된 이메일 주소입니다.");
                });
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmail(email).orElseThrow(() -> {
            return new UsernameNotFoundException("존재하지 않는 사용자입니다 : " + email);
        });

        log.info(member.toString());

        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                // TODO : Status가 Inactive이거나 Deleted일 때 처리
                .build();
    }

}
