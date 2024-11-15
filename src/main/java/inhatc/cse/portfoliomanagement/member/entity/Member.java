package inhatc.cse.portfoliomanagement.member.entity;

import inhatc.cse.portfoliomanagement.common.entity.BaseTimeEntity;
import inhatc.cse.portfoliomanagement.member.constant.Role;
import inhatc.cse.portfoliomanagement.member.constant.Status;
import inhatc.cse.portfoliomanagement.member.dto.MemberDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(length = 30, nullable = false)
    private String name;

    @Column(length = 20, nullable = true)
    private String phoneNumber;

    @Column(length = 200, nullable = true)
    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Status status;

    public static Member createMember(MemberDto memberDto, PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(memberDto.getEmail())
                .password(passwordEncoder.encode(memberDto.getPassword()))
                .name(memberDto.getName())
                .phoneNumber(memberDto.getPhoneNumber())
                .address(memberDto.getAddress())
                .role(Role.USER)
                .status(Status.ACTIVE)
                .build();
    }

}
