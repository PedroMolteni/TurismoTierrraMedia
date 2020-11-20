package TurismoTierraMedia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class AppTurismo {

	public static void main(String[] args) {
		String appTerminada = "Y";
		Scanner sc = new Scanner(System.in);
		ArrayList<Usuario> usuarios = LeerUsuario.ingresarUsuarios("tpTurismo//usuarios.txt");
		ArrayList<Comprable> atracciones = LeerAtraccion.ingresarAtracciones("tpTurismo//atracciones.txt");
		atracciones.addAll(LeerPromocion.ingresarPromociones("tpTurismo//promociones.txt", atracciones));
		Collections.sort(atracciones);
		
		System.out.println("BIENVENIDO A TURISMO EN TIERRA MEDIA by TheBooleans");
		System.out.println("P.D. No nos hacemos responsable por incidentes con orcos.");
		
		while(appTerminada.equals("Y")) {
			System.out.println("Seleccione su numero de usuario:");
			for(int i = 0; i<usuarios.size(); i++) {
				System.out.println("Usuario nro " + (i+1) + ": " + usuarios.get(i).getNombre());
			}
			Integer respuesta = sc.nextInt();
			if(respuesta >= 1 && respuesta <= usuarios.size())
				Comprable.vender(usuarios.get(respuesta-1), atracciones);
			else
				System.out.println("Numero invalido");
			
			System.out.println("Desea continuar con otro usuario? (Y/N)");
			appTerminada = sc.next();
		}
		
		System.out.println("Gracias por los datos de su tarjeta, emm... digo POR ELEGIRNOS!");
		sc.close();
	}
}
