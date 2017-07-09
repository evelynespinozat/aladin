package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.primefaces.context.RequestContext;


import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Destino;
import com.certicom.ittsa.domain.Enlace;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Programacion;
import com.certicom.ittsa.domain.Servicio;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.DestinoServices;
import com.certicom.ittsa.services.EnlaceService;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.ServicioService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="enlaceMB")
@ViewScoped
public class EnlaceMB extends GenericBeans implements Serializable{

	private Enlace enlace;
	private List<Enlace> listaEnlaces;
	private List<Enlace> listaFiltroEnlaces;
	private List<Enlace> listaEnlacesTramos;
	private List<Agencia> listaAgencias;
	private List<Destino> listaDestino;
	private List<Servicio> listaServicios;
	private List<Servicio> listaFiltroServicio;
	private List<Servicio> listaTramosEnlace;
	private List<Agencia> listaAgeTramos;
	private Programacion programacionFilter;
	private Boolean editar;
	private Servicio selectedServicio;
	private Enlace enlaceUno;
	private Enlace enlaceDos;
	
	//services
	private EnlaceService enlaceService;
	private AgenciaService agenciaService;
	private DestinoServices destinoServices;
	private ServicioService servicioService;
	private MenuServices menuServices;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	
	// 03/03/2014

	private Servicio servTramoOrig;
	private String tipo;
	
	//will tu api
	List<Servicio> listaServiciosCompartidos; 
	
	public EnlaceMB(){;}
	
