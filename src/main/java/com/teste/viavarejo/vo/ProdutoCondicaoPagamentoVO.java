package com.teste.viavarejo.vo;

import com.teste.viavarejo.domain.CondicaoPagamento;
import com.teste.viavarejo.domain.Produto;

public class ProdutoCondicaoPagamentoVO {
  private Produto produto;
  private CondicaoPagamento condicaoPagamento;

  public Produto getProduto() {
    return produto;
  }

  public void setProduto(Produto produto) {
    this.produto = produto;
  }

  public CondicaoPagamento getCondicaoPagamento() {
    return condicaoPagamento;
  }

  public void setCondicaoPagamento(CondicaoPagamento condicaoPagamento) {
    this.condicaoPagamento = condicaoPagamento;
  }
}
