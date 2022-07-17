package com.usuarios.service.repositorio;

import com.usuarios.service.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  UsuarioRepository extends JpaRepository<Usuario, Integer> {
}
