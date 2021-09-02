package toyproject.toyshop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.toyshop.domain.Item;
import toyproject.toyshop.domain.ItemReview;
import toyproject.toyshop.domain.Member;
import toyproject.toyshop.domain.ReviewComment;
import toyproject.toyshop.repository.ItemReviewRepository;
import toyproject.toyshop.repository.MemberRepository;
import toyproject.toyshop.repository.ReviewCommentRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewCommentService {

    private final ReviewCommentRepository reviewCommentRepository;
    private final MemberRepository memberRepository;
    private final ItemReviewRepository itemReviewRepository;


    /** 댓글 등록 */
    @Transactional
    public Long comment(Member member, ItemReview itemReview, String comment) {
        ReviewComment reviewComment = ReviewComment.createReviewComment(member, itemReview, comment);
        reviewCommentRepository.save(reviewComment);
        return reviewComment.getId();
    }

    /** 댓글 수정 */
    @Transactional
    public void updateComment(ReviewComment reviewComment) {
        ReviewComment findReviewComment = reviewCommentRepository.findOne(reviewComment.getId());
        findReviewComment.setComment(reviewComment.getComment());
        findReviewComment.setUpdatedDate(LocalDateTime.now());
    }

    public List<ReviewComment> findItemReviews() {
        return reviewCommentRepository.findAll();
    }

    public ReviewComment findOne(Long id) {
        return reviewCommentRepository.findOne(id);
    }

    public List<ReviewComment> findByItemReview(ItemReview ItemReview) {
        return reviewCommentRepository.findByItemReview(ItemReview);
    }

    public List<ReviewComment> findByMember(Member member) {
        return reviewCommentRepository.findByMember(member);
    }

    @Transactional //이 댓글 하나만 없애고 싶을 때
    public void delete(ReviewComment reviewComment) {
        reviewCommentRepository.delete(reviewComment);
        reviewComment.getMember().getComments().remove(reviewComment);
        reviewComment.getItemReview().getComments().remove(reviewComment);
    }

}
