package toyproject.toyshop.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import toyproject.toyshop.domain.Member;
import toyproject.toyshop.repository.MemberRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");
        //when
        Long savedId = memberService.join(member);
        //then
        assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");
        //when
        memberService.join(member1);
        memberService.join(member2);
        //then
        fail();
        //todo exception handling
    }

    @Test
    public void 회원수정() throws Exception {
        Member member1 = new Member();
        member1.setName("kim");
        memberService.join(member1);
        memberService.updateName(member1.getId(), "cho");

        Member findMember = memberService.findOne(member1.getId());
        assertThat(findMember.getName()).isEqualTo("cho");
    }

    @Test
    public void 회원삭제() {
        Member member1 = new Member();
        member1.setName("kim");
        Member member2 = new Member();
        member2.setName("cho");
        memberService.join(member1);
        memberService.join(member2);

        memberService.deleteMember(member1);
        List<Member> members = memberService.findMembers();
        assertThat(members.size()).isEqualTo(1);
        assertThat(members.get(0)).isEqualTo(member2);
    }
}