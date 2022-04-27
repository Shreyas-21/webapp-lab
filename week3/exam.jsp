<%@page import="java.sql.*,java.io.*,java.util.*"  %>  
<html>
	<head>
		<title>Exam Results</title>
		<link href="style.css" rel="stylesheet" type="text/css"/>
	</head>
	<body>
		<h2>Results</h2>
		<table>
			<tr>
				<th>Name</th>
				<th>Roll Number</th>
				<th>Marks</th>
			</tr>
			<% 
				Connection con;
				try  {
					String name = request.getParameter("name");
					String rollno = request.getParameter("rollno");
					HashMap<String, String> answers = new HashMap<String, String>();
					
					answers.put("q1", "o2");
					answers.put("q2", "o2");
					answers.put("q3", "o3");
					answers.put("q4", "o4");
					answers.put("q5", "o4");
					
					Integer score = 0;
					for (Integer i = 1; i <= 5; ++i ) {
						String qn = "q" + i.toString();
						String ans = answers.get(qn);
						if (ans.equals(request.getParameter(qn)))
							score += 5;
					}
					
					Class.forName("com.mysql.jdbc.Driver"); 
					con=DriverManager.getConnection("jdbc:mysql://localhost:3306/week_three","user","password"); 
					con.createStatement().executeUpdate("UPDATE students SET marks = " + score.toString() + " WHERE rollno = " + rollno);
					
					ResultSet rs = con.createStatement().executeQuery("SELECT * FROM students WHERE rollno = " + rollno);
					while(rs.next()) {
						out.print("<tr>");
						out.print("<td>" + rs.getString("name") + "</td>");
						out.print("<td>" + rs.getInt("rollno") + "</td>");
						out.print("<td>" + rs.getInt("marks") + "</td>");
						out.print("</tr>");
					}
				} catch(Exception e) { 
					e.printStackTrace(); 
				} 
			%>
		</table>
	</body>
</html> 
