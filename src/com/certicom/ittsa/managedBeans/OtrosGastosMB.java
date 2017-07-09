package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Oficina;
import com.certicom.ittsa.domain.OtrosGastos;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.OficinaService;
import com.certicom.ittsa.services.OtrosGastosService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;


@ManagedBean(name="otrosGastosMB")
@ViewScoped
public class OtrosGastosMB extends GenericBeans implements Serializable{
	private String titulo;
	private OtrosGastos otrosGastos;
	private OtrosGastos otrosGastosSelec;
	private boolean editar;
	private OtrosGastosService otrosGastosService;
	private List<OtrosGastos> listaOtrosGastos;
	private List<OtrosGastos> listFiltroOtrosGastos;
	
	private Log log;
	private LogMB logmb;
	RequestContext context; 
	private MenuServices menuServices;
	
	private Oficina oficinaSelec;//agencia oficina
	private Agencia agenciaSelec;
	private AgenciaService agenciaService;
	private OficinaService oficinaservice;
	private List<Oficina> listaOficina;
	private List<Agencia> listaAgencia;
	
	private Double saldoPendiente=0.0;
	private boolean estadoSaldo=false;
	@PostConstruct
	public void inicia()
	{
		this.otrosGastos=new OtrosGastos();
		this.otrosGastosSelec=new OtrosGastos();
		this.otrosGastosService=new OtrosGastosService();
		this.agenciaSelec=new Agencia();
		this.oficinaSelec=new Oficina();
		this.agenciaService=new AgenciaService();
		this.oficinaservice=new OficinaService();
		this.listaAgencia=new ArrayList<>();
		this.listaOficina=new ArrayList<>();
		
		this.editar = Boolean.FALSE;
		menuServices = new MenuServices();
		
		log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		
		try {
			int codMenu = menuServices.opcionMenuByPretty("pretty:otrosGastos");
			log.setCod_menu(codMenu);
			
			this.listaOtrosGastos=this.otrosGastosService.findAll();
			this.listaAgencia=this.agenciaService.findAll();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

public void listarOficinas(){
	try {
		
		this.listaOficina=this.oficinaservice.listaOficinasXAgencia(this.otrosGastos.getIdAgencia());
		
	} catch (Exception e) {
		
		e.printStackTrace();
	}
}

 public void nuevoGastoAdicional(){
	 	this.otrosGastos=new OtrosGastos();
	 	this.saldoPendiente=0.0;
	 	this.titulo = "Registrar nuevo gasto adicional";
	 	try {
			this.listaAgencia=this.agenciaService.findAll();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		this.editar = Boolean.FALSE;
		this.otrosGastos.setEstado(Boolean.TRUE);
		
	}
 
 public void editarOtrosGastos(OtrosGastos otrosGastos){
		
		try {
			
			this.otrosGastos=this.otrosGastosService.findById(otrosGastos.getIdOtrosGastos());
			this.listaOficina=this.oficinaservice.listaOficinasXAgencia(this.otrosGastos.getIdAgencia());
			this.saldoPendiente=this.otrosGastos.getSaldo();
			this.titulo = "Modificar gasto adicional";
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		this.editar = Boolean.TRUE;
	}
	
	public void eliminarOtrosGastos(OtrosGastos otrosGastos){
		this.otrosGastosSelec=otrosGastos;
		
	}
	
	public void descontar(){
		RequestContext context = RequestContext.getCurrentInstance(); 
		Boolean valido;
		
		System.out.println("============= entra a descontar "+this.otrosGastos.getUltimoAporte());
		double res=0.0;
		if(this.otrosGastos.getSaldo()>=0.0){
			if(this.otrosGastos.getUltimoAporte()>=0.0){
				res=this.otrosGastos.getSaldo()-this.otrosGastos.getUltimoAporte();
				if(res>=0.0){
					this.saldoPendiente=res;
					this.estadoSaldo=true;
					if(res==0.0){
						this.otrosGastos.setEstado(Boolean.FALSE);
					}
					valido=Boolean.FALSE;
			   	    context.addCallbackParam("validationFailed", valido );
				}else{
					valido=Boolean.TRUE;
					context.addCallbackParam("validationFailed", valido );
					FacesUtils.showFacesMessage("Aporte supera el saldo.", Constante.INFORMACION);
					context.update("msgNuevo");
				}
			}else{
				valido=Boolean.TRUE;
				context.addCallbackParam("validationFailed", valido );
				FacesUtils.showFacesMessage("Ingrese cantidades Positivas.", Constante.INFORMACION);
				context.update("msgNuevo");
			}
		}else{
			valido=Boolean.TRUE;
			context.addCallbackParam("validationFailed", valido );
			FacesUtils.showFacesMessage("Se completó el monto correctamente.", Constante.INFORMACION);
			context.update("msgNuevo");
		}
	}

	public void confirmaEliminar()
	{
		try {
			
			this.otrosGastosService.eliminarOtrosGastos(this.otrosGastosSelec.getIdOtrosGastos());
			FacesUtils.showFacesMessage("Otro gasto eliminado correctamente.", 3);
			this.listaOtrosGastos=this.otrosGastosService.findAll();
			log.setAccion("DELETE");
	        log.setDescripcion("Se ha eliminado  : " + this.otrosGastosSelec.getMotivo());
	        logmb.insertarLog(log);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void cambiarEstado(OtrosGastos otrosGastos){
		
		   if(otrosGastos.getEstado()){
			   otrosGastos.setEstado(Boolean.FALSE);
			   System.out.println("Cambió estado a false");
		   }else{
			   otrosGastos.setEstado(Boolean.TRUE);
			   System.out.println("Cambió estado a true");
		   }
		   
		   try {
			   this.otrosGastosService.actualizarOtrosGastos(otrosGastos);
			   FacesUtils.showFacesMessage("Gasto adicional actualizado correctamente.",Constante.INFORMACION);
			   this.listaOtrosGastos=this.otrosGastosService.findAll();
			   log.setAccion("UPDATE");
	           log.setDescripcion("Se actualizó el estado a  : " + otrosGastos.getEstado());
	           logmb.insertarLog(log);
		   } catch (Exception e) {
			   System.out.println("Error:"+e.getMessage());
			   e.printStackTrace();
		   }   
	}
	
	public void guardarGasto(){
		
		Boolean valido=Boolean.TRUE;
		RequestContext context = RequestContext.getCurrentInstance();  
   	    context.addCallbackParam("esValido", valido);
		
		try {
			
			if(this.editar) {
				this.otrosGastos.setUltimoAporte(0.0);
				if(this.estadoSaldo){
				this.otrosGastos.setSaldo(this.saldoPendiente);
				}
				this.otrosGastosService.actualizarOtrosGastos(this.otrosGastos);
				FacesUtils.showFacesMessage("Gasto adicional actualizado correctamente.", 3);
				log.setAccion("UPDATE");
		        log.setDescripcion("Se actualizo el Gasto: "+this.otrosGastos.getMotivo());
		        logmb.insertarLog(log);
		        
		        this.saldoPendiente=0.0;
		        
			}else{
				//this.resultado.setEstado(Boolean.TRUE);
				this.otrosGastos.setSaldo(this.otrosGastos.getMonto());
				this.otrosGastos.setUltimoAporte(0.0);
				this.otrosGastosService.crearOtrosGastos(this.otrosGastos);
				FacesUtils.showFacesMessage("Gasto adicional registrado correctamente.", 3);
				log.setAccion("INSERT");
		        log.setDescripcion("Se a registrado  : " + this.otrosGastos.getMotivo());
		        logmb.insertarLog(log);
			}
			
			this.editar = Boolean.FALSE;
			this.otrosGastos=new OtrosGastos();			
			this.listaOtrosGastos=this.otrosGastosService.findAll();
		
			context.update("sms");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public OtrosGastos getOtrosGastos() {
		return otrosGastos;
	}

	public void setOtrosGastos(OtrosGastos otrosGastos) {
		this.otrosGastos = otrosGastos;
	}

	public OtrosGastos getOtrosGastosSelec() {
		return otrosGastosSelec;
	}

	public void setOtrosGastosSelec(OtrosGastos otrosGastosSelec) {
		this.otrosGastosSelec = otrosGastosSelec;
	}

	public boolean isEditar() {
		return editar;
	}

	public void setEditar(boolean editar) {
		this.editar = editar;
	}

	public OtrosGastosService getOtrosGastosService() {
		return otrosGastosService;
	}

	public void setOtrosGastosService(OtrosGastosService otrosGastosService) {
		this.otrosGastosService = otrosGastosService;
	}

	public List<OtrosGastos> getListaOtrosGastos() {
		return listaOtrosGastos;
	}

	public void setListaOtrosGastos(List<OtrosGastos> listaOtrosGastos) {
		this.listaOtrosGastos = listaOtrosGastos;
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

	public RequestContext getContext() {
		return context;
	}

	public void setContext(RequestContext context) {
		this.context = context;
	}

	public MenuServices getMenuServices() {
		return menuServices;
	}

	public void setMenuServices(MenuServices menuServices) {
		this.menuServices = menuServices;
	}

	public List<OtrosGastos> getListFiltroOtrosGastos() {
		return listFiltroOtrosGastos;
	}

	public void setListFiltroOtrosGastos(List<OtrosGastos> listFiltroOtrosGastos) {
		this.listFiltroOtrosGastos = listFiltroOtrosGastos;
	}

	public Oficina getOficinaSelec() {
		return oficinaSelec;
	}

	public void setOficinaSelec(Oficina oficinaSelec) {
		this.oficinaSelec = oficinaSelec;
	}

	public Agencia getAgenciaSelec() {
		return agenciaSelec;
	}

	public void setAgenciaSelec(Agencia agenciaSelec) {
		this.agenciaSelec = agenciaSelec;
	}

	public AgenciaService getAgenciaService() {
		return agenciaService;
	}

	public void setAgenciaService(AgenciaService agenciaService) {
		this.agenciaService = agenciaService;
	}

	public OficinaService getOficinaservice() {
		return oficinaservice;
	}

	public void setOficinaservice(OficinaService oficinaservice) {
		this.oficinaservice = oficinaservice;
	}

	public List<Oficina> getListaOficina() {
		return listaOficina;
	}

	public void setListaOficina(List<Oficina> listaOficina) {
		this.listaOficina = listaOficina;
	}

	public List<Agencia> getListaAgencia() {
		return listaAgencia;
	}

	public void setListaAgencia(List<Agencia> listaAgencia) {
		this.listaAgencia = listaAgencia;
	}

	public Double getSaldoPendiente() {
		return saldoPendiente;
	}

	public void setSaldoPendiente(Double saldoPendiente) {
		this.saldoPendiente = saldoPendiente;
	}

	public void setEstadoSaldo(boolean estadoSaldo) {
		this.estadoSaldo = estadoSaldo;
	}
	
	public boolean getEstadoSaldo() {
		return this.estadoSaldo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	
}
