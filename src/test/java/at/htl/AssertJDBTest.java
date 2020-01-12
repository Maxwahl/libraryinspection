package at.htl;


import io.quarkus.test.junit.QuarkusTest;
import org.assertj.db.type.Table;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.sql.DataSource;


import static org.assertj.db.api.Assertions.assertThat;


@QuarkusTest
public class AssertJDBTest {


    @Inject
    DataSource dataSource;



    @Test
    public void t01_TableExists(){
        Table itemTable = new Table(dataSource,"item");

        assertThat(itemTable).exists();
    }

    @Test
    public void t02_TableDoesNotExist(){
        Table carTable = new Table(dataSource,"car");

        assertThat(carTable).doesNotExist();
    }

    @Test
    public void t03_TableHasColumns(){
        Table personTable = new Table(dataSource,"person");

        assertThat(personTable).hasNumberOfColumns(2);
        assertThat(personTable).column("id").isNumber(false);
        assertThat(personTable).column("name").isText(false);

    }

    @Test
    public void t04_TableHasData(){
        Table personTable = new Table(dataSource,"person");

        assertThat(personTable).hasNumberOfRows(2);
        assertThat(personTable).column("id").hasValues(1,2);
        assertThat(personTable).column("name").hasValues("Meier","Hofer");
    }

}
