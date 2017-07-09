package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.util.ArrayList;
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
import com.certicom.ittsa.domain.Destino;
import com.certicom.ittsa.domain.DistritoCategoria;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Tarifa;
import com.certicom.ittsa.domain.Ubigeo;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.DestinoServices;
import com.certicom.ittsa.services.DistritoCategoriaService;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.TarifaServices;
import com.certicom.ittsa.services.UbigeoService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

/**
 * 
 * @since 11.03.2015 @drequejo 
 * 	Se corrige error de listado de tarifarios por agencia, agregando un map {@link #mapAgenciaListaTarifaDestino} para evitar el incorrecto uso compartido de {@link #listaDestino} para listar las agencias destino
 *
 */
@ManagedBean(name="tarifaRepartoMB")
@ViewScoped
public class TarifaRepartoMB  extends GenericBeans implements Serializable{
	
	private static final long serialVersionUID = 6602340387989105163L;
	
	private Tarifa tarifa;
	private List<Tarifa> listaTarifaReparto;
	private List<Tarifa> listaFiltroTarifaReparto;
	private List<Agencia> listaAgencias;
	private List<Agencia> listaFilterAgencias;
	
	private List<Destino> listaFilterDestino;
	
	private DistritoCategoria distritoCategoria;
	private Tarifa disSelec;
	private List<DistritoCategoria> listaCategoriasDis;
	
	private List<Agencia> listaAgDestinos;
	private List<Ubigeo> listaUbigeo;
	
	private Boolean editar;
	private Integer idOrigen;
	private Integer idDestino;
	private String simbolo;
	
	private MenuServices menuServices;
	private AgenciaService agenciaServices;
	private TarifaServices tarifaServices;
	private DestinoServices destinoServices;
	private UbigeoService ubigeoService;
	private DistritoCategoriaService distritoCategoriaService;

	// 11.03.2015 drequejo
	private Map<Integer, List<Destino>> mapAgenciaListaTarifaDestino;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	public TarifaRepartoMB(){}
	
