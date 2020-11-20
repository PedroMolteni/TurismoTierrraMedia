package TurismoTierraMedia;

import java.util.ArrayList;

public class PromocionAxB extends Promocion {
	Atraccion atraccionGratis;

	public PromocionAxB(String nombre, Atraccion atraccionGratis, ArrayList<Atraccion> atracciones) {
		super(nombre, atracciones);
		this.atraccionGratis = atraccionGratis;
		this.atracciones.add(atraccionGratis);
	}

	@Override
	public Integer getCosto() {
		Integer costo = 0;
		for(Comprable atraccion : atracciones) {
			costo += atraccion.getCosto();
		}
		return costo - atraccionGratis.getCosto();
	}

	@Override
	public String toString() {
		return getNombre()+" \nPrecio: " + getCosto() + " monedas de oro" + "\nTiempo: " + getTiempo() + " horas" + 
				"\nTipo predominante: " + getTipo() + "\n";	}

} 