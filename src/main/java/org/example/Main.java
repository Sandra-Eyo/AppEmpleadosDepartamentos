package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Statement sentencia = null;
        Connection conexion = null;


        int op = 0;

        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String url = "jdbc:mariadb://localhost:3306/?user=root&password=";
        try {
            conexion = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println("No hay ningún driver que reconozca la URL especificada");
        } catch (Exception e) {
            System.out.println("Se ha producido algún otro error");
        }

        try {
            sentencia = conexion.createStatement();
        } catch (SQLException e) {
            System.out.println("Error");
        }

        CreacionBD.crearBase(sentencia);

        do {
            System.out.println("**** MENU ****\n"
                    + "[1] Insertar un nuevo departamento"
                    + "[2] Insertar un nuevo empleado"
                    + "[3] Borrar empleado"
                    + "[4] Borrar departamento"
                    + "[5] Consultar empleados de un departamento"
                    + "[6] Consultar nombre de un departamento y localidad con el dni de un empleado"
                    + "[7] Modificar salario o comisión de un empleado con su DNI"
                    + "[8] Modificar nombre departamento a través de su localidad"
                    + "[9] Salir");
            op = sc.nextInt();
            sc.nextLine();

            switch (op) {
                case 1:
                    insertarDepartamento(sentencia);
                    break;
                case 2:
                    (sentencia);
                    break;
                case 3:
                    (sentencia);
                    break;
                case 4:
                    (sentencia);
                    break;
                case 5:
                    (sentencia);
                    break;
                case 6:
                    (sentencia);
                    break;
                case 7:
                    (sentencia);
                    break;
                case 8:
                    (sentencia);
                    break;
            }

        } while (op != 9);

    }

    public static void insertarDepartamento(Statement sentencia) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.println("Introduce el nombre del departamento:");
            String nombre = sc.nextLine();

            System.out.println("Introduce la localidad del departamento:");
            String localidad = sc.nextLine();

            String query = "INSERT INTO Departamentos (Dnombre, Localidad) VALUES ('" + nombre + "', '" + localidad + "')";
            sentencia.executeUpdate(query);

            System.out.println("Departamento insertado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al insertar el departamento: " + e.getMessage());
        } finally {
            sc.close();
        }
    }

}