package peermarket.peershop.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import peermarket.peershop.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUsername(String username);

    Optional<Member> findByEmail(String email);

}
