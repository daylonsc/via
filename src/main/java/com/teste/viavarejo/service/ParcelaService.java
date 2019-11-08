package com.teste.viavarejo.service;

import com.teste.viavarejo.client.SelicRestClient;
import com.teste.viavarejo.domain.CondicaoPagamento;
import com.teste.viavarejo.domain.Parcela;
import com.teste.viavarejo.domain.Produto;
import com.teste.viavarejo.domain.TaxaSelic;
import com.teste.viavarejo.vo.ProdutoCondicaoPagamentoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class ParcelaService {

    private final int MAIOR = 1;
    private final int IGUAL = 0;
    private final int MENOR = -1;

    private SelicRestClient selicRestClient;

    @Autowired
    public ParcelaService(SelicRestClient selicRestClient) {
        this.selicRestClient = selicRestClient;
    }

    public List<Parcela> getParcelas(ProdutoCondicaoPagamentoVO produtoCondicaoPagamentoVO) {

        validarCampos(produtoCondicaoPagamentoVO);

        List<Parcela> parcelas = new ArrayList<>();
        int numeroParcelas = produtoCondicaoPagamentoVO.getCondicaoPagamento().getQtdeParcelas();
        BigDecimal juros = BigDecimal.ZERO;
        BigDecimal taxaJuros = BigDecimal.ZERO;

        if (numeroParcelas > 6) {
            taxaJuros = getTaxaJuros();
            juros = taxaJuros.add(BigDecimal.ONE);
        }

        BigDecimal parcelaSemJuros = getValorParcelaSemJuros(produtoCondicaoPagamentoVO, numeroParcelas);

        for (int i = 1; i <= numeroParcelas; i++) {
            Parcela parcela = new Parcela();
            parcela.setNumeroParcela(i);
            parcela.setTaxaJurosAoMes(taxaJuros.setScale(6, RoundingMode.HALF_EVEN));
            parcela.setValorParcelaSemJuros(parcelaSemJuros);
            parcela.setValorParcelaComJuros(taxaJuros.compareTo(BigDecimal.ZERO) == MAIOR ? getValorParcelaComJuros(parcelaSemJuros, taxaJuros, i) : parcelaSemJuros);
            parcela.setValorJuros(parcela.getValorParcelaComJuros().subtract(parcelaSemJuros));

            parcelas.add(parcela);

            juros = juros.multiply(juros);
            parcela.setJurosAcumulado(juros.setScale(4, RoundingMode.HALF_EVEN));
        }

        return parcelas;
    }

    private BigDecimal getValorParcelaSemJuros(ProdutoCondicaoPagamentoVO produtoCondicaoPagamentoVO, int numeroParcelas) {
        BigDecimal valorProduto = produtoCondicaoPagamentoVO.getProduto().getValor();
        BigDecimal valorEntrada = produtoCondicaoPagamentoVO.getCondicaoPagamento().getValorEntrada();
        BigDecimal valorTotal = valorProduto.subtract(valorEntrada);

        return valorTotal.divide(BigDecimal.valueOf(numeroParcelas), 2, RoundingMode.HALF_EVEN);
    }

    private BigDecimal getValorParcelaComJuros(BigDecimal valorParcelaSemJuros, BigDecimal taxaJuros, int numeroParcela) {
        BigDecimal potencia = taxaJuros.add(BigDecimal.ONE).pow(numeroParcela);
        BigDecimal denominador = BigDecimal.ONE.subtract(BigDecimal.ONE.divide(potencia, 20, RoundingMode.HALF_EVEN));
        BigDecimal valorParcelaComJuros = valorParcelaSemJuros.multiply(taxaJuros).divide(denominador, 2, RoundingMode.HALF_EVEN);
        return valorParcelaComJuros.multiply(BigDecimal.valueOf(numeroParcela));
    }

    private BigDecimal getTaxaJuros() {
        BigDecimal juros = getTaxaAcumulada();
        juros = juros.divide(BigDecimal.valueOf(100));
        return juros;
    }

    private BigDecimal getTaxaAcumulada() {
        return selicRestClient.getUltimosTaxaSelicAcumuladaPorMeses("1").get(0).getValor();
    }

    private List<TaxaSelic> getUltimos30Dias() {
        return selicRestClient.getUltimosTaxaSelicPorDia("30");
    }

    private void validarCampos(ProdutoCondicaoPagamentoVO produtoCondicaoPagamentoVO) {
        Produto produto = produtoCondicaoPagamentoVO.getProduto();
        CondicaoPagamento condicaoPagamento = produtoCondicaoPagamentoVO.getCondicaoPagamento();

        if (condicaoPagamento.getValorEntrada().compareTo(produto.getValor()) == MAIOR) {
            throw new RuntimeException("Entrada não pode ser maior que o valor do produto!");
        } else if (condicaoPagamento.getValorEntrada().compareTo(produto.getValor()) == IGUAL) {
            throw new RuntimeException("Entrada não pode ser igual a o valor do produto!");
        } else if (condicaoPagamento.getQtdeParcelas() < 1) {
            throw new RuntimeException("Total de parcela tem que ser maior que zero!");
        }
    }
}
