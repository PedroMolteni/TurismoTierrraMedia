package TurismoTierraMedia;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class LeerAtraccion {
	public static ArrayList<Comprable> ingresarAtracciones(String archivo)  {
		ArrayList<Comprable> atracciones = new ArrayList<Comprable>();
		Scanner sc = null;
		try {
				sc = new Scanner(new File(archivo));
				sc.useLocale(Locale.ENGLISH);
				while(sc.hasNext()) {
					String line = sc.nextLine();
					String [] datos = line.split(";");
					atracciones.add(new Atraccion(datos[0], Integer.parseInt(datos[1]),  Double.parseDouble(datos[2]), Integer.parseInt(datos[3]), datos[4]));
				}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			sc.close();
		}

		return atracciones;
	}
}
