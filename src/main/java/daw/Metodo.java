/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package daw;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tomas
 */
public class Metodo {

    public static void main(String[] args) {
        System.out.println("Existe el usuario tomas3, passwd: 73  --> " + comprobarUsuario("Tomas3", "73"));

    }

    public static final String NOMBRE_FICHERO = "usuarios.csv";

    // Método que lee el fichero y lo convierte a una lista de usuarios
    // Método para leer un fichero csv y guardarlo en una lista de String
    public static List<String> leerFichero(String nombreFichero) {
        List<String> lista = new ArrayList<>();
        try {
            lista = Files.readAllLines(Paths.get(nombreFichero), StandardCharsets.UTF_8);
            if (!lista.isEmpty()) {
                lista.remove(0);
                //HAgo que no lea la primera linea, que sino al pasar los datos
                // al  objeto vehiculo, el año no es un String. 
            }
        } catch (IOException ioe) {
            System.out.println("Error accediendo a fichero: " + nombreFichero);
        }
        return lista;
    }

    public static List<Usuarios> listaUsuarios() {
        List<String> listaString = new ArrayList<>();
        listaString = leerFichero(NOMBRE_FICHERO);
        List<Usuarios> listaUsuarios = new ArrayList<>();
        for (String l : listaString) {
            // Separo la lineas por las comas que tengan
            String[] array = l.split(",");
            String nombre = array[0];
            String password = array[1];
            Usuarios aux = new Usuarios(nombre, password);
            listaUsuarios.add(aux);
        }
        return listaUsuarios;
    }

    // Método que comprueba si eres un usuario o no.
    public static boolean comprobarUsuario(String nombre, String password) {

        List<Usuarios> listaUsuarios = new ArrayList<>();
        listaUsuarios = listaUsuarios();
        boolean encontrado = false;
        for (Usuarios aux : listaUsuarios) {
            if (aux.getNombre().equals(nombre)) {
                if (aux.getPassword().equals(password)) {
                    System.out.println("Si existe ese usuario en los registros");
                    encontrado = true;
                    break;
                }
            }
        }

        return encontrado;
    }

}
