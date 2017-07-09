package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;
import org.primefaces.event.ToggleEvent;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Tarifa_Producto;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.Tarifa_ProductoServices;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;
import com.pe.certicom.ittsa.commons.Utiles;

/**
 * 
 * @since 11.03.2015 @drequejo 
 * 	Se corrige error de listado de tarifarios por agencia, agregando un map {@link #mapAgenciaListaTarifaProducto} para evitar el incorrecto uso compartido de {@link #listaTarifa_Producto} para listar las tarifas
 *
 */
@ManagedBean(name="tarifa_productoMB")
@ViewScoped
public class Tarifa_ProductoMB  extends GenericBeans implements Serializable{
	
	private static final long serialVersionUID = -2911293203799763769L;
	
	private String titulo;
	private Tarifa_Producto tarifa_producto;
	private List<Tarifa_Producto> listaFiltroTarifa_Producto;
	private List<Agencia> listaAgencias;
	private List<Agencia> listaFiltroAgencias;
	
	private Boolean editar;
	private Integer idOrigen;
	private Integer idDestino;
	private String simbolo;
	
	private MenuServices menuServices;
	private AgenciaService agenciaServices;
	private Tarifa_ProductoServices tarifa_ProductoServices;

	// 11.03.2015 drequejo
	private Map<Integer, List<Tarifa_Producto>> mapAgenciaListaTarifaProducto;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	public Tarifa_ProductoMB(){}
	
	@PostConstruct
	public void inicia()
	{
		this.agenciaServices = new AgenciaService();
		this.tarifa_ProductoServices= new Tarifa_ProductoServices();
		this.tarifa_producto = new Tarifa_Producto();
		
		mapAgenciaListaTarifaProducto = new HashMap<Integer, List<Tarifa_Producto>>();
		
		try {
			this.listaAgencias = agenciaServices.findAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	public void onRowToggle(ToggleEvent event) {  
		Integer idAgencia =  ((Agencia) event.getData()).getIdagencia();
		System.out.println("Elemento Selecccionado:" + idAgencia);
		
		try {
			List<Tarifa_Producto> listaTarifa_Producto = tarifa_ProductoServices.findByIdAgencia(idAgencia);  
			mapAgenciaListaTarifaProducto.put(idAgencia, listaTarifa_Producto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }  
	
	public void nuevoTarifa_Producto()
	{
		this.editar = Boolean.FALSE;
		this.tarifa_producto = new Tarifa_Producto();
		this.titulo = "Registrar nueva tarifa de producto";

		
		
	}
	
	public void editarTarifa_Producto(Tarifa_Producto tar)
	{
		this.editar = Boolean.TRUE;
		this.tarifa_producto = tar;
		this.titulo = "Modificar tarifa de producto";
		//this.idOrigen = tarifa.getIdOrigen();
		//this.idDestino = tarifa.getIdDestino();
	}
	
	
	public void guardarTarifa_Producto()
	{
		
		RequestContext context = RequestContext.getCurrentInstance();  
		this.tarifa_producto.setDescripcion(this.tarifa_producto.getDescripcion().toUpperCase());
		
		if(this.editar)
		{
			try {
				this.tarifa_ProductoServices.actualizarTarifa_Producto(this.tarifa_producto);
				FacesUtils.showFacesMessage("Tarifa de producto actualizada correctamente.",Constante.INFORMACION);
				context.update("sms");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			try {
				Double Precio = Double.parseDouble(this.tarifa_producto.getPrecioEnvio().toString());
				Double Peso = Double.parseDouble(this.tarifa_producto.getPeso().toString());
				Double PrecioKilo = Precio/Peso;
				this.tarifa_producto.setPrecioKilo(Utiles.round(new BigDecimal(PrecioKilo), 2, true)  ); 
				this.tarifa_ProductoServices.crearTarifa_Producto(this.tarifa_producto); 
				FacesUtils.showFacesMessage("Tarifa de giro registrada correctamente.",Constante.INFORMACION);
				context.update("sms");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
	
	
	public void eliminarTarifa_Producto()
	{
		try {
			
			this.tarifa_ProductoServices.eliminarTarifa_Producto(tarifa_producto.getIdTarifa_Producto()); 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
		
	/*############################-------setter y getter---------##############################3*/

	
	

	public Boolean getEditar() {
		return editar;
	}

	public Tarifa_Producto getTarifa_producto() {
		return tarifa_producto;
	}

	public void setTarifa_producto(Tarifa_Producto tarifa_producto) {
		this.tarifa_producto = tarifa_producto;
	}

	public List<Tarifa_Producto> getListaFiltroTarifa_Producto() {
		return listaFiltroTarifa_Producto;
	}

	public void setListaFiltroTarifa_Producto(
			List<Tarifa_Producto> listaFiltroTarifa_Producto) {
		this.listaFiltroTarifa_Producto = listaFiltroTarifa_Producto;
	}

	public List<Agencia> getListaFiltroAgencias() {
		return listaFiltroAgencias;
	}

	public void setListaFiltroAgencias(List<Agencia> listaFiltroAgencias) {
		this.listaFiltroAgencias = listaFiltroAgencias;
	}

	public void setEditar(Boolean editar) {
		this.editar = editar;
	}

	public List<Agencia> getListaAgencias() {
		return listaAgencias;
	}

	public void setListaAgencias(List<Agencia> listaAgencias) {
		this.listaAgencias = listaAgencias;
	}

	public Integer getIdOrigen() {
		return idOrigen;
	}

	public void setIdOrigen(Integer idOrigen) {
		this.idOrigen = idOrigen;
	}

	public String getSimbolo() {
		return simbolo;
	}

	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}

	public Integer getIdDestino() {
		return idDestino;
	}

	public void setIdDestino(Integer idDestino) {
		this.idDestino = idDestino;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public Map<Integer, List<Tarifa_Producto>> getMapAgenciaListaTarifaProducto() {
		return mapAgenciaListaTarifaProducto;
	}
	
	public void setMapAgenciaListaTarifaProducto(
			Map<Integer, List<Tarifa_Producto>> mapAgenciaListaTarifaProducto) {
		this.mapAgenciaListaTarifaProducto = mapAgenciaListaTarifaProducto;
	}

}
