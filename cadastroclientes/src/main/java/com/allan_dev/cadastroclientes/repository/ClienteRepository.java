package com.allan_dev.cadastroclientes.repository;

import com.allan_dev.cadastroclientes.entity.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
