package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.repository.ItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    EntityManager em;

    @Test
    void updateItem() {
        //given
        Book book = createBook("jpa", 10000, 10);

        //when
        itemService.updateItem(book.getId(), "jpa2", 9000, 12);

        //then
        Assertions.assertThat(itemRepository.findOne(book.getId()).getName()).isEqualTo("jpa2");
        Assertions.assertThat(itemRepository.findOne(book.getId()).getPrice()).isEqualTo(9000);
        Assertions.assertThat(itemRepository.findOne(book.getId()).getStockQuantity()).isEqualTo(12);
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setStockQuantity(stockQuantity);
        book.setPrice(price);
        em.persist(book);
        return book;
    }
}