package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRParameter;

import org.primefaces.context.RequestContext;

import pe.gob.mtc.wshr.Errores;
import pe.gob.mtc.wshr.ResultViaje;
import pe.gob.mtc.wshr.Seguridad;
import pe.gob.mtc.wshr.Viaje;
import pe.gob.mtc.wshr.WSServiciosHR;
import pe.gob.mtc.wshr.WSServiciosHRLocator;
import pe.gob.mtc.wshr.WSServiciosHRSoap;
import pe.gob.mtc.wshr.WSServiciosHRSoapStub;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Asiento;
import com.certicom.ittsa.domain.AsientoVenta;
import com.certicom.ittsa.domain.ClaseServicio;
import com.certicom.ittsa.domain.Destino;
import com.certicom.ittsa.domain.HistorialPasajero;
import com.certicom.ittsa.domain.Persona;
import com.certicom.ittsa.domain.PersonaTrama;
import com.certicom.ittsa.domain.Programacion;
import com.certicom.ittsa.domain.Servicio;
import com.certicom.ittsa.domain.TipoAsiento;
import com.certicom.ittsa.domain.Tripulacion;
import com.certicom.ittsa.domain.Usuario;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.AsientoServices;
import com.certicom.ittsa.services.AsientoVentaServices;
import com.certicom.ittsa.services.ClaseServicioServices;
import com.certicom.ittsa.services.DestinoServices;
import com.certicom.ittsa.services.HistorialPasajeroService;
import com.certicom.ittsa.services.ParametroServices;
import com.certicom.ittsa.services.PersonaServices;
import com.certicom.ittsa.services.PersonaTramaServices;
import com.certicom.ittsa.services.ProgramacionService;
import com.certicom.ittsa.services.ServicioService;
import com.certicom.ittsa.services.TipoAsientoService;
import com.certicom.ittsa.services.TripulacionServices;
import com.certicom.ittsa.services.UmbralCapturaService;
import com.certicom.ittsa.services.UmbralDeteccionService;
import com.certicom.ittsa.services.UmbralVerificacionService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.ExportarArchivo;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

import org.apache.axis.encoding.Base64;

@ManagedBean(name = "manifPasajeroMB")
@ViewScoped
public class ManifPasajeroMB extends GenericBeans implements Serializable {

	@ManagedProperty(value = "#{loginMB.usuario}")
	private Usuario usuarioLogin;

	@ManagedProperty(value = "#{loginMB.desOficina}")
	private String oficinaUser;

	@ManagedProperty(value = "#{loginMB.agencia}")
	private Agencia agenciaUser;

	public List<Programacion> listaProgramacion;
	public List<Programacion> listaProgramacionFilter;
	public List<Destino> listaDestinos;
	public List<Agencia> listaOrigenes;
	private List<AsientoVenta> listaManifiesto;
	private List<AsientoVenta> listaFiltroManifiesto;

	private List<AsientoVenta> listaManifiestoNoEmbarcados;
	private List<AsientoVenta> listaManifiestoNoEmbarcadosFiltro;

	public Programacion programacionSelected;
	public Tripulacion tripulacion_prog;

	public Integer f_idOrigen;
	public Integer f_idDestino;
	public Boolean activarManifiesto;

	public Integer as_embarcados = 0;
	public Integer as_capacidad = 0;
	public Integer as_alibres = 0;
	public Integer as_noembarcados = 0;
	public Integer as_comprados = 0;
	public Integer as_tripulacion = 0;

	public String p_dni = "";
	public String p_pasajero = "";
	public Integer p_asiento;

	private String tipoEmbarque;
	private boolean actEmbarDni;
	private boolean actEmbarBiometrico;
	private boolean actProcBio;

	/** BIOMETRICO */
	private boolean habConfig;
	private int umbralCaptura;
	private int umbralDeteccion;
	private int umbralVerificacion;
	private int minUmbralVerificacion;
	private int totalHuellas = 0;
	private String cadenaHuellas = "";
	private List<AsientoVenta> listaVentas;

	private UmbralCapturaService umbralCapturaService;
	private UmbralDeteccionService umbralDeteccionService;
	private UmbralVerificacionService umbralVerificacionService;
	private PersonaTramaServices personaTramaService;
	/* FIN BIMETRICO */

	private ProgramacionService programacionService;
	private DestinoServices destinoServices;
	private AsientoVentaServices asientoVentaServices;
	private PersonaServices personaService;
	private TripulacionServices tripulacionServices;

	private ServicioService servicioService;
	private AsientoServices asientoService;
	private ClaseServicioServices claseServicioServices;
	private TipoAsientoService tipoAsientoService;

	private AgenciaService agenciaService;
	
	private ClaseServicio busClase;
	private TipoAsiento tipoAsientop1;
	private TipoAsiento tipoAsientop2;
	private Integer nroColumnasPisoUno;
	private Integer nroColumnasPisoDos;
	private Integer nroFilas;
	private Integer anchoBusDos;
	private List<Asiento> listaAsientosPisoUno;
	private List<Asiento> listaAsientosPisoDos;

	// MODIFICACION jtarazona 05-08-2014
	private List<Persona> listaPersonas;
	private Persona persona;
	private Persona personaSelec;
	private int totalHuellasBD;
	private AsientoVenta asientoV;
	private List<AsientoVenta> listaVtas;
	private String huella;
	private String mensajeVerificacion;
	private ArrayList<PersonaTrama> listaTemporalPersonaTrama;
	private Integer index = 0;
	private Integer embarcados;
	private Integer noEmbarcados;
	private Integer libres;
	private Persona pasajero;
	private String nroAsientoSelec;
	private Boolean estadoApplet;
	private String mensajeCaptura;
	private String tramaHuella;
	private String imagenHuella;
	private Boolean estadoAppletCaptura;
	private PersonaTramaServices personaTramaServices;
	private String destinoBus;
	private HistorialPasajeroService historialPasajeroService;
	private Boolean frecuencia;
	private Boolean nuevo;
	private ParametroServices parametroServices;
	private String nroCroq;
	private String dniCroq;

