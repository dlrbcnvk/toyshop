package toyproject.toyshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import toyproject.toyshop.domain.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Page<Member> findByAge(int age, Pageable pageable);

}
