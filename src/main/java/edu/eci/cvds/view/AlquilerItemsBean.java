package edu.eci.cvds.view;

import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.services.ExcepcionServiciosAlquiler;
import edu.eci.cvds.samples.services.ServiciosAlquiler;

import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.util.List;


@ManagedBean(name="AlquilerItemsBean")
@ApplicationScoped
public class AlquilerItemsBean extends BasePageBean {

    @Inject
    private ServiciosAlquiler serviciosAlquiler;

    public List<Cliente> consultarClientes(){
        List<Cliente> clientes = null;
        try{
            clientes=serviciosAlquiler.consultarClientes();
        } catch (ExcepcionServiciosAlquiler e) {
            setErrorMessage(e);
        }
        return clientes;
    }
    public Cliente consultarCliente(long documento){
        Cliente cliente=null;
        try {
            cliente=serviciosAlquiler.consultarCliente(documento);
        } catch (Exception e) {
            setErrorMessage(e);
        }
        return cliente;
    }

    private void setErrorMessage(Exception e){
        String message = e.getMessage();
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, message, null));
    }

}