	private Integer numeroImagen;
	private String nombreImagen = "";
	private Date fecha;

	@PostConstruct
	public void inicia() {

		this.activarManifiesto = Boolean.FALSE;

		this.programacionSelected = new Programacion();
		this.tripulacion_prog = new Tripulacion();

		this.programacionService = new ProgramacionService();
		this.destinoServices = new DestinoServices();
		this.asientoVentaServices = new AsientoVentaServices();
		this.personaService = new PersonaServices();
		this.tripulacionServices = new TripulacionServices();
		this.personaTramaService = new PersonaTramaServices();
		this.listaManifiestoNoEmbarcados = new ArrayList<>();

		this.servicioService = new ServicioService();
		this.asientoService = new AsientoServices();
		this.claseServicioServices = new ClaseServicioServices();
		this.tipoAsientoService = new TipoAsientoService();
		this.personaTramaServices = new PersonaTramaServices();
		this.parametroServices = new ParametroServices();
		
		this.agenciaService = new AgenciaService();

		// MODIFICACION jtarazona 05-08-2014
		this.listaPersonas = new ArrayList<>();
		this.persona = new Persona();
		this.personaSelec = new Persona();
		this.asientoV = new AsientoVenta();

		this.listaProgramacion = new ArrayList<Programacion>();
		this.actProcBio = Boolean.FALSE;

		// this.tipoEmbarque = "B";
		// this.actEmbarDni = Boolean.FALSE;
		// this.actEmbarBiometrico = Boolean.TRUE;

		this.tipoEmbarque = "D";
		this.actEmbarDni = Boolean.TRUE;
		this.actEmbarBiometrico = Boolean.FALSE;

		this.estadoApplet = Boolean.TRUE;
		this.estadoAppletCaptura = Boolean.FALSE;

		this.frecuencia = Boolean.FALSE;
		this.nuevo = Boolean.FALSE;

		this.umbralCapturaService = new UmbralCapturaService();
		this.umbralDeteccionService = new UmbralDeteccionService();
		this.umbralVerificacionService = new UmbralVerificacionService();

		try {
			fecha= new Date();
			
			this.umbralCaptura = this.umbralCapturaService.findByEstado(true)
					.getId_captura();
			this.umbralDeteccion = this.umbralDeteccionService.findByEstado(
					true).getId_deteccion();
			this.umbralVerificacion = this.umbralVerificacionService
					.findByEstado(true).getId_verificacion();
			this.minUmbralVerificacion = this.umbralVerificacionService
					.findMinUmbral();

			this.listaOrigenes = agenciaService.listaAgenciasActivas();
//			this.listaDestinos = this.destinoServices
//					.obtenerDestino(getUsuarioLogin().getIdagencia());
			this.historialPasajeroService = new HistorialPasajeroService();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void listarDestinosxOri() {
		try {
			System.out.println("entro");
			this.listaDestinos = new ArrayList<Destino>();
			this.listaDestinos = destinoServices.obtenerDestino(getF_idOrigen());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void limpiarValores() {
		// cuando trabaje con verificacion Biometrica
		// this.p_pasajero="";
		// this.p_asiento=null;
		as_embarcados = 0;
		as_capacidad = 0;
		as_alibres = 0;
		as_noembarcados = 0;
		as_comprados = 0;
		as_tripulacion = 0;
		this.listaVentas = new ArrayList<>();
		this.listaTemporalPersonaTrama = new ArrayList<>();
		this.totalHuellas = 0;
		

	}

	public void registrarHojaRuta() {
		try {
			WSServiciosHR w = new WSServiciosHRLocator();
			WSServiciosHRSoap soap = new WSServiciosHRSoapStub(new URL(
					w.getWSServiciosHRSoapAddress()), w);

			Seguridad seguridad = new Seguridad();
			seguridad.setUsuario(Constante.WEBSERVICE_USUARIO);
			seguridad.setClave(Constante.WEBSERVICE_CLAVE);
			seguridad.setPartida(Constante.WEBSERVICE_PARTIDA);
			seguridad.setRuc(Constante.WEBSERVICE_RUC);

			Viaje oViaje = new Viaje();
			// oViaje.setSeguridad(seguridad);
			// oViaje.setNroRuta("0007");
			// oViaje.setTerSalida(82);
			// oViaje.setTerLlegada(2803);
			// oViaje.setFecSalida("15/08/2014");
			// oViaje.setHorSalida("09:00");
			// oViaje.setFecEstLlegada("15/08/2014");
			// oViaje.setHorEstLlegada("17:00");

			oViaje.setSeguridad(seguridad);
			oViaje.setNroRuta(this.programacionSelected.getIdRutaDestino());
			oViaje.setTerSalida(this.programacionSelected.getTerminalSalida());
			oViaje.setTerLlegada(this.programacionSelected.getTerminalLLegada());
			oViaje.setFecSalida("15/08/2014");
			oViaje.setHorSalida("09:00");
			oViaje.setFecEstLlegada("15/08/2014");
			oViaje.setHorEstLlegada("17:00");

			ResultViaje resViaje = soap.setViaje(oViaje);
			if (resViaje.is_return()) {
				this.programacionService.actualizarHojaRuta(resViaje.getCode(),
						this.programacionSelected.getIdProgramacion());

				FacesUtils.showFacesMessage("Hoja de ruta registrada correctamente.",	Constante.INFORMACION);
			} else {
				for (int i = 0; i < resViaje.getErrores().length; i++) {
					Errores err = resViaje.getErrores()[i];
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR, err
									.getCode() + " - " + err.getInfo(), ""));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void buscarProgramaciones() {
		try {
			String fechaactual = new SimpleDateFormat("dd-MM-yyyy")
					.format(new Date());
			// listando programacion:
			this.listaProgramacion.clear();
			this.listaProgramacion = this.programacionService
					.buscarPorOrigenDestinoAndFsalida(getUsuarioLogin()
							.getIdagencia(), this.f_idDestino, fechaactual);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (Exception ef) {
			ef.printStackTrace();
		}
	}

	public void consultarProgramacion() {
		try {
			this.listaProgramacion = this.programacionService
					.listarProgxSalida(getUsuarioLogin().getIdagencia(),
							this.f_idDestino,this.fecha);
			// System.out.println("*********** f_idDestino "+this.f_idDestino);

			for (Destino destino : this.listaDestinos) {
				if (destino != null) {
					if (destino.getDestino() == this.f_idDestino) {
						// System.out.println("****** Destino es "+destino.getDesDestino());
						this.destinoBus = destino.getDesDestino();
					}
				}
			}
			// hallando la descripcion del destino
			// for (int i = 0; i < array.length; i++) {
			// if(this.f_idDestino == ){
			//
			// }
			// }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void selectProgramacion(Programacion pr) {
		// System.out.println("****** ingresa a seleccionar programacion");
		RequestContext context = RequestContext.getCurrentInstance();
		// System.out.println("******* SELECT PROGRAMACION "+pr.getIdProgramacion());
		this.programacionSelected = pr;
		limpiarValores();
		this.listaManifiesto = new ArrayList<>();
		try {
			this.listaManifiesto = this.asientoVentaServices.listarManifiestoxProg(pr.getIdProgramacion());
			for (AsientoVenta av : this.listaManifiesto) {
				if (av != null) {
					Persona p = this.personaService.findByNroDocumento(av.getDocumentoPersona());
					if (p != null) {
						av.setNombres(p.getNombres());
						av.setaPaterno(p.getAPaterno());
						av.setaMaterno(p.getAMaterno());
						av.setDocumentoPersona(p.getDni());
						av.setSexo(p.getSexo());
						// av.setEdadPersona(operacionCalculoEdad(p.getFnac()));
						// System.out.println("***** as_comrados "+this.as_comprados);
						this.as_comprados++;
						if (av.getAbordo() == true) {
							this.as_embarcados++;
							// System.out.println("** as_embarcados "+this.as_embarcados);
						} else {
							this.as_noembarcados++;
							// System.out.println("**** as_noEmbarcados "+this.as_noembarcados);
						}

					} else {
						av.setPersona(new Persona());
						this.as_alibres++;
					}
				}
			}

			// datos del manifiesto
			// this.as_capacidad = this.listaManifiesto.size();
			// this.tripulacion_prog =
			// this.tripulacionServices.listarTripulacionxProg(pr.getIdProgramacion());

			this.as_capacidad = this.listaManifiesto.size();
			// System.out.println(" ********************** pr.getIdProgramacion()"
			// +pr.getIdProgramacion());
			this.tripulacion_prog = this.tripulacionServices
					.listarTripulacionxProg(pr.getIdProgramacion());

			if (this.tripulacion_prog != null) {
				// System.out.println("********** tripulacion_prog != NULL");
				if (this.tripulacion_prog.getDnipiloto() != null) {
					this.as_tripulacion += 1;
				}
				if (this.tripulacion_prog.getDnicopiloto() != null) {
					this.as_tripulacion += 1;
				}
				if (this.tripulacion_prog.getDniterramoza1() != null) {
					this.as_tripulacion += 1;
				}
				if (this.tripulacion_prog.getDniterramoza2() != null) {
					this.as_tripulacion += 1;
				}
			}

			this.activarManifiesto = Boolean.TRUE;
		} catch (Exception e) {
			e.printStackTrace();
		}

		getHuellasPersonas(); // para la verificacion

		// pintarBus();
	}

	public Integer operacionCalculoEdad(Date fechaNac) {
		Integer edad = 0;
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(fechaNac);
		Integer anioNac = calendario.get(Calendar.YEAR);

		Calendar calendario2 = Calendar.getInstance();
		calendario2.setTime(new Date());
		Integer anioAct = calendario2.get(Calendar.YEAR);
		edad = anioAct - anioNac;
		return edad;
	}

	public void arribarPasajero() {

		// System.out.println("***** ingresa a arribar pasajero");

		RequestContext context = RequestContext.getCurrentInstance();
		boolean existe = false;

		try {
			// System.out.println("entro como te gusta");
			// agregar el metodo para recuperar el nombre del metodo

			AsientoVenta av = this.asientoVentaServices.buscarPasajeroxDni(this.programacionSelected.getIdProgramacion(), this.p_dni);
			if (av != null) {
				Persona p = this.personaService.findByNroDocumento(this.p_dni);
				if (p != null) {
					this.p_pasajero = p.getNombres() + " " + p.getAPaterno()+ " " + p.getAMaterno();
					List<HistorialPasajero> viajes = this.historialPasajeroService.getNumberTravel(p.getDni());
					if (viajes.size() > (Integer.parseInt(this.parametroServices.findParametro_byNombre("PASAJERO_FRECUENTE")))) {
						System.out.println("******** PASAJERO FRECUENTE");
						this.nuevo = false;
						this.frecuencia = true;
					} else {
						this.frecuencia = false;
						this.nuevo = true;
						System.out.println("***** PASAJERO NUEVO");
					}

				}
				this.p_asiento = Integer.parseInt(av.getNumero());
				if (av.getNumeroImagen() != null) {
					av.setNumeroImagen(av.getNumeroImagen() + 1);
				} else {
					av.setNumeroImagen(1);
				}

				this.nombreImagen = "1" + String.valueOf(this.programacionSelected.getIdProgramacion())+ av.getDocumentoPersona() + String.valueOf(av.getNumeroImagen());
				System.out.println("************ NOMBRE PARA IMAGEN EN XAMPP ***"+ this.nombreImagen);
				av.setNombreImagen(this.nombreImagen);				
//				context.addCallbackParam("nombreImagenMB", this.nombreImagen);

				av.setAbordo(Boolean.TRUE);
				// System.out.println("*** actualizando asiento venta --abordo");
				this.asientoVentaServices.actualizarAbordoAsVenta(av);
				// System.out.println("***** arribar pasajero---> seleccionar programacion");
				selectProgramacion(this.programacionSelected);
				// System.out.println("*** arribar pasajero---> muestra applet");
				mostrarAppletCaptura();
				// System.out.println("*** arribar pasajero---> context.execute --> captura");
				context.execute("captura('"+this.nombreImagen+"')");
			} else {
				FacesUtils.showFacesMessage("El número de documento "+ this.p_dni+ " no figura en el manifiesto, consulte nuevamente.", Constante.ERROR);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void imprimirManifiestoPDF() {
		try {
			ServletContext servletContext = (ServletContext) (FacesContext
					.getCurrentInstance().getExternalContext().getContext());

			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			String fecha = formato.format(new Date());

			Integer total = this.listaManifiesto.size();
			//
			String p_logo = ExportarArchivo
					.getPath("/resources/img/LogoIttsaBus.png");
			System.out.println("logo ruta:" + p_logo);

			Map<String, Object> input = new HashMap<String, Object>();
			input.put("P_LOGO", p_logo);
			input.put("P_FECHA", fecha);
			input.put("P_TOTAL", total.toString());

			if (this.tripulacion_prog != null) {
				input.put("P_BUS_NRO", this.tripulacion_prog.getNumeroBus());
				input.put("P_BUS_PLACA", this.tripulacion_prog.getPlacabus());
				input.put("P_BUS_MARCA", this.tripulacion_prog.getMarcaBus());
				input.put("P_PILOTO", this.tripulacion_prog.getPiloto());
				input.put("P_PILOTO_BREV", this.tripulacion_prog.getLicPiloto());
				input.put("P_COPILOTO", this.tripulacion_prog.getCopiloto());
				input.put("P_COPILOTO_BREV", this.tripulacion_prog.getLicCopiloto());
				input.put("P_TERRAMOZA1", this.tripulacion_prog.getTerramoza1());
				input.put("P_TERRAMOZA1_DNI", this.tripulacion_prog.getDniterramoza1());
				input.put("P_TERRAMOZA2", this.tripulacion_prog.getTerramoza2());
				input.put("P_TERRAMOZA2_DNI", this.tripulacion_prog.getDniterramoza2());
				input.put("P_FSALIDA", this.programacionSelected.getfSalida());
				input.put("P_ORIGEN", this.agenciaUser.getDescripcion());
				input.put("P_DESTINO", this.programacionSelected.getDesDestino());
			}

			input.put(JRParameter.REPORT_LOCALE, new Locale("es"));
			//
			String path = ExportarArchivo
					.getPath("/resources/reports/jxrml/rptManPasajeros.jasper");
			HttpServletResponse response = (HttpServletResponse) FacesContext
					.getCurrentInstance().getExternalContext().getResponse();

			byte[] bytes;
			bytes = ExportarArchivo.exportPdf(path, input, this.listaManifiesto);
			ExportarArchivo.executePdf(bytes, "ManifiestoPasajeros.pdf");
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setearTipoEmbarque() {
		if (this.tipoEmbarque.equals("D")) {
			this.actEmbarDni = Boolean.TRUE;
			this.actEmbarBiometrico = Boolean.FALSE;

		} else if (this.tipoEmbarque.equals("B")) {
			this.actEmbarDni = Boolean.FALSE;
			this.actEmbarBiometrico = Boolean.TRUE;

		}
	}

	public void getHuellasPersonas() {
		// System.out.println("****** INGRESA A GET HUELLAS PERSONAS ");
		// System.out.println("=============> CONTADOR ? --->"+this.contador);
		this.listaTemporalPersonaTrama = new ArrayList<PersonaTrama>();
		PersonaTrama per = new PersonaTrama();
		try {
			for (AsientoVenta av : this.listaManifiesto) {
				if (av != null) {
					per = this.personaTramaService.findByNroDocumento(av.getDocumentoPersona());
					if (per != null) {
						// System.out.println("persona "+per.getDni());
						// System.out.println("NOMBRE : --->"+av.getNombres());
						this.listaTemporalPersonaTrama.add(per);
					}
				}
			}
			for (PersonaTrama p : this.listaTemporalPersonaTrama) {
				if (p.getTrama() != null) {
					cadenaHuellas = cadenaHuellas + p.getTrama();
					this.totalHuellas = this.totalHuellas + 1;
				}
			}
			// System.out.println("********** totalHuellas----> totalHuellas "+this.totalHuellas);

		} catch (Exception e1) {
			System.out.println("  Error Listando huellas ");
			e1.printStackTrace();
		}
		// this.contador++;
	}

	// public void getHuellasPersonas(){
	// // System.out.println("*********** INGRESA A GET HUELLAS PERSONAS");
	//
	// //
	// System.out.println(" la programacion Actual"+this.programacionSelected.getIdProgramacion());
	// try {
	//
	// this.listaVentas =
	// this.asientoVentaServices.listarManifiestoxProg(this.programacionSelected.getIdProgramacion());
	// for(AsientoVenta as : this.listaVentas){
	// PersonaTrama pt = null;
	// if(as.getDocumentoPersona()!=null){
	// pt=this.personaTramaService.findByNroDocumento(as.getDocumentoPersona());
	// }
	// if(pt!=null && pt.getTrama()!=null){
	// listaTemporalPersonaTrama.add(pt);
	// pt=new PersonaTrama();
	// }
	// }
	//
	// for(PersonaTrama p :listaTemporalPersonaTrama){
	// if(p.getTrama()!=null){
	// cadenaHuellas=cadenaHuellas+p.getTrama();
	// this.totalHuellas=this.totalHuellas+1;
	// // System.out.println(" la huella "+p.getTrama());
	// }
	// }
	//
	// } catch (Exception e1) {
	// // System.out.println("  Error Listando huellas ");
	// e1.printStackTrace();
	// }
	// // System.out.println("*** ---> TOTAL DE HUELLAS "+this.totalHuellas);
	//
	// }

	public void listarNoEmbarcados() {

		this.listaManifiestoNoEmbarcados.clear();
		try {

			this.listaManifiestoNoEmbarcados = this.asientoVentaServices.listarManifiestoxProgNoEmbarcados(this.programacionSelected.getIdProgramacion());

			for (AsientoVenta av : this.listaManifiestoNoEmbarcados) {

				Persona p = this.personaService.findByNroDocumento(av.getDocumentoPersona());
				// seteando el objeto persona como atributo de AsientoVenta
				if (p != null) {
					av.setPersona(p);
				}
			}

			// RequestContext.getCurrentInstance().update("dlgDetNoEmbarcados");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void procesarApplet() {
		RequestContext context = RequestContext.getCurrentInstance();
		boolean fin = false;
		// System.out.println("***** INGRESA A PROCESAR APPLET");
		// System.out.println("Index es: "+index);
		PersonaTrama perTrama = this.listaTemporalPersonaTrama.get(index);
		// System.out.println("----> "+perTrama.getDni());

		for (AsientoVenta av : this.listaManifiesto) {
			if (av.getDocumentoPersona() != null && perTrama.getDni() != null) {
				if (av.getDocumentoPersona().equals(perTrama.getDni())) {
					this.p_pasajero = av.getNombres() + " " + av.getaPaterno()+ " " + av.getaMaterno();
					// System.out.println("-------> nombres "+p_pasajero);
					this.p_asiento = Integer.parseInt(av.getNumero());
					// System.out.println("-------> getNumero "+av.getNumero());
					// System.out.println("-------> getIdAsiento "+av.getIdAsiento());

					// actualizando asiento venta--> pasajero a bordo
					av.setAbordo(Boolean.TRUE);
					fin = true;

					try {
						this.asientoVentaServices.actualizarAbordoAsVenta(av);
						actualizarCantidadAbordo();
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}
			if (fin == true) {
				// System.out.println("encontro av");
				// context.execute("verificar()");
				// System.out.println("========> despues de context.execute()");
				context.update(":pnlEmbBiometrico");
				context.update(":formAppletMensaje:pngBienvenido");
				context.update(":formListaManf");
				context.update(":fmTipoEmbarque:pnlEmbBiometrico:formAppletMensaje:pngBienvenido");
				context.update(":formListaManf:pnlResumenAsiento");
				break;
			}
		}

	}

	public void actualizarCantidadAbordo() {
		this.as_alibres = 0;
		this.as_comprados = 0;
		this.as_noembarcados = 0;
		this.as_embarcados = 0;

		for (AsientoVenta av : this.listaManifiesto) {
			if (av != null) {
				// System.out.println("***** docuemto es "+av.getDocumentoPersona());
				// Persona p;
				try {
					// p =
					// this.personaService.findByNroDocumento(av.getDocumentoPersona());

					if (av.getDocumentoPersona() != null
							&& !av.getDocumentoPersona().equals("")
							&& !av.getDocumentoPersona().equals("-")) {
						// av.setNombres(p.getNombres());
						// av.setaPaterno(p.getAPaterno());
						// av.setaMaterno(p.getAMaterno());
						// av.setDocumentoPersona(p.getDni());
						// av.setSexo(p.getSexo());

						this.as_comprados++;
						if (av.getAbordo() == true) {
							this.as_embarcados++;
							//
						} else {
							this.as_noembarcados++;
							//
						}

					} else {
						// av.setPersona(new Persona());
						this.as_alibres++;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	// public void procesarApplet(){
	// // System.out.println("******** INGRESA A PROCESAR APPLET");
	// RequestContext context = RequestContext.getCurrentInstance();
	// boolean fin=false;
	// // System.out.println("Index es: "+index);
	// // System.out.println("--------->"+this.p_asiento);
	// PersonaTrama perTrama=this.listaTemporalPersonaTrama.get(index);
	// // System.out.println("----> "+perTrama.getDni());
	//
	// for(AsientoVenta av: this.listaManifiesto){
	// if(av.getDocumentoPersona()!=null && perTrama.getDni()!=null){
	// if(av.getDocumentoPersona().equals(perTrama.getDni())){
	//
	// this.p_pasajero=av.getNombres()+" "+av.getaPaterno()+" "+av.getaMaterno();
	// // System.out.println("-------> nombres "+p_pasajero);
	// this.p_asiento=Integer.parseInt(av.getNumero());
	// // System.out.println("-------> getNumero "+av.getNumero());
	// // System.out.println("-------> getIdAsiento "+av.getIdAsiento());
	//
	// //actualizando asiento venta--> pasajero a bordo
	// av.setAbordo(Boolean.TRUE);
	// selectProgramacion(this.programacionSelected);
	//
	// context.update(":pnlEmbBiometrico");
	// context.update(":formAppletMensaje:pngBienvenido");
	// context.update(":formListaManf");
	// context.update(":fmTipoEmbarque:pnlEmbBiometrico:formAppletMensaje:pngBienvenido");
	// context.update(":formListaManf:pnlResumenAsiento");
	//
	// fin=true;
	//
	// try {
	// this.asientoVentaServices.actualizarAbordoAsVenta(av);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// }
	// }
	// if(fin==true){
	// // System.out.println("encontro av");
	// context.execute("verificar()");
	// // System.out.println("========> despues de context.execute()");
	// break;
	// }
	// }
	// }

	public void setBienvenidoDeafult() {
		// System.out.println("******* INGRESA A DET BIENVENIDOS X DEFAULT");
		RequestContext context = RequestContext.getCurrentInstance();
		this.p_pasajero = "";
		this.p_asiento = null;
		context.update(":pnlEmbBiometrico");
		context.update(":formAppletMensaje:pngBienvenido");
		context.update(":formListaManf");
		context.update(":fmTipoEmbarque:pnlEmbBiometrico:formAppletMensaje:pngBienvenido");
		context.update(":formListaManf:pnlResumenAsiento");
		context.execute("verificar()");

	}

	// public void setBienvenidoDeafult(){
	//
	// // System.out.println("********* INGRESA A BIENVENIDO X DEFAULT");
	// RequestContext context = RequestContext.getCurrentInstance();
	// this.p_pasajero="";
	// this.p_asiento=0;
	//
	// context.update(":pnlEmbBiometrico");
	// context.update(":formAppletMensaje:pngBienvenido");
	// context.update(":formListaManf");
	// context.update(":fmTipoEmbarque:pnlEmbBiometrico:formAppletMensaje:pngBienvenido");
	// context.update(":formListaManf:pnlResumenAsiento");
	// context.execute("verificar()");
	//
	// }

	/**
	 * @Desc: Metodo que pinta el bus en un popup
	 * @Auth: Will tu papi
	 * @Param: ----
	 * */
	public void pintarBus() {
		System.out.println("pintando el bus");
		System.out.println("la programacion es:"
				+ this.programacionSelected.getIdProgramacion());

		// obteniendo los embracados:
		try {
			this.embarcados = this.asientoVentaServices
					.countEmbarcadosNoEmbarcados(
							this.programacionSelected.getIdProgramacion(),
							Boolean.TRUE);
			this.noEmbarcados = this.asientoVentaServices
					.countEmbarcadosNoEmbarcados(
							this.programacionSelected.getIdProgramacion(),
							Boolean.FALSE);
			this.libres = this.asientoVentaServices
					.countLibres(this.programacionSelected.getIdProgramacion());

			this.asientoVentaServices
					.countEmbarcadosNoEmbarcados(
							this.programacionSelected.getIdProgramacion(),
							Boolean.TRUE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.pintarBus(this.programacionSelected);

	}

	public void pintarBus(Programacion pr) {
		System.out.println("******** pintando bus x programacion");
		RequestContext context = RequestContext.getCurrentInstance();
		// bus clase
		try {
			Servicio servicio = this.servicioService.findById(pr
					.getIdServicio());
			pr.setServicio(servicio);

			if (pr.getServicio() != null) {
				pr.setSeleccionado(Boolean.TRUE);
				this.busClase = this.claseServicioServices.findById(pr
						.getServicio().getIdClase());

				this.tipoAsientop1 = this.tipoAsientoService
						.findById(this.busClase.getIdtipoasientop1());

				if (this.busClase.getNroPisos().equals(2)) {
					this.tipoAsientop2 = this.tipoAsientoService
							.findById(this.busClase.getIdtipoasientop2());
				}

				consultarConfiguracion(pr);
				// context.update(":formListaManf");
				// context.update(":formListaManf:frmBus");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void consultarConfiguracion(Programacion pr) throws Exception {
		this.nroColumnasPisoUno = this.busClase.getNroColumnasPisoUno();
		// this.anchoBusUno = 560;
		this.anchoBusDos = 860;
		// this.anchoBusDos = 700;

		// generando asientos para el piso uno siempre
		this.listaAsientosPisoUno = this.asientoService.findAsientoByClase(
				this.busClase.getIdclase(), new Integer(1));
		AsientoVenta asv = null;

		for (Asiento as : this.listaAsientosPisoUno) {
			asv = this.asientoVentaServices
					.findAsientoByAsientoAndProgramacion(as.getIdAsiento(),
							as.getNumero(), pr.getIdProgramacion());

			as.setAsientoVenta(asv);

		}

		// generando conf inicial para el piso dos
		if (this.busClase.getNroPisos() == 2) {
			this.nroColumnasPisoDos = this.busClase.getNroColumnasPisoDos();
			this.listaAsientosPisoDos = this.asientoService.findAsientoByClase(
					this.busClase.getIdclase(), new Integer(2));
			for (Asiento as : this.listaAsientosPisoDos) {

				asv = this.asientoVentaServices
						.findAsientoByAsientoAndProgramacion(as.getIdAsiento(),
								as.getNumero(), pr.getIdProgramacion());

				as.setAsientoVenta(asv);

			}
		}

		HttpServletResponse response = (HttpServletResponse) FacesContext
				.getCurrentInstance().getExternalContext().getResponse();
		response.setHeader("Cache-Control",
				"no-cache, no-store, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", -1);

	}

	public void buscarPersona() {

		// recuperando parametros enviados desde js jquery
		Map<String, String> requestParamMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();
		String dni = (String) requestParamMap.get("doc");
		// System.out.println(" buscarPersona dni:"+dni);
		this.nroAsientoSelec = requestParamMap.get("nro");

		try {
			this.pasajero = this.personaService.findByNroDocumento(dni);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void buscarPersona2() {
		System.out.println("llego a asiento");

	}

	public void buscarPersonaCroq() {
		System.out.println("******* entra a buscar Persona Crokis ");
		System.out.println("****dni " + this.dniCroq);
		String dni = this.dniCroq;
		System.out.println(" buscarPersona dni Croq:" + dni);
		this.nroAsientoSelec = this.nroCroq;

		try {
			this.pasajero = this.personaService.findByNroDocumento(dni);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void ocultarApplet() {
		System.out.println("***** oculta applet");
		this.estadoApplet = Boolean.FALSE;
		// pintarBus();
	}

	public void mostrarApplet() {
		System.out.println("******** muestra applet ");
		this.estadoApplet = Boolean.TRUE;
	}

	public void guadarCaptura() {
		System.out.println("****** ingresa a guardar");
		RequestContext context = RequestContext.getCurrentInstance();
		PersonaTrama p = new PersonaTrama();
		byte[] arrayImagen = null;
		System.out.println("****** GUARDANDO HUELLA CAPTURADA");
		System.out.println("**** dni " + this.p_dni);

		System.out.println("----ingreso al filtro");
		p.setDescripcion("CAPTURA");
		p.setDni(this.p_dni);
		p.setTrama(this.tramaHuella);

		try {
			arrayImagen = Base64.decode(this.imagenHuella);
			p.setImagen(arrayImagen);

			PersonaTrama pt = this.personaTramaServices
					.findByNroDocumento(this.p_dni);

			if (pt != null) {
				this.personaTramaServices.actualizarPersonaTrama(p);
				pt = new PersonaTrama();
				System.out.println("**** ACTUALIZA");

			} else {
				this.personaTramaServices.crearPersonaTrama(p);
				pt = new PersonaTrama();
				System.out.println("*** NUEVO");
			}
			// System.out.println("********** pintando bus ");
			// System.out.println("*********** guadar huella ---> pintar Bus");
			// this.pintarBus(this.programacionSelected);

			FacesUtils.showFacesMessage("Se verificó el manifiesto correctamente.",Constante.INFORMACION);
			context.update("smsComprobacion");
			// context.update(":formListaManf");
			// context.update(":formListaManf:frmBus");

		} catch (Exception e) {
			e.printStackTrace();
		}

		this.estadoAppletCaptura = Boolean.FALSE;
		this.p_dni = "";
		this.p_pasajero = "";
		this.p_asiento = null;
	}

	public void mostrarAppletCaptura() {
		System.out.println("***** muestra applet ");
		this.estadoAppletCaptura = Boolean.TRUE;
	}

	/* ##################---------setter y getter------################## */

	public List<Programacion> getListaProgramacion() {
		return listaProgramacion;
	}

	public void setListaProgramacion(List<Programacion> listaProgramacion) {
		this.listaProgramacion = listaProgramacion;
	}

	public Usuario getUsuarioLogin() {
		return usuarioLogin;
	}

	public void setUsuarioLogin(Usuario usuarioLogin) {
		this.usuarioLogin = usuarioLogin;
	}

	public String getOficinaUser() {
		return oficinaUser;
	}

	public void setOficinaUser(String oficinaUser) {
		this.oficinaUser = oficinaUser;
	}

	public Agencia getAgenciaUser() {
		return agenciaUser;
	}

	public void setAgenciaUser(Agencia agenciaUser) {
		this.agenciaUser = agenciaUser;
	}

	public Integer getF_idDestino() {
		return f_idDestino;
	}

	public void setF_idDestino(Integer f_idDestino) {
		this.f_idDestino = f_idDestino;
	}

	public List<Destino> getListaDestinos() {
		return listaDestinos;
	}

	public void setListaDestinos(List<Destino> listaDestinos) {
		this.listaDestinos = listaDestinos;
	}

	public List<Programacion> getListaProgramacionFilter() {
		return listaProgramacionFilter;
	}

	public void setListaProgramacionFilter(
			List<Programacion> listaProgramacionFilter) {
		this.listaProgramacionFilter = listaProgramacionFilter;
	}

	public List<AsientoVenta> getListaManifiesto() {
		return listaManifiesto;
	}

	public void setListaManifiesto(List<AsientoVenta> listaManifiesto) {
		this.listaManifiesto = listaManifiesto;
	}

	public List<AsientoVenta> getListaFiltroManifiesto() {
		return listaFiltroManifiesto;
	}

	public void setListaFiltroManifiesto(
			List<AsientoVenta> listaFiltroManifiesto) {
		this.listaFiltroManifiesto = listaFiltroManifiesto;
	}

	public Boolean getActivarManifiesto() {
		return activarManifiesto;
	}

	public void setActivarManifiesto(Boolean activarManifiesto) {
		this.activarManifiesto = activarManifiesto;
	}

	public Integer getAs_embarcados() {
		return as_embarcados;
	}

	public void setAs_embarcados(Integer as_embarcados) {
		this.as_embarcados = as_embarcados;
	}

	public Integer getAs_capacidad() {
		return as_capacidad;
	}

	public void setAs_capacidad(Integer as_capacidad) {
		this.as_capacidad = as_capacidad;
	}

	public Integer getAs_alibres() {
		return as_alibres;
	}

	public void setAs_alibres(Integer as_alibres) {
		this.as_alibres = as_alibres;
	}

	public Integer getAs_noembarcados() {
		return as_noembarcados;
	}

	public void setAs_noembarcados(Integer as_noembarcados) {
		this.as_noembarcados = as_noembarcados;
	}

	public Integer getAs_comprados() {
		return as_comprados;
	}

	public void setAs_comprados(Integer as_comprados) {
		this.as_comprados = as_comprados;
	}

	public Tripulacion getTripulacion_prog() {
		return tripulacion_prog;
	}

	public void setTripulacion_prog(Tripulacion tripulacion_prog) {
		this.tripulacion_prog = tripulacion_prog;
	}

	public String getP_dni() {
		return p_dni;
	}

	public void setP_dni(String p_dni) {
		this.p_dni = p_dni;
	}

	public String getP_pasajero() {
		return p_pasajero;
	}

	public void setP_pasajero(String p_pasajero) {
		this.p_pasajero = p_pasajero;
	}

	public Integer getP_asiento() {
		return p_asiento;
	}

	public void setP_asiento(Integer p_asiento) {
		this.p_asiento = p_asiento;
	}

	public Programacion getProgramacionSelected() {
		return programacionSelected;
	}

	public void setProgramacionSelected(Programacion programacionSelected) {
		this.programacionSelected = programacionSelected;
	}

	public String getTipoEmbarque() {
		return tipoEmbarque;
	}

	public void setTipoEmbarque(String tipoEmbarque) {
		this.tipoEmbarque = tipoEmbarque;
	}

	public boolean isActEmbarDni() {
		return actEmbarDni;
	}

	public void setActEmbarDni(boolean actEmbarDni) {
		this.actEmbarDni = actEmbarDni;
	}

	public boolean isActEmbarBiometrico() {
		return actEmbarBiometrico;
	}

	public void setActEmbarBiometrico(boolean actEmbarBiometrico) {
		this.actEmbarBiometrico = actEmbarBiometrico;
	}

	public Integer getAs_tripulacion() {
		return as_tripulacion;
	}

	public void setAs_tripulacion(Integer as_tripulacion) {
		this.as_tripulacion = as_tripulacion;
	}

	public boolean isHabConfig() {
		return habConfig;
	}

	public void setHabConfig(boolean habConfig) {
		this.habConfig = habConfig;
	}

	public int getUmbralCaptura() {
		return umbralCaptura;
	}

	public void setUmbralCaptura(int umbralCaptura) {
		this.umbralCaptura = umbralCaptura;
	}

	public int getUmbralDeteccion() {
		return umbralDeteccion;
	}

	public void setUmbralDeteccion(int umbralDeteccion) {
		this.umbralDeteccion = umbralDeteccion;
	}

	public int getUmbralVerificacion() {
		return umbralVerificacion;
	}

	public void setUmbralVerificacion(int umbralVerificacion) {
		this.umbralVerificacion = umbralVerificacion;
	}

	public int getTotalHuellas() {
		return totalHuellas;
	}

	public void setTotalHuellas(int totalHuellas) {
		this.totalHuellas = totalHuellas;
	}

	public List<AsientoVenta> getListaVentas() {
		return listaVentas;
	}

	public void setListaVentas(List<AsientoVenta> listaVentas) {
		this.listaVentas = listaVentas;
	}

	public int getTotalHuellasBD() {
		return totalHuellasBD;
	}

	public void setTotalHuellasBD(int totalHuellasBD) {
		this.totalHuellasBD = totalHuellasBD;
	}

	public int getMinUmbralVerificacion() {
		return minUmbralVerificacion;
	}

	public void setMinUmbralVerificacion(int minUmbralVerificacion) {
		this.minUmbralVerificacion = minUmbralVerificacion;
	}

	public String getCadenaHuellas() {
		return cadenaHuellas;
	}

	public void setCadenaHuellas(String cadenaHuellas) {
		this.cadenaHuellas = cadenaHuellas;
	}

	public List<AsientoVenta> getListaManifiestoNoEmbarcados() {
		return listaManifiestoNoEmbarcados;
	}

	public void setListaManifiestoNoEmbarcados(
			List<AsientoVenta> listaManifiestoNoEmbarcados) {
		this.listaManifiestoNoEmbarcados = listaManifiestoNoEmbarcados;
	}

	public List<AsientoVenta> getListaManifiestoNoEmbarcadosFiltro() {
		return listaManifiestoNoEmbarcadosFiltro;
	}

	public void setListaManifiestoNoEmbarcadosFiltro(
			List<AsientoVenta> listaManifiestoNoEmbarcadosFiltro) {
		this.listaManifiestoNoEmbarcadosFiltro = listaManifiestoNoEmbarcadosFiltro;
	}

	// 30-10-2014

	public String getMensajeVerificacion() {
		return mensajeVerificacion;
	}

	public void setMensajeVerificacion(String mensajeVerificacion) {
		this.mensajeVerificacion = mensajeVerificacion;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
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

	public Integer getNroFilas() {
		return nroFilas;
	}

	public void setNroFilas(Integer nroFilas) {
		this.nroFilas = nroFilas;
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

	public Integer getEmbarcados() {
		return embarcados;
	}

	public void setEmbarcados(Integer embarcados) {
		this.embarcados = embarcados;
	}

	public Integer getNoEmbarcados() {
		return noEmbarcados;
	}

	public void setNoEmbarcados(Integer noEmbarcados) {
		this.noEmbarcados = noEmbarcados;
	}

	public Integer getLibres() {
		return libres;
	}

	public void setLibres(Integer libres) {
		this.libres = libres;
	}

	public Persona getPasajero() {
		return pasajero;
	}

	public void setPasajero(Persona pasajero) {
		this.pasajero = pasajero;
	}

	public String getNroAsientoSelec() {
		return nroAsientoSelec;
	}

	public void setNroAsientoSelec(String nroAsientoSelec) {
		this.nroAsientoSelec = nroAsientoSelec;
	}

	public Boolean getEstadoApplet() {
		return estadoApplet;
	}

	public void setEstadoApplet(Boolean estadoApplet) {
		this.estadoApplet = estadoApplet;
	}

	public String getMensajeCaptura() {
		return mensajeCaptura;
	}

	public void setMensajeCaptura(String mensajeCaptura) {
		this.mensajeCaptura = mensajeCaptura;
	}

	public String getTramaHuella() {
		return tramaHuella;
	}

	public void setTramaHuella(String tramaHuella) {
		this.tramaHuella = tramaHuella;
	}

	public String getImagenHuella() {
		return imagenHuella;
	}

	public void setImagenHuella(String imagenHuella) {
		this.imagenHuella = imagenHuella;
	}

	public Boolean getEstadoAppletCaptura() {
		return estadoAppletCaptura;
	}

	public void setEstadoAppletCaptura(Boolean estadoAppletCaptura) {
		this.estadoAppletCaptura = estadoAppletCaptura;
	}

	public String getDestinoBus() {
		return destinoBus;
	}

	public void setDestinoBus(String destinoBus) {
		this.destinoBus = destinoBus;
	}

	public Boolean getFrecuencia() {
		return frecuencia;
	}

	public void setFrecuencia(Boolean frecuencia) {
		this.frecuencia = frecuencia;
	}

	public Boolean getNuevo() {
		return nuevo;
	}

	public void setNuevo(Boolean nuevo) {
		this.nuevo = nuevo;
	}

	public String getDniCroq() {
		return dniCroq;
	}

	public void setDniCroq(String dniCroq) {
		this.dniCroq = dniCroq;
	}

	public String getNroCroq() {
		return nroCroq;
	}

	public void setNroCroq(String nroCroq) {
		this.nroCroq = nroCroq;
	}

	public String getNombreImagen() {
		return nombreImagen;
	}

	public void setNombreImagen(String nombreImagen) {
		this.nombreImagen = nombreImagen;
	}

	public Integer getF_idOrigen() {
		return f_idOrigen;
	}

	public void setF_idOrigen(Integer f_idOrigen) {
		this.f_idOrigen = f_idOrigen;
	}

	public List<Agencia> getListaOrigenes() {
		return listaOrigenes;
	}

	public void setListaOrigenes(List<Agencia> listaOrigenes) {
		this.listaOrigenes = listaOrigenes;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

}
