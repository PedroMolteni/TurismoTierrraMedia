package TurismoTierraMedia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public abstract class Compra implements Comparable<Compra> {

	public static void vender(Usuario usuario, ArrayList<Compra> listaAOfrecer) {
		Compra.ofrecerOpciones(listaAOfrecer, usuario);
		System.out.println(usuario.devolverCompras());
		System.out.println(usuario.devolverEstado());
		LeerUsuario.grabarItinerario(usuario);
	}

	public static void ofrecerOpciones(ArrayList<Compra> listaAOfrecer, Usuario usuario) {
		ArrayList<Compra> listaAOfrecerOrdenada = Compra.ordenarSegunPreferencia(listaAOfrecer,
				usuario.getPreferencia());
		Compra.imprimirOpciones(listaAOfrecerOrdenada, usuario);
	}

	public static ArrayList<Compra> ordenarSegunPreferencia(ArrayList<Compra> listaAOfrecer, String preferencia) {
		Collections.sort(listaAOfrecer);
		ArrayList<Compra> listaAOfrecerDuplicada = new ArrayList<Compra>(listaAOfrecer);
		ArrayList<Compra> listaAOfrecerOrdenada = new ArrayList<Compra>();
		for (int i = 0; i < listaAOfrecerDuplicada.size(); i++) {
			if (listaAOfrecerDuplicada.get(i).mismoTipo(preferencia)) {
				listaAOfrecerOrdenada.add(listaAOfrecerDuplicada.get(i));
				listaAOfrecerDuplicada.remove(i);
				i--;
			}
		}
		listaAOfrecerOrdenada.addAll(listaAOfrecerDuplicada);
		return listaAOfrecerOrdenada;
	}

	public static void imprimirOpciones(ArrayList<Compra> listaAOfrecerOrdenada, Usuario usuario) {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.println();
		for (Compra c : listaAOfrecerOrdenada) {
			if (usuario.puedeSeguirComprando(c.getCosto(), c.getTiempo()) && !usuario.yaCompro(c) && c.getCupo() > 0) {
				System.out.println(usuario.devolverEstado());
				System.out.println("Le podemos ofrecer " + c + "\nDesea comprar esta oferta? (Y/N)");
				String respuesta = sc.nextLine();
				
				while(!(respuesta.equals("Y") || respuesta.equals("y") || respuesta.equals("N")
							|| respuesta.equals("n"))) {
					System.err.println("Ingreso incorrecto, pruebe nuevamente.");
					usuario.devolverEstado();
					System.out.println("Le podemos ofrecer " + c + "\nDesea comprar esta oferta? (Y/N)");
					respuesta = sc.nextLine();
				}
				if (respuesta.equals("Y") || respuesta.equals("y")) {
					usuario.comprar(c);
					c.restarCupo();
				}
			}
		}
		// sc.close(); si lo cerramos aca se rompe todo!
	}
/* CAMBIAR
 * 	public static void imprimirOpciones(ArrayList<Compra> listaAOfrecerOrdenada, Usuario usuario) {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.println();
		for (Compra c : listaAOfrecerOrdenada) {
			if (usuario.puedeSeguirComprando(c.getCosto(), c.getTiempo()) && !usuario.yaCompro(c) && c.getCupo() > 0) {
				usuario.imprimirEstado();
				System.out.println("Le podemos ofrecer " + c + "\nDesea comprar esta oferta? (Y/N)");
				String respuesta = sc.nextLine();
				try {
					if (!(respuesta.equals("Y") || respuesta.equals("y") || respuesta.equals("N")
							|| respuesta.equals("n")))
						throw new Exception("Ingreso incorrecto");
					if (respuesta.equals("Y") || respuesta.equals("y")) {
						usuario.comprar(c);
						c.restarCupo();
					}
				} catch (Exception e) {
					System.err.println(e.getMessage());
					Compra.imprimirOpciones(listaAOfrecerOrdenada, usuario);
					break;
				}
			}
		}
		// sc.close(); si lo cerramos aca se rompe todo!
	}
 */

	public static boolean pertenceA(ArrayList<Compra> compra, Compra atraccion) {
		for (Compra comprable : compra) {
			if (comprable.contieneA(atraccion))
				return true;
		}
		return false;
	}

	public int compareTo(Compra otroComprable) {
		if (this.esUnaPromo()) {
			if (!otroComprable.esUnaPromo())
				return -1;
		} else if (otroComprable.esUnaPromo())
			return 1;
		if (this.getCosto() == (otroComprable.getCosto())) {
			return (-1) * Double.compare(this.getTiempo(), (otroComprable.getTiempo()));
		}
		return (-1) * Integer.compare(this.getCosto(), (otroComprable.getCosto()));
	}

	public boolean mismoTipo(String tipo) {
		return this.getTipo().equals(tipo);
	}

	public static int costoTotal(ArrayList<Compra> compra) {
		int sumatoria = 0;
		for (Compra c : compra) {
			sumatoria += c.getCosto();
		}
		return sumatoria;
	}

	public static double tiempoTotal(ArrayList<Compra> compra) {
		double sumatoria = 0;
		for (Compra c : compra) {
			sumatoria += c.getTiempo();
		}
		return sumatoria;
	}

	public abstract String getNombre();

	public abstract String getTipo();

	public abstract int getCosto();

	public abstract double getTiempo();

	public abstract int getCupo();

	public abstract void restarCupo();

	public abstract boolean contieneA(Compra compra);

	public abstract boolean tiene(Atraccion atraccion);

	public abstract boolean esUnaPromo();
}