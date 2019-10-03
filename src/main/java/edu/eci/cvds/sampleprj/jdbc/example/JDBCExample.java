/*
 * Copyright (C) 2015 hcadavid
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.eci.cvds.sampleprj.jdbc.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hcadavid
 */
public class JDBCExample {
    
    public static void main(String[] args){
        try {
            String url="jdbc:mysql://desarrollo.is.escuelaing.edu.co:3306/bdprueba";
            String driver="com.mysql.jdbc.Driver";
            String user="bdprueba";
            String pwd="prueba2019";
                        
            Class.forName(driver);
            Connection con=DriverManager.getConnection(url,user,pwd);
            con.setAutoCommit(false);
                 
            
            System.out.println("Valor total pedido 1:"+valorTotalPedido(con, 1));
            
            List<String> prodsPedido=nombresProductosPedido(con, 1);
            
            
            System.out.println("Productos del pedido 1:");
            System.out.println("-----------------------");
            for (String nomprod:prodsPedido){
                System.out.println(nomprod);
            }
            System.out.println("-----------------------");
            
            
            int suCodigoECI=2152725;
            registrarNuevoProducto(con, suCodigoECI, "Rojas", 99999999);            
            con.commit();
                        
            
            con.close();
                                   
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(JDBCExample.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    /**
     * Agregar un nuevo producto con los parámetros dados
     * @param con la conexión JDBC
     * @param codigo
     * @param nombre
     * @param precio
     * @throws SQLException 
     */
    public static void registrarNuevoProducto(Connection con, int codigo, String nombre,int precio) throws SQLException{
        //Crear preparedStatement
        //Asignar parámetros
        //usar 'execute'
		PreparedStatement insertProduct;
        String insertString="insert into ORD_PRODUCTOS (codigo,nombre,precio) values ( ? ,? ,? )";
		insertProduct=con.prepareStatement(insertString);
		insertProduct.setInt(1,codigo);
		insertProduct.setString(2,nombre);
		insertProduct.setInt(3,precio);
		insertProduct.execute();
		insertProduct.close();
		con.commit();
        
    }
    
    /**
     * Consultar los nombres de los productos asociados a un pedido
     * @param con la conexión JDBC
     * @param codigoPedido el código del pedido
     * @return 
     */
    public static List<String> nombresProductosPedido(Connection con, int codigoPedido)throws SQLException {
		  
        //Crear prepared statement
		
		
		PreparedStatement selectProducts= null;
		String selectString = "select pr.nombre from ORD_DETALLE_PEDIDO d,ORD_PEDIDOS p, ORD_PRODUCTOS pr where p.codigo =d.pedido_fk and d.producto_fk=pr.codigo and p.codigo= ?";
		ResultSet rs;
		String nombreProducto;
		List<String> np=new LinkedList<>();
	
		selectProducts=con.prepareStatement(selectString);
		//asignar parámetros
		selectProducts.setInt(1,codigoPedido);
	
		//usar executeQuery
		
		rs=selectProducts.executeQuery();
		while(rs.next()){
			//Sacar resultados del ResultSet
			nombreProducto=rs.getString(1);
			//llenar lista
			if(!np.contains(nombreProducto))
			np.add(nombreProducto);
		}
		selectProducts.close();
		rs.close();
		con.commit();
		
    
        return np;
    }

    
    /**
     * Calcular el costo total de un pedido
     * @param con conexión
     * @param codigoPedido código del pedido cuyo total se calculará
     * @return el costo total del pedido (suma de: cantidades*precios)
     */
    public static int valorTotalPedido(Connection con, int codigoPedido)throws SQLException{
        
        //Crear prepared statement
		
		
		PreparedStatement selectValor= null;
		String selectString = "select sum(pr.precio*d.cantidad) as suma  from ORD_DETALLE_PEDIDO d,ORD_PEDIDOS p, ORD_PRODUCTOS pr  where p.codigo =d.pedido_fk and d.producto_fk=pr.codigo and p.codigo= ?";
		ResultSet rs;
		int valorPedido=0;
	
		selectValor=con.prepareStatement(selectString);
		//asignar parámetros
		selectValor.setInt(1,codigoPedido);
		
		//usar executeQuery
		
		rs=selectValor.executeQuery();
		if(rs.next()){
			valorPedido  =rs.getInt("suma");
		}

		selectValor.close();
		rs.close();
		con.commit();
        return valorPedido;
    }
    

    
    
    
}