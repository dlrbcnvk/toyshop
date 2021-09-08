package toyproject.toyshop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.toyshop.domain.*;
import toyproject.toyshop.repository.ItemRepository;
import toyproject.toyshop.repository.MemberJpaRepository;
import toyproject.toyshop.repository.OrderItemRepository;
import toyproject.toyshop.repository.OrderRepository;

import java.util.ArrayList;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final MemberJpaRepository memberJpaRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final OrderItemRepository orderItemRepository;

    /** 주문 **/
    @Transactional
    public Long order(Long memberId, Long[] itemIds, int[] itemCounts) {
        if (itemIds.length != itemCounts.length) {
            throw new IllegalStateException("정보가 안 맞다..! -_- ");
        }
        //멤버 조회
        Member member = memberJpaRepository.findOne(memberId);
        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setStatus(DeliveryStatus.READY);

        //상품 조회 + 주문상품 생성 (여러 개)
        ArrayList<OrderItem> orderItems = new ArrayList<>();
        for (int i = 0; i < itemIds.length; i++) {
            Item item = itemRepository.findOne(itemIds[i]);
            int getCount = itemCounts[i];
            OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), getCount);
            orderItemRepository.save(orderItem);
            orderItems.add(orderItem);
        }

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItems);

        //주문 저장
        orderRepository.save(order);
        return order.getId();
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        order.cancel();
    }

    /** 주문 검색 */

}
