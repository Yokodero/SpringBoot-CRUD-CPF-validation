package com.api_cliente.cliente.Repository;

import com.api_cliente.cliente.Entity.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long>
{
    
}
