package com.template.controller;


import com.template.model.dao.SerieDAO;
import com.template.model.dto.SerieDTO;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;


import java.util.ArrayList;
import static com.template.util.DialogUtil.*;



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


        SerieDTO objSerieDTO = new SerieDTO();
        objSerieDTO.setNome(nome);
        objSerieDTO.setGenero(genero);
        objSerieDTO.setAnoLancamento(anoLancamento);
        objSerieDTO.setPlataforma(plataforma);


        SerieDAO objSerieDAO = new SerieDAO();
        objSerieDAO.cadastrarSerie(objSerieDTO);
        carregarSerie();
        btnLimparAction(null);
        showInformation("Salvo com sucesso");


    }
    @FXML
    private void btnAtualizarAction(ActionEvent event){
        int id = Integer.parseInt(txtId.getText());
        String nome = txtNome.getText();
        String genero = txtGenero.getText();
        int anoLancamento = Integer.parseInt(txtAnoLancamento.getText());
        String plataforma = txtPlataforma.getText();


        SerieDTO objSerieDTO = new SerieDTO();
        objSerieDTO.setId(id);
        objSerieDTO.setNome(nome);
        objSerieDTO.setGenero(genero);
        objSerieDTO.setAnoLancamento(anoLancamento);
        objSerieDTO.setPlataforma(plataforma);


        SerieDAO objSerieDAO = new SerieDAO();
        objSerieDAO.atualizarSerie(objSerieDTO);
        carregarSerie();
    }


    @FXML
    private void btnDeletarAction(ActionEvent event){
        int id = Integer.parseInt(txtId.getText());


        SerieDAO objSerieDAO = new SerieDAO();
        objSerieDAO.deletarSerie(id);
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
        txtAnoLancamento.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtAnoLancamento.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });


        carregarSerie();
        tblSerie.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {


                newScene.setOnKeyPressed(event -> {


                    if (event.getCode() == KeyCode.ENTER) {
                        btnSalvar.fire();
                    }


                    if (event.getCode() == KeyCode.F2) {
                        btnAtualizar.fire();
                    }


                    if (event.getCode() == KeyCode.DELETE) {
                        btnDeletar.fire();
                    }


                    if (event.getCode() == KeyCode.ESCAPE) {
                        btnLimparAction(null);
                    }


                });
            }
        });
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

