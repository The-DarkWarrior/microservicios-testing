package com.usuarios.service.feignClients;

import com.usuarios.service.modelos.Carro;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "carro-service")
public interface CarroFeignClient {
    @RequestMapping(method = RequestMethod.POST, value = "/carro")
    Carro save(@RequestBody Carro carro);

    @RequestMapping(method = RequestMethod.GET, value = "/carro/usuario/{usuarioId}")
    List<Carro> getCarros(@PathVariable("usuarioId") int usuarioId);
}
