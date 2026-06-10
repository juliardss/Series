package com.template;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;


public class MainController {

    @FXML private Button btnSalvar;
    @FXML private Button btnDeletar;
    @FXML private Button btnAtualizar;
    @FXML private TextField txtId;
    @FXML private TextField txtNome;
    @FXML private TextField txtGenero;
    @FXML private TextField txtAnoLancamento;
    @FXML private TextField txtPlataforma;
    @FXML private TableView<SerieDTO> tblSerie;
    @FXML private TableColumn<SerieDTO, Integer> colId;
    @FXML private TableColumn<SerieDTO, String> colNome;
    @FXML private TableColumn<SerieDTO, String> colGenero;
    @FXML private TableColumn<SerieDTO, Integer> colAnoLancamento;
    @FXML private TableColumn<SerieDTO, String> colPlataforma;

    @FXML
    private void btnSalvarAction(ActionEvent event) {

        String nome = txtNome.getText();
        String genero = txtGenero.getText();
        int anoLancamento = Integer.parseInt(txtAnoLancamento.getText());
        String plataforma = txtPlataforma.getText();

        SerieDTO objseriedto = new SerieDTO();
        objseriedto.setNome(nome);
        objseriedto.setGenero(genero);
        objseriedto.setAnoLancamento(anoLancamento);
        objseriedto.setPlataforma(plataforma);

        SerieDAO objseriedao = new SerieDAO();
        objseriedao.cadastrarSerie(objseriedto);

        carregarSerie();

    }
    @FXML
    private void btnAtualizarAction(ActionEvent event){
        int id = Integer.parseInt(txtId.getText());
        String nome = txtNome.getText();
        String genero = txtGenero.getText();
        int anoLancamento = Integer.parseInt(txtAnoLancamento.getText());
        String plataforma = txtPlataforma.getText();

        SerieDTO objseriedto = new SerieDTO();
        objseriedto.setId(id);
        objseriedto.setNome(nome);
        objseriedto.setGenero(genero);
        objseriedto.setAnoLancamento(anoLancamento);
        objseriedto.setPlataforma(plataforma);

        SerieDAO objseriedao = new SerieDAO();
        objseriedao.atualizarSerie(objseriedto);
        carregarSerie();
    }

    @FXML
    private void btnDeletarAction(ActionEvent event){
        int id = Integer.parseInt(txtId.getText());

        SerieDAO objseriedao = new SerieDAO();
        objseriedao.deletarSerie(id);
        carregarSerie();
    }


    @FXML
    private void btnLimparAction(ActionEvent event){
        txtId.clear();
        txtNome.clear();
        txtAnoLancamento.clear();
        txtGenero.clear();
        txtPlataforma.clear();
        txtNome.requestFocus();// Garante o foco para o usuário continuar digitando via TAB
    }

    @FXML
    private void initialize(){
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colAnoLancamento.setCellValueFactory(new PropertyValueFactory<>("anoLancamento"));
        colGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        colPlataforma.setCellValueFactory(new PropertyValueFactory<>("plataforma"));

        carregarSerie();
    }

    @FXML
    private void carregarSerie(){
        SerieDAO objSerieDAO = new SerieDAO();
        ArrayList<SerieDTO> listaSerie = objSerieDAO.listarSerie();
        tblSerie.setItems(FXCollections.observableArrayList(listaSerie));
    }

    @FXML
    private void carregarCampos(){
        SerieDTO serieDTO = tblSerie.getSelectionModel().getSelectedItem();
        txtId.setText(String.valueOf((serieDTO.getId())));
        txtNome.setText(serieDTO.getNome());
        txtAnoLancamento.setText(String.valueOf((serieDTO.getAnoLancamento())));
        txtGenero.setText(serieDTO.getGenero());
        txtPlataforma.setText(serieDTO.getPlataforma());
    }
}
