package com.example.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.app.models.entity.Cliente;

public interface IClienteDao extends CrudRepository<Cliente, Long>{
	
}
