/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;

public class CachedConnectionServlet extends HttpServlet {

  public void doGet(
   HttpServletRequest request, HttpServletResponse response, String lines[]) 
   throws IOException, ServletException {

    response.setContentType("text/html");
    PrintWriter out = response.getWriter(  );
    out.println("<html>");
    out.println("<head>");
    out.println("<title>Cached Connection Servlet</title>");
    out.println("</head>");
    out.println("<body>");

    // Turn on verbose output
    CacheConnection.setVerbose(true);

    // Get a cached connection
    Connection connection = CacheConnection.checkOut(  );
  
    Statement  statement  = null;
    ResultSet  resultSet  = null;
    String     userName   = null;  
    int i=0;
    try { 
      // Test the connection
      statement = connection.createStatement(  );
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");

        
      for(String line : lines){
            String[] csv=line.split(",");
            if(csv[0].equals("Region")){continue;}
            i = statement.executeUpdate("INSERT INTO \"Datadb\"(region, country, item_type, sales_channel, order_priority, order_date, order_id, ship_date, units_sold, unit_price, unit_cost, total_revenue, total_cost, total_profit) VALUES ('"+csv[0].trim()+"','"+ csv[1].trim()+"','"+ csv[2].trim()+"','" + csv[3].trim() +"','"+csv[4].trim()+"','" + csv[5].trim() +"','"+Integer.parseInt(csv[6].trim())+"','" +csv[7].trim()+"','" +Integer.parseInt(csv[8].trim())+"','" +Double.parseDouble(csv[9].trim())+"','" +Double.parseDouble(csv[10].trim())+"','" +Double.parseDouble(csv[11].trim())+"','" +Double.parseDouble(csv[12].trim())+"','" +Double.parseDouble(csv[13].trim())+"');");
        }
      if (i!=0)
       userName = "did it!!!";
    }
    catch (SQLException e) {
      out.println("DedicatedConnection.doGet(  ) SQLException: " + 
       e.getMessage(  ) + "<p>");
    }
    finally {
      if (resultSet != null) 
        try { resultSet.close(  ); } catch (SQLException ignore) { }
      if (statement != null) 
        try { statement.close(  ); } catch (SQLException ignore) { }
    }

    // Return the conection
    CacheConnection.checkIn(connection);

    out.println("Hello " + userName + "!<p>");
    out.println("You're using a cached connection!<p>");
    out.println("</body>");
    out.println("</html>");
  }

    /**
     *
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    @Override
  public void doPost(
   HttpServletRequest request, HttpServletResponse response)
   throws IOException, ServletException {
      String content = request.getParameter("content");
      String []lines = content.split("\\r?\\n");
      
    doGet(request, response, lines);
  }
}