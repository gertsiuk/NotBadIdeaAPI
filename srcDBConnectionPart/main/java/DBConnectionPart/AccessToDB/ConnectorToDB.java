package DBConnectionPart.AccessToDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Nikolia on 29.11.2016.
 */
public class ConnectorToDB {


    /* Connection and it's data*/
    Connection connection = null;
    String ConnectionData;

    public ConnectorToDB()
    {
        ConnectionData= "jdbc:mysql://"+ ServerDataConnection.ip+
                ":"+ServerDataConnection.port+
                "/"+ServerDataConnection.database+"?" +
                "user="+ServerDataConnection.username+
                "&password="+ServerDataConnection.password+"";
    }


    // connects to DB and returns state of connection
    public boolean OpenConnection()
    {
        try {
            connection = DriverManager.getConnection(ConnectionData);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    public Connection getConnection()
    {
        return connection;
    }

    // Close connection and return state of action
    public boolean CloseConnection()
    {
        try {
            connection.close();
            return true;
        }
        catch (SQLException ex) {
            return false;
        }
    }
}
