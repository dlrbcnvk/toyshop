package toyproject.toyshop.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toyproject.toyshop.domain.Book;
import toyproject.toyshop.domain.Item;
import toyproject.toyshop.repository.ItemRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired ItemService itemService;
    @Autowired
    ItemRepository itemRepository;

    @Test
    public void 상품등록() throws Exception {
        //given
        Book book = new Book();
        book.setAuthor("kim");
        book.setIsbn("4646");
        //when
        Long savedId = itemService.saveItem(book);
        //then
        assertThat(book).isEqualTo(itemRepository.findOne(savedId));
    }
    
    @Test
    public void 상품수정() throws Exception {
        //given
        Book book = new Book();
        book.setAuthor("kim");
        book.setIsbn("4646");
        Long savedId = itemService.saveItem(book);

        // todo

    }

    @Test
    public void 상품목록조회() {
        Book book1 = new Book();
        book1.setAuthor("kim");
        book1.setIsbn("4646");
        Book book2 = new Book();
        book2.setAuthor("cho");
        book2.setIsbn("2222");
        itemService.saveItem(book1);
        itemService.saveItem(book2);

        Book findItem = (Book)itemService.findOne(book1.getId());
        assertThat(findItem.getIsbn()).isEqualTo(book1.getIsbn());
    }

    @Test
    public void 상품삭제() {
        Book book1 = new Book();
        book1.setAuthor("kim");
        book1.setIsbn("4646");
        Book book2 = new Book();
        book2.setAuthor("cho");
        book2.setIsbn("2222");
        itemService.saveItem(book1);
        itemService.saveItem(book2);

        itemService.delete(book1);
        List<Item> items = itemService.findItems();
        assertThat(items.size()).isEqualTo(1);
        assertThat(items.get(0)).isEqualTo(book2);
    }
}