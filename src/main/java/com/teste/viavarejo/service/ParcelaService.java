package com.teste.viavarejo.service;

import com.teste.viavarejo.client.SelicRestClient;
import com.teste.viavarejo.domain.Parcela;
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

    @Autowired
    SelicRestClient selicRestClient;

    public List<Parcela> getParcelas(ProdutoCondicaoPagamentoVO produtoCondicaoPagamentoVO) {
        List<Parcela> parcelas = new ArrayList<>();
        Parcela parcela;
        int numeroParcelas = produtoCondicaoPagamentoVO.getCondicaoPagamento().getQtdeParcelas();
        BigDecimal juros = BigDecimal.valueOf(0);
        if (numeroParcelas <= 6) {
            parcela = getPacelaSemJuros(numeroParcelas, produtoCondicaoPagamentoVO);
        } else {
            parcela = getPacelaComJuros(numeroParcelas, produtoCondicaoPagamentoVO);
            juros = parcela.getTaxaJurosAoMes().add(BigDecimal.valueOf(1));
        }

        BigDecimal valorParcela = parcela.getValor();

        for (int i = 1; i <= numeroParcelas; i++) {
            valorParcela = valorParcela.add(valorParcela.multiply(parcela.getTaxaJurosAoMes()));
            parcelas.add(new Parcela(i, valorParcela.setScale(2, BigDecimal.ROUND_HALF_EVEN), juros.setScale(4, BigDecimal.ROUND_HALF_EVEN)));
            juros = juros.multiply(juros);
        }

        return parcelas;
    }

    private Parcela getPacelaComJuros(int numeroParcelas, ProdutoCondicaoPagamentoVO produtoCondicaoPagamentoVO) {
        BigDecimal juros = getTaxaAcumulada();
//        juros = BigDecimal.valueOf(1.15);

        BigDecimal valorProduto = produtoCondicaoPagamentoVO.getProduto().getValor();
        BigDecimal valorEntrada = produtoCondicaoPagamentoVO.getCondicaoPagamento().getValorEntrada();
        BigDecimal valorProdutoSemEntrada = valorProduto.subtract(valorEntrada);
        juros = juros.divide(new BigDecimal(100));
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

    private BigDecimal getTaxaAcumulada() {
        BigDecimal taxaAcumulada = BigDecimal.valueOf(0);
        List<TaxaSelic> taxaSelicList = selicRestClient.getUltimosTaxaSelicAcumuladaPorMeses("1");
        for (TaxaSelic t : taxaSelicList) {
            taxaAcumulada = taxaAcumulada.add(t.getValor());
        }
        return taxaAcumulada;
    }

    private BigDecimal getUltimos30Dias() {
        BigDecimal taxaAcumulada = BigDecimal.valueOf(0);
        List<TaxaSelic> taxaSelicList = selicRestClient.getUltimosTaxaSelicPorDia("30");
        for (TaxaSelic t : taxaSelicList) {
            taxaAcumulada = taxaAcumulada.add(t.getValor());
        }
        return taxaAcumulada;
    }
}
