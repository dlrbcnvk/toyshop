package toyproject.toyshop.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import toyproject.toyshop.api.response.CreateMemberResponse;
import toyproject.toyshop.api.response.UpdateMemberResponse;
import toyproject.toyshop.domain.Address;
import toyproject.toyshop.domain.Member;
import toyproject.toyshop.api.request.CreateMemberRequest;
import toyproject.toyshop.api.request.UpdateMemberRequest;
import toyproject.toyshop.service.MemberService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    /**
     * 회원 등록 : name, email, password, Address
     */
    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Validated CreateMemberRequest request) {
        Member member = new Member();
        member.setName(request.getName());
        member.setEmail(request.getEmail());
        member.setPassword(request.getPassword());
        member.setAddress(request.getAddress());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    /**
     * 회원 수정 (이름 수정)
     * update sql에서 비밀번호가 노출되는데....?! 문제 없는 건가 어떻게 고쳐야 하는건가 모르곘네.. ㅜㅜ
     */
    @PostMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id,
                                               @RequestBody @Validated UpdateMemberRequest request) {
        memberService.updateName(id, request.getName());
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    /**
     * 회원 조회
     */
    @GetMapping("/api/v2/members")
    public Result membersV2() {
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getName(), m.getEmail(), m.getAddress()))
                .collect(Collectors.toList());
        return new Result(collect);
    }

    @Data
    @AllArgsConstructor
    class Result<T> {
        private T data;
    }

    @Data
    @AllArgsConstructor
    class MemberDto{
        private String name;
        private String email;
        private Address address;
    }
}
