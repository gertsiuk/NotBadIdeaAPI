package DBConnectionPartTest;

/**
 * Created by m on 29.11.2016.
 */
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import DBConnectionPart.AccessToDB.ConnectorToDB;

/**
 * Created by Nikolia on 29.11.2016.
 * Perform one-by-one to check connection and rights
 * first checks connection
 * second checks inserts rights
 * third fails anyway, returns result and check rights of select (there will be difference)
 * fourth checks update rights
 * fifth checks delete rights
 */
public class AccessToDBTest {

    @Test
    public void ConnectionTest()
    {
        ConnectorToDB connectortoDB;
        connectortoDB = new ConnectorToDB();
        boolean OpenConnectionValue = connectortoDB.OpenConnection();
        assertEquals(true,OpenConnectionValue);
        boolean CloseConnectionValue = connectortoDB.CloseConnection();
        assertEquals(true,CloseConnectionValue);
    }

    @Test
    public void InsertTest()
    {
        ConnectorToDB connectortoDB;
        connectortoDB = new ConnectorToDB();
        boolean OpenConnectionValue = connectortoDB.OpenConnection();
        assertEquals(true,OpenConnectionValue);
        try {
            Statement st = connectortoDB.getConnection().createStatement();
            st.execute( " INSERT INTO `ideaservice`.`status` (`status`)" +
                        " VALUES                             ('Not Ready');");
        }
        catch (SQLException e) {
            assertEquals("Fails if here, either pass", e.getMessage());
        }
        connectortoDB.CloseConnection();
    }

    @Test
    // Must fails anyway
    public void SelectTest()
    {
        ConnectorToDB connectortoDB;
        connectortoDB = new ConnectorToDB();
        connectortoDB.OpenConnection();
        try {
            String query = "SELECT     `status`.`statusPK`," +
                    "           `status`.`status`" +
                    "FROM       `ideaservice`.`status`";
            PreparedStatement ps = connectortoDB.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    assertEquals("Must fails anyway", rs.getInt(1) + " " + rs.getString(2));
                }
            }
        }
        catch (SQLException e) {
            assertEquals("If fails here, there is rights trouble", e.getMessage());
        }
        connectortoDB.CloseConnection();
    }

    @Test
    public void UpdateTest()
    {
        ConnectorToDB connectortoDB;
        connectortoDB = new ConnectorToDB();
        boolean OpenConnectionValue = connectortoDB.OpenConnection();
        assertEquals(true,OpenConnectionValue);
        try {
            Statement st = connectortoDB.getConnection().createStatement();
            st.execute( "UPDATE `ideaservice`.`status`" +
                    "SET    `status` = 'Ready'" +
                    "WHERE  `statusPK` = 'Not Ready';");
        }
        catch (SQLException e) {
            assertEquals("Fails if here, either pass", e.getMessage());
        }
        connectortoDB.CloseConnection();
    }

    @Test
    public void DeleteTest()
    {
        ConnectorToDB connectortoDB;
        connectortoDB = new ConnectorToDB();
        boolean OpenConnectionValue = connectortoDB.OpenConnection();
        assertEquals(true,OpenConnectionValue);
        try {
            Statement st = connectortoDB.getConnection().createStatement();
            st.execute( " DELETE FROM   `ideaservice`.`status`" +
                        " WHERE          `status`='Ready';");
        }
        catch (SQLException e) {
            assertEquals("Fails if here, either pass", e.getMessage());
        }
        boolean CloseConnectionValue = connectortoDB.CloseConnection();
        assertEquals(true,CloseConnectionValue);
    }


}

