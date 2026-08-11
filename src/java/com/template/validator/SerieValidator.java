package com.template.validator;

import com.template.util.DialogUtil;

import java.time.Year;

public class SerieValidator {

    public static boolean validarSerie(
            String nome,
            String genero,
            Integer anoLancamento,
            String plataforma) {

        // Verifica campos vazios
        if (nome == null || nome.isEmpty() ||
                genero == null || genero.isEmpty() ||
                anoLancamento == null ||
                plataforma == null || plataforma.isEmpty()) {

            DialogUtil.showWarning("Preencha todos os campos!");
            return false;
        }

        // Valida nome
        if (nome.length() < 3) {
            DialogUtil.showWarning(
                    "O nome da série deve ter pelo menos 3 caracteres!"
            );
            return false;
        }

        // Valida ano
        int anoAtual = Year.now().getValue();

        if (anoLancamento < 1900 || anoLancamento > anoAtual) {
            DialogUtil.showWarning(
                    "Informe um ano de lançamento válido!"
            );
            return false;
        }

        // Valida gênero
        if (genero.length() < 3) {
            DialogUtil.showWarning(
                    "Informe um gênero válido!"
            );
            return false;
        }

        // Valida plataforma
        if (plataforma.length() < 3) {
            DialogUtil.showWarning(
                    "Informe uma plataforma válida!"
            );
            return false;
        }

        return true;
    }
}