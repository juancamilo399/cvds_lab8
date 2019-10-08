package edu.eci.cvds.sampleprj.dao;

import edu.eci.cvds.samples.entities.Item;

import java.util.List;

public interface ItemDAO {

    public void save(Item it) throws PersistenceException;

    public Item consultarItem(int id) throws PersistenceException;

    public List<Item> consultarItems() throws PersistenceException;

    public List<Item> consultarDisponibles() throws PersistenceException;

    public void actualizarTarifaItem( int id, long tarifa) throws PersistenceException;

}