package hospitalmanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class patients {
    private Connection connection;
    private Scanner scanner;
    public patients(Connection connection,Scanner scanner){
        this.connection=connection;
        this.scanner=scanner;

    }

    public void  addpatient(){
        System.out.println("Enter patient name: ");
        String name=scanner.next();
        System.out.println("Enter patient age: ");
        int age=scanner.nextInt();
        System.out.println("Enter patient gender: ");
        String gender=scanner.next();

        try{
            String query="insert into patients(name,age,gender)values(?,?,?)";
            PreparedStatement preparedstatement=connection.prepareStatement(query);
            preparedstatement.setString(1,name);
            preparedstatement.setInt(2,age);
            preparedstatement.setString(3,gender);
            int rowseffected=preparedstatement.executeUpdate();
            if (rowseffected>0){
                System.out.println("patient added successfully");
            }else {
                System.out.println("failed to add patient");
            }


        }catch (SQLException e){
            e.printStackTrace();

        }

    }

    public void viewpatients(){
        try {
            String query = "select*from patients";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs=preparedStatement.executeQuery();
            System.out.println("++=======================================+");
            while (rs.next()){
                int id=rs.getInt("id");
                String name= rs.getString("name");
                int age= rs.getInt("age");
                String gender=rs.getString("gender");
                System.out.println("id:"+id);
                System.out.println("name:"+name);
                System.out.println("age:"+age);
                System.out.println("gender:"+gender);
                System.out.println("+=========");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean  checkpatient(int id){
        try {
            String query="select*from patients where id=?";
            PreparedStatement preparedStatement=connection.prepareStatement(query);
           preparedStatement.setInt(1,id);
            ResultSet rs= preparedStatement.executeQuery();
            if (rs.next()){
                return true;

            }else {
                return false;
            }



        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;


    }
}
