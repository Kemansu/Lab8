package org.sc.manager;

import org.sc.Request;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserManager {

    public static Boolean isExist(String username) throws SQLException {

        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:33011/studs", "s409352", "dcmOAv4R700WqSCh");
        PreparedStatement pstmt = conn.prepareStatement("SELECT login FROM users WHERE login = ?");
        pstmt.setString(1, username);

        return pstmt.executeQuery().next();

    }

    public static String createUser(Request request) throws NoSuchAlgorithmException, SQLException {
        String password = request.getPassword();
        String username = request.getLogin();

        MessageDigest md = MessageDigest.getInstance("SHA-384");
        byte[] hashedPassword = md.digest(password.getBytes());

        StringBuilder sb = new StringBuilder();

        for (byte b : hashedPassword) {
            sb.append(String.format("%02x", b));
        }


        if (UserManager.isExist(request.getLogin())) {
            return "Username already exists";
        } else {
            DBmanager.addUser(username, sb.toString());
            return "User created successfully";
        }

    }
    public static Boolean isAthCorrect(String username, String password) throws SQLException, NoSuchAlgorithmException {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:33011/studs", "s409352", "dcmOAv4R700WqSCh");
        PreparedStatement pstmt = connection.prepareStatement("SELECT login, password FROM users WHERE login = ? AND password = ?");
        pstmt.setString(1, username);

        MessageDigest md = MessageDigest.getInstance("SHA-384");
        byte[] hashedPassword = md.digest(password.getBytes());

        StringBuilder sb = new StringBuilder();

        for (byte b : hashedPassword) {
            sb.append(String.format("%02x", b));
        }

        pstmt.setString(2, sb.toString());

        return pstmt.executeQuery().next();

    }

}
