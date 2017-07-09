package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.primefaces.context.RequestContext;

import pe.gob.mtc.wshr.Errores;
import pe.gob.mtc.wshr.ResultVehiculo;
import pe.gob.mtc.wshr.Seguridad;
import pe.gob.mtc.wshr.Vehiculo;
import pe.gob.mtc.wshr.WSServiciosHR;
import pe.gob.mtc.wshr.WSServiciosHRLocator;
import pe.gob.mtc.wshr.WSServiciosHRSoap;
import pe.gob.mtc.wshr.WSServiciosHRSoapStub;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Asiento;
import com.certicom.ittsa.domain.AsientoVenta;
import com.certicom.ittsa.domain.Asignacion;
import com.certicom.ittsa.domain.CategoriaServicio;
import com.certicom.ittsa.domain.ClaseServicio;
import com.certicom.ittsa.domain.Destino;
import com.certicom.ittsa.domain.Flota;
import com.certicom.ittsa.domain.FlotaAutomotor;
import com.certicom.ittsa.domain.FlotaAutoparte;
import com.certicom.ittsa.domain.Historial_AsigPCT;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Piloto;
import com.certicom.ittsa.domain.Programacion;
import com.certicom.ittsa.domain.Servicio;
import com.certicom.ittsa.domain.TipoAsiento;
import com.certicom.ittsa.domain.Tripulacion;
import com.certicom.ittsa.domain.Usuario;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.AsientoServices;
import com.certicom.ittsa.services.AsientoVentaServices;
import com.certicom.ittsa.services.AsignacionService;
import com.certicom.ittsa.services.CategoriaServicioService;
import com.certicom.ittsa.services.ClaseServicioServices;
import com.certicom.ittsa.services.DestinoServices;
import com.certicom.ittsa.services.EnlaceService;
import com.certicom.ittsa.services.FlotaAutomotorService;
import com.certicom.ittsa.services.FlotaAutoparteService;
import com.certicom.ittsa.services.FlotaService;
import com.certicom.ittsa.services.Historial_AsigPCTServices;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.ParametroServices;
import com.certicom.ittsa.services.PilotoService;
import com.certicom.ittsa.services.ProgramacionService;
import com.certicom.ittsa.services.ServicioService;
import com.certicom.ittsa.services.TipoAsientoService;
import com.certicom.ittsa.services.TripulacionServices;
import com.certicom.ittsa.services.UsuarioServices;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name = "progFlotaMB")
@ViewScoped
public class ProgramacionFlotaMB extends GenericBeans implements Serializable {

	
	@ManagedProperty(value="#{loginMB.usuario}")
	private Usuario usuarioLogin;
	public void setUsuarioLogin(Usuario usuarioLogin) {
		this.usuarioLogin = usuarioLogin;
	}



	private Programacion programacion;
	private Servicio servicioPaint;
	//private 
	private List<Agencia> listaAgencias;
	private List<Destino> listaDestino;
	private List<CategoriaServicio> listaCatServicio;
	private List<Programacion> listaProgramacion;
	private List<Programacion> listaProgramacionFilter;
	private List<Programacion> listaProgramacionAsig;
	private List<Programacion> listaProgramacionAsigFilter;
	private List<Flota> listaBuses;
	private List<Flota> listaBusesFilter;
	private List<Piloto> listaPilotos;
	private List<Piloto> listaCopilotos;
	private List<ClaseServicio> listaServicios;
	private Integer COD = null;
	private Integer idClaseBus;
	private String hora_Tramo2;
	private String minutos_Tramo2;
	private String am_pm_Tramo2;
	private List<Flota> listaFlotaAsociada;
	private Flota flotaCapacidad;

	private Date fechaProgramacion;
	private Integer idOrigen;
	private Integer idDestino;
	private Integer idCategoriaServ;
	private Flota busSelected;
	private boolean editarBus;
	private boolean editarPiloto;
	private boolean editarCopiloto;
	private Programacion programacionFilter;
	private Programacion programacionRuta;
	private Servicio servicioRuta;
	private Servicio servicioPadre;
	private List<FlotaAutomotor> listaAutomotorxBusKilometraje;
	private List<Agencia> listaAgeTramos;
	private boolean _paseTramo;
	private boolean _busAsignado;
	private String mensajeError = "";
	private Integer asi_vendidos;
	private Integer asi_reservados;
	
	// services
	private ProgramacionService programacionService;
	private CategoriaServicioService categoriaServServices;
	private AgenciaService agenciaService;
	private DestinoServices destinoServices;
	private MenuServices menuServices;
	private FlotaService busServices;
	private PilotoService pilotoServices;
	private TripulacionServices tripulacionServices;
	private UsuarioServices usuarioServices;
	private Historial_AsigPCTServices historial_asigPCTServices;
	private ServicioService servicioService;
	private ClaseServicioServices clasServicioServices;
	private FlotaService flotaService;
	private FlotaAutomotorService flotaAutomotorService;
	private FlotaAutoparteService flotaAutoparteService;
	private EnlaceService enlaceService;
	private AsientoVentaServices asientoVentaServices;
	private ParametroServices parametroServices;
	private AsientoVentaServices asientoVentaService;
	
	private Programacion programacionSeleccionada;
	private Servicio servicioSeleccionado;
	private ClaseServicio claseServicioOriginal;
	private ClaseServicio claseServicioNuevo;
	private Integer pisoDestino;
	private List<AsientoVenta> listaAsientoVenta;
	
	private Log log;
	private LogMB logmb;
	
	private String tipoAsignacion;
	private Boolean asignarPilotoCopiloto;
	private Boolean conversionRutaCompartida;
	private Boolean asignacionBus;
	 
	
	List<Servicio> listaServiciosCompartidos; 
	
	private AsignacionService asignacionService;

	private Boolean listarHijos;
	private Programacion programacionAsignacion;
	private Asignacion asignacion;

	public ProgramacionFlotaMB() {
	}

