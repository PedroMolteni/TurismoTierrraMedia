package TurismoTierraMedia;

import java.util.ArrayList;
import java.util.Scanner;

public abstract class Comprable implements Comparable<Comprable>{
	
	public static ArrayList<Comprable> ordenarSegunPreferencia(ArrayList<Comprable> atraccionesRecibidas, String preferencia) {
		ArrayList<Comprable> atracciones = new ArrayList<Comprable>(atraccionesRecibidas);
		ArrayList<Comprable> atraccionesPreferentes = new ArrayList<Comprable>();
		for(int i = 0; i < atracciones.size(); i++) {
			if(atracciones.get(i).mismoTipo(preferencia) ) {
				atraccionesPreferentes.add(atracciones.get(i));
				atracciones.remove(i);
				i--;
			}
		}
		atraccionesPreferentes.addAll(atracciones);
		return atraccionesPreferentes;
	}
	
	public static void imprimirOpciones(ArrayList<Comprable> atracciones, Usuario usuario){
		Scanner sc = new Scanner(System.in);
		for(Comprable atraccion : atracciones) {
			if(usuario.puedeSeguirComprando(atraccion.getCosto(), atraccion.getTiempo()) && !usuario.yaCompro(atraccion)) {
				usuario.imprimirDatos();
				System.out.println(atraccion + "\nDesea comprar esta oferta?(Y/N):");
				String respuesta = sc.nextLine();
				if(respuesta.equals("Y")) {
					usuario.comprar(atraccion);
				}
			}	
		}
		//sc.close(); si lo cerramos aca se rompe todo
	}
	
	public static void vender(Usuario usuario, ArrayList<Comprable> atracciones) {
		usuario.ofrecerOpciones(atracciones);
		usuario.imprimirCompras();
		LeerUsuario.crearArchivo(usuario);
	}

	public static boolean pertenceA(ArrayList<Comprable> comprables, Comprable atraccion){
		for(Comprable comprable : comprables) {
			if(comprable.contieneA(atraccion))
				return true;
		}
		return false;
	}

	public int compareTo(Comprable otroComprable) {
		if(this.esUnPack()) {
			if(!otroComprable.esUnPack())
				return -1;
		}else if(otroComprable.esUnPack())
			return 1;
		
		if(this.getCosto().equals(otroComprable.getCosto())) {
			return (-1)*this.getTiempo().compareTo(otroComprable.getTiempo());
		}
		return (-1)*this.getCosto().compareTo(otroComprable.getCosto());
	}
	
	public boolean mismoTipo(String tipo) {
		return this.getTipo().equals(tipo);
	}
	
	public static Integer costoTotal(ArrayList<Comprable> atracciones) {
		Integer sumatoria = 0;
		for(Comprable comprable : atracciones) {
			sumatoria += comprable.getCosto();
		}
		return sumatoria;
	}
	
	
	public static Double tiempoTotal(ArrayList<Comprable> atracciones) {
		Double sumatoria = 0.0;
		for(Comprable comprable : atracciones) {
			sumatoria += comprable.getTiempo();
		}
		return sumatoria;
	}

	public abstract boolean contieneA(Comprable comprable);
	public abstract boolean tiene(Atraccion atraccion);
	public abstract boolean esUnPack();
	public abstract Integer getCosto();
	public abstract Double getTiempo();
	public abstract String getTipo();
	public abstract Integer getCupo();
	public abstract String toString();
	public abstract String getNombre();
}
