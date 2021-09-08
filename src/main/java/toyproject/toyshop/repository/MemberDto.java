package toyproject.toyshop.repository;

import lombok.Data;
import toyproject.toyshop.domain.Address;
import toyproject.toyshop.domain.ItemReview;
import toyproject.toyshop.domain.Order;
import toyproject.toyshop.domain.ReviewComment;

import java.util.ArrayList;
import java.util.List;

@Data
public class MemberDto {
    private Long id;
    private String name;
    private Address address;

    private List<Order> orders = new ArrayList<>();
    private List<ReviewComment> comments = new ArrayList<>();
    private List<ItemReview> reviews = new ArrayList<>();

    public MemberDto(Long id, String name, Address address, List<Order> orders,
                     List<ReviewComment> comments, List<ItemReview> reviews) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.orders = orders;
        this.comments = comments;
        this.reviews = reviews;
    }
}
