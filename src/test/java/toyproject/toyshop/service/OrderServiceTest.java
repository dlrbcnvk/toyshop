package toyproject.toyshop.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import toyproject.toyshop.domain.*;
import toyproject.toyshop.repository.OrderRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
class OrderServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() {
        Member member = createMember();
        Long memberId = member.getId();
        Book item1 = createBook("시골 JPA", 10000, 10);
        Book item2 = createBook("도시 JPA", 20000, 15);
        Book item3 = createBook("부산 Spring", 30000, 12);
        Book item4 = createBook("서울 클린코드", 25000, 7);
        int orderCount1 = 2;
        int orderCount2 = 3;
        int orderCount3 = 5;
        int orderCount4 = 4;
        Long[] itemIds = new Long[4];
        itemIds[0] = item1.getId();
        itemIds[1] = item2.getId();
        itemIds[2] = item3.getId();
        itemIds[3] = item4.getId();
        int[] itemCounts = new int[4];
        itemCounts[0] = orderCount1;
        itemCounts[1] = orderCount2;
        itemCounts[2] = orderCount3;
        itemCounts[3] = orderCount4;
        Long orderId = orderService.order(memberId, itemIds, itemCounts);

        Order getOrder = orderRepository.findOne(orderId);

        assertThat(getOrder.getStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(14).isEqualTo(getOrder.getTotalCount());
        assertThat(330000).isEqualTo(getOrder.getTotalPrice());
        assertThat(item1.getStockQuantity()).isEqualTo(8);
        assertThat(12).isEqualTo(item2.getStockQuantity());
        assertThat(7).isEqualTo(item3.getStockQuantity());
        assertThat(3).isEqualTo(item4.getStockQuantity());
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);
        return member;
    }
    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setStockQuantity(stockQuantity);
        book.setPrice(price);
        em.persist(book);
        return book;
    }

}