package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Flota;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Oficina;
import com.certicom.ittsa.domain.Usuario;
import com.certicom.ittsa.domain.UsuarioAutorizante;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.OficinaService;
import com.certicom.ittsa.services.UsuarioAutorizanteServices;
import com.certicom.ittsa.services.UsuarioServices;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;
import com.pe.certicom.ittsa.commons.UsuarioConverter;

@ManagedBean(name = "usuarioAutorizanteMB")
@ViewScoped
public class UsuarioAutorizanteMB extends GenericBeans implements Serializable {
	private String titulo;
	private UsuarioAutorizante usuarioAutorizante;
	private List<UsuarioAutorizante> listaUsuarioAutorizantefiltro;
	private List<UsuarioAutorizante> listaUsuarioAutorizante;
	private List<Agencia> listaAgencia;
	private List<Oficina> listaOficinas;
	
	
	
	private Boolean editar;
	private Boolean disIdUsu = false;
	private Usuario usuarioSelected;
	private List<Usuario> listaUsuariosActivos;
	
	

	private UsuarioAutorizanteServices usuarioAutorizanteServices;
	private MenuServices menuServices;
	private AgenciaService agenciaService;
	private OficinaService oficinaService;
	private UsuarioServices usuarioServices;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	public UsuarioAutorizanteMB() {
		;
	}

