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
                    + "[1] Insertar un nuevo departamento\n"
                    + "[2] Insertar un nuevo empleado\n"
                    + "[3] Borrar empleado\n"
                    + "[4] Borrar departamento\n"
                    + "[5] Consultar empleados de un departamento\n"
                    + "[6] Consultar nombre de un departamento y localidad con el dni de un empleado\n"
                    + "[7] Modificar salario o comisión de un empleado con su DNI\n"
                    + "[8] Modificar nombre departamento a través de su localidad\n"
                    + "[9] Salir");
            op = sc.nextInt();
            sc.nextLine();

            switch (op) {
                case 1:
                    insertarDepartamento(sentencia);
                    break;
                case 2:
                    insertarEmpleado(sentencia);
                    break;
                case 3:
                    borrarEmpleado(sentencia);
                    break;
                case 4:
                    borrarDepartamento(sentencia);
                    break;
                case 5:
                    consultarEmpleadosDepartamento(sentencia);
                    break;
                case 6:
                    consultarDepartamentoLocalidadEmpleado(sentencia);
                    break;
                case 7:
                    modificarSalarioComisionEmpleado(sentencia);
                    break;
                case 8:
                    modificarLocalidadDepartamento(sentencia);
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

    public static void borrarDepartamento(Statement sentencia) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.println("Introduce el número del departamento que deseas borrar:");
            int num_departamento = sc.nextInt();

            // Verificar si el departamento existe
            ResultSet rs = sentencia.executeQuery("SELECT * FROM Departamentos WHERE Nu_dept = " + num_departamento);
            if (!rs.next()) {
                System.out.println("El departamento especificado no existe.");
                return;
            }

            // Borrar los empleados relacionados con el departamento
            String queryBorrarEmpleados = "DELETE FROM Empleados WHERE Nu_dept = " + num_departamento;
            sentencia.executeUpdate(queryBorrarEmpleados);

            // Borrar el departamento
            String queryBorrarDepartamento = "DELETE FROM Departamentos WHERE Nu_dept = " + num_departamento;
            sentencia.executeUpdate(queryBorrarDepartamento);

            System.out.println("Departamento y empleados relacionados borrados correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al borrar el departamento: " + e.getMessage());
        } finally {
            sc.close();
        }
    }

    public static void consultarEmpleadosDepartamento(Statement sentencia) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.println("Introduce el nombre del departamento para ver los datos de los empleados:");
            String nombre_departamento = sc.nextLine();

            // Obtener el número de departamento basado en el nombre
            ResultSet rsNumeroDepartamento = sentencia.executeQuery("SELECT Nu_dept FROM Departamentos WHERE Dnombre = '" + nombre_departamento + "'");
            if (!rsNumeroDepartamento.next()) {
                System.out.println("El departamento especificado no existe.");
                return;
            }
            int num_departamento = rsNumeroDepartamento.getInt("Nu_dept");

            // Consultar los empleados del departamento
            ResultSet rsEmpleados = sentencia.executeQuery("SELECT * FROM Empleados WHERE Nu_dept = " + num_departamento);
            if (!rsEmpleados.next()) {
                System.out.println("No hay empleados en el departamento especificado.");
                return;
            }

            // Mostrar los datos de los empleados
            System.out.println("Datos de los empleados en el departamento '" + nombre_departamento + "':");
            System.out.println("---------------------------------------------------");
            do {
                System.out.println("DNI: " + rsEmpleados.getString("Dni"));
                System.out.println("Nombre: " + rsEmpleados.getString("Nombre"));
                System.out.println("Estudios: " + rsEmpleados.getString("Estudios"));
                System.out.println("Dirección: " + rsEmpleados.getString("Dir"));
                System.out.println("Fecha de alta: " + rsEmpleados.getDate("Fecha_alt"));
                System.out.println("Salario: " + rsEmpleados.getInt("Salario"));
                System.out.println("Comisión: " + rsEmpleados.getInt("Comision"));
                System.out.println("Número de departamento: " + rsEmpleados.getString("Nu_dept"));
                System.out.println("----------------------------------------");
            } while (rsEmpleados.next());

        } catch (SQLException e) {
            System.out.println("Error al consultar los empleados del departamento: " + e.getMessage());
        } finally {
            sc.close();
        }
    }

    public static void consultarDepartamentoLocalidadEmpleado(Statement sentencia) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.println("Introduce el DNI del empleado para ver el nombre del departamento y la localidad:");
            String dni_empleado = sc.nextLine();

            // Consultar el nombre del departamento y la localidad del empleado
            ResultSet rs = sentencia.executeQuery("SELECT Dnombre, Localidad FROM Departamentos INNER JOIN Empleados ON Departamentos.Nu_dept = Empleados.Nu_dept WHERE Empleados.Dni = '" + dni_empleado + "'");
            if (!rs.next()) {
                System.out.println("No se encontró ningún empleado con el DNI especificado.");
                return;
            }

            // Mostrar el nombre del departamento y la localidad del empleado
            String nombre_departamento = rs.getString("Dnombre");
            String localidad = rs.getString("Localidad");
            System.out.println("Nombre del departamento asignado: " + nombre_departamento);
            System.out.println("Localidad del departamento asignado: " + localidad);

        } catch (SQLException e) {
            System.out.println("Error al consultar el departamento y la localidad del empleado: " + e.getMessage());
        } finally {
            sc.close();
        }
    }

    public static void modificarSalarioComisionEmpleado(Statement sentencia) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.println("Introduce el DNI del empleado para modificar su salario o comisión:");
            String dni_empleado = sc.nextLine();

            // Verificar si el empleado existe
            ResultSet rs = sentencia.executeQuery("SELECT * FROM Empleados WHERE Dni = '" + dni_empleado + "'");
            if (!rs.next()) {
                System.out.println("No se encontró ningún empleado con el DNI especificado.");
                return;
            }

            // Solicitar al usuario qué desea modificar
            System.out.println("¿Qué desea modificar?\n[1] Salario\n[2] Comisión");
            int opcion = sc.nextInt();
            sc.nextLine(); // Consumir el salto de línea

            if (opcion != 1 && opcion != 2) {
                System.out.println("Opción no válida.");
                return;
            }

            String columnaModificar = (opcion == 1) ? "Salario" : "Comision";

            System.out.println("Introduce el nuevo valor:");
            int nuevo_valor = sc.nextInt();

            // Actualizar el salario o la comisión del empleado
            String query = "UPDATE Empleados SET " + columnaModificar + " = " + nuevo_valor + " WHERE Dni = '" + dni_empleado + "'";
            sentencia.executeUpdate(query);

            System.out.println("Salario o comisión del empleado modificado correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al modificar el salario o la comisión del empleado: " + e.getMessage());
        } finally {
            sc.close();
        }
    }

    public static void modificarLocalidadDepartamento(Statement sentencia) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.println("Introduce el nombre del departamento para modificar su localidad:");
            String nombre_departamento = sc.nextLine();

            // Verificar si el departamento existe
            ResultSet rs = sentencia.executeQuery("SELECT * FROM Departamentos WHERE Dnombre = '" + nombre_departamento + "'");
            if (!rs.next()) {
                System.out.println("No se encontró ningún departamento con el nombre especificado.");
                return;
            }

            // Solicitar al usuario la nueva localidad
            System.out.println("Introduce la nueva localidad:");
            String nueva_localidad = sc.nextLine();

            // Actualizar la localidad del departamento
            String query = "UPDATE Departamentos SET Localidad = '" + nueva_localidad + "' WHERE Dnombre = '" + nombre_departamento + "'";
            sentencia.executeUpdate(query);

            System.out.println("Localidad del departamento modificada correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al modificar la localidad del departamento: " + e.getMessage());
        } finally {
            sc.close();
        }
    }

}