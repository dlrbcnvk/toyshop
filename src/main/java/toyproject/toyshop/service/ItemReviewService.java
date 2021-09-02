package toyproject.toyshop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.toyshop.domain.Item;
import toyproject.toyshop.domain.ItemReview;
import toyproject.toyshop.domain.Member;
import toyproject.toyshop.domain.ReviewComment;
import toyproject.toyshop.repository.ItemRepository;
import toyproject.toyshop.repository.ItemReviewRepository;
import toyproject.toyshop.repository.MemberRepository;
import toyproject.toyshop.repository.ReviewCommentRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemReviewService {

    private final ItemReviewRepository itemReviewRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final ReviewCommentRepository reviewCommentRepository;

    //todo

    /**
     * 페이지 처리
     */

    /** 리뷰 등록 */
    @Transactional
    public Long review(Item item, Member member, String review) {
        ItemReview itemReview = ItemReview.createItemReview(item, member, review);
        itemReviewRepository.save(itemReview);
        return itemReview.getId();
    }

    /** 리뷰 수정 */
    @Transactional
    public void updateReview(ItemReview itemReview) {
        ItemReview findItemReview = itemReviewRepository.findOne(itemReview.getId());
        findItemReview.setReview(itemReview.getReview());
        findItemReview.setUpdatedDate(LocalDateTime.now());
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

    @Transactional // 리뷰를 없애고자 할 때, 댓글 먼저 다 없애고, 멤버, 아이템에서 해당 리뷰 제거, 이후에 리뷰 제거
    public void delete(ItemReview itemReview) {
        for (ReviewComment reviewComment : itemReview.getComments()) {
            reviewCommentRepository.delete(reviewComment);
            itemReview.getMember().getComments().remove(reviewComment);
        }
        itemReview.getMember().getReviews().remove(itemReview);
        itemReview.getItem().getReviews().remove(itemReview);
        itemReviewRepository.delete(itemReview);
    }



}
