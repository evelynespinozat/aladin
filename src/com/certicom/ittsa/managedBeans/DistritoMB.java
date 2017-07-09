package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;
import org.primefaces.event.ToggleEvent;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.CategoriaServicio;
import com.certicom.ittsa.domain.ClaseServicio;
import com.certicom.ittsa.domain.Destino;
import com.certicom.ittsa.domain.Distrito;
import com.certicom.ittsa.domain.Excepcion;
import com.certicom.ittsa.domain.Frecuencia;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.RutaServicio;
import com.certicom.ittsa.domain.Servicio;
import com.certicom.ittsa.domain.Tarifa;
import com.certicom.ittsa.domain.Usuario;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.CategoriaServicioService;
import com.certicom.ittsa.services.ClaseServicioServices;
import com.certicom.ittsa.services.DestinoServices;
import com.certicom.ittsa.services.DistritoServices;
import com.certicom.ittsa.services.FrecuenciaService;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.ProgramacionService;
import com.certicom.ittsa.services.RutaServicioService;
import com.certicom.ittsa.services.ServicioService;
import com.certicom.ittsa.services.TarifaServices;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="distritoMB")
@ViewScoped
public class DistritoMB  extends GenericBeans implements Serializable{
	
	private Distrito distrito;
	private List<Distrito> listaDistrito;
	private List<Distrito> listaFiltroDistrito;
	private List<Agencia> listaAgencias;
	private List<Agencia> listaAgenciasFilter;
	
	private Boolean editar;
	private Integer idOrigen;
	private String simbolo;
	
	private MenuServices menuServices;
	private AgenciaService agenciaServices;
	private DistritoServices distritoServices;

	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	public DistritoMB(){}
	
	@PostConstruct
	public void inicia()
	{
		this.agenciaServices = new AgenciaService();
		this.distritoServices= new DistritoServices();
		this.distrito = new Distrito();
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
		this.idOrigen = idAgencia;
		try {
			this.listaDistrito = distritoServices.findByAgencia(idAgencia); 
			this.listaAgencias = agenciaServices.findAll();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }  
	
	public void nuevoDistrito()
	{
		this.editar = Boolean.FALSE;
		this.distrito = new Distrito();

	}
	
	public void editarDistrito(Distrito dis)
	{
		this.editar = Boolean.TRUE;
		this.distrito = dis;
	}
	
	
	public void guardarDistrito()
	{
		
		RequestContext context = RequestContext.getCurrentInstance();  
		
		if(this.editar)
		{
			try {
				//this.tarifa.setIdOrigen(getIdOrigen()); 
				this.distritoServices.actualizarDistrito(this.distrito);
				FacesUtils.showFacesMessage("Distrito actualizado correctamente.",Constante.INFORMACION);
				context.update("sms");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			//System.out.println("imrimiendo hora:"+this.servicio.getHsalida());
			
			try {
				//this.tarifa.setfRegistro(new Date());
				//this.tarifa.setTipo("G");
				//this.tarifa.setIdOrigen(getIdOrigen());
				this.distritoServices.crearDistrito(this.distrito);
				FacesUtils.showFacesMessage("Distrito registrado correctamente.",Constante.INFORMACION);
				 context.update("sms");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	

	/*
	public void cambiarEstado(Tarifa tar){
		
		if(tar.getEstado()){
			tar.setEstado(Boolean.FALSE);
		}else{
			tar.setEstado(Boolean.TRUE);
		}
		
		try {
			this.distritoServices.actualizarEstado(tar);
			FacesUtils.showFacesMessage("Se cambio de estado",Constante.INFORMACION);
		} catch (Exception e) {
			System.out.println("erro actualizar estado usuario"+e.getMessage());	
			e.printStackTrace();
		}
		 
	 }
	
	*/
	
	public void eliminarDistrito()
	{
		try {
			
			this.distritoServices.eliminarDistrito(distrito.getIdDistrito()); 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
		
	/*############################-------setter y getter---------##############################3*/

	


	public Boolean getEditar() {
		return editar;
	}

	public Distrito getDistrito() {
		return distrito;
	}

	public void setDistrito(Distrito distrito) {
		this.distrito = distrito;
	}

	public List<Distrito> getListaDistrito() {
		return listaDistrito;
	}

	public void setListaDistrito(List<Distrito> listaDistrito) {
		this.listaDistrito = listaDistrito;
	}

	public List<Distrito> getListaFiltroDistrito() {
		return listaFiltroDistrito;
	}

	public void setListaFiltroDistrito(List<Distrito> listaFiltroDistrito) {
		this.listaFiltroDistrito = listaFiltroDistrito;
	}

	public List<Agencia> getListaAgenciasFilter() {
		return listaAgenciasFilter;
	}

	public void setListaAgenciasFilter(List<Agencia> listaAgenciasFilter) {
		this.listaAgenciasFilter = listaAgenciasFilter;
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
	
	
	

	

}
