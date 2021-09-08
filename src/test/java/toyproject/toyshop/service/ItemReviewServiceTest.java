package toyproject.toyshop.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toyproject.toyshop.domain.*;
import toyproject.toyshop.repository.ItemRepository;
import toyproject.toyshop.repository.ItemReviewRepository;
import toyproject.toyshop.repository.MemberJpaRepository;
import toyproject.toyshop.repository.ReviewCommentRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
@Transactional
class ItemReviewServiceTest {

    @Autowired ItemReviewService itemReviewService;
    @Autowired ItemReviewRepository itemReviewRepository;
    @Autowired
    ReviewCommentRepository reviewCommentRepository;
    @Autowired ItemRepository itemRepository;
    @Autowired
    MemberJpaRepository memberJpaRepository;

    @PersistenceContext EntityManager em;

    @Test
    public void 리뷰등록() {
        Book book = new Book();
        book.setAuthor("kim");
        book.setIsbn("4646");
        book.setName("host");
        book.setPrice(10000);
        book.setStockQuantity(10);
        Member member = new Member();
        member.setName("choi");
        member.setEmail("dlrbcnvk@naver.com");
        member.setPassword("1234");
        member.setAddress(new Address("gunpo", "samsung", "15524"));
        itemRepository.save(book);
        memberJpaRepository.save(member);
        String review = "정말 좋은 아이템이네요~! >,<";
        Long reviewId = itemReviewService.review(book, member, review);

        ItemReview findItemReview = itemReviewRepository.findOne(reviewId);
        Item findItem = findItemReview.getItem();
        Assertions.assertThat(findItemReview.getItem().getStockQuantity()).isEqualTo(10);
        Assertions.assertThat(findItem.getPrice()).isEqualTo(10000);
    }

    @Test
    public void 리뷰수정() {
        Book book = new Book();
        book.setName("host");
        Member member = new Member();
        member.setName("choi");
        itemRepository.save(book);
        memberJpaRepository.save(member);
        String review = "정말 좋은 아이템이네요~! >,<";
        Long reviewId = itemReviewService.review(book, member, review);

        ItemReview itemReview = new ItemReview();
        itemReview.setId(reviewId);
        itemReview.setReview("zzzz");

        itemReviewService.updateReview(itemReview);
        ItemReview findItemReview = itemReviewRepository.findOne(reviewId);
        Assertions.assertThat(findItemReview.getReview()).isEqualTo("zzzz");
        Assertions.assertThat(findItemReview.getItem()).isEqualTo(book);
    }

    @Test
    public void 리뷰삭제() {
        Book book = new Book();
        book.setName("host");
        Member member = new Member();
        member.setName("choi");
        itemRepository.save(book);
        memberJpaRepository.save(member);
        Long reviewId = itemReviewService.review(book, member, "정말 좋은 아이템이네요~! >,<");
        ItemReview findItemReview = itemReviewRepository.findOne(reviewId);
        ReviewComment reviewComment1 = ReviewComment.createReviewComment(member, findItemReview, "1등");
        ReviewComment reviewComment2 = ReviewComment.createReviewComment(member, findItemReview, "2등");
        ReviewComment reviewComment3 = ReviewComment.createReviewComment(member, findItemReview, "3등");
//        reviewCommentRepository.save(reviewComment1);
//        reviewCommentRepository.save(reviewComment2);
//        reviewCommentRepository.save(reviewComment3);

        findItemReview = itemReviewRepository.findOne(reviewId);
        itemReviewService.delete(findItemReview);
        Assertions.assertThat(member.getReviews().size()).isEqualTo(0);
        Assertions.assertThat(member.getComments().size()).isEqualTo(0);

    }
}