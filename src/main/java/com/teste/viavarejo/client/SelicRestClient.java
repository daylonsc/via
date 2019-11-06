package com.teste.viavarejo.client;

import com.teste.viavarejo.domain.TaxaSelic;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(url = "${configuracao.pedido.service.url}", name = "selic")
public interface SelicRestClient {

    @GetMapping("/dados/serie/bcdata.sgs.11/dados?formato=json")
    List<TaxaSelic> getTaxaSelic();
}
