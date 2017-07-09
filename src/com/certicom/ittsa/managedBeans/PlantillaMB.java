package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.ClaseServicio;
import com.certicom.ittsa.domain.Destino;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Oficina;
import com.certicom.ittsa.domain.Plantilla;
import com.certicom.ittsa.domain.Servicio;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.ClaseServicioServices;
import com.certicom.ittsa.services.DestinoServices;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.OficinaService;
import com.certicom.ittsa.services.PlantillaService;
import com.certicom.ittsa.services.ServicioService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="plantillaMB")
@ViewScoped
public class PlantillaMB extends GenericBeans implements Serializable{
	private String titulo;
	private Plantilla plantilla;
	private List<Plantilla> listaPlantillas;
	private List<Plantilla> listaFiltroPlantillas;
	private Boolean editar;
	private Integer codigoDestino;
	
	private List<Agencia> listaAgencias;
	private List<Oficina> listaOficinas;
	private List<Agencia> listaOrigen;
	private List<Destino> listaDestino;
	private List<ClaseServicio> listaCServicio;
	private List<Servicio> listaServicios;
	
	
	//services
	private AgenciaService agenciaService;
	private OficinaService oficinaService;
	private DestinoServices destinoServices;
	private ClaseServicioServices claseServicioServices;
	private PlantillaService plantillaSevice;
	private MenuServices menuServices;
	private ServicioService servicioService;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	public PlantillaMB(){;}
	
