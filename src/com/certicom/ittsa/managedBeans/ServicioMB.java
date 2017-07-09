package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.CategoriaServicio;
import com.certicom.ittsa.domain.ClaseServicio;
import com.certicom.ittsa.domain.Destino;
import com.certicom.ittsa.domain.Excepcion;
import com.certicom.ittsa.domain.Frecuencia;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.RutaServicio;
import com.certicom.ittsa.domain.Servicio;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.CategoriaServicioService;
import com.certicom.ittsa.services.ClaseServicioServices;
import com.certicom.ittsa.services.DestinoServices;
import com.certicom.ittsa.services.FrecuenciaService;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.ProgramacionService;
import com.certicom.ittsa.services.RutaServicioService;
import com.certicom.ittsa.services.ServicioService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="servicioMB")
@ViewScoped
public class ServicioMB  extends GenericBeans implements Serializable{
	private String titulo;
	private Servicio servicio;
	private List<Servicio> listaServicio;
	private List<Servicio> listaFiltroServicio;
	private List<Agencia> listaAgOrigen;
	private List<Agencia> listaAgDestinos;
 	private List<ClaseServicio> listaClaseServicio;
 	private List<RutaServicio> listaRutaServicio;
 	private List<Frecuencia> listaTipoFrecuencia;
 	private String _dialServicio;
 	
	private Boolean editar;
	private Destino destino;
	private Agencia agencia;
	private ClaseServicio claseServicio;
	private RutaServicio rutaServicio;
	
	private MenuServices menuServices;
	private AgenciaService agenciaService;
	private ServicioService servicioService;
	private ClaseServicioServices claseServicioService;
	private RutaServicioService rutaServicioService;
	private CategoriaServicioService categoriaServicioService;
	private ProgramacionService programacionService;
	private FrecuenciaService frecuenciaService;
	
	private DestinoServices destinoService;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	public ServicioMB(){}
	
