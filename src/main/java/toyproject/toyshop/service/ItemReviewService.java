package toyproject.toyshop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.toyshop.domain.Item;
import toyproject.toyshop.domain.ItemReview;
import toyproject.toyshop.domain.Member;
import toyproject.toyshop.repository.ItemReviewRepository;
import toyproject.toyshop.repository.ReviewCommentRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemReviewService {

    private final ItemReviewRepository itemReviewRepository;

    //todo
    /** 페이지 처리 */

    public void saveItemReview(ItemReview itemReview) {
        itemReviewRepository.save(itemReview);
    }

    public List<ItemReview> findItemReviews() {
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

    public void delete(ItemReview itemReview) {
        itemReviewRepository.delete(itemReview);
    }

}
