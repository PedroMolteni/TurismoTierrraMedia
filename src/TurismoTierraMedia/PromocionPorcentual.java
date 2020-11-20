package TurismoTierraMedia;

import java.util.ArrayList;

public class PromocionPorcentual extends Promocion {
	private Integer descuento;

	public PromocionPorcentual(String nombre, Integer descuento, ArrayList<Atraccion> atracciones) {
		super(nombre, atracciones);
		this.descuento = descuento;
	}
	
	@Override
	public Integer getCosto() {
		Integer costo = 0;
		for(Comprable atraccion : atracciones) {
			costo += atraccion.getCosto();
		}
		return costo - (costo*this.descuento/100);
	}

	@Override
	public String toString() {	
		return getNombre()+"\nCosto: " + getCosto() + " monedas de oro"+ "\nDescuento: " + descuento + "%" + "\nTiempo: " + getTiempo() + " horas" +
			"\nTipo predominante: " + getTipo() + "\n";	}


}