	@PostConstruct
	public void inicia() {
		this.usuarioAutorizante	 = new UsuarioAutorizante();
//		listaOficinas = new ArrayList<Oficina>();
//		listaOficinasFiltradas = new ArrayList<Oficina>();
		this.editar = Boolean.FALSE;
		this.usuarioAutorizanteServices = new UsuarioAutorizanteServices();
		this.menuServices=new MenuServices();
		this.agenciaService = new AgenciaService();
		this.oficinaService = new OficinaService();
		this.usuarioServices = new UsuarioServices();
		this.listaUsuariosActivos = UsuarioConverter.listaUsuarios;
		
		 log = (Log)getSpringBean(Constante.SESSION_LOG);
		 logmb = new LogMB();
		
		try {
			int codMenu = menuServices.opcionMenuByPretty("pretty:usuarioautorizante");
			log.setCod_menu(codMenu);
			this.listaUsuarioAutorizante = this.usuarioAutorizanteServices.findAll();
			this.listaAgencia = agenciaService.listaAgenciasActivas();
			this.listaOficinas = oficinaService.listaOficinasActivas();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void nuevoUsuAut() {
		this.usuarioAutorizante = new UsuarioAutorizante();
		this.editar = Boolean.FALSE;
		this.listaOficinas = new ArrayList<Oficina>();
		this.usuarioSelected = new Usuario();
		this.disIdUsu = Boolean.FALSE;
		this.titulo = "Registrar nuevo usuario autorizante";
	}
	
	public void editarUsuAut(UsuarioAutorizante ua) {
		this.usuarioAutorizante = ua;
		this.editar = Boolean.TRUE;
		this.titulo = "Modificar usuario autorizante";
		try {
			this.listaOficinas = this.oficinaService.getOficinasxAgencia(this.usuarioAutorizante.getIdAgencia());
		} catch (Exception e) {
			e.printStackTrace();	
		}
		this.usuarioSelected = new Usuario();
		this.disIdUsu = Boolean.TRUE;
	}

	public void cambiarEstado(UsuarioAutorizante usuarioAutorizante) {

		if (usuarioAutorizante.isEstado()) {
			usuarioAutorizante.setEstado(Boolean.FALSE);
		} else {
			usuarioAutorizante.setEstado(Boolean.TRUE);
		}

		try {
			this.usuarioAutorizanteServices.actualizarUsuarioAutorizante(usuarioAutorizante);
			 log.setAccion("CHANGE ESTADO");
	         log.setDescripcion("Se cambio el estado en Usuario Autorizante: " + usuarioAutorizante.getNombres());
	         logmb.insertarLog(log);
			FacesUtils.showFacesMessage("Usuario autorizante actualizado correctamente.",Constante.INFORMACION);
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
		}

	}

	public void guardarUAutorizante() {
		Boolean valido = Boolean.TRUE;
		RequestContext context = RequestContext.getCurrentInstance();
		context.addCallbackParam("esValido", valido);
		
		this.usuarioAutorizante.setCargo(this.usuarioAutorizante.getCargo().toUpperCase());
		this.usuarioAutorizante.setAbreNombre(this.usuarioAutorizante.getAbreNombre().toUpperCase());
		try {
			if (this.editar) { // editando
				usuarioAutorizanteServices.actualizarUsuarioAutorizante(usuarioAutorizante);
				log.setAccion("UPDATE");
	            log.setDescripcion("Se actualiza el usuario autorizante: " + usuarioAutorizante.getNombres());
	            logmb.insertarLog(log);
	            
	            FacesUtils.showFacesMessage("Usuario autorizante actualizado correctamente.",Constante.INFORMACION);
	            context.update("sms");
			} else {// guardando un nuevo registro
				this.usuarioAutorizante.setEstado(Boolean.TRUE);

				this.usuarioAutorizanteServices.registrarUsuarioAutorizante(this.usuarioAutorizante);
				//this.listaUsuarioAutorizante = usuarioAutorizanteServices.findAll();
				 log.setAccion("INSERT");
	             log.setDescripcion("Se registro el Usuario Autorizante: " + usuarioAutorizante.getNombres());
	             logmb.insertarLog(log);
				FacesUtils.showFacesMessage("Usuario autorizante registrado correctamente.",Constante.INFORMACION);
				context.update("sms");
			}
			this.listaUsuarioAutorizante = usuarioAutorizanteServices.findAll();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.editar = Boolean.FALSE;
		this.usuarioAutorizante = new UsuarioAutorizante();
		
		

	}

	public void eliminarUAutorizante() {

		try {
			this.usuarioAutorizanteServices.eliminarUsuarioAutorizante(usuarioAutorizante.getIdAutorizante());
			this.listaUsuarioAutorizante.remove(usuarioAutorizante);
			FacesUtils.showFacesMessage("Usuario autorizante eliminado correctamente.",Constante.INFORMACION);
			log.setAccion("DELETE");
	        log.setDescripcion("Se elimina el usuario autorizante: " + usuarioAutorizante.getNombres());
	        logmb.insertarLog(log);
		} catch (Exception e) {
			FacesUtils.showFacesMessage("Usuario autorizante no se puede eliminar, consulte con el administrador.",Constante.ERROR);
			e.printStackTrace();
		}
		this.usuarioAutorizante = new UsuarioAutorizante();

	}
	
	public void listarOficinas(){
		try {
			this.listaOficinas = this.oficinaService.getOficinasxAgencia(this.usuarioAutorizante.getIdAgencia());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getUsuarioxDni(){
		try {
			if(this.usuarioAutorizante.getDni() != null && !this.usuarioAutorizante.getDni().isEmpty()){
				Usuario  u = this.usuarioServices.getUsuarioxDni(usuarioAutorizante.getDni());
				if(u != null){
					this.usuarioAutorizante.setIdUsuario(u.getIdusuario());
					this.usuarioAutorizante.setNombres(u.getNombre_completo());
					this.usuarioAutorizante.setEmail(u.getEmail());
				} else {
					FacesUtils.showFacesMessage("No se encontró el usuario con el DNI ingresado.",Constante.ERROR);
				}
			}
		} catch (Exception e) {
			FacesUtils.showFacesMessage("No se encontró el usuario.",Constante.ERROR);
			e.printStackTrace();
		}
	}
	
	public List<Usuario> completeUsuario(String query){
		 List<Usuario> suggestions = new ArrayList<Usuario>();
	        for(Usuario p : this.listaUsuariosActivos) {  
	        	if(p.getDni().startsWith(query) || p.getNombre().contains(query)  || p.getApellido_paterno().contains(query) 
						|| p.getApellido_materno().contains(query)){
					suggestions.add(p); 	
				} 
	        }  
	          
	        return suggestions;  
	}
	
	public void setearUsuario(SelectEvent event){
		Usuario u = (Usuario)event.getObject();
		this.usuarioAutorizante.setIdUsuario(u.getIdusuario());
		this.usuarioAutorizante.setDni(u.getDni());
		this.usuarioAutorizante.setNombres(u.getNombre() + " " + u.getApellido_materno() + " " + u.getApellido_materno());
		this.usuarioAutorizante.setEmail(u.getEmail());
		this.disIdUsu = Boolean.TRUE;
	}
	
	public UsuarioAutorizante getUsuarioAutorizante() {
		return usuarioAutorizante;
	}

	public void setUsuarioAutorizante(UsuarioAutorizante usuarioAutorizante) {
		this.usuarioAutorizante = usuarioAutorizante;
	}

	public List<UsuarioAutorizante> getListaUsuarioAutorizantefiltro() {
		return listaUsuarioAutorizantefiltro;
	}

	public void setListaUsuarioAutorizantefiltro(
			List<UsuarioAutorizante> listaUsuarioAutorizantefiltro) {
		this.listaUsuarioAutorizantefiltro = listaUsuarioAutorizantefiltro;
	}

	public List<UsuarioAutorizante> getListaUsuarioAutorizante() {
		return listaUsuarioAutorizante;
	}

	public void setListaUsuarioAutorizante(
			List<UsuarioAutorizante> listaUsuarioAutorizante) {
		this.listaUsuarioAutorizante = listaUsuarioAutorizante;
	}

	public Boolean getEditar() {
		return editar;
	}

	public void setEditar(Boolean editar) {
		this.editar = editar;
	}

	public UsuarioAutorizanteServices getUsuarioAutorizanteServices() {
		return usuarioAutorizanteServices;
	}

	public void setUsuarioAutorizanteServices(
			UsuarioAutorizanteServices usuarioAutorizanteServices) {
		this.usuarioAutorizanteServices = usuarioAutorizanteServices;
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

	public List<Agencia> getListaAgencia() {
		return listaAgencia;
	}

	public void setListaAgencia(List<Agencia> listaAgencia) {
		this.listaAgencia = listaAgencia;
	}

	public List<Oficina> getListaOficinas() {
		return listaOficinas;
	}

	public void setListaOficinas(List<Oficina> listaOficinas) {
		this.listaOficinas = listaOficinas;
	}

	public AgenciaService getAgenciaService() {
		return agenciaService;
	}

	public void setAgenciaService(AgenciaService agenciaService) {
		this.agenciaService = agenciaService;
	}

	public OficinaService getOficinaService() {
		return oficinaService;
	}

	public void setOficinaService(OficinaService oficinaService) {
		this.oficinaService = oficinaService;
	}

	public UsuarioServices getUsuarioServices() {
		return usuarioServices;
	}

	public void setUsuarioServices(UsuarioServices usuarioServices) {
		this.usuarioServices = usuarioServices;
	}

	public Usuario getUsuarioSelected() {
		return usuarioSelected;
	}

	public void setUsuarioSelected(Usuario usuarioSelected) {
		this.usuarioSelected = usuarioSelected;
	}

	public List<Usuario> getListaUsuariosActivos() {
		return listaUsuariosActivos;
	}

	public void setListaUsuariosActivos(List<Usuario> listaUsuariosActivos) {
		this.listaUsuariosActivos = listaUsuariosActivos;
	}

	public Boolean getDisIdUsu() {
		return disIdUsu;
	}

	public void setDisIdUsu(Boolean disIdUsu) {
		this.disIdUsu = disIdUsu;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	// set and get 

	
	
	
}
