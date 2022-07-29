package com.usuarios.service.controller;

import com.usuarios.service.entidades.Usuario;
import com.usuarios.service.modelos.Carro;
import com.usuarios.service.modelos.Moto;
import com.usuarios.service.servicio.UsuarioService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios(){
        List<Usuario> usuarios = usuarioService.getAll();
        if(usuarios.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuario(
            @PathVariable("id") int  id
            ){
        Usuario usuario = usuarioService.getUsuarioById(id);
        if(usuario == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(usuario);
    }

    @PostMapping
    public ResponseEntity<Usuario> guardarUsuario(@RequestBody Usuario usuario){
        Usuario nuevoUsuario = usuarioService.save(usuario);
        return ResponseEntity.ok(nuevoUsuario);
    }

    @CircuitBreaker(name = "carrosCB", fallbackMethod = "fallbackGetCarros")
    @GetMapping("/carros/{usuarioId}")
    public ResponseEntity<List<Carro>> listarCarros(@PathVariable("usuarioId") int usuarioId) {
        Usuario usuario = usuarioService.getUsuarioById(usuarioId);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        List<Carro> carros = usuarioService.getCarros(usuarioId);
        return ResponseEntity.ok(carros);
    }

    @CircuitBreaker(name = "motosCB", fallbackMethod = "fallbackGetMotos")
    @GetMapping("/motos/{usuarioId}")
    public ResponseEntity<List<Moto>> listarMotos(@PathVariable("usuarioId") int usuarioId) {
        Usuario usuario = usuarioService.getUsuarioById(usuarioId);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        List<Moto> motos = usuarioService.getMotos(usuarioId);
        return ResponseEntity.ok(motos);
    }

    @CircuitBreaker(name = "carrosCB", fallbackMethod = "fallbackSaveCarros")
    @PostMapping("/carro/{usuarioId}")
    public ResponseEntity<Carro> guardarCarro(@PathVariable("usuarioId") int usuarioId, @RequestBody Carro carro) {
        Carro nuevoCarro = usuarioService.saveCarro(usuarioId, carro);
        return ResponseEntity.ok(nuevoCarro);
    }

    @CircuitBreaker(name = "motosCB", fallbackMethod = "fallbackSaveMotos")
    @PostMapping("/moto/{usuarioId}")
    public ResponseEntity<Moto> guardarMoto(@PathVariable("usuarioId") int usuarioId, @RequestBody Moto moto) {
        Moto nuevaMoto = usuarioService.saveMoto(usuarioId, moto);
        return ResponseEntity.ok(nuevaMoto);
    }

    @CircuitBreaker(name = "todosCB", fallbackMethod = "fallbackGetTodos")
    @GetMapping("/todos/{usuarioId}")
    public ResponseEntity<Map<String, Object>> ListarTodosLosVehiculos(@PathVariable("usuarioId") int usuarioId) {
        Map<String, Object> resultado = usuarioService.getUsuarioAndVehiculos(usuarioId);
        return ResponseEntity.ok(resultado);
    }

    private ResponseEntity<List<Carro>> fallbackGetCarros(@PathVariable("usuarioId") int id, RuntimeException exception) {
        return new ResponseEntity("El usuario: " + id + " tiene los carros en el taller", HttpStatus.OK);
    }

    private ResponseEntity<Carro> fallbackSaveCarros(@PathVariable("usuarioId") int id, @RequestBody Carro carro, RuntimeException exception) {
        return new ResponseEntity("El usuario: " + id + " no puede guardar en el siguiente carro " + carro, HttpStatus.OK);
    }

    private ResponseEntity<List<Moto>> fallbackGetMotos(@PathVariable("usuarioId") int id, RuntimeException exception) {
        return new ResponseEntity("El usuario: " + id + " tiene las motos en el taller", HttpStatus.OK);
    }

    private ResponseEntity<Moto> fallbackSaveMotos(@PathVariable("usuarioId") int id, @RequestBody Moto moto, RuntimeException exception) {
        return new ResponseEntity("El usuario: " + id + " no puede guardar en el siguiente moto " + moto, HttpStatus.OK);
    }

    private ResponseEntity<Map<String, Object>> fallbackGetTodos(@PathVariable("usuarioId") int id, RuntimeException exception) {
        return new ResponseEntity("El usuario: " + id + " no tiene vehiculos", HttpStatus.OK);
    }
}
