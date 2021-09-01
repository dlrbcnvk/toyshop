package toyproject.toyshop.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toyproject.toyshop.domain.ItemReview;
import toyproject.toyshop.domain.Member;
import toyproject.toyshop.domain.ReviewComment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewCommentRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(ReviewComment reviewComment) {
        em.persist(reviewComment);
    }

    public ReviewComment findOne(Long id) {
        return em.find(ReviewComment.class, id);
    }

    public List<ReviewComment> findAll() {
        return em.createQuery("select comment from ReviewComment comment", ReviewComment.class)
                .getResultList();
    }

    public List<ReviewComment> findByReview(ItemReview itemReview) {
        return em.createQuery("select comment from ReviewComment comment where comment.itemReview = :itemReview", ReviewComment.class)
                .setParameter("itemReview", itemReview)
                .getResultList();
    }

    public List<ReviewComment> findByMember(Member member) {
        return em.createQuery("select comment from ReviewComment comment where comment.member = :member", ReviewComment.class)
                .setParameter("member", member)
                .getResultList();
    }

    public void delete(ReviewComment reviewComment) {
        em.remove(reviewComment);
    }


}
