package com.teste.viavarejo.vo;

import java.math.BigDecimal;

public class CondicaoPagamentoVO {
    private BigDecimal valorEntrada;
    private int qtdeParcela;

    public BigDecimal getValorEntrada() {
        return valorEntrada;
    }

    public void setValorEntrada(BigDecimal valorEntrada) {
        this.valorEntrada = valorEntrada;
    }

    public int getQtdeParcela() {
        return qtdeParcela;
    }

    public void setQtdeParcela(int qtdeParcela) {
        this.qtdeParcela = qtdeParcela;
    }
}
