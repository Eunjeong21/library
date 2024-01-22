package com.example.library.controller.api;

import com.example.library.dto.JoinMemberDto;
import com.example.library.dto.ModifyMemberDto;
import com.example.library.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/new")
    public HttpStatus join(@RequestBody @Valid JoinMemberDto request) {
        memberService.joinMember(request);
        return HttpStatus.OK;
    }

    @PatchMapping
    public HttpStatus modify(@RequestBody @Valid ModifyMemberDto request) {
        memberService.modifyMember(request);
        return HttpStatus.OK;
    }

    @DeleteMapping
    public HttpStatus withdrawal(Long id) {
        memberService.withdrawalMember(id);
        return HttpStatus.OK;
    }
}
