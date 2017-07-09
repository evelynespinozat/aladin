package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.aspectj.runtime.internal.PerObjectMap;
import org.primefaces.context.RequestContext;

import sun.java2d.pipe.SpanShapeRenderer.Simple;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Destino;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Persona;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.OficinaService;
import com.certicom.ittsa.services.PersonaServices;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="personaMB")
@ViewScoped
public class PersonaMB extends GenericBeans implements Serializable{

	private Persona persona;
	private Persona personaFilter;
	private List<Persona> listaPersona;
	private List<Persona> listaFiltroPersonas;
	private Boolean editar;

	//services
	private PersonaServices personaSevice;
	private MenuServices menuServices;
	private AgenciaService agenciaService;
		
	//datos Log
    private Log log;
	private LogMB logmb;
	
	public PersonaMB(){;}
	
	@PostConstruct
	public void incia()
	{
		this.editar = Boolean.FALSE;
	    this.listaPersona = new ArrayList<>();
		this.menuServices=new MenuServices();
		this.personaFilter= new Persona();
		this.personaSevice = new PersonaServices();
		 log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		//listando
		try {
			int codMenu = menuServices.opcionMenuByPretty("pretty:pasajeros");
			log.setCod_menu(codMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		
	}
	

	public void buscarPersona(){
		try {
			this.listaPersona = new ArrayList<>();
			if(this.personaFilter.getDni() != null && !this.personaFilter.getDni().equals("")){
				Persona per = new Persona();
				per = this.personaSevice.findByNroDocumento(this.personaFilter.getDni());
				this.listaPersona.add(per);	
			}else if(this.personaFilter.getAPaterno() != null && !this.personaFilter.getAPaterno().equals("") && this.personaFilter.getAMaterno() != null && !this.personaFilter.getAMaterno().equals("")){
				this.listaPersona = this.personaSevice.findByApellidos(this.personaFilter.getAPaterno(), this.personaFilter.getAMaterno());
			}else if(this.personaFilter.getAPaterno() != null && !this.personaFilter.getAPaterno().equals("")){
				this.listaPersona = this.personaSevice.findByApellidoPaterno(this.personaFilter.getAPaterno());
			}else if(this.personaFilter.getAMaterno() != null && !this.personaFilter.getAMaterno().equals("")){
				this.listaPersona = this.personaSevice.findByApellidoMaterno(this.personaFilter.getAPaterno());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void nuevoPasajero(){
		this.persona = new Persona();
	}
	
	public void guardarPersona(){
		try {
			RequestContext context = RequestContext.getCurrentInstance();  
			Boolean valido=Boolean.FALSE;
			//validando el dni
			Persona pe = this.personaSevice.findByNroDocumento(this.persona.getDni());
			if(pe == null){
				this.persona.setTipodocumento("DNI");
				this.personaSevice.crearPersona(this.persona);
				valido=Boolean.TRUE;
				FacesUtils.showFacesMessage("Persona registrada correctamente.",Constante.INFORMACION);
				context.update("sms");
			}else{		
				
				valido=Boolean.FALSE;
				FacesUtils.showFacesMessage("EL DNI ya se encuentra registrado.",Constante.INFORMACION);
				context.update("msgNuevo");
			}
			context.addCallbackParam("esValido", valido );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//INICIO piscoya
	public void editarPersona(Persona p){
		this.persona = new Persona();
		this.persona = p;			
	}

	public void limpiarValores(){
		
		}	
	
	
	public void guardarEditarPersona(){	
		System.out.println("[PERSONAMB] public void guardarEditarPersona() : ");
		
		try {

			RequestContext context = RequestContext.getCurrentInstance();  
			Boolean valido=Boolean.TRUE;
			this.personaSevice.actualizarPersona(this.persona);                  
			FacesUtils.showFacesMessage("Persona actualizada correctamente.",Constante.INFORMACION);
			context.update("sms");
			context.addCallbackParam("esValido", valido );

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("ERROR [GUARDAR EDITAR PERSONA] : "+e);
		}		
		
	
	}
	
	
	public void litarOrigenDestino(){
				
	}
	//FIN piscoya
	
	//**set an get 
	
	public MenuServices getMenuServices() {
		return menuServices;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Persona getPersonaFilter() {
		return personaFilter;
	}

	public void setPersonaFilter(Persona personaFilter) {
		this.personaFilter = personaFilter;
	}

	public List<Persona> getListaPersona() {
		return listaPersona;
	}

	public void setListaPersona(List<Persona> listaPersona) {
		this.listaPersona = listaPersona;
	}

	public List<Persona> getListaFiltroPersonas() {
		return listaFiltroPersonas;
	}

	public void setListaFiltroPersonas(List<Persona> listaFiltroPersonas) {
		this.listaFiltroPersonas = listaFiltroPersonas;
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
	
}

