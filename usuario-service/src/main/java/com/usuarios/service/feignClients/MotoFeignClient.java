package com.usuarios.service.feignClients;
import com.usuarios.service.modelos.Moto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.List;

@FeignClient(name = "moto-service")
public interface MotoFeignClient {
    @RequestMapping(method = RequestMethod.POST, value = "/moto")
    Moto save(@RequestBody Moto moto);

    @RequestMapping(method = RequestMethod.GET, value = "/moto/usuario/{usuarioId}")
    List<Moto> getMotos(@PathVariable("usuarioId") int usuarioId);
}
