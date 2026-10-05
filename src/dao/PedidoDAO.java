package dao;

import database.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    public boolean insertarPedido(String direccion, String tipo) {
        String sql = "INSERT INTO pedidos (direccion, tipo, estado) VALUES (?, ?, 'PENDIENTE')";
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
        String sql = "SELECT id, direccion, tipo, estado FROM pedidos";
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

    public boolean actualizarEstado(int idPedido, String nuevoEstado) {
        String sql = "UPDATE pedidos SET estado = ? WHERE id = ?";
        Connection conn = ConexionBD.obtenerConexion();
        if (conn == null) {
            System.err.println("No hay conexión a la base de datos");
            return false;
        }
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nuevoEstado);
            pstmt.setInt(2, idPedido);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al actualizar estado del pedido: " + e.getMessage());
            return false;
        } finally {
            try { conn.close(); } catch (SQLException ignored) {}
        }
    }

    public boolean actualizarPedido(int id, String direccion, String tipo) {
        String sql = "UPDATE pedidos SET direccion = ?, tipo = ? WHERE id = ?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, direccion);
            pstmt.setString(2, tipo);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al actualizar pedido: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarPedido(int id) {
        String sql = "DELETE FROM pedidos WHERE id = ?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al eliminar pedido: " + e.getMessage());
            return false;
        }
    }
}