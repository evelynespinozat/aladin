package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.CategoriaServicio;
import com.certicom.ittsa.domain.ClaseServicio;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.TipoAsiento;
import com.certicom.ittsa.services.AsientoServices;
import com.certicom.ittsa.services.CategoriaServicioService;
import com.certicom.ittsa.services.ClaseServicioServices;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.TipoAsientoService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name = "claseServicioMB")
@ViewScoped
public class ClaseServicioMB extends GenericBeans implements Serializable {
	private String titulo;
	private ClaseServicio claseServicio;
	private List<ClaseServicio> listaClaseServiciofiltro;
	private List<ClaseServicio> listaClaseServicio;
	private List<CategoriaServicio> listaCategoriaServicio;
	private Boolean editar;
	
	private List<TipoAsiento> listatipoAsiento;

	
	//services
	private ClaseServicioServices claseServicioServices;
	private CategoriaServicioService categoriaServicioService;
	private MenuServices menuServices;
	private AsientoServices asientoService;
	private TipoAsientoService tipoAsientoService;
	//datos Log
    private Log log;
	private LogMB logmb;
	
	private ClaseServicio claseServicioCapacidad;
	private List<ClaseServicio> listaClaseServicioCapacidad;
	
	public ClaseServicioMB() {
		;
	}

