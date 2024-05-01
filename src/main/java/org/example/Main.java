package org.example;

import java.sql.*;
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

    public static void insertarEmpleado(Statement sentencia) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.println("Introduce el DNI del empleado:");
            String dni = sc.nextLine();

            System.out.println("Introduce el nombre del empleado:");
            String nombre = sc.nextLine();

            System.out.println("Introduce los estudios del empleado:");
            String estudios = sc.nextLine();

            System.out.println("Introduce la dirección del empleado:");
            String direccion = sc.nextLine();

            System.out.println("Introduce la fecha de alta del empleado (YYYY-MM-DD):");
            String fecha_alta = sc.nextLine();

            System.out.println("Introduce el salario del empleado:");
            int salario = sc.nextInt();

            System.out.println("Introduce la comisión del empleado:");
            int comision = sc.nextInt();

            System.out.println("Introduce el número del departamento del empleado:");
            String num_departamento = sc.next();

            // Verificar si el departamento existe
            ResultSet rs = sentencia.executeQuery("SELECT * FROM Departamentos WHERE Nu_dept = '" + num_departamento + "'");
            if (!rs.next()) {
                System.out.println("El departamento especificado no existe. Por favor, inserta un número de departamento válido.");
                return;
            }

            // Insertar el empleado
            String query = "INSERT INTO Empleados (Dni, Nombre, Estudios, Dir, Fecha_alt, Salario, Comision, Nu_dept) VALUES ('" + dni + "', '" + nombre + "', '" + estudios + "', '" + direccion + "', '" + fecha_alta + "', " + salario + ", " + comision + ", '" + num_departamento + "')";
            sentencia.executeUpdate(query);

            System.out.println("Empleado insertado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al insertar el empleado: " + e.getMessage());
        } finally {
            sc.close();
        }
    }

    public static void borrarEmpleado(Statement sentencia) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.println("Introduce el DNI del empleado que deseas borrar:");
            String dni = sc.nextLine();

            // Verificar si el empleado existe
            ResultSet rs = sentencia.executeQuery("SELECT * FROM Empleados WHERE Dni = '" + dni + "'");
            if (!rs.next()) {
                System.out.println("El empleado con el DNI especificado no existe.");
                return;
            }

            // Borrar el empleado
            String query = "DELETE FROM Empleados WHERE Dni = '" + dni + "'";
            sentencia.executeUpdate(query);

            System.out.println("Empleado borrado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al borrar el empleado: " + e.getMessage());
        } finally {
            sc.close();
        }
    }


}