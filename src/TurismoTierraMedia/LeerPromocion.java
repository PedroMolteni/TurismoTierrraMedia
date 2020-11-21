package TurismoTierraMedia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class LeerPromocion {

	public static ArrayList<Compra> ingresarPromociones(ArrayList<Compra> todasLasAtracciones) {
		ArrayList<Compra> promociones = new ArrayList<Compra>();
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:db/ttmdb.db");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			ResultSet rs = statement.executeQuery("SELECT * FROM promocion");

			while (rs.next()) {
				switch (rs.getString("tipo_promocion")) {
				case "porcentual":
					promociones.add(new PromocionPorcentual(rs.getString("nombre"), rs.getString("tipo_atraccion"),
							rs.getInt("costo"), rs.getDouble("tiempo"),
							LeerPromocion.busquedaAtracciones(rs.getString("nombre"), todasLasAtracciones),
							rs.getInt("porcentaje_descuento")));
					break;
				case "absoluta":
					promociones.add(new PromocionAbsoluta(rs.getString("nombre"), rs.getString("tipo_atraccion"),
							rs.getInt("costo"), rs.getDouble("tiempo"),
							LeerPromocion.busquedaAtracciones(rs.getString("nombre"), todasLasAtracciones)));
					break;
				case "axb":
					Atraccion atraccionGratis = null;
					for (Compra c : todasLasAtracciones) {
						if (c.getNombre().equals(rs.getString("atraccion_gratis")))
							atraccionGratis = (Atraccion) c;
					}
					ArrayList<Atraccion> atracciones = LeerPromocion.busquedaAtracciones(rs.getString("nombre"),
							todasLasAtracciones);
					atracciones.add(atraccionGratis);
					promociones.add(new PromocionAxB(rs.getString("nombre"), rs.getString("tipo_atraccion"),
							rs.getInt("costo"), rs.getDouble("tiempo"), atracciones, atraccionGratis));
					break;
				}
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				System.err.println(e.getMessage());
			}
		}
		return promociones;
	}

	private static ArrayList<Atraccion> busquedaAtracciones(String promocion, ArrayList<Compra> todasLasAtracciones) {
		ArrayList<Atraccion> atracciones = new ArrayList<Atraccion>();
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:db/ttmdb.db");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			ResultSet rs = statement
					.executeQuery("SELECT * FROM promocion_atracciones WHERE promocion='" + promocion + "'");
			String nombresAtracciones = "";
			while (rs.next()) {
				nombresAtracciones += rs.getString("atraccion") + ":";
			}
			String[] nombrAtrac = nombresAtracciones.split(":");
			for (String nombre : nombrAtrac) {
				for (Compra a : todasLasAtracciones) {
					if (a.getNombre().equals(nombre)) {
						atracciones.add((Atraccion) a);
					}
				}

			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				System.err.println(e.getMessage());
			}
		}
		return atracciones;
	}
}