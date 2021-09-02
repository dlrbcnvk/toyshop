package toyproject.toyshop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ReviewComment {

    @Id @GeneratedValue
    @Column(name = "review_comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_review_id")
    private ItemReview itemReview;

    private String comment;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    //==생성 메서드==//
    public ReviewComment createReviewComment(Member member, ItemReview itemReview, String comment) {
        ReviewComment reviewComment = new ReviewComment();
        reviewComment.setMember(member);
        reviewComment.setItemReview(itemReview);
        reviewComment.setComment(comment);
        reviewComment.setCreatedDate(LocalDateTime.now());
        reviewComment.setUpdatedDate(LocalDateTime.now());

        itemReview.addComment(reviewComment);
        return reviewComment;
    }

    //==조회 로직==//
}
