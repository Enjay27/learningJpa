package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepositoryOld;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepositoryOld memberRepository;

    @Autowired
    EntityManager em;

    @Test
    @Rollback(false)
    public void createMember() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long savedId = memberService.join(member);

        //then
        assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test
    public void duplicateException() throws Exception {
        //given
        Member member1 = new Member();
        Member member2 = new Member();

        member1.setName("kim");
        member2.setName("kim");

        //when
        memberService.join(member1);

        //then
        Assertions.assertThrows(IllegalStateException.class, () -> {
            memberService.join(member2);
        });

    }

}