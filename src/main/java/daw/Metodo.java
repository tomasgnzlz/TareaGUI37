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
import javax.swing.JOptionPane;

/**
 *
 * @author tomas
 */
public class Metodo {

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
                    encontrado = true;
                    break;
                }
            }
        }
        return encontrado;
    }

    public static void añadirRegistroUsuario(String nombre, String password) {
        List<Usuarios> listaUsuarios = listaUsuarios();
        boolean existe = false;

        for (Usuarios Uaux : listaUsuarios) {
            if (Uaux.getNombre().equals(nombre)) {
                JOptionPane.showMessageDialog(null, "SE HA MODIFICADO LA CONTRASEÑA DEL USUARIO: " + nombre.toUpperCase());
                Uaux.setPassword(password);
                existe = true;
                break;
            }
        }
        if (!existe) {
            Usuarios userAux = new Usuarios(nombre, password);
            listaUsuarios.add(userAux);
            JOptionPane.showMessageDialog(null, "SE HA REGISTRADO EL USUARIO: " + nombre.toUpperCase());
        }

        // Una vez tengo añadido el usuario a la lista o la modificacion de la 
        // passwd escribo escribo el csv
        escrituraRegistro(listaUsuarios);
    }

    // Método que escribe la lista de usuarios al csv una vez de añade o modifican
    public static void escrituraRegistro(List<Usuarios> listaUsuarios) {
        List<String> listaStrings = new ArrayList<>();
        listaStrings.add("Nombre,Password");
        for (Usuarios aux : listaUsuarios) {
            listaStrings.add(aux.toString2());
        }

        //Ahora hago la escritura de la lista de Strings
        try {
            Files.write(Paths.get(NOMBRE_FICHERO), listaStrings);
        } catch (IOException e) {
            System.out.println("ERROR AL ESCRIBRI EN EL FICHERO");
        }
    }
}
