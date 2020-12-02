package TurismoTierraMedia;

public class Atraccion extends Compra {
	private String nombre;
	private String tipo;
	private int costo;
	private double tiempo;
	private int cupo;

	public Atraccion(String nombre, String tipo, int costo, double tiempo, int cupo) {
		this.nombre = nombre;
		this.tipo = tipo;
		this.costo = costo;
		this.tiempo = tiempo;
		this.cupo = cupo;
	}

	@Override
	public String getNombre() {
		return this.nombre;
	}

	@Override
	public String getTipo() {
		return this.tipo;
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
		return this.cupo;
	}

	@Override
	public void restarCupo() {
		try {
			if (this.getCupo() > 0)
				this.cupo--;
			else
				throw new Exception("No hay mas cupo");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public boolean contieneA(Compra compra) {
		return compra.tiene(this);
	}

	@Override
	public boolean tiene(Atraccion atraccion) {
		return this.getNombre().equals(atraccion.getNombre());
	}

	@Override
	public boolean esUnaPromo() {
		return false;
	}

	@Override
	public String toString() {
		return "|" + this.getNombre() + "|" + "\nCosto: " + this.getCosto() + " monedas de oro" + "\nTiempo: "
				+ this.getTiempo() + " horas" + " \nCupo: " + this.getCupo() + "\nTipo: " + this.getTipo() + "\n";
	}
}