package TurismoTierraMedia;

import java.util.ArrayList;

public abstract class Promocion extends Compra {
	protected String nombre;
	protected String tipoPreferencia;
	protected int costo;
	protected double tiempo;
	protected ArrayList<Atraccion> atracciones;

	public Promocion(String nombre, String tipoPreferencia, int costo, double tiempo,
			ArrayList<Atraccion> atracciones) {
		this.nombre = nombre;
		this.tipoPreferencia = tipoPreferencia;
		this.costo = costo;
		this.tiempo = tiempo;
		this.atracciones = atracciones;
	}

	public ArrayList<Atraccion> getAtracciones() {
		return this.atracciones;
	}

	@Override
	public String getNombre() {
		return this.nombre;
	}

	@Override
	public String getTipo() {
		return this.tipoPreferencia;
	}

	@Override
	public int getCosto() {
		return this.costo;
	}

	@Override
	public double getTiempo() {
		return this.tiempo;
	}

	@Override
	public int getCupo() {
		int cupo = Integer.MAX_VALUE;
		for (Atraccion a : this.getAtracciones()) {
			if (a.getCupo() < cupo)
				cupo = a.getCupo();
		}
		return cupo;
	}

	@Override
	public void restarCupo() {
		for (Atraccion a : this.getAtracciones())
			a.restarCupo();
	}

	@Override
	public boolean contieneA(Compra compra) {
		for (Atraccion a : this.getAtracciones()) {
			if (compra.tiene(a))
				return true;
		}
		return false;
	}

	@Override
	public boolean tiene(Atraccion atraccion) {
		if (this.getAtracciones().contains(atraccion))
			return true;
		return false;
	}

	@Override
	public boolean esUnaPromo() {
		return true;
	}

	@Override
	public String toString() {
		String nombresAtracciones = "";
		for (Compra c : this.getAtracciones())
			nombresAtracciones += c.getNombre() + ", ";
		nombresAtracciones = nombresAtracciones.substring(0, nombresAtracciones.length()-2)+".";
		return "|" + getNombre() + "|\nPreferencia: " + this.getTipo() + "\nCosto: " + this.getCosto()
				+ " monedas de oro\nTiempo: " + this.getTiempo() + " horas\nAtracciones: " + nombresAtracciones + "\n";
	}
}