	@PostConstruct
	public void incia()
	{
		this.editar = Boolean.FALSE;
		this.agenciaService = new AgenciaService();
		this.destinoServices = new DestinoServices();
		this.enlaceService = new EnlaceService();
		this.menuServices=new MenuServices();
		this.servicioService = new ServicioService();
		this.enlace = new Enlace();
		this.programacionFilter = new Programacion();
		this.selectedServicio = new Servicio();
		this.enlaceUno = new Enlace();
		this.enlaceDos = new Enlace();
		this.listaServiciosCompartidos =  new ArrayList<>();
		
		this.listaServicios = new ArrayList<>();
		this.listaAgencias = new ArrayList<>();
		this.listaEnlaces = new ArrayList<>();
		this.listaTramosEnlace = new ArrayList();
		this.listaAgeTramos = new ArrayList<Agencia>();
		
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		Programacion programacion=(Programacion) flash.get("programacion");
		
		if(programacion != null)
		{
			System.out.println("La rogramacion pasada es:"+programacion);
			if (programacion.getDesTipoFrecuencia().equals("1") )//frecuente
			{
				this.tipo = "1";
			}else{//demanda
				this.tipo = "2";
			}
			
			this.programacionFilter.setOrigen(programacion.getOrigen());
			this.programacionFilter.setDestino(programacion.getDestino());
			this.listarDestinosxOri();
			this.programacionFilter.setIdServicio(programacion.getIdServicio());
		}

		
		
		log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		//listando
		try {
			this.listaAgencias = agenciaService.listaAgenciasActivas();
			this.listaAgeTramos = this.listaAgencias;
			this.listaEnlaces = enlaceService.listaEnlaces();
			int codMenu = menuServices.opcionMenuByPretty("pretty:enlace");
			log.setCod_menu(codMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
	}
	
	public void cancelar(){
		this.enlace = new Enlace();
	}
	
	/*
	public void buscarServicio() {
		try {
             //en este caso es de tipo buses 
			programacionFilter.setIdCatServicio(4);
			
			this.listaServicios.clear();
			this.listaServicios = this.servicioService.findByOrigenDestinoRutaCompartida(this.programacionFilter.getOrigen(),this.programacionFilter.getDestino());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/
	
	
	public void buscarServicio() {
		try {
			this.listaServicios = new ArrayList<>();
             //en este caso es de tipo buses 
			programacionFilter.setIdCatServicio(4);
			
			//this.listaServicios = this.servicioService.findByOrigenDestinoRutaCompartida(this.programacionFilter.getOrigen(),this.programacionFilter.getDestino());
			this.listaServicios = this.servicioService.listaServiciosPadres(Integer.parseInt(this.tipo),this.programacionFilter.getOrigen(),this.programacionFilter.getDestino());
			this.listaServiciosCompartidos = new ArrayList<>();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void seleccionar()
	{
		this.buscarTramos(this.servTramoOrig);
	}
	
	public void buscarTramos(Servicio s)
	{
		
		
		try {
			this.servTramoOrig = this.servicioService.findById(s.getIdServicio());
			
			this.listaServiciosCompartidos = this.servicioService.listaHijosServiciosCompartidos(this.servTramoOrig.getIntCorre().intValue() == 0?null:this.servTramoOrig.getIntCorre());
			
			if(this.listaServiciosCompartidos.isEmpty())
			{
				this.listaServiciosCompartidos.add(s);
			}
			
			
			
			/*
			this.listaEnlacesTramos = this.enlaceService.listarTramosServicio(s.getIdServicio());
			Enlace enlace1 = this.listaEnlacesTramos.get(0);  //9
			Enlace enlace2 = this.listaEnlacesTramos.get(1);  //12
			//Nombre de los tramos
			System.out.println("enlace 1 O  " +enlace1.getDesOrigen() );
			System.out.println("enlace 1 D  " +enlace1.getDesDestino() );
			this.enlaceUno.setDesOrigen(enlace1.getDesOrigen());
			this.enlaceUno.setDesDestino(enlace1.getDesDestino());
			this.enlaceUno.setDesServicio(enlace1.getDesServicio());
			this.enlaceDos.setDesOrigen(enlace2.getDesOrigen());
			*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void guardarEnlace()
	{
		Boolean valido=Boolean.TRUE;
		RequestContext context = RequestContext.getCurrentInstance();  
   	    context.addCallbackParam("esValido", valido );
   	    
		try {

			if(this.editar)
			{
				this.enlace.setIdEnlace(this.enlace.getIdEnlace());
				this.enlaceService.actualizarEnlace(enlace);
				 log.setAccion("UPDATE");
	             log.setDescripcion("Se actualiza la enlace: " + enlace.getServicioOrigen() + " - " + enlace.getServicioTramo());
	             logmb.insertarLog(log);
				FacesUtils.showFacesMessage("Enlace actualizado correctamente.",Constante.INFORMACION);
				context.update("sms");
				listaEnlaces = this.enlaceService.listaEnlaces();
			}else
			{
				if(selectedServicio != null){
					this.enlace.setEstado(Boolean.TRUE);
					this.enlace.setServicioTramo(selectedServicio.getIdServicio());
					this.enlaceService.crearEnlace(enlace);
					 log.setAccion("INSERT");
		             log.setDescripcion("Se inserta el enlace: " + enlace.getServicioOrigen() + " - " + enlace.getServicioTramo());
		             logmb.insertarLog(log);
					 FacesUtils.showFacesMessage("Enlace registrado correctamente.",Constante.INFORMACION);
					 context.update("sms");
					 listaEnlaces = this.enlaceService.listaEnlaces();
				}else{
					FacesUtils.showFacesMessage("Seleccione un tramo.",Constante.ERROR);
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.enlace = new Enlace();
		this.editar =  Boolean.FALSE;
		
	}
	
	public void filtrarDestinos(Servicio servi){
		try {
			this.enlace.setServicioOrigen(servi.getIdServicio());
			this.listaTramosEnlace = servicioService.obtenerEnlaces(servi);
		} catch (Exception e) {
		   e.printStackTrace();
		}
		
	}
	
	public void cambiarEstado(Enlace enla){
		
		   if(enla.isEstado()){
			   enla.setEstado(Boolean.FALSE);
		   }else{
			   enla.setEstado(Boolean.TRUE);
		   }
		   
		   try {
			   this.enlaceService.actualizarEnlace(enla);
			   log.setAccion("CHANGE ESTADO");
	           log.setDescripcion("Se cambio el estado en Enlace: " + enlace.getRuta());
	           logmb.insertarLog(log);
			   FacesUtils.showFacesMessage("Estado de enlace actualizado correctamente.",Constante.INFORMACION);
		   } catch (Exception e) {
			   System.out.println("Error:"+e.getMessage());
			   e.printStackTrace();
		   }   
	}
	
	public void registrarEnlace(){

		try
		{
			
			Integer corre;
			
			//obteniendo correlativo
			this.servTramoOrig = this.servicioService.findById(this.servTramoOrig.getIdServicio());
			if(this.servTramoOrig.getIntCorre().intValue()==0)
			{
				corre = (this.enlaceService.obtenerCorrelativo()) +1; //coje el ultimo correlativo
			}else{
				corre =  this.servTramoOrig.getIntCorre(); //le asigna el que tiene
			}
			
			String horaEnlaceMedio = this.enlace.getHora().trim().toUpperCase();
			//-------------------------------------------- CREANDO SERVICIO HIJO ------------------------------------------------
			Servicio s1 = this.servTramoOrig;
			s1.setIntCorre(corre); //lo hijos tiene el mismo correlativo que el padre
			s1.setOrigen(this.enlace.getOrigen());
			s1.setDestino(this.enlace.getDestino());
			s1.setPrecio1(this.enlace.getPrecio1());
			s1.setPrecio2(this.enlace.getPrecio2());
			s1.setHorasViaje(this.enlace.getHorasViaje());
			s1.setHSalida(horaEnlaceMedio);
			s1.setHLlegada(this.servicioService.sumaHoras(s1.getHora24(), s1.getHorasViaje())); //calculo con hor ade slaida + nro de horas
			s1.setHora24(this.servicioService.convertirHSalida24(s1.getHSalida()));//calculo con hroa de salida
			s1.setFRegistro(new Date());
			s1.setEstado(Boolean.TRUE);
			//s1.setTipoFrecuencia("1");
			s1.setRutaCompartida("SI");
			
			if(s1.getTipoFrecuencia().equals("2")) //demanda
			{
				s1.setRutaCompEnCaliente("SI");
			}else{
				s1.setRutaCompEnCaliente("NO");
			}
			
			
			s1.setGradoServ(2);//los hijos tiene grado servicio dos, el padre tiene como valor 1
			this.servTramoOrig.setIntCorre(corre);
			this.servicioService.actualizarServicioCorrelativo(this.servTramoOrig);
			
			this.servicioService.crearServicio(s1);																																		
			
			this.enlaceService.actualizarCorrelativo(1,corre);
			
			this.buscarTramos(this.servTramoOrig);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void listarDestinosxOri(){
		try {
			this.listaDestino = new ArrayList<Destino>();
			this.listaDestino = destinoServices.obtenerDestino(this.programacionFilter.getOrigen());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void eliminarEnlace(){
		try {
			this.enlaceService.eliminarEnlace(enlace.getIdEnlace());
			FacesUtils.showFacesMessage("Enlace eliminada correctamente.",Constante.INFORMACION);
			log.setAccion("DELETE");
	        log.setDescripcion("Se elimina la Enlace: " + enlace.getIdEnlace());
	        logmb.insertarLog(log);
	        this.listaServicios = this.servicioService.findByOrigenDestinoRutaCompartida(this.programacionFilter.getOrigen(), this.programacionFilter.getDestino());
	        this.listaEnlaces = enlaceService.listaEnlaces();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.enlace =  new Enlace();
	}
	
	public void eliminarEnlaceDirecto(Integer idenlace){
		try {
			this.enlaceService.eliminarEnlace(idenlace);
			log.setAccion("DELETE");
	        log.setDescripcion("Se elimina la Enlace: " + idenlace);
	        logmb.insertarLog(log);
	        this.listaEnlaces = enlaceService.listaEnlaces();
	        this.listaEnlacesTramos = enlaceService.listarTramosServicio(this.servTramoOrig.getIdServicio());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void editarAgencia(Enlace ag)
	{
		this.editar = Boolean.TRUE;
		this.enlace = ag;
	}
	
	public void nuevoEnlace()
	{
		this.editar = Boolean.FALSE;
		this.enlace = new Enlace();
	}
	
	public void setearTramoOriginal(Servicio s){
		this.servTramoOrig = s;
		this.enlace = new Enlace();
	}
	
	public void elimnarTramos(Servicio servicio){
		try {
			//this.enlaceService.eliminarEnlacesByServicio(servicio.getIdServicio());
			Servicio ser = this.servicioService.findById(servicio.getIdServicio());
			this.servicioService.eliminarServiciosHijos(ser.getIntCorre());
			this.servicioService.actualizarServicioPadre(ser.getIdServicio());
			log.setAccion("DELETE");
	        log.setDescripcion("Se elimina la Enlace del servicio: " + servicio.getIdServicio());
	        logmb.insertarLog(log);
	        //this.listaEnlacesTramos = new ArrayList<>();
	        this.listaServiciosCompartidos.clear();
	        buscarServicio();
	       // this.listaServicios.clear();
			//this.listaServicios = this.servicioService.findByOrigenDestinoRutaCompartida(this.programacionFilter.getOrigen(), this.programacionFilter.getDestino());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	
	public void listarDestinosxOriTramos()
	{
		this.listaAgeTramos  = new ArrayList<>();
		for(Agencia ag : this.listaAgencias)
		{
			if(this.enlace.getOrigen().compareTo(ag.getIdagencia()) != 0)
			{
				this.listaAgeTramos.add(ag);
			}
		}
		
	}
	
	
	//**set an get 
	
	

	public MenuServices getMenuServices() {
		return menuServices;
	}

	public Servicio getSelectedServicio() {
		return selectedServicio;
	}

	public void setSelectedServicio(Servicio selectedServicio) {
		this.selectedServicio = selectedServicio;
	}

	public List<Servicio> getListaTramosEnlace() {
		return listaTramosEnlace;
	}

	public void setListaTramosEnlace(List<Servicio> listaTramosEnlace) {
		this.listaTramosEnlace = listaTramosEnlace;
	}

	public List<Servicio> getListaFiltroServicio() {
		return listaFiltroServicio;
	}

	public void setListaFiltroServicio(List<Servicio> listaFiltroServicio) {
		this.listaFiltroServicio = listaFiltroServicio;
	}

	public List<Servicio> getListaServicios() {
		return listaServicios;
	}

	public void setListaServicios(List<Servicio> listaServicios) {
		this.listaServicios = listaServicios;
	}

	public Programacion getProgramacionFilter() {
		return programacionFilter;
	}

	public void setProgramacionFilter(Programacion programacionFilter) {
		this.programacionFilter = programacionFilter;
	}

	public ServicioService getServicioService() {
		return servicioService;
	}

	public void setServicioService(ServicioService servicioService) {
		this.servicioService = servicioService;
	}

	public List<Agencia> getListaAgencias() {
		return listaAgencias;
	}

	public void setListaAgencias(List<Agencia> listaAgencias) {
		this.listaAgencias = listaAgencias;
	}

	public List<Destino> getListaDestino() {
		return listaDestino;
	}

	public void setListaDestino(List<Destino> listaDestino) {
		this.listaDestino = listaDestino;
	}

	public AgenciaService getAgenciaService() {
		return agenciaService;
	}

	public void setAgenciaService(AgenciaService agenciaService) {
		this.agenciaService = agenciaService;
	}

	public DestinoServices getDestinoServices() {
		return destinoServices;
	}

	public void setDestinoServices(DestinoServices destinoServices) {
		this.destinoServices = destinoServices;
	}

	public Enlace getEnlace() {
		return enlace;
	}

	public void setEnlace(Enlace enlace) {
		this.enlace = enlace;
	}

	public List<Enlace> getListaEnlaces() {
		return listaEnlaces;
	}

	public void setListaEnlaces(List<Enlace> listaEnlaces) {
		this.listaEnlaces = listaEnlaces;
	}

	public List<Enlace> getListaFiltroEnlaces() {
		return listaFiltroEnlaces;
	}

	public void setListaFiltroEnlaces(List<Enlace> listaFiltroEnlaces) {
		this.listaFiltroEnlaces = listaFiltroEnlaces;
	}

	public EnlaceService getEnlaceService() {
		return enlaceService;
	}

	public void setEnlaceService(EnlaceService enlaceService) {
		this.enlaceService = enlaceService;
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

	public Servicio getServTramoOrig() {
		return servTramoOrig;
	}

	public void setServTramoOrig(Servicio servTramoOrig) {
		this.servTramoOrig = servTramoOrig;
	}

	public List<Agencia> getListaAgeTramos() {
		return listaAgeTramos;
	}

	public void setListaAgeTramos(List<Agencia> listaAgeTramos) {
		this.listaAgeTramos = listaAgeTramos;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public List<Enlace> getListaEnlacesTramos() {
		return listaEnlacesTramos;
	}

	public void setListaEnlacesTramos(List<Enlace> listaEnlacesTramos) {
		this.listaEnlacesTramos = listaEnlacesTramos;
	}

	public Enlace getEnlaceUno() {
		return enlaceUno;
	}

	public void setEnlaceUno(Enlace enlaceUno) {
		this.enlaceUno = enlaceUno;
	}

	public Enlace getEnlaceDos() {
		return enlaceDos;
	}

	public void setEnlaceDos(Enlace enlaceDos) {
		this.enlaceDos = enlaceDos;
	}

	public List<Servicio> getListaServiciosCompartidos() {
		return listaServiciosCompartidos;
	}

	public void setListaServiciosCompartidos(
			List<Servicio> listaServiciosCompartidos) {
		this.listaServiciosCompartidos = listaServiciosCompartidos;
	}
	
	
}

