/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;

import org.json.JSONObject;

/**
 *
 * @author kittu333
 */
public class Viewservlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Viewservlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Viewservlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        String l = request.getParameter("l");
        String u = request.getParameter("u");
        System.out.println(u);
        PrintWriter out = response.getWriter(  );
        CacheConnection.setVerbose(true);
        // Get a cached connection
        Connection connection = CacheConnection.checkOut(  );
        Statement  statement  = null;
        ResultSet  resultSet  = null;
        JSONArray result = null;
        try {
            // Test the connection
            statement = connection.createStatement(  );
            
            resultSet = statement.executeQuery("SELECT * from \"Datadb\" WHERE order_date BETWEEN \'"+l+"\' AND \'"+u+"\';");
            result =  convertToJSONArray(resultSet);
        }
        catch (SQLException e) {
            out.println("DedicatedConnection.doGet(  ) SQLException: " +
                    e.getMessage(  ) + "<p>");
        }   catch (Exception ex) {
            Logger.getLogger(Viewservlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            if (resultSet != null)
                try { resultSet.close(  ); } catch (SQLException ignore) { }
            if (statement != null)
                try { statement.close(  ); } catch (SQLException ignore) { }
        }
        // Return the conection
        CacheConnection.checkIn(connection);
        out.println(result);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
            CacheConnection.setVerbose(true);
     PrintWriter out = response.getWriter(  );
    // Get a cached connection
    Connection connection = CacheConnection.checkOut(  );
  
    Statement  statement  = null;
    ResultSet  resultSet  = null;
    try { 
      // Test the connection
      statement = connection.createStatement(  );
      resultSet = statement.executeQuery("TRUNCATE TABLE \"Datadb\";");
    }
    catch (SQLException e) {
      out.println("DedicatedConnection.doGet(  ) SQLException: " + 
       e.getMessage(  ) + "<p>");
    }   catch (Exception ex) {
            Logger.getLogger(Viewservlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    
public static JSONArray convertToJSONArray(ResultSet resultSet)
        throws Exception {
    JSONArray jsonArray = new JSONArray();
    while (resultSet.next()) {
  JSONObject obj = new JSONObject();
        int total_rows = resultSet.getMetaData().getColumnCount();
        for (int i = 0; i < total_rows; i++) {
            obj.put(resultSet.getMetaData().getColumnLabel(i + 1)
                    .toLowerCase(), resultSet.getObject(i + 1));

        }
jsonArray.put(obj);
    }
    return jsonArray;}

  
}
