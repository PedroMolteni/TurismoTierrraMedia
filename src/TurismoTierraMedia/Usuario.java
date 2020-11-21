package TurismoTierraMedia;

import java.util.ArrayList;

public class Usuario {
	private String nombre;
	private String preferencia;
	private int presupuesto;
	private double tiempoDisponible;
	private ArrayList<Compra> compras;

	public Usuario(String nombre, String preferencia, int presupuesto, double tiempoDisponible) {
		this.nombre = nombre;
		this.preferencia = preferencia;
		this.presupuesto = presupuesto;
		this.tiempoDisponible = tiempoDisponible;
		this.compras = new ArrayList<Compra>();
	}

	public String getNombre() {
		return this.nombre;
	}

	public String getPreferencia() {
		return this.preferencia;
	}

	public int getPresupuesto() {
		return this.presupuesto;
	}

	private void setPresupuesto(int presupuesto) {
		this.presupuesto = presupuesto;
	}

	public double getTiempoDisponible() {
		return this.tiempoDisponible;
	}

	private void setTiempoDisponible(double tiempoDisponible) {
		this.tiempoDisponible = tiempoDisponible;
	}

	public ArrayList<Compra> getCompras() {
		return this.compras;
	}

	public void imprimirCompras() {
		System.out.println("Compraste: " + this.getCompras());
	}

	public void comprar(Compra compra) {
		this.getCompras().add(compra);
		this.setPresupuesto(this.getPresupuesto() - compra.getCosto());
		this.setTiempoDisponible(this.getTiempoDisponible() - compra.getTiempo());
	}

	public void imprimirDatos() {
		System.out.println("|" + this.getNombre() + "| Oro disponible: " + this.getPresupuesto()
				+ " monedas, Tiempo disponible: " + this.getTiempoDisponible() + " horas");
	}

	public boolean yaCompro(Compra compra) {
		return Compra.pertenceA(this.getCompras(), compra);
	}

	public boolean puedeSeguirComprando(int costo, double tiempo) {
		return (this.getPresupuesto() > 0) && (this.getTiempoDisponible() > 0) && this.tieneMonedasSuficientes(costo)
				&& this.tieneTiempoSuficiente(tiempo);
	}

	public boolean tieneMonedasSuficientes(int costo) {
		return this.getPresupuesto() >= costo;
	}

	public boolean tieneTiempoSuficiente(double tiempo) {
		return this.getTiempoDisponible() >= tiempo;
	}

	@Override
	public String toString() {
		return "|" + this.getNombre() + "|" + ", Preferencia: " + this.getPreferencia() + ", Presupuesto: "
				+ this.getPresupuesto() + " monedas, Tiempo disponible: " + this.getTiempoDisponible()
				+ " horas, Compras: " + this.getCompras();
	}
}