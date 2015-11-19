package entity;


import entity.statments.SingleEntityStatementFactory;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.*;

/**
 * Created by kujtimh on 15-11-05.
 */

public class Driver {
    public static void main(String[] args) throws PropertyVetoException, SQLException, IOException {
//        Connection connection = null;
        PreparedStatement statement = null;
//        Refzonetype refzonetype=new Refzonetype();
//        refzonetype.setName("Test ref123");
//        refzonetype.setActive((byte)1);
//        refzonetype.setFlag((byte)0);
//        refzonetype.setUniqueId("81b30d6d-160e-4b91-8d28-33c4fc71ab3d");
//        java.util.Date date= new java.util.Date();
//        refzonetype.setDateInserted(new Timestamp(date.getTime()));
//        statement=new SingleEntityStatementFactory().addEntityStatement(Refzonetype.class,refzonetype);
//        try{
//           int resultSet= statement.executeUpdate();
//            System.out.println(resultSet);
//
//        } catch (SQLException e) {
//
//            System.out.println(e.getMessage());
//
//        } finally {
//
//            if (statement != null) {
//                statement.close();
//            }
//        }

//        while (resultSet.next()) {
//                Timestamp timestamp= (Timestamp) resultSet.getObject("DateInserted");
//                System.out.println(timestamp);
//
//                System.out.println("Id: " + resultSet.getString("Id"));
//                System.out.println("Name: " + resultSet.getString("Name"));
//            }
//        try {
//            connection = DataSource.getInstance().getConnection();
//            statement = connection.prepareStatement("select * from refzonetype WHERE UniqueId=?");
//            statement.setString(1,"f7f18a86-6b9f-4898-bb78-33618046ce98");
//            resultSet = statement.executeQuery();
//            while (resultSet.next()) {
//                Timestamp timestamp= (Timestamp) resultSet.getObject("DateInserted");
//                System.out.println(timestamp);
//
//                System.out.println("Id: " + resultSet.getString("Id"));
//                System.out.println("Name: " + resultSet.getString("Name"));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            if (resultSet != null) try { resultSet.close();} catch (SQLException e) {e.printStackTrace();}
//            if (statement != null) try { statement.close();} catch (SQLException e) {e.printStackTrace();}
//            if (connection != null) try { connection.close();} catch (SQLException e) {e.printStackTrace();}
//        }
        //f7f18a86-6b9f-4898-bb78-33618046ce98
        //new SingleEntityStatementFactory().updateEntityStatement(Refzonetype.class,new Refzonetype());
//        new SingleEntityStatementFactory().getEntityStatement(String.class,"Hello");
//        new SingleEntityStatementFactory().addEntitiesStatement(Integer.class,54);
    }
}
