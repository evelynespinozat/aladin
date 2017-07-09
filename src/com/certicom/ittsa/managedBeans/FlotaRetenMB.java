package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Flota;
import com.certicom.ittsa.domain.FlotaReten;
import com.certicom.ittsa.domain.Historial_AsigPCT;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Oficina;
import com.certicom.ittsa.domain.Piloto;
import com.certicom.ittsa.domain.Tripulacion;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.FlotaRetenService;
import com.certicom.ittsa.services.FlotaService;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.PilotoService;
import com.certicom.ittsa.services.TripulacionServices;
import com.pe.certicom.ittsa.commons.BusConverter;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.CopilotoConverter;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;
import com.pe.certicom.ittsa.commons.PilotoConverter;

@ManagedBean(name="flotaRetenMB")
@ViewScoped
public class FlotaRetenMB extends GenericBeans implements Serializable{
	private String titulo;
	private FlotaReten flotaReten;
	private List<FlotaReten> listaFReten;
	private List<FlotaReten> listaFRetenFiltro;
	private List<Flota> listaBuses;
	private List<Agencia> listaAgencias;
	private Map<Integer, Flota> listaVehicularMP;
	
	private List<Piloto> listaPiloto;
	private List<Piloto> listaCopiloto;
	
	private List<Piloto> listaFiltroPiloto;
	private List<Piloto> listaFiltroCopiloto;
	
	private Flota flotaSelected;
	private Piloto pilotoSelected;
	private Piloto cpilotoSelected;
	
	private Boolean editar;
	
	private Boolean disF = false;
	private Boolean disP = false;
	private Boolean disCP = false;
	
	//services
	private FlotaRetenService flotaRetenService;
	private TripulacionServices tripulacionServices;
	private FlotaService  flotaService;
	private MenuServices menuServices;
	private AgenciaService  agenciaService;
	private PilotoService pilotoService;
	private Boolean pilotoCopilotoRecuperado;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	public FlotaRetenMB(){;}
	
