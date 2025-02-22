package hospitalmanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class doctors {
    private Connection connection;

    public doctors(Connection connection){
        this.connection=connection;


    }



    public void viewdoctors(){
        try {
            String query = "select*from doctors";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs=preparedStatement.executeQuery();
            System.out.println("++=======================================+");
            while (rs.next()){
                int id=rs.getInt("id");
                String name= rs.getString("name");
                String specilization=rs.getString("specialization");
                System.out.println("id:"+id);
                System.out.println("name"+name);
                System.out.println("specilization"+specilization);
                System.out.println("+++=============");


            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean  checkdoctor(int id){
        try {
            String query="select*from doctors where id=?";
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
