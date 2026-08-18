package com.template.service;

import com.template.model.dao.SerieDAO;
import com.template.model.dto.SerieDTO;

import java.util.ArrayList;

public class SerieService {

    private final SerieDAO serieDAO;

    public SerieService() {
        serieDAO = new SerieDAO();
    }

    public void cadastrar(SerieDTO serie) {
        serieDAO.cadastrarSerie(serie);
    }

    public void atualizar(SerieDTO serie) {
        serieDAO.atualizarSerie(serie);
    }

    public void deletar(int id) {
        serieDAO.deletarSerie(id);
    }

    public ArrayList<SerieDTO> listar() {
        return serieDAO.listarSerie();
    }
}