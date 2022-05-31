package peermarket.peershop.dto;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class JoinMemberDto {

    @NotEmpty(message = "이메일은 필수 입력 사항입니다.")
    @Email(message = "이메일 형식이 아닙니다.")
    private String email;

    @NotEmpty(message = "이름은 필수 입력 사항입니다..")
    private String username;

    @NotEmpty
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d~!@#$%^&*()+|=]{8,20}$", message = "비밀번호는 영어, 숫자를 포함해 8~20 글자이어야 합니다.")
    private String password;

    @NotEmpty(message = "비밀번호를 확인해주세요.")
    private String passwordConfirm;

    @AssertTrue
    private boolean passwordEqual() {
        return password.equals(passwordConfirm);
    }

}
