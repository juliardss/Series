package com.template.controller;

import com.template.model.dto.SerieDTO;
import com.template.service.SerieService;
import com.template.validator.SerieValidator;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;

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

    private final SerieService serieService = new SerieService();


    @FXML
    private void btnSalvarAction(ActionEvent event) {

        try {

            int anoLancamento =
                    Integer.parseInt(txtAnoLancamento.getText());

            if (!SerieValidator.validarSerie(
                    txtNome.getText(),
                    txtGenero.getText(),
                    anoLancamento,
                    txtPlataforma.getText())) {
                return;
            }

            SerieDTO serie = criarSerie(anoLancamento);

            serieService.cadastrar(serie);

            carregarSerie();
            limparCampos();

            showInformation("Salvo com sucesso!");

        } catch (NumberFormatException e) {

            showWarning("Informe um ano de lançamento válido!");
        }
    }


    @FXML
    private void btnAtualizarAction(ActionEvent event) {

        try {

            int id = Integer.parseInt(txtId.getText());

            int anoLancamento =
                    Integer.parseInt(txtAnoLancamento.getText());

            if (!SerieValidator.validarSerie(
                    txtNome.getText(),
                    txtGenero.getText(),
                    anoLancamento,
                    txtPlataforma.getText())) {
                return;
            }

            SerieDTO serie = criarSerie(anoLancamento);
            serie.setId(id);

            serieService.atualizar(serie);

            carregarSerie();
            limparCampos();

            showInformation("Atualizado com sucesso!");

        } catch (NumberFormatException e) {

            showWarning("Selecione uma série e informe um ano válido!");
        }
    }


    @FXML
    private void btnDeletarAction(ActionEvent event) {

        try {

            int id = Integer.parseInt(txtId.getText());

            serieService.deletar(id);

            // Atualiza a tabela depois da exclusão
            tblSerie.getItems().clear();
            tblSerie.getItems().addAll(
                    serieService.listar()
            );

            limparCampos();

            carregarSerie();
            showInformation("Deletado com sucesso!");


        } catch (NumberFormatException e) {

            showWarning("Selecione uma série para deletar!");
        }
    }


    private SerieDTO criarSerie(int anoLancamento) {

        SerieDTO serie = new SerieDTO();

        serie.setNome(txtNome.getText());
        serie.setGenero(txtGenero.getText());
        serie.setAnoLancamento(anoLancamento);
        serie.setPlataforma(txtPlataforma.getText());

        return serie;
    }


    @FXML
    private void btnLimparAction(ActionEvent event) {

        limparCampos();
    }


    private void limparCampos() {

        txtId.clear();
        txtNome.clear();
        txtAnoLancamento.clear();
        txtGenero.clear();
        txtPlataforma.clear();

        txtNome.requestFocus();
    }


    private void configurarTabela() {

        colId.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );

        colNome.setCellValueFactory(
                new PropertyValueFactory<>("nome")
        );

        colGenero.setCellValueFactory(
                new PropertyValueFactory<>("genero")
        );

        colAnoLancamento.setCellValueFactory(
                new PropertyValueFactory<>("anoLancamento")
        );

        colPlataforma.setCellValueFactory(
                new PropertyValueFactory<>("plataforma")
        );
    }


    private void configurarAno() {

        txtAnoLancamento.textProperty().addListener(
                (observable, oldValue, newValue) -> {

                    if (!newValue.matches("\\d*")) {

                        txtAnoLancamento.setText(
                                newValue.replaceAll("[^\\d]", "")
                        );
                    }
                }
        );
    }


    private void configurarTeclas() {

        tblSerie.sceneProperty().addListener(
                (observable, oldScene, newScene) -> {

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
                                limparCampos();
                            }
                        });
                    }
                }
        );
    }


    private void carregarSerie() {

        tblSerie.getItems().clear();

        tblSerie.getItems().addAll(
                serieService.listar()
        );
    }


    @FXML
    private void carregarCampos() {

        SerieDTO serie =
                tblSerie.getSelectionModel().getSelectedItem();

        if (serie == null) {
            return;
        }

        txtId.setText(String.valueOf(serie.getId()));
        txtNome.setText(serie.getNome());
        txtAnoLancamento.setText(
                String.valueOf(serie.getAnoLancamento())
        );
        txtGenero.setText(serie.getGenero());
        txtPlataforma.setText(serie.getPlataforma());
    }


    @FXML
    private void initialize() {

        configurarTabela();
        configurarAno();
        configurarTeclas();
        carregarSerie();
    }
}