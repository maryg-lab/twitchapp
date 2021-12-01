package lab.maryg;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import javax.inject.Inject;
import javax.sql.DataSource;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@RequestScoped
public class RestHelloWorld {

    @Resource(name = "jdbc/twitchDS")
    DataSource ds1;

    /*    @GET
        @Produces(MediaType.APPLICATION_JSON)
        public String getTestString()  {
            return "test string";
        }
    */

    @GET
    @Path("Restful")
    @Produces(MediaType.APPLICATION_JSON)
    public String getDBString() throws SQLException {

        Connection con = null;
        String retVal = new String();

        try {
            con = ds1.getConnection();
            Statement stmt = null;
            stmt = con.createStatement();
            // create a table
            stmt.executeUpdate("create table cities (name varchar(50) not null primary key, population int, county varchar(30))");
            // insert a test record
            stmt.executeUpdate("insert into cities values ('myHomeCity', 106769, 'myHomeCounty')");
            // select a record
            ResultSet result = stmt.executeQuery("select county from cities where name='myHomeCity'");
            result.next();
            // display the county information for the city.
            //out.println("The county for myHomeCity is " + result.getString(1));
            retVal = result.getString(1);
            // drop the table to clean up and to be able to rerun the test.
            stmt.executeUpdate("drop table cities");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return retVal;
    }

}
