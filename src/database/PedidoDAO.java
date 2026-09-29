package database;

import model.Pedido;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    public boolean insertarPedido(String direccion, String tipo) {
        String sql = "INSERT INTO pedido (direccion, tipo, estado) VALUES (?, ?, 'PENDIENTE')";
        Connection conn = ConexionBD.obtenerConexion();
        if (conn == null) {
            System.err.println("No hay conexión a la base de datos");
            return false;
        }
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, direccion);
            pstmt.setString(2, tipo);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al insertar pedido: " + e.getMessage());
            return false;
        } finally {
            try { conn.close(); } catch (SQLException ignored) {}
        }
    }

    public List<String[]> obtenerPedidos() {
        List<String[]> lista = new ArrayList<>();
        String sql = "SELECT id, direccion, tipo, estado FROM pedido";
        Connection conn = ConexionBD.obtenerConexion();
        if (conn == null) {
            System.err.println("No hay conexión a la base de datos");
            return lista;
        }
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String[] fila = {
                        String.valueOf(rs.getInt("id")),
                        rs.getString("direccion"),
                        rs.getString("tipo"),
                        rs.getString("estado")
                };
                lista.add(fila);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener pedidos: " + e.getMessage());
        } finally {
            try { conn.close(); } catch (SQLException ignored) {}
        }
        return lista;
    }
}