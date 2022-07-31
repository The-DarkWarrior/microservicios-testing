package com.usuarios.service.feignClients;

import com.usuarios.service.modelos.Carro;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "carro-service")
@RequestMapping("/carro")
public interface CarroFeignClient {
    @PostMapping()
    Carro save(@RequestBody Carro carro);

    @GetMapping( "/usuario/{usuarioId}")
    List<Carro> getCarros(@PathVariable("usuarioId") int usuarioId);
}
