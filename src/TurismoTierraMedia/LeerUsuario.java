package TurismoTierraMedia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class LeerUsuario {
	public static ArrayList<Usuario> ingresarUsuarios(ArrayList<Compra> todasLasAtracciones) {
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:db/ttmdb.db");
			Statement statement1 = connection.createStatement();
			statement1.setQueryTimeout(30);
			ResultSet rs = statement1.executeQuery(
					"SELECT usuario.id, usuario.nombre_usuario, usuario.presupuesto, usuario.tiempo_disponible, atraccion_tipo.nombre_atraccion_tipo FROM usuario JOIN atraccion_tipo ON atraccion_tipo.id = usuario.id_atraccion_tipo");
			while (rs.next()) {
				Usuario usuario = new Usuario(rs.getString("nombre_usuario"), rs.getString("nombre_atraccion_tipo"),
						rs.getInt("presupuesto"), rs.getDouble("tiempo_disponible"),
						LeerUsuario.buscarCompras(rs.getInt("id"), todasLasAtracciones));
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
		int idUsuario = 0;
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:db/ttmdb.db");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			PreparedStatement psInsertar = null;
			if (!usuario.getCompras().isEmpty()) {
				ResultSet rs = statement.executeQuery(
						"SELECT usuario.id FROM usuario WHERE usuario.nombre_usuario = '" + usuario.getNombre() + "'");
				rs.next();
				idUsuario = rs.getInt("id");
				psInsertar = connection.prepareStatement(
						"INSERT INTO itinerario(id_usuario, costo, tiempo) VALUES(?,?,?)",
						PreparedStatement.RETURN_GENERATED_KEYS);
				psInsertar.setInt(1, idUsuario);
				psInsertar.setInt(2, Compra.costoTotal(usuario.getCompras()));
				psInsertar.setDouble(3, Compra.tiempoTotal(usuario.getCompras()));
				psInsertar.executeUpdate();
				psInsertar = connection.prepareStatement(
						"INSERT INTO itinerario_atracciones(id_itinerario, id_atraccion) VALUES(?,?)");
				rs = psInsertar.getGeneratedKeys();
				rs.next();
				int idItinerario = rs.getInt(1);
				for (Compra c : usuario.getCompras()) {
					if (c.esUnaPromo()) {
						Promocion p = (Promocion) c;
						for (Atraccion a : p.getAtracciones()) {
							psInsertar.setInt(1, idItinerario);
							psInsertar.setInt(2, LeerAtraccion.buscarIDAtraccion(a));
							psInsertar.executeUpdate();
						}
					} else {
						Atraccion a = (Atraccion) c;
						psInsertar.setInt(1, idItinerario);
						psInsertar.setInt(2, LeerAtraccion.buscarIDAtraccion(a));
						psInsertar.executeUpdate();
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
	}

	public static ArrayList<Compra> buscarCompras(int idUsuario, ArrayList<Compra> todasLasAtracciones) {
		ArrayList<Compra> compras = new ArrayList<Compra>();
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:db/ttmdb.db");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			ResultSet rs = statement.executeQuery(
					"SELECT atraccion.nombre_atraccion FROM usuario JOIN itinerario ON itinerario.id_usuario = usuario.id JOIN itinerario_atracciones ON itinerario_atracciones.id_itinerario = itinerario.id JOIN atraccion ON atraccion.id = itinerario_atracciones.id_atraccion WHERE usuario.id = '"
							+ idUsuario + "'");
			while (rs.next()) {
				for (Compra c : todasLasAtracciones) {
					if (c.getNombre().equals(rs.getString("nombre_atraccion")))
						compras.add(c);
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
		return compras;
	}
}