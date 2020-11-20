package TurismoTierraMedia;

import java.util.ArrayList;

public class PromocionAbsoluta extends Promocion {
	private Integer precioFijo;

	public PromocionAbsoluta(String nombre, Integer precioFijo, ArrayList<Atraccion> atracciones) {
		super(nombre, atracciones);
		this.precioFijo = precioFijo;
	}

	@Override
	public Integer getCosto() {
		return this.precioFijo;
	}

	@Override
	public String toString() {
		return getNombre()+"\nPrecioFijo: " + getCosto() + " monedas de oro" + "\nTiempo: " + getTiempo() + " horas" + 
				"\nTipo predominante: " + getTipo() + "\n";
	}
}
