package toyproject.toyshop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.toyshop.domain.Member;
import toyproject.toyshop.repository.MemberJpaRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberJpaRepository memberJpaRepository;

    /**
     * 회원 가입
     */
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberJpaRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberJpaRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers(){
        return memberJpaRepository.findAll();
    }

    public Member findOne(Long memberId){
        return memberJpaRepository.findOne(memberId);
    }

    /**
     * 회원 수정
     */
    @Transactional
    public void updateName(Long id, String name) {
        Member findMember = memberJpaRepository.findOne(id);
        if (findMember == null) {
            throw new IllegalStateException("없는 사람 이름을 바꾸려고 하네..?!");
        }
        findMember.setName(name);
    }

    /**
     * 회원 삭제
     */
    @Transactional
    public void deleteMember(Member member) {
         memberJpaRepository.delete(member);
    }
}
