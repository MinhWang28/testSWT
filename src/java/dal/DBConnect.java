/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Lenovo
 */
public class DBConnect {
    protected Connection connection;
    private final String serverName = "localhost";
    private final String dbName = "MyStore";
    private final String portNumber = "1433";
    private final String instance = "";//LEAVE THIS ONE EMPTY IF YOUR SQL IS A SINGLE INSTANCE
    private final String userID = "sa";
    private final String password = "123456";
    public DBConnect()
    {
        try {
            String url = "jdbc:sqlserver://" + serverName + ":" + portNumber + "\\" 
                + instance + ";databaseName=" + dbName
                + ";encrypt=false;integratedSecurity=false";
        if (instance == null || instance.trim().isEmpty()) {
            url = "jdbc:sqlserver://" + serverName + ":" + portNumber + ";databaseName=" + dbName
                    + ";encrypt=false;integratedSecurity=false";
        }
            System.out.println(url);
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        connection = DriverManager.getConnection(url, userID, password);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

