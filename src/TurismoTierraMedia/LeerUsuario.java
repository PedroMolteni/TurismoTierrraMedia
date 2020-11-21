package TurismoTierraMedia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class LeerUsuario {
	public static ArrayList<Usuario> ingresarUsuarios() {
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:db/ttmdb.db");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);

			ResultSet rs = statement.executeQuery("SELECT * FROM usuario");
			while (rs.next()) {
				Usuario usuario = new Usuario(rs.getString("nombre"), rs.getString("preferencia"),
						rs.getInt("presupuesto"), rs.getDouble("tiempo_disponible"));
				usuarios.add(usuario);
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
		return usuarios;
	}

	public static void grabarItinerario(Usuario usuario) {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:db/ttmdb.db");
			PreparedStatement psInsertar = null;

			psInsertar = connection.prepareStatement("INSERT INTO itinerario(usuario, costo, tiempo) VALUES(?,?,?)",
					PreparedStatement.RETURN_GENERATED_KEYS);
			psInsertar.setString(1, usuario.getNombre());
			psInsertar.setInt(2, Compra.costoTotal(usuario.getCompras()));
			psInsertar.setDouble(3, Compra.tiempoTotal(usuario.getCompras()));
			psInsertar.executeUpdate();

			psInsertar = connection
					.prepareStatement("INSERT INTO itinerario_atracciones(itinerario, atraccion) VALUES(?,?)");
			ResultSet rs = psInsertar.getGeneratedKeys();
			rs.next();
			int idItinerario = rs.getInt(1);
			for (Compra c : usuario.getCompras()) {
				if (c.esUnaPromo()) {
					Promocion p = (Promocion) c;
					for (Atraccion a : p.getAtracciones()) {
						psInsertar.setInt(1, idItinerario);
						psInsertar.setString(2, a.getNombre());
						psInsertar.executeUpdate();
					}
				} else {
					psInsertar.setInt(1, idItinerario);
					psInsertar.setString(2, c.getNombre());
					psInsertar.executeUpdate();
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
	}
}