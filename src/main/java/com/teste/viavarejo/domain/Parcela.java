package com.teste.viavarejo.domain;

import java.math.BigDecimal;

public class Parcela {

    private int numeroParcela;
    private BigDecimal taxaJurosAoMes;
    private BigDecimal jurosAcumulado;
    private BigDecimal valorJuros;
    private BigDecimal valorParcelaSemJuros;
    private BigDecimal valorParcelaComJuros;

    public Parcela() {
    }

    public Parcela(BigDecimal taxaJurosAoMes, BigDecimal valorParcelaSemJuros) {
        this.taxaJurosAoMes = taxaJurosAoMes;
        this.valorParcelaSemJuros = valorParcelaSemJuros;
    }

    public int getNumeroParcela() {
        return numeroParcela;
    }

    public void setNumeroParcela(int numeroParcela) {
        this.numeroParcela = numeroParcela;
    }

    public BigDecimal getTaxaJurosAoMes() {
        return taxaJurosAoMes;
    }

    public void setTaxaJurosAoMes(BigDecimal taxaJurosAoMes) {
        this.taxaJurosAoMes = taxaJurosAoMes;
    }

    public BigDecimal getJurosAcumulado() {
        return jurosAcumulado;
    }

    public void setJurosAcumulado(BigDecimal jurosAcumulado) {
        this.jurosAcumulado = jurosAcumulado;
    }

    public BigDecimal getValorJuros() {
        return valorJuros;
    }

    public void setValorJuros(BigDecimal valorJuros) {
        this.valorJuros = valorJuros;
    }

    public BigDecimal getValorParcelaSemJuros() {
        return valorParcelaSemJuros;
    }

    public void setValorParcelaSemJuros(BigDecimal valorParcelaSemJuros) {
        this.valorParcelaSemJuros = valorParcelaSemJuros;
    }

    public BigDecimal getValorParcelaComJuros() {
        return valorParcelaComJuros;
    }

    public void setValorParcelaComJuros(BigDecimal valorParcelaComJuros) {
        this.valorParcelaComJuros = valorParcelaComJuros;
    }
}
