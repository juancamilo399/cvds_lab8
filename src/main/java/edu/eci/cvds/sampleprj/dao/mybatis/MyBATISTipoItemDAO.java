package edu.eci.cvds.sampleprj.dao.mybatis;

import com.google.inject.Inject;


import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.sampleprj.dao.TipoItemDAO;

import edu.eci.cvds.sampleprj.dao.mybatis.mappers.TipoItemMapper;

import edu.eci.cvds.samples.entities.TipoItem;

import java.util.List;

public class MyBATISTipoItemDAO implements TipoItemDAO{

    @Inject
    private TipoItemMapper tipoItemMapper;

    @Override
    public void save(TipoItem tipoItem) throws PersistenceException{
        try{
            tipoItemMapper.addTipoItem(tipoItem);
        }
        catch(org.apache.ibatis.exceptions.PersistenceException e){
            throw new PersistenceException("Error al registrar el tipo de item:"+ tipoItem.toString(),e);
        }
    }

    @Override
    public TipoItem consultarTipoItem(int id) throws PersistenceException {
        try{
            return tipoItemMapper.getTipoItem(id);
        }
        catch(org.apache.ibatis.exceptions.PersistenceException e){
            throw new PersistenceException("Error al consultar el tipo de item item "+id,e);
        }
    }
    @Override
    public List<TipoItem> consultarTiposItem() throws PersistenceException {
        try {
            return tipoItemMapper.getTipoItem();
        } catch (org.apache.ibatis.exceptions.PersistenceException e) {
            throw new PersistenceException("Error al consultar los tipos de item", e);
        }
    }
}