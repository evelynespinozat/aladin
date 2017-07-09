package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.CategoriaServicio;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Oficina;
import com.certicom.ittsa.domain.PuntoVenta;
import com.certicom.ittsa.domain.Turno;
import com.certicom.ittsa.domain.Usuario;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.CategoriaServicioService;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.OficinaService;
import com.certicom.ittsa.services.PuntoVentaService;
import com.certicom.ittsa.services.UsuarioServices;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="puntoVentaMB")
@ViewScoped
public class PuntoVentaMB extends GenericBeans implements Serializable{
	private String titulo;
	private PuntoVenta puntoVenta;
	private List<PuntoVenta> listaPuntoVenta;
	private List<Agencia> listaAgencia;
	private List<PuntoVenta> listaFiltroPuntoVenta;
	private List<CategoriaServicio> listaCatServicio;
	private Boolean editar;
	
	
	
	private List<Oficina> listaOficinas;
	
	private PuntoVenta puntoVentaSelec;
	//services
	private AgenciaService agenciaService;
	private PuntoVentaService puntoVentaService;
	private MenuServices menuServices;
	private OficinaService oficinaService;
	private CategoriaServicioService categoriaServicioService;
	private UsuarioServices usuarioServices;
	
	@ManagedProperty(value = "#{loginMB}")
	private LoginMB loginMB;
	
	
	
//	@ManagedProperty(value = "#{loginMB.usuario}")
    private Usuario usuarioLogin;
	
//	private Usuario usuarioLogin;
	
//	@ManagedProperty(value = "#{loginMB.usuario}")
//	private Usuario usuarioLogin;
//	
//	@ManagedProperty(value = "#{loginMB.impresoraAsociada}")
//	private boolean impresoraAsociadaaux;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	
	public PuntoVentaMB(){;}
	
