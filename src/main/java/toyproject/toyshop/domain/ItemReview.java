package toyproject.toyshop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class ItemReview {

    @Id @GeneratedValue
    @Column(name = "item_review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private String review;

    @OneToMany(mappedBy = "itemReview")
    private List<ReviewComment> comments = new ArrayList<>();

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    //==생성 메서드==//
    public ItemReview createItemReview(Item item, Member member, String review) {
        ItemReview itemReview = new ItemReview();
        itemReview.setMember(member);
        itemReview.setItem(item);
        itemReview.setReview(review);
        itemReview.setCreatedDate(LocalDateTime.now());
        itemReview.setUpdatedDate(LocalDateTime.now());
        return itemReview;
    }

    //==비즈니스 로직==//
    //todo cancel

    public void addComment(ReviewComment reviewComment) {
        comments.add(reviewComment);
    }
    //==조회 로직==//
    //todo
}
