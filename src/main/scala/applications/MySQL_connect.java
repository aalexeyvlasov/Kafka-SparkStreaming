package applications;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;


public class MySQL_connect {

    //Переменные для соединения с БД
    //URL = Oracle: "jdbc:oracle://localhost:3306/traffic_limits"; //копирование из properties (правая кнопка мыши на бд/properties) бд
    private static final String URL = "jdbc:mysql://localhost:3306/traffic_limits" +
            "?useSSL=false" +                       //SSL
            "&useUnicode=true" +                    //Unicode
            "&useJDBCCompliantTimezoneShift=true" + //timezone
            "&useLegacyDatetimeCode=false" +
            "&serverTimezone=UTC";
            //https://dev.mysql.com/doc/connector-j/5.1/en/connector-j-reference-configuration-properties.html

    private  static final String USERNAME = "root";
    private  static final String PASSWORD = "admin";




    public static void main(String args[]){



        try{

            //Загрузка JDBC //Загрузка прописана в build.sbt: "mysql-connector-java"
            Class.forName("com.mysql.jdbc.Driver");

            Connection my_con = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            if (!my_con.isClosed()) {
                System.out.println("Соединение с БД установлено\n");
            }

            //Обновление таблицы
            //Добавить/Удалить строку AVG
            System.out.println("Добавить строку AVG  - 1, Не добавлять(удалить) - 0: \n");
            Scanner scan_addel = new Scanner(System.in);
            Integer addel = scan_addel.nextInt();

            Statement stmt = my_con.createStatement();

            if (addel == 1){
                stmt.execute("REPLACE INTO traffic_limits.limits_per_hour VALUES ('AVG', 100501, '2015-10-19');");

            }
            else if (addel == 0){
                stmt.execute("delete from traffic_limits.limits_per_hour WHERE limit_name = 'AVG';");

            }
            else  {
                System.out.println("Неправильный ввод");

            }



            //ResultSet - для вывода информации из бд
            ResultSet rs = stmt.executeQuery("select * from traffic_limits.limits_per_hour;");
            System.out.println("\nlimits_per_hour: ");

            while(rs.next())

                System.out.println(rs.getString(1)+"  "+rs.getInt(2)+"  "+rs.getString(3));

            my_con.close();

            if (my_con.isClosed()) {
                System.out.println("\nСоединение с БД Закрыто");
            }


        }catch(Exception e){
            System.out.println(e);
            System.err.println("\nОшибка: соединение с БД не установлено");
        }


    }
}