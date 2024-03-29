package com.usuarios.service.feignClients;
import com.usuarios.service.modelos.Moto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "moto-service")
@RequestMapping("/moto")
public interface MotoFeignClient {
    @PostMapping()
    public Moto save(@RequestBody Moto moto);

    @GetMapping( "/usuario/{usuarioId}")
    public List<Moto> getMotos(@PathVariable("usuarioId") int usuarioId);
}
