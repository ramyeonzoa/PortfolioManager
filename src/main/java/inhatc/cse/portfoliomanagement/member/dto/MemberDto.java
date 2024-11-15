package inhatc.cse.portfoliomanagement.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {

    @NotBlank(message = "이메일은 필수 항목입니다.")
    @Email(message = "이메일 형식으로 입력하세요.")
    private String email;

    @NotEmpty(message = "비밀번호는 필수 항목입니다.")
    @Length(min = 4, max = 20, message = "비밀번호는 4자 이상 20자 이하로 설정해 주세요.")
    private String password;

    @NotBlank(message = "이름은 필수 항목입니다.")
    private String name;

    private String phoneNumber;

    private String address;

}
