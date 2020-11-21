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

			ResultSet rs = statement.executeQuery("SELECT * FROM atraccion");
			while (rs.next()) {
				Compra atraccion = new Atraccion(rs.getString("nombre"), rs.getString("tipo"), rs.getInt("costo"),
						rs.getDouble("tiempo"), rs.getInt("cupo"));
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
}