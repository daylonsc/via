package com.teste.viavarejo.client;

import com.teste.viavarejo.domain.TaxaSelic;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(url = "${configuracao.pedido.service.url}", name = "selic")
public interface SelicRestClient {

    @GetMapping("/bcdata.sgs.11/dados?formato=json")
    List<TaxaSelic> getAllTaxaSelic();

    @GetMapping("/bcdata.sgs.11/dados/ultimos/{dias}?formato=json")
    List<TaxaSelic> getUltimosTaxaSelicPorDia(@PathVariable("dias") String dias);

    @GetMapping("/bcdata.sgs.4390/dados/ultimos/{meses}?formato=json")
    List<TaxaSelic> getUltimosTaxaSelicAcumuladaPorMeses(@PathVariable("meses") String meses);


}
