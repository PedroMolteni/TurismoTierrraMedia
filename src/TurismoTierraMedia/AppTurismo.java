package TurismoTierraMedia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class AppTurismo {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		ArrayList<Compra> listaAOfrecer = LeerAtraccion.ingresarAtracciones();
		ArrayList<Usuario> usuarios = LeerUsuario.ingresarUsuarios(listaAOfrecer);
		listaAOfrecer.addAll(LeerPromocion.ingresarPromociones(listaAOfrecer));

		System.out.println("BIENVENIDO A TURISMO EN TIERRA MEDIA by TheBooleans");
		System.out.println("P.D. No nos hacemos responsables por incidentes con Orcos\n");
		System.out.println("Seleccione su numero de usuario:");

		for (int i = 0; i < usuarios.size(); i++) {
			System.out.println("Usuario nro " + (i + 1) + ": " + usuarios.get(i).getNombre());
		}

		String ingreso = sc.next();
		int respuesta = 0;
		try {
			if (AppTurismo.isNumeric(ingreso))
				respuesta = Integer.parseInt(ingreso);
			if (respuesta >= 1 && respuesta <= usuarios.size()) {
				Compra.vender(usuarios.get(respuesta - 1), listaAOfrecer);
				System.out.println("\nGracias por los datos de su tarjeta, emm... digo POR ELEGIRNOS!");
				actualizarDB(usuarios, listaAOfrecer);
			} else
				throw new Exception("Usuario incorrecto");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			sc.close();
			System.out.println("Regrese pronto...");
		}
	}

	public static void actualizarDB(ArrayList<Usuario> usuarios, ArrayList<Compra> listaCompras) {
		int idUsuario = 0;
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:db/ttmdb.db");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			PreparedStatement psInsertar = null;

			for (Usuario u : usuarios) {
				ResultSet rs = statement.executeQuery(
						"SELECT usuario.id FROM usuario WHERE usuario.nombre_usuario = '" + u.getNombre() + "'");
				rs.next();
				idUsuario = rs.getInt("id");

				psInsertar = connection
						.prepareStatement("UPDATE usuario SET presupuesto=?, tiempo_disponible=? WHERE id=?");
				psInsertar.setInt(1, u.getPresupuesto());
				psInsertar.setDouble(2, u.getTiempoDisponible());
				psInsertar.setInt(3, idUsuario);
				psInsertar.executeUpdate();
			}
			for (Compra c : listaCompras) {
				if (!c.esUnaPromo()) {
					Atraccion a = (Atraccion) c;
					psInsertar = connection.prepareStatement("UPDATE atraccion SET cupo=? WHERE id=?");
					psInsertar.setInt(1, c.getCupo());
					psInsertar.setInt(2, LeerAtraccion.buscarIDAtraccion(a));
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

	public static boolean isNumeric(String cadena) {
		try {
			Integer.parseInt(cadena);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}
}