	@PostConstruct
	public void incia()
	{
		this.editar = Boolean.FALSE;

		this.plantilla = new Plantilla();
		this.plantillaSevice = new PlantillaService();
		this.menuServices=new MenuServices();
		this.agenciaService = new AgenciaService();
		this.oficinaService = new OficinaService();
		this.destinoServices = new DestinoServices();
		this.claseServicioServices = new ClaseServicioServices();
		this.servicioService = new ServicioService();
		
		 log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		//listando
		try {
			this.listaPlantillas = plantillaSevice.findAll();
			this.listaAgencias = agenciaService.listaAgenciasActivas();
			this.listaOrigen = this.listaAgencias;
			this.listaCServicio = claseServicioServices.listaCServiciosActivos();
			int codMenu = menuServices.opcionMenuByPretty("pretty:plantillaProd");
			log.setCod_menu(codMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void listarDestinosxOri(){
		try {
			this.listaDestino = this.destinoServices.obtenerDestino(this.plantilla.getIdOrigen());
			for(int i=0 ; i<this.listaDestino.size(); i++){
				Destino des = this.listaDestino.get(i);
				System.out.println("el valor del id es " + des.getDestino());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void listarServiciosOriDes(){
		try {
			this.listaServicios = this.servicioService.getServiciosOriDesClase(this.plantilla.getIdOrigen(),this.plantilla.getIdDestino(), this.plantilla.getIdClase());
			System.out.println("this.listaServicios.size() "+ this.listaServicios.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void pintarDestino(){
		this.plantilla.setIdDestino(this.plantilla.getIdDestino());
	}
	
	public void cancelar(){
		this.plantilla = new Plantilla();
	}

	public void guardarPlantilla()
	{
		Boolean valido=Boolean.TRUE;
		RequestContext context = RequestContext.getCurrentInstance();  
   	    context.addCallbackParam("esValido", valido );
   	    
		try {
			plantilla.setDescripcion(plantilla.getDescripcion().toUpperCase().trim());
			//plantilla.setGrupo(plantilla.getGrupo().toUpperCase().trim());
			if(this.editar)
			{
				this.plantillaSevice.actualizarPlantilla(plantilla);
				 log.setAccion("UPDATE");
	             log.setDescripcion("Se actualizao la plantilla: " + plantilla.getDescripcion());
	             logmb.insertarLog(log);
				FacesUtils.showFacesMessage("Plantilla actualizada correctamente.",Constante.INFORMACION);
				context.update("sms");
			}else
			{
				this.plantillaSevice.crearPlantilla(plantilla);
				 log.setAccion("INSERT");
	             log.setDescripcion("Se inserto la plantilla: " + plantilla.getDescripcion());
	             logmb.insertarLog(log);
				 FacesUtils.showFacesMessage("Plantilla registrada correctamente.",Constante.INFORMACION);
				 context.update("sms");
			}
			listaPlantillas = this.plantillaSevice.findAll();
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.plantilla = new Plantilla();
		this.editar =  Boolean.FALSE;
		
	}
	
	public void getOficinasxAgencia(){
		try {
			this.listaOficinas = this.oficinaService.getOficinasxAgencia(this.plantilla.getIdagencia());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void eliminarPlantilla()
	{
		try {
			RequestContext context = RequestContext.getCurrentInstance();
			
			int cantPlantillas = 0;
			//cantPlantillas = this.plantillaSevice.cantProdxPlantilla(plantilla.getIdPlantilla());
			if(cantPlantillas == 0){
				this.plantillaSevice.eliminarPlantilla(plantilla.getIdPlantilla());
				listaPlantillas.remove(plantilla);
				FacesUtils.showFacesMessage("Plantilla eliminada correctamente.",Constante.INFORMACION);
				
				log.setAccion("DELETE");
				log.setDescripcion("Se elimina la Plantilla: " + plantilla.getDescripcion());
				logmb.insertarLog(log);
				
			} else {
				context.execute("dlgOmiso.show()");
			}
			
			
			

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.plantilla =  new Plantilla();
		
	}
	
	public void editarPlantilla(Plantilla ag)
	{
		this.editar = Boolean.TRUE;
		this.plantilla = ag;
		this.titulo = "Modificar plantilla";
		try {
			listaOficinas = this.oficinaService.getOficinasxAgencia(this.plantilla.getIdagencia());
			listaDestino = this.destinoServices.obtenerDestino(this.plantilla.getIdOrigen());
			listaServicios = this.servicioService.getServiciosOriDesClase(this.plantilla.getIdOrigen(), this.plantilla.getIdDestino(), this.plantilla.getIdClase());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void nuevaPlantilla()
	{
		this.editar = Boolean.FALSE;
		this.plantilla = new Plantilla();
		this.titulo = "Registrar nueva plantilla";
	}
	
	public String agregarProductos(Plantilla p){
		String outcome = null;
		try {
			Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
			flash.put("plantilla", p);
			outcome = "pretty:plantillaDetalle";
		} catch (Exception e) {
			System.out.println("error " + e.getMessage());
			e.printStackTrace();
		}
		return outcome;
	}
	

	//**set an get 
	
	
	
	public MenuServices getMenuServices() {
		return menuServices;
	}

	public void setMenuServices(MenuServices menuServices) {
		this.menuServices = menuServices;
	}

	public List<Plantilla> getListaFiltroPlantillas() {
		return listaFiltroPlantillas;
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

	public void setListaFiltroPlantillas(List<Plantilla> listaFiltroPlantillas) {
		this.listaFiltroPlantillas = listaFiltroPlantillas;
	}

	public Plantilla getPlantilla() {
		return plantilla;
	}

	public void setPlantilla(Plantilla plantilla) {
		this.plantilla = plantilla;
	}

	public List<Plantilla> getListaPlantillas() {
		return listaPlantillas;
	}

	public void setListaPlantillas(List<Plantilla> listaPlantillas) {
		this.listaPlantillas = listaPlantillas;
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

	public List<Oficina> getListaOficinas() {
		return listaOficinas;
	}

	public PlantillaService getPlantillaSevice() {
		return plantillaSevice;
	}

	public OficinaService getOficinaService() {
		return oficinaService;
	}

	public void setListaAgencias(List<Agencia> listaAgencias) {
		this.listaAgencias = listaAgencias;
	}

	public void setListaOficinas(List<Oficina> listaOficinas) {
		this.listaOficinas = listaOficinas;
	}

	public void setPlantillaSevice(PlantillaService plantillaSevice) {
		this.plantillaSevice = plantillaSevice;
	}

	public void setOficinaService(OficinaService oficinaService) {
		this.oficinaService = oficinaService;
	}

	public List<Agencia> getListaOrigen() {
		return listaOrigen;
	}

	public List<Destino> getListaDestino() {
		return listaDestino;
	}

	public void setListaOrigen(List<Agencia> listaOrigen) {
		this.listaOrigen = listaOrigen;
	}

	public void setListaDestino(List<Destino> listaDestino) {
		this.listaDestino = listaDestino;
	}

	public List<ClaseServicio> getListaCServicio() {
		return listaCServicio;
	}

	public void setListaCServicio(List<ClaseServicio> listaCServicio) {
		this.listaCServicio = listaCServicio;
	}

	public List<Servicio> getListaServicios() {
		return listaServicios;
	}

	public void setListaServicios(List<Servicio> listaServicios) {
		this.listaServicios = listaServicios;
	}

	public Integer getCodigoDestino() {
		return codigoDestino;
	}

	public void setCodigoDestino(Integer codigoDestino) {
		this.codigoDestino = codigoDestino;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	
	
}

