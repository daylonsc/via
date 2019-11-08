package com.teste.viavarejo;

import static org.junit.Assert.assertEquals;

import com.teste.viavarejo.domain.CondicaoPagamento;
import com.teste.viavarejo.domain.Parcela;
import com.teste.viavarejo.domain.Produto;
import com.teste.viavarejo.service.ParcelaService;
import com.teste.viavarejo.vo.ProdutoCondicaoPagamentoVO;
import java.math.BigDecimal;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ParcelaControllerTest {

  @InjectMocks
  ParcelaService parcelaServiceMock;

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void getParcelasSemJuros() {
    List<Parcela> list = parcelaServiceMock.getParcelas(initObject(6));
    assertEquals(list.size() <= 6, true);
  }

  @Test
  public void getParcelasComJuros() {
    List<Parcela> list = parcelaServiceMock.getParcelas(initObject(10));
    assertEquals(list.size() > 6, true);
  }

  private ProdutoCondicaoPagamentoVO initObject(int quantidadeParcelas) {
    ProdutoCondicaoPagamentoVO produtoCondicaoPagamentoVO = new ProdutoCondicaoPagamentoVO();
    Produto produto = new Produto();
    CondicaoPagamento condicaoPagamento = new CondicaoPagamento();

    produto.setCodigo(Long.valueOf(123));
    produto.setNome("Produto teste");
    produto.setValor(BigDecimal.valueOf(10000.00));

    condicaoPagamento.setQtdeParcelas(quantidadeParcelas);
    condicaoPagamento.setValorEntrada(BigDecimal.valueOf(500.00));

    produtoCondicaoPagamentoVO.setProduto(produto);
    produtoCondicaoPagamentoVO.setCondicaoPagamento(condicaoPagamento);

    return produtoCondicaoPagamentoVO;
  }
}
