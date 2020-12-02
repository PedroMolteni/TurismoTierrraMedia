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
			ResultSet rs = statement.executeQuery(
					"SELECT promocion.id, promocion.nombre_promocion, promocion.tipo, promocion.costo, promocion.tiempo, promocion. porcentaje_descuento, promocion.atraccion_gratis, atraccion_tipo.nombre_atraccion_tipo FROM promocion JOIN atraccion_tipo ON atraccion_tipo.id = promocion.id_atraccion_tipo");
			while (rs.next()) {
				switch (rs.getString("tipo")) {
				case "porcentual":
					promociones.add(new PromocionPorcentual(rs.getString("nombre_promocion"),
							rs.getString("nombre_atraccion_tipo"), rs.getInt("costo"), rs.getDouble("tiempo"),
							LeerPromocion.busquedaAtracciones(rs.getInt("id"), todasLasAtracciones),
							rs.getInt("porcentaje_descuento")));
					break;
				case "absoluta":
					promociones.add(new PromocionAbsoluta(rs.getString("nombre_promocion"),
							rs.getString("nombre_atraccion_tipo"), rs.getInt("costo"), rs.getDouble("tiempo"),
							LeerPromocion.busquedaAtracciones(rs.getInt("id"), todasLasAtracciones)));
					break;
				case "axb":
					Atraccion atraccionGratis = null;
					for (Compra c : todasLasAtracciones) {
						if (c.getNombre().equals(rs.getString("atraccion_gratis")))
							atraccionGratis = (Atraccion) c;
					}
					ArrayList<Atraccion> atracciones = LeerPromocion.busquedaAtracciones(rs.getInt("id"),
							todasLasAtracciones);
					atracciones.add(atraccionGratis);
					promociones.add(
							new PromocionAxB(rs.getString("nombre_promocion"), rs.getString("nombre_atraccion_tipo"),
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

	private static ArrayList<Atraccion> busquedaAtracciones(int idPromocion, ArrayList<Compra> todasLasAtracciones) {
		ArrayList<Atraccion> atracciones = new ArrayList<Atraccion>();
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:db/ttmdb.db");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			ResultSet rs = statement.executeQuery(
					"SELECT atraccion.nombre_atraccion FROM promocion_atracciones JOIN promocion ON promocion.id = promocion_atracciones.id_promocion JOIN atraccion ON atraccion.id = promocion_atracciones.id_atraccion WHERE promocion.id = '"
							+ idPromocion + "'");
			String nombresAtracciones = "";
			while (rs.next()) {
				nombresAtracciones += rs.getString("nombre_atraccion") + ":";
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