package TurismoTierraMedia;

import java.util.ArrayList;

public class PromocionAxB extends Promocion {
	private Atraccion atraccionGratis;

	public PromocionAxB(String nombre, String tipoPreferencia, int costo, double tiempo,
			ArrayList<Atraccion> atracciones, Atraccion atraccionGratis) {
		super(nombre, tipoPreferencia, costo, tiempo, atracciones);
		this.atraccionGratis = atraccionGratis;
	}

	public Atraccion getAtraccionGratis() {
		return this.atraccionGratis;
	}
}