	@PostConstruct
	public void inicia() {
		
		this.listaServicios = new ArrayList<>();
		this.listaFlotaAsociada = new ArrayList<>();
		this.programacionFilter = new Programacion();
		this.agenciaService = new AgenciaService();
		this.destinoServices = new DestinoServices();
		this.programacionService = new ProgramacionService();
		this.menuServices = new MenuServices();
		this.busServices = new FlotaService();
		this.pilotoServices = new PilotoService();
		this.tripulacionServices = new TripulacionServices();
		this.usuarioServices = new UsuarioServices();
		this.historial_asigPCTServices = new Historial_AsigPCTServices();
		this.categoriaServServices = new CategoriaServicioService();
		this.servicioService = new ServicioService();
		this.programacion = new Programacion();
		this.servicioPaint = new Servicio();
		this.clasServicioServices = new ClaseServicioServices();
		this.flotaService = new FlotaService();
		this.flotaAutomotorService = new FlotaAutomotorService();
		this.flotaAutoparteService = new FlotaAutoparteService();
		this.programacionRuta = new Programacion();
		this.servicioRuta = new Servicio(); 
		this.enlaceService = new EnlaceService();
		this.asientoVentaServices = new AsientoVentaServices();
		this.parametroServices = new ParametroServices();
		this._paseTramo = false;
		this.asientoVentaService =  new AsientoVentaServices();
		this.conversionRutaCompartida = Boolean.FALSE;
		this.asignacionBus = Boolean.FALSE;
		this.listaServiciosCompartidos =  new ArrayList<>();
		this.asignacionService =  new AsignacionService();
		
		this.asignacion = new Asignacion();
		/* INICIO VARIABLES CROQUIS **/
		this._busAsignado = Boolean.FALSE;
		this.visibleBus = Boolean.FALSE;
		this.manifiesto = Boolean.FALSE;
		this.programacionSelec = new Programacion();
		this.tipoAsientop1 = new TipoAsiento();
		this.tipoAsientop2 = new TipoAsiento();
		
		this.claseServicioServices = new ClaseServicioServices();
		this.tipoAsientoService = new TipoAsientoService();
		this.asientoService = new AsientoServices();
		
		this.asignarPilotoCopiloto = Boolean.FALSE;
		
		/* */
		this.flotaCapacidad = new Flota();
		this.fechaProgramacion = new Date();
		this.listarHijos = Boolean.FALSE;

		
		log = (Log) getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		// listando
		try {
			this.listaAgencias = agenciaService.listaAgenciasActivas();
			this.listaServicios = this.clasServicioServices.listaCServiciosActivos();
			listarCategoriasServ();
			int codMenu = menuServices.opcionMenuByPretty("pretty:progFlota");
			log.setCod_menu(codMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void listarDestinos() {
		try {
			this.listaDestino = destinoServices.obtenerDestino(getIdOrigen());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void listarCategoriasServ() {
		try {
			this.listaCatServicio = new ArrayList<>();
			this.listaCatServicio = categoriaServServices.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void buscarProgramacion()
	{
		try {
			this.listaProgramacion = this.programacionService.findProgByFecha_orig_dest(getFechaProgramacion(), getIdOrigen(), getIdDestino(),getIdCategoriaServ());
			Servicio serv  = null;
			ClaseServicio clase = null;
			
			for(Programacion p : listaProgramacion)
			{
				if(p.getIntCorreEnlace() != 0)
				{
					List<Servicio> lista = this.servicioService.listaHijosServiciosCompartidos(p.getIntCorreEnlace());
					if(!lista.isEmpty())
					{
						for(Servicio ser : lista)
						{
							if(ser.getRutaCompEnCaliente().equals("SI"))
							{
								p.setDesRutaCompartida("NO-Comp");
								break;
							}
						}
					}
					
					
				}
				
				
				serv = this.servicioService.findById(p.getIdServicio());
				clase = this.claseServicioServices.findById(serv.getIdClase());
				p.setClase(clase);
				
				Tripulacion t = tripulacionServices.findByIdProgramacion(p.getIdProgramacion());
				if(t!=null){
					if(t.getIdBus()!=null){
						Flota f = busServices.findById(p.getIdBus());
						p.setFlota(f);
						p.setAsignarBus(Boolean.TRUE);
					}
					if(t.getIdPiloto()!=null){
						Piloto pil = pilotoServices.findById(t.getIdPiloto());
						p.setPiloto(pil); 
						p.setAsignarPiloto(Boolean.TRUE);
					}
					if(t.getIdCopiloto()!=null){
						Piloto cop = pilotoServices.findById(t.getIdCopiloto());
						p.setCopiloto(cop); 
						p.setAsignarCopiloto(Boolean.TRUE); 
					}
					p.setGuardarProgFlota(Boolean.FALSE);
				}else{
					p.setGuardarProgFlota(Boolean.TRUE);
				}
				
								
				
			}
				
			this.listaProgramacionAsig = this.programacionService.findProgByFecha_orig_dest_Asig(getFechaProgramacion(), getIdOrigen(), getIdDestino(),getIdCategoriaServ());
			
			
				for(Programacion p2 : this.listaProgramacionAsig)
				{
					System.out.println("programacion seleccionada xx " + p2.getIdProgramacion());
					
					serv = this.servicioService.findById(p2.getIdServicio());
					clase = this.claseServicioServices.findById(serv.getIdClase());
					p2.setClase(clase);
					
					Tripulacion t2 = this.tripulacionServices.findByIdProgramacion(p2.getIdProgramacion());
					if(t2!=null){
						if(t2.getIdBus()!=null){
							Flota f = busServices.findById(p2.getIdBus());
							p2.setFlota(f);
							//p.setAsignarBus(Boolean.TRUE);
						}
						if(t2.getIdPiloto()!=null){
							Piloto pil = pilotoServices.findById(t2.getIdPiloto());
							p2.setPiloto(pil); 
							//p.setAsignarPiloto(Boolean.TRUE);
						}
						if(t2.getIdCopiloto()!=null){
							Piloto cop = pilotoServices.findById(t2.getIdCopiloto());
							p2.setCopiloto(cop); 
							//p.setAsignarCopiloto(Boolean.TRUE); 
						}
					}
					
					//buscando asignaciones:
					
					Asignacion asig = this.asignacionService.findByProgramacion(p2.getIdProgramacion());
					
					if(asig != null)
					{
						if(asig.getMonto() != null)
						{
							p2.setAsignacionViatico(asig.getMonto());
						}
						
					}else{
						p2.setAsignacionViatico(new BigDecimal(0));
					}

					
			   }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void buscarBus(Programacion p)
	{
		try {
			//el opcional para el filtro 
			this.programacionFilter = new Programacion();
			this.programacionFilter = p;
			//fin
			this.listaBuses = new ArrayList<>();
			this.programacion = p;
			this.listaBuses = busServices.findByIdClase(p.getIdClase());
			for(Flota f : listaBuses){
				Piloto pil = pilotoServices.findById(f.getPiloto());
				f.setNombPiloto(pil.getApellidoPaterno() +" "+pil.getApellidoMaterno()+" "+pil.getNombres());
				Piloto cop = pilotoServices.findById(f.getCopiloto());
				f.setNombCopiloto(cop.getApellidoPaterno() +" "+cop.getApellidoMaterno()+" "+cop.getNombres()); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.asignarPilotoCopiloto = Boolean.FALSE;
	}
	
	public void obtenerDatosRutaCompartida(Programacion prog)
	{
		
		try {
			
			this.programacionRuta = prog;
			//Obteniendo el servicio padre
			//this.servicioPadre = this.servicioService.findById(prog.getIdServicio());
			this.listaAgeTramos = this.agenciaService.listaAgenciasActivas();
			this.buscarTramos();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	public void buscarTramos()
	{
		this.listarHijos = Boolean.FALSE;
		try {
			this.listaServiciosCompartidos .clear();
			this.servicioPadre = this.servicioService.findById(programacionRuta.getIdServicio());
			
			//this.servicioPadre = this.servicioService.findById(this.servicioPadre.getIdServicio());
			
			this.listaServiciosCompartidos = this.servicioService.listaHijosServiciosCompartidos(this.servicioPadre.getIntCorre().intValue() == 0?null:this.servicioPadre.getIntCorre());
			
			if(this.listaServiciosCompartidos.isEmpty())
			{
				Agencia ag = this.agenciaService.findById(this.servicioPadre.getOrigen());
				this.servicioPadre.setDesOrigen(ag.getDescripcion());
				ag = this.agenciaService.findById(this.servicioPadre.getDestino());
				this.servicioPadre.setDesDestino(ag.getDescripcion());
				this.listaServiciosCompartidos.add(this.servicioPadre);
			}else{
				this.conversionRutaCompartida = Boolean.TRUE;
				this.listarHijos = Boolean.TRUE;
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public void convertirRutaCompartida()
	{
		try {
		
		int correlativo = ((this.enlaceService.obtenerCorrelativo())+ 1);
		String horaEnlace = this.hora_Tramo2+":"+this.minutos_Tramo2+" "+this.am_pm_Tramo2;
		//int idServicio = this.servicioService.obtenerIdUltimoRegistro();
		//actualizando el servicio padre
		this.servicioPadre.setRutaCompartida("NO");
		
		if(this.servicioPadre.getTipoFrecuencia().equals("1"))
		{
			this.servicioPadre.setTipoFrecuencia("1");//frecuente
		}else{
			this.servicioPadre.setTipoFrecuencia("2");//demanda
		}
		
		this.servicioPadre.setHSalida(this.programacionRuta.gethSalida());
		this.servicioPadre.setGradoServ(1);
		this.servicioPadre.setIntCorre(correlativo);
		this.servicioService.actualizarServicio(this.servicioPadre);
		
		this.programacionRuta.setIntCorreEnlace(correlativo);
		this.programacionService.actualizarProgramacionCorrelativo(correlativo, this.programacionRuta.getIdProgramacion());
		
		
		//this.servicioService.actualizarServicioCorrelativo(this.servicioPadre);
		this.enlaceService.actualizarCorrelativo(1, correlativo);//actualizar tablacorrelativo
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.conversionRutaCompartida = Boolean.TRUE;
		this.programacionRuta.setDesRutaCompartida("NO-Comp");
		
		FacesUtils.showFacesMessage("Se convirtió la ruta correctamente.", 3);
	}
	
	
	
	public void generarProgRuta(){
		try {
			int correlativo = this.servicioPadre.getIntCorre();
			int correlativoHijo = correlativo;
			Servicio servicioHijo = this.servicioRuta;
			//registrando el servicio hijo
			String horaEnlace = this.hora_Tramo2+":"+this.minutos_Tramo2+" "+this.am_pm_Tramo2;
			servicioHijo.setIntCorre(correlativo);
			servicioHijo.setRutaCompartida("SI");
			servicioHijo.setDescripcion(this.servicioPadre.getDescripcion());
			servicioHijo.setIdClase(this.servicioPadre.getIdClase());
			
			this.servicioRuta.setHora24(this.servicioService.convertirHSalida24(horaEnlace));

			
			if(this.servicioPadre.getTipoFrecuencia().equals("1"))
			{
				servicioHijo.setTipoFrecuencia("1");
			}else{
				servicioHijo.setTipoFrecuencia("2");
			}
			servicioHijo.setFRegistro(new Date());
			servicioHijo.setFrecuenteSabado(0);
			servicioHijo.setEstado(Boolean.TRUE);
			servicioHijo.setGradoServ(2);
			servicioHijo.setHSalida(horaEnlace);
			servicioHijo.setIdRutaDestino(servicioPadre.getIdRutaDestino());
			servicioHijo.setHLlegada(this.servicioService.sumaHoras(this.servicioRuta.getHora24(), this.servicioRuta.getHorasViaje())); //calculo con hor ade slaida + nro de horas
			servicioHijo.setHora24(this.servicioService.convertirHSalida24(this.servicioRuta.getHSalida()));//calculo con hroa de salida
			servicioHijo.setRutaCompEnCaliente("SI");
			this.servicioService.crearServicio(servicioHijo);
			int idServicio = this.servicioService.obtenerIdUltimoRegistro();
			
			//generando la programacion
			Programacion pro = this.programacion;
			pro.setIdServicio(idServicio);
			
			//pro.setPrecioPiso1(this.servicioRuta.getPrecio1());
			//pro.setPrecioPiso2(this.servicioRuta.getPrecio2());
			//pro.sethSalida(this.servicioRuta.getHSalida());
			
			pro.setPrecioPiso1(servicioHijo.getPrecio1());
			pro.setPrecioPiso2(servicioHijo.getPrecio2());
			pro.sethSalida(servicioHijo.getHSalida());
			pro.setIntCorreEnlace(correlativoHijo);
			
			if (getAm_pm_Tramo2().equals("PM")) 
			{
				Integer h = Integer.parseInt(getHora_Tramo2()) + 12;
				this.hora_Tramo2 = h.toString();
			}
			SimpleDateFormat formatoDelTextoOne = new SimpleDateFormat("HH:mm:ss");
			String strFechaOne = getHora_Tramo2() + ":" + getMinutos_Tramo2() + ":00";
			
			pro.setHora24(formatoDelTextoOne.parse(strFechaOne));
			pro.setFechaRegistro(new Date());
			pro.setFcreacion(new Date());
			pro.setIdBus(0);
			pro.setfLiquidacion(this.fechaProgramacion);
			pro.setfSalida(this.fechaProgramacion);
			pro.setEstado("3");//generado por el job
			
			this.programacionService.crearProgramacion(pro);
			this.servicioRuta = new Servicio();
			this._paseTramo=true;
			this.buscarTramos();
			
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("dlgRutaCompart.hide()");
			
			//actualizar los asientos vendidos de los padres
            //los obteniendo la programacion del servicio padre
			Programacion program = this.programacionService.programacionRutaCompartida(this.fechaProgramacion,this.servicioPadre.getIdServicio(),this.servicioPadre.getIntCorre());
			System.out.println("la programacion padre es " + program.getIdProgramacion());
			List<AsientoVenta> listAsientoVentaPadre = this.asientoVentaServices.verificandoVentaAsiento(program.getIdProgramacion());
			System.out.println("la listAsientoVentaPadre es " + listAsientoVentaPadre.size());
			
			//replicando en las programaciones hijos
			Programacion programHijo = this.programacionService.programacionRutaCompartida(this.fechaProgramacion,idServicio,this.servicioPadre.getIntCorre());
			System.out.println("la programacion programHijo es " + programHijo.getIdProgramacion());
			List<AsientoVenta> listAsientoVentaHijo = this.asientoVentaServices.listarManifiestoxProg(programHijo.getIdProgramacion());
			System.out.println("la listAsientoVentaHijo es " + listAsientoVentaHijo.size());
			
			for(int i=0; i<listAsientoVentaPadre.size(); i++){
				AsientoVenta asientVentaP =  listAsientoVentaPadre.get(i);
				for(int j=0; j<listAsientoVentaHijo.size();j++){
					AsientoVenta asientVentaH =  listAsientoVentaHijo.get(j);
					System.out.println(" COMPARACION " + asientVentaP.getIdAsiento() +" == "+ asientVentaH.getIdAsiento());
					if(asientVentaP.getIdAsiento().intValue() == asientVentaH.getIdAsiento().intValue()){
						System.out.println("ENTRO ");
						int codHijo = asientVentaH.getIdasientoventa();
						int codPrograHijo = asientVentaH.getIdProgramacion(); 
						asientVentaH = asientVentaP;
						asientVentaH.setIdasientoventa(codHijo);
						asientVentaH.setIdProgramacion(codPrograHijo);
						this.asientoVentaServices.actualizarVentaAsiento(asientVentaH);
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void quitarRutaCompartida()
	{
		System.out.println("Quitando ruta compartidas");
		try {

			this.servicioPadre = this.servicioService.findById(this.programacionRuta.getIdServicio());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	public void confirmQuitarRutaCompartida()
	{
		System.out.println("confrmado Quitando ruta compartidas");
		//1 :quitando los hijos
		//2: actualizando el padre
		
		Integer correlativo = this.servicioPadre.getIntCorre();
		
		try {
			List<Servicio> lista = this.servicioService.listaHijosServiciosCompartidos(correlativo);
			for(Servicio ser : lista)
			{
				if(ser.getIdServicio().intValue() != this.servicioPadre.getIdServicio().intValue())
				{
					//eimnando
					this.servicioService.eliminarServicio(ser.getIdServicio());
				}else{
					this.servicioPadre.setRutaCompartida("NO");
					this.servicioPadre.setIntCorre(0);
					this.servicioPadre.setGradoServ(0);
					this.servicioService.actualizarServicio(this.servicioPadre);
				}
			}
			
			this.programacionRuta.setDesRutaCompartida(this.servicioPadre.getRutaCompartida());
			
			this.buscarTramos();
			
			this.cerrarVentanaRutaCompartidaCaliente();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	

	
	
	
	public void buscarBusClase(){
		try {
			this.listaBuses = new ArrayList<>();
			this.listaBuses = busServices.findByIdClase(this.idClaseBus);

			for(Flota f : listaBuses){
				Piloto pil = pilotoServices.findById(f.getPiloto());
				f.setNombPiloto(pil.getApellidoPaterno() +" "+pil.getApellidoMaterno()+" "+pil.getNombres());
				Piloto cop = pilotoServices.findById(f.getCopiloto());
				f.setNombCopiloto(cop.getApellidoPaterno() +" "+cop.getApellidoMaterno()+" "+cop.getNombres()); 
			}
			this.programacionFilter = null;
			this.idClaseBus = null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setearBus(Programacion p){
		this.programacion = p;
		this.programacionFilter = p;
		if(p.getUsuarioRegistro()!=null){
			Usuario u = this.usuarioServices.getUsuario_byIdUsuario(p.getUsuarioRegistro());
			this.programacion.setNombUsuarioRegistro(u.getApellido_paterno()+" "+u.getApellido_materno()+" "+u.getNombre()); 
		}
		
		
	}
	
	public void asignarBus(Flota f)
	{
		this.asignarPilotoCopiloto = Boolean.TRUE;
		this.asignacionBus = Boolean.FALSE;
		RequestContext context = RequestContext.getCurrentInstance();  
		//verificamos que tenga autoparte
		
		try {
		       List<FlotaAutomotor> listaFA = this.flotaAutomotorService.listaAutomotorxFlota(f.getIdBus());
		if(listaFA.size()>0){
			boolean _paseParametro = false;
			String valor = this.parametroServices.findParametro_byNombre("VALIDACION_WEB_SERVICE");
			System.out.println(" el valor del parametro es  " + valor);
			if(valor.equals("1")){
				//VALIDACION DE FLOTA POR MTC
				WSServiciosHR w = new WSServiciosHRLocator();
				WSServiciosHRSoap soap = new WSServiciosHRSoapStub(new URL(w.getWSServiciosHRSoapAddress()),w);
				Seguridad seguridad = new Seguridad();
				seguridad.setUsuario(Constante.WEBSERVICE_USUARIO);
				seguridad.setClave(Constante.WEBSERVICE_CLAVE);
				seguridad.setPartida(Constante.WEBSERVICE_PARTIDA);
				seguridad.setRuc(Constante.WEBSERVICE_RUC);
				Vehiculo oVehiculo = new Vehiculo();
				String placaMTC = f.getPlaca().replace("-", "");
				oVehiculo.setNroPlaca(placaMTC);
				oVehiculo.setSeguridad(seguridad);
				
				ResultVehiculo resVehiculo = soap.getVehiculo(oVehiculo);
				
				if(resVehiculo.is_return()){
					_paseParametro = true;
				}else{
					_paseParametro = false;
					this.mensajeError = "";
					for (int i = 0; i < resVehiculo.getErrores().length; i++) {
						Errores err = resVehiculo.getErrores()[i];
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, err.getCode() +" - "+ err.getInfo(),""));
					}
				}
			}else{
				_paseParametro = true;
			}
			
			
			
			
			if(_paseParametro){
				if(editarBus){
					Tripulacion t;
					//retiramos los kilometros asignados al bus
					Destino destino = this.destinoServices.obtenerRecorridoAproximado(programacion.getOrigen(), programacion.getDestino());
					this.flotaService.retirarKilometrajeAproximado(this.programacion.getFlota().getIdBus(), destino.getKmDistancia());

					//retiramos los kilometros a sus partes
					FlotaAutoparte flotaAutopa = new FlotaAutoparte();
					flotaAutopa.setIdBus(this.programacion.getFlota().getIdBus());
					flotaAutopa.setFecKmIncrementoAprox(new Date());
					flotaAutopa.setKmAdherir(destino.getKmDistancia());
				    this.flotaAutoparteService.retirarincrementarKmAproximado(flotaAutopa);
					
				    //aumentar el kilometraje aproximado al bus nuevo
					Destino destino2 = this.destinoServices.obtenerRecorridoAproximado(this.programacion.getOrigen(), this.programacion.getDestino());
					this.flotaService.aumentarKilometrajeAproximado(f.getIdBus(), destino2.getKmDistancia());
					
					//actualizamos la cantidad de km recorridos a las partes del bus nuevo
					FlotaAutoparte floPartex = new FlotaAutoparte();
					floPartex.setIdBus(f.getIdBus());
					floPartex.setKmAdherir(destino2.getKmDistancia());
					floPartex.setFecKmIncrementoAprox(new Date());
					this.flotaAutoparteService.incrementarKmAproximado(floPartex);
					
					t = tripulacionServices.findByIdProgramacion(this.programacion.getIdProgramacion());
					this.programacionService.actualizarBusProgramacionAsig(f.getIdBus(), programacion.getIdProgramacion());
					this.tripulacionServices.actualizarIdBus(t.getIdTripulacion(), f.getIdBus());
					
					//verificando si es compartida 
					if(this.programacion.getDesRutaCompartida().equals("SI")){
				    	//TRAER SERVICIOS Hijos
						List<Servicio> listaServHijo = this.servicioService.obtenerServiciosHijos(this.programacion.getIdServicio());
						for(int i=0; i<listaServHijo.size();i++){
							Servicio sv = listaServHijo.get(i);
							sv.setfSalidaProgramacion(this.programacion.getfSalida());
							List<Programacion> listaProgramacionesHijos = this.programacionService.obtenerProgramacionesHijos(sv);
							for(int a=0; a<listaProgramacionesHijos.size();a++){
								Programacion pro = listaProgramacionesHijos.get(a);
								//eliminar tripulaciones
							    Tripulacion tx = new Tripulacion();
							    tx = tripulacionServices.findByIdProgramacion(pro.getIdProgramacion());
								this.programacionService.actualizarBusProgramacionAsig(f.getIdBus(), pro.getIdProgramacion());
								this.tripulacionServices.actualizarIdBus(tx.getIdTripulacion(), f.getIdBus());
							}
						}
				    }else if(this.programacion.getIntCorreEnlace()>0){
				    	//TRAER SERVICIOS Hijos
						List<Servicio> listaServHijo = this.servicioService.obtenerServiciosHijos(this.programacion.getIdServicio());
						for(int i=0; i<listaServHijo.size();i++){
							Servicio sv = listaServHijo.get(i);
							sv.setfSalidaProgramacion(this.programacion.getfSalida());
							List<Programacion> listaProgramacionesHijos = this.programacionService.obtenerProgramacionesHijos(sv);
							for(int a=0; a<listaProgramacionesHijos.size();a++){
								Programacion pro = listaProgramacionesHijos.get(a);
								//eliminar tripulaciones
							    Tripulacion tx = new Tripulacion();
							    tx = tripulacionServices.findByIdProgramacion(pro.getIdProgramacion());
								this.programacionService.actualizarBusProgramacionAsig(f.getIdBus(), pro.getIdProgramacion());
								this.tripulacionServices.actualizarIdBus(tx.getIdTripulacion(), f.getIdBus());
							}
						}
					}
					
					for(Programacion p : listaProgramacionAsig){
						if(p.getIdProgramacion()==programacion.getIdProgramacion()){
							Piloto pil  = pilotoServices.findById(f.getPiloto());
							f.setNombPiloto(pil.getApellidoPaterno()+" "+pil.getApellidoMaterno()+" "+pil.getNombres()); 
							Piloto cop = pilotoServices.findById(f.getCopiloto());
							f.setNombCopiloto(cop.getApellidoPaterno()+" "+cop.getApellidoMaterno()+" "+cop.getNombres());
							p.setFlota(f);
						}
					}
		
			}else{
				for(Programacion p : listaProgramacion){
					if(p.getIdProgramacion()==programacion.getIdProgramacion()){
						try {
							Piloto pil  = pilotoServices.findById(f.getPiloto());
							f.setNombPiloto(pil.getApellidoPaterno()+" "+pil.getApellidoMaterno()+" "+pil.getNombres()); 
							Piloto cop = pilotoServices.findById(f.getCopiloto());
							f.setNombCopiloto(cop.getApellidoPaterno()+" "+cop.getApellidoMaterno()+" "+cop.getNombres()); 
							p.setFlota(f);
							p.setGuardarProgFlota(Boolean.FALSE);
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				}
				
				this._busAsignado = Boolean.TRUE;
			}
				/*
			context.update("@form");
			context.update("dlgDetBus");
			context.update("formLista");
			context.update("formLista2");
			context.execute("dlgBus.hide()");
			*/
			}
			//fin
			this.asignacionBus = Boolean.TRUE;
		}else{
			this.asignacionBus = Boolean.FALSE;
			FacesUtils.showFacesMessage("El bus no cuenta con sistemas automotor para ser asignado.",Constante.INFORMACION);
			context.update("msgSelectFlota");
		}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	
	public void cerrarVentanaRutaCompartidaCaliente()
	{
		this.conversionRutaCompartida = Boolean.FALSE;
		this.listarHijos = Boolean.FALSE;
	} 
	
	
	public void buscarPiloto(Programacion p){
		try {
			this.programacion = p;
			this.listaPilotos = pilotoServices.findByEstado();
			for(int i=0; i<listaPilotos.size();i++){
				Piloto pil = listaPilotos.get(i);
				if(p.getCopiloto()!=null){
					if(p.getCopiloto().getIdPiloto()==pil.getIdPiloto()){
						listaPilotos.remove(i);
					}
				}			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//28-02-2014
	public void asignarPiloto(Piloto pil){
		try {
			
			Tripulacion t = this.tripulacionServices.findByIdProgramacion(this.programacion.getIdProgramacion());
			
			if(!pil.isFlagTemporal()) //actualizar de manera definitiva
			{
				this.busServices.actualizarPiloto(pil.getIdPiloto(),t.getIdBus());
			}
			
			//persistir en t_tripulacionasis ea definito o temporal
			this.tripulacionServices.actualizarIdPiloto(t.getIdTripulacion(), pil.getIdPiloto(), pil.isFlagTemporal());
			Historial_AsigPCT historial = new Historial_AsigPCT();
			historial.setIdBus(t.getIdBus());
			historial.setFecha(new Date());
			historial.setEstado("0"); 
			historial.setIdPiloto(pil.getIdPiloto()); 
			this.historial_asigPCTServices.actualizarEstadoHistorial_Piloto(historial);
			historial.setEstado("1"); 
			this.historial_asigPCTServices.crearHistorial_Piloto(historial);
			
			//actulizando si es compartida o no 
			if(this.programacion.getDesRutaCompartida().equals("SI")){
		    	//TRAER SERVICIOS Hijos
				List<Servicio> listaServHijo = this.servicioService.obtenerServiciosHijos(this.programacion.getIdServicio());
				for(int i=0; i<listaServHijo.size();i++){
					Servicio sv = listaServHijo.get(i);
					sv.setfSalidaProgramacion(this.programacion.getfSalida());
					List<Programacion> listaProgramacionesHijos = this.programacionService.obtenerProgramacionesHijos(sv);
					for(int a=0; a<listaProgramacionesHijos.size();a++){
						Programacion pro = listaProgramacionesHijos.get(a);
						//eliminar tripulaciones
					    Tripulacion tx = new Tripulacion();
					    tx = tripulacionServices.findByIdProgramacion(pro.getIdProgramacion());
					    this.tripulacionServices.actualizarIdPiloto(tx.getIdTripulacion(), pil.getIdPiloto(), pil.isFlagTemporal());
					}
				}
		    }else if(this.programacion.getIntCorreEnlace()>0){
		    	//TRAER SERVICIOS Hijos
				List<Servicio> listaServHijo = this.servicioService.obtenerServiciosHijos(this.programacion.getIdServicio());
				for(int i=0; i<listaServHijo.size();i++){
					Servicio sv = listaServHijo.get(i);
					sv.setfSalidaProgramacion(this.programacion.getfSalida());
					List<Programacion> listaProgramacionesHijos = this.programacionService.obtenerProgramacionesHijos(sv);
					for(int a=0; a<listaProgramacionesHijos.size();a++){
						Programacion pro = listaProgramacionesHijos.get(a);
						//eliminar tripulaciones
					    Tripulacion tx = new Tripulacion();
					    tx = tripulacionServices.findByIdProgramacion(pro.getIdProgramacion());
					    this.tripulacionServices.actualizarIdPiloto(tx.getIdTripulacion(), pil.getIdPiloto(), pil.isFlagTemporal());
					}
				}
			}
			
			for(Programacion p : listaProgramacionAsig){
				if(p.getIdProgramacion()==programacion.getIdProgramacion()){
					p.setPiloto(pil);
				}
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void buscarCopiloto(Programacion p){
		try {
			this.programacion = p;
			this.listaCopilotos = pilotoServices.findByEstado();
			for(int i=0; i<listaCopilotos.size();i++){
				Piloto pil = listaCopilotos.get(i);
				if(p.getPiloto()!=null){
					if(p.getPiloto().getIdPiloto()==pil.getIdPiloto()){
						listaCopilotos.remove(i);
					}
				}			
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void asignarCopiloto(Piloto pil){
		
		try {
			Tripulacion t = tripulacionServices.findByIdProgramacion(programacion.getIdProgramacion());
			if(!pil.isFlagTemporal()){
				busServices.actualizarCopiloto(pil.getIdPiloto(),t.getIdBus());
			}
			this.tripulacionServices.actualizarIdCopiloto(t.getIdTripulacion(), pil.getIdPiloto(), pil.isFlagTemporal());
			
			Historial_AsigPCT historial = new Historial_AsigPCT();
			historial.setIdBus(t.getIdBus());
			historial.setFecha(new Date());
			historial.setEstado("0"); 
			historial.setIdCopiloto(pil.getIdPiloto()); 
			historial_asigPCTServices.actualizarEstadoHistorial_Copiloto(historial);
			historial.setEstado("1"); 
			historial_asigPCTServices.crearHistorial_Copiloto(historial);
			
			//actulizando si es compartida o no 
			if(this.programacion.getDesRutaCompartida().equals("SI")){
		    	//TRAER SERVICIOS Hijos
				List<Servicio> listaServHijo = this.servicioService.obtenerServiciosHijos(this.programacion.getIdServicio());
				for(int i=0; i<listaServHijo.size();i++){
					Servicio sv = listaServHijo.get(i);
					sv.setfSalidaProgramacion(this.programacion.getfSalida());
					List<Programacion> listaProgramacionesHijos = this.programacionService.obtenerProgramacionesHijos(sv);
					for(int a=0; a<listaProgramacionesHijos.size();a++){
						Programacion pro = listaProgramacionesHijos.get(a);
						//eliminar tripulaciones
					    Tripulacion tx = new Tripulacion();
					    tx = tripulacionServices.findByIdProgramacion(pro.getIdProgramacion());
					    this.tripulacionServices.actualizarIdCopiloto(tx.getIdTripulacion(), pil.getIdPiloto(), pil.isFlagTemporal());
					}
				}
		    }else if(this.programacion.getIntCorreEnlace()>0){
		    	//TRAER SERVICIOS Hijos
				List<Servicio> listaServHijo = this.servicioService.obtenerServiciosHijos(this.programacion.getIdServicio());
				for(int i=0; i<listaServHijo.size();i++){
					Servicio sv = listaServHijo.get(i);
					sv.setfSalidaProgramacion(this.programacion.getfSalida());
					List<Programacion> listaProgramacionesHijos = this.programacionService.obtenerProgramacionesHijos(sv);
					for(int a=0; a<listaProgramacionesHijos.size();a++){
						Programacion pro = listaProgramacionesHijos.get(a);
						//eliminar tripulaciones
					    Tripulacion tx = new Tripulacion();
					    tx = tripulacionServices.findByIdProgramacion(pro.getIdProgramacion());
					    this.tripulacionServices.actualizarIdCopiloto(tx.getIdTripulacion(), pil.getIdPiloto(), pil.isFlagTemporal());
					}
				}
			}
			
			for(Programacion p : listaProgramacionAsig){
				if(p.getIdProgramacion()==programacion.getIdProgramacion()){
					p.setCopiloto(pil);
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	public void guardarProgramacionFlota(Programacion pro) {
		
		try {
			Tripulacion t = tripulacionServices.findByIdProgramacion(pro.getIdProgramacion());
			//actualidando el campo IDBus de la tabla t_programacion el valor sera el id del bus(t_flota) que ya tiene piloto y copiloto asignado
			this.programacionService.actualizarBusProgramacionAsig(programacion.getFlota().getIdBus(), pro.getIdProgramacion());
			
			t=new Tripulacion();
			t.setIdProgramacion(pro.getIdProgramacion()); 
			t.setfRegistro(new Date());
			t.setTipo(1); 
			t.setEstado(0);
			t.setIdBus(programacion.getFlota().getIdBus());
			t.setIdPiloto(programacion.getFlota().getPiloto());
			t.setIdCopiloto(programacion.getFlota().getCopiloto()); 
			
			Programacion p = this.programacionService.findById(pro.getIdProgramacion());
			
			t.setIdTerramoza(p.getIdTerramoza());
			t.setIdTerramoza2(p.getIdTerramoza2());
			//creamos una nueva tripulacion
			tripulacionServices.crearTripulacion(t);
			
			//Verificamos que no sea una programacion con ruta Compartida
			if(pro.getDesRutaCompartida().equals("SI")){
				//TRAER SERVICIOS Hijos
				List<Servicio> listaServHijo = this.servicioService.obtenerServiciosHijos(pro.getIdServicio());
				for(int i=0; i<listaServHijo.size();i++){
					Servicio sv = listaServHijo.get(i);
					sv.setfSalidaProgramacion(pro.getfSalida());
					List<Programacion> listaProgramacionesHijos = this.programacionService.obtenerProgramacionesHijos(sv);
					for(int j=0;j<listaProgramacionesHijos.size();j++){
						Programacion proH = listaProgramacionesHijos.get(j);
						Tripulacion tripuHijos = t;
						tripuHijos.setIdProgramacion(proH.getIdProgramacion()); 
						this.tripulacionServices.crearTripulacion(tripuHijos);
						//ACTUALIZAMOS EL ESTADO DE LA PROGRAMACIONES HIJOS
						this.programacionService.actualizarBusProgramacionAsig(programacion.getFlota().getIdBus(), proH.getIdProgramacion());
					}
										
				}
			}
			
			//Validar si se convirtio en ruta compartida
			if(this._paseTramo){
				//TRAER SERVICIOS Hijos
				List<Servicio> listaServHijo2 = this.servicioService.obtenerServiciosHijos(pro.getIdServicio());
				for(int i2=0; i2<listaServHijo2.size();i2++){
					Servicio sv2 = listaServHijo2.get(i2);
					sv2.setfSalidaProgramacion(pro.getfSalida());
					List<Programacion> listaProgramacionesHijos2 = this.programacionService.obtenerProgramacionesHijos(sv2);
					for(int j=0;j<listaProgramacionesHijos2.size();j++){
						Programacion proH2 = listaProgramacionesHijos2.get(j);
						Tripulacion tripuHijos2 = t;
						tripuHijos2.setIdProgramacion(proH2.getIdProgramacion()); 
						this.tripulacionServices.crearTripulacion(tripuHijos2);
						//ACTUALIZAMOS EL ESTADO DE LA PROGRAMACIONES HIJOS
						this.programacionService.actualizarBusProgramacionConvCompartida(programacion.getFlota().getIdBus(), proH2.getIdProgramacion(),sv2.getIntCorre());
						//Ponemos el int Correlativo del padre
						this.programacionService.actualizarProgramacionPadreCorrelativo(pro.getIdProgramacion(), sv2.getIntCorre());
					}
				}
				this._paseTramo = false;
			}

			//aumentar el kilometraje aproximado al bus 
			Destino destino = this.destinoServices.obtenerRecorridoAproximado(pro.getOrigen(), pro.getDestino());
			this.flotaService.aumentarKilometrajeAproximado(this.programacion.getFlota().getIdBus(), destino.getKmDistancia());
			//actualizamos la cantidad de km recorridos a las partes del bus
		    
			FlotaAutoparte floPartex = new FlotaAutoparte();
			floPartex.setIdBus(this.programacion.getFlota().getIdBus());
			floPartex.setKmAdherir(destino.getKmDistancia());
			floPartex.setFecKmIncrementoAprox(this.fechaProgramacion);
			this.flotaAutoparteService.incrementarKmAproximado(floPartex);
			
			
			buscarProgramacion();
			FacesUtils.showFacesMessage("Programacion de bus actualizada correctamente.",Constante.INFORMACION);

		} catch (Exception e) {
			e.printStackTrace();
		}

		this.programacion = new Programacion();
	}

	
	
	
	public void eliminarProgramacionFlota()
	{
		
		try {
			boolean _pase = false;
			//validando que no se haya VENDIDO NI RESERVADO boletos
			List<AsientoVenta> lista1 = this.asientoVentaServices.verificandoVentaAsiento(this.programacion.getIdProgramacion());
			if(lista1.size()==0) _pase = true; 
			
		  if(this.programacion.getDesRutaCompartida().equals("SI"))
		  {
				List<Servicio> listaServHijo = this.servicioService.obtenerServiciosHijos(this.programacion.getIdServicio());
				for(int i=0; i<listaServHijo.size();i++){
					Servicio sv = listaServHijo.get(i);
					sv.setfSalidaProgramacion(this.programacion.getfSalida());
					List<Programacion> listaProgramacionesHijos = this.programacionService.obtenerProgramacionesHijos(sv);
					for(int a=0; a<listaProgramacionesHijos.size();a++){
						Programacion pro = listaProgramacionesHijos.get(a);
						List<AsientoVenta> lista2 = this.asientoVentaServices.verificandoVentaAsiento(pro.getIdProgramacion());
						if(lista2.size()==0) _pase = true;
					}
				}
		   }else if(this.programacion.getIntCorreEnlace()>0){
		    	List<Programacion> listaProHijos = this.programacionService.findByIntCorreEnlace(this.programacion.getIntCorreEnlace(),this.programacion.getIdProgramacion());
		    	for(int x=0;x<listaProHijos.size();x++){
		    		Programacion proHi = listaProHijos.get(x);
		    		List<AsientoVenta> lista3 = this.asientoVentaServices.verificandoVentaAsiento(proHi.getIdProgramacion());
					if(lista3.size()==0) _pase = true;
		    	}
		  }
		  
		  //si tiene un servicio padre  OJO Observar
		  List<Servicio> lis = this.servicioService.listaHijosServiciosCompartidos(this.programacion.getIntCorreEnlace());
		  for(Servicio ser : lis)
		  {
			 if(this.programacion.getIdServicio().intValue() == ser.getIdServicio().intValue())
			 {
				 	if(ser.getGradoServ().intValue() == 2)
					{
						_pase = Boolean.FALSE;
						break;
					}
				 	break;
			 } 

		  }
		   
		    
		  
			
			if(_pase)
			{
				Tripulacion t = this.tripulacionServices.findByIdProgramacion(this.programacion.getIdProgramacion());
				this.tripulacionServices.eliminarTripulacion(t.getIdTripulacion());
				this.programacionService.actualizarBusProgramacionNoAsig(this.programacion.getIdProgramacion());
				//retiramos los kilometros asignados al bus
				Destino destino = this.destinoServices.obtenerRecorridoAproximado(this.programacion.getOrigen(), this.programacion.getDestino());
				this.flotaService.retirarKilometrajeAproximado(this.programacion.getFlota().getIdBus(), destino.getKmDistancia());

				//retiramos los kilometros a sus partes
				FlotaAutoparte flotaAutopa = new FlotaAutoparte();
				flotaAutopa.setIdBus(this.programacion.getFlota().getIdBus());
				flotaAutopa.setFecKmIncrementoAprox(new Date());
				flotaAutopa.setKmAdherir(destino.getKmDistancia());
			    this.flotaAutoparteService.retirarincrementarKmAproximado(flotaAutopa);
			    
			    //retiramos las programaciones hijas si las tuviera
			    if(this.programacion.getDesRutaCompartida().equals("SI")){
					List<Servicio> listaServHijo = this.servicioService.obtenerServiciosHijos(this.programacion.getIdServicio());
					for(int i=0; i<listaServHijo.size();i++){
						Servicio sv = listaServHijo.get(i);
						sv.setfSalidaProgramacion(this.programacion.getfSalida());
						List<Programacion> listaProgramacionesHijos = this.programacionService.obtenerProgramacionesHijos(sv);
						for(int a=0; a<listaProgramacionesHijos.size();a++){
							Programacion pro = listaProgramacionesHijos.get(a);
							this.tripulacionServices.eliminarTripulacionXProgramacion(pro.getIdProgramacion());
							this.programacionService.actualizarBusProgramacionNoAsig(pro.getIdProgramacion());
						}
					}
			    }else if(this.programacion.getIntCorreEnlace()>0){
			    	List<Programacion> listaProHijos = this.programacionService.findByIntCorreEnlace(this.programacion.getIntCorreEnlace(),this.programacion.getIdProgramacion());
			    	for(int x=0;x<listaProHijos.size();x++){
			    		Programacion proHi = listaProHijos.get(x);
			    		this.servicioService.eliminarServicio(proHi.getIdServicio());
			    		this.programacionService.eliminarProgramacion(proHi.getIdProgramacion());
			    		this.tripulacionServices.eliminarTripulacionXProgramacion(proHi.getIdProgramacion());
			    	}
			    }
				buscarProgramacion();
				
				RequestContext context = RequestContext.getCurrentInstance();  
				FacesUtils.showFacesMessage("Operación realizada correctamente.",Constante.INFORMACION);
				context.update("sms");
				
			}else{
				RequestContext context = RequestContext.getCurrentInstance();  
				FacesUtils.showFacesMessage("No se puede liberar la programacion debido a boletos vendidos o tiene una ruta principal.",Constante.ERROR);
				context.update("sms");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cancelarRutaCompartida(){
		this._paseTramo = false;
	}
	
	public Servicio buscarServicio(String fecha, Integer origenId,
			Integer destinoId, String hsalida, Integer idProgramacion) {
		Servicio serv = null;
		try {
			serv = this.servicioService.serviciobyProgramacion(fecha, origenId,
					destinoId, hsalida, idProgramacion);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return serv;
	}
	
	
	public void verCroquis(Programacion pro){

		this.programacionSelec = pro;
		Servicio serv = null;
		ClaseServicio clase = null;
		serv = this.buscarServicio(new SimpleDateFormat("yyyy-MM-dd").format(this.fechaProgramacion),this.idOrigen,this.idDestino, pro.gethSalida(),pro.getIdProgramacion());
		//serv = this.buscarServicio(this.fechaProgramacion,this.idOrigen,this.idDestino, pro.gethSalida(),pro.getIdProgramacion());
		try {
			clase = this.claseServicioServices.findByServicio(serv.getIdServicio());
		
		this.programacionSelec.setServicio(serv);
		this.programacionSelec.setClase(clase);
		
		this.disponibles = this.asientoVentaServices.countbyprogramacionAndEstado(this.programacionSelec.getIdProgramacion(),new String("LIBRE"),Boolean.TRUE);
		this.asi_vendidos = this.asientoVentaServices.countbyprogramacionAndEstado(this.programacionSelec.getIdProgramacion(),new String("VENTA"),Boolean.TRUE);
		this.asi_reservados = this.asientoVentaServices.countbyprogramacionAndEstado(this.programacionSelec.getIdProgramacion(),new String("RESERVA"),Boolean.TRUE);
		this.disponiblesP1 = this.asientoVentaServices.countbyprogramacionAndEstadoXPiso(this.programacionSelec.getIdProgramacion(), "LIBRE", new Integer(1),Boolean.TRUE);
		this.disponiblesP2 = this.asientoVentaServices.countbyprogramacionAndEstadoXPiso(this.programacionSelec.getIdProgramacion(), "LIBRE", new Integer(2),Boolean.TRUE);
		
		this.programacionSelec.setVendidos(asi_vendidos);
		this.programacionSelec.setReservados(asi_reservados);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		StringBuilder etiqueta = new StringBuilder();
		if (clase.getNroPisos() == 1) {
			etiqueta.append(programacionSelec.gethSalida());
			etiqueta.append(", ");
			etiqueta.append(serv.getDescripcion());
			//etiqueta.append(" ");
			//etiqueta.append("Cap.:");
			//etiqueta.append(clase.getAsientos());
			//etiqueta.append(" ");
			//etiqueta.append(" P1: S/.");
			//etiqueta.append(serv.getPrecio1());


		} else {
			etiqueta.append(programacionSelec.gethSalida());
			etiqueta.append(", ");
			etiqueta.append(serv.getDescripcion());
			//etiqueta.append(" ");
			//etiqueta.append("Cap.:");
			//etiqueta.append(clase.getAsientos());
			//etiqueta.append(" P1: S/.");
			//etiqueta.append(serv.getPrecio1());
			//etiqueta.append(" P2: S/.");
			//etiqueta.append(serv.getPrecio2());

		}
		this.programacionSelec.setEtiqueta(etiqueta.toString());
		this.programacionSelec.setDisponibles(this.disponibles);
		this.programacionSelec.setDisponibleP1(this.disponiblesP1);
		this.programacionSelec.setDisponibleP2(this.disponiblesP2);
		
		this.manifiesto = Boolean.FALSE;
		this._rutaCompartida = this.programacionSelec.getDesRutaCompartida();
		this._idServicioCompartido = this.programacionSelec.getIdServicio();
		this._gradoServTramo = this.programacionSelec.getDesGradoServicio();
		this._intCorreEnlace = this.programacionSelec.getIntCorreEnlace();

		if (this.programacionSelec.getServicio() != null) 
		{
			this.programacionSelec.setSeleccionado(Boolean.TRUE);
			for (Programacion prog : listaProgramacion)
			{
				if(prog != this.programacionSelec )
				{
					prog.setSeleccionado(Boolean.FALSE);

				}
			}

			// bus clase
			try {
				this.busClase = this.claseServicioServices.findById(this.programacionSelec.getServicio().getIdClase());

				this.tipoAsientop1 = this.tipoAsientoService.findById(this.busClase.getIdtipoasientop1());
				
				if (this.busClase.getNroPisos().equals(2)) 
				{
					this.tipoAsientop2 = this.tipoAsientoService.findById(this.busClase.getIdtipoasientop2());
				}

				consultarConfiguracion();
				
				this.visibleBus = Boolean.TRUE;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void consultarConfiguracion() throws Exception 
	{

		System.out.println("configuracion de asientos " + this.programacionSelec.getIdProgramacion());
		this.nroColumnasPisoUno = this.busClase.getNroColumnasPisoUno();
		// this.anchoBusUno = 560;
		this.anchoBusDos = 860;
		// this.anchoBusDos = 700;

		// generando asientos para el piso uno siempre
		this.listaAsientosPisoUno = this.asientoService.findAsientoByClase(this.busClase.getIdclase(), new Integer(1));
		AsientoVenta asv = null;

		for (Asiento as : this.listaAsientosPisoUno) {
			asv = this.asientoVentaServices.findAsientoByAsientoAndProgramacion(as.getIdAsiento(), as.getNumero(),this.programacionSelec.getIdProgramacion());
			as.setAsientoVenta(asv);
		}

		// generando conf inicial para el piso dos
		if (this.busClase.getNroPisos() == 2) 
		{
			this.nroColumnasPisoDos = this.busClase.getNroColumnasPisoDos();
			this.listaAsientosPisoDos = this.asientoService.findAsientoByClase(
					this.busClase.getIdclase(), new Integer(2));
			for (Asiento as : this.listaAsientosPisoDos) {
				asv = this.asientoVentaServices.findAsientoByAsientoAndProgramacion(as.getIdAsiento(),as.getNumero(),this.programacionSelec.getIdProgramacion());
				as.setAsientoVenta(asv);
			}
		}

	}
	
	public void capacidad(){
		System.out.println("entro aca ");
	}
	
	/*
	public void aumentarCapacidadFlota(){
		try {
			Boolean valido=Boolean.TRUE;
			RequestContext context = RequestContext.getCurrentInstance();  
	   	    context.addCallbackParam("esValido", valido );
	   	    System.out.println("el id de programacion seleccionada es " + this.programacion.getIdProgramacion());
			 this.asientoVentaServices.aumentarCapacidad(this.programacion.getIdProgramacion());
			 //Actualizando programacion 
			 for (int i = 0; i < this.listaFlotaAsociada.size(); i++) {
				Flota f = this.listaFlotaAsociada.get(i);
				if(f.getIdBus() == this.flotaCapacidad.getIdBus()){
					this.flotaCapacidad = f;
				}
			}
			 Piloto piloto = new Piloto();
			 piloto.setIdPiloto(this.flotaCapacidad.getPiloto());
			 piloto.setFlagTemporal(Boolean.FALSE);
			 asignarPiloto(piloto);
			 
			 Piloto copiloto = new Piloto();
			 copiloto.setIdPiloto(this.flotaCapacidad.getCopiloto());
			 copiloto.setFlagTemporal(Boolean.FALSE);
			 asignarCopiloto(copiloto);

			 
			 this.programacionService.actualizarServicio(this.programacion.getIdProgramacion(),this.flotaCapacidad.getIdServicio(), this.flotaCapacidad.getIdBus());

			  if(this.programacion.getDesRutaCompartida().equals("SI")){
					List<Servicio> listaServHijo = this.servicioService.obtenerServiciosHijos(this.programacion.getIdServicio());
					for(int i=0; i<listaServHijo.size();i++){
						Servicio sv = listaServHijo.get(i);
						sv.setfSalidaProgramacion(this.programacion.getfSalida());
						List<Programacion> listaProgramacionesHijos = this.programacionService.obtenerProgramacionesHijos(sv);
						for(int a=0; a<listaProgramacionesHijos.size();a++){
							Programacion pro = listaProgramacionesHijos.get(a);
							 this.asientoVentaServices.aumentarCapacidad(pro.getIdProgramacion());
							 this.programacionService.actualizarServicio(pro.getIdProgramacion(),this.flotaCapacidad.getIdServicio(), this.flotaCapacidad.getIdBus());
							 asignarPiloto(piloto);
							 asignarCopiloto(copiloto);
						}
					}
			    }else if(this.programacion.getIntCorreEnlace()>0){
			    	List<Programacion> listaProHijos = this.programacionService.findByIntCorreEnlace(this.programacion.getIntCorreEnlace(),this.programacion.getIdProgramacion());
			    	for(int x=0;x<listaProHijos.size();x++){
			    		Programacion proHi = listaProHijos.get(x);
			    		 this.asientoVentaServices.aumentarCapacidad(proHi.getIdProgramacion());
			    		 this.programacionService.actualizarServicio(proHi.getIdProgramacion(),this.flotaCapacidad.getIdServicio(), this.flotaCapacidad.getIdBus());
			    		 asignarPiloto(piloto);
						 asignarCopiloto(copiloto);
			    	}
			    }
			  
			  buscarProgramacion();
			  
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/
	
	
	/*
	public void listarFlotasAsociadas(Programacion program){
		try {
			this.programacion = program;
			//obteniendo los origenes y destinos del servicio
			Servicio serv = this.servicioService.findById(this.programacion.getIdServicio());
			
			
			this.listaFlotaAsociada = this.flotaService.obtenerFlotaAsociadas(program.getIdClase(),serv.getOrigen(), serv.getDestino());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/
	public void eliminarProgramacionNoAsignada()
	{
		try {
			System.out.println("la programacion seleccionada es " + this.programacion.getIdProgramacion());
			RequestContext context = RequestContext.getCurrentInstance();
			
			//consultando hijos:
			Servicio serv = this.servicioService.findById(this.programacion.getIdServicio());
			List<Servicio> listaServ = new ArrayList<>();
			if(serv.getIntCorre().intValue() != 0)
			{
				listaServ = this.servicioService.listaHijosServiciosCompartidos(serv.getIntCorre());
			}
			

			if(this._busAsignado){
				FacesUtils.showFacesMessage("La programacion cuenta con bus asignado.",Constante.ERROR);
				context.update("sms");
			}else if(!listaServ.isEmpty()){
				FacesUtils.showFacesMessage("La programacion cuenta con rutas compartidas, elimine las rutas compartidas primero.",Constante.ERROR);
				context.update("sms");
			}else{
				//validando que no tenga asientos vendidos 
				List<AsientoVenta> lista = this.asientoVentaServices.verificandoVentaAsiento(this.programacion.getIdProgramacion());
				if(lista.size()>0){
					FacesUtils.showFacesMessage("La programacion cuenta con asientos vendidos.",Constante.ERROR);
					context.update("sms");
				}else{
					FacesUtils.showFacesMessage("Programación eliminada correctamente.",Constante.INFORMACION);
					context.update("sms");
					this.programacionService.eliminarProgramacion(this.programacion.getIdProgramacion());
					buscarProgramacion();
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void amppliarCapacidadBus(Programacion p)
	{
		try {
			
			RequestContext context = RequestContext.getCurrentInstance();
			
			
			
			/*this.listaAsientosPisoUno = this.asientoService.findAsientoByClase(this.claseServicioOriginal.getIdclase(), new Integer(1));
			this.listaAsientosPisoDos = this.asientoService.findAsientoByClase(this.claseServicioOriginal.getIdclase(), new Integer(2));*/
			

			
			this.listaAsientosPisoUno = this.asientoService.findAsientoByClase(p.getIdClase(), new Integer(1));
			this.listaAsientosPisoDos = this.asientoService.findAsientoByClase(p.getIdClase(), new Integer(2));
			
			this.hacerAmpliacion(p, this.listaAsientosPisoUno, this.listaAsientosPisoDos);
			
			FacesUtils.showFacesMessage("El bus ha sido ampliado para la fecha especifica.",Constante.INFORMACION);
			context.update("sms");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	public void hacerAmpliacion(Programacion prog,List<Asiento> listaAsientosPisoUno, List<Asiento> listaAsientosPisoDos)
	{
		System.out.println("ampliando servicio..");
		ClaseServicio clase;
		
		try {
			
			clase = prog.getClase();
			AsientoVenta asv = null;
			Boolean encontrado = Boolean.FALSE;
			
			if(clase.getAsientos().intValue() == 39 || clase.getAsientos().intValue() == 53)
			{

				for (Asiento as : listaAsientosPisoUno) 
				{
					if(as.getNumero().equals("52"))
					{
						encontrado = Boolean.TRUE;
						
					}else if(as.getNumero().equals("55")){
						
						encontrado = Boolean.TRUE;
					}else if(as.getNumero().equals("56")){
						
						encontrado = Boolean.TRUE;
					}
					
					if(encontrado)
					{
						asv = this.asientoVentaService.findAsientoByAsientoAndProgramacion(as.getIdAsiento(), as.getNumero(),prog.getIdProgramacion());
						as.setAsientoVenta(asv);
						//actualizando visible a true
						this.asientoVentaService.aumentarCapacidad(asv.getIdasientoventa());
						encontrado = Boolean.FALSE;
						System.out.println("servicio ampliado con exito...!!!");
					}
	
				}
				
				
			}else if(clase.getAsientos().intValue() == 31){

				for (Asiento as : listaAsientosPisoDos) 
				{
					if(as.getNumero().equals("12"))
					{
						encontrado = Boolean.TRUE;
					}
					
					if(encontrado)
					{
						asv = this.asientoVentaService.findAsientoByAsientoAndProgramacion(as.getIdAsiento(), as.getNumero(),prog.getIdProgramacion());
						as.setAsientoVenta(asv);
						//actualizando visible a true
						this.asientoVentaService.aumentarCapacidad(asv.getIdasientoventa());
						encontrado = Boolean.FALSE;
						System.out.println("servicio 31 ampliado con exito...!!!");
						break;
					}
	
				}
				//pintando el bus
				//this.seleccionar();
				
			}
			
			
			//marcando la programacion como ampliado
			prog.setAmpliado(Boolean.TRUE);
			this.programacionService.actualizarAmpliacionBusProgramacion(Boolean.TRUE, prog.getIdProgramacion());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	
	
	public void guardarIngreso()
	{
		System.out.println("asignando");
		try {
			if(this.asignacion.getIdasignacion() != null) //editar
			{
				this.asignacionService.actualizarAsignacion(this.asignacion);
				
			}else{//crear
				this.asignacion.setIdUsuario(this.usuarioLogin.getIdusuario());
				this.asignacion.setProgramacionId(this.programacionAsignacion.getIdProgramacion());
				this.asignacion.setIdPiloto(this.programacionAsignacion.getPiloto().getIdPiloto());
				this.asignacion.setAgenciaOrigen(this.idOrigen);
				this.asignacionService.crearAsignacion(this.asignacion);
			}
			
			this.programacionAsignacion.setAsignacionViatico(this.asignacion.getMonto());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void asignarIngreso(Programacion p)
	{
		System.out.println("REcuperando si tiene y si no guardando");
		this.buscarBus(p);
		try {
			this.programacionAsignacion = p;
			this.asignacion = this.asignacionService.findByProgramacion(p.getIdProgramacion());
			if(this.asignacion == null)
			{	this.asignacion = new Asignacion();
				this.asignacion.setFecha(new Date());
				this.asignacion.setMonto(new BigDecimal(0));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	/** VARIABLES PARA EL CROQUIS TOMADO DE VentaDirectaMB :  */
	
	private Boolean manifiesto;
	private String _rutaCompartida;
	private Integer _idServicioCompartido;
	private Integer _gradoServTramo;
	private Integer _intCorreEnlace;
	private Boolean visibleBus;
	private Integer nroColumnasPisoUno;
	private Integer nroColumnasPisoDos;
	private Integer anchoBusDos;
	private Integer nroFilas;
	private Integer disponibles;
	private Integer disponiblesP1;
	private Integer disponiblesP2;
	
	private List<Asiento> listaAsientosPisoUno;
	private List<Asiento> listaAsientosPisoDos;
	
	private Programacion programacionSelec;
	private ClaseServicio busClase;
	private TipoAsiento tipoAsientop1;
	private TipoAsiento tipoAsientop2;
	
	private ClaseServicioServices claseServicioServices;
	private TipoAsientoService tipoAsientoService;
	private AsientoServices asientoService;
	
	
	/**********************Getter's and Seter's************************/

	
	
	public MenuServices getMenuServices() {
		return menuServices;
	}

	public Programacion getProgramacionFilter() {
		return programacionFilter;
	}

	public void setProgramacionFilter(Programacion programacionFilter) {
		this.programacionFilter = programacionFilter;
	}

	public Integer getIdClaseBus() {
		return idClaseBus;
	}

	public void setIdClaseBus(Integer idClaseBus) {
		this.idClaseBus = idClaseBus;
	}

	public List<ClaseServicio> getListaServicios() {
		return listaServicios;
	}

	public void setListaServicios(List<ClaseServicio> listaServicios) {
		this.listaServicios = listaServicios;
	}

	public Integer getIdCategoriaServ() {
		return idCategoriaServ;
	}

	public void setIdCategoriaServ(Integer idCategoriaServ) {
		this.idCategoriaServ = idCategoriaServ;
	}

	public Servicio getServicioPaint() {
		return servicioPaint;
	}

	public void setServicioPaint(Servicio servicioPaint) {
		this.servicioPaint = servicioPaint;
	}

	public Date getFechaProgramacion() {
		return fechaProgramacion;
	}

	public void setFechaProgramacion(Date fechaProgramacion) {
		this.fechaProgramacion = fechaProgramacion;
	}

	public Programacion getProgramacion() {
		return programacion;
	}

	public void setProgramacion(Programacion programacion) {
		this.programacion = programacion;
	}

	public List<Destino> getListaDestino() {
		return listaDestino;
	}

	public void setListaDestino(List<Destino> listaDestino) {
		this.listaDestino = listaDestino;
	}

	public ProgramacionService getProgramacionService() {
		return programacionService;
	}

	public void setProgramacionService(ProgramacionService programacionService) {
		this.programacionService = programacionService;
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

	public List<Agencia> getListaAgencias() {
		return listaAgencias;
	}

	public void setListaAgencias(List<Agencia> listaAgencias) {
		this.listaAgencias = listaAgencias;
	}

	public Integer getIdOrigen() {
		return idOrigen;
	}

	public void setIdOrigen(Integer idOrigen) {
		this.idOrigen = idOrigen;
	}

	public Integer getIdDestino() {
		return idDestino;
	}

	public void setIdDestino(Integer idDestino) {
		this.idDestino = idDestino;
	}

	public List<Programacion> getListaProgramacion() {
		return listaProgramacion;
	}

	public void setListaProgramacion(List<Programacion> listaProgramacion) {
		this.listaProgramacion = listaProgramacion;
	}

	public List<Flota> getListaBuses() {
		return listaBuses;
	}

	public void setListaBuses(List<Flota> listaBuses) {
		this.listaBuses = listaBuses;
	}

	public Flota getBusSelected() {
		return busSelected;
	}

	public void setBusSelected(Flota busSelected) {
		this.busSelected = busSelected;
	}

	public List<Piloto> getListaPilotos() {
		return listaPilotos;
	}

	public void setListaPilotos(List<Piloto> listaPilotos) {
		this.listaPilotos = listaPilotos;
	}

	public List<Piloto> getListaCopilotos() {
		return listaCopilotos;
	}

	public void setListaCopilotos(List<Piloto> listaCopilotos) {
		this.listaCopilotos = listaCopilotos;
	}

	public List<Programacion> getListaProgramacionAsig() {
		return listaProgramacionAsig;
	}

	public void setListaProgramacionAsig(List<Programacion> listaProgramacionAsig) {
		this.listaProgramacionAsig = listaProgramacionAsig;
	}

	public boolean isEditarBus() {
		return editarBus;
	}

	public void setEditarBus(boolean editarBus) {
		this.editarBus = editarBus;
	}

	public boolean isEditarPiloto() {
		return editarPiloto;
	}

	public void setEditarPiloto(boolean editarPiloto) {
		this.editarPiloto = editarPiloto;
	}

	public boolean isEditarCopiloto() {
		return editarCopiloto;
	}

	public void setEditarCopiloto(boolean editarCopiloto) {
		this.editarCopiloto = editarCopiloto;
	}

	public List<Flota> getListaBusesFilter() {
		return listaBusesFilter;
	}

	public void setListaBusesFilter(List<Flota> listaBusesFilter) {
		this.listaBusesFilter = listaBusesFilter;
	}

	public List<Programacion> getListaProgramacionFilter() {
		return listaProgramacionFilter;
	}

	public void setListaProgramacionFilter(
			List<Programacion> listaProgramacionFilter) {
		this.listaProgramacionFilter = listaProgramacionFilter;
	}

	public List<Programacion> getListaProgramacionAsigFilter() {
		return listaProgramacionAsigFilter;
	}

	public void setListaProgramacionAsigFilter(
			List<Programacion> listaProgramacionAsigFilter) {
		this.listaProgramacionAsigFilter = listaProgramacionAsigFilter;
	}

	public List<CategoriaServicio> getListaCatServicio() {
		return listaCatServicio;
	}

	public void setListaCatServicio(List<CategoriaServicio> listaCatServicio) {
		this.listaCatServicio = listaCatServicio;
	}

	public String getTipoAsignacion() {
		return tipoAsignacion;
	}

	public void setTipoAsignacion(String tipoAsignacion) {
		this.tipoAsignacion = tipoAsignacion;
	}

	public Programacion getProgramacionRuta() {
		return programacionRuta;
	}

	public void setProgramacionRuta(Programacion programacionRuta) {
		this.programacionRuta = programacionRuta;
	}

	public Servicio getServicioRuta() {
		return servicioRuta;
	}

	public void setServicioRuta(Servicio servicioRuta) {
		this.servicioRuta = servicioRuta;
	}

	public List<Agencia> getListaAgeTramos() {
		return listaAgeTramos;
	}

	public void setListaAgeTramos(List<Agencia> listaAgeTramos) {
		this.listaAgeTramos = listaAgeTramos;
	}

	public String getHora_Tramo2() {
		return hora_Tramo2;
	}

	public void setHora_Tramo2(String hora_Tramo2) {
		this.hora_Tramo2 = hora_Tramo2;
	}

	public String getMinutos_Tramo2() {
		return minutos_Tramo2;
	}

	public void setMinutos_Tramo2(String minutos_Tramo2) {
		this.minutos_Tramo2 = minutos_Tramo2;
	}

	public String getAm_pm_Tramo2() {
		return am_pm_Tramo2;
	}

	public void setAm_pm_Tramo2(String am_pm_Tramo2) {
		this.am_pm_Tramo2 = am_pm_Tramo2;
	}

	public boolean is_paseTramo() {
		return _paseTramo;
	}

	public void set_paseTramo(boolean _paseTramo) {
		this._paseTramo = _paseTramo;
	}

	// getters croquis
	
	public Boolean getManifiesto() {
		return manifiesto;
	}

	public void setManifiesto(Boolean manifiesto) {
		this.manifiesto = manifiesto;
	}

	public String get_rutaCompartida() {
		return _rutaCompartida;
	}

	public void set_rutaCompartida(String _rutaCompartida) {
		this._rutaCompartida = _rutaCompartida;
	}

	public Integer get_idServicioCompartido() {
		return _idServicioCompartido;
	}

	public void set_idServicioCompartido(Integer _idServicioCompartido) {
		this._idServicioCompartido = _idServicioCompartido;
	}

	public Integer get_gradoServTramo() {
		return _gradoServTramo;
	}

	public void set_gradoServTramo(Integer _gradoServTramo) {
		this._gradoServTramo = _gradoServTramo;
	}

	public Integer get_intCorreEnlace() {
		return _intCorreEnlace;
	}

	public void set_intCorreEnlace(Integer _intCorreEnlace) {
		this._intCorreEnlace = _intCorreEnlace;
	}

	public Boolean getVisibleBus() {
		return visibleBus;
	}

	public void setVisibleBus(Boolean visibleBus) {
		this.visibleBus = visibleBus;
	}

	public Integer getNroColumnasPisoUno() {
		return nroColumnasPisoUno;
	}

	public void setNroColumnasPisoUno(Integer nroColumnasPisoUno) {
		this.nroColumnasPisoUno = nroColumnasPisoUno;
	}

	public Integer getNroColumnasPisoDos() {
		return nroColumnasPisoDos;
	}

	public void setNroColumnasPisoDos(Integer nroColumnasPisoDos) {
		this.nroColumnasPisoDos = nroColumnasPisoDos;
	}

	public Integer getAnchoBusDos() {
		return anchoBusDos;
	}

	public void setAnchoBusDos(Integer anchoBusDos) {
		this.anchoBusDos = anchoBusDos;
	}

	public List<Asiento> getListaAsientosPisoUno() {
		return listaAsientosPisoUno;
	}

	public void setListaAsientosPisoUno(List<Asiento> listaAsientosPisoUno) {
		this.listaAsientosPisoUno = listaAsientosPisoUno;
	}

	public List<Asiento> getListaAsientosPisoDos() {
		return listaAsientosPisoDos;
	}

	public void setListaAsientosPisoDos(List<Asiento> listaAsientosPisoDos) {
		this.listaAsientosPisoDos = listaAsientosPisoDos;
	}

	public Programacion getProgramacionSelec() {
		return programacionSelec;
	}

	public void setProgramacionSelec(Programacion programacionSelec) {
		this.programacionSelec = programacionSelec;
	}

	public ClaseServicio getBusClase() {
		return busClase;
	}

	public void setBusClase(ClaseServicio busClase) {
		this.busClase = busClase;
	}

	public TipoAsiento getTipoAsientop1() {
		return tipoAsientop1;
	}

	public void setTipoAsientop1(TipoAsiento tipoAsientop1) {
		this.tipoAsientop1 = tipoAsientop1;
	}

	public TipoAsiento getTipoAsientop2() {
		return tipoAsientop2;
	}

	public void setTipoAsientop2(TipoAsiento tipoAsientop2) {
		this.tipoAsientop2 = tipoAsientop2;
	}

	public AsientoServices getAsientoService() {
		return asientoService;
	}

	public void setAsientoService(AsientoServices asientoService) {
		this.asientoService = asientoService;
	}

	public Integer getNroFilas() {
		return nroFilas;
	}

	public void setNroFilas(Integer nroFilas) {
		this.nroFilas = nroFilas;
	}

	public Integer getDisponibles() {
		return disponibles;
	}

	public void setDisponibles(Integer disponibles) {
		this.disponibles = disponibles;
	}

	public Integer getDisponiblesP1() {
		return disponiblesP1;
	}

	public void setDisponiblesP1(Integer disponiblesP1) {
		this.disponiblesP1 = disponiblesP1;
	}

	public Integer getDisponiblesP2() {
		return disponiblesP2;
	}

	public void setDisponiblesP2(Integer disponiblesP2) {
		this.disponiblesP2 = disponiblesP2;
	}

	public List<Flota> getListaFlotaAsociada() {
		return listaFlotaAsociada;
	}

	public void setListaFlotaAsociada(List<Flota> listaFlotaAsociada) {
		this.listaFlotaAsociada = listaFlotaAsociada;
	}

	public FlotaService getFlotaService() {
		return flotaService;
	}

	public void setFlotaService(FlotaService flotaService) {
		this.flotaService = flotaService;
	}

	public Flota getFlotaCapacidad() {
		return flotaCapacidad;
	}

	public void setFlotaCapacidad(Flota flotaCapacidad) {
		this.flotaCapacidad = flotaCapacidad;
	}

	public boolean is_busAsignado() {
		return _busAsignado;
	}

	public void set_busAsignado(boolean _busAsignado) {
		this._busAsignado = _busAsignado;
	}

	public List<Servicio> getListaServiciosCompartidos() {
		return listaServiciosCompartidos;
	}

	public void setListaServiciosCompartidos(
			List<Servicio> listaServiciosCompartidos) {
		this.listaServiciosCompartidos = listaServiciosCompartidos;
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

	public Integer getAsi_vendidos() {
		return asi_vendidos;
	}

	public void setAsi_vendidos(Integer asi_vendidos) {
		this.asi_vendidos = asi_vendidos;
	}

	public Integer getAsi_reservados() {
		return asi_reservados;
	}

	public void setAsi_reservados(Integer asi_reservados) {
		this.asi_reservados = asi_reservados;
	}

	public Servicio getServicioSeleccionado() {
		return servicioSeleccionado;
	}

	public void setServicioSeleccionado(Servicio servicioSeleccionado) {
		this.servicioSeleccionado = servicioSeleccionado;
	}

	public ClaseServicio getClaseServicioOriginal() {
		return claseServicioOriginal;
	}

	public void setClaseServicioOriginal(ClaseServicio claseServicioOriginal) {
		this.claseServicioOriginal = claseServicioOriginal;
	}

	public ClaseServicio getClaseServicioNuevo() {
		return claseServicioNuevo;
	}

	public void setClaseServicioNuevo(ClaseServicio claseServicioNuevo) {
		this.claseServicioNuevo = claseServicioNuevo;
	}

	public Integer getPisoDestino() {
		return pisoDestino;
	}

	public void setPisoDestino(Integer pisoDestino) {
		this.pisoDestino = pisoDestino;
	}

	public List<AsientoVenta> getListaAsientoVenta() {
		return listaAsientoVenta;
	}

	public void setListaAsientoVenta(List<AsientoVenta> listaAsientoVenta) {
		this.listaAsientoVenta = listaAsientoVenta;
	}

	public Programacion getProgramacionSeleccionada() {
		return programacionSeleccionada;
	}

	public void setProgramacionSeleccionada(Programacion programacionSeleccionada) {
		this.programacionSeleccionada = programacionSeleccionada;
	}

	public Boolean getAsignarPilotoCopiloto() {
		return asignarPilotoCopiloto;
	}

	public void setAsignarPilotoCopiloto(Boolean asignarPilotoCopiloto) {
		this.asignarPilotoCopiloto = asignarPilotoCopiloto;
	}

	public Boolean getConversionRutaCompartida() {
		return conversionRutaCompartida;
	}

	public void setConversionRutaCompartida(Boolean conversionRutaCompartida) {
		this.conversionRutaCompartida = conversionRutaCompartida;
	}

	public Boolean getAsignacionBus() {
		return asignacionBus;
	}

	public void setAsignacionBus(Boolean asignacionBus) {
		this.asignacionBus = asignacionBus;
	}

	public Boolean getListarHijos() {
		return listarHijos;
	}

	public void setListarHijos(Boolean listarHijos) {
		this.listarHijos = listarHijos;
	}


	public Programacion getProgramacionAsignacion() {
		return programacionAsignacion;
	}

	public void setProgramacionAsignacion(Programacion programacionAsignacion) {
		this.programacionAsignacion = programacionAsignacion;
	}

	public Asignacion getAsignacion() {
		return asignacion;
	}

	public void setAsignacion(Asignacion asignacion) {
		this.asignacion = asignacion;
	}


}
