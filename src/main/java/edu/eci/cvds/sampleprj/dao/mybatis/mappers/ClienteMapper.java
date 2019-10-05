package edu.eci.cvds.sampleprj.dao.mybatis.mappers;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import edu.eci.cvds.samples.entities.Cliente;

/**
 *
 * @author 2106913
 */
public interface ClienteMapper {
    
    public Cliente consultarCliente(@Param ("idcli") long id);
    public List<Cliente> consultarCliente();
    
    /**
     * Registrar un nuevo item rentado asociado al cliente identificado
     * con 'idc' y relacionado con el item identificado con 'idi'
     * @param id
     * @param idit
     * @param fechainicio
     * @param fechafin 
     */
    public void agregarItemRentadoACliente(@Param("idcl") long id,
            @Param("idit") int idit,
            @Param("Fechai") Date fechainicio,
            @Param("Fechaf") Date fechafin);

    /**
     * Consultar todos los clientes
     * @return 
     */

    public void insertarCliente(@Param("cliente") Cliente cliente);

    public void vetarCliente(@Param("cliente") long idCliente,@Param("estado") int estado);
}
