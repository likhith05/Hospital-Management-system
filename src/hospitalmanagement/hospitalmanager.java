package hospitalmanagement;

import java.sql.*;
import java.util.Scanner;

public class hospitalmanager {
    private static final String url="jdbc:mysql://localhost:3306/hospital";
    private static final String username="root";
    private static final String password="2002";

    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Scanner scanner=new Scanner(System.in);
        try{
            Connection connection= DriverManager.getConnection(url,username,password);
            patients patients=new patients(connection,scanner);
            doctors doctors=new doctors(connection);
            while (true){
                System.out.println("HOSPITAL MANAGEMENT");
                System.out.println("1.Add patients");
                System.out.println("2.view patients");
                System.out.println("3.View doctors");
                System.out.println("4.Book appointment");
                System.out.println("5.Exit");
                System.out.println("Enter your choice");
                int choice=scanner.nextInt();
                switch (choice){
                    case 1:
                        patients.addpatient();
                        break;

                    case 2:
                        patients.viewpatients();
                        break;

                    case 3:
                        doctors.viewdoctors();
                        break;

                    case 4:
                        bookappointment(patients,doctors,connection,scanner);
                        break;

                    case 5:
                        return;

                    default:
                        System.out.println("enter valid choise");



                }


            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void bookappointment(patients patients,doctors doctors,  Connection connection,Scanner scanner){
        System.out.println("Enter patient id:");
        int patientid=scanner.nextInt();
        System.out.println("Entr doctor id:");
        int doctorid=scanner.nextInt();
        System.out.println("Enter appointment date(yyyy-mm-dd):");
        String date=scanner.next();
        if (patients.checkpatient(patientid)&& doctors.checkdoctor(doctorid)){
            if (cheackavailability(doctorid,date,connection)){
                String appquery="insert into appointments(patient_id,doctor_id,appointment_date) values(?,?,?)";
                try{
                    PreparedStatement preparedStatement=connection.prepareStatement(appquery);
                    preparedStatement.setInt(1,patientid);
                    preparedStatement.setInt(2,doctorid);
                    preparedStatement.setString(3,date);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }else {
            System.out.println("Either doctor or patient doesn't exist");
        }




    }
    public static boolean cheackavailability(int doctorid, String date,Connection connection ){
        String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ?";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1,doctorid);
            preparedStatement.setString(2,date);
            ResultSet resultSet=preparedStatement.executeQuery();
            if (resultSet.next()){
                int count=resultSet.getInt(1);
                if (count==0){
                    return true;
                }else {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;



    }


}
