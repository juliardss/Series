package com.template;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SerieDAO {

    private static final Logger logger = Logger.getLogger(SerieDAO.class.getName());
    ArrayList<SerieDTO> listaSeries = new ArrayList<>();
    public void cadastrarSerie(SerieDTO serie) {

        String sql = "INSERT INTO serie (nome, ano_lancamento, genero, plataforma) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexao.conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, serie.getNome());
            ps.setInt(2, serie.getAnoLancamento());
            ps.setString(3, serie.getGenero());
            ps.setString(4, serie.getPlataforma());

            ps.executeUpdate(); // executa insert

            System.out.println("\nSerie cadastrada com sucesso!");

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao cadastrar série: " + e.getMessage());
        }

    }


    public ArrayList<SerieDTO> listarSerie() {

        String sql = "SELECT * FROM serie";

        try (Connection conn = Conexao.conectar(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            System.out.println("\n===== LISTA DE SERIES =====");

            while (rs.next()) {
                SerieDTO serie = new SerieDTO();
                serie.setId(rs.getInt("id"));
                serie.setNome(rs.getString("nome"));
                serie.setAnoLancamento(rs.getInt("ano_lancamento"));
                serie.setGenero(rs.getString("genero"));
                serie.setPlataforma(rs.getString("plataforma"));
                listaSeries.add(serie);

            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE,"Erro ao listar series: " + e.getMessage());
        }
        return listaSeries;
    }

    public void atualizarSerie(SerieDTO serie) {

        String sql = "UPDATE serie SET nome = ?, genero = ?, ano_lancamento = ?, plataforma = ? WHERE id = ?";

        try (Connection conn = Conexao.conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, serie.getNome());
            ps.setString(2, serie.getGenero());
            ps.setInt(3, serie.getAnoLancamento());
            ps.setString(4, serie.getPlataforma());
            ps.setInt(5, serie.getId());

            ps.executeUpdate();

            logger.log(Level.SEVERE,"\nSerie atualizada com sucesso!");

        } catch (SQLException e) {
            logger.log(Level.SEVERE,"Erro ao atualizar serie: " + e.getMessage());
        }
    }

    public void deletarSerie(int id) {

        String sql = "DELETE FROM serie WHERE id = ?";

        try (Connection conn = Conexao.conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            ps.executeUpdate();

            logger.log(Level.SEVERE,"\nSerie deletada com sucesso!");

        } catch (SQLException e) {
            logger.log(Level.SEVERE,"Erro ao deletar serie: " + e.getMessage());
        }
    }

}