	@PostConstruct
	public void incia()
	{
		this.editar = Boolean.FALSE;
		this.flotaReten = new FlotaReten();
	
		this.menuServices=new MenuServices();
		this.flotaRetenService = new FlotaRetenService();
		this.flotaService = new FlotaService();
		this.agenciaService = new AgenciaService();
		this.pilotoService = new PilotoService();
		this.listaBuses = new ArrayList<>();
		this.pilotoCopilotoRecuperado = Boolean.FALSE;
		
		this.tripulacionServices = new TripulacionServices(); 
		
		log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		//listando
		try {
			this.listaFReten = flotaRetenService.listaFlotaRetensActivas();
			this.listaAgencias = agenciaService.listaAgenciasActivas();
			
			//lisa te de busese con pilot y copiloto asignado
			this.listaBuses = flotaService.listaFlotaActivas();
			
			
			this.listaPiloto = pilotoService.listaPilotoActivas();
			this.listaCopiloto = this.listaPiloto;
		
			/**  lista para autocomplete*/
//			this.listaBuses = BusConverter.listaFlotas;
//			this.listaPiloto = PilotoConverter.listaPilotos;
//			this.listaCopiloto = PilotoConverter.listaPilotos;
			
			/** Lista de Flota en map **/
			this.listaVehicularMP = new  LinkedHashMap<Integer,Flota>();
			for (int i = 0; i < this.listaBuses.size(); i++) {
				this.listaVehicularMP.put(this.listaBuses.get(i).getIdBus(),this.listaBuses.get(i));
			}
			
			int codMenu = this.menuServices.opcionMenuByPretty("pretty:busReten");
			this.log.setCod_menu(codMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void mostrarDatosBus()
	{
		this.flotaReten.setIdPiloto(null);
		this.flotaReten.setNamePiloto("");
		this.flotaReten.setDniPiloto("");
		this.flotaReten.setIdCopiloto(null);
		this.flotaReten.setNameCopiloto("");
		this.flotaReten.setDniCopiloto("");
		
		for (int i = 0; i < this.listaBuses.size(); i++) {
			Flota f = this.listaBuses.get(i);
			if(f.getIdBus() == this.flotaReten.getIdBus()){
				this.flotaReten.setNroBus(f.getNumero().toString());
				this.flotaReten.setPlaca(f.getPlaca());
				if(f.getPiloto()!= null){
					for (int j = 0; j < this.listaPiloto.size(); j++) {
						Piloto pilo = this.listaPiloto.get(j);
						if(f.getPiloto().toString().equals(pilo.getIdPiloto().toString())){
							System.out.println("entro ");
							this.flotaReten.setIdPiloto(pilo.getIdPiloto());
							this.flotaReten.setDniPiloto(pilo.getdNI());
							this.flotaReten.setNamePiloto(pilo.getNombres() + " " + pilo.getApellidoPaterno() + " " + pilo.getApellidoMaterno());
						}
					}
				}
				if(f.getCopiloto()!= null){
					for (int k = 0; k < this.listaCopiloto.size(); k++) {
						Piloto copilo = this.listaCopiloto.get(k);
						if(f.getCopiloto().toString().equals(copilo.getIdPiloto().toString())){
							this.flotaReten.setIdCopiloto(copilo.getIdPiloto());
							this.flotaReten.setDniCopiloto(copilo.getdNI());
							this.flotaReten.setNameCopiloto(copilo.getNombres() + " " + copilo.getApellidoPaterno() + " " + copilo.getApellidoMaterno());
						}
					}
				}
			}
		}
		
		if(this.flotaReten.getIdPiloto() != null && this.flotaReten.getIdCopiloto() !=null)
		{
			this.pilotoCopilotoRecuperado = Boolean.TRUE;
		}
		
	}
	
	public void setearBus(SelectEvent event){
		Flota f = (Flota)event.getObject();
		this.flotaReten.setNroBus(f.getNumero().toString());
		this.flotaReten.setIdBus(f.getIdBus());
		this.disF = Boolean.TRUE;
	}
	
	 public void setearPiloto(SelectEvent event){
		 Piloto p = (Piloto)event.getObject();
		 this.flotaReten.setDniPiloto(p.getdNI());
		 this.flotaReten.setNamePiloto(p.getNombres()+" "+ p.getApellidoPaterno() + " " +p.getApellidoMaterno());
		 this.flotaReten.setIdPiloto(p.getIdPiloto());
		 this.disP = Boolean.TRUE;
	 }
	 
	 public void setearCopiloto(SelectEvent event){
		 Piloto cp = (Piloto)event.getObject();
		 this.flotaReten.setDniCopiloto(cp.getdNI());
		 this.flotaReten.setNameCopiloto(cp.getNombres()+" "+ cp.getApellidoPaterno() + " " +cp.getApellidoMaterno());
		 this.flotaReten.setIdCopiloto(cp.getIdPiloto());
		 this.disCP = Boolean.TRUE;
	 }
	
	
	public List<Flota> completeFlota(String query) {  
        List<Flota> suggestions = new ArrayList<Flota>();  
        for(Flota p : this.listaBuses) {  
        	// if(p.getPlaca().startsWith(query)){
            if(p.getNumero().toString().startsWith(query)){
            	suggestions.add(p); 	
            } 
        }  
          
        return suggestions;  
    }  
	
	public List<Piloto> completePiloto(String query) {  
		List<Piloto> suggestions = new ArrayList<Piloto>();  
		for(Piloto p : this.listaPiloto) {  
			if(p.getdNI().startsWith(query) || p.getApellidoPaterno().contains(query) || p.getApellidoMaterno().contains(query)){
				suggestions.add(p); 	
			} 
		}  
		
		return suggestions;  
	}  
	
	public List<Piloto> completeCopiloto(String query) {  
		List<Piloto> suggestions = new ArrayList<Piloto>();  
		for(Piloto p : this.listaCopiloto) {  
			if(p.getdNI().startsWith(query) || p.getApellidoPaterno().contains(query) || p.getApellidoMaterno().contains(query)){
				suggestions.add(p); 	
			} 
		}  
		return suggestions;  
	}  

	public void guardarFlotaReten()
	{
		System.out.println("entro en esta parte a guardar");
		Boolean valido=Boolean.TRUE;
		RequestContext context = RequestContext.getCurrentInstance();  
   	    context.addCallbackParam("esValido", valido );
		try {
			if(this.editar)
			{
				this.flotaRetenService.actualizarFlotaReten(this.flotaReten);
				this.log.setAccion("UPDATE");
				this.log.setDescripcion("Se actualiza el Bus reten: " + this.flotaReten.getIdBusReten());
				this.logmb.insertarLog(log);
				FacesUtils.showFacesMessage("Bus retenido actualizado correctamente.",Constante.INFORMACION);
				context.update("sms");
			}else
			{
				this.flotaRetenService.crearFlotaReten(this.flotaReten);
				this.log.setAccion("INSERT");
				this.log.setDescripcion("Se inserto el bus retenido: " + this.flotaReten.getIdBus());
				this.logmb.insertarLog(this.log);
				 FacesUtils.showFacesMessage("Bus retenido registrado correctamente.",Constante.INFORMACION);
				 context.update("sms");
			}
			this.listaFReten = this.flotaRetenService.listaFlotaRetensActivas();
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.flotaReten = new FlotaReten();
		this.editar =  Boolean.FALSE;
		
	}
	
	
	public void eliminarFlotaReten()
	{
		try {
			this.flotaRetenService.eliminarFlotaReten(flotaReten.getIdBusReten());
			listaFReten.remove(flotaReten);
			FacesUtils.showFacesMessage("Bus retenido eliminado correctamente.",Constante.INFORMACION);
			
			log.setAccion("DELETE");
	        log.setDescripcion("Se elimina el bus retenido Nro " + flotaReten.getIdBus());
	        logmb.insertarLog(log);

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.flotaReten =  new FlotaReten();
		
	}
	
	public void editarFlotaReten(FlotaReten fr)
	{
		this.editar = Boolean.TRUE;
		this.flotaReten = fr;
		System.out.println(this.flotaReten.getNamePiloto());
		resetearItemsAutocomplete();
		resetDisableFields(Boolean.TRUE);
		this.titulo = "Modificar retención de flota";
	}
	
	public void nuevaFlotaReten() {
		this.editar = Boolean.FALSE;
		this.flotaReten = new FlotaReten();
		resetearItemsAutocomplete();
		resetDisableFields(Boolean.FALSE);
		this.titulo = "Registrar nueva retención de flota";
	}
	
	private void resetDisableFields(boolean v){
		this.disF = v;
		this.disP= v;
		this.disCP = v;
	}
	
	
	private void resetearItemsAutocomplete(){
		this.flotaSelected = new Flota();
		this.pilotoSelected = new Piloto();
		this.cpilotoSelected = new Piloto();
	}

	public void listarPilotosActivos()
	{
		try {
			this.listaPiloto = pilotoService.listaPilotoActivas();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void listarCopilotosActivos()
	{
		this.listaCopiloto = null;
		
		try {
			this.listaCopiloto = this.pilotoService.copilotosDisponibles();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void asignarPiloto(Piloto pilot)
	{
		System.out.println("piloto seleccionado: "+pilot.getApellidoPaterno());
		
		
		/*
		Tripulacion t = this.tripulacionServices.findByIdProgramacion(this.programacion.getIdProgramacion());
		
		if(!pil.isFlagTemporal()){
			this.busServices.actualizarPiloto(pil.getIdPiloto(),t.getIdBus());
		}
		
		this.tripulacionServices.actualizarIdPiloto(t.getIdTripulacion(), pil.getIdPiloto(), pil.isFlagTemporal());
		Historial_AsigPCT historial = new Historial_AsigPCT();
		historial.setIdBus(t.getIdBus());
		historial.setFecha(new Date());
		historial.setEstado("0"); 
		historial.setIdPiloto(pil.getIdPiloto()); 
		this.historial_asigPCTServices.actualizarEstadoHistorial_Piloto(historial);
		historial.setEstado("1"); 
		this.historial_asigPCTServices.crearHistorial_Piloto(historial);
		
		
		this.flotaReten.setNamePiloto(namePiloto);
		
		*/
		
	}
	
	public void asignarCopiloto(Piloto copilot)
	{
		System.out.println("copiloto seleccionado: "+copilot.getApellidoPaterno());
		
		/*
		Tripulacion t = this.tripulacionServices.findByIdProgramacion(this.programacion.getIdProgramacion());
		
		if(!pil.isFlagTemporal()){
			this.busServices.actualizarPiloto(pil.getIdPiloto(),t.getIdBus());
		}
		
		this.tripulacionServices.actualizarIdPiloto(t.getIdTripulacion(), pil.getIdPiloto(), pil.isFlagTemporal());
		Historial_AsigPCT historial = new Historial_AsigPCT();
		historial.setIdBus(t.getIdBus());
		historial.setFecha(new Date());
		historial.setEstado("0"); 
		historial.setIdPiloto(pil.getIdPiloto()); 
		this.historial_asigPCTServices.actualizarEstadoHistorial_Piloto(historial);
		historial.setEstado("1"); 
		this.historial_asigPCTServices.crearHistorial_Piloto(historial);
		
		this.flotaReten.setNameCopiloto(nameCopiloto);
		*/
	}
	
	
	//**set an get 
	
	public MenuServices getMenuServices() {
		return menuServices;
	}

	public void setMenuServices(MenuServices menuServices) {
		this.menuServices = menuServices;
	}

	public Log getLog() {
		return log;
	}

	public void setLog(Log log) {
		this.log = log;
	}

	public LogMB getLogmb() {
		return logmb;
	}

	public void setLogmb(LogMB logmb) {
		this.logmb = logmb;
	}

	public Boolean getEditar() {
		return editar;
	}

	public void setEditar(Boolean editar) {
		this.editar = editar;
	}

	public FlotaReten getFlotaReten() {
		return flotaReten;
	}

	public void setFlotaReten(FlotaReten flotaReten) {
		this.flotaReten = flotaReten;
	}

	public List<FlotaReten> getListaFReten() {
		return listaFReten;
	}

	public void setListaFReten(List<FlotaReten> listaFReten) {
		this.listaFReten = listaFReten;
	}

	public List<FlotaReten> getListaFRetenFiltro() {
		return listaFRetenFiltro;
	}

	public void setListaFRetenFiltro(List<FlotaReten> listaFRetenFiltro) {
		this.listaFRetenFiltro = listaFRetenFiltro;
	}

	public FlotaRetenService getFlotaRetenService() {
		return flotaRetenService;
	}

	public void setFlotaRetenService(FlotaRetenService flotaRetenService) {
		this.flotaRetenService = flotaRetenService;
	}

	public List<Flota> getListaBuses() {
		return listaBuses;
	}

	public void setListaBuses(List<Flota> listaBuses) {
		this.listaBuses = listaBuses;
	}

	public List<Agencia> getListaAgencias() {
		return listaAgencias;
	}

	public void setListaAgencias(List<Agencia> listaAgencias) {
		this.listaAgencias = listaAgencias;
	}

	public FlotaService getFlotaService() {
		return flotaService;
	}

	public void setFlotaService(FlotaService flotaService) {
		this.flotaService = flotaService;
	}

	public AgenciaService getAgenciaService() {
		return agenciaService;
	}

	public void setAgenciaService(AgenciaService agenciaService) {
		this.agenciaService = agenciaService;
	}

	public List<Piloto> getListaPiloto() {
		return listaPiloto;
	}

	public void setListaPiloto(List<Piloto> listaPiloto) {
		this.listaPiloto = listaPiloto;
	}

	public List<Piloto> getListaCopiloto() {
		return listaCopiloto;
	}

	public void setListaCopiloto(List<Piloto> listaCopiloto) {
		this.listaCopiloto = listaCopiloto;
	}

	public PilotoService getPilotoService() {
		return pilotoService;
	}

	public void setPilotoService(PilotoService pilotoService) {
		this.pilotoService = pilotoService;
	}

	public Flota getFlotaSelected() {
		return flotaSelected;
	}

	public void setFlotaSelected(Flota flotaSelected) {
		this.flotaSelected = flotaSelected;
	}

	public Piloto getPilotoSelected() {
		return pilotoSelected;
	}

	public void setPilotoSelected(Piloto pilotoSelected) {
		this.pilotoSelected = pilotoSelected;
	}

	public Piloto getCpilotoSelected() {
		return cpilotoSelected;
	}

	public void setCpilotoSelected(Piloto cpilotoSelected) {
		this.cpilotoSelected = cpilotoSelected;
	}

	public Boolean getDisF() {
		return disF;
	}

	public void setDisF(Boolean disF) {
		this.disF = disF;
	}

	public Boolean getDisP() {
		return disP;
	}

	public void setDisP(Boolean disP) {
		this.disP = disP;
	}

	public Boolean getDisCP() {
		return disCP;
	}

	public void setDisCP(Boolean disCP) {
		this.disCP = disCP;
	}

	public Map<Integer, Flota> getListaVehicularMP() {
		return listaVehicularMP;
	}

	public void setListaVehicularMP(Map<Integer, Flota> listaVehicularMP) {
		this.listaVehicularMP = listaVehicularMP;
	}

	public List<Piloto> getListaFiltroPiloto() {
		return listaFiltroPiloto;
	}

	public void setListaFiltroPiloto(List<Piloto> listaFiltroPiloto) {
		this.listaFiltroPiloto = listaFiltroPiloto;
	}

	public List<Piloto> getListaFiltroCopiloto() {
		return listaFiltroCopiloto;
	}

	public void setListaFiltroCopiloto(List<Piloto> listaFiltroCopiloto) {
		this.listaFiltroCopiloto = listaFiltroCopiloto;
	}

	public Boolean getPilotoCopilotoRecuperado() {
		return pilotoCopilotoRecuperado;
	}

	public void setPilotoCopilotoRecuperado(Boolean pilotoCopilotoRecuperado) {
		this.pilotoCopilotoRecuperado = pilotoCopilotoRecuperado;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

}

