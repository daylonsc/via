package com.teste.viavarejo.service;

import com.teste.viavarejo.domain.Parcela;
import com.teste.viavarejo.vo.ProdutoCondicaoPagamentoVO;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ParcelaService {

  public List<Parcela> getParcelas(ProdutoCondicaoPagamentoVO produtoCondicaoPagamentoVO) {
    List<Parcela> parcelas = new ArrayList<>();
    Parcela parcela;
    int numeroParcelas = produtoCondicaoPagamentoVO.getCondicaoPagamento().getQtdeParcelas();

    if (numeroParcelas < 6) {
      parcela = getPacelaSemJuros(numeroParcelas, produtoCondicaoPagamentoVO);
    } else {
      parcela = getPacelaComJuros(numeroParcelas, produtoCondicaoPagamentoVO);
    }

    BigDecimal valorParcela =  parcela.getValor();
    for (int i = 1; i <= numeroParcelas; i++) {
      valorParcela = valorParcela.add(valorParcela.multiply(parcela.getTaxaJurosAoMes()));
      parcelas.add(new Parcela(i, valorParcela, parcela.getTaxaJurosAoMes()));
    }

    return parcelas;
  }

  private Parcela getPacelaComJuros(int numeroParcelas, ProdutoCondicaoPagamentoVO produtoCondicaoPagamentoVO) {
    BigDecimal taxaJuros = BigDecimal.valueOf(1.15); //TODO PEGAR DA SELIC

    BigDecimal valorProduto = produtoCondicaoPagamentoVO.getProduto().getValor();
    BigDecimal valorEntrada = produtoCondicaoPagamentoVO.getCondicaoPagamento().getValorEntrada();
    BigDecimal valorProdutoSemEntrada = valorProduto.subtract(valorEntrada);
    BigDecimal juros = taxaJuros.divide(new BigDecimal(100));
    BigDecimal potencia = juros.add(BigDecimal.ONE).pow(numeroParcelas);
    BigDecimal denominador = BigDecimal.ONE.subtract(BigDecimal.ONE.divide(potencia, 20, RoundingMode.HALF_EVEN));
    BigDecimal valorParcelaComJuros = valorProdutoSemEntrada.multiply(juros).divide(denominador, 2, RoundingMode.HALF_EVEN);
    BigDecimal valorParcelaSemJuros = valorProdutoSemEntrada.divide(new BigDecimal(numeroParcelas), 2, RoundingMode.HALF_EVEN);

    return new Parcela(valorParcelaSemJuros, juros);
  }

  private Parcela getPacelaSemJuros(int numeroParcelas, ProdutoCondicaoPagamentoVO produtoCondicaoPagamentoVO) {
    BigDecimal valorProduto = produtoCondicaoPagamentoVO.getProduto().getValor();
    BigDecimal valorEntrada = produtoCondicaoPagamentoVO.getCondicaoPagamento().getValorEntrada();
    BigDecimal valorProdutoSemEntrada = valorProduto.subtract(valorEntrada);
    BigDecimal valorParcelaSemJuros = valorProdutoSemEntrada.divide(new BigDecimal(numeroParcelas), 2, RoundingMode.HALF_EVEN);

    return new Parcela(valorParcelaSemJuros, BigDecimal.valueOf(0));
  }

}
