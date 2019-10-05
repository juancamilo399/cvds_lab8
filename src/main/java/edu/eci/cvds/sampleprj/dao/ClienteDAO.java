package edu.eci.cvds.sampleprj.dao;


import edu.eci.cvds.samples.entities.Cliente;

import java.util.Date;
import java.util.List;

public interface ClienteDAO {

    public void save(Cliente cl) throws PersistenceException;

    public Cliente consultarCliente(long id) throws PersistenceException;

    public List<Cliente> consultarClientes() throws PersistenceException;

    public void agregarItemRentadoACliente(long idCliente, int idItem, Date fechainicio,Date fechafin) throws PersistenceException;

    public void vetarCliente(long idCliente,int estado) throws PersistenceException;
}

