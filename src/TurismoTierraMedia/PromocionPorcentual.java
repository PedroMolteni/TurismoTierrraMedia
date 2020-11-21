package TurismoTierraMedia;

import java.util.ArrayList;

public class PromocionPorcentual extends Promocion {
	private int porcentajeDescuento;

	public PromocionPorcentual(String nombre, String tipoPreferencia, int costo, double tiempo,
			ArrayList<Atraccion> atracciones, int porcentajeDescuento) {
		super(nombre, tipoPreferencia, costo, tiempo, atracciones);
		this.porcentajeDescuento = porcentajeDescuento;
	}

	public int getPorcentajeDescuento() {
		return this.porcentajeDescuento;
	}
}