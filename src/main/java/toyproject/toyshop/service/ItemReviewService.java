package toyproject.toyshop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.toyshop.domain.Item;
import toyproject.toyshop.domain.ItemReview;
import toyproject.toyshop.domain.Member;
import toyproject.toyshop.domain.ReviewComment;
import toyproject.toyshop.repository.ItemReviewRepository;
import toyproject.toyshop.repository.ReviewCommentRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemReviewService {

    private final ItemReviewRepository itemReviewRepository;
    private final ReviewCommentRepository reviewCommentRepository;

    //todo
    /** 페이지 처리 */

    @Transactional
    public Long saveItemReview(ItemReview itemReview) {
        itemReviewRepository.save(itemReview);
        return itemReview.getId();
    }

    public List<ItemReview> findAll() {
        return itemReviewRepository.findAll();
    }

    public ItemReview findOne(Long id) {
        return itemReviewRepository.findOne(id);
    }

    public List<ItemReview> findByItem(Item item) {
        return itemReviewRepository.findByItem(item);
    }

    public List<ItemReview> findByMember(Member member) {
        return itemReviewRepository.findByMember(member);
    }

    @Transactional // 리뷰를 없애고자 할 때, 댓글 먼저 다 없애고 리뷰 제거
    public void delete(ItemReview itemReview) {
        for (ReviewComment comment : itemReview.getComments()) {
            reviewCommentRepository.delete(comment);
        }
        itemReviewRepository.delete(itemReview);
    }



}
