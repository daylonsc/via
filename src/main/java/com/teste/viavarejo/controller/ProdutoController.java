package com.teste.viavarejo.controller;

import com.teste.viavarejo.entity.Produto;
import com.teste.viavarejo.vo.CondicaoPagamentoVO;
import com.teste.viavarejo.vo.ParcelaVO;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController{

    @GetMapping
    public List<ParcelaVO> buscarParcela(@RequestBody Produto produto, @RequestBody CondicaoPagamentoVO condicaoPagamentoVO) {
        return new ArrayList<ParcelaVO>();
    }
}
