package toyproject.toyshop.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toyproject.toyshop.domain.Book;
import toyproject.toyshop.domain.Item;
import toyproject.toyshop.domain.ItemReview;
import toyproject.toyshop.domain.ReviewComment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemReviewServiceTest {

    @Autowired ItemReviewService itemReviewService;
    @Autowired ReviewCommentService reviewCommentService;
    @Autowired ItemService itemService;

    @PersistenceContext
    EntityManager em;

    @Test
    public void 리뷰등록() {
        Book book = new Book();
        book.setAuthor("kim");
        book.setIsbn("4646");
        book.setName("host");
        book.setPrice(10000);
        book.setStockQuantity(10);
        Long savedId = itemService.saveItem(book);



        ReviewComment comment1 = new ReviewComment();
        comment1.setComment("그러게요~~");
        ReviewComment comment2 = new ReviewComment();
        comment2.setComment("이건 좀 아니지 않나...");
        ReviewComment comment3 = new ReviewComment();
        comment3.setComment("선 넘었넹");

        List<ReviewComment> comments = itemReview.getComments();
        comments.add(comment1);
        comments.add(comment2);
        comments.add(comment3);
        List<ItemReview> reviews = book.getReviews();
        reviews.add(itemReview);

        Long comment1Id = reviewCommentService.saveReviewComment(comment1);
        Long comment2Id = reviewCommentService.saveReviewComment(comment2);
        Long comment3Id = reviewCommentService.saveReviewComment(comment3);
        Long reviewId = itemReviewService.saveItemReview(itemReview);

        em.flush();
        em.clear();
        Item findItem = itemService.findOne(savedId);

        assertThat(findItem.getReviews().size()).isEqualTo(1);

    }
}