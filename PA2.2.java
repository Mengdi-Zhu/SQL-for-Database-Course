import java.sql.*;
import java.util.*;
import edu.brandeis.cs127b.pa2.gnuplot.*;
public class Part1 {
	static final String JDBC_DRIVER = "com.postgresql.jdbc.Driver";
	static final String DB_TYPE = "postgresql";
	static final String DB_DRIVER = "jdbc";
	static final String DB_NAME = System.getenv("PGDATABASE");
	static final String DB_HOST = System.getenv("PGHOST");
	static final String DB_URL = String.format("%s:%s://%s/%s",DB_DRIVER, DB_TYPE, DB_HOST, DB_NAME);
	static final String DB_USER = System.getenv("PGUSER");
	static final String DB_PASSWORD = System.getenv("PGPASSWORD");
	static Connection conn;

    //This code is for five queries about select of data of total sales.
    static final String QUERY1 = "SELECT EXTRACT(YEAR from l_shipdate),EXTRACT(MONTH from l_shipdate), SUM(l_extendedprice *(1 + l_tax) *(1-  l_discount)) FROM lineitem, supplier,nation,region WHERE (l_suppkey = s_suppkey AND s_nationkey = n_nationkey AND n_regionkey = r_regionkey AND r_regionkey = 0) GROUP BY EXTRACT(YEAR from l_shipdate), EXTRACT(MONTH from l_shipdate) ORDER BY EXTRACT(YEAR from l_shipdate), EXTRACT(MONTH from l_shipdate)";

    static final String QUERY2 = "SELECT EXTRACT(YEAR from l_shipdate),EXTRACT(MONTH from l_shipdate), SUM(l_extendedprice *(1 + l_tax) *(1-  l_discount)) FROM lineitem, supplier,nation,region WHERE (l_suppkey = s_suppkey AND s_nationkey = n_nationkey AND n_regionkey = r_regionkey AND r_regionkey = 1) GROUP BY EXTRACT(YEAR from l_shipdate), EXTRACT(MONTH from l_shipdate) ORDER BY  EXTRACT(YEAR from l_shipdate), EXTRACT(MONTH from l_shipdate)";
   
    static final String QUERY3 = "SELECT EXTRACT(YEAR from l_shipdate),EXTRACT(MONTH from l_shipdate), SUM(l_extendedprice *(1 + l_tax) *(1-  l_discount)) FROM lineitem, supplier,nation,region WHERE (l_suppkey = s_suppkey AND s_nationkey = n_nationkey AND n_regionkey = r_regionkey AND r_regionkey = 2) GROUP BY EXTRACT(YEAR from l_shipdate), EXTRACT(MONTH from l_shipdate) ORDER BY  EXTRACT(YEAR from l_shipdate), EXTRACT(MONTH from l_shipdate)";
   
    static final String QUERY4 = "SELECT EXTRACT(YEAR from l_shipdate),EXTRACT(MONTH from l_shipdate), SUM(l_extendedprice *(1 + l_tax) *(1-  l_discount)) FROM lineitem, supplier,nation,region WHERE (l_suppkey = s_suppkey AND s_nationkey = n_nationkey AND n_regionkey = r_regionkey AND r_regionkey = 3) GROUP BY EXTRACT(YEAR from l_shipdate), EXTRACT(MONTH from l_shipdate) ORDER BY  EXTRACT(YEAR from l_shipdate), EXTRACT(MONTH from l_shipdate)";
   
    static final String QUERY5 = "SELECT EXTRACT(YEAR from l_shipdate),EXTRACT(MONTH from l_shipdate), SUM(l_extendedprice *(1 + l_tax) *(1-  l_discount)) FROM lineitem, supplier,nation,region WHERE (l_suppkey = s_suppkey AND s_nationkey = n_nationkey AND n_regionkey = r_regionkey AND r_regionkey = 4) GROUP BY EXTRACT(YEAR from l_shipdate), EXTRACT(MONTH from l_shipdate) ORDER BY  EXTRACT(YEAR from l_shipdate), EXTRACT(MONTH from l_shipdate)";

    //We create the graph's title,xname and yname parameters.
    public static void main(String[] args) throws SQLException {
    conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
    final String title = "Monthly TPC-H Order Sales Total by region";
    final String xlabel = "Year";
    final String ylabel = "Order Total(Thousands)";

    //This code if to create five corresponding statements.
    TimeSeriesPlot plot = new TimeSeriesPlot(title, xlabel, ylabel);
    Statement st1 = conn.createStatement();
    Statement st2 = conn.createStatement();
    Statement st3 = conn.createStatement();
    Statement st4 = conn.createStatement();
    Statement st5 = conn.createStatement();

    //We put the result into ResultSet.
    ResultSet rs1 = st1.executeQuery(QUERY1);
    ResultSet rs2 = st2.executeQuery(QUERY2);
    ResultSet rs3 = st3.executeQuery(QUERY3);
    ResultSet rs4 = st4.executeQuery(QUERY4);
    ResultSet rs5 = st5.executeQuery(QUERY5);

    //We set up five DateLine object of five regions.
    DateLine africa = new DateLine("AFRICA");
    DateLine america = new DateLine("AMERICA");
    DateLine asia = new DateLine("ASIA");
    DateLine europe = new DateLine("EUROPE");
    DateLine middleeast = new DateLine("MIDDLE EAST");

    //Use calender to define variables.    
    while ( rs1.next() ) {
            Calendar cal_Calendar = new GregorianCalendar(rs1.getInt(1),rs1.getInt(2),10);
            java.util.Date cal_Date = cal_Calendar.getTime();
            africa.add(new DatePoint(cal_Date, rs1.getDouble(3)/1000));
    }
    plot.add(africa);


    while ( rs2.next() ) {
            Calendar cal_Calendar = new GregorianCalendar(rs2.getInt(1),rs2.getInt(2),10);
            java.util.Date cal_Date = cal_Calendar.getTime();
            america.add(new DatePoint(cal_Date, rs2.getDouble(3)/1000));
    }
    plot.add(america);

    while ( rs3.next() ) {
            Calendar cal_Calendar = new GregorianCalendar(rs3.getInt(1),rs3.getInt(2),10);
            java.util.Date cal_Date = cal_Calendar.getTime();
            asia.add(new DatePoint(cal_Date, rs3.getDouble(3)/1000));
    }
    plot.add(asia);

    while ( rs4.next() ) {
            Calendar cal_Calendar = new GregorianCalendar(rs4.getInt(1),rs4.getInt(2),10);
            java.util.Date cal_Date = cal_Calendar.getTime();
            europe.add(new DatePoint(cal_Date, rs4.getDouble(3)/1000));
    }
    plot.add(europe);

    while ( rs5.next() ) {
            Calendar cal_Calendar = new GregorianCalendar(rs5.getInt(1),rs5.getInt(2),10);
            java.util.Date cal_Date = cal_Calendar.getTime();
            middleeast.add(new DatePoint(cal_Date, rs5.getDouble(3)/1000));
    }
    plot.add(middleeast);

    System.out.println(plot);
	}
}
