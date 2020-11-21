package TurismoTierraMedia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class AppTurismo {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		ArrayList<Usuario> usuarios = LeerUsuario.ingresarUsuarios();
		ArrayList<Compra> listaAOfrecer = LeerAtraccion.ingresarAtracciones();
		listaAOfrecer.addAll(LeerPromocion.ingresarPromociones(listaAOfrecer));

		System.out.println("BIENVENIDO A TURISMO EN TIERRA MEDIA by TheBooleans");
		System.out.println("P.D. No nos hacemos responsable por incidentes con Orcos");
		System.out.println("Seleccione su numero de usuario:");

		for (int i = 0; i < usuarios.size(); i++) {
			System.out.println("Usuario nro " + (i + 1) + ": " + usuarios.get(i).getNombre());
		}

		int respuesta = sc.nextInt();
		try {
			if (respuesta >= 1 && respuesta <= usuarios.size()) {
				Compra.vender(usuarios.get(respuesta - 1), listaAOfrecer);
				System.out.println("Gracias por los datos de su tarjeta, emm... digo POR ELEGIRNOS!");
				actualizarDB(usuarios, listaAOfrecer);
			} else
				throw new Exception("Usuario incorrecto");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			sc.close();
			System.out.println("Regrese pronto...");
		}
	}

	public static void actualizarDB(ArrayList<Usuario> usuarios, ArrayList<Compra> listaCompras) {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:db/ttmdb.db");
			PreparedStatement psInsertar = null;
			for (Usuario u : usuarios) {
				psInsertar = connection
						.prepareStatement("UPDATE usuario SET presupuesto=?, tiempo_disponible=? WHERE nombre=?");
				psInsertar.setInt(1, u.getPresupuesto());
				psInsertar.setDouble(2, u.getTiempoDisponible());
				psInsertar.setString(3, u.getNombre());
				psInsertar.executeUpdate();
			}
			for (Compra c : listaCompras) {
				if (!c.esUnaPromo()) {
					psInsertar = connection.prepareStatement("UPDATE atraccion SET cupo=? WHERE nombre=?");
					psInsertar.setInt(1, c.getCupo());
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