	@PostConstruct
	public void inicia()
	{
		this.destino = new Destino();
		this.servicio = new Servicio();
		this.claseServicio = new ClaseServicio();
		this.rutaServicio = new  RutaServicio();
		this.listaTipoFrecuencia = new ArrayList<>();
		
		this.menuServices=new MenuServices();
		this.agenciaService = new AgenciaService();
		this.servicioService =  new ServicioService();
		this.claseServicioService = new ClaseServicioServices();
		this.rutaServicioService = new RutaServicioService();
		this.categoriaServicioService = new  CategoriaServicioService();
		this.programacionService = new ProgramacionService();
		this.frecuenciaService = new FrecuenciaService();
		this.destinoService=new DestinoServices();
		
		this.listaServicio = new ArrayList<>();
		
		log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		try {
			//int codMenu = this.menuServices.opcionMenuByPretty("pretty:servicio");
			this.listaAgOrigen = this.agenciaService.findAll();
			this.listaClaseServicio = this.claseServicioService.findAll();
			this.listaRutaServicio = this.rutaServicioService.findAll();
			this.listaTipoFrecuencia = frecuenciaService.listaFrecuenciasActivas();
			int codMenu = menuServices.opcionMenuByPretty("pretty:frecuencia");
			log.setCod_menu(codMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void nuevoServicio()
	{
		this.editar = Boolean.FALSE;
		this.servicio = new Servicio();
		this.claseServicio = new ClaseServicio();
		this.rutaServicio = new RutaServicio();
		this.destino = new Destino();
		this.titulo = "Registrar nuevo servicio";
	}
	
	public void editarServicio(Servicio serv)
	{
		this.editar = Boolean.TRUE;
		this.servicio = serv;
		this._dialServicio = serv.getHSalida().substring(6,8);
		this.titulo = "Modificar servicio";
		try {
			this.claseServicio = this.claseServicioService.findById(this.servicio.getIdClase());
			this.rutaServicio =  this.rutaServicioService.findById(this.servicio.getIdRutaDestino());
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}
	
	public void guardarServicio()
	{
		try {
		
		this.servicio.setHora24(this.servicioService.convertirHSalida24(this.servicio.getHSalida()+" "+this._dialServicio));//calculo con hroa de salida
		//this.servicio.setHLlegada(this.servicioService.sumaHoras(this.servicio.getHSalida()+" "+this._dialServicio, this.servicio.getHorasViaje())); //calculo con hor ade slaida + nro de horas
		this.servicio.setHLlegada(this.servicioService.sumaHoras(this.servicio.getHora24(), this.servicio.getHorasViaje()));
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("HH:mm:ss");
		String strFecha = this.servicio.getHSalida()+":00";
		this.servicio.setHorasViaje(this.servicio.getHorasViaje());
		this.servicio.setHSalida(this.servicio.getHSalida()+" "+this._dialServicio);
		this.servicio.setDescripcion(this.servicio.getDescripcion().trim().toUpperCase());
		this.servicio.setIdClase(this.claseServicio.getIdclase());
		this.servicio.setIdRutaDestino(this.rutaServicio.getIdRutaDestino());
		this.servicio.setOrigen(this.destino.getOrigen());
		this.servicio.setDestino(this.destino.getDestino());
		this.servicio.setRutaCompEnCaliente("NO");
		RequestContext context = RequestContext.getCurrentInstance();  
	
					if(this.editar)
					{
						try {
							 this.servicioService.actualizarServicio(this.servicio);
							 log.setAccion("UPDATE");
				             log.setDescripcion("Se actualiza el Servicio: " + servicio.getDescripcion());
				             logmb.insertarLog(log);
							FacesUtils.showFacesMessage("Servicio actualizado correctamente.",Constante.INFORMACION);
							context.update("sms");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}else{
						//validando que no este en la misma hora 
						Integer val = this.servicioService.validarServicioxHora(this.servicio);
						if(val>0){
							FacesUtils.showFacesMessage("Servicio a esta hora se encuentra ya registrado.",Constante.INFORMACION);
							context.update("sms");
						}else{
							
						this.servicio.setFRegistro(new Date());
						try {
			                this.servicio.setIntCorre(0);
			                this.servicio.setGradoServ(1);
							this.servicioService.crearServicio(this.servicio);
							log.setAccion("INSERT");
				            log.setDescripcion("Se registra el servicio: " + servicio.getDescripcion());
				            logmb.insertarLog(log);
							FacesUtils.showFacesMessage("Servicio registrado correctamente.",Constante.INFORMACION);
							context.update("sms");
						} catch (Exception e) {
							e.printStackTrace();
						}
						}
					}
					listarServicios();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Date calcularHoraLlegada(String horaSalida, String horasViaje){
		
		Integer hll = 0;
		Integer mll = 0;
		String hs = horaSalida.substring(0,1);
		String hv = horasViaje.substring(0,1);
		
		String ms = horaSalida.substring(3,4);
		String mv = horasViaje.substring(3,4);
		
		if(ms.equals("00") && mv.equals("00")){
			Integer hsal = Integer.parseInt(hs);
			Integer hviaj = Integer.parseInt(hv);
			
			Integer sum = hsal + hviaj;
			if(sum>24){
				hll = sum -24;
			} else hll = sum; 
			
		}else{
			
		}
		
		
		
		
		
		Date horallegada = new Date();
		
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("HH:mm:ss");
		String strFecha = "0"+hll+":0"+mll+":00";
		System.out.println("la hora a enviar es " + strFecha);
		
		 try {
			horallegada = formatoDelTexto.parse(strFecha);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return horallegada;
	}
	
	
	public void eliminarServicio()
	{
		try {
			RequestContext context = RequestContext.getCurrentInstance();
			int count = this.programacionService.programacionxServicio(this.servicio.getIdServicio());
			if(count == 0){
				this.servicioService.eliminarServicio(this.servicio.getIdServicio());
				log.setAccion("DELETE");
	            log.setDescripcion("Se elimina el servicio: " + servicio.getDescripcion());
	            logmb.insertarLog(log);
				this.listaServicio.remove(this.servicio);
				FacesUtils.showFacesMessage("Servicio eliminado correctamente.",Constante.INFORMACION);	
			}else{
				FacesUtils.showFacesMessage("El servicio cuenta con asientos vendidos y/o reservados.",Constante.ERROR);	
				context.execute("dlgOmiso.show()");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void listarServicios()
	{
		try {
			this.listaServicio.clear();
			this.listaServicio = this.servicioService.findByOrigenDestino(this.destino.getOrigen(),this.destino.getDestino());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void listarAgeDestino(){
		
		this.listaAgDestinos = new ArrayList<Agencia>();
		try {
			this.listaAgDestinos=this.agenciaService.listarAsociados(this.destino.getOrigen());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		
//		for(Agencia ag : this.listaAgOrigen)
//		{
//			if(this.destino.getOrigen()!=ag.getIdagencia() )
//			{
//				this.listaAgDestinos.add(ag);
//			}
//		}

	}
	
	public void setearNombreCorto()
	{
		try {
			
			this.claseServicio =  this.claseServicioService.findById(this.claseServicio.getIdclase());
			this.servicio.setDescripcion(this.claseServicio.getNombreCorto());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	

	/*############################-------setter y getter---------##############################3*/
	
	public Servicio getServicio() {
		return servicio;
	}

	public List<Frecuencia> getListaTipoFrecuencia() {
		return listaTipoFrecuencia;
	}

	public void setListaTipoFrecuencia(List<Frecuencia> listaTipoFrecuencia) {
		this.listaTipoFrecuencia = listaTipoFrecuencia;
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

	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}

	public List<Servicio> getListaServicio() {
		return listaServicio;
	}

	public void setListaServicio(List<Servicio> listaServicio) {
		this.listaServicio = listaServicio;
	}

	public Boolean getEditar() {
		return editar;
	}

	public void setEditar(Boolean editar) {
		this.editar = editar;
	}

	public List<Servicio> getListaFiltroServicio() {
		return listaFiltroServicio;
	}

	public void setListaFiltroServicio(List<Servicio> listaFiltroServicio) {
		this.listaFiltroServicio = listaFiltroServicio;
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

	public Destino getDestino() {
		return destino;
	}

	public void setDestino(Destino destino) {
		this.destino = destino;
	}

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	public List<ClaseServicio> getListaClaseServicio() {
		return listaClaseServicio;
	}

	public void setListaClaseServicio(List<ClaseServicio> listaClaseServicio) {
		this.listaClaseServicio = listaClaseServicio;
	}

	public ClaseServicio getClaseServicio() {
		return claseServicio;
	}

	public void setClaseServicio(ClaseServicio claseServicio) {
		this.claseServicio = claseServicio;
	}

	public List<RutaServicio> getListaRutaServicio() {
		return listaRutaServicio;
	}

	public void setListaRutaServicio(List<RutaServicio> listaRutaServicio) {
		this.listaRutaServicio = listaRutaServicio;
	}

	public RutaServicio getRutaServicio() {
		return rutaServicio;
	}

	public void setRutaServicio(RutaServicio rutaServicio) {
		this.rutaServicio = rutaServicio;
	}

	public String get_dialServicio() {
		return _dialServicio;
	}

	public void set_dialServicio(String _dialServicio) {
		this._dialServicio = _dialServicio;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
}
