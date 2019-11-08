package com.teste.viavarejo.domain;

import java.math.BigDecimal;

public class CondicaoPagamento {
    private BigDecimal valorEntrada;

    private int qtdeParcelas;

    public BigDecimal getValorEntrada() {
        return valorEntrada;
    }

    public void setValorEntrada(BigDecimal valorEntrada) {
        this.valorEntrada = valorEntrada;
    }

    public int getQtdeParcelas() {
        return qtdeParcelas;
    }

    public void setQtdeParcelas(int qtdeParcelas) {
        this.qtdeParcelas = qtdeParcelas;
    }
}
