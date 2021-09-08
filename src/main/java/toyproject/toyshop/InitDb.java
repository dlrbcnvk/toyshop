package toyproject.toyshop;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import toyproject.toyshop.domain.*;
import toyproject.toyshop.repository.MemberJpaRepository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;
    private static MemberJpaRepository memberJpaRepository;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;

        public void dbInit1() {
            Member member = createMember("userA", "dlrbcnvk@naver.com", "1234",
                    "seoul", "1", "1111");
            em.persist(member);
            Book book1 = createBook("JPA1 BOOK", 10000, 100);
            em.persist(book1);
            Book book2 = createBook("Spring Tobby", 20000, 50);
            em.persist(book2);
            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);
            ArrayList<OrderItem> orderItems = new ArrayList<>();
            orderItems.add(orderItem1);
            orderItems.add(orderItem2);
            Order order = Order.createOrder(member, createDelivery(member), orderItems);
            em.persist(order);
            ItemReview itemReview1 = createItemReview(member, book1, "아 별로네~ ㅜㅜ");
            em.persist(itemReview1);
            ItemReview itemReview2 = createItemReview(member, book2, "우와 이거 정말 좋다~ ㅎㅎ");
            ReviewComment reviewComment = createReviewComment(member, itemReview2, "222222");
            em.persist(itemReview2);
            em.persist(reviewComment);
        }

        public void dbInit2() {
            Member member = createMember("userB", "dlrbcnvk@gmail.com", "4321",
                    "gunpo", "66", "22");
            em.persist(member);
            Book book1 = createBook("Object Oriented Programming", 7000, 20);
            em.persist(book1);
            Book book2 = createBook("Software Engineering", 50000, 150);
            em.persist(book2);
            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 7000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 50000, 2);
            ArrayList<OrderItem> orderItems = new ArrayList<>();
            orderItems.add(orderItem1);
            orderItems.add(orderItem2);
            Order order = Order.createOrder(member, createDelivery(member), orderItems);
            em.persist(order);
            ItemReview itemReview1 = createItemReview(member, book1, "너무 좋아 흐흐흐");
            em.persist(itemReview1);
            ItemReview itemReview2 = createItemReview(member, book2, "하아 한 학기 동안 전젱이네 ㅠ");
            ReviewComment reviewComment = createReviewComment(member, itemReview2, "ㅋㅋㅋㅋ");
            em.persist(itemReview2);
            em.persist(reviewComment);
        }

        private Member createMember(String name, String email, String password, String city, String street, String zipcode) {
            Member member = new Member();
            member.setName(name);
            member.setAddress(new Address(city, street, zipcode));
            return member;
        }

        private Book createBook(String name, int price, int stockQuantity) {
            Book book = new Book();
            book.setName(name);
            book.setPrice(price);
            book.setStockQuantity(stockQuantity);
            return book;
        }

        private Delivery createDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }

        private ItemReview createItemReview(Member member, Item item, String review) {
            ItemReview itemReview = new ItemReview();
            itemReview.setMember(member);
            itemReview.setItem(item);
            itemReview.setReview(review);

            item.getReviews().add(itemReview);
            member.getReviews().add(itemReview);
            return itemReview;
        }

        private ReviewComment createReviewComment(Member member, ItemReview itemReview, String comment) {
            ReviewComment reviewComment = new ReviewComment();
            reviewComment.setMember(member);
            reviewComment.setItemReview(itemReview);
            reviewComment.setComment(comment);

            member.getComments().add(reviewComment);
            itemReview.getComments().add(reviewComment);
            return reviewComment;
        }
    }

}
