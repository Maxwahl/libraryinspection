package at.htl;


import at.htl.library.business.ItemDao;
import at.htl.library.model.Book;
import at.htl.library.model.CD;
import at.htl.library.model.Item;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.QueryTimeoutException;
import javax.persistence.TypedQuery;
import javax.swing.text.html.parser.Entity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@QuarkusTest
public class MockingTest {

    static ItemDao mockedItemDao = mock(ItemDao.class);
    static Book predeterminedBook = new Book("Das Erbe der Elfen",12.0,"fantasy","Andrzej Sapkowski",320);
    @BeforeAll
    public static void init(){
        EntityManager mockedEntityManager = mock(EntityManager.class);
        TypedQuery<Item> mockedQuery = (TypedQuery<Item>) mock(TypedQuery.class);
        when(mockedQuery.getResultList()).thenThrow(new QueryTimeoutException("test",null,null));
        when(mockedEntityManager.createNamedQuery("Item.findAll",Item.class)).thenReturn(mockedQuery);
        when(mockedItemDao.getBook(anyLong())).thenReturn(predeterminedBook);
        when(mockedItemDao.get()).thenCallRealMethod();
        when(mockedItemDao.getCD(anyLong())).thenThrow(new RuntimeException());
        mockedItemDao.em = mockedEntityManager;
    }




    @Test
    public void t01_listItemsGetException(){
        List<Item> items = mockedItemDao.get();
        assertThat(items).isNull();
    }
    //get predetermined data
    @Test
    public void t02_getPredeterminedBook(){
        Book book = mockedItemDao.getBook(1);
        assertThat(book.getName()).isEqualTo("Das Erbe der Elfen");
        assertThat(book.getPrice()).isEqualTo(12.0);
    }
    //
    @Test
    public void t03_catchException(){
        Exception exception = assertThrows(RuntimeException.class, () -> {
            mockedItemDao.getCD(1);
        });
        assertThat(exception).isNotNull();
    }
}
