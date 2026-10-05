package dao;

import database.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EntregaDAO {

    // 1. Versión que recibe Objetos SQL
    public boolean registrarEntrega(int idPedido, int idRepartidor, java.sql.Date fecha, java.sql.Time hora) {
        String sql = "INSERT INTO entregas (id_pedido, id_repartidor, fecha, hora) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idPedido);
            pstmt.setInt(2, idRepartidor);
            pstmt.setDate(3, fecha);
            pstmt.setTime(4, hora);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al registrar entrega: " + e.getMessage());
            return false;
        }
    }

    // 2. Versión alternativa que recibe Strings
    public boolean registrarEntrega(int idPedido, int idRepartidor, String fecha, String hora) {
        return registrarEntrega(idPedido, idRepartidor, java.sql.Date.valueOf(fecha), java.sql.Time.valueOf(hora));
    }

    public List<String[]> obtenerEntregas() {
        List<String[]> lista = new ArrayList<>();
        // Usamos AS para evitar conflictos de nombres repetidos (como 'id') en el ResultSet
        String sql = "SELECT e.id AS id_entrega, p.id AS id_pedido, p.direccion, r.id AS id_repartidor, r.nombre, e.fecha, e.hora " +
                "FROM entregas e " +
                "JOIN pedidos p ON e.id_pedido = p.id " +
                "JOIN repartidores r ON e.id_repartidor = r.id";
        try (Connection conn = ConexionBD.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String[] entrega = {
                        String.valueOf(rs.getInt("id_entrega")),
                        String.valueOf(rs.getInt("id_pedido")),
                        rs.getString("direccion"),
                        String.valueOf(rs.getInt("id_repartidor")),
                        rs.getString("nombre"),
                        rs.getString("fecha"),
                        rs.getString("hora")
                };
                lista.add(entrega);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar entregas: " + e.getMessage());
        }
        return lista;
    }

    public boolean eliminarEntrega(int idEntrega) {
        String sql = "DELETE FROM entregas WHERE id = ?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idEntrega);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al eliminar entrega: " + e.getMessage());
            return false;
        }
    }
}