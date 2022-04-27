

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mysql.jdbc.Driver;
/**
 * Servlet implementation class Books
 */
public class Books extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Books() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String user_name = request.getParameter("user");
		Connection con;
		
		out.println("<div class=\"table-title\"><h2>Books Owned by User</h2></div>");
		out.println("<table class=\"table-fill\">\n"
				+ "        <thead>\n"
				+ "			<tr>\n"
				+ "				<th class=\"text-left\">Title</th>\n"
				+ "				<th class=\"text-left\">Author</th>\n"
				+ "				<th class=\"text-left\">Published</th>\n"
				+ "			</tr>\n"
				+ "		</thead>\n"
				+ "		<tbody class=\"table-hover\">");
		
		try { 
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/week_four","user","password");
			ResultSet user_rs = con.createStatement().executeQuery("SELECT * FROM user WHERE name = '" + user_name + "'");
			
			while(user_rs.next()) {
				int user_id = user_rs.getInt("id");
				ResultSet book_rs = con.createStatement().executeQuery("SELECT * FROM book WHERE owner = " + user_id);
				while(book_rs.next()) {
					String title = book_rs.getString("title");
					String author = book_rs.getString("author");
					String published = book_rs.getString("published");
					out.print("<tr><td class='text-left'>" + title + "</td>\n");
					out.print("<td class='text-left'>" + author + "</td>\n");
					out.print("<td class='text-left'>" + published + "</td></td>\n");
				}
			}
			out.println("</tbody></table>");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
