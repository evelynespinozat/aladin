package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang3.ObjectUtils.Null;
import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.ClaseServicio;
import com.certicom.ittsa.domain.Usuario;
import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.CategoriaServicio;
import com.certicom.ittsa.domain.Destino;
import com.certicom.ittsa.domain.Excepcion;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Programacion;
import com.certicom.ittsa.domain.Servicio;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.CategoriaServicioService;
import com.certicom.ittsa.services.ClaseServicioServices;
import com.certicom.ittsa.services.DestinoServices;
import com.certicom.ittsa.services.ExcepcionService;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.ProgramacionService;
import com.certicom.ittsa.services.ServicioService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name = "programacionMB")
@ViewScoped
public class ProgramacionMB extends GenericBeans implements Serializable {

	private Programacion programacion;
	private Programacion programFilterDemanda;
	private Servicio servicioPaint;
	private boolean visibleComp;

	@ManagedProperty(value = "#{loginMB.usuario}")
	private Usuario usuarioLogin;

	private List<Agencia> listaAgencias;
	private List<Destino> listaDestino;
	private List<Servicio> listaServicio;
	private List<Servicio> listaFiltroServicio;
	private List<Excepcion> listaExcepciones;
	private List<ClaseServicio> listaClases;

	private Programacion programacionPaint;
	private Programacion editProgramacion;
	private Date dateInicio;
	private Date dateFinal;
	private String hora;
	private String minutos;
	private String am_pm;

	private String hora_Tramo2;
	private String minutos_Tramo2;
	private String am_pm_Tramo2;

	// services
	private ProgramacionService programacionService;
	private AgenciaService agenciaService;
	private DestinoServices destinoServices;
	private ServicioService servicioService;
	private MenuServices menuServices;
	private ExcepcionService excepcionService;
	private ClaseServicioServices claseServicioServices;

	// datos Log
	private Log log;
	private LogMB logmb;

	private Programacion progFilter;
	private CategoriaServicioService categoriaServicioService;
	private List<CategoriaServicio> listaCatServicio;
	private List<Programacion> listaProgramacion;
	private List<Programacion> listaProgramacionDemanda;
	private List<Programacion> listaProgramacionFilter;

	public ProgramacionMB() {
	}

