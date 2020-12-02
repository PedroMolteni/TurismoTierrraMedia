package TurismoTierraMedia;

import java.util.ArrayList;

public class Usuario {
	private String nombre;
	private String preferencia;
	private int presupuesto;
	private double tiempoDisponible;
	private ArrayList<Compra> compras;

	public Usuario(String nombre, String preferencia, int presupuesto, double tiempoDisponible,
			ArrayList<Compra> compras) {
		this.nombre = nombre;
		this.preferencia = preferencia;
		this.presupuesto = presupuesto;
		this.tiempoDisponible = tiempoDisponible;
		this.compras = compras;
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

	public void comprar(Compra compra) {
		this.getCompras().add(compra);
		this.setPresupuesto(this.getPresupuesto() - compra.getCosto());
		this.setTiempoDisponible(this.getTiempoDisponible() - compra.getTiempo());
	}

	public boolean yaCompro(Compra compra) {
		return Compra.pertenceA(this.getCompras(), compra);
	}

	public boolean puedeSeguirComprando(int costo, double tiempo) {
		return (this.tieneMonedasSuficientes(costo) && this.tieneTiempoSuficiente(tiempo));
	}

	private boolean tieneMonedasSuficientes(int costo) {
		return this.getPresupuesto() >= costo;
	}

	private boolean tieneTiempoSuficiente(double tiempo) {
		return this.getTiempoDisponible() >= tiempo;
	}

	public String imprimirEstado() {
		return "|" + this.getNombre() + "| Le quedan " + this.getPresupuesto() + " monedas y "
				+ this.getTiempoDisponible() + " horas disponibles";
	}

	public String imprimirCompras() {
		String nombresCompras = "";
		for (Compra c : this.getCompras())
			nombresCompras += c.getNombre() + ", ";
		return "\nCompró: " + nombresCompras;
	}

	@Override
	public String toString() {
		return "|" + this.getNombre() + "|" + ", Preferencia: " + this.imprimirEstado() + this.imprimirCompras();
	}
}