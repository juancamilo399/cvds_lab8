package edu.eci.cvds.sampleprj.dao;

import edu.eci.cvds.samples.entities.TipoItem;

import java.util.List;

public interface TipoItemDAO {
    public void save(TipoItem tipoItem) throws PersistenceException;

    public TipoItem consultarTipoItem(int id) throws PersistenceException;
    public List<TipoItem> consultarTiposItem() throws PersistenceException;



}
