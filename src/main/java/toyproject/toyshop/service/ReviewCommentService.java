package toyproject.toyshop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.toyshop.domain.Item;
import toyproject.toyshop.domain.ItemReview;
import toyproject.toyshop.domain.Member;
import toyproject.toyshop.domain.ReviewComment;
import toyproject.toyshop.repository.ReviewCommentRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewCommentService {

    private final ReviewCommentRepository reviewCommentRepository;

    public void saveReviewComment(ReviewComment ReviewComment) {
        reviewCommentRepository.save(ReviewComment);
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

    public void delete(ReviewComment ReviewComment) {
        reviewCommentRepository.delete(ReviewComment);
    }

}
