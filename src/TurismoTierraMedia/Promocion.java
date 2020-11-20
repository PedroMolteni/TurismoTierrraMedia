package TurismoTierraMedia;

import java.util.ArrayList;

public abstract class Promocion extends Comprable{
	protected ArrayList<Atraccion> atracciones; 
	protected String nombre;
	protected String tipoPredominante;
	
	public Promocion(String nombre, ArrayList<Atraccion> atracciones) {
		this.nombre = nombre;
		this.atracciones = atracciones;
		this.tipoPredominante = atracciones.get(0).getTipo();
	}
	
	@Override
	public boolean contieneA(Comprable comprable) {
		for(Atraccion atraccion : atracciones) {
			if(comprable.tiene(atraccion))
				return true;
		}
		return false;
	}
	
	@Override
	public boolean tiene(Atraccion atraccion){
		if(atracciones.contains(atraccion))
			return true;
		return false;
	}
	
	@Override
	public Double getTiempo() {
		Double tiempo = 0.0;
		for(Comprable atraccion : atracciones) {
			tiempo += atraccion.getTiempo();
		}
		return tiempo;
	}
	@Override
	public String getNombre() {
		String texto = this.nombre + ": "; 
		for(int i =0; i<atracciones.size(); i++) {
			if(i==0)
				texto = texto + atracciones.get(i).getNombre();
			else
				texto = texto + ", " + atracciones.get(i).getNombre();
		}
		return texto + ".";
	}
	
	@Override
	public String getTipo() {
		return this.tipoPredominante;
	}

	@Override
	public Integer getCupo() {
		Integer cupo = 0;
		for(Comprable atraccion : atracciones) {
			if(cupo == 0)
				cupo = atraccion.getCupo();
			if(cupo < atraccion.getCupo())
				cupo = atraccion.getCupo();
		}
		return cupo;
	}
	
	@Override
	public boolean esUnPack() {
		return true;
	}

}
