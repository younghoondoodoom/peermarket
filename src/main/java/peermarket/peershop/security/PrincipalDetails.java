package peermarket.peershop.security;

// 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다.
// 로그인ㅇ르 진행이 완료가 되면 시큐리티 session을 만들어준다.(Security ContextHolder)
// 오브젝트가 정해져있음 => Authentication 타입 객체
// Authentication 안에 member 정보가 있어야 됨.
// User 오브젝트타입 -> userDetails 타입 객체

// Security Session => Authentication => UserDetails

import java.util.ArrayList;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import peermarket.peershop.entity.Member;
import peermarket.peershop.entity.status.MemberStatus;

@Getter
@AllArgsConstructor
public class PrincipalDetails implements UserDetails {

    private final Member member;

    // 해당 유저의 권한을 리턴하는 곳
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authentications = new ArrayList<>();
        authentications.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return member.getRole();
            }
        });
        return authentications;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        if (member.getStatus().equals(MemberStatus.DELETED)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        if (member.getStatus().equals(MemberStatus.INACTIVE)) {
            return false;
        }
        return true;
    }
}