	@PostConstruct
	public void incia()
	{
		this.editar = Boolean.FALSE;
	
		this.puntoVentaService = new PuntoVentaService();
		this.agenciaService = new AgenciaService();
		this.menuServices=new MenuServices();
		this.oficinaService = new OficinaService();
		this.categoriaServicioService = new CategoriaServicioService();
		this.usuarioServices = new UsuarioServices(); 
		
		this.puntoVenta = new PuntoVenta();
		this.puntoVenta.setNombreImpresora("DEFAULT-PRINTER");
		
		this.puntoVentaSelec=new PuntoVenta();
		
		log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		usuarioLogin = loginMB.getUsuario();
		//listando
		try {
			this.listaPuntoVenta = puntoVentaService.listaPuntoVenta();
			this.listaAgencia = agenciaService.listaAgenciasActivas(); 
			this.listaOficinas = oficinaService.listaOficinasActivas();
			this.listaCatServicio = categoriaServicioService.findAll();
			int codMenu = menuServices.opcionMenuByPretty("pretty:puntoventa");
			log.setCod_menu(codMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cancelar(){
		this.puntoVenta = new PuntoVenta();
	}
	
	public void nuevoPventa(){
		this.puntoVenta = new PuntoVenta();
		this.editar = Boolean.FALSE;
		this.titulo = "Registrar nuevo punto de venta";
		this.listaOficinas = new ArrayList<Oficina>();
	}
	
	public void editarPventa(PuntoVenta pv){
		this.puntoVenta = pv;
		this.editar = Boolean.TRUE;
		this.titulo = "Modificar punto de venta";
		this.listaOficinas = this.oficinaService.getOficinasxAgencia(this.puntoVenta.getIdAgencia());
	}
	
	public void listarOficinas(){
		try {
			this.listaOficinas = oficinaService.getOficinasxAgencia(this.puntoVenta.getIdAgencia());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	public void guardarPuntoVenta()
	{
		Boolean valido;
		RequestContext context = RequestContext.getCurrentInstance(); 
		try {
			
			if(this.editar)
			{	
				this.puntoVentaSelec=this.puntoVenta;
				//buscando IP diferentes al punto de venta que se va editar
				
				if (buscarIPDiferentes(this.puntoVentaSelec))
				{
					valido=Boolean.FALSE;
			   	    context.addCallbackParam("validationFailed", valido );
			   	    
			   	    
			   	    //sin validacion de imprsora
			   	    puntoVentaSelec.setNamePuntoVenta(puntoVentaSelec.getNamePuntoVenta().toUpperCase().trim());
					this.puntoVentaSelec.setIdPuntoVenta(this.puntoVentaSelec.getIdPuntoVenta());  	 
					this.puntoVentaService.actualizarPuntoVenta(puntoVentaSelec);
					
					 log.setAccion("UPDATE");
		             log.setDescripcion("Se actualiza el Punto de Venta: " + puntoVentaSelec.getNamePuntoVenta());
		             logmb.insertarLog(log);
		             
		             //System.out.println("usuarioLogin.getLogin():"+usuarioLogin.getLogin() + "this.puntoVentaSelec.getLogin()"+this.puntoVentaSelec.getLogin());
		             if((usuarioLogin.getNombre() +" " +usuarioLogin.getApellido_materno()).equals(this.puntoVentaSelec.getLogin())){
		            	 if(this.puntoVentaSelec.getNombreImpresora().isEmpty() || this.puntoVentaSelec.getNombreImpresora() == null)
							{
		            		 //System.out.println("Mismo Usuario - FALSE");
		            		 
		            		 	this.loginMB.setImpresoraAsociada(Boolean.FALSE);
		            		 	this.loginMB.pv.setNombreImpresora(this.puntoVentaSelec.getNombreImpresora());
								//this.impresoraAsociada = Boolean.FALSE;
								
							}else if(this.puntoVentaSelec.getNumeroIP().isEmpty() || this.puntoVentaSelec.getNumeroIP() == null){
								//System.out.println("Mismo Usuario - FALSE");
								this.loginMB.setImpresoraAsociada(Boolean.FALSE);
								this.loginMB.pv.setNombreImpresora(this.puntoVentaSelec.getNombreImpresora());
//								this.impresoraAsociada = Boolean.FALSE;
								
							}else{
								//System.out.println("Mismo Usuario - TRUE");
								this.loginMB.setImpresoraAsociada(Boolean.TRUE);
								this.loginMB.pv.setNombreImpresora(this.puntoVentaSelec.getNombreImpresora());
//								this.impresoraAsociada = Boolean.TRUE;
							}
		            	 
		             }else{
		            	 System.out.println("Otro Usuario");
		             }
		             
					FacesUtils.showFacesMessage("Punto de venta actualizado correctamente.",Constante.INFORMACION);
					 context.update("sms");
					 this.editar =  Boolean.FALSE;
					 this.puntoVenta=new PuntoVenta();
					 this.puntoVentaSelec=new PuntoVenta();
						
			   	    /*
			   	     * con validacon de impresora
			   	    if(buscarImpresoraDiferentes(this.puntoVentaSelec))
						{
							
							puntoVentaSelec.setNamePuntoVenta(puntoVentaSelec.getNamePuntoVenta().toUpperCase().trim());
							this.puntoVentaSelec.setIdPuntoVenta(this.puntoVentaSelec.getIdPuntoVenta());  	 
							this.puntoVentaService.actualizarPuntoVenta(puntoVentaSelec);
							 log.setAccion("UPDATE");
				             log.setDescripcion("Se actualiza el Punto de Venta: " + puntoVentaSelec.getNamePuntoVenta());
				             logmb.insertarLog(log);
							FacesUtils.showFacesMessage("Punto de venta actualizado correctamente.",Constante.INFORMACION);
							 context.update("sms");
							 this.editar =  Boolean.FALSE;
							 this.puntoVenta=new PuntoVenta();
							 this.puntoVentaSelec=new PuntoVenta();
							 
						}else{					
							this.puntoVenta=this.puntoVentaSelec;
							valido=Boolean.TRUE;
							context.addCallbackParam("validationFailed", valido );
							FacesUtils.showFacesMessage("Impresora se encuentra ya registrada.",Constante.INFORMACION);
							 context.update("msgNuevo");
							 
						}
					*/
				}else{
						 	valido=Boolean.TRUE;
							context.addCallbackParam("validationFailed", valido );							
							FacesUtils.showFacesMessage("La IP ya se encuentra registrada.",Constante.INFORMACION);
							this.puntoVenta=this.puntoVentaSelec;	
							context.update("msgNuevo");
							this.puntoVentaSelec=null;
				}
				
			}
		else
			{
				
				//buscando IP en toda la tabla
				if (buscarIP(this.puntoVenta))
				{
					valido=Boolean.FALSE;
			   	    context.addCallbackParam("validationFailed", valido );
				
			   	    //sin validar nombres de impresora:
				   	 puntoVenta.setNamePuntoVenta(puntoVenta.getNamePuntoVenta().toUpperCase().trim());
					 this.puntoVenta.setEstado(Boolean.TRUE);
					this.puntoVentaService.crearPuntoVenta(puntoVenta);
					
					 log.setAccion("INSERT");
		             log.setDescripcion("Se inserta la Punto Venta: " + puntoVenta.getNamePuntoVenta());
		             logmb.insertarLog(log);
					FacesUtils.showFacesMessage("Punto de venta registrada correctamente.",Constante.INFORMACION);
					 context.update("sms");
						
			   	    /*
			   	     * validando nombre  de impresoras iguales
			   	    	if(buscarImpresora(this.puntoVenta))
						{
							
							puntoVenta.setNamePuntoVenta(puntoVenta.getNamePuntoVenta().toUpperCase().trim());
							 this.puntoVenta.setEstado(Boolean.TRUE);
							this.puntoVentaService.crearPuntoVenta(puntoVenta);
							
							 log.setAccion("INSERT");
				             log.setDescripcion("Se inserta la Punto Venta: " + puntoVenta.getNamePuntoVenta());
				             logmb.insertarLog(log);
							FacesUtils.showFacesMessage("Punto de venta registrada correctamente.",Constante.INFORMACION);
							 context.update("sms");
							
						}else{
							valido=Boolean.TRUE;
							context.addCallbackParam("validationFailed", valido );
							FacesUtils.showFacesMessage("Impresora se encuentra ya registrada.",Constante.INFORMACION);
							 context.update("msgNuevo");
						}
					*/
				}else{
					valido=Boolean.TRUE;
					context.addCallbackParam("validationFailed", valido );
					FacesUtils.showFacesMessage("La IP ya se encuentra registrada.",Constante.INFORMACION);
					 context.update("msgNuevo");
				}
				
				
			}
			
			listaPuntoVenta = this.puntoVentaService.listaPuntoVenta();
			
			/*
			else{
				valido=Boolean.TRUE;
				context.addCallbackParam("validationFailed", valido );
				FacesUtils.showFacesMessage("La IP ya se encuentra registrada.",Constante.INFORMACION);
				 context.update("msgNuevo");
			}
			*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//this.puntoVenta = new PuntoVenta();
		
		
	}
	
	public void cambiarEstado(PuntoVenta puntoVent){
		
		   if(puntoVent.isEstado()){
			   //System.out.println("es igual a uno se pone a cero");
			   puntoVent.setEstado(Boolean.FALSE);
			   //sistem.setInd_activo(new Integer(0));
		   }else{
			   //System.out.println("es igual a cero");
			   puntoVent.setEstado(Boolean.TRUE);
			   //sistem.setInd_activo(new Integer(1));
		   }
		   
		   try {
			   this.puntoVentaService.actualizarPuntoVenta(puntoVent);
			   log.setAccion("CHANGE ESTADO");
	           log.setDescripcion("Se cambio el estado en Punto Venta: " + puntoVenta.getDesAgencia());
	           logmb.insertarLog(log);
			   FacesUtils.showFacesMessage("Estado de punto de venta actualizado correctamente.",Constante.INFORMACION);
		   } catch (Exception e) {
			   System.out.println("Error:"+e.getMessage());
			   e.printStackTrace();
		   }   
	}
	
	public void eliminarPuntoVenta(){
		try {
			RequestContext context = RequestContext.getCurrentInstance();
        	
			int cant = this.usuarioServices.obtenerPuntoVentaxUsu(puntoVenta.getIdPuntoVenta());
			int cantCom=this.puntoVentaService.obtenerComponentesAsociados(puntoVenta.getIdPuntoVenta());
			if(cant == 0 && cantCom==0){
				this.puntoVentaService.eliminarPuntoVenta(puntoVenta.getIdPuntoVenta());
				listaPuntoVenta.remove(puntoVenta);
				FacesUtils.showFacesMessage("Punto venta eliminada correctamente.",Constante.INFORMACION);
				
				log.setAccion("DELETE");
		        log.setDescripcion("Se elimina el Punto de Venta: " + puntoVenta.getNamePuntoVenta());
		        logmb.insertarLog(log);	
			}else{
				context.execute("dlgOmiso.show()");
			}
			
	        

		} catch (Exception e) {
			e.printStackTrace();
		}
		this.puntoVenta =  new PuntoVenta();
		
	}
	
	
	public String agregarComponente(PuntoVenta  pv){
		String outcome = null;
		try {
			System.out.println("pv pasado:"+pv.getNamePuntoVenta());
    		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
    		flash.put("puntoVenta", pv);
    		//return "/faces/control_acceso/asignarPermisos?faces-redirect=true";
    		outcome="pretty:agregaComponente";
		} catch (Exception e) {	
			System.out.println("Error:"+e.getMessage());
			e.printStackTrace();
		}
		return outcome;
	}
	
	public boolean buscarIP(PuntoVenta puntoVenta){
		
		boolean respuesta=true;
		try {
			List<PuntoVenta> lista=this.puntoVentaService.buscarIP(puntoVenta);
			
			if(lista.size()>0){
				respuesta=false;
			}
		} catch (Exception e) {	
			System.out.println("Error:"+e.getMessage());
			e.printStackTrace();
		}
		
		return respuesta;
		
	}
	
	public boolean buscarIPDiferentes(PuntoVenta pv){
		boolean  respuesta=true;
		try {
			List<PuntoVenta> lista=this.puntoVentaService.buscarIPDiferentes(pv.getIdPuntoVenta(), pv.getNumeroIP());
			
			if(lista.size()>0){
				respuesta=false;
			}
		} catch (Exception e) {	
			System.out.println("Error:"+e.getMessage());
			e.printStackTrace();
		}
		
		return respuesta;
	}

	public boolean buscarImpresoraDiferentes(PuntoVenta pv){
		boolean  respuesta=true;
		try {
			List<PuntoVenta> lista=this.puntoVentaService.buscarImpresoraDiferentes(pv.getIdPuntoVenta(), pv.getNombreImpresora());
			
			if(lista.size()>0){
				respuesta=false;
			}
		} catch (Exception e) {	
			System.out.println("Error:"+e.getMessage());
			e.printStackTrace();
		}
		
		return respuesta;
	}
	
	public boolean buscarImpresora(PuntoVenta pv){
		boolean  respuesta=true;
		try {
			List<PuntoVenta> lista=this.puntoVentaService.buscarImpresora(pv.getNombreImpresora());
			
			if(lista.size()>0){
				respuesta=false;
			}
		} catch (Exception e) {	
			System.out.println("Error:"+e.getMessage());
			e.printStackTrace();
		}
		
		return respuesta;
	}
	//**set an get
	
	public MenuServices getMenuServices() {
		return menuServices;
	}

	public CategoriaServicioService getCategoriaServicioService() {
		return categoriaServicioService;
	}

	public void setCategoriaServicioService(
			CategoriaServicioService categoriaServicioService) {
		this.categoriaServicioService = categoriaServicioService;
	}

	public List<CategoriaServicio> getListaCatServicio() {
		return listaCatServicio;
	}

	public void setListaCatServicio(List<CategoriaServicio> listaCatServicio) {
		this.listaCatServicio = listaCatServicio;
	}

	public List<Oficina> getListaOficinas() {
		return listaOficinas;
	}

	public void setListaOficinas(List<Oficina> listaOficinas) {
		this.listaOficinas = listaOficinas;
	}

	public List<Agencia> getListaAgencia() {
		return listaAgencia;
	}

	public void setListaAgencia(List<Agencia> listaAgencia) {
		this.listaAgencia = listaAgencia;
	}

	public PuntoVenta getPuntoVenta() {
		return puntoVenta;
	}

	public void setPuntoVenta(PuntoVenta puntoVenta) {
		this.puntoVenta = puntoVenta;
	}

	public List<PuntoVenta> getListaPuntoVenta() {
		return listaPuntoVenta;
	}

	public void setListaPuntoVenta(List<PuntoVenta> listaPuntoVenta) {
		this.listaPuntoVenta = listaPuntoVenta;
	}

	public List<PuntoVenta> getListaFiltroPuntoVenta() {
		return listaFiltroPuntoVenta;
	}

	public void setListaFiltroPuntoVenta(List<PuntoVenta> listaFiltroPuntoVenta) {
		this.listaFiltroPuntoVenta = listaFiltroPuntoVenta;
	}

	public AgenciaService getAgenciaService() {
		return agenciaService;
	}

	public void setAgenciaService(AgenciaService agenciaService) {
		this.agenciaService = agenciaService;
	}

	public PuntoVentaService getPuntoVentaService() {
		return puntoVentaService;
	}

	public void setPuntoVentaService(PuntoVentaService puntoVentaService) {
		this.puntoVentaService = puntoVentaService;
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

	public LoginMB getLoginMB() {
		return loginMB;
	}

	public void setLoginMB(LoginMB loginMB) {
		this.loginMB = loginMB;
	}
	

	public Usuario getUsuarioLogin() {
		return usuarioLogin;
	}

	public void setUsuarioLogin(Usuario usuarioLogin) {
		this.usuarioLogin = usuarioLogin;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	
}

