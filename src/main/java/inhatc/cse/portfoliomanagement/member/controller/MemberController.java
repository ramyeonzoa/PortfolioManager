package inhatc.cse.portfoliomanagement.member.controller;

import inhatc.cse.portfoliomanagement.member.dto.MemberDto;
import inhatc.cse.portfoliomanagement.member.entity.Member;
import inhatc.cse.portfoliomanagement.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {
        return "member/login";
    }

    // 로그인 실패 시 model에 LoginFailureMessage를 실어서 member/login [GET]
    // SecurityConfig에 작성되어있음
    // 진짜 이메일 또는 비밀번호때문에 로그인 실패인지? 다른 에러는 아닐지? <- 더 찾아보기
    @GetMapping("/login/failure")
    public String loginFailure(Model model) {
        model.addAttribute("loginFailureMessage", "이메일 또는 비밀번호가 잘못되었습니다.");
        return "member/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/";
    }

    // SignUp [GET] [POST]
    @GetMapping("/signup")
    public String showSignUpForm(Model model) {
        model.addAttribute("memberDto", new MemberDto());
        return "member/signup"; // signup.html) th:object="${memberDto}"
    }

    @PostMapping("/signup")
    public String showSignUpForm(@Valid MemberDto memberDto,
                                 BindingResult bindingResult,
                                 Model model) {

        if (bindingResult.hasErrors()) {
            return "member/signup";
        }

        try {
            Member member = Member.createMember(memberDto, passwordEncoder);
            memberService.saveMember(member);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/signup";
        }

        return "member/login";
    }


}
