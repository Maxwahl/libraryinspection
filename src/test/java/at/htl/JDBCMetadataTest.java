package at.htl;


import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@QuarkusTest
public class JDBCMetadataTest {

    private static Connection connection;
    private static DatabaseMetaData metaData;

    @BeforeAll
    static void init(){
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/library","library","passme");
            metaData =connection.getMetaData();
            System.out.println("connected to db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @Test
    public void t01_TableExists(){
        try {
            ResultSet tables = metaData.getTables(null, null, "item", new String[]{"TABLE"});
            if(tables.next())
            {
                assertThat(tables.getString("TABLE_NAME")).isEqualTo("item");
            }
//
        } catch (SQLException e) {
            e.printStackTrace();
            fail("SQL-Exception");
        }
    }

    @Test
    public void t02_TableDoesNotExist(){
        try {
            ResultSet tables = metaData.getTables(null, null, "car", new String[]{"TABLE"});
            if(tables.next()){
                fail("Table \"car\" does exist");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            fail("SQL-Exception");
        }
    }

    @Test
    public void t03_TableHasColumns(){
        try {
            ResultSet columns = metaData.getColumns(null,"public", "person", null);
            int cnt = 0;
            while(columns.next())
            {
                String columnName = columns.getString("COLUMN_NAME");
                String datatype = columns.getString("DATA_TYPE");

                if(cnt == 0){
                    assertThat(columnName).isEqualTo("id");
                    assertThat(datatype).isEqualTo("-5");
                }
                else {
                    assertThat(columnName).isEqualTo("name");
                    assertThat(datatype).isEqualTo("12");
                }
                cnt++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            fail("SQL-Exception");
        }
    }

    @Test
    public void t04_TableHasPK(){
        try {
            ResultSet pk = metaData.getPrimaryKeys(null,null, "person");
            if(pk.next()){
                assertThat(pk.getString("COLUMN_NAME")).isEqualTo("id");
            }
            else {
                fail("no PK found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            fail("SQL-Exception");
        }
    }
    @Test
    public void t05_TableHasFK(){
        try {
            ResultSet fk = metaData.getImportedKeys(null,null, "exemplar");
            if(fk.next()){
                assertThat(fk.getString("PKTABLE_NAME")).isEqualTo("item");
            }
            else {
                fail("no FK found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            fail("SQL-Exception");
        }
    }

}
