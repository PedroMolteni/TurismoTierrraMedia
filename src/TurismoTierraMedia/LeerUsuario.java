package TurismoTierraMedia;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class LeerUsuario {
	public static ArrayList<Usuario> ingresarUsuarios(String archivo)  {
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		Scanner sc = null;
		try {
				sc = new Scanner(new File(archivo));
				sc.useLocale(Locale.ENGLISH);
				while(sc.hasNext()) {
					String line = sc.nextLine();
					String [] datos = line.split(";");
					usuarios.add(new Usuario(datos[0],  datos[1], Integer.parseInt(datos[2]),  Double.parseDouble(datos[3])));
				}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally {
			sc.close();
		}
		return usuarios;
	}
	
	public static void crearArchivo(Usuario usuario) {
		PrintWriter salida = null;
		String nombreArchivo = "comprasDe";
		try {
			salida = new PrintWriter(new FileWriter(nombreArchivo + usuario.getNombre() + ".txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}  
	    String s="Compras de " + usuario.getNombre() +":\n";
	    for(Comprable comprable : usuario.getCompras()) {
	    	s = s + comprable.getNombre() + "\n";
	    }
	    	s = s + "Costo total: " + Comprable.costoTotal(usuario.getCompras()) + " monedas de oro\n";
	    	s= s + "Tiempo necesario: " + Comprable.tiempoTotal(usuario.getCompras()) + " horas\n";
	    salida.println(s);       
		
		salida.close();
	}
}
