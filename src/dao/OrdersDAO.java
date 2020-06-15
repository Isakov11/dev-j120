/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.avalon.java.dev.j120.practice.entity.Order;
import ru.avalon.java.dev.j120.practice.entity.OrderStatusEnum;
import ru.avalon.java.dev.j120.practice.entity.Person;

public class OrdersDAO {
    private String url, username, password;
    private OrderedItemsDAO orderedItemsDAO;
    private Connection conn;
    
    public OrdersDAO(String url, String username, String password) {
        try {
            this.url = url;
            this.username = username;
            this.password = password;
            orderedItemsDAO = new OrderedItemsDAO();
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            Logger.getLogger(OrdersDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*public OrdersDAO(Connection conn) {
        this.conn = conn;
    }*/
    
    public Order create(Order order){
        long orderNumber=0;        
        //try (Connection conn = DriverManager.getConnection(url, username, password)){
        
            //запись шапки заказа
            String sqlStatement = "INSERT orders(orderDate, person, discount, orderStatus) VALUES (?,?,?,?)";
           try (PreparedStatement preparedStatement = conn.prepareStatement(sqlStatement,Statement.RETURN_GENERATED_KEYS)){

            preparedStatement.setDate(1, Date.valueOf(order.getOrderDate()));
            preparedStatement.setString(2, order.getContactPerson().getPhoneNumber());
            preparedStatement.setLong(3, order.getDiscount());
            preparedStatement.setString(4, order.getOrderStatus().toString());

            int rows = preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();

            if (rs.next()) {
                orderNumber = rs.getLong(1);
            }
            //запись заказанных позиций
            orderedItemsDAO.create(order, conn);
        }
           
        catch(IllegalArgumentException | SecurityException | SQLException ex){
            System.out.println("Error! In OrderDAO.create ");
            System.out.println(ex);
            return null;
        }
        return new Order(orderNumber, order.getOrderDate(), 
                order.getContactPerson(), order.getDiscount(), order.getOrderStatus(),order.getOrderList());
    }
    
    public ArrayList<Order> findAll(){
        //try (Connection conn = DriverManager.getConnection(url, username, password)){
        
        ArrayList<Order> ordersArray = new ArrayList<>();

        String sqlStatement =   "SELECT " +
                                "orderDate, " +
                                "orderNumber, " +
                                "discount, " + 
                                "orderStatus, " + 
                                "persons.name as name, " +
                                "persons.surname as surname, " +
                                "persons.address as address, " +
                                "persons.phoneNumber as phoneNumber " +
                                "FROM orders " +
                                "INNER JOIN persons ON orders.person = persons.phoneNumber";

        try (PreparedStatement preparedStatement = conn.prepareStatement(sqlStatement)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){

                long orderNumber = resultSet.getLong("orderNumber");
                LocalDate orderDate = resultSet.getDate("orderDate").toLocalDate();

                Person contactPerson = new Person(resultSet.getString("name"),
                        resultSet.getString("surname"), resultSet.getString("address"),
                        resultSet.getString("phoneNumber") );

                int discount = resultSet.getInt("discount");
                OrderStatusEnum orderStatus = OrderStatusEnum.valueOf(resultSet.getString("orderStatus"));

                Order order = new Order(orderNumber,orderDate,contactPerson,discount,orderStatus);

                order = orderedItemsDAO.findAll(order, conn);
                ordersArray.add(order);

            }
        } catch (SQLException ex) {
            System.out.println(ex);
            return new ArrayList<>();
        }
        return ordersArray;
        
    }
    
    public Order findId(long orderNumber){
        //try (Connection conn = DriverManager.getConnection(url, username, password)){
        
        Order order = null;
        String sqlStatement =   "SELECT " +
                                "orderNumber, " +
                                "orderDate, " +
                                "discount, " + 
                                "orderStatus, " + 
                                "persons.name as name, " +
                                "persons.surname as surname, " +
                                "persons.address as address, " +
                                "persons.phoneNumber as phoneNumber " +
                                "FROM orders " +
                                "INNER JOIN persons ON orders.person = persons.phoneNumber " +
                                "WHERE orderNumber = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(sqlStatement)){
            preparedStatement.setLong(1, orderNumber);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                LocalDate orderDate = resultSet.getDate("orderDate").toLocalDate();

                Person contactPerson = new Person(resultSet.getString("name"), 
                    resultSet.getString("surname"), resultSet.getString("address"), 
                    resultSet.getString("phoneNumber") );

                int discount = resultSet.getInt("discount");
                OrderStatusEnum orderStatus = OrderStatusEnum.valueOf(resultSet.getString("orderStatus"));

                order = new Order(orderNumber,orderDate,contactPerson,discount,orderStatus);
                orderedItemsDAO.findAll(order, conn);
            }
            return order;
                
        } catch (SQLException ex) {
            System.out.println("Error! In OrderDAO.findId ");
            System.out.println(ex);
            return null;
        }
    }
    
    public int update(Order order){
        //try (Connection conn = DriverManager.getConnection(url, username, password)){
        
        String sqlStatement = "UPDATE orders SET " +
                "orderDate = ?, person = ?, discount = ? , orderStatus = ? WHERE orderNumber= ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(sqlStatement)){

            preparedStatement.setDate(1, Date.valueOf(order.getOrderDate()));
            preparedStatement.setString(2, order.getContactPerson().getPhoneNumber());
            preparedStatement.setLong(3, order.getDiscount());
            preparedStatement.setString(4, order.getOrderStatus().toString());

            preparedStatement.setLong(5, order.getOrderNumber());

            int row = preparedStatement.executeUpdate();
            orderedItemsDAO.update(order, conn);
            return row;

        } catch (SQLException ex) {
            System.out.println("Error! In OrderDAO.update ");
            System.out.println(ex);
            return 0;
        }        
    }
    
    public int delete(long orderNumber){
        //try (Connection conn = DriverManager.getConnection(url, username, password)){
         
        String sqlStatement ="DELETE FROM orders WHERE orderNumber= ?";
        try(PreparedStatement preparedStatement = conn.prepareStatement(sqlStatement)){

            preparedStatement.setLong(1, orderNumber);

            int row = preparedStatement.executeUpdate();
            orderedItemsDAO.delete(orderNumber, conn);
            return row;

        } catch (SQLException ex) {
            return 0;
        }
    }
}
