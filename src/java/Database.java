/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kittu333
 */
import java.sql.*;
import java.util.*;

public class Database {
  private static boolean verbose  = false;

  public static final Connection getConnection(String baseName) {
    Connection conn = null;
    String driver   = null;
    String url      = null;
    String username = null;
    String password = null;
    try {
      
      driver              = "org.postgresql.Driver";
      url                 = "jdbc:postgresql://localhost:5432/email_admin";
      username            = "postgres";
      password            = "login123";
      Class.forName(driver);
    }
    catch(MissingResourceException e) {
      System.err.println("Missing Resource: " + e.getMessage(  ));
      return conn;
    }
    catch(ClassNotFoundException e) {
      System.err.println("Class not found: " + e.getMessage(  ));
      return conn;
    }
    try {
      if (verbose) {
        System.out.println("baseName=" + baseName);
        System.out.println("driver=" + driver);
        System.out.println("url=" + url);
        System.out.println("username=" + username);
        System.out.println("password=" + password);
      }

      conn = DriverManager.getConnection(url, username, password);
    }
    catch(SQLException e) {
      System.err.println(e.getMessage(  ));
      System.err.println("in Database.getConnection");
      System.err.println("on getConnection");
      conn = null;
    }
    finally {
      return conn;
    }
  }

  public static void setVerbose(boolean v) {
    verbose = v;
  }
}