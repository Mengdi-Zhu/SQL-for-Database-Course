import java.sql.*;
import edu.brandeis.cs127b.pa2.graphviz.*;
public class Part2 {
	static final String JDBC_DRIVER = "com.postgresql.jdbc.Driver";
	static final String DB_TYPE = "postgresql";
	static final String DB_DRIVER = "jdbc";
	static final String DB_NAME = System.getenv("PGDATABASE");
	static final String DB_HOST = System.getenv("PGHOST");
	static final String DB_URL = String.format("%s:%s://%s/%s",DB_DRIVER, DB_TYPE, DB_HOST, DB_NAME);
	static final String DB_USER = System.getenv("PGUSER");
	static final String DB_PASSWORD = System.getenv("PGPASSWORD");

        //This code is to calculate the total sales between suppliers and customers grouped by the regions where the suppliers and customers are located.
	static final String QUERY = "SELECT A1.r_regionkey, A2.r_regionkey,SUM(l_extendedprice*(1+l_tax)*(1-l_discount)) FROM lineitem, orders, customer, nation AS B1, supplier,nation AS B2, region AS A1, region AS A2 WHERE(l_orderkey = o_orderkey AND l_suppkey = s_suppkey AND o_custkey = c_custkey AND c_nationkey = B1.n_nationkey AND B1.n_regionkey = A1.r_regionkey AND s_nationkey = B2.n_nationkey AND B2.n_regionkey = A2.r_regionkey) GROUP BY A1.r_regionkey, A2.r_regionkey ORDER BY A1.r_regionkey, A2.r_regionkey";
    
	public static void main(String[] args) throws SQLException{
		DirectedGraph g = new DirectedGraph();
		try {
			Connection conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
			Statement st = conn.createStatement();
     		ResultSet rs = st.executeQuery(QUERY);
                //We create an array to store different region node.
                Node[] REGION_N = {new Node("AFRICA"), new Node("AMERICA"), new Node("ASIA"), new Node("EUROPE"), new Node("MIDDLE EAST")};
		String weight;
		while ( rs.next() ) {
			weight = "$" + rs.getInt(3)/1000000 + "M";
			Node from = REGION_N[rs.getInt(1)];
			Node to = REGION_N[rs.getInt(2)];
			DirectedEdge e = new DirectedEdge(from, to);
			e.addLabel(weight);
			g.add(e);
		}
		System.out.println(g);
	} catch (SQLException s) {
		throw s;
	}
}
}








