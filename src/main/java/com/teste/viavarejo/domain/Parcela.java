package com.teste.viavarejo.domain;

import java.math.BigDecimal;

public class Parcela {

  private int numeroParcela;
  private BigDecimal valor;
  private BigDecimal taxaJurosAoMes;

  public Parcela() {
  }

  public Parcela(BigDecimal valor, BigDecimal taxaJurosAoMes) {
    this.valor = valor;
    this.taxaJurosAoMes = taxaJurosAoMes;
  }

  public Parcela(int numeroParcela, BigDecimal valor, BigDecimal taxaJurosAoMes) {
    this.numeroParcela = numeroParcela;
    this.valor = valor;
    this.taxaJurosAoMes = taxaJurosAoMes;
  }

  public int getNumeroParcela() {
    return numeroParcela;
  }

  public void setNumeroParcela(int numeroParcela) {
    this.numeroParcela = numeroParcela;
  }

  public BigDecimal getValor() {
    return valor;
  }

  public void setValor(BigDecimal valor) {
    this.valor = valor;
  }

  public BigDecimal getTaxaJurosAoMes() {
    return taxaJurosAoMes;
  }

  public void setTaxaJurosAoMes(BigDecimal taxaJurosAoMes) {
    this.taxaJurosAoMes = taxaJurosAoMes;
  }
}
