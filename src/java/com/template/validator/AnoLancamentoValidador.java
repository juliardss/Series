package com.template.validator;

public class AnoLancamentoValidador implements Validador<String> {

    private final String ano;

    public AnoLancamentoValidador(String ano) {
        this.ano = ano;
    }

    @Override
    public boolean validar(String valor) {
        return this.ano != null && this.ano.matches("\\d{4}");
    }

    @Override
    public String getMensagemError() {
        return "Digite um ano de lançamento válido (exemplo: 2020)!";
    }

    @Override
    public String getValor() {
        return ano;
    }
}