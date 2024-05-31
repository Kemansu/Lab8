package org.sc.manager;

import org.sc.data.*;

import java.sql.*;
import java.time.LocalDate;

public class DBmanager {
    public static int addFlat(Flat flat, long id) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:33011/studs", "s409352", "dcmOAv4R700WqSCh");
        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO flat (id, owner_id, name, x, y, area, number_of_rooms, kitchen_area, view, transport, name_of_house, year_of_house, number_of_flats_on_floor, creation_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        int id_of_flat;

        if (id == 0) {
            Statement statement_id = conn.createStatement();
            ResultSet resultSet = statement_id.executeQuery("SELECT nextval('flat_id_seq')");
            resultSet.next();
            id_of_flat = resultSet.getInt(1);
        } else {
            id_of_flat = (int) id;
        }


        pstmt.setInt(1, id_of_flat);
        pstmt.setInt(2, Math.toIntExact(flat.getOwnerId()));
        pstmt.setString(3, flat.getName());
        pstmt.setDouble(4, flat.getCoordinates().getX());
        pstmt.setLong(5, flat.getCoordinates().getY());
        pstmt.setLong(6, flat.getArea());
        pstmt.setInt(7, flat.getNumberOfRooms());
        pstmt.setDouble(8, flat.getKitchenArea());
        pstmt.setString(9, flat.getView().toString());
        pstmt.setString(10, flat.getTransport().toString());
        pstmt.setString(11, flat.getHouse().getName());
        pstmt.setInt(12, Math.toIntExact(flat.getHouse().getYear()));
        pstmt.setInt(13, flat.getHouse().getNumberOfFlatsOnFloor());
        pstmt.setString(14, String.valueOf(flat.getCreationDate()));
        pstmt.executeUpdate();
        pstmt.close();
        conn.close();

        return id_of_flat;
    }

    public static void addUser(String username, String password) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:33011/studs", "s409352", "dcmOAv4R700WqSCh");
        PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO users(login, password) VALUES (?, ?)");
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        conn.close();
    }

    public static void ReadDB() throws SQLException{
        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:33011/studs", "s409352", "dcmOAv4R700WqSCh");
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM flat");
        while (resultSet.next()){
            Flat flat = new Flat(resultSet.getLong("id"));
            flat.setOwnerId(resultSet.getLong("owner_id"));
            flat.setName(resultSet.getString("name"));

            Coordinates coordinates = new Coordinates(0, 0);
            coordinates.setX(resultSet.getDouble("x"));
            coordinates.setY(resultSet.getLong("y"));
            flat.setCoordinates(coordinates);

            flat.setArea(resultSet.getLong("area"));
            flat.setNumberOfRooms(resultSet.getInt("number_of_rooms"));
            flat.setKitchenArea(resultSet.getDouble("kitchen_area"));
            flat.setView(View.valueOf(resultSet.getString("view")));
            flat.setTransport(Transport.valueOf(resultSet.getString("transport")));
            House house = new House();
            house.setName(resultSet.getString("name_of_house"));
            house.setYear((long) resultSet.getInt("year_of_house"));
            house.setNumberOfFlatsOnFloor(resultSet.getInt("number_of_flats_on_floor"));
            flat.setHouse(house);
            flat.setCreationDate(LocalDate.parse(resultSet.getString("creation_date")));
            CollectionManager.add(flat);
        }

    }
    public static Long getUserId(String username) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:33011/studs", "s409352", "dcmOAv4R700WqSCh");
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT id FROM users WHERE login = ?");
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getLong("id");
    }
    public static void removeFlat(int id) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:33011/studs", "s409352", "dcmOAv4R700WqSCh");
        PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM flat WHERE id = ?");
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    public static void Synchronized() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:33011/studs", "s409352", "dcmOAv4R700WqSCh");
        PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM flat WHERE id = ?");

        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT id FROM flat");

        while (resultSet.next()) {
            long id_from_DB = resultSet.getInt(1);
            if (!CollectionManager.isIdExist(id_from_DB)){
                preparedStatement.setLong(1, id_from_DB);
                preparedStatement.executeUpdate();
            }
        }


    }


}
