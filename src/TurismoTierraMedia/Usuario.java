package TurismoTierraMedia;

import java.util.ArrayList;

public class Usuario {
	private String nombre;
	private String preferencia;
	private int presupuesto;
	private double tiempoDisponible;
	private ArrayList<Comprable> compras;

	public Usuario(String nombre, String preferencia, int presupuesto, double tiempoDisponible) {
		this.nombre = nombre;
		this.preferencia = preferencia;
		this.presupuesto = presupuesto;
		this.tiempoDisponible = tiempoDisponible;
		this.compras = new ArrayList<Comprable>();
	}
	
	public void ofrecerOpciones(ArrayList<Comprable> atraccionesRecibidas) {
		ArrayList<Comprable> atracciones = Comprable.ordenarSegunPreferencia(atraccionesRecibidas, this.preferencia);
		Comprable.imprimirOpciones(atracciones, this);
	}
	
	public void imprimirCompras() {
		System.out.println("Compraste: " + compras);
	}
	
	public void comprar(Comprable atraccion) {
		compras.add(atraccion);
		this.presupuesto -= atraccion.getCosto();
		this.tiempoDisponible -= atraccion.getTiempo();
	}
	
	public void imprimirDatos() {
		System.out.println(nombre+" { Oro disponible:" + presupuesto + ", Horas disponible:" + tiempoDisponible + "}");
	}
	
	public boolean puedeSeguirComprando(int costo, Double tiempo) {
		return (presupuesto > 0) && (tiempoDisponible > 0) && puedeComprar(costo) && tieneTiempoSuficiente(tiempo);
	}
	
	public boolean puedeComprar(int costo) {
		return this.presupuesto >= costo;
	}
	
	public boolean yaCompro(Comprable atraccion){
		return Comprable.pertenceA(compras, atraccion);
	}
	public boolean tieneTiempoSuficiente(Double tiempo) {
		return this.tiempoDisponible >= tiempo;
	}
	
	@Override
	public String toString() {
		return "Usuario [nombre=" + nombre + ", preferencia=" + preferencia + ", presupuesto=" + presupuesto
				+ ", tiempoDisponible=" + tiempoDisponible + ", compras=" + compras + "]";
	}

	public ArrayList<Comprable> getCompras() {
		return compras;
	}

	public String getNombre() {
		return nombre;
	}
	
}
