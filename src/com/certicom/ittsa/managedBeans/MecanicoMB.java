package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.Almacen;
import com.certicom.ittsa.domain.Mecanico;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.services.MecanicoService;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.OficinaService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="mecanicoMB")
@ViewScoped
public class MecanicoMB extends GenericBeans implements Serializable{
	private String titulo;
	private Mecanico mecanico;
	private List<Mecanico> listaMecanicos;
	private List<Mecanico> listaFiltroMecanicos;
	private Boolean editar;
	
	//services
	private MecanicoService mecanicoSevice;
	private MenuServices menuServices;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	public MecanicoMB(){;}
	
	@PostConstruct
	public void incia()
	{
		this.editar = Boolean.FALSE;
	
		this.mecanicoSevice = new MecanicoService();
		this.menuServices=new MenuServices();
		this.mecanico = new Mecanico();
		
		 log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		//listando
		try {
			this.listaMecanicos = mecanicoSevice.findAll();
			int codMenu = menuServices.opcionMenuByPretty("pretty:mecanico");
			log.setCod_menu(codMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cancelar(){
		this.mecanico = new Mecanico();
	}

	public void guardarMecanico()
	{
		Boolean valido=Boolean.TRUE;
		RequestContext context = RequestContext.getCurrentInstance();  
   	    context.addCallbackParam("esValido", valido );
   	    
   	    this.mecanico.setNombre(this.mecanico.getNombre().toUpperCase());
   	    this.mecanico.setApePaterno(this.mecanico.getApePaterno().toUpperCase());
   	    this.mecanico.setApeMaterno(this.mecanico.getApeMaterno().toUpperCase());
   	    this.mecanico.setDireccion(this.mecanico.getDireccion().toUpperCase());
		try {
			if(this.editar)
			{
				this.mecanico.setIdMecanico(this.mecanico.getIdMecanico());
				this.mecanicoSevice.actualizarMecanico(mecanico);
				 log.setAccion("UPDATE");
	             log.setDescripcion("Se actualiza la mecanico: " + mecanico.getNombre() +" "+ mecanico.getApePaterno() +" "+ mecanico.getApeMaterno());
	             logmb.insertarLog(log);
				FacesUtils.showFacesMessage("Mecanico actualizado correctamente.",Constante.INFORMACION);
				context.update("sms");
			}else
			{ 
				this.mecanico.setEstado(Boolean.TRUE);
				this.mecanicoSevice.crearMecanico(this.mecanico);
				 log.setAccion("INSERT");
	             log.setDescripcion("Se inserta la mecanico: " + mecanico.getNombre() +" "+ mecanico.getApePaterno() +" "+ mecanico.getApeMaterno());
	             logmb.insertarLog(log);
				 FacesUtils.showFacesMessage("Mecánico registrado correctamente.",Constante.INFORMACION);
				 context.update("sms");
			}
			listaMecanicos = this.mecanicoSevice.findAll();
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.mecanico = new Mecanico();
		this.editar =  Boolean.FALSE;
		
	}
	
	public void cambiarEstado(Mecanico mec){
		
		   System.out.println("entro el valor del mec " + mec.isEstado());
		   if(mec.isEstado()){
			   mec.setEstado(Boolean.FALSE);
		   }else{
			   mec.setEstado(Boolean.TRUE);
		   }
		   
		   try {
			   System.out.println("el valordel mec es  " + mec.isEstado());
			   this.mecanicoSevice.actualizarMecanico(mec);
			   log.setAccion("CHANGE ESTADO");
	           log.setDescripcion("Se cambio el estado en Mecanico: " + mecanico.getNombre() +" "+ mecanico.getApePaterno() +" "+ mecanico.getApeMaterno());
	           logmb.insertarLog(log);
			   FacesUtils.showFacesMessage("Estado de mecánico actualizado correctamente.",Constante.INFORMACION);
		   } catch (Exception e) {
			   System.out.println("Error:"+e.getMessage());
			   e.printStackTrace();
		   }   
	}
	
	public void eliminarMecanico()
	{
		try {
			RequestContext context = RequestContext.getCurrentInstance();
			
				this.mecanicoSevice.eliminarMecanico(mecanico.getIdMecanico());
				listaMecanicos.remove(mecanico);
				FacesUtils.showFacesMessage("Mecanico eliminado correctamente.",Constante.INFORMACION);
				
				log.setAccion("DELETE");
				log.setDescripcion("Se eliminó la Mecanico: " + mecanico.getNombre() +" "+ mecanico.getApePaterno() +" "+ mecanico.getApeMaterno());
				logmb.insertarLog(log);

		} catch (Exception e) {
			e.printStackTrace();
		}
		this.mecanico =  new Mecanico();
		
	}
	
	public void nuevoMecanico()
	{
		this.editar = Boolean.FALSE;
		this.mecanico = new Mecanico();
		this.titulo = "Registrar nuevo mecánico";
	}
	
	public void editarMecanico(Mecanico ag)
	{
		this.editar = Boolean.TRUE;
		this.mecanico = ag;
		this.titulo = "Modificar mecánico";
	}
	

	//**set an get 
	
	
	public MenuServices getMenuServices() {
		return menuServices;
	}

	public void setMenuServices(MenuServices menuServices) {
		this.menuServices = menuServices;
	}

	public List<Mecanico> getListaFiltroMecanicos() {
		return listaFiltroMecanicos;
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

	public void setListaFiltroMecanicos(List<Mecanico> listaFiltroMecanicos) {
		this.listaFiltroMecanicos = listaFiltroMecanicos;
	}

	public Mecanico getMecanico() {
		return mecanico;
	}

	public void setMecanico(Mecanico mecanico) {
		this.mecanico = mecanico;
	}

	public List<Mecanico> getListaMecanicos() {
		return listaMecanicos;
	}

	public void setListaMecanicos(List<Mecanico> listaMecanicos) {
		this.listaMecanicos = listaMecanicos;
	}

	public Boolean getEditar() {
		return editar;
	}

	public void setEditar(Boolean editar) {
		this.editar = editar;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	
}

