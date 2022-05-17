package peermarket.peershop.controller.dto;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class JoinUserDto {

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String nickname;

    @NotEmpty
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d~!@#$%^&*()+|=]{8,20}$")
    private String password;

    @NotEmpty
    private String passwordConfirm;

    @AssertTrue
    private boolean passwordEqual() {
        return password.equals(passwordConfirm);
    }

}
