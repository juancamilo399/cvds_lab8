package edu.eci.cvds.test;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.ItemRentado;
import edu.eci.cvds.samples.entities.TipoItem;
import edu.eci.cvds.samples.entities.Item;
import java.text.SimpleDateFormat;
import edu.eci.cvds.samples.services.ExcepcionServiciosAlquiler;
import edu.eci.cvds.samples.services.ServiciosAlquiler;
import edu.eci.cvds.samples.services.ServiciosAlquilerFactory;
import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class ServiciosAlquilerTest {

    @Inject
    private SqlSession sqlSession;

    ServiciosAlquiler serviciosAlquiler;

    public ServiciosAlquilerTest() {
        serviciosAlquiler = ServiciosAlquilerFactory.getInstance().getServiciosAlquilerTesting();
    }

    @Before
    public void setUp() {
    }

    @Test
    public void consultarCostoAlquilerItemNoExistenteTest(){
        try{
            serviciosAlquiler.registrarItem(new Item(new TipoItem(1, "Videojuego" ),99,
                             "item99", "item99", new SimpleDateFormat("yyyy/MM/dd").parse("2019/09/28"),
                                   99,"Digital","99"));
            serviciosAlquiler.consultarCostoAlquiler(89,2);
        }
        catch (ExcepcionServiciosAlquiler e ){
            Assert.assertEquals("El item no existe",e.getMessage());
        }
        catch (Exception e){}

    }
    @Test
    public void consultarCostoAlquilerDiasMenoresA1Test(){
        try{
            serviciosAlquiler.registrarItem(new Item(new TipoItem(1, "Videojuego" ),99,
                    "item99", "item99", new SimpleDateFormat("yyyy/MM/dd").parse("2019/09/28"),
                    99,"Digital","99"));
            serviciosAlquiler.consultarCostoAlquiler(99,-1);
        }
        catch (ExcepcionServiciosAlquiler e ){
            Assert.assertEquals("el numero de dias debe ser mayor o igual a 1",e.getMessage());
        }
        catch (Exception e){}

    }
    @Test
    public void consultarCostoAlquilerDiasParametrosCorrectos(){
        try {
            serviciosAlquiler.registrarItem(new Item(new TipoItem(1, "Videojuego"), 99,
                    "item99", "item99", new SimpleDateFormat("yyyy/MM/dd").parse("2019/09/28"),
                    99, "Digital", "99"));
            Assert.assertEquals(serviciosAlquiler.consultarCostoAlquiler(99, 1), 99);
        }
        catch (Exception e){}

    }





    @Test
    public void emptyDB() {
        for(int i = 0; i < 100; i += 10) {
            boolean r = false;
            try {
                Cliente cliente = serviciosAlquiler.consultarCliente(i);
            } catch(ExcepcionServiciosAlquiler e) {
                r = true;
            } catch(IndexOutOfBoundsException e) {
                r = true;
            }

            // Validate no Client was found;
            Assert.assertTrue(r);
        };
    }
}