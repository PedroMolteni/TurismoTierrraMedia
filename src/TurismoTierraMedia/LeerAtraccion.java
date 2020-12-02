package TurismoTierraMedia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class LeerAtraccion {
	public static ArrayList<Compra> ingresarAtracciones() {
		ArrayList<Compra> atracciones = new ArrayList<Compra>();
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:db/ttmdb.db");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			ResultSet rs = statement.executeQuery(
					"SELECT atraccion.nombre_atraccion, atraccion.costo, atraccion.tiempo, atraccion.cupo, atraccion_tipo.nombre_atraccion_tipo FROM atraccion JOIN atraccion_tipo ON atraccion_tipo.id = atraccion.id_tipo");
			while (rs.next()) {
				Compra atraccion = new Atraccion(rs.getString("nombre_atraccion"),
						rs.getString("nombre_atraccion_tipo"), rs.getInt("costo"), rs.getDouble("tiempo"),
						rs.getInt("cupo"));
				atracciones.add(atraccion);
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

	public static int buscarIDAtraccion(Atraccion atraccion) {
		int idAtraccion = 0;
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:db/ttmdb.db");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			ResultSet rs = statement
					.executeQuery("SELECT atraccion.id FROM atraccion WHERE atraccion.nombre_atraccion = '"
							+ atraccion.getNombre() + "'");
			rs.next();
			idAtraccion = rs.getInt("id");
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
		return idAtraccion;
	}
}