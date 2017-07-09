package com.certicom.ittsa.servlet;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DisplayImage
 */
//@WebServlet("/DisplayImage")
public class DisplayImage extends HttpServlet {
	
	private static final long serialVersionUID = 4593558495041379082L;
    
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PreparedStatement pstmt = null;
		ResultSet rs;
		Connection con;
		
		
		byte[] data = null;
		 //prueba
		try {

			   //obteniendo el archivo de imagen.
			   String codigo = "";
			   codigo = request.getParameter("codigo").toString();
			   
			   Properties prop = new Properties();

	            try {
	                prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("com/certicom/ittsa/propiedades/conexion.properties"));
	            } catch (IOException ex) {
	                ex.printStackTrace();
	            }
			  
			   String urldb = prop.getProperty("jdbc.url");
			   String userbd= prop.getProperty("jdbc.username");
			   String passbd = prop.getProperty("jdbc.password");
			   
			   con = DriverManager.getConnection(urldb,userbd,passbd);
			   if(codigo.substring(0,1).equals("P")){
				   String idPiloto = codigo.substring(1, codigo.length());//request.getParameter("idPiloto").toString();	
				   System.out.println("id cliente:"+idPiloto);
				   if(!idPiloto.equals(""))
				   {
					   /*para meter a bd parametros*/
					   //Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

					   String strSql = "select imagen from  t_piloto where IdPiloto = ?";
					   pstmt = con.prepareStatement(strSql);
			           pstmt.setInt(1,Integer.parseInt(idPiloto));
					   
			           rs = pstmt.executeQuery();
			           
			           if (rs.next()) 
			           {
			            	//System.out.println("resultados encontrados, pintando la imagen");
			 		        data = rs.getBytes("imagen");
			           }else{
			            	System.out.println("No hay registros");
			           }

					   // escribiendo el contenido d ela imagen  a la respuesta.
			           response.reset();
		               response.setContentType("image/jpeg");
		               //solo para sqlserver: jajaj una huada
		               if(data!=null)
		               {
		            	   response.getOutputStream().write(data);
		               }
					   
				   }

			   }else if(codigo.substring(0,1).equals("T")){
				   System.out.println("Obtener Foto de la terramoza");
				   String idTerramoza = codigo.substring(1, codigo.length());
				  
				   if(!idTerramoza.equals(""))
				   {
					  System.out.println("id cliente:"+idTerramoza);
				   String strSql = "select imagen from  t_terramoza where IdTerramoza = ?";
				   pstmt = con.prepareStatement(strSql);
		           pstmt.setInt(1,Integer.parseInt(idTerramoza));
				   
		           rs = pstmt.executeQuery();
		           
		           if (rs.next()) 
		           {
		            	//System.out.println("resultados encontrados, pintando la imagen");
		 		        data = rs.getBytes("imagen");
		           }else{
		            	System.out.println("No hay registros");
		           }

				   // escribiendo el contenido d ela imagen  a la respuesta.
		           response.reset();
	               response.setContentType("image/jpeg");
	               //solo para sqlserver: jajaj una huada
	               if(data!=null)
	               {
	            	   response.getOutputStream().write(data);
	               }
	               
	               System.out.println("fin --------");
	               
				   }
			   }else if(codigo.substring(0, 1).equals("D")){
				   System.out.println("Obtener la imagen del codigo de Barras pdetalle");
				   String idDetalleEncomienda = codigo.substring(1, codigo.length());
				   String strSql = "select CodigoBarras from t_productos_detalleEnc where IdProducto_detalleEnc = ?";
				   pstmt = con.prepareStatement(strSql);
		           pstmt.setInt(1,Integer.parseInt(idDetalleEncomienda));
				   
		           rs = pstmt.executeQuery();
		           
		           if (rs.next()) 
		           {
		            	//System.out.println("resultados encontrados, pintando la imagen");
		 		        data = rs.getBytes("CodigoBarras");
		           }else{
		            	System.out.println("No hay registros");
		           }

				   // escribiendo el contenido d ela imagen  a la respuesta.
		           response.reset();
	               response.setContentType("image/jpeg");
	               //solo para sqlserver: jajaj una huada
	               if(data!=null)
	               {
	            	   response.getOutputStream().write(data);
	               }
			   }
			   else{
				   String idDetalleEncomienda = codigo.substring(1, codigo.length());
				   String strSql = "select CodigoBarras from  t_encomiendaDetalle where IdDetalle = ?";
				   pstmt = con.prepareStatement(strSql);
		           pstmt.setInt(1,Integer.parseInt(idDetalleEncomienda));
				   
		           rs = pstmt.executeQuery();
		           
		           if (rs.next()) 
		           {
		            	//System.out.println("resultados encontrados, pintando la imagen");
		 		        data = rs.getBytes("CodigoBarras");
		           }else{
		            	System.out.println("No hay registros");
		           }

				   // escribiendo el contenido d ela imagen  a la respuesta.
		           response.reset();
	               response.setContentType("image/jpeg");
	               //solo para sqlserver: jajaj una huada
	               if(data!=null)
	               {
	            	   response.getOutputStream().write(data);
	               }
			   }
			
			  
			} catch (IOException e) {
				e.printStackTrace();
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        
	}

}

