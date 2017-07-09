package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Destino;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.DestinoServices;
import com.certicom.ittsa.services.MenuServices;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name = "destinoMB")
@ViewScoped
public class DestinoMB extends GenericBeans implements Serializable {
	private String titulo;
	private Destino destino;
	
	private Destino destinoSelect;
	
	private Integer origen_select = 0;
	private Integer destino_select = 0;
	
	private List<Destino> listaDestinofiltro;
	private List<Destino> listaDestino;
	private Boolean editar;
	private Agencia agenciaOrigen;
	private Agencia agenciaDestino; 

	private DestinoServices destinoServices;
	private MenuServices menuServices;
	private AgenciaService agenciaService;
	
	private List<Agencia> listaAgOrigen;
	private List<Agencia> listaAgDestinos;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	public DestinoMB() {
		;
	}

	@PostConstruct
	public void inicia() {
		this.destino = new Destino();
		this.destinoSelect = new Destino();
		this.editar = Boolean.FALSE;
		this.destinoServices = new DestinoServices();
		this.menuServices=new MenuServices();
		this.agenciaService = new AgenciaService();
		
		 log = (Log)getSpringBean(Constante.SESSION_LOG);
		 logmb = new LogMB();
		
		try {
			int codMenu = menuServices.opcionMenuByPretty("pretty:agencia");
			log.setCod_menu(codMenu);
			this.listaDestino = this.destinoServices.findAll();
			this.listaAgOrigen = this.agenciaService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void newInsert() {
		this.destino = new Destino();
	}

	public void cambiarEstado(Destino destino) {
		if (destino.isEstado()) {
			destino.setEstado(Boolean.FALSE);
		} else {
			destino.setEstado(Boolean.TRUE);
		}

		try {
			this.destinoServices.actualizarDestino(destino);
			 log.setAccion("CHANGE ESTADO");
	         log.setDescripcion("Se cambio el estado en Destino: " + destino.getDesDestino());
	         logmb.insertarLog(log);
			FacesUtils.showFacesMessage("Estado de destino actualizado correctamente.",Constante.INFORMACION);
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
		}

	}

	public void guardarDestino() {
		Boolean valido = Boolean.TRUE;
		RequestContext context = RequestContext.getCurrentInstance();
		context.addCallbackParam("esValido", valido);
		
		int cant = 0;
		
		try {
			
			//validando
			
			
			if (this.editar) { // editando
				
				System.out.println("origen ant " + origen_select);
				System.out.println("destino ant " + destino_select);
				
				System.out.println("origen new " + this.destino.getOrigen());
				System.out.println("destino new " + this.destino.getDestino());
			
				if((this.destino.getOrigen() == origen_select) && (this.destino.getDestino() == destino_select)){

					destinoServices.actualizarDestino(this.destino);
					log.setAccion("UPDATE");
				//	log.setDescripcion("Se actualiza el destino: " + destino.getOrigen() +" - " +destino.getDestino());
					logmb.insertarLog(log);
					
					FacesUtils.showFacesMessage("Destino actualizado correctamente.",Constante.INFORMACION);
					context.update("sms");
					
					
					this.listaDestino = destinoServices.findAll();

					this.editar = Boolean.FALSE;
					this.destino = new Destino();
				
				} 
				
				else{
					cant = this.destinoServices.validarDestinoRepetido(this.destino.getOrigen(), this.destino.getDestino());
					if(cant == 0){
					
						destinoServices.actualizarDestino(this.destino);
						log.setAccion("UPDATE");
						log.setDescripcion("Se actualiza el destino: " + destino.getOrigen() +" - " +destino.getDestino());
						logmb.insertarLog(log);
						
						FacesUtils.showFacesMessage("Destino actualizado correctamente.",Constante.INFORMACION);
						context.update("sms");
						
						this.listaDestino = destinoServices.findAll();

						this.editar = Boolean.FALSE;
						this.destino = new Destino();
						
					} else {
						FacesUtils.showFacesMessage("El destino ya se encuentra registrado.",Constante.ERROR);
						context.addCallbackParam("esValido", Boolean.FALSE);
					}
					
				}
				
	            
			} else {// guardando un nuevo registro
				
				cant = this.destinoServices.validarDestinoRepetido(this.destino.getOrigen(), this.destino.getDestino());
				if(cant == 0){
				
				this.destino.setEstado(Boolean.TRUE);

				this.destinoServices.registrarDestino(this.destino);
				//this.listaDestino = destinoServices.findAll();
				 log.setAccion("INSERT");
	             log.setDescripcion("Se registro el Destino: " + destino.getOrigen() +" - " +destino.getDestino());
	             logmb.insertarLog(log);
				FacesUtils.showFacesMessage("Destino registrado correctamente.",Constante.INFORMACION);
				context.update("sms");
					
					this.listaDestino = destinoServices.findAll();
				
					this.editar = Boolean.FALSE;
					this.destino = new Destino();
				
				} else{
					FacesUtils.showFacesMessage("Este destino ya se encuentra registrado.",Constante.ERROR);
					context.addCallbackParam("esValido", Boolean.FALSE);
				}
				
				
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}

	public void eliminarDestino() {

		try {
			this.destinoServices.eliminarDestino(destino.getIdDestino());
			this.listaDestino.remove(destino);
			FacesUtils.showFacesMessage("Destino eliminado correctamente.",Constante.INFORMACION);
			log.setAccion("DELETE");
	        log.setDescripcion("Se elimina el destino: " + destino.getDesOrigen() +"-"+destino.getDesDestino());
	        logmb.insertarLog(log);
		} catch (Exception e) {
			FacesUtils.showFacesMessage("El destino no se puede eliminar, consulte con el administrador.",Constante.ERROR);
			e.printStackTrace();
		}
		this.destino = new Destino();

	}
	
	public void listarAgeDestino(){
		
		this.listaAgDestinos = new ArrayList<Agencia>();
		for(Agencia ag : this.listaAgOrigen)
		{
			if(this.destino.getOrigen()!=ag.getIdagencia())
			{
				this.listaAgDestinos.add(ag);
			}
		}

	}

	
	
	public void editarDestino(Destino des)
	{
		this.destino = des;
		this.origen_select = this.destino.getOrigen();
		this.destino_select = this.destino.getDestino();
		System.out.println("origen ant " + origen_select);
		System.out.println("destino ant " + destino_select);
		
		System.out.println("origen new " + this.destino.getOrigen());
		System.out.println("destino new " + this.destino.getDestino());
		this.editar = Boolean.TRUE;
		this.listaAgDestinos = this.listaAgOrigen;
		this.titulo = "Modificar destino";
	}
	
	public void nuevoDestino()
	{
		this.editar= Boolean.FALSE;
		this.destino = new Destino();
		this.titulo = "Registrar nuevo destino";
		
	}

	/*###################################---------------getter and setter------------####################################################*/
	
	
	
	public Destino getDestino() {
		return destino;
	}

	public void setDestino(Destino destino) {
		this.destino = destino;
	}

	public List<Destino> getListaDestinofiltro() {
		return listaDestinofiltro;
	}

	public void setListaDestinofiltro(List<Destino> listaDestinofiltro) {
		this.listaDestinofiltro = listaDestinofiltro;
	}

	public List<Destino> getListaDestino() {
		return listaDestino;
	}

	public void setListaDestino(List<Destino> listaDestino) {
		this.listaDestino = listaDestino;
	}

	public Boolean getEditar() {
		return editar;
	}

	public void setEditar(Boolean editar) {
		this.editar = editar;
	}

	public DestinoServices getDestinoServices() {
		return destinoServices;
	}

	public void setDestinoServices(DestinoServices destinoServices) {
		this.destinoServices = destinoServices;
	}

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

	public AgenciaService getAgenciaService() {
		return agenciaService;
	}

	public void setAgenciaService(AgenciaService agenciaService) {
		this.agenciaService = agenciaService;
	}

	public List<Agencia> getListaAgOrigen() {
		return listaAgOrigen;
	}

	public void setListaAgOrigen(List<Agencia> listaAgOrigen) {
		this.listaAgOrigen = listaAgOrigen;
	}

	public List<Agencia> getListaAgDestinos() {
		return listaAgDestinos;
	}

	public void setListaAgDestinos(List<Agencia> listaAgDestinos) {
		this.listaAgDestinos = listaAgDestinos;
	}

	public Agencia getAgenciaOrigen() {
		return agenciaOrigen;
	}

	public void setAgenciaOrigen(Agencia agenciaOrigen) {
		this.agenciaOrigen = agenciaOrigen;
	}

	public Agencia getAgenciaDestino() {
		return agenciaDestino;
	}

	public void setAgenciaDestino(Agencia agenciaDestino) {
		this.agenciaDestino = agenciaDestino;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	// set and get 
	
	

}