	@PostConstruct
	public void inicia()
	{
		this.agenciaServices = new AgenciaService();
		this.tarifaServices= new TarifaServices();
		this.destinoServices = new DestinoServices();
		this.ubigeoService = new UbigeoService();
		this.tarifa = new Tarifa();
		this.distritoCategoriaService = new DistritoCategoriaService();
		
		mapAgenciaListaTarifaDestino = new HashMap<Integer, List<Destino>>();
		
		try {
			this.listaAgencias = agenciaServices.findAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void listarAgeDestino(){
		
		this.listaAgDestinos = new ArrayList<Agencia>();
		for(Agencia ag : this.listaAgencias)
		{
			if(this.tarifa.getIdOrigen()!=ag.getIdagencia())
			{
				this.listaAgDestinos.add(ag);
			}
		}

	}
	
	public void listarDistritos(){
		this.listaTarifaReparto = new ArrayList<>();
		Agencia a;
		try {
			a = this.agenciaServices.findById(this.tarifa.getIdDestino()); 
			this.listaUbigeo = ubigeoService.listardistritosByProvincia(a.getDescripcion());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void listarTarifas(Destino d){
		this.listaTarifaReparto = new ArrayList<>();
		Agencia a = new Agencia();
		try {
			a = this.agenciaServices.findById(d.getDestino());
			this.listaUbigeo = ubigeoService.listardistritosByProvincia(a.getDescripcion());
			System.out.println("el tamaño de la lista es  "  + this.listaUbigeo.size() );
			for(Ubigeo u : listaUbigeo){
				Tarifa t = null;
				System.out.println("el destino y el distrito son = -------- " + d.getDestino() + " - - --  - "  + u.getSdistrito());
				//t = tarifaServices.findByDestProvincia(d.getDestino(),u.getSdistrito());
				t = tarifaServices.findByOriDestProvincia(d.getOrigen(),d.getDestino(),u.getSdistrito());
				if(t!=null){
					listaTarifaReparto.add(t);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void onRowToggleDestinos(ToggleEvent event) {  
		Integer idAgencia =  ((Agencia) event.getData()).getIdagencia();
		System.out.println("Agencia Origen:" + idAgencia);
		
		try {
			List<Destino> listaDestino = destinoServices.obtenerDestino(idAgencia); 
			/*this.listaAgencias = agenciaServices.findAll();
			for(Tarifa t: listaTarifaReparto){
				Agencia ag = agenciaServices.findById(t.getIdOrigen());
				t.setNombOrigen(ag.getDescripcion());
				Agencia des= agenciaServices.findById(t.getIdDestino());
				t.setNombDestino(des.getDescripcion()); 
				
				if(t.getPorcentual()){
					t.setPrecioString(t.getPrecioEnvioNormal()+" %"); 
					t.setPrecioCorporativoString(t.getPrecioEnvioCorporativo() + " %"); 
				}else{
					t.setPrecioString("S/. "+t.getPrecioEnvioNormal()); 
					t.setPrecioCorporativoString("S/. "+ t.getPrecioEnvioCorporativo()); 
				}			
			}*/
			mapAgenciaListaTarifaDestino.put(idAgencia, listaDestino);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }  
	
	public void onRowToggleTarifas(ToggleEvent event) {  
		Integer idDestino =  ((Destino) event.getData()).getDestino();
		System.out.println("Agencia Destino:" + idDestino);
		
		try {
			this.listaTarifaReparto = this.tarifaServices.findRepartoByTipo(this.tarifa.getIdOrigen(), this.tarifa.getIdDestino(), "R");
			/*this.listaAgencias = agenciaServices.findAll();
			for(Tarifa t: listaTarifaReparto){
				Agencia ag = agenciaServices.findById(t.getIdOrigen());
				t.setNombOrigen(ag.getDescripcion());
				Agencia des= agenciaServices.findById(t.getIdDestino());
				t.setNombDestino(des.getDescripcion()); 
				
				if(t.getPorcentual()){
					t.setPrecioString(t.getPrecioEnvioNormal()+" %"); 
					t.setPrecioCorporativoString(t.getPrecioEnvioCorporativo() + " %"); 
				}else{
					t.setPrecioString("S/. "+t.getPrecioEnvioNormal()); 
					t.setPrecioCorporativoString("S/. "+ t.getPrecioEnvioCorporativo()); 
				}			
			}*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }  
	
	public void nuevoTarifaGiro()
	{
		this.editar = Boolean.FALSE;
		this.tarifa = new Tarifa();


		
		
	}
	
	public void editarTarifaReparto(Tarifa tar)
	{
		this.editar = Boolean.TRUE;
		this.tarifa = tar;
	}
	
	
	public void guardarTarifaGiro()
	{
		
		RequestContext context = RequestContext.getCurrentInstance();  
		
		if(this.editar)
		{
			try {
				this.tarifaServices.actualizarTarifaEncomienda(this.tarifa);
				FacesUtils.showFacesMessage("Tarifa de reparto actualizada correctamente.",Constante.INFORMACION);
				context.update("msgEditar");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			
			try {
				Tarifa t = tarifaServices.findByDestProvincia(this.tarifa.getIdDestino(), this.tarifa.getSdistrito());
				if(t==null){
					this.tarifa.setfRegistro(new Date());
					this.tarifa.setTipo("R");
					Ubigeo u = this.ubigeoService.obtenerUbigeoById(this.tarifa.getSid_ubigeo());
					this.tarifa.setSdistrito(u.getSdistrito());
					this.tarifaServices.crearTarifaReparto(this.tarifa);
					FacesUtils.showFacesMessage("Tarifa de reparto registrada correctamente.",Constante.INFORMACION);
					context.update("sms");
				}else{
					FacesUtils.showFacesMessage("Tarifa se encuentra ya registrada.",Constante.ERROR);
					context.update("sms");
				}
				
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
			FacesUtils.showFacesMessage("Estado de tarifa de reparto actualizado correctamente.",Constante.INFORMACION);
		} catch (Exception e) {
			System.out.println("error actualizar estado usuario"+e.getMessage());	
			e.printStackTrace();
		}
		 
	 }
	
	
	
	public void eliminarTarifaReparto()
	{
		try {
			
			this.tarifaServices.eliminarTarifa(tarifa.getIdTarifa()); 
			this.listaTarifaReparto = new ArrayList<>();
			Agencia a;
		
			a = this.agenciaServices.findById(tarifa.getIdDestino());
			this.listaUbigeo = ubigeoService.listardistritosByProvincia(a.getDescripcion());
			for(Ubigeo u : listaUbigeo){
			Tarifa t = null;
			t = tarifaServices.findByDestProvincia(tarifa.getIdDestino(),u.getSdistrito());
				if(t!=null){
					listaTarifaReparto.add(t);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	// AGREGADO 26/03/2014
	public void consultarCategoriaReparto(Tarifa t){
		this.distritoCategoria = new DistritoCategoria();
		this.disSelec = t;
		try {
			this.listaCategoriasDis = this.distritoCategoriaService.listarCategoriaxDistrito(t.getSid_ubigeo());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void registrarCategoriaDistrito(){
		try {
			this.distritoCategoria.setSid_ubigeo(this.disSelec.getSid_ubigeo());
			this.distritoCategoriaService.registrarDistritoCategoria(this.distritoCategoria);
			FacesUtils.showFacesMessage("Categoría de reparto para este distrito registrada correctamente.",Constante.INFORMACION);
			this.distritoCategoria = new DistritoCategoria(); 
			this.listaCategoriasDis = this.distritoCategoriaService.listarCategoriaxDistrito(this.disSelec.getSid_ubigeo());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void eliminarCatDistrito(DistritoCategoria dc){
		try {
			this.distritoCategoriaService.eliminarDistritoCategoria(dc.getId());
			FacesUtils.showFacesMessage("Categoría de reparto eliminada correctamente.",Constante.INFORMACION);
			this.listaCategoriasDis = this.distritoCategoriaService.listarCategoriaxDistrito(this.disSelec.getSid_ubigeo());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	/*############################-------setter y getter---------##############################3*/

	
	
	public Tarifa getTarifa() {
		return tarifa;
	}

	public List<Tarifa> getListaTarifaReparto() {
		return listaTarifaReparto;
	}

	public void setListaTarifaReparto(List<Tarifa> listaTarifaReparto) {
		this.listaTarifaReparto = listaTarifaReparto;
	}

	public List<Tarifa> getListaFiltroTarifaReparto() {
		return listaFiltroTarifaReparto;
	}

	public void setListaFiltroTarifaReparto(List<Tarifa> listaFiltroTarifaReparto) {
		this.listaFiltroTarifaReparto = listaFiltroTarifaReparto;
	}

	public List<Agencia> getListaFilterAgencias() {
		return listaFilterAgencias;
	}

	public void setListaFilterAgencias(List<Agencia> listaFilterAgencias) {
		this.listaFilterAgencias = listaFilterAgencias;
	}

	public void setTarifa(Tarifa tarifa) {
		this.tarifa = tarifa;
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

	public List<Destino> getListaFilterDestino() {
		return listaFilterDestino;
	}

	public void setListaFilterDestino(List<Destino> listaFilterDestino) {
		this.listaFilterDestino = listaFilterDestino;
	}

	public List<Agencia> getListaAgDestinos() {
		return listaAgDestinos;
	}

	public void setListaAgDestinos(List<Agencia> listaAgDestinos) {
		this.listaAgDestinos = listaAgDestinos;
	}

	public List<Ubigeo> getListaUbigeo() {
		return listaUbigeo;
	}

	public void setListaUbigeo(List<Ubigeo> listaUbigeo) {
		this.listaUbigeo = listaUbigeo;
	}

	public DistritoCategoria getDistritoCategoria() {
		return distritoCategoria;
	}

	public void setDistritoCategoria(DistritoCategoria distritoCategoria) {
		this.distritoCategoria = distritoCategoria;
	}

	public Tarifa getDisSelec() {
		return disSelec;
	}

	public void setDisSelec(Tarifa disSelec) {
		this.disSelec = disSelec;
	}

	public List<DistritoCategoria> getListaCategoriasDis() {
		return listaCategoriasDis;
	}

	public void setListaCategoriasDis(List<DistritoCategoria> listaCategoriasDis) {
		this.listaCategoriasDis = listaCategoriasDis;
	}

	public Map<Integer, List<Destino>> getMapAgenciaListaTarifaDestino() {
		return mapAgenciaListaTarifaDestino;
	}
	
	public void setMapAgenciaListaTarifaDestino(
			Map<Integer, List<Destino>> mapAgenciaListaTarifaDestino) {
		this.mapAgenciaListaTarifaDestino = mapAgenciaListaTarifaDestino;
	}

}
