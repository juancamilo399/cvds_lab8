package edu.eci.cvds.test;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import com.google.inject.Inject;
import java.sql.SQLException;
import java.sql.Statement;
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
import org.junit.After;

import java.sql.Date;

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
    public void consultarItemsDisponiblesTest(){
        try {
            TipoItem tipo1=new TipoItem(1, "Videojuego");
            serviciosAlquiler.registrarTipoItem(tipo1);
            Item a = new Item(tipo1, 99,
                    "item99", "item99", new SimpleDateFormat("yyyy/MM/dd").parse("2019/09/28"),
                    99, "Digital", "99");
            serviciosAlquiler.registrarItem(a);
            ArrayList<Item> items = new ArrayList<Item>();
            items.add(a);

            Assert.assertEquals(items.toString(), serviciosAlquiler.consultarItemsDisponibles().toString());
        }
        catch (Exception e){

        }
    }

    @Test
    public void consultarItemsClienteNoEncontradoTest(){
        try{
            serviciosAlquiler.consultarItemsCliente(2);
        }
        catch (ExcepcionServiciosAlquiler e){
            Assert.assertEquals(e.getMessage(),"El cliente no esta registrado");
        }
    }



    @Test
    public void registrarAlquilerClienteNoEncontradoTest(){
        try{
            TipoItem tipo1=new TipoItem(1, "Videojuego");
            serviciosAlquiler.registrarTipoItem(tipo1);
            Item a = new Item(tipo1, 99,
                    "item99", "item99", new SimpleDateFormat("yyyy/MM/dd").parse("2019/09/28"),
                    99, "Digital", "99");
            serviciosAlquiler.registrarItem(a);
            LocalDate at =LocalDate.parse("2019-09-28");
            serviciosAlquiler.registrarAlquilerCliente(java.sql.Date.valueOf(at) ,2,a,10);
        }
        catch (ExcepcionServiciosAlquiler e){
            Assert.assertEquals("El cliente no esta registrado",e.getMessage());
        }
        catch (Exception e){
        }
    }

    @Test
    public void registrarAlquilerClienteItemNoEncontradoTest(){
        try{
            ArrayList<ItemRentado> itr=new ArrayList<ItemRentado>();
            Cliente cliente= new Cliente("cl",1,"123","abc","aa",false,itr);
            serviciosAlquiler.registrarCliente(cliente);
            TipoItem tipo1=new TipoItem(1, "Videojuego");
            serviciosAlquiler.registrarTipoItem(tipo1);
            Item a = new Item(tipo1, 98,
                    "item99", "item99", new SimpleDateFormat("yyyy/MM/dd").parse("2019/09/28"),
                    99, "Digital", "99");
            LocalDate at =LocalDate.parse("2019-09-28");
            LocalDate af =LocalDate.parse("2019-09-29");
            serviciosAlquiler.registrarAlquilerCliente(java.sql.Date.valueOf(at) ,1,a,1);
        }
        catch (ExcepcionServiciosAlquiler e){
            Assert.assertEquals("El item no esta registrado",e.getMessage());
        }
        catch (Exception e){
        }
    }
    @Test
    public void registrarAlquilerClienteItemYaRegistradoTest(){
        try{
            ArrayList<ItemRentado> itr=new ArrayList<ItemRentado>();
            Cliente cliente= new Cliente("cl",1,"123","abc","aa",false,itr);
            serviciosAlquiler.registrarCliente(cliente);
            TipoItem tipo1=new TipoItem(1, "Videojuego");
            serviciosAlquiler.registrarTipoItem(tipo1);
            Item a = new Item(tipo1, 99,
                    "item99", "item99", new SimpleDateFormat("yyyy/MM/dd").parse("2019/09/28"),
                    99, "Digital", "99");
            serviciosAlquiler.registrarItem(a);
            LocalDate at =LocalDate.parse("2019-09-28");
            LocalDate af =LocalDate.parse("2019-09-29");
            serviciosAlquiler.registrarAlquilerCliente(java.sql.Date.valueOf(at) ,1,a,1);
            serviciosAlquiler.registrarAlquilerCliente(java.sql.Date.valueOf(at) ,1,a,1);
        }
        catch (ExcepcionServiciosAlquiler e){
            Assert.assertEquals("Este item con id: 99 ya se encuentra rentado",e.getMessage());
        }
        catch (Exception e){
        }
    }
    @Test
    public void registrarAlquilerClienteDiasNegativosTest(){
        try{
            ArrayList<ItemRentado> itr=new ArrayList<ItemRentado>();
            Cliente cliente= new Cliente("cl",1,"123","abc","aa",false,itr);
            serviciosAlquiler.registrarCliente(cliente);
            TipoItem tipo1=new TipoItem(1, "Videojuego");
            serviciosAlquiler.registrarTipoItem(tipo1);
            Item a = new Item(tipo1, 99,
                    "item99", "item99", new SimpleDateFormat("yyyy/MM/dd").parse("2019/09/28"),
                    99, "Digital", "99");
            serviciosAlquiler.registrarItem(a);
            LocalDate at =LocalDate.parse("2019-09-28");
            LocalDate af =LocalDate.parse("2019-09-29");
            serviciosAlquiler.registrarAlquilerCliente(java.sql.Date.valueOf(at) ,1,a,-1);
        }
        catch (ExcepcionServiciosAlquiler e){
            Assert.assertEquals("el numero de dias debe ser mayor o igual a 1",e.getMessage());
        }
        catch (Exception e){
        }
    }
    @Test
    public void registrarAlquilerClienteTest(){
        try{
            ArrayList<ItemRentado> itr=new ArrayList<ItemRentado>();
            Cliente cliente= new Cliente("cl",3,"123","abc","aa",false,itr);
            serviciosAlquiler.registrarCliente(cliente);
            TipoItem tipo1=new TipoItem(1, "Videojuego");
            serviciosAlquiler.registrarTipoItem(tipo1);
            Item a = new Item(tipo1, 99,
                    "item99", "item99", new SimpleDateFormat("yyyy/MM/dd").parse("2019/09/28"),
                    99, "Digital", "99");
            serviciosAlquiler.registrarItem(a);
            LocalDate at =LocalDate.parse("2019-09-28");
            LocalDate af =LocalDate.parse("2019-09-29");
            itr.add(new ItemRentado(3,a,java.sql.Date.valueOf(at),java.sql.Date.valueOf(af)));
            serviciosAlquiler.registrarAlquilerCliente(java.sql.Date.valueOf(at) ,3,a,1);
            Assert.assertEquals(itr.toString(),serviciosAlquiler.consultarCliente(3).getRentados().toString());
        }
        catch (Exception e){
        }
    }
    @Test
    public void consultarItemsCliente(){
        try{
            ArrayList<ItemRentado> itr=new ArrayList<ItemRentado>();
            Cliente cliente= new Cliente("cl",8,"123","abc","aa",false,itr);
            serviciosAlquiler.registrarCliente(cliente);
            TipoItem tipo1=new TipoItem(1, "Videojuego");
            serviciosAlquiler.registrarTipoItem(tipo1);
            Item a = new Item(tipo1, 99,
                    "item99", "item99", new SimpleDateFormat("yyyy/MM/dd").parse("2019/09/28"),
                    99, "Digital", "99");
            serviciosAlquiler.registrarItem(a);
            LocalDate at =LocalDate.parse("2019-09-28");
            LocalDate af =LocalDate.parse("2019-09-29");
            itr.add(new ItemRentado(1,a,java.sql.Date.valueOf(at),java.sql.Date.valueOf(af)));
            serviciosAlquiler.registrarAlquilerCliente(java.sql.Date.valueOf(at) ,8,a,1);
            Assert.assertEquals(itr.toString(),serviciosAlquiler.consultarItemsCliente(8).toString());
        }
        catch (Exception e){
        }
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