package TurismoTierraMedia;

public class Atraccion extends Comprable{
	private String nombre;
	private Integer costo;
	private Double tiempo;
	private Integer cupo;
	private String tipo;

	public Atraccion(String nombre, Integer costo, Double tiempo, Integer cupo, String tipo) {
		this.nombre = nombre;
		this.tipo = tipo;
		this.costo = costo;
		this.tiempo = tiempo;
		this.cupo = cupo;
	}

	@Override
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public Integer getCosto() {
		return costo;
	}

	public void setCosto(Integer costo) {
		this.costo = costo;
	}

	@Override
	public Double getTiempo() {
		return tiempo;
	}

	public void setTiempo(Double tiempo) {
		this.tiempo = tiempo;
	}
	
	@Override
	public Integer getCupo() {
		return cupo;
	}

	public void setCupo(Integer cupo) {
		this.cupo = cupo;
	}
	
	@Override
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return nombre + "\nCosto: " + costo + " monedas de oro" + "\nTiempo: " + tiempo + " horas" + " \nCupo: " + cupo + "\nTipo: "
				+ tipo + "\n";
	}
	
	@Override
	public boolean contieneA(Comprable comprable) {
		return comprable.tiene(this);
	}
	
	@Override
	public boolean tiene(Atraccion atraccion){
		return this.equals(atraccion);
	}
	
	
	@Override
	public boolean esUnPack() {
		return false;
	}
}
