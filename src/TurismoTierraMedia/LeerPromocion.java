package TurismoTierraMedia;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class LeerPromocion {

	public static ArrayList<Comprable> ingresarPromociones(String archivo, ArrayList<Comprable> todasLasAtracciones) {
		ArrayList<Comprable> promociones = new ArrayList<Comprable>();
		Scanner sc = null;
		try {
			sc = new Scanner(new File(archivo));
			sc.useLocale(Locale.ENGLISH);
			while(sc.hasNext()) {
				String line = sc.nextLine();
				String [] datos = line.split(";");
				
				ArrayList<Atraccion> atracciones = busquedaAtracciones(datos[3], todasLasAtracciones);
				
				switch(datos[0]) {
				case "P":
					promociones.add(new PromocionPorcentual(datos[1], Integer.parseInt(datos[2]), atracciones));
					break;
				case "A":
					promociones.add(new PromocionAbsoluta(datos[1], Integer.parseInt(datos[2]), atracciones));
					break;
				case "X":
					Atraccion atraccion = busquedaAtracciones(datos[2], todasLasAtracciones).get(0);
					promociones.add(new PromocionAxB(datos[1], atraccion, atracciones));
					break;
				default:
					//hello
				}
			}
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally {
			sc.close();
		}
		return promociones;
	}
	
	private static ArrayList<Atraccion> busquedaAtracciones(String nombres, ArrayList<Comprable> todasLasAtracciones){
		ArrayList<Atraccion> atracciones = new ArrayList<Atraccion>();
		String [] nombresAtrac = nombres.split(":");
		for(String nombre : nombresAtrac) {
			int i = 0;
			boolean concidenciaEncontrada = false;
			while(i<todasLasAtracciones.size() && !concidenciaEncontrada) {
				if(todasLasAtracciones.get(i).getNombre().equals(nombre)) { 
					atracciones.add((Atraccion)todasLasAtracciones.get(i));
					concidenciaEncontrada = true;
				}
				i++;
			}
		}
		return atracciones;
	}
	
}
