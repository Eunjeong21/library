package com.example.library.controller.api;

import com.example.library.domain.Member;
import com.example.library.dto.ResponseDto;
import com.example.library.dto.ResponseMemberDto;
import com.example.library.dto.SearchMemberDto;
import com.example.library.service.MemberService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/members")
public class AdminMemberController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<ResponseDto> searchAllMember() {
        List<Member> members = memberService.searchAllMember();
        return getResponseMembers(members);
    }


    @GetMapping("/{keyword}")
    public ResponseEntity<ResponseDto> searchKeywordMember(@PathVariable @Valid SearchMemberDto keyword) {
        List<Member> members = memberService.searchKeywordMember(keyword);
        return getResponseMembers(members);
    }

    private ResponseEntity<ResponseDto> getResponseMembers(List<Member> members) {
        List<ResponseMemberDto> responseMembers = members.stream()
            .map(m -> new ResponseMemberDto(
                m.getName(),
                m.getLoginId(),
                m.getEmail(),
                m.getPhone(),
                m.getAddress(),
                m.getLoanStatus().name(),
                m.getCreatedAt(),
                m.getModifiedAt(),
                m.isWithdrawalStatus()
            )).toList();
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ResponseDto.builder()
                .status(HttpStatus.OK)
                .data(responseMembers)
                .build());
    }
}
