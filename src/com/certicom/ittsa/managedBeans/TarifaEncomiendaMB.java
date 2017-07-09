package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.util.Date;
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
import com.certicom.ittsa.domain.Tarifa;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.TarifaServices;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

/***
 * 
 * @since 11.03.2015 @drequejo 
 * 	Se corrige error de listado de tarifarios por agencia, agregando un map {@link #mapAgenciaListaTarifaEncomienda} para evitar el incorrecto uso compartido de {@link #listaTarifaGiro} para listar las tarifas
 *
 */
@ManagedBean(name="tarifaEncomiendaMB")
@ViewScoped
public class TarifaEncomiendaMB  extends GenericBeans implements Serializable{
	
	private static final long serialVersionUID = 8279196732253305714L;
	
	private String titulo;
	private Tarifa tarifa;
	
	private List<Tarifa> listaFiltroTarifaGiro;
	private List<Agencia> listaAgencias;
	
	private Boolean editar;
	private String simbolo;
	
	private MenuServices menuServices;
	private AgenciaService agenciaServices;
	private TarifaServices tarifaServices;

	// 11.03.2015 drequejo
	private Map<Integer, List<Tarifa>> mapAgenciaListaTarifaEncomienda;
	
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	public TarifaEncomiendaMB(){}
	
	@PostConstruct
	public void inicia()
	{
		this.agenciaServices = new AgenciaService();
		this.tarifaServices= new TarifaServices();
		this.tarifa = new Tarifa();
		
		mapAgenciaListaTarifaEncomienda = new HashMap<Integer, List<Tarifa>>();
		
		try {
			this.listaAgencias = agenciaServices.findAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void setearTipoCobro(){
		if(this.tarifa.getPorcentual()){
			
		}
	}
	
	public void onRowToggle(ToggleEvent event) {  
		Integer idAgencia =  ((Agencia) event.getData()).getIdagencia();
		System.out.println("Elemento Selecccionado:" + idAgencia);
		List<Tarifa> listaTarifaGiro = null;
		
		try {
			listaTarifaGiro = tarifaServices.findGirosByTipo(idAgencia,"E");
			
			for(Tarifa t: listaTarifaGiro){
				//Agencia ag = agenciaServices.findById(t.getIdOrigen());
				//t.setNombOrigen(ag.getDescripcion());
				Agencia des= agenciaServices.findById(t.getIdDestino());
				t.setNombDestino(des.getDescripcion()); 
				if(t.getPorcentual()){
					t.setPrecioString(t.getPrecioEnvioNormal()+" %"); 
					t.setPrecioCorporativoString(t.getPrecioEnvioCorporativo() + " %"); 
				}else{
					t.setPrecioString("S/. "+t.getPrecioEnvioNormal()); 
					t.setPrecioCorporativoString("S/. "+ t.getPrecioEnvioCorporativo()); 
				}
			}
			mapAgenciaListaTarifaEncomienda.put(idAgencia, listaTarifaGiro);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }  
	
	public void nuevoTarifaGiro()
	{
		this.editar = Boolean.FALSE;
		this.tarifa = new Tarifa();
		this.titulo = "Registrar nueva tarifa de encomienda";

		
		
	}
	
	public void editarTarifaGiro(Tarifa tar)
	{
		this.editar = Boolean.TRUE;
		this.tarifa = tar;
		this.titulo = "Modificar tarifa de encomienda";
	}
	
	
	public void guardarTarifaGiro()
	{
		
		RequestContext context = RequestContext.getCurrentInstance();  
		
		if(this.editar)
		{
			try {
				this.tarifaServices.actualizarTarifaEncomienda(this.tarifa);
				FacesUtils.showFacesMessage("Servicio actualizado correctamente.",Constante.INFORMACION);
				context.update("sms");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			try {
				this.tarifa.setfRegistro(new Date());
				this.tarifa.setTipo("E");
				this.tarifaServices.crearTarifaEncomienda(this.tarifa);
				FacesUtils.showFacesMessage("Tarifa de giro registrada correctamente.",Constante.INFORMACION);
				 context.update("sms");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	

	
	public void cambiarEstado(Tarifa tar){
		
		if(tar.getEstado()){
			tar.setEstado(Boolean.FALSE);
		}else{
			tar.setEstado(Boolean.TRUE);
		}
		
		try {
			this.tarifaServices.actualizarEstado(tar);
			FacesUtils.showFacesMessage("Se cambio de estado.",Constante.INFORMACION);
		} catch (Exception e) {
			System.out.println("erro actualizar estado usuario"+e.getMessage());	
			e.printStackTrace();
		}
		 
	 }
	
	
	
	public void eliminarTarifaGiro()
	{
		try {
			
			this.tarifaServices.eliminarTarifa(tarifa.getIdTarifa()); 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
		
	/*############################-------setter y getter---------##############################3*/

	
	public Tarifa getTarifa() {
		return tarifa;
	}

	public void setTarifa(Tarifa tarifa) {
		this.tarifa = tarifa;
	}

	public List<Tarifa> getListaFiltroTarifaGiro() {
		return listaFiltroTarifaGiro;
	}

	public void setListaFiltroTarifaGiro(List<Tarifa> listaFiltroTarifaGiro) {
		this.listaFiltroTarifaGiro = listaFiltroTarifaGiro;
	}

	public Boolean getEditar() {
		return editar;
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

	public String getSimbolo() {
		return simbolo;
	}

	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public Map<Integer, List<Tarifa>> getMapAgenciaListaTarifaEncomienda() {
		return mapAgenciaListaTarifaEncomienda;
	}
	
	public void setMapAgenciaListaTarifaEncomienda(
			Map<Integer, List<Tarifa>> mapAgenciaListaTarifaEncomienda) {
		this.mapAgenciaListaTarifaEncomienda = mapAgenciaListaTarifaEncomienda;
	}
	

	

}
