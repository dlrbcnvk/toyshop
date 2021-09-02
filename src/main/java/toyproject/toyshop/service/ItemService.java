package toyproject.toyshop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.toyshop.domain.Item;
import toyproject.toyshop.repository.ItemRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public Long saveItem(Item item) {
        itemRepository.save(item);
        return item.getId();
    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    public void delete(Item item) {
        itemRepository.delete(item);
    }

    @Transactional
    public void updateItem(Item itemParam) { //이걸로 리뷰, 상품 상세는 수정 불가
        Item findItem = itemRepository.findOne(itemParam.getId());
        findItem.setName(itemParam.getName());
        findItem.setPrice(itemParam.getPrice());
        findItem.setStockQuantity(itemParam.getStockQuantity());
    }


}