	@PostConstruct
	public void incia() {
		dateInicio = new Date();
		dateFinal = new Date();

		progFilter = new Programacion();
		progFilter.setFecInicio(new Date());
		progFilter.setFecFin(new Date());
		this.visibleComp = false;

		this.categoriaServicioService = new CategoriaServicioService();
		this.agenciaService = new AgenciaService();
		this.destinoServices = new DestinoServices();
		this.programacionService = new ProgramacionService();
		this.servicioService = new ServicioService();
		this.menuServices = new MenuServices();
		this.excepcionService = new ExcepcionService();
		this.claseServicioServices = new ClaseServicioServices();

		this.programacion = new Programacion();
		this.editProgramacion = new Programacion();
		this.programFilterDemanda = new Programacion();
		this.servicioPaint = new Servicio();

		log = (Log) getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();

		this.programFilterDemanda.setFecInicio(new Date());
		this.programFilterDemanda.setFecFin(new Date());
		// listando
		try {
			this.listaClases = claseServicioServices.listaCServiciosActivos();
			this.listaAgencias = agenciaService.listaAgenciasActivas();
			this.listaCatServicio = categoriaServicioService
					.listaCatServicioActivos();
			// this.listaServicio =
			// servicioService.serviciosFilter(programFilterDemanda);
			int codMenu = menuServices.opcionMenuByPretty("pretty:agencia");
			log.setCod_menu(codMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cancelar() {
		this.programacion = new Programacion();
	}

	public void editarProgramacionDemanda(Programacion programa) {

		try {
			
			
			this.editProgramacion = new Programacion();
			this.editProgramacion = programa;
			// descomponiendo la hora

			String horaReal = this.editProgramacion.gethSalida();
			String hora = horaReal.substring(0, 2);
			String minuto = horaReal.substring(3, 5);
			String dial = horaReal.substring(6, 8);

			this.hora = hora;
			this.minutos = minuto;
			this.am_pm = dial;

			this.listaServicio = servicioService.obtenerServicioporClase(this.editProgramacion.getIdClase());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/** Editar Programacion **/
	public void editarProgramacion() {

		try {

			RequestContext context = RequestContext.getCurrentInstance();

			Programacion pro = this.editProgramacion;
			pro.setIdProgramacion(pro.getIdProgramacion());
			pro.setPrecioPiso1(pro.getPrecioPiso1());
			pro.setPrecioPiso2(pro.getPrecioPiso2());
			pro.setfSalida(pro.getfSalida());
			pro.setfLiquidacion(pro.getfSalida());
			pro.setIdServicio(pro.getIdServicio());
			// for(int i=0 ; i<=this.listaServicio.size();i++){
			// Servicio ser = this.listaServicio.get(i);
			// if(ser.getIdServicio() == pro.getIdServicio()){
			// pro.setOrigen(ser.getOrigen());
			// pro.setDestino(ser.getDestino());
			// }
			// }

			pro.sethSalida(getHora() + ":" + getMinutos() + " " + getAm_pm());

			if (getAm_pm().equals("PM")) {
				Integer h = Integer.parseInt(getHora()) + 12;
				this.hora = h.toString();
			}

			SimpleDateFormat formatoDelTextoOne = new SimpleDateFormat(
					"HH:mm:ss");
			String strFechaOne = getHora() + ":" + getMinutos() + ":00";

			pro.setHora24(formatoDelTextoOne.parse(strFechaOne));
			this.programacionService.actualizarProgramacion(pro);

			FacesUtils.showFacesMessage("Programación actualizada correctamente.",Constante.INFORMACION);
			context.execute("dlgEditar.hide();");
			context.update("sms");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void infoProgramacion(Programacion program) {
		this.programacionPaint = new Programacion();
		this.programacionPaint = program;
	}

	public void obtenerInformacion() 
	{
		System.out.println("obteniendo la info");	
		for (int i = 0; i < this.listaServicio.size(); i++) 
		{
			Servicio ser = this.listaServicio.get(i);
			if (this.servicioPaint.getIdServicio().intValue() == ser.getIdServicio().intValue()) 
			{
				this.servicioPaint.setPrecio1(ser.getPrecio1());
				this.servicioPaint.setPrecio2(ser.getPrecio2());
				this.servicioPaint.setDesDestino(ser.getDesDestino());
				this.servicioPaint.setDesOrigen(ser.getDesOrigen());
				this.servicioPaint.setRutaCompartida(ser.getRutaCompartida());
				this.servicioPaint.setHSalida(ser.getHSalida());
				if (ser.getRutaCompartida().equals("SI")) 
				{
					//si es ruta compartida a demanda no permite crear
					this.servicioPaint.setIntCorre(ser.getIntCorre());
					this.visibleComp = true;
				} else {
					this.visibleComp = false;
				}
				
				
				this.hora = this.servicioPaint.getHSalida().substring(0,2);
				this.minutos = this.servicioPaint.getHSalida().substring(3,5).trim();
				this.am_pm = this.servicioPaint.getHSalida().substring(5).trim().toUpperCase();
				
				break;
			} else {
				this.servicioPaint.setPrecio1(new BigDecimal(0));
				this.servicioPaint.setPrecio2(new BigDecimal(0));
				this.servicioPaint.setDesDestino("");
				this.servicioPaint.setDesOrigen("");
			}
		}
	}

	public void validarFechas() 
	{
		try {
			Boolean valido = Boolean.TRUE;
			RequestContext context = RequestContext.getCurrentInstance();
			context.addCallbackParam("esValido", valido);

			DateFormat df = new SimpleDateFormat("yyyy");
			int anioInicio = Integer.parseInt(df.format(dateInicio));
			int anioFin = Integer.parseInt(df.format(dateFinal));

			if (anioInicio == anioFin) 
			{
				if (dateInicio.before(dateFinal)) 
				{
					generarProgramacion(anioInicio);
				} else {
					if (dateFinal.before(dateInicio)) 
					{
						FacesUtils.showFacesMessage("La fecha inicial debe ser menor a la final.",Constante.INFORMACION);
						context.update("msgNuevo");
					} else {
						generarProgramacionOneDay(anioInicio);
					}
				}
			} else {
				FacesUtils.showFacesMessage("Las fechas deben estar en el mismo anio.",Constante.INFORMACION);
				context.update("msgNuevo");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.servicioPaint = new Servicio();
		
	}

	public void listarServicios() {

		try {
			//listra solo demanda: TipoFrecuencia 1: frecuente 2: demanda
			
			this.listaServicio = servicioService.obtenerServicioporClaseDemanda(this.servicioPaint.getIdClase());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void generarProgramacion(int anio) {

		RequestContext context = RequestContext.getCurrentInstance();
		boolean _pase = false;
		try {
			DateFormat dfMes = new SimpleDateFormat("MM");
			DateFormat dfDia = new SimpleDateFormat("dd");

			int mes1 = Integer.parseInt(dfMes.format(dateInicio));
			int mes2 = Integer.parseInt(dfMes.format(dateFinal));
			int dia1 = Integer.parseInt(dfDia.format(dateInicio));
			int dia2 = Integer.parseInt(dfDia.format(dateFinal));
			Programacion program = new Programacion();
			program.setUsuarioRegistro(this.usuarioLogin.getIdusuario());
			program.setFechaRegistro(new Date());
			program.setIdBus(0);
			program.setIdServicio(servicioPaint.getIdServicio());
			program.setPrecioPiso1(this.servicioPaint.getPrecio1());
			program.setPrecioPiso2(this.servicioPaint.getPrecio2());
			program.sethSalida(getHora() + ":" + getMinutos() + " "+ getAm_pm());
			program.setPrecioPiso1(this.servicioPaint.getPrecio1());
			program.setPrecioPiso2(this.servicioPaint.getPrecio2());
			if (getAm_pm().equals("PM")) {
				Integer h = Integer.parseInt(getHora()) + 12;
				this.hora = h.toString();
			}
			SimpleDateFormat formatoDelTextoOne = new SimpleDateFormat("HH:mm:ss");
			String strFechaOne = getHora() + ":" + getMinutos() + ":00";
			program.setHora24(formatoDelTextoOne.parse(strFechaOne));
			program.setEstado("0");
			program.setEstEmbarque("0");
			program.setEstDesembarque("0");
			program.setFcreacion(new Date());
			SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
			
			
			if (this.servicioPaint.getRutaCompartida().equals("SI")) {
				_pase=true; 
			}
			
			
			if (mes1 == mes2) 
			{
				for (int i = dia1; i <= dia2; i++) {
					String strFecha = anio + "-" + mes1 + "-" + i;
					Date fecha = null;
					fecha = formatoDelTexto.parse(strFecha);
					program.setfSalida(fecha);
					program.setfLiquidacion(fecha);
					program.setfSalida(fecha);
					program.setIntCorreEnlace(0);
					program.setEstManifiesto(0);
					programacionService.crearProgramacion(program);
					
				}
				
				
				if(_pase)
				{
					for(int i2 = dia1; i2 <= dia2; i2++)
					{
						String strFecha = anio + "-" + mes1 + "-" + i2;
						Date fecha = null;
						fecha = formatoDelTexto.parse(strFecha);
						program.setfSalida(fecha);
						program.setfLiquidacion(fecha);
						program.setfSalida(fecha);
						generacionProgramacionHijos(program);
					}	
				}
				
				
				
				FacesUtils.showFacesMessage("Programación registrada correctamente.",Constante.INFORMACION);
				context.execute("dlgNuevo.hide();");
				context.update("sms");
				this.programacion = new Programacion();
				this.programFilterDemanda = new Programacion();
				_pase=false;
			} else {
				for (int j = mes1; j <= mes2; j++) {
					int days = diasDelMes(j, anio);
					if (j == mes1) {
						for (int x = dia1; x <= days; x++) {
							String strFecha = anio + "-" + j + "-" + x;
							Date fecha = null;
							fecha = formatoDelTexto.parse(strFecha);
							program.setfSalida(fecha);
							program.setfLiquidacion(fecha);
							program.setfSalida(fecha);
							programacionService.crearProgramacion(program);
							generacionProgramacionHijos(program);
						}
						
						if(_pase){
							for(int x2 = dia1; x2 <= dia2; x2++){
								String strFecha = anio + "-" + mes1 + "-" + x2;
								Date fecha = null;
								fecha = formatoDelTexto.parse(strFecha);
								program.setfSalida(fecha);
								program.setfLiquidacion(fecha);
								program.setfSalida(fecha);
								generacionProgramacionHijos(program);
							}	
						}
					} else if (j == mes2) {
						for (int y = 1; y <= dia2; y++) {
							String strFecha = anio + "-" + j + "-" + y;
							Date fecha = null;
							fecha = formatoDelTexto.parse(strFecha);
							program.setfSalida(fecha);
							program.setfLiquidacion(fecha);
							program.setfSalida(fecha);
							programacionService.crearProgramacion(program);
						}
						
						if(_pase){
							for(int y2 = dia1; y2 <= dia2; y2++){
								String strFecha = anio + "-" + j + "-" + y2;
								Date fecha = null;
								fecha = formatoDelTexto.parse(strFecha);
								program.setfSalida(fecha);
								program.setfLiquidacion(fecha);
								program.setfSalida(fecha);
								generacionProgramacionHijos(program);
							}	
						}
					} else {
						for (int k = 1; k <= days; k++) {
							String strFecha = anio + "-" + j + "-" + k;
							Date fecha = null;
							fecha = formatoDelTexto.parse(strFecha);
							program.setfSalida(fecha);
							program.setfLiquidacion(fecha);
							program.setfSalida(fecha);
							programacionService.crearProgramacion(program);
						}
						
						if(_pase){
							for(int k2 = dia1; k2 <= dia2; k2++){
								String strFecha = anio + "-" + j + "-" + k2;
								Date fecha = null;
								fecha = formatoDelTexto.parse(strFecha);
								program.setfSalida(fecha);
								program.setfLiquidacion(fecha);
								program.setfSalida(fecha);
								generacionProgramacionHijos(program);
							}	
						}
					}

				}
				listarProgDemanda();
				
				FacesUtils.showFacesMessage("Programación registrada correctamente.",Constante.INFORMACION);
				context.execute("dlgNuevo.hide();");
				context.update("sms");
				this.dateInicio = new Date();
				this.programacion = new Programacion();
				this.programFilterDemanda = new Programacion();
				_pase = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void generacionProgramacionHijos(Programacion programHijo){
		try {
			System.out.println("ENTRO EN PROGRAMACION HIJOS ");
			List<Servicio> listServHijos = new ArrayList<>();
			if (this.servicioPaint.getRutaCompartida().equals("SI")) {
				System.out.println("ENTRO DENTRO DEL IF");
				// obtenemos los servicios hijos
				listServHijos = this.servicioService.obtenerServiciosHijos(this.servicioPaint.getIdServicio());
//				programHijo = program;
				for (int i = 0; i < listServHijos.size(); i++) {
					Servicio sv = listServHijos.get(i);
					programHijo.setIdServicio(sv.getIdServicio());
					programHijo.setPrecioPiso1(this.servicioPaint.getTramo2Precio1());
					programHijo.setPrecioPiso2(this.servicioPaint.getTramo2Precio2());
					if (getAm_pm_Tramo2().equals("PM")) {
						Integer horaTemp = Integer.parseInt(getHora_Tramo2());
						Integer h2 = horaTemp + 12;
						this.hora_Tramo2 = h2.toString();
					}
					programHijo.sethSalida(this.hora_Tramo2 + ":" + getMinutos_Tramo2() + " "+ getAm_pm_Tramo2());
					SimpleDateFormat formatoDelTexto2 = new SimpleDateFormat("HH:mm:ss");
					String strFecha2 = getHora_Tramo2() + ":" + getMinutos_Tramo2() + ":00";
					programHijo.setHora24(formatoDelTexto2.parse(strFecha2));
					
					System.out.println("1 " +programHijo.getIdServicio());
					System.out.println("2 " +programHijo.getIdBus());
					System.out.println("3 " +programHijo.getfLiquidacion());
					System.out.println("4 " +programHijo.getfSalida());
					System.out.println("5 " +programHijo.getPrecioPiso1());
					System.out.println("6 " +programHijo.getPrecioPiso2());
					System.out.println("7 " +programHijo.gethSalida());
					System.out.println("8 " +programHijo.getFcreacion());
					System.out.println("9 " +programHijo.getHora24());
					System.out.println("10 " +programHijo.getEstado());
					System.out.println("11 " +programHijo.getEstEmbarque());
					System.out.println("12 " +programHijo.getEstDesembarque());
					System.out.println("13 " +programHijo.getEstManifiesto());
					System.out.println("14 " +programHijo.getUsuarioRegistro());
					System.out.println("15 " +programHijo.getFechaRegistro());
					
					programacionService.crearProgramacion(programHijo);
				}

		}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void generarProgramacionOneDay(int anio) {
		try {
			List<Servicio> listServHijos = new ArrayList<>();
			RequestContext context = RequestContext.getCurrentInstance();

			Programacion program = new Programacion();
			program.setUsuarioRegistro(this.usuarioLogin.getIdusuario());
			program.setFechaRegistro(new Date());
			program.setIdBus(0);
			program.setIdServicio(this.servicioPaint.getIdServicio());
			program.setfSalida(this.dateInicio);
			program.sethSalida(getHora() + ":" + getMinutos() + " "+ getAm_pm());
			program.setPrecioPiso1(this.servicioPaint.getPrecio1());
			program.setPrecioPiso2(this.servicioPaint.getPrecio2());
			
			
			
			
			
			if (this.am_pm.equals("PM")) 
			{
				Integer h = Integer.parseInt(getHora()) + 12;
				this.hora = h.toString();
			}
			
			
			/*
			this.hora = this.servicioPaint.getHSalida().substring(0,2);
			this.minutos = this.servicioPaint.getHSalida().substring(3,5).trim();
			
			if (this.servicioPaint.getHSalida().contains("PM")) 
			{
				Integer h = Integer.parseInt(getHora()) + 12;
				this.hora = h.toString();
			}
			*/
			
			SimpleDateFormat formatoDelTexto = new SimpleDateFormat("HH:mm:ss");
			String strFecha = getHora() + ":" + getMinutos() + ":00";
			program.setHora24(formatoDelTexto.parse(strFecha));
			program.setIntCorreEnlace(0);
			program.setEstado("0");
			program.setEstEmbarque("0");
			program.setEstDesembarque("0");
			program.setEstManifiesto(0);
			program.setfLiquidacion(this.dateInicio);
			program.setfSalida(this.dateInicio);
			program.setFcreacion(new Date());
			programacionService.crearProgramacion(program);

			// ---------------------- verificamos que los servicios tengan ruta compartida o no ------------
			if (this.servicioPaint.getRutaCompartida().equals("SI")) {
				// obtenemos los servicios hijos
				listServHijos = this.servicioService.obtenerServiciosHijos(this.servicioPaint.getIdServicio());
				Programacion programHijo = new Programacion();
				programHijo = program;
				for (int i = 0; i < listServHijos.size(); i++) {
					Servicio sv = listServHijos.get(i);
					programHijo.setIdServicio(sv.getIdServicio());
					programHijo.setPrecioPiso1(this.servicioPaint.getTramo2Precio1());
					programHijo.setPrecioPiso2(this.servicioPaint.getTramo2Precio2());
					programHijo.sethSalida(getHora_Tramo2() + ":" + getMinutos_Tramo2() + " "+ getAm_pm_Tramo2());
					if (getAm_pm_Tramo2().equals("PM")) {
						Integer h2 = Integer.parseInt(getHora_Tramo2()) + 12;
						this.hora_Tramo2 = h2.toString();
					}
					SimpleDateFormat formatoDelTexto2 = new SimpleDateFormat("HH:mm:ss");
					String strFecha2 = getHora_Tramo2() + ":" + getMinutos_Tramo2() + ":00";
					programHijo.setHora24(formatoDelTexto2.parse(strFecha2));
					programacionService.crearProgramacion(programHijo);
				}

			}
			listarProgDemanda();

			FacesUtils.showFacesMessage("Programación registrada correctamente.",Constante.INFORMACION);
			context.execute("dlgNuevo.hide();");
			context.update("sms");
			this.dateInicio = new Date();
			this.programacion = new Programacion();
			this.programFilterDemanda = new Programacion();
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public int diasDelMes(int mes, int anio) {
		int cantMes = 0;
		switch (mes) {
		case 1: // Enero
		case 3: // Marzo
		case 5: // Mayo
		case 7: // Julio
		case 8: // Agosto
		case 10: // Octubre
		case 12: // Diciembre
			return cantMes = 31;
		case 4: // Abril
		case 6: // Junio
		case 9: // Septiembre
		case 11: // Noviembre
			return cantMes = 30;
		case 2: // Febrero
			if (((anio % 100 == 0) && (anio % 400 == 0))
					|| ((anio % 100 != 0) && (anio % 4 == 0)))
				return cantMes = 29; // Anio Bisiesto
			else
				return cantMes = 28;
		}
		return cantMes;
	}

	public void guardarProgamacion() {
		Boolean valido = Boolean.TRUE;
		RequestContext context = RequestContext.getCurrentInstance();
		context.addCallbackParam("esValido", valido);

		try {
			this.programacionService.crearProgramacion(programacion);
			log.setAccion("INSERT");
			log.setDescripcion("Se inserta la programación: "
					+ programacion.getIdServicio());
			logmb.insertarLog(log);
			FacesUtils.showFacesMessage("Programación registrada correctamente.",Constante.INFORMACION);
			context.update("sms");

		} catch (Exception e) {
			e.printStackTrace();
		}

		this.programacion = new Programacion();

	}

	public void editarServicio(Servicio servi) {
		this.servicioPaint = servi;
	}

	public void buscarServicio() {
		try {

			this.listaServicio = servicioService
					.serviciosFilter(programFilterDemanda);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void nuevaProgramacion() {
		this.programacion = new Programacion();
	}

	public void listarDestinos() {
		try {
			this.listaDestino = destinoServices
					.obtenerDestino(this.programFilterDemanda.getOrigen());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** changed by juan */
	public void listarDestinosxOri() {
		try {
			System.out.println("entro");
			this.listaDestino = new ArrayList<Destino>();
			this.listaDestino = destinoServices.obtenerDestino(this.progFilter
					.getOrigen());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void listarProgFrecuente() {
		try {
			this.listaProgramacion = new ArrayList<Programacion>();
			this.listaProgramacion = this.programacionService.listarProgFrecuente(this.progFilter);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void listarProgDemanda() {

		try {

			this.listaProgramacionDemanda = new ArrayList<Programacion>();
			this.listaProgramacionDemanda = this.programacionService.listarProgDemanda(this.programFilterDemanda);
			
			this.programFilterDemanda.setFecInicio(new Date());
			this.programFilterDemanda.setFecFin(new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void imprimirProgFrec() throws Exception {
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// **set an get

	public MenuServices getMenuServices() {
		return menuServices;
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

	public boolean isVisibleComp() {
		return visibleComp;
	}

	public void setVisibleComp(boolean visibleComp) {
		this.visibleComp = visibleComp;
	}

	public Programacion getProgFilter() {
		return progFilter;
	}

	public void setProgFilter(Programacion progFilter) {
		this.progFilter = progFilter;
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

	public List<Programacion> getListaProgramacion() {
		return listaProgramacion;
	}

	public void setListaProgramacion(List<Programacion> listaProgramacion) {
		this.listaProgramacion = listaProgramacion;
	}

	public List<Programacion> getListaProgramacionFilter() {
		return listaProgramacionFilter;
	}

	public void setListaProgramacionFilter(
			List<Programacion> listaProgramacionFilter) {
		this.listaProgramacionFilter = listaProgramacionFilter;
	}

	public List<Excepcion> getListaExcepciones() {
		return listaExcepciones;
	}

	public void setListaExcepciones(List<Excepcion> listaExcepciones) {
		this.listaExcepciones = listaExcepciones;
	}

	public ExcepcionService getExcepcionService() {
		return excepcionService;
	}

	public void setExcepcionService(ExcepcionService excepcionService) {
		this.excepcionService = excepcionService;
	}

	public Servicio getServicioPaint() {
		return servicioPaint;
	}

	public void setServicioPaint(Servicio servicioPaint) {
		this.servicioPaint = servicioPaint;
	}

	public List<Servicio> getListaFiltroServicio() {
		return listaFiltroServicio;
	}

	public void setListaFiltroServicio(List<Servicio> listaFiltroServicio) {
		this.listaFiltroServicio = listaFiltroServicio;
	}

	public Date getDateInicio() {
		return dateInicio;
	}

	public void setDateInicio(Date dateInicio) {
		this.dateInicio = dateInicio;
	}

	public Date getDateFinal() {
		return dateFinal;
	}

	public void setDateFinal(Date dateFinal) {
		this.dateFinal = dateFinal;
	}

	public Programacion getProgramFilterDemanda() {
		return programFilterDemanda;
	}

	public void setProgramFilterDemanda(Programacion programFilterDemanda) {
		this.programFilterDemanda = programFilterDemanda;
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

	public List<Servicio> getListaServicio() {
		return listaServicio;
	}

	public void setListaServicio(List<Servicio> listaServicio) {
		this.listaServicio = listaServicio;
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

	public ServicioService getServicioService() {
		return servicioService;
	}

	public void setServicioService(ServicioService servicioService) {
		this.servicioService = servicioService;
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

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getMinutos() {
		return minutos;
	}

	public void setMinutos(String minutos) {
		this.minutos = minutos;
	}

	public String getAm_pm() {
		return am_pm;
	}

	public void setAm_pm(String am_pm) {
		this.am_pm = am_pm;
	}

	public Usuario getUsuarioLogin() {
		return usuarioLogin;
	}

	public void setUsuarioLogin(Usuario usuarioLogin) {
		this.usuarioLogin = usuarioLogin;
	}

	public List<ClaseServicio> getListaClases() {
		return listaClases;
	}

	public void setListaClases(List<ClaseServicio> listaClases) {
		this.listaClases = listaClases;
	}

	public Programacion getProgramacionPaint() {
		return programacionPaint;
	}

	public void setProgramacionPaint(Programacion programacionPaint) {
		this.programacionPaint = programacionPaint;
	}

	public List<Programacion> getListaProgramacionDemanda() {
		return listaProgramacionDemanda;
	}

	public void setListaProgramacionDemanda(
			List<Programacion> listaProgramacionDemanda) {
		this.listaProgramacionDemanda = listaProgramacionDemanda;
	}

	public Programacion getEditProgramacion() {
		return editProgramacion;
	}

	public void setEditProgramacion(Programacion editProgramacion) {
		this.editProgramacion = editProgramacion;
	}

}
