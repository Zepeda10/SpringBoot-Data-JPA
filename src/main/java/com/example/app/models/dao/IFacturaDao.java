package com.example.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.app.models.entity.Factura;

public interface IFacturaDao extends CrudRepository<Factura, Long>{

}
