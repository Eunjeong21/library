package com.example.library.service;

import com.example.library.domain.Member;
import com.example.library.dto.ConfirmPasswordDto;
import com.example.library.dto.JoinMemberDto;
import com.example.library.dto.ModifyMemberDto;
import com.example.library.dto.SearchMemberDto;
import java.util.List;

public interface MemberService {

    void joinMember(JoinMemberDto request);

    void modifyMember(ModifyMemberDto request);

    void confirmPassword(ConfirmPasswordDto request);

    void withdrawalMember(Long id);

    List<Member> searchKeywordMember(SearchMemberDto request);

    List<Member> searchAllMember();
}
