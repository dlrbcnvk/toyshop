package toyproject.toyshop.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toyproject.toyshop.domain.Book;
import toyproject.toyshop.domain.ItemReview;
import toyproject.toyshop.domain.Member;
import toyproject.toyshop.domain.ReviewComment;
import toyproject.toyshop.repository.ItemRepository;
import toyproject.toyshop.repository.ItemReviewRepository;
import toyproject.toyshop.repository.MemberRepository;
import toyproject.toyshop.repository.ReviewCommentRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ReviewCommentServiceTest {

    @Autowired ReviewCommentService reviewCommentService;
    @Autowired ReviewCommentRepository reviewCommentRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired ItemReviewRepository itemReviewRepository;
    @Autowired ItemRepository itemRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    public void 상품댓글등록() {
        Book book = new Book();
        book.setName("software Engineering");
        Member member = new Member();
        member.setName("kim");
        ItemReview itemReview = ItemReview.createItemReview(book, member, "I love this book...!");
        memberRepository.save(member);
        itemRepository.save(book);
        itemReviewRepository.save(itemReview);

        Long commentId = reviewCommentService.comment(member, itemReview, "me too~ >,<");

        ReviewComment findReviewComment = reviewCommentRepository.findOne(commentId);
        assertThat(findReviewComment.getComment()).isEqualTo("me too~ >,<");
        assertThat(findReviewComment.getMember()).isEqualTo(member);
        assertThat(findReviewComment.getItemReview()).isEqualTo(itemReview);
        assertThat(findReviewComment.getItemReview().getItem()).isEqualTo(book);
    }

    @Test
    public void 상품댓글수정() {
        Book book = new Book();
        book.setName("software Engineering");
        Member member = new Member();
        member.setName("kim");
        ItemReview itemReview = ItemReview.createItemReview(book, member, "I love this book...!");
        memberRepository.save(member);
        itemRepository.save(book);
        itemReviewRepository.save(itemReview);
        Long commentId = reviewCommentService.comment(member, itemReview, "me too~ >,<");

        ReviewComment reviewComment = new ReviewComment();
        reviewComment.setId(commentId);
        reviewComment.setComment("시러시러 ㅠ,ㅠ");

        reviewCommentService.updateComment(reviewComment);
        ReviewComment findReviewComment = reviewCommentRepository.findOne(commentId);
        assertThat(findReviewComment.getComment()).isEqualTo("시러시러 ㅠ,ㅠ");
        assertThat(findReviewComment.getMember()).isEqualTo(member);
        assertThat(findReviewComment.getItemReview().getItem()).isEqualTo(book);
    }

    @Test
    public void 상품댓글삭제() {
        Book book = new Book();
        book.setName("software Engineering");
        Member member = new Member();
        member.setName("kim");
        itemRepository.save(book);
        memberRepository.save(member);
        ItemReview itemReview = ItemReview.createItemReview(book, member, "I love this book...!");
        itemReviewRepository.save(itemReview);
        Long commentId1 = reviewCommentService.comment(member, itemReview, "me too~ >,<");
        Long commentId2 = reviewCommentService.comment(member, itemReview, "아 2등이네 ㅠ,ㅠ");
        Long commentId3 = reviewCommentService.comment(member, itemReview, "머고 이건");
        ReviewComment findReviewComment = reviewCommentRepository.findOne(commentId2);
        reviewCommentService.delete(findReviewComment);

        assertThat(member.getComments().size()).isEqualTo(2);
        assertThat(member.getReviews().size()).isEqualTo(1);
        assertThat(member.getReviews().get(0).getComments().size()).isEqualTo(2);

    }

}