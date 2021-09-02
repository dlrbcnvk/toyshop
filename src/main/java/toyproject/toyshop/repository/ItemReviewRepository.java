package toyproject.toyshop.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toyproject.toyshop.domain.Item;
import toyproject.toyshop.domain.ItemReview;
import toyproject.toyshop.domain.Member;
import toyproject.toyshop.domain.ReviewComment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemReviewRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(ItemReview itemReview) {
        em.persist(itemReview);
    }

    public ItemReview findOne(Long id) {
        return em.find(ItemReview.class, id);
    }

    public List<ItemReview> findAll() {
        return em.createQuery("select review from ItemReview review", ItemReview.class)
                .getResultList();
    }

    public List<ItemReview> findByItem(Item item) {
        return em.createQuery("select review from ItemReview review where review.item = :item", ItemReview.class)
                .setParameter("item", item)
                .getResultList();
    }

    public List<ItemReview> findByMember(Member member) {
        return em.createQuery("select review from ItemReview review where review.member = :member", ItemReview.class)
                .setParameter("member", member)
                .getResultList();
    }

    public void delete(ItemReview itemReview) {
        em.remove(itemReview);
    }


}
