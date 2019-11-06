package com.teste.viavarejo.service;

import com.teste.viavarejo.client.SelicRestClient;
import com.teste.viavarejo.domain.Parcela;
import com.teste.viavarejo.domain.TaxaSelic;
import com.teste.viavarejo.vo.ProdutoCondicaoPagamentoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class ParcelaService {

    @Autowired
    SelicRestClient selicRestClient;

    public List<Parcela> getParcelas(ProdutoCondicaoPagamentoVO produtoCondicaoPagamentoVO) {
        List<Parcela> parcelas = new ArrayList<>();
        Parcela parcela;
        int numeroParcelas = produtoCondicaoPagamentoVO.getCondicaoPagamento().getQtdeParcelas();

        if (numeroParcelas < 6) {
            parcela = getPacelaSemJuros(numeroParcelas, produtoCondicaoPagamentoVO);
        } else {
            parcela = getPacelaComJuros(numeroParcelas, produtoCondicaoPagamentoVO);
        }

        BigDecimal valorParcela = parcela.getValor();
        for (int i = 1; i <= numeroParcelas; i++) {
            valorParcela = valorParcela.add(valorParcela.multiply(parcela.getTaxaJurosAoMes()));
            parcelas.add(new Parcela(i, valorParcela, parcela.getTaxaJurosAoMes()));
        }

        return parcelas;
    }

    private Parcela getPacelaComJuros(int numeroParcelas, ProdutoCondicaoPagamentoVO produtoCondicaoPagamentoVO) {
        BigDecimal juros = getTaxaAcumulada();

        BigDecimal valorProduto = produtoCondicaoPagamentoVO.getProduto().getValor();
        BigDecimal valorEntrada = produtoCondicaoPagamentoVO.getCondicaoPagamento().getValorEntrada();
        BigDecimal valorProdutoSemEntrada = valorProduto.subtract(valorEntrada);
//        BigDecimal juros = taxaSelicAcumulada30dias.divide(new BigDecimal(100));
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
        List<TaxaSelic> taxaSelicList = selicRestClient.getTaxaSelic();
        taxaSelicList = ordernarListaTaxaSelic(taxaSelicList);
        for (TaxaSelic t : taxaSelicList) {
            taxaAcumulada = taxaAcumulada.add(t.getValor());
        }
//        taxaSelicList.forEach(item->taxaAcumulada.add(item.getValor()));
        return taxaAcumulada;
    }

    private Date stringToDate(String data) {
        Date date1 = null;
        try {
            date1 = new SimpleDateFormat("dd/MM/yyyy").parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }

    private List<TaxaSelic> ordernarListaTaxaSelic(List<TaxaSelic> list) {
        list.forEach(item -> item.setDateTime(stringToDate(item.getData())));

        list.sort(Comparator.comparing(TaxaSelic::getDateTime).reversed());

        return new ArrayList<>(list.subList(0, 30));
    }
}