	@PostConstruct
	public void inicia() {
		this.claseServicio = new ClaseServicio();
		this.editar = Boolean.FALSE;
		this.claseServicioServices = new ClaseServicioServices();
		this.categoriaServicioService = new CategoriaServicioService();
		this.tipoAsientoService = new TipoAsientoService(); 
		this.menuServices=new MenuServices();
		this.asientoService = new AsientoServices();
		
		this.claseServicioCapacidad = new ClaseServicio();

		 log = (Log)getSpringBean(Constante.SESSION_LOG);
		 logmb = new LogMB();
		
		try {
			
			int codMenu = menuServices.opcionMenuByPretty("pretty:claseservicio");
			log.setCod_menu(codMenu);
			this.listaClaseServicio = this.claseServicioServices.findAll();
			for(ClaseServicio cls : this.listaClaseServicio)
			{
				ClaseServicio csc = this.claseServicioServices.findById(cls.getIdclasecapacidad());
				if(csc != null){
					cls.setClaseServicioCapacidad(csc);
				} else {
					cls.setClaseServicioCapacidad(new ClaseServicio());
				}
			}
			
			
			this.listaCategoriaServicio = this.categoriaServicioService.findAll();
			this.listatipoAsiento = this.tipoAsientoService.findAll();
			this.listaClaseServicioCapacidad = this.claseServicioServices.findAll();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void nuevoCServicio() {
		this.claseServicio = new ClaseServicio();
		this.claseServicioCapacidad = new ClaseServicio();
		this.editar = Boolean.FALSE;
		this.titulo = "Registrar nueva clase de servicio";
		try {
			this.listaClaseServicioCapacidad = this.claseServicioServices.findAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void editarCServicio(ClaseServicio cs) {
		this.claseServicio = cs ;
		this.claseServicioCapacidad = new ClaseServicio();
		System.out.println("color "+ cs.getColor().substring(1));
		this.claseServicio.setColor(cs.getColor().substring(1));
		this.editar = Boolean.TRUE;
		this.titulo = "Modificar clase de servicio";
		try {
			this.listaClaseServicioCapacidad = this.claseServicioServices.findAll();
			this.claseServicioCapacidad =this.claseServicioServices.findById(this.claseServicio.getIdclasecapacidad());
			if(this.claseServicioCapacidad!=null){
				this.claseServicioCapacidad.setIdclasecapacidad(this.claseServicio.getIdclasecapacidad());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void cambiarEstado(ClaseServicio cservicio) {

		if (cservicio.isEstado()) {
			cservicio.setEstado(Boolean.FALSE);
		} else {
			cservicio.setEstado(Boolean.TRUE);
		}

		try {
			this.claseServicioServices.actualizarClaseServicio(cservicio);
			log.setAccion("CHANGE ESTADO");
	        log.setDescripcion("Se cambio el estado en  Clase de Servicio: " + claseServicio.getDescripcion());
	        logmb.insertarLog(log);
			FacesUtils.showFacesMessage("Estado de clase de servicio actualizado correctamente.",Constante.INFORMACION);
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
		}

	}

	public void guardarClaseServicio() {
		Boolean valido = Boolean.TRUE;
		RequestContext context = RequestContext.getCurrentInstance();
		context.addCallbackParam("esValido", valido);
		
		this.claseServicio.setNombreCorto(this.claseServicio.getNombreCorto().trim().toUpperCase());
		this.claseServicio.setDescripcion(this.claseServicio.getDescripcion().trim().toUpperCase());
		this.claseServicio.setColor("#"+this.claseServicio.getColor());
//		if(this.claseServicio != null){
//		this.claseServicio.setIdclasecapacidad(this.claseServicioCapacidad.getIdclasecapacidad());
//		}

		try {
			if (this.editar) { // editando
				
				if(this.claseServicio.getNroPisos().equals(1))
				{
					this.claseServicio.setIdtipoasientop2(null);
					this.claseServicio.setNroColumnasPisoDos(0);
				}
				
				claseServicioServices.actualizarClaseServicio(claseServicio);
				log.setAccion("UPDATE");
	            log.setDescripcion("Se actualiza la clase servicio: " + claseServicio.getDescripcion());
	            logmb.insertarLog(log);
	            FacesUtils.showFacesMessage("Clase de servicio actualizada correctamente.",Constante.INFORMACION);
	            context.update("sms");
	        	this.listaClaseServicio = this.claseServicioServices.findAll();
				for(ClaseServicio cls : this.listaClaseServicio)
				{
					ClaseServicio csc = this.claseServicioServices.findById(cls.getIdclasecapacidad());
					if(csc != null){
						cls.setClaseServicioCapacidad(csc);
					} else {
						cls.setClaseServicioCapacidad(new ClaseServicio());
					}
				}
			} else {// guardando un nuevo registro
				this.claseServicio.setEstado(Boolean.TRUE);

				this.claseServicioServices.registrarClaseServicio(this.claseServicio);
				this.listaClaseServicio = this.claseServicioServices.findAll();
				/*
				for(ClaseServicio cls : this.listaClaseServicio)
				{
					ClaseServicio csc = this.claseServicioServices.findById(cls.getIdclasecapacidad());
					if(csc != null){
						cls.setClaseServicioCapacidad(csc);
					} else {
						cls.setClaseServicioCapacidad(new ClaseServicio());
					}
				}
				*/
				 log.setAccion("INSERT");
	             log.setDescripcion("Se registro la clase servicio: " + claseServicio.getDescripcion());
	             logmb.insertarLog(log);
				FacesUtils.showFacesMessage("Clase de servicio registrada correctamente.",Constante.INFORMACION);
				context.update("sms");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.editar = Boolean.FALSE;
		this.claseServicio = new ClaseServicio();
		
	}

	public void eliminarCServicio() {

		try {
			this.asientoService.eliminarAsientosxClase(claseServicio.getIdclase());
			
			this.claseServicioServices.eliminarClaseServicio(claseServicio.getIdclase());
			this.listaClaseServicio.remove(claseServicio);
			FacesUtils.showFacesMessage("Clase de servicio eliminada correctamente.",Constante.INFORMACION);
			log.setAccion("DELETE");
	        log.setDescripcion("Se elimina la clase servicio: " + claseServicio.getDescripcion());
	        logmb.insertarLog(log);
		} catch (Exception e) {
			FacesUtils.showFacesMessage("Clase de servicio no se puede eliminar, consulte con el administrador.",Constante.ERROR);
			e.printStackTrace();
		}
		this.claseServicio = new ClaseServicio();

	}
	
	public String irANuevaClaseServicio(ClaseServicio cservicio)
	{
		System.out.println("INICIO - irANuevaClaseServicio");
		System.out.println("entrando a metodo cservicio.getIdclase() " + cservicio.getIdclase());
		String outcome= null;
		Integer cantidad = 0;
		try {
			cantidad = this.asientoService.findByClase(cservicio.getIdclase());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.put("busClase", cservicio);
		
		
		if(cantidad > 0)
		{
			outcome="pretty:verClaseServicio";
		}else{
			outcome =  "pretty:nuevaClaseServicio";
		}
		flash.put("cantidad", cantidad);

		System.out.println("FIN - irANuevaClaseServicio");

		return outcome;
		
	}	
	
	
	
	// set and get 

	public ClaseServicio getClaseServicio() {
		return claseServicio;
	}

	public List<CategoriaServicio> getListaCategoriaServicio() {
		return listaCategoriaServicio;
	}

	public void setListaCategoriaServicio(
			List<CategoriaServicio> listaCategoriaServicio) {
		this.listaCategoriaServicio = listaCategoriaServicio;
	}

	public CategoriaServicioService getCategoriaServicioService() {
		return categoriaServicioService;
	}

	public void setCategoriaServicioService(
			CategoriaServicioService categoriaServicioService) {
		this.categoriaServicioService = categoriaServicioService;
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

	public void setClaseServicio(ClaseServicio claseServicio) {
		this.claseServicio = claseServicio;
	}

	public List<ClaseServicio> getListaClaseServiciofiltro() {
		return listaClaseServiciofiltro;
	}

	public void setListaClaseServiciofiltro(
			List<ClaseServicio> listaClaseServiciofiltro) {
		this.listaClaseServiciofiltro = listaClaseServiciofiltro;
	}

	public List<ClaseServicio> getListaClaseServicio() {
		return listaClaseServicio;
	}

	public void setListaClaseServicio(List<ClaseServicio> listaClaseServicio) {
		this.listaClaseServicio = listaClaseServicio;
	}

	public Boolean getEditar() {
		return editar;
	}

	public void setEditar(Boolean editar) {
		this.editar = editar;
	}

	public ClaseServicioServices getClaseServicioServices() {
		return claseServicioServices;
	}

	public void setClaseServicioServices(ClaseServicioServices claseServicioServices) {
		this.claseServicioServices = claseServicioServices;
	}


	public List<TipoAsiento> getListatipoAsiento() {
		return listatipoAsiento;
	}

	public ClaseServicio getClaseServicioCapacidad() {
		return claseServicioCapacidad;
	}

	public void setClaseServicioCapacidad(ClaseServicio claseServicioCapacidad) {
		this.claseServicioCapacidad = claseServicioCapacidad;
	}

	public void setListatipoAsiento(List<TipoAsiento> listatipoAsiento) {
		this.listatipoAsiento = listatipoAsiento;
	}

	public List<ClaseServicio> getListaClaseServicioCapacidad() {
		return listaClaseServicioCapacidad;
	}

	public void setListaClaseServicioCapacidad(
			List<ClaseServicio> listaClaseServicioCapacidad) {
		this.listaClaseServicioCapacidad = listaClaseServicioCapacidad;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	

}
