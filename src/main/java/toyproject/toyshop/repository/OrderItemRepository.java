package toyproject.toyshop.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toyproject.toyshop.domain.OrderItem;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class OrderItemRepository {

    private final EntityManager em;

    public void save(OrderItem orderItem) {
        em.persist(orderItem);
    }

    public OrderItem findOne(Long id) {
        return em.find(OrderItem.class, id);
    }
}
