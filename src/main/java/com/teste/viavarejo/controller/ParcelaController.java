package com.teste.viavarejo.controller;

import com.teste.viavarejo.domain.Parcela;
import com.teste.viavarejo.service.ParcelaService;
import com.teste.viavarejo.vo.ProdutoCondicaoPagamentoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/parcelas")
public class ParcelaController{

    @Autowired
    ParcelaService parcelaService;

    @PostMapping
    public List<Parcela> buscarParcela(@RequestBody ProdutoCondicaoPagamentoVO produtoCondicaoPagamentoVO) {
     return parcelaService.getParcelas(produtoCondicaoPagamentoVO);
    }
}
