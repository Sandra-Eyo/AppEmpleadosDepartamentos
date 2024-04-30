package org.example;

import java.sql.SQLException;
import java.sql.Statement;

public class CreacionBD {

    public static void crearBase(Statement sentencia)
    {
        try
        {
            //String con el nombre de la base de datos:
            String bd = "AppEmpleadosDepartamentos";

            //Sentencias de crear si no existe y usar la bd creada:
            sentencia.execute("CREATE DATABASE IF NOT EXISTS " + bd + ";");
            sentencia.execute("USE "+ bd +";");

            // Creación de la tabla Empleados:
            sentencia.execute("CREATE TABLE IF NOT EXISTS Empleados ("
                    + "Dni VARCHAR(9) PRIMARY KEY,"
                    + "Nombre VARCHAR(10),"
                    + "Estudios VARCHAR(10),"
                    + "Dir VARCHAR(10),"
                    + "Fecha_alt DATE,"
                    + "Salario INT,"
                    + "Comision INT,"
                    + "Nu_dept VARCHAR(2),"
                    + "FOREIGN KEY (Nu_dept) REFERENCES Departamentos(Nu_dept))");

            // Creación de la tabla Departamentos:
            sentencia.execute("CREATE TABLE IF NOT EXISTS Departamentos ("
                    + "Nu_dept INT AUTO_INCREMENT PRIMARY KEY,"
                    + "Dnombre VARCHAR(10) UNIQUE,"
                    + "Localidad VARCHAR(10))");
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
    }
}

