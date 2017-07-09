package com.certicom.ittsa.managedBeans;


import groovy.util.logging.Log4j;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.ActionEvent;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.PrinterName;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import jcifs.dcerpc.msrpc.netdfs;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

//import sun.nio.cs.ext.TIS_620;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Asiento;
import com.certicom.ittsa.domain.AsientoPasajero;
import com.certicom.ittsa.domain.AsientoVenta;
import com.certicom.ittsa.domain.Boleto;
import com.certicom.ittsa.domain.ClaseServicio;
import com.certicom.ittsa.domain.CopiaBoleto;
import com.certicom.ittsa.domain.Destino;
import com.certicom.ittsa.domain.Detalle_Liquidacion;
import com.certicom.ittsa.domain.Empresa;
import com.certicom.ittsa.domain.Flota;
import com.certicom.ittsa.domain.HistorialPasajero;
import com.certicom.ittsa.domain.Inhabilitacion;
import com.certicom.ittsa.domain.Liquidacion;
import com.certicom.ittsa.domain.Liquidacion_Venta;
import com.certicom.ittsa.domain.ListaNegra;
import com.certicom.ittsa.domain.Oficina;
import com.certicom.ittsa.domain.PagadorPrepagado;
import com.certicom.ittsa.domain.Parametro;
import com.certicom.ittsa.domain.Perfil;
import com.certicom.ittsa.domain.Persona;
import com.certicom.ittsa.domain.Postergacion;
import com.certicom.ittsa.domain.Programacion;
import com.certicom.ittsa.domain.Promocion;
import com.certicom.ittsa.domain.PuntoVenta;
import com.certicom.ittsa.domain.Secuencia;
import com.certicom.ittsa.domain.Servicio;
import com.certicom.ittsa.domain.TipoAsiento;
import com.certicom.ittsa.domain.TrackingBoleto;
import com.certicom.ittsa.domain.Tripulacion;
import com.certicom.ittsa.domain.Usuario;
import com.certicom.ittsa.domain.UsuarioAutorizante;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.AsientoServices;
import com.certicom.ittsa.services.AsientoVentaServices;
import com.certicom.ittsa.services.ClaseServicioServices;
import com.certicom.ittsa.services.DestinoServices;
import com.certicom.ittsa.services.EmpresaService;
import com.certicom.ittsa.services.HistorialPasajeroService;
import com.certicom.ittsa.services.Liquidacion_VentaServices;
import com.certicom.ittsa.services.ListaNegraService;
import com.certicom.ittsa.services.OficinaService;
import com.certicom.ittsa.services.ParametroServices;
import com.certicom.ittsa.services.PerfilServices;
import com.certicom.ittsa.services.PersonaServices;
import com.certicom.ittsa.services.PostergarService;
import com.certicom.ittsa.services.ProgramacionService;
import com.certicom.ittsa.services.PromocionServices;
import com.certicom.ittsa.services.PuntoVentaService;
import com.certicom.ittsa.services.SecuenciaServices;
import com.certicom.ittsa.services.ServicioService;
import com.certicom.ittsa.services.TipoAsientoService;
import com.certicom.ittsa.services.TrackingBoletoService;
import com.certicom.ittsa.services.TripulacionServices;
import com.certicom.ittsa.services.UsuarioAutorizanteServices;
import com.certicom.ittsa.services.UsuarioServices;
import com.pe.certicom.ittsa.commons.Conexion;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.ConstanteVentas;
import com.pe.certicom.ittsa.commons.ExportarArchivo;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.NumeroALetra;
import com.pe.certicom.ittsa.commons.Utiles;
import com.pe.certicom.ittsa.commons.Utils;
//import com.sun.org.apache.bcel.internal.generic.NEW;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jdt.internal.compiler.ast.ThisReference;


@ManagedBean(name = "ventaDirectaMB")
@ViewScoped
public class VentaDirectaMB {

	private static final Log LOG = LogFactory.getLog(VentaDirectaMB.class);

	@ManagedProperty(value="#{loginMB.usuario}")
	private Usuario usuarioLogin;
	
	private List<Agencia> listaAgOrigen;
	private List<Agencia> listaAgDestinos;
	//private Destino destino;
	private Agencia agenciaOrigen;
	private Agencia agenciaDestino;
	private List<Agencia> listaAgenciasExternas;
	private Agencia agenciaExterna;
	private List<Oficina> listaOficinasExternas;
	private Oficina oficinaExterna;
	private String fechaSelec;
	private String fechaString;
	private List<Programacion> listaProgramacionCompartidas;
	private List<Programacion> listaProgramacion;
	private List<Programacion> listaFiltroProgramacion;
	private Programacion programacionSelec;
	// VARIABLES GLOBALES
	private String _rutaCompartida;
	private Integer _idServicioCompartido;
	private Integer _gradoServTramo;
	private Integer _intCorreEnlace;
	//private String _tipoVenta = "1";
	private String _tipoPago = "EFECTIVO";
	private String _tipoEntrega = "OFICINA";
	private String _Serie ="";
	private String _Numero ="";

	private TreeNode raiz;
	private Integer tipoItinerario;
	private Integer _cantidadViajes;
	private String _ultimaFechaViaje;
	private String _fechahoy;
	// private TreeNode raizFecha;
	private TreeNode nodoFechaSeleccionado;
	private Tripulacion tripulacion_venta;
	// private TreeNode nodoServicioSeleccionado;

	// bus:
	private ClaseServicio busClase;
	private Integer nroColumnasPisoUno;
	private Integer nroColumnasPisoDos;
	private Integer nroFilas;
	//private Integer anchoBusUno;
	private Integer anchoBusDos;
	private List<Asiento> listaAsientosPisoUno;
	private List<Asiento> listaAsientosPisoDos;
	private Boolean visibleBus;
	private Date temp;
	private Asiento asientoSelec;
	private AsientoVenta asientoVenta;
	private Persona persona;
	private Boolean dniDigitado;
	private Date fechaCumpleAnios;
	// private Boolean verFecha;
	private TipoAsiento tipoAsientop1;
	private TipoAsiento tipoAsientop2;
	private BigDecimal precioAsiento;
	private BigDecimal precioAsientoPromocion;
	private Boolean boletoRuc;
	private Boolean esOficinaExterna;
	private Empresa empresa;
	private Boolean personaEncontrada;
	private Boolean empresaEncontrada;
	private Boolean estaLibre;
	private Boolean editar;
	private Boolean copiaBoleto;
	private Boolean manifiesto;
	private Boolean cambioServicio;
	private Boolean _isCompartida;
	private String tipoVenta;
	private String observacion;
	private Boolean alertaReserva;
	private Date fechaLimiteReserva;
	private Date fechaLimiteDelivery;
	private Date fechaEntregaDelivery;
	private String fechaStringEntregaDelivery;
	private Boolean _frecuencia;
	private Boolean _nuevo;
	private Boolean _es_dni;
	private Constante constantes;
	private Integer intsecuencia;
	private Boolean estaListaNegra;
	private Boolean _bandHoraReserva;
	//private List<Asiento> listaVentaVarios;
	private List<AsientoVenta> listaAsientosxVender;
	/* Datos informativos */
	private Integer disponibles;
	private Integer disponiblesP1;
	private Integer disponiblesP2;
	private Integer capacidad;
	private Integer asi_vendidos;
	private Integer asi_reservados;
	private Integer asi_separados;
	//private Integer totalVendidos;
	private PuntoVenta puntoVenta;
	private String observacionesMultiple;
	private String nroAsientoAnular;
	private BigDecimal montoPrepagado;
	private BigDecimal montoDelivery;
	private List<AsientoVenta> listaManifiesto;
	private List<AsientoVenta> listaFiltroManifiesto;
	private String tipoTarjeta;
	private Double montoEfectivo;
	private Double montoTarjeta;
	private String numeroReferenciaTarjeta;
	private Boolean reservaEnElDia;
	private Boolean deliveryEnElDia;
	private TrackingBoleto trackingBoletoAnul;
	private List<Persona> listpersonabuscadas;
	
	private AsientoPasajero asientoPasajero;

	//MODIFICACION 
	private boolean pasajeroReg=false;
	private String fechaCompraBoleto;
	private String horaSalidaCompra;
	
	//postergacion
	@ManagedProperty(value="#{loginMB}")
	private LoginMB login;
	
	private Postergacion postergacion;
	private Boolean postergar;
	private Date fechaCaducidad;
	private Date fechaActual;
	private List<Postergacion> listaPostergacion;
	private Boolean rucDigitado;
	private Boolean boletoPrepagado;
	private Boolean deliveryVenta;
	private Boolean transBancariaVenta;
	private Boolean oficinaVenta;
	private Boolean fechaAbierta;
	private Boolean boletoReserva;
	private Boolean estadoBoletoVendido;
	private Persona personaNueva;
	private AgenciaService agenciaService;
	private ProgramacionService programacionService;
	private ServicioService servicioService;
	private AsientoServices asientoService;
	private ClaseServicioServices claseServicioServices;
	private AsientoVentaServices asientoVentaService;
	private TipoAsientoService tipoAsientoService;
	private PersonaServices personaService;
	private EmpresaService empresaService;
	private Liquidacion_VentaServices liquidacion_VentaServices;
	private HistorialPasajeroService historialPasajeroService;
	private PuntoVentaService puntoVentaService;
	private TrackingBoletoService trackBoletoService;
	private TripulacionServices tripulacionServices;
	private Integer horaMaximaReserva;
	private Integer minutoMaximaReserva;
	private Integer horaMaximaDelivery;
	private Date fechaMaximaSalida;
	private ParametroServices parametroServices;
	private SecuenciaServices secuenciaServices;
	private UsuarioServices usuarioServices;
	private PerfilServices perfilServices;
	//private StringBuilder codigoReserva;
	private OficinaService oficinaService;

	
	//postergacion
	private PostergarService postergarService;
	
	private ListaNegraService listaNegraService;
	
	
	//prepgado
	private PagadorPrepagado pagador;
	
	private PagadorPrepagado delivery;
	
	private List<TrackingBoleto> listaTracking;
	private List<TrackingBoleto> listaFiltroTracking;
	private List<HistorialPasajero> listaHistorialPasajero;
	private List<HistorialPasajero> listaFiltroHistorialPasajero;
	private List<HistorialPasajero> listaHistorialPasajeroDetalle;
	private HistorialPasajero historialPasajero;
	private TrackingBoleto tracking;
	private TrackingBoleto trackingCodigo;
	private String serieBoletoMov;
	private String numeroBoletoMov;
	private List<Persona> listaPersonasPorApellido;
	private List<Persona> listaFiltroPersonasPorApellido;
	private String tipoVista;
	private Boolean destinoSeleccionado;
	private Boolean esPromocional;
	private Boolean enablePromocion;
	private List<UsuarioAutorizante> listaUsuarioAutorizante;
	private Promocion promocion;
	private UsuarioAutorizanteServices usuarioAutorizanteServices;
	private UsuarioAutorizante usuarioAutorizante;
	private PromocionServices promocionServices;
	private Boolean tienePromocion;
	private Boolean idaVuelta;
	private Boolean noPromocionIdaVuelta;
	private Boolean activaPostergacion;
	private String serieBoletoAnul;
	private String numeroBoletoAnul;
	private String nroRuc;
	private String tipo_documento= "DNI";
	
	private DestinoServices destinoServices;
	
	//Boleto seleccionado a reimprimir
	private TrackingBoleto boletoReprint;
	private HistorialPasajero  rePrintBoleto;
	

	private List<Promocion> listaCmbPromociones;
	private Integer idPromocionSelect;
	private String transferenciaBanco;
	private String transferenciaNumero;
	
	private String dsctoBoletoMultiple;
	private BigDecimal costoBoletoPromocional;
	
	//MODIFICACION 09-09-2014
	private String auxRUC="";
	private String auxRSocial="";
	
	private List<Integer> listaAsientosDisponibles;
	private BigDecimal diferencia;
	private Servicio servicioCompra;
	private Date fechaHoraLimitePostergacion;
	private List <AsientoVenta> asientoVentaPrepagados;

	//concurrencia
	private Usuario usuarioAsientoVenta;
	private List<AsientoVenta> listaAsientosxVenderValidos;
	private List<AsientoVenta> listaAsientosxVenderNoValidos;
	
	private Boolean bandMultiple;
	private CopiaBoleto boletaCopia;
	//private String serieBoleto;
	//private String numeroBoleto;
	private List<TrackingBoleto> trackingBoleto;
	//private Boolean recuperarPostergacion;
	private Boolean postergacionRecuperadaEnMes;
	//private Boolean imprimirMontoNominal;
	
	private Boolean recuperarInhabilitacion;
	private Boolean inhabilitacionRecuperadaEnMes;

	private Inhabilitacion inhabilitacion;
	private Boolean buscarInhabilitado;
	private Integer pisoCambio;
	private List<AsientoVenta> listaAsientosVentaLibres;
	private List<Asiento> listaAsientosPisoUnoTarget;
	private List<Asiento> listaAsientosPisoDosTarget;
	private AsientoVenta asientoVentaCambiado;
	private String tipoVentaHistorial; 
	private Persona personaHistorial;
	private Boolean consultaRapida;
	
        //Busqueda pasajero
    //private String busqBoletoSerie;
    //private String busqBoletoNumero;
	private List<TrackingBoleto> listaTrackingBoleto;
	private List<TrackingBoleto> listaFiltroTrackingBoleto;

	//private String serieBoletoInhabilitado;
	//private String numeroBoletoInhabilitado;
	
	private String boletoSerieBuscado;
    private String boletoNumeroBuscado;
    private Boolean diferenciaPostergacion;
    private BigDecimal valorBoletoInhabilitado;
    private Servicio servicioInhabilitado;
    private Boolean buscarResultado;
    private Integer nroMovimientosBoleto;
        
	public VentaDirectaMB() {
	}

	@PostConstruct
	public void inicia() 
	{
		this.buscarResultado = Boolean.FALSE;
		this.nroMovimientosBoleto = new Integer(0);
		this.nroRuc = null;
		this.valorBoletoInhabilitado = new BigDecimal(0.0);
		this.diferenciaPostergacion = Boolean.FALSE;
		this.consultaRapida = Boolean.FALSE;
		this.asientoVentaCambiado = new AsientoVenta();
		this.listaAsientosVentaLibres = new ArrayList<>();
		this.buscarInhabilitado = Boolean.FALSE;
		this.inhabilitacionRecuperadaEnMes = Boolean.FALSE;
		this.recuperarInhabilitacion = Boolean.FALSE;
		this.inhabilitacion = new Inhabilitacion();
		this.postergacionRecuperadaEnMes = Boolean.FALSE;
		//this.recuperarPostergacion = Boolean.FALSE;
		this.postergacion = new Postergacion();
		this.visibleBus = Boolean.FALSE;
		this.manifiesto = Boolean.FALSE;
		this._bandHoraReserva = Boolean.FALSE;
		this.setNroFilas(5);
		this.programacionSelec = new Programacion();
		this.boletaCopia =  new CopiaBoleto();
		this.boletaCopia.setValorCopia(new BigDecimal(1));
		//this.destino = new Destino();
		this.asientoSelec = new Asiento();
		this.setAsientoVenta(new AsientoVenta());
		this.agenciaService = new AgenciaService();
		this.programacionService = new ProgramacionService();
		this.liquidacion_VentaServices = new Liquidacion_VentaServices();
		this.historialPasajeroService = new HistorialPasajeroService();
		this.puntoVentaService = new PuntoVentaService();
		this.parametroServices = new ParametroServices(); 
		this.usuarioServices = new UsuarioServices();
		this.perfilServices = new PerfilServices();
		this.oficinaService = new OficinaService();
		this.servicioService = new ServicioService();
		this.claseServicioServices = new ClaseServicioServices();
		this.secuenciaServices = new SecuenciaServices();
		this.asientoService = new AsientoServices();
		this.usuarioAutorizanteServices = new UsuarioAutorizanteServices();
		this.asientoVentaService = new AsientoVentaServices();
		this.tipoAsientoService = new TipoAsientoService();
		this.listaNegraService = new ListaNegraService();
		this.personaService = new PersonaServices();
		this.empresaService = new EmpresaService();
		this.tipoAsientop1 = new TipoAsiento();
		this.tipoAsientop2 = new TipoAsiento();
		this.persona = new Persona();
		this.empresa = new Empresa();
		this.persona.setTipodocumento(this.tipo_documento);
		this.estaLibre = Boolean.FALSE;
		this.editar = Boolean.FALSE;
		this.boletoPrepagado = Boolean.FALSE;
		this.deliveryVenta = Boolean.FALSE;
		this._isCompartida = Boolean.FALSE;
		this.listaAsientosxVender = new ArrayList<>();
		this.puntoVenta = new PuntoVenta();
		this.personaEncontrada = Boolean.FALSE;
		this.empresaEncontrada = Boolean.FALSE;
		this.esOficinaExterna = Boolean.FALSE;
		this.setDniDigitado(Boolean.FALSE);
		this.fechaCumpleAnios = new Date();
		this.agenciaExterna = new Agencia();
		this.oficinaExterna = new Oficina();
		this.listaAgenciasExternas  = new ArrayList<>();
		this.listaOficinasExternas=  new ArrayList<>();
		this.listaManifiesto = new ArrayList<>();
		//this.listaFiltroManifiesto = new ArrayList<>();
		this.montoPrepagado = new  BigDecimal(1.0);
		this.montoDelivery = new  BigDecimal(0.0);
		this.fechaAbierta = Boolean.FALSE;
		this.tipoTarjeta="V";
		this.alertaReserva = Boolean.FALSE;
		this.trackBoletoService = new TrackingBoletoService();
		this.fechaActual = new Date();
		//postergacion
		this.postergarService = new PostergarService();
		postergar = Boolean.FALSE;
		this.listaPostergacion = new ArrayList<>();
		this.pagador = new PagadorPrepagado();
		this.delivery = new PagadorPrepagado();
		this.estaListaNegra = Boolean.FALSE;
		this.cambioServicio = Boolean.FALSE;
		this.listaPersonasPorApellido = new ArrayList<>();
		this.reservaEnElDia = Boolean.FALSE;
		this.deliveryEnElDia = Boolean.FALSE;
		this.destinoSeleccionado =Boolean.FALSE;
		this.esPromocional = Boolean.FALSE;
		this.enablePromocion = Boolean.FALSE;
		this.promocion = new Promocion();
		this.promocionServices = new PromocionServices();
		this.tripulacionServices = new TripulacionServices();
		this.tienePromocion = Boolean.FALSE;
		this.idaVuelta = Boolean.FALSE;		
		this.noPromocionIdaVuelta = Boolean.FALSE;
		this.tipoItinerario = 1;
		this._frecuencia = Boolean.FALSE;
		this._nuevo = Boolean.FALSE;
		this._es_dni= Boolean.FALSE;
		this.destinoServices = new DestinoServices();
		
		this.listaCmbPromociones = new ArrayList<Promocion>();
		this.listaHistorialPasajero = new ArrayList<>();
		this.listaFiltroHistorialPasajero = new ArrayList<>();
		this.listaHistorialPasajeroDetalle = new ArrayList<>();
		this.historialPasajero = new HistorialPasajero();
		this.activaPostergacion = Boolean.FALSE;
		this.servicioCompra = new Servicio();
		
		this.listaAsientosxVenderValidos=new ArrayList<AsientoVenta>();
		this.listaAsientosxVenderNoValidos=new ArrayList<AsientoVenta>();
		
		this.bandMultiple=Boolean.FALSE;
		//System.out.println("la oficina es:"+this.login.getPv().getDesOficina());
		this.personaHistorial= new Persona();
		
		try {
			this.listaAgOrigen = this.agenciaService.listaAgenciasActivas();
			this.puntoVenta = this.puntoVentaService.findById(usuarioLogin.getIdpuntoventa());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void cargaRaiz(TreeNode root) 
	{
		// primer nivel : origenes
		for (Agencia ag : this.listaAgOrigen) 
		{
			TreeNode origen = new DefaultTreeNode(ag.getDescripcion(), root);

			// nivel dos: destinos
			for (Agencia age : this.listarAgeDestino(ag.getIdagencia())) 
			{
				TreeNode destino = new DefaultTreeNode(age.getDescripcion(),origen);
				
				//nivel tres: fechas calendario
				Date hoy = new Date();
				//45 dias
				Integer nroDias= this.login.getAgencia().getDiascroquis();
				//validanndo hast adonde hay rogramacines
				
				
				for (int i = 0; i <= nroDias-1; i++) 
				{
					Date fechaSuma = this.sumaDias(hoy, i);
						//TreeNode fecha = new DefaultTreeNode(new SimpleDateFormat("dd-MM-yyyy").format(fechaSuma), destino);
					TreeNode fecha = new DefaultTreeNode(new SimpleDateFormat("EEEE d-MMMM-yyyy").format(fechaSuma), destino);

				}
				
			}
		}
	}

	public void onNodoFechaSeleccionado(NodeSelectEvent event) 
	{
		//System.out.println("aqui");

		String centuria =  StringUtils.right(event.getTreeNode().toString(), 4).substring(0, 2);
		if (event.getTreeNode().toString().length() > 7	&& centuria.equals("20")) // centuria
		{
			this.visibleBus = Boolean.FALSE;
			this.manifiesto = Boolean.FALSE;
			
			try {

				this.agenciaOrigen = this.agenciaService.findByDescripcion(event.getTreeNode().getParent().getParent().toString());
				this.agenciaDestino = this.agenciaService.findByDescripcion(event.getTreeNode().getParent().toString());
				this.fechaString = event.getTreeNode().toString();
				
				//this.temp = new SimpleDateFormat("dd-MM-yyyy").parse(this.fechaString);
				this.temp = new SimpleDateFormat("EEEE d-MMMM-yyyy").parse(this.fechaString);
				this.fechaSelec = new SimpleDateFormat("yyyy-MM-dd").format(temp);
				
				prepararProgramacion(this.agenciaOrigen.getIdagencia(),this.agenciaDestino.getIdagencia(), this.fechaSelec);
				//System.out.println("lista de programacion:"+this.listaProgramacion.size());
				
				if(this.listaProgramacion.isEmpty())
				{
					RequestContext.getCurrentInstance().execute("dlgNoProgramacion.show();");
				}
				
				
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception ef) {
				ef.printStackTrace();
			}
		}

	}
	
	public void prepararProgramacion(Integer agenciaOrigenId,Integer agenciaDestinoId,String fech)
	{
		// listando programacion:
		try {
	        this.manifiesto = Boolean.FALSE;
			this.programacionSelec = new Programacion();
			this.listaProgramacion = this.listarProgramacion(agenciaOrigenId,agenciaDestinoId, fech);
			/* transformar las horas de 24 a formato de 12 horas */
			for (int i = 0; i < this.listaProgramacion.size(); i++) 
			{
				Programacion pro = listaProgramacion.get(i);
				pro.sethLlegadaAprox(calcaulateTime(pro.gethSalida(),pro.getHorasViaje()));
			}
			this.visibleBus = Boolean.FALSE;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String calcaulateTime(String horaSalida, Double horasViaje) {
		String hFinal = "";
		String hora = horaSalida.substring(0, 2);
		String minuto = horaSalida.substring(3, 5);

		String hViaje = Double.toString(horasViaje); // Lo convierte a notacion
														// cientifica cuando el
														// Double es muy grande
		String[] nums = hViaje.split("\\.");
		int horaV = Integer.parseInt(nums[0]);
		int minutoV = Integer.parseInt(nums[1]);

		int horaFinal = Integer.parseInt(hora) + horaV;
		Integer minutoFinal = Integer.parseInt(minuto) + minutoV;
		String minutoPinta = "";
		if (minutoFinal > 59) {
			int tem = minutoFinal - 60;
			horaFinal += 1;
			minutoFinal = tem;
			if (minutoFinal < 10) {
				minutoPinta = "0" + minutoFinal;
			} else {
				minutoPinta = minutoFinal + "";
			}

		} else {
			if (minutoFinal < 10) {
				minutoPinta = "0" + minutoFinal;
			} else {
				minutoPinta = minutoFinal + "";
			}

		}

		if (horaFinal > 12) {
			int tem = horaFinal - 12;
			horaFinal = tem;
			if (horaFinal < 10) {
				hFinal = "0" + horaFinal + ":" + minutoPinta + " PM";
			} else {
				hFinal = horaFinal + ":" + minutoPinta + " PM";
			}
		} else {
			if (horaFinal < 10) {
				hFinal = "0" + horaFinal + ":" + minutoPinta + " AM";
			} else {
				hFinal = horaFinal + ":" + minutoPinta + " AM";
			}
		}
		return hFinal;
	}

   
        
	public void mostrarPanelVentas(String tipv) 
	{
		System.out.println("INICIO - mostrarPanelVentas");
		RequestContext context = RequestContext.getCurrentInstance();
		int respuesta=-1;
		AsientoVenta asvSelec;
		this._Serie=""; //limpiando campo del formulario
		this._Numero=""; //limpiando campo del formulario
				
		listaAsientosxVenderValidos.clear();
		listaAsientosxVenderNoValidos.clear();
		
		PuntoVenta ptv = new PuntoVenta();
		try {
			ptv = this.puntoVentaService.getPuntoVentaxUsuario(usuarioLogin.getIdusuario());
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
		switch(tipv)
		{
			case "venta" :  this.tipoVenta = ConstanteVentas.ESTADO_VENTA; 
							this._Serie=ptv.getSerieBoleto();
							this._Numero=String.valueOf((ptv.getUltimoBoleto()+1));	
							this._bandHoraReserva = Boolean.FALSE;break;
			case "reserv" : this.tipoVenta = ConstanteVentas.ESTADO_RESERVA;
						{	//this._Serie=ptv.getSeriereserva();
							//this._Numero=String.valueOf((ptv.getUltimareserva()+1));
			                verificarFechaLimiteReserva();
			                if(!this.alertaReserva)
			                {//A tiempo para hacer la reserva
			                //double horasRestantes = (calfHoraReserva.getTimeInMillis() - now.getTimeInMillis()) / (1000 * 60 * 60);
			                 if(DateUtils.isSameDay(this.fechaActual, this.fechaLimiteReserva)) {
			                                		this.reservaEnElDia = Boolean.TRUE;
			        				} else {
									this.reservaEnElDia = Boolean.FALSE;
								}
								
								for(AsientoVenta av : this.listaAsientosxVender)
								{
									
									PuntoVenta pv = null;
									Integer ultimaReserva = 0;
									try {
										
										pv = this.puntoVentaService.obtenerPuntoVenta(this.login.getPv().getIdPuntoVenta());
										ultimaReserva = pv.getUltimareserva() + 1;
										this.puntoVentaService.actualizarUltimaReserva(pv.getIdPuntoVenta(), ultimaReserva);
										 
										StringBuilder codReserv = new StringBuilder();
										codReserv.append(this.login.getAgencia().getGrupo());
										codReserv.append("-");
										codReserv.append(this.login.getPv().getSeriereserva());
										codReserv.append("-");
										codReserv.append("0"+ultimaReserva);
										av.setCodigoReserva(codReserv.toString());
										 
									} catch (Exception e) {
										e.printStackTrace();
									}
									//en lugar de: av.serieBoletoMulti} #{av.numeroBoletoMulti
								}
							}
						}
						break; 
		}
		
		//System.out.println("ENTRA EN MOSTRAR PANEL VENTAS");
		if(this.programacionSelec.getDesRutaCompartida().equals("SI") || this.programacionSelec.getIntCorreEnlace()>0)
		{	System.out.println("entro en ruta compartida");
			this._isCompartida = true;
		}else{ //System.out.println("entro en el else de ruta compartida");
			this._isCompartida = false;
		}
		
		//obteniendo el nro serie y nro ultimo del boleto
		//Se comento esto puesto que en la parte superior encuentra el numero y serial 
		try {
			this.puntoVenta = this.puntoVentaService.findById(this.usuarioLogin.getIdpuntoventa());
			if(this.puntoVenta != null){
				this._Serie = this.puntoVenta.getSerieBoleto(); 
				this._Numero =	(this.puntoVenta.getUltimoBoleto() + 1) + ""; 
			}
			
		this.empresaEncontrada = Boolean.FALSE;
		
		//Venta de Asientos individual
		if (this.listaAsientosxVender.size() == 1) 
		{
			
			try {
				asvSelec = this.asientoVentaService.findById(this.asientoVenta.getIdasientoventa());
				this.usuarioAsientoVenta=this.usuarioServices.buscarPorId(asvSelec.getIdusuarioventa());
				System.out.println("USUARIO "+usuarioAsientoVenta.getLogin());
				 if(asvSelec.getIdusuarioventa()==login.getIdUsuario()){					
					 respuesta=0;	
				 }
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			this.persona = new Persona();
			this.persona.setTipodocumento("DNI");
			
			this.rucDigitado = Boolean.FALSE;
			if(respuesta==0){
				context.execute("dlgVender.show()");				
			}else{
				context.execute("dlgAvisoCompra.show()");
			}

		//Venta de Asientos multiples	
		} else if (this.listaAsientosxVender.size() > 1) {
			this.boletoRuc = Boolean.FALSE;
			this.rucDigitado = Boolean.FALSE;
			this.bandMultiple=Boolean.TRUE;
//			for (AsientoVenta av: listaAsientosxVender) {
//				av.getPersona().setTipodocumento("DNI");
//				//System.out.println("el tipo de documento que entra es "+ av.getPersona().getTipodocumento());
//			}
			for (AsientoVenta av: listaAsientosxVender) 
			{
				try {
					 asvSelec = this.asientoVentaService.findById(av.getIdasientoventa());
					 
					 if(asvSelec.getIdusuarioventa()==login.getIdUsuario()){							
						 listaAsientosxVenderValidos.add(av);
						 //System.out.println("*** PUEDE VENDER ..."+login.getLogin());
					 }else{
						 this.usuarioAsientoVenta=this.usuarioServices.buscarPorId(asvSelec.getIdusuarioventa());
						 av.setUsuario(this.usuarioAsientoVenta);
						 listaAsientosxVenderNoValidos.add(av);
						 respuesta=1; //lista asientos no validos
					 }
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				av.getPersona().setTipodocumento("DNI");
				//System.out.println("el tipo de documento que entra es "+ av.getPersona().getTipodocumento());
			}
			
			listaAsientosxVender = new ArrayList<>(listaAsientosxVenderValidos);
			
			if(respuesta==1){
				context.execute("dlgAvisoCompra.show()");
				context.update("dlgDetVentaMulti");
			}else{
//				System.out.println("--------MUESTRA DIALOGO COMPRA ");
//				context.execute("dlgAvisoCompra.show()");
				context.update("dlgDetVentaMulti");
				context.execute("dlgVentaMulti.show()");
			}
			
			
//			context.execute("dlgAvisoCompra.show()");
//			
//				
//			context.update("dlgDetVentaMulti");
//			context.execute("dlgVentaMulti.show()");
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		System.out.println("FIN - mostrarPanelVentas");

	}
	 private void verificarFechaLimiteReserva() 
	 {
	        Integer horasDesdeSalida = null;
	        Integer horasCaducidadReserva = null;
	        try {
	            List<Perfil> perfiles = this.perfilServices.listarPerfilesxUsuario(usuarioLogin);
	            for (Perfil perfil : perfiles) {
	                if (perfil.getCod_perfil().equals(Perfil.ADMINISTRADOR) || perfil.getCod_perfil().equals(Perfil.JEFE_COUNTERS_PASAJES)) {
	                    horasDesdeSalida = 0;
	                }
	            }
	            if (horasDesdeSalida == null) {
	                horasDesdeSalida = Integer.parseInt(this.parametroServices.findParametro_byNombre("HORA_CADUCIDAD_RESERVA"));
	            }
	            horasCaducidadReserva = Integer.parseInt(this.parametroServices.findParametro_byNombre("DIAS_CADUCIDAD_RESERVA"));
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
	        String fechaSalidaString = df.format(this.programacionSelec.getfSalida());
	        DateFormat dfHora = new SimpleDateFormat("HH:mm:ss");
	        String horatring = dfHora.format(this.programacionSelec.getHora24());
	        fechaSalidaString = fechaSalidaString + " " + horatring;
	        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

	        Date fHorasalida = null;
	        try {
	            fHorasalida = sdf.parse(fechaSalidaString);
	        } catch (ParseException e1) {
	            e1.printStackTrace();
	        }
	        //comparando
	        Calendar now = Calendar.getInstance();
	        
	        Calendar calfHoraSalida = (Calendar) now.clone();
	        calfHoraSalida.setTime(fHorasalida);
	        
	        Calendar calfHoraReserva = (Calendar) now.clone();
	        calfHoraReserva.add(Calendar.HOUR_OF_DAY, horasCaducidadReserva);
	        
	        Calendar calfHoraMaximaReserva = (Calendar) now.clone();
	        calfHoraMaximaReserva.setTime(fHorasalida);
	        calfHoraMaximaReserva.add(Calendar.HOUR_OF_DAY, -horasDesdeSalida);
	        
	        if (calfHoraReserva.before(calfHoraMaximaReserva)) {
	            calfHoraMaximaReserva = calfHoraReserva;
	        }
	        if (now.before(calfHoraMaximaReserva)){
	            this.alertaReserva = Boolean.FALSE;
	        } else {
	            this.alertaReserva = Boolean.TRUE;
	        }
	        this.fechaActual = now.getTime();
	        this.fechaLimiteReserva = calfHoraMaximaReserva.getTime();
	        this.fechaMaximaSalida = fHorasalida;
	    }
	
	public void separarAsiento()
	{
		
		System.out.println("separando asientos");
		
		for( AsientoVenta asv : this.listaAsientosxVender)
		{
			asv.setEstado(ConstanteVentas.ESTADO_SEPARADO);
			asv.setIdusuarioventa(this.login.getUsuario().getIdusuario());
			asv.setOficinaId(this.login.getPv().getIdOficina());
			try {
				this.asientoVentaService.actualizarVentaAsiento(asv);
                                programacionSelec.setSeparados(programacionSelec.getSeparados() + 1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.listaAsientosxVender.clear();
		seleccionar();
	}

	
	public List<Programacion> listarProgramacion(Integer idOrigen,Integer idDestino, String fechaSel) throws Exception, ParseException {
		List<Programacion> lista = null;
		
		if(tipoItinerario == 1){
			lista = this.programacionService.findByOrigenDestinoAndFsalida(idOrigen, idDestino, fechaSel);	
		}else{
			lista = this.programacionService.findByOrigenDestinoAndFsalidaAllDay(idOrigen, idDestino, fechaSel);
		}
		// nivel cuatro: hora-salida
                
                /* jljimenez (Reemplazado por superquery en ProgramacionMapper.xml)
                
		Servicio serv = null;
		ClaseServicio clase = null;
		for (Programacion prog : lista) 
		{
			serv = this.buscarServicio(fechaSelec,this.agenciaOrigen.getIdagencia(),this.agenciaDestino.getIdagencia(), prog.gethSalida(),prog.getIdProgramacion());
			clase = this.claseServicioServices.findByServicio(serv.getIdServicio());
			
			TipoAsiento tipAs = null; 
			
			tipAs = this.tipoAsientoService.findById(clase.getIdtipoasientop1());
			prog.setTipoAsientoP1(tipAs);
			if(clase.getNroPisos().intValue()==2)
			{
				tipAs = this.tipoAsientoService.findById(clase.getIdtipoasientop2());
				prog.setTipoAsientop2(tipAs);
			}

			
			this.capacidad = clase.getAsientos();
			// obteniendo la cabtidad de asientos disponibles
			this.disponibles = this.asientoVentaService.countbyprogramacionAndEstado(prog.getIdProgramacion(),new String("LIBRE"),Boolean.TRUE);
			this.disponiblesP1 = this.asientoVentaService.countbyprogramacionAndEstadoXPiso(prog.getIdProgramacion(), "LIBRE", new Integer(1),Boolean.TRUE);
			this.disponiblesP2 = this.asientoVentaService.countbyprogramacionAndEstadoXPiso(prog.getIdProgramacion(), "LIBRE", new Integer(2),Boolean.TRUE);
			//this.reservados = this.asientoVentaService.countbyprogramacionAndEstado(prog.getIdProgramacion(),new String("RESERVA"));
			// obteniendo la clase de bus
			StringBuilder etiqueta = new StringBuilder();
			if (clase.getNroPisos() == 1) {
				etiqueta.append(prog.gethSalida());
				etiqueta.append(", ");
				etiqueta.append(serv.getDescripcion());
				//etiqueta.append(" ");
				//etiqueta.append("Cap.:");
				//etiqueta.append(clase.getAsientos());
				//etiqueta.append(" ");
				//etiqueta.append(" P1: S/.");
				//etiqueta.append(serv.getPrecio1());


			} else {
				etiqueta.append(prog.gethSalida());
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
			// etiqueta.append(this.disponibles);
			prog.setDisponibles(this.disponibles);
			prog.setDisponibleP1(this.disponiblesP1);
			prog.setDisponibleP2(this.disponiblesP2);
			prog.setEtiqueta(etiqueta.toString());
			prog.setServicio(serv);
			prog.setClase(clase);


		}*/

		return lista;
	}

	public void fechaSeleccionado() 
	{
		System.out.println("fechaSeleccionado");
		try {
			this.fechaSelec = new SimpleDateFormat("yyyy-MM-dd").format(temp);
			this.fechaString = new SimpleDateFormat("dd-MM-yyyy").format(temp);
			/*
			this.listaProgramacion = this.listarProgramacion(
			this.agenciaOrigen.getIdagencia(),
			this.agenciaDestino.getIdagencia(), this.fechaSelec);
			*/
			this.visibleBus = Boolean.FALSE;
			
			// listando programacion:
			this.listaProgramacion.clear();
			this.listaProgramacion = this.listarProgramacion(this.agenciaOrigen.getIdagencia(),this.agenciaDestino.getIdagencia(), this.fechaSelec);
                        
			/* transformar las horas de 24 a formato de 12 horas */
			for (int i = 0; i < this.listaProgramacion.size(); i++) {
				Programacion pro = listaProgramacion.get(i);
				pro.sethLlegadaAprox(calcaulateTime(pro.gethSalida(),pro.getHorasViaje()));
			}
			
			
			
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (Exception ef) {
			ef.printStackTrace();
		}
	}

	
        public void seleccionar()
	{
		System.out.println("programaion selec:"+this.programacionSelec.getIdProgramacion());
		this.pintarBus(this.programacionSelec);
                verificarFechaLimiteReserva();
	}
	
	public void pintarBus(Programacion pr) 
	{
		//this.programacionSelec = pr;
		
		this.manifiesto = Boolean.FALSE;
		this._rutaCompartida = pr.getDesRutaCompartida();
		this._idServicioCompartido = pr.getIdServicio();
		this._gradoServTramo = pr.getDesGradoServicio();
		this._intCorreEnlace = pr.getIntCorreEnlace();
		
		if (pr.getServicio() != null) 
		{
			pr.setSeleccionado(Boolean.TRUE);
			/* jljimenez
                        for (Programacion prog : listaProgramacion)
			{
			
				try {
					Programacion progPinta = this.programacionService.getStateSeats(prog.getIdProgramacion());
					
					this.disponibles = progPinta.getDisponibles();
					this.asi_vendidos = progPinta.getVendidos();
					this.asi_reservados = progPinta.getReservados();
					this.disponiblesP1 = progPinta.getDisponibleP1();
					this.disponiblesP2 = progPinta.getDisponibleP2();
					this.asi_separados = progPinta.getSeparados();
					prog.setDisponibles(this.disponibles);
					prog.setDisponibleP1(this.disponiblesP1);
					prog.setDisponibleP2(this.disponiblesP2);
					prog.setReservados(this.asi_reservados);
					prog.setVendidos(this.asi_vendidos);
					prog.setSeparados(this.asi_separados);
					
					prog.setCapacidadVerdadera(this.asientoVentaService.countCapacidadVerdadera(prog.getIdProgramacion(), Boolean.TRUE));
					
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
                        */
			// bus clase
			try {
//				this.busClase = this.claseServicioServices.findById(pr.getServicio().getIdClase());
				this.busClase = pr.getClase();

//				this.tipoAsientop1 = this.tipoAsientoService.findById(this.busClase.getIdtipoasientop1());
				this.tipoAsientop1 = pr.getTipoAsientoP1();
				
				if (this.busClase.getNroPisos().equals(2)) 
				{
//					this.tipoAsientop2 = this.tipoAsientoService.findById(this.busClase.getIdtipoasientop2());
					this.tipoAsientop2 = pr.getTipoAsientop2();
				}

				//buscando promociones asocidas de solo ida
				this.promocion = this.promocionServices.findByServicioIda(pr.getServicio().getIdServicio());
				
				if(this.promocion!=null)
				{
					//comparamos la afcehas de vigencia contra la fecha de compra actual
					if(this.fechaActual.after(this.promocion.getfInicio()) && this.fechaActual.before(this.promocion.getfFin()) )
					{
						this.tienePromocion= Boolean.TRUE;
						
						if(this.promocion.getTipo().equals("C")) //cantidad a descontar
						{
							pr.getServicio().setPrecio1(pr.getServicio().getPrecio1().subtract(new BigDecimal(this.promocion.getValorBoleto())) );
							pr.getServicio().setPrecio2(pr.getServicio().getPrecio2().subtract(new BigDecimal(this.promocion.getValorBoleto())) );
							
						}else if(this.promocion.getTipo().equals("F")){ //precio fijo a pagar
							
							pr.getServicio().setPrecio1(new BigDecimal(this.promocion.getValorBoleto()));
							pr.getServicio().setPrecio2(new BigDecimal(this.promocion.getValorBoleto()));
							
						}else if(this.promocion.getTipo().equals("P")){ //porcentaje a descontar

							pr.getServicio().setPrecio1(pr.getServicio().getPrecio1().multiply(new BigDecimal((1-this.promocion.getValorBoleto()/100))));
							pr.getServicio().setPrecio2(pr.getServicio().getPrecio2().multiply(new BigDecimal((1-this.promocion.getValorBoleto()/100))));
						}
					}else{
						this.tienePromocion= Boolean.FALSE;
					}
					
				}else{
					this.promocion = new Promocion();
					this.tienePromocion= Boolean.FALSE;
				}
				consultarConfiguracion(pr);
				this.visibleBus = Boolean.TRUE;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void consultarConfiguracion(Programacion pr) throws Exception 
	{
		this.nroColumnasPisoUno = this.busClase.getNroColumnasPisoUno();
		// this.anchoBusUno = 560;
		this.anchoBusDos = 860;
		// this.anchoBusDos = 700;

		// generando asientos para el piso uno siempre
		this.listaAsientosPisoUno = this.asientoService.findAsientoByProgramacion(pr.getIdProgramacion(), 1);
		/* jljimenez
                AsientoVenta asv = null;

		for (Asiento as : this.listaAsientosPisoUno) {
			asv = this.asientoVentaService.findAsientoByAsientoAndProgramacion(as.getIdAsiento(), as.getNumero(),pr.getIdProgramacion());
			as.setAsientoVenta(asv);

		}
                */
		// generando conf inicial para el piso dos
		if (this.busClase.getNroPisos() == 2) 
		{
			this.nroColumnasPisoDos = this.busClase.getNroColumnasPisoDos();
			this.listaAsientosPisoDos = this.asientoService.findAsientoByProgramacion(pr.getIdProgramacion(), 2);
                        /*
			for (Asiento as : this.listaAsientosPisoDos) {
				
				asv = this.asientoVentaService.findAsientoByAsientoAndProgramacion(as.getIdAsiento(),as.getNumero(),pr.getIdProgramacion());
				as.setAsientoVenta(asv);
                        
		}
                        */
		}

	}

	public List<Agencia> listarAgeDestino(Integer origenID) {
		List<Agencia> listaAgenciasDestino = new ArrayList<>();
		try {
		List<Destino> lista = new ArrayList<>();
			lista = this.destinoServices.obtenerDestino(origenID);
			for(Destino dest: lista){
				for (Agencia ag : this.listaAgOrigen) {
					if (dest.getDestino() == ag.getIdagencia()) {
						listaAgenciasDestino.add(ag);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaAgenciasDestino;
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

	public Date sumaDias(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days); // minus number would decrement the days
		return cal.getTime();
	}

	public void mostrarAsiento(Asiento as) 
	{
		System.out.println("INICIO - mostrarAsiento");
		Integer respuesta = -1;

		System.out.println("programacion seleccionada :" + this.programacionSelec.getIdProgramacion());
		this.asientoVenta = new AsientoVenta();
		this.noPromocionIdaVuelta = Boolean.FALSE;
		this.setDniDigitado(Boolean.FALSE);
		this.persona = new Persona();
		AsientoVenta asv = null;

		try {
			System.out.println("Prueba de bloqueo!!");
			// this.asientoVenta.setNroAsiento(Integer.parseInt(this.asientoVenta.getNumero()));
			System.out.println("** idAsiento " + as.getIdAsiento());
			System.out.println("** programacion " + as.getAsientoVenta().getIdProgramacion());
			System.out.println("** numero " + as.getNumero());
			// respuesta=this.asientoVentaService.transac_block_seat(this.asientoVenta.getIdProgramacion(),this.asientoVenta.getNumero(),null,this.asientoVenta.getIdAsiento(),null);
			asv = this.asientoVentaService.findEstadoAsientoByAsientoAndProgramacion(as.getAsientoVenta());
			respuesta = this.asientoVentaService.transac_block_seat_before(as.getAsientoVenta().getIdProgramacion(), as.getNumero(), as.getIdAsiento());
			System.out.println("** respuesta " + respuesta);

		} catch (Exception e1) {

			System.out.println("ERROR TRANSAC SQL " + e1.toString());
		}

		// try {
		// asv =
		// this.asientoVentaService.findEstadoAsientoByAsientoAndProgramacion(as.getAsientoVenta());
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		this.asientoSelec = as;
		this.asientoVenta = as.getAsientoVenta();
		this.asientoVenta.setHorareserva(new Date());
		this.asientoVenta.setfSalida(this.programacionSelec.getfSalida());
		this.asientoVenta.setPromocional(asv.getPromocional());
		this.asientoVenta.setIdusuarioautorizante(asv.getIdusuarioautorizante());
		try {
			this.usuarioAutorizante = this.usuarioAutorizanteServices
					.findById(asv.getIdusuarioautorizante());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		this.asientoVenta.setDesOficina(asv.getDesOficina());
		this.asientoVenta.setCompletoVendedor(asv.getCompletoVendedor());
		this.asientoVenta.setAsiento(as);
		this.asientoVenta.setEstado(asv.getEstado());
		this.asientoVenta.setSexo(asv.getSexo());
		this.asientoVenta.setDocumentoPersona(asv.getDocumentoPersona());
		this.asientoVenta.setDocumentoEmpresa(asv.getDocumentoEmpresa());
		this.asientoVenta.setNrodocprepagado(asv.getNrodocprepagado());
		this.tipoVenta = ConstanteVentas.ESTADO_VENTA;

		// try {
		// System.out.println("***** block asiento Venta");
		// this.asientoVenta.setNroAsiento(Integer.parseInt(this.asientoVenta.getNumero()));
		// System.out.println("** estado "+this.asientoVenta.getEstado());
		// System.out.println("** idAsiento "+this.asientoVenta.getIdAsiento());
		// System.out.println("** programacion "+this.asientoVenta.getIdProgramacion());
		// System.out.println("** numero "+this.asientoVenta.getNumero());
		// //
		// respuesta=this.asientoVentaService.transac_block_seat(this.asientoVenta.getIdProgramacion(),this.asientoVenta.getNumero(),null,this.asientoVenta.getIdAsiento(),null);
		// respuesta=this.asientoVentaService.transac_block_seat(this.asientoVenta);
		//
		// System.out.println("** respuesta "+respuesta);
		//
		//
		// } catch (Exception e1) {
		//
		// System.out.println("ERROR TRANSAC SQL "+e1.toString());
		// }

		if (this.asientoVenta.getEstado().equals("LIBRE") && respuesta == 0) 
		{
			System.out.println("**** respuesta OK");
			this.asientoVenta.setIdusuarioventa(this.login.getIdUsuario());
			this.asientoVenta.setIdAsiento(as.getIdAsiento());
			this.asientoVenta.setNumero(as.getNumero());
			this.asientoVenta.setPiso(as.getPiso());
			this.asientoVenta.setPersona(new Persona());
			this.asientoVenta.setIdProgramacion(this.programacionSelec.getIdProgramacion());
			this.asientoVenta.setRucBoolean(Boolean.FALSE);
			this.asientoVenta.setAsiento(as);
			if (as.getPiso().equals(1)) {  // obteniendo el precio del asiento:
				this.asientoVenta.setMonto(this.programacionSelec.getServicio().getPrecio1());
				this.asientoVenta.setCostoRealAsiento(this.programacionSelec.getServicio().getPrecio1());
				this.precioAsiento = this.asientoVenta.getMonto();
				this.precioAsientoPromocion = this.programacionSelec.getServicio().getPrecio1();
			} else {
				this.asientoVenta.setMonto(this.programacionSelec.getServicio().getPrecio2());
				this.asientoVenta.setCostoRealAsiento(this.programacionSelec.getServicio().getPrecio2());
				this.precioAsiento = this.asientoVenta.getMonto();
				this.precioAsientoPromocion = this.programacionSelec.getServicio().getPrecio2();
			}

			try { // obteniendo las series de boleto
				this.puntoVenta = this.puntoVentaService.findById(usuarioLogin.getIdpuntoventa());
				if (this.puntoVenta != null) {
					this._Serie = this.puntoVenta.getSerieBoleto();
					this._Numero = (this.puntoVenta.getUltimoBoleto() + 1) + "";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			this.listaAsientosxVender.add(this.asientoVenta);
			for (int x = 0; x < this.listaAsientosxVender.size(); x++) {
				System.out.println("setSerieBoletoMulti :" + this._Serie);
				System.out.println("setNumeroBoletoMulti :" + Integer.parseInt(this._Numero) + x);
				this.asientoVenta.setSerieBoletoMulti(this._Serie);
				this.asientoVenta.setNumeroBoletoMulti(Integer.parseInt(this._Numero) + x);
			}
			this.venderAsiento("PROCESO");
			this.estaLibre = Boolean.TRUE;
			this.persona = new Persona();
			this.empresa = new Empresa();
			actualizarDisponibilidadVenta(as, this.programacionSelec);// Actualizado disponibilidad
			
		//FORMULARIO INFORMACION DE LA VENTA 
		} else if (this.asientoVenta.getEstado().equals(ConstanteVentas.ESTADO_VENTA)|| this.asientoVenta.getEstado().equals(ConstanteVentas.ESTADO_RESERVA)
				|| this.asientoVenta.getEstado().equals(ConstanteVentas.ESTADO_SEPARADO)) {
			try {
				this.persona = this.personaService.findByNroDocumento(this.asientoVenta.getDocumentoPersona());

				if (this.asientoVenta.getDocumentoEmpresa() != null) {
					this.empresa = this.empresaService.findByNroRUC(this.asientoVenta.getDocumentoEmpresa());
				}

				if (this.asientoVenta.getNrodocprepagado() != null) {
					Persona per = this.personaService.findByNroDocumento(this.asientoVenta.getNrodocprepagado());
					this.pagador.setNombre(per.getAMaterno() + " " + per.getAPaterno() + " " + per.getNombres());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			// obteniendo las series de boleto
			try {
				// this.puntoVentaService.findById(usuarioLogin.getIdpuntoventa());
				this.trackingCodigo = this.trackBoletoService.searchByAsientoVenta(as.getAsientoVenta().getIdasientoventa(), as.getAsientoVenta().getIdAsiento());
				if (this.trackingCodigo != null) {
					this._Serie = this.trackingCodigo.getSerieBoleto();
					this._Numero = (this.trackingCodigo.getNumeroBoleto()) + "";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//this.nroMovimientosBoleto = this.trackBoletoService
			this.estaLibre = Boolean.FALSE;
			this.consultaRapida = Boolean.TRUE;
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("dlgVentaInfo.show()");
		
			//FORMULARIO ASIENTO TOMADO
		} else {
			this.estaLibre = Boolean.FALSE;
			// este asiento ya ha sido tomado:

			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("dlgTomado.show()");

		}
		this.editar = Boolean.FALSE;
		System.out.println("FIN - mostrarAsiento");
	}
	
	public void activarReserva(){
		Asiento as = this.asientoSelec;
		
		this.asientoVenta = new AsientoVenta();
		this.noPromocionIdaVuelta= Boolean.FALSE;
		this.setDniDigitado(Boolean.FALSE);
		this.persona = new Persona();
		AsientoVenta asv = null;
		try {
			asv = this.asientoVentaService.findEstadoAsientoByAsientoAndProgramacion(as.getAsientoVenta());
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.asientoSelec = as;
		this.asientoVenta = as.getAsientoVenta();
		this.asientoVenta.setHorareserva(new Date());
		this.asientoVenta.setfSalida(this.programacionSelec.getfSalida());
		this.asientoVenta.setPromocional(asv.getPromocional());
		this.asientoVenta.setIdusuarioautorizante(asv.getIdusuarioautorizante());
		try {
			this.usuarioAutorizante = this.usuarioAutorizanteServices.findById(asv.getIdusuarioautorizante());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		this.asientoVenta.setDesOficina(asv.getDesOficina());
		this.asientoVenta.setCompletoVendedor(asv.getCompletoVendedor());		
		this.asientoVenta.setAsiento(as);
		this.asientoVenta.setEstado(asv.getEstado());
		this.asientoVenta.setSexo(asv.getSexo());
		this.asientoVenta.setDocumentoPersona(asv.getDocumentoPersona());
		this.asientoVenta.setDocumentoEmpresa(asv.getDocumentoEmpresa());
		this.asientoVenta.setNrodocprepagado(asv.getNrodocprepagado());
		
		try {
			this.persona = this.personaService.findByNroDocumento(this.asientoVenta.getDocumentoPersona());
			this.personaEncontrada = Boolean.TRUE;

			if (this.asientoVenta.getDocumentoEmpresa() != null) {
				this.empresa = this.empresaService.findByNroRUC(this.asientoVenta.getDocumentoEmpresa());
			}
			
			if(this.asientoVenta.getNrodocprepagado()!=null)
			{
				Persona per =  this.personaService.findByNroDocumento(this.asientoVenta.getNrodocprepagado());
				this.pagador.setNombre(per.getAMaterno()+" "+per.getAPaterno()+" "+per.getNombres());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.tipoVenta = ConstanteVentas.ESTADO_VENTA;

		
		//SERIE DE BOLETOS
		//obteniendo las series de boleto
		try {
			
			//antes de setear _Serie y _Numero
			
			this.puntoVenta = this.puntoVentaService.findById(usuarioLogin.getIdpuntoventa());
		//	this.trackingCodigo = this.trackBoletoService.searchByAsientoVenta(as.getAsientoVenta().getIdasientoventa(),as.getAsientoVenta().getIdAsiento());
		//	if(this.trackingCodigo != null){
				this._Serie = this.puntoVenta.getSerieBoleto(); 
				this.puntoVenta.setUltimoBoleto(this.puntoVenta.getUltimoBoleto()+1);
				this._Numero = this.puntoVenta.getUltimoBoleto().toString(); 
		//	}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.estaLibre = Boolean.TRUE;

		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("dlgVender.show()");
		
	}

	
	/*
	public void mostrarReservarAsiento(Asiento as) 
	{
		this.dniDigitado = Boolean.FALSE;
		this.persona = new Persona();
		AsientoVenta asv = null;
		try {
			asv = this.asientoVentaService
					.findEstadoAsientoByAsientoAndProgramacion(as
							.getAsientoVenta());
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.asientoSelec = as;
		this.asientoVenta = as.getAsientoVenta();
		this.asientoVenta.setEstado(asv.getEstado());
		this.asientoVenta.setSexo(asv.getSexo());
		this.asientoVenta.setDocumentoPersona(asv.getDocumentoPersona());
		this.asientoVenta.setDocumentoEmpresa(asv.getDocumentoEmpresa());
		this.estaLibre = Boolean.TRUE;

		if (this.asientoVenta.getEstado().equals("LIBRE")) 
		{
			if (as.getPiso().equals(1)) 
			{
				this.precioAsiento = this.programacionSelec.getServicio().getPrecio1();
			} else {
				this.precioAsiento = this.programacionSelec.getServicio().getPrecio2();
			}
			this.estaLibre = Boolean.TRUE;
			this.venderAsiento("PROCESO");
			this.tipoVenta = "RESERVAR";
			this.persona = new Persona();
			this.empresa = new Empresa();
			
			//generando codigo de reserva
			PuntoVenta pv = null;
			Integer ultimaReserva = 0;
			
			
			try {
				pv = this.puntoVentaService.obtenerPuntoVenta(this.login.getPv().getIdPuntoVenta());
				
				ultimaReserva = pv.getUltimareserva() + 1;
				this.puntoVentaService.actualizarUltimaReserva(pv.getIdPuntoVenta(), ultimaReserva);
				 
				codigoReserva.append(this.login.getAgencia().getGrupo());
				codigoReserva.append("-");
				codigoReserva.append(this.login.getPv().getSeriereserva());
				codigoReserva.append("-");
				codigoReserva.append("0"+ultimaReserva);
				codigoReserva.toString();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			actualizarDisponibilidadVenta(as,this.programacionSelec);

		} else if (this.asientoVenta.getEstado().equals("VENTA") || this.asientoVenta.getEstado().equals("RESERVA")) {

			if (as.getPiso().equals(1)) {
				this.precioAsiento = this.programacionSelec.getServicio().getPrecio1();
			} else {
				this.precioAsiento = this.programacionSelec.getServicio().getPrecio2();
			}

			this.estaLibre = Boolean.FALSE;

		} else {
			this.estaLibre = Boolean.FALSE;
		}
	}

	*/
		
	public void mostrarInhabilitarAsiento() 
	{	Asiento as = this.asientoSelec;
		this.estaLibre = Boolean.TRUE;
		this.tipoVenta = "EDITAR";
		this.asientoSelec = as;
		this.asientoVenta = as.getAsientoVenta();
		this.personaNueva = new Persona();

		if (as.getPiso().equals(1)) {
			this.precioAsiento = this.programacionSelec.getServicio()
					.getPrecio1();
		} else {
			this.precioAsiento = this.programacionSelec.getServicio()
					.getPrecio2();
		}
		if (this.asientoVenta.getEstado().equals(ConstanteVentas.ESTADO_VENTA)
				|| this.asientoVenta.getEstado().equals(ConstanteVentas.ESTADO_RESERVA)) {

			try {
				this.persona = this.personaService
						.findByNroDocumento(this.asientoVenta
								.getDocumentoPersona());
				if (this.asientoVenta.getDocumentoEmpresa() != null) {
					this.empresa = this.empresaService
							.findByNroRUC(this.asientoVenta
									.getDocumentoEmpresa());
					this.boletoRuc = Boolean.TRUE;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void venderVarios(Asiento as) {
		/*
		 * AsientoVenta asv = null; try { asv =
		 * this.asientoVentaService.findEstadoAsientoByAsientoAndProgramacion
		 * (as.getAsientoVenta()); } catch (Exception e) { e.printStackTrace();
		 * } this.asientoSelec = as; this.asientoVenta = as.getAsientoVenta();
		 * this.asientoVenta.setEstado(asv.getEstado());
		 * //this.asientoVenta.setSexo(asv.getSexo());
		 * //this.asientoVenta.setDocumentoPersona(asv.getDocumentoPersona());
		 * //this.asientoVenta.setDocumentoEmpresa(asv.getDocumentoEmpresa());
		 * //this.tipoVenta = "VENDER";
		 * if(this.asientoVenta.getEstado().equals("LIBRE")) { //obteniendo el
		 * precio del asiento: if(as.getPiso().equals(1)) { this.precioAsiento =
		 * this.programacionSelec.getServicio().getPrecio1(); }else{
		 * this.precioAsiento =
		 * this.programacionSelec.getServicio().getPrecio2(); }
		 * this.venderAsiento("PROCESO"); this.estaLibre = Boolean.TRUE;
		 * //this.persona = new Persona(); //this.empresa = new Empresa();
		 * this.listaVentaVarios.add(this.asientoSelec);
		 * 
		 * }else if(this.asientoVenta.getEstado().equals("VENTA") ||
		 * this.asientoVenta.getEstado().equals("RESERVA")){ this.estaLibre =
		 * Boolean.FALSE; }else{ this.estaLibre = Boolean.FALSE; }
		 */

	}

	public void mostrarLiberarAsiento(Asiento as) {
		this.asientoSelec = as;
		this.asientoVenta = as.getAsientoVenta();
		if (as.getPiso().equals(1)) {
			this.precioAsiento = this.programacionSelec.getServicio()
					.getPrecio1();
		} else {
			this.precioAsiento = this.programacionSelec.getServicio()
					.getPrecio2();
		}

	}

	public void buscarPersona(AsientoVenta asiVent) 
	{
		

		for (int i = 0; i < this.listaAsientosxVender.size(); i++) {
			
			AsientoVenta asien = this.listaAsientosxVender.get(i);
			Persona per = asien.getPersona();
			
			System.out.println("la persona tiene un tipo de documento de " + per.getTipodocumento()); 
			
			
			System.out.println("el objeto asien es "+ asien.getNumero() );
			System.out.println("el tipo de documento del objeto es "+ asien.getPersona().getTipodocumento() );
			System.out.println("el asiento que se quiere vender es "+ asiVent.getNumero() );
			System.out.println("el tipo de documento del asiento es " + asiVent.getNumero()+" es " +asiVent.getPersona().getTipodocumento()) ;
			System.out.println("TIPO DE DOCUMENTO DE LA VISTA ES   " +asiVent.getPersona().getTipodocumento()) ;
			System.out.println("            " ) ;
			
			
		
			if(asien.getNumero().equals(asiVent.getNumero())){
			
				if(per.getTipodocumento().equals("DNI")){
					asiVent.getPersona().setTipodocumento("DNI");
					per.setTipodocumento("DNI"); 
					
					
					try {
						
						Persona p = this.personaService.findByNroDocumento(asiVent.getDocumentoPersona());
						
						if (p != null) 
						{
							//verificando
							ListaNegra indiv = null;
							try {
								indiv = listaNegraService.findByIndividuo(p.getDni());
								this.persona = p;
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if( indiv!= null)
							{
								this.estaListaNegra = Boolean.TRUE;
							}else{
								this.estaListaNegra = Boolean.FALSE;
							}
							
							asiVent.setSexo(p.getSexo());
							p.setUbicado(Boolean.TRUE);
							
							Integer anioNac= 0; 
							//calculo de la edad
							if( p.getNacimiento() != null)
							{
								 anioNac= Integer.parseInt(p.getNacimiento().substring(0, 4));
							}else{
								//Calendar fecha = new GregorianCalendar();
								 //anioNac= fecha.get(Calendar.YEAR);
							}
							
							
							Calendar fecha = new GregorianCalendar();
							Integer anioAct = fecha.get(Calendar.YEAR);
							p.setEdad(anioAct - anioNac);
							p.setTipodocumento(asiVent.getPersona().getTipodocumento());
							
							
							if(p.getEdad() < 18)
							{
								this.observacionesMultiple="Hay un menor de edad, debe presentar DNI y carta notarial";
							}else{
								this.observacionesMultiple="";
							}
							

						}else{
							p = new Persona();
							p.setTipodocumento(asiVent.getPersona().getTipodocumento());
							p.setDni(asiVent.getDocumentoPersona());
							
						}
						
						asiVent.setPersona(p);
						//actualizarTipoDocumento(asiVent);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					// this.personaEncontrada = Boolean.TRUE;
					
					
				}else{
					Persona p = new Persona(); 
					
					if(per.getTipodocumento().equals("PAS")){
						asiVent.getPersona().setTipodocumento("PAS");
						per.setTipodocumento("PAS"); 
						
					}else{
						asiVent.getPersona().setTipodocumento("CE");
						per.setTipodocumento("CE"); 
					}
						
				}
			
		
				System.out.println("ahora la persona tiene tipo de documento de  " + asiVent.getPersona().getTipodocumento() );
				System.out.println("            " ) ;
		
			
		}
		}
		
		
		
		
		
	}

	public void buscarPersonaSola() 
	{
		try {
		List<HistorialPasajero> viajes = this.historialPasajeroService.getNumberTravel(this.persona.getDni());
		if(viajes.size()>(Integer.parseInt(this.parametroServices.findParametro_byNombre("PASAJERO_FRECUENTE")))){
			this._nuevo = false;
			this._frecuencia = true;
			this._cantidadViajes = viajes.size();
			this._ultimaFechaViaje = Utiles.dateFormatToString(viajes.get(this._cantidadViajes-1).getfRegistro(),1);
		}else{
			this._frecuencia = false;
			this._nuevo = true;
			this._cantidadViajes = viajes.size();
			this._fechahoy= Utiles.dateFormatToString(this.fechaActual, 1);
			
		}
		
		this.persona = buscarPersona(this.persona.getDni(), this.persona.getTipodocumento());
		
		if(this.personaEncontrada && this.persona.getEdad()<Integer.parseInt(this.parametroServices.findParametro_byNombre("MENOR_EDAD_VENTAS"))){
			this.observacion = "Es menor de edad y debe presentar DNI y carta notarial";
		}else{
			this.observacion = null;
		}
		
		HistorialPasajero historialPasajero = new HistorialPasajero();
		historialPasajero = this.historialPasajeroService.findByNrocDNI(this.persona.getDni());
		if (historialPasajero!=null){
		if(historialPasajero.getNroRuc()!=null && !historialPasajero.getNroRuc().equals(""))
		{
		this.boletoRuc=true;
		this.setNroRuc(historialPasajero.getNroRuc());
		this.empresa.setRUC(historialPasajero.getNroRuc());
		this.buscarEmpresa();
		//recuperarUltimRuc();
		}else{
			this.setNroRuc(historialPasajero.getNroRuc());
			this.boletoRuc=false;
			this.empresaEncontrada=null;
		}
		}else{
			this.boletoRuc=false;
			this.empresaEncontrada=null;
		}
		
		//buscandio en la lista negra
		ListaNegra indiv = null;
		indiv = listaNegraService.findByIndividuo(this.persona.getDni());
		if( indiv!= null)
		{
			this.estaListaNegra = Boolean.TRUE;
		}else{
			this.estaListaNegra = Boolean.FALSE;
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//MODIFICACION 

		//System.out.println("***** El DNI de la persona es "+this.persona.getDni());

		AsientoVenta auxAsientoVenta=new AsientoVenta();
		String patron = "dd-MM-yyyy";
	    SimpleDateFormat formato = new SimpleDateFormat(patron);

		//MODIFICACION 
		for( Programacion prg:this.listaProgramacion){

			//System.out.println("---------programacion de "+prg.getIdProgramacion());
			if(prg!=null){
			try {
				auxAsientoVenta=this.asientoVentaService.findPasajeroByProgramacion(prg.getIdProgramacion(), this.persona.getDni());
				if(auxAsientoVenta!=null){

					//System.out.println("-------------"+auxAsientoVenta.getIdasientoventa());
                     System.out.println("ENTRA BUSCA PERSONA SOLA FOR PROGRAMACION ");
					this.pasajeroReg=Boolean.TRUE;				
					this.fechaCompraBoleto=formato.format(auxAsientoVenta.getFechaventa());
					this.servicioCompra = this.servicioService.findById(prg.getIdServicio());
					this.horaSalidaCompra=prg.gethSalida();
					break;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			}
		}
	}

	public void buscarPersonaNueva() 
	{
		this.personaNueva = buscarPersona(this.personaNueva.getDni(), this.personaNueva.getTipodocumento());
	}
		
	public Persona buscarPersona(String dni, String tipDoc) {
		Persona per = null;
		System.out.println("DNI: : "+dni);
		System.out.println("Documento: : "+tipDoc);
		try {
			per = this.personaService.findByNroDocumento(dni.trim());
			
			// this.persona = new Persona();
			if (per != null) {

				// calculo de la edad
//				Integer anioNac = Integer.parseInt(per.getNacimiento()
//						.substring(0, 4));
//				Calendar fecha = new GregorianCalendar();
//				Integer anioAct = fecha.get(Calendar.YEAR);
//				
//				this.operacionCalculoEdad(per.getNacimiento());
				System.out.println("Nueva forma de calcular la edad: "+per.getNacimiento());
                if (per.getNacimiento() != null) {
                    per.setEdad(this.operacionCalculoEdad(new SimpleDateFormat("yyyyMMdd").parse(per.getNacimiento().trim())));
                    if (per.getEdad() < 18) {
                    	this.observacion = "El asiento " + this.asientoVenta.getNumero() + " es menor de edad, debe presentar DNI y carta notarial";
                    }
                } else {
					this.observacion = "";
				}

				this.personaEncontrada = Boolean.TRUE;

			} else {
				System.out.println("Persona No encontrada: ");
				this.personaEncontrada = Boolean.FALSE;
				per = new Persona();
				per.setTipodocumento(tipDoc);
				per.setDni(dni);
				this.observacion = "";

			}
			this.setDniDigitado(Boolean.TRUE);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}

		return per;
	}
		
	public void calculaEdad()
	{
		//calculo de la edad
		//System.out.println("calculando la edad");
		this.persona.setEdad(this.operacionCalculoEdad(this.fechaCumpleAnios));
		
		if(this.persona.getEdad() < 18)
		{
			this.observacion="El asiento "+this.asientoVenta.getNumero()+" es menor de edad, debe presentar DNI y carta notarial";
		}else{
			this.observacion="";
		}
	}
	
	public void calculaEdadNuevo()
	{
		//calculo de la edad
		//System.out.println("calculando la edad");
		this.personaNueva.setEdad(this.operacionCalculoEdad(this.fechaCumpleAnios));
		
		if(this.personaNueva.getEdad() < 18)
		{
			this.observacion="El asiento "+this.asientoVenta.getNumero()+" es menor de edad, debe presentar DNI y carta notarial";
		}else{
			this.observacion="";
		}
	}
	
	public void calculaEdadPersona(Persona per)
	{
		System.out.println("calulando al edad multiple");
		per.setEdad(this.operacionCalculoEdad(per.getFnac()));
		
		if(per.getEdad() < 18)
		{
			this.observacionesMultiple="Hay un menor de edad, debe presentar DNI y carta notarial";
		}else{
			this.observacionesMultiple="";
		}
		
		//persistiendo o actualizando la persona
		
		
		//a?adiendo los no ubicados
		if(!per.getUbicado())
		{
			//setetando digito y naciemiento
			per.setDigito(per.getSexo().equals("M") ? "1":"2");

			//build nacimiento: 19510924  
			String anio;
			Integer mes;
			String dia;
			
			Calendar calendario = Calendar.getInstance();
			calendario.setTime(per.getFnac());
			anio = calendario.get(Calendar.YEAR)+"";
			mes = calendario.get(Calendar.MONTH);
			mes++;
			
			dia = calendario.get(Calendar.DAY_OF_MONTH)+"";
			if(Integer.parseInt(dia) < 10)
			{
				dia= "0" + dia;
			}
			
			StringBuilder str = new StringBuilder();
			str.append(anio);
			
			if(mes < 10)
			{
				str.append("0"+mes);
			}else{
				str.append(mes);
			}
			
			str.append(dia);
			per.setNacimiento(str.toString());
			per.setAMaterno(per.getAMaterno().trim().toUpperCase());
			per.setAPaterno(per.getAPaterno().trim().toUpperCase());
			per.setNombres(per.getNombres().trim().toUpperCase());
			
			try {
				this.personaService.crearPersona(per);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else{ //actualizando solo fecha de nacimiento, paterno, materno, nombres y sexo
			
			try {
				this.personaService.actualizarPersona(per);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	

	
	public Integer operacionCalculoEdad(Date fechaNac)
	{
		System.out.println("Fecha: "+fechaNac);
		Calendar birth = new GregorianCalendar();
		Calendar today = new GregorianCalendar();
		int age = 0;
		int factor = 0;
		//Date birthDate = new SimpleDateFormat("dd-MM-yyyy").parse(datetext);
		Date currentDate = new Date(); // current date
		birth.setTime(fechaNac);
		today.setTime(currentDate);
		if (today.get(Calendar.MONTH) <= birth.get(Calendar.MONTH)) {
			if (today.get(Calendar.MONTH) == birth.get(Calendar.MONTH)) {
				System.out.println("Today Date: "+today.get(Calendar.DATE));
				System.out.println("Birth Date: "+birth.get(Calendar.DATE));
				if (today.get(Calendar.DATE) > birth.get(Calendar.DATE)) {
					factor = -1; // Aun no celebra su cumpleaños
				}
			} 
			
			else {
				factor = -1; // Aun no celebra su cumpleaños
			}
		}
		age = (today.get(Calendar.YEAR) - birth.get(Calendar.YEAR))
				+ factor;
		return age;

		// Integer edad = 0;
		// Calendar calendario = Calendar.getInstance();
		// calendario.setTime(fechaNac);
		// Integer anioNac = calendario.get(Calendar.YEAR);
		//
		//
		// Calendar calendario2 = Calendar.getInstance();
		// calendario2.setTime(new Date());
		// Integer anioAct = calendario2.get(Calendar.YEAR);
		// edad = anioAct - anioNac;
		// return edad;
	}
	
	
	
	/*
	public void cambiarTextoBoton()
	{
		this.fechaLimiteReserva = new Date();
		switch(this._tipoVenta)
		{
			case "1" : this.tipoVenta = ConstanteVentas.ESTADO_VENTA;break;
			case "2" : this.tipoVenta = ConstanteVentas.ESTADO_RESERVA;break;
			case "3" : this.tipoVenta = ConstanteVentas.ESTADO_SEPARADO;break;
			default: this.tipoVenta = ConstanteVentas.ESTADO_VENTA;
		}
		PuntoVenta ptv = new PuntoVenta();
		try {
			ptv = this.puntoVentaService.getPuntoVentaxUsuario(usuarioLogin.getIdusuario());
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
		if(this._tipoVenta.equals("1")) //venta
		{	
			this._Serie=ptv.getSerieBoleto();
			this._Numero=String.valueOf((ptv.getUltimoBoleto()+1));
			this._bandHoraReserva = Boolean.FALSE;
		}else if(this._tipoVenta.equals("2")) //reservar
		{	
			this._bandHoraReserva = Boolean.TRUE;	    
			Integer horaParametro = null;
			try {
				horaParametro = Integer.parseInt(this.parametroServices.findParametro_byNombre("HORA_CADUCIDAD_RESERVA"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			this._Serie=ptv.getSeriereserva();
			this._Numero=String.valueOf((ptv.getUltimareserva()+1));

                        DateFormat df  = new SimpleDateFormat("dd-MM-yyyy");
			String fechaString = df.format(this.programacionSelec.getfSalida());
			DateFormat dfHora  = new SimpleDateFormat("HH:mm:ss");
			String horatring = dfHora.format(this.programacionSelec.getHora24());
			fechaString = fechaString +" "+ horatring;
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			
			//restando las horas a la fecha de reserva 
			//Integer horasalidaActual = Integer.parseInt(horatring.substring(0,2));
			
			
			Date fHorasalida =null;
			try {
				fHorasalida = sdf.parse(fechaString);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			//comparando
			Calendar calfHoraSalida = Calendar.getInstance();
			Calendar calfHoraReserva = Calendar.getInstance();
			calfHoraSalida.setTime(fHorasalida);
                        
                        // jljimenez {
                        
			calfHoraReserva.setTime(fHorasalida);
			calfHoraReserva.add(Calendar.HOUR_OF_DAY, -horaParametro);
//			Long milSalida = calfHoraSalida.getTimeInMillis();
//			Long milReserva = calfHoraReserva.getTimeInMillis();
//			Long diffEnHoras= Math.abs((milReserva - milSalida))/(60*60*1000);

                        //comparando
			Calendar now = Calendar.getInstance();
			if(now.before(calfHoraReserva)){//A tiempo para hacer la reserva
                            
				this.alertaReserva = Boolean.FALSE;
				String horaM24 = Utils.timeAString(this.programacionSelec.getHora24());  
				
//				if(diffEnHoras > 120){ //mayor que periodo largo: actualmente 5 dias
//					calfHoraSalida.add(Calendar.HOUR, -24); //la fceha de caducidad es un dia antes
//					this.reservaEnElDia = Boolean.FALSE;
//					Calendar cal = Calendar.getInstance(); // creates calendar
//				    cal.setTime(this.programacionSelec.getfSalida()); // sets calendar time/date
//				    cal.add(Calendar.HOUR_OF_DAY, Integer.parseInt(horaM24.substring(0,2))); // adds one hour
//				    //cal.add(Calendar.MINUTE,Integer.parseInt(horaM24.substring(3,5)));//minutos
//					this.fechaMaximaSalida =  cal.getTime(); //
//				}
                                
                                double horasRestantes = (calfHoraReserva.getTimeInMillis() - now.getTimeInMillis()) / (1000 * 60 * 60);
				if(horasRestantes >= 24){
					//calfHoraSalida.add(Calendar.HOUR, -horaParametro); //para reservas de priodo corto la fecha d ecaducidad es de dos horas antes de hora de salida                                    
					this.reservaEnElDia = Boolean.FALSE;
					
//					Calendar cal = Calendar.getInstance(); // creates calendar
//				    cal.setTime(this.programacionSelec.getfSalida()); // sets calendar time/date
//				    cal.add(Calendar.HOUR_OF_DAY, Integer.parseInt(horaM24.substring(0,2))); // adds one hour
//				    //cal.add(Calendar.MINUTE,Integer.parseInt(horaM24.substring(3,5)));//minutos
					this.fechaMaximaSalida =  calfHoraSalida.getTime(); //
					
				}else if(horasRestantes<24) //reservaen el dia
				{
//					calfHoraSalida.add(Calendar.HOUR, -horaParametro);
					this.reservaEnElDia = Boolean.TRUE;
					//limitando el tiempo
					this.horaMaximaReserva = calfHoraReserva.get(Calendar.HOUR_OF_DAY);
					this.minutoMaximaReserva = calfHoraReserva.get(Calendar.MINUTE);
				}		
                                
                                // } jljimenez
                                
				//this.fechaLimiteReserva = calfHoraSalida.getTime();
				
				this.fechaLimiteReserva = calfHoraReserva.getTime();
				
				for(AsientoVenta av : this.listaAsientosxVender)
				{
					
					PuntoVenta pv = null;
					Integer ultimaReserva = 0;
					try {
						
						pv = this.puntoVentaService.obtenerPuntoVenta(this.login.getPv().getIdPuntoVenta());
						ultimaReserva = pv.getUltimareserva() + 1;
						this.puntoVentaService.actualizarUltimaReserva(pv.getIdPuntoVenta(), ultimaReserva);
						 
						StringBuilder codReserv = new StringBuilder();
						codReserv.append(this.login.getAgencia().getGrupo());
						codReserv.append("-");
						codReserv.append(this.login.getPv().getSeriereserva());
						codReserv.append("-");
						codReserv.append("0"+ultimaReserva);
						av.setCodigoReserva(codReserv.toString());
						 
					} catch (Exception e) {
						e.printStackTrace();
					}
					//en lugar de: av.serieBoletoMulti} #{av.numeroBoletoMulti
				}
			}else{
				this.alertaReserva = Boolean.TRUE;
			}
			
		}else if(this._tipoVenta.equals("3")){
		
			System.out.println("entro en separacion");
		}

	}
	*/
	
	
	public Date sumarRestarHorasFecha(Date fecha, int horas){
		       Calendar calendar = Calendar.getInstance();
		       calendar.setTime(fecha); // Configuramos la fecha que se recibe
		       calendar.add(Calendar.HOUR, horas);  // numero de horas a a?adir, o restar en caso de horas<0
		       return calendar.getTime(); // Devuelve el objeto Date con las nuevas horas a?adidas
	}
	
	
	public void visualizarPagador()
	{
		//RequestContext context = RequestContext.getCurrentInstance(); 
		this.delivery = new PagadorPrepagado();
		
		if(this._tipoEntrega.equals("DELIVERY"))
		{
			// validando si es mayor por 3 horas antes de la hora de salida
			Integer horaParametro = null;
			try {
				horaParametro = Integer.parseInt(this.parametroServices.findParametro_byNombre("TIEMPO_MINIMO_DELIVERY"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			String fechaProg = Utiles.dateFormatToString(this.programacionSelec.getfSalida(), 1);
			String horaProg = Utiles.dateFormatToString(this.programacionSelec.getHora24(), 2);
			
			String fecHorProgString = fechaProg + " " + horaProg;
			System.out.println("fecha salida " + fecHorProgString);
			
			Date fechaHoraProgr = null;
			try {
				fechaHoraProgr = Utiles.stringFormatToDate(fecHorProgString, 1);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			// falta restarle la fecha - 3
			Long diferenciaHoras = Utiles.getDiferenciasenHoras(new Date(), fechaHoraProgr);
			
			if(diferenciaHoras>= horaParametro)
			{
				// restandole  horas a la hora de salida
				String horaM24 = Utils.timeAString(Utiles.agregarQuitarHoras(this.programacionSelec.getHora24(),-horaParametro));  
				
				this.deliveryVenta  = Boolean.TRUE;
				System.out.println("HORAS " +diferenciaHoras);
				System.out.println("Hora parametro " +horaParametro);
				System.out.println("fecha de salida " + this.programacionSelec.getfSalida());
				
				this.deliveryEnElDia = Boolean.TRUE;
				this.horaMaximaDelivery = Integer.parseInt(horaM24.substring(0,2));
			
				
				//this.fechaEntregaDelivery = Utiles.agregarQuitarHoras(fechaHoraProgr, -horaParametro);
				this.fechaEntregaDelivery = new Date();
				
				this.montoDelivery = this.asientoVenta.getMonto();
				
			} else{
				System.out.println("No se puede realizar esta operacion dentro de 3 horas para la salida del bus");
				 FacesUtils.showFacesMessage("No se puede realizar esta operacion dentro de 3 horas para la salida del bus.",Constante.ERROR);
				
			}
			
			RequestContext.getCurrentInstance().execute("wdlgDelivery.show();");
			
			
			
		}else if(this._tipoEntrega.equals("OFICINA")){
			this.oficinaVenta = Boolean.TRUE;
			this.deliveryVenta= Boolean.FALSE; 
		}else{//traf bancaria
			this.transBancariaVenta = Boolean.TRUE;
		}
	}
	
	public void buscarEmpresa() 
	{
		System.out.println("--------ingreso a buscar empresa");
		this.empresaEncontrada = Boolean.FALSE;
		String ruc = getNroRuc().trim();
		System.out.println("--------su RUC es "+ruc);
		this.auxRUC = ruc;
		
		//String ruc = this.empresa.getRUC().trim();
		System.out.println("********************** auxRuc es "+this.auxRUC);
		
	
		try {
			//this.empresa = this.empresaService.findByNroRUC(ruc);
			if(this.auxRUC!=null){
				Empresa auxEmp=this.empresaService.findByNroRUC(this.auxRUC);
			
			//if(this.empresa != null)
			if(auxEmp != null)	
			{
				//System.out.println("buscando a emPRESA :" + this.empresa.getRUC());
				System.out.println("buscando a emPRESA :" + auxEmp.getRUC());
				this.setNroRuc(ruc);
				this.setAuxRSocial(auxEmp.getRazonSocial());
				//this.empresa.setRazonSocial(this.empresa.getRazonSocial());
				//System.out.println("La raz?n social es: "+this.empresa.getRazonSocial());
				System.out.println("La raz?n social es: "+auxEmp.getRazonSocial());
				this.empresa=auxEmp;
				this.empresaEncontrada = Boolean.TRUE;
				
				
			}else{
				//this.empresa = new Empresa();
				auxEmp=new Empresa();
				//this.empresa.setRUC(ruc);
				
				//this.setNroRuc(ruc);
				//this.auxRUC="";
				//this.auxRSocial="";
				this.empresa=new Empresa();
				//this.nroRuc="";
			}
			
			this.rucDigitado = Boolean.TRUE;
			}
			if(ruc.equalsIgnoreCase("")){
				System.out.println("*********NO SE DIGITO RUC");
				this.auxRSocial="";
				this.auxRUC="";
				this.rucDigitado = Boolean.FALSE;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * @Desc: busca empresa
	 * @Auth:W
	 */
	public void buscarEmpresaInhabilitado() 
	{
		this.empresaEncontrada = Boolean.FALSE;
		try{
			
			this.empresa = this.empresaService.findByNroRUC(this.empresa.getRUC().trim());
			if(this.empresa != null && !this.empresa.getRUC().trim().isEmpty())
			{
				this.empresaEncontrada = Boolean.TRUE;
			}else{
				this.empresa = new Empresa();
			}
		}catch(Exception e)
		{
			System.out.println("error buscando la empresa");
			e.printStackTrace();
		}

	}

	/**
	 * codigo nuevo
	 * */

	public void venderAsientosMultiple(){
		System.out.println("INGRESO A VENTA MULTIPLE");

		//vendiendo los boletos
		HistorialPasajero historial = null;
		try {
			if(this.boletoRuc && !this.empresaEncontrada)  //boleto con ruc
			{
				//persisitiendo
				this.empresa.setRazonSocial(this.empresa.getRazonSocial().trim().toUpperCase());
				this.empresa.setFRegistro(new Date());
				this.empresa.setEstado(Boolean.TRUE);
				this.empresaService.crearEmpresa(empresa);
			}
			
			for(AsientoVenta asien : this.listaAsientosxVender)
			{
				historial = new  HistorialPasajero();
				
				asien.setObservacion(this.observacionesMultiple);
				asien.getPersona().setDni(asien.getDocumentoPersona());
				asien.setSexo(asien.getPersona().getSexo());
				asien.setFechaventa(new Date());
				asien.setTipoventa(ConstanteVentas.VENTA_DIRECTA);
				asien.setDocumentoPersona(asien.getDocumentoPersona());
				asien.setDocumentoEmpresa(asien.getDocumentoEmpresa());
				asien.setExterno(this.esOficinaExterna);
				asien.setOficinaId(this.esOficinaExterna ? this.oficinaExterna.getIdOficina() :  this.login.getPv().getIdOficina());
				asien.setEstado(this.tipoVenta);
				
				if(this.tipoVenta.equals(ConstanteVentas.ESTADO_RESERVA)){
					asien.setHorareserva(this.fechaLimiteReserva);
				}
				
				if(this.boletoRuc)
				{
					asien.setEmpresa(this.empresa);
					asien.setDocumentoEmpresa(this.empresa.getRUC());
				}
				
				//prepagadpo
				if(this.boletoPrepagado)
				{
					asien.setPrepagado(Boolean.TRUE);
					asien.setTipdocprepagado(this.pagador.getTipoDoc());
					asien.setNrodocprepagado(this.pagador.getNroDoc());		
					asien.setEstado_delivery(ConstanteVentas.NO_IMPRESO_PREPAGADO);
				}
				
				//delivery
				if(this.deliveryVenta)
				{
					asien.setDelivery(Boolean.TRUE);
					asien.setTipdocdelivery(this.delivery.getTipoDoc());
					asien.setNrodocdelivery(this.delivery.getNroDoc());	
					asien.setDireccion(this.delivery.getDireccion());
					
					//nuevos campos agregados
					asien.setEstado_delivery(ConstanteVentas.NO_ENTREGADO_DELIVERY);
					asien.setReferenDelivery(this.delivery.getReferencia());
					asien.setTelefDelivery(this.delivery.getTelefono());
					this.fechaEntregaDelivery = Utiles.agregarFechaAHora(this.programacionSelec.getfSalida(), this.fechaEntregaDelivery);
					asien.setFechaEntregaDelivery(this.fechaEntregaDelivery);
					
					
				}
				
				if(this.esPromocional)
				{
					asien.setPromocional(Boolean.TRUE);
					asien.setIdusuarioautorizante(this.promocion.getIdAutorizante());
					asien.setMonto(this.costoBoletoPromocional);
					asien.setIdpromocion(null);
				}else {
					this.asientoVenta.setPromocional(Boolean.FALSE);
				}
				
				//actualizando
				asien.setHorareserva(this.fechaLimiteReserva);
				//modificacion jtarazona
				asien.setTransferenciaBanco(this.transferenciaBanco);
				asien.setTransferenciaNumero(this.transferenciaNumero);
				
				System.out.println("--> actualizando Asiento Venta "+asien.getIdProgramacion());
				this.asientoVentaService.actualizarVentaAsiento(asien);
				
				if (this._rutaCompartida.equals("SI")) {
					this.asientoVentaService.updateAssociatedSeats(asien);
				}else if(this._intCorreEnlace>0){
					this.asientoVentaService.updateAssociatedSeats(asien);
				}
				
				Double monto = new Double(asien.getMonto().toString());
				
				if(this.deliveryVenta==false){
					registrarLiquidacionBoleto(monto,this._tipoPago,this._tipoEntrega,asien.getSerieBoletoMulti(),asien.getNumeroBoletoMulti(),"MULTIPLE");	
				}
				
				/*registrando historial pasajero*/
				historial.setAsientoventaid(asien.getIdasientoventa());
				historial.setDni(asien.getDocumentoPersona());
				historial.setfRegistro(new Date());
				historial.setIdOficina(usuarioLogin.getIdoficina());
				historial.setIdprogramacion(this.programacionSelec.getIdProgramacion());
				historial.setIdservicio(this.programacionSelec.getIdServicio());
				historial.setSerieBoleto(this._Serie);
				historial.setOperacion(this.tipoVenta);
				historial.setNumeroBoleto(Integer.parseInt(this._Numero));
				historial.setNroRuc(asien.getDocumentoEmpresa());
				historial.setPiso(asien.getPiso());
				historial.setNroasiento(Integer.parseInt(asien.getNumero()));
				historial.setMonto(asien.getMonto());
				historial.setPuntoVentaId(this.login.getPv().getIdPuntoVenta());
				
				
				this.historialPasajeroService.crearHistorialPasajero(historial);
				
				//registrarHistorialPasajero(asien.getDocumentoPersona(),asien.getSerieBoletoMulti(),asien.getNumeroBoletoMulti(),this.empresa.getRUC());
				
				//registrar traking boleto 
				TrackingBoleto track = new TrackingBoleto();
				//registrar tracking de boleto:
				track.setSerieBoleto(asien.getSerieBoletoMulti());
				//track.setNumeroBoleto(this.asientoVenta.getNumero());
				track.setNumeroBoleto(asien.getNumeroBoletoMulti().toString());
				track.setFregistro(new Date());
				track.setIdusuario(this.login.getUsuario().getIdusuario());
				track.setIdasiento(asien.getIdAsiento());
				track.setIdasientoventa(asien.getIdasientoventa());
				track.setEstado(this.tipoVenta);
				track.setIdprogramacion(this.programacionSelec.getIdProgramacion());
				track.setIdservicio(this.programacionSelec.getIdServicio());
				track.setNrodocumento(asien.getDocumentoPersona());
				if(this.empresa != null){
					track.setNrodocempresa(this.empresa.getRUC());	
				}
				
				this.trackBoletoService.creartrack(track);
			}
			
			if(!this.listaAsientosxVender.isEmpty())
			{
				this.puntoVentaService.actualizarUltimoBoleto(this.puntoVenta.getIdPuntoVenta(),this.listaAsientosxVender.get(this.listaAsientosxVender.size()-1).getNumeroBoletoMulti());
				
			}

			//this.puntoVentaService.actualizarUltimoBoleto(this.puntoVenta.getIdPuntoVenta(),this.listaAsientosxVender.get(this.listaAsientosxVender.size()-1).getNumeroBoletoMulti());
			this._tipoPago = "EFECTIVO";
			this._tipoEntrega = "OFICINA";
			
			List <AsientoVenta> asientoAux = new ArrayList<AsientoVenta>(this.listaAsientosxVender);
			
			this.listaAsientosxVender.clear();

			if(this.boletoPrepagado) //mandar boleta de pago de 1 sol
			{
				//this.mandarImpresionBoletaPRepagado(asientoAux); hacer la mims a que en boletos simples
			}else{//manda boleto
				this.mandarImpresion(asientoAux,Boolean.TRUE);
			}
			//RequestContext context = RequestContext.getCurrentInstance();
			//context.execute("dlgVentaMulti.hide()");
			
			this.listaCmbPromociones = new ArrayList<>();
			this.enablePromocion = Boolean.FALSE;
			this.tienePromocion= Boolean.FALSE;
			this.idPromocionSelect = null;
			this.promocion = new Promocion();
			this.dsctoBoletoMultiple = "";
			this._bandHoraReserva = Boolean.FALSE;
			this.boletoRuc = Boolean.FALSE;
			this.rucDigitado = Boolean.FALSE;
			//this._tipoVenta = "1";
			this.listaAgenciasExternas.clear();
			this.listaOficinasExternas.clear();
			this.esOficinaExterna = Boolean.FALSE;
			this.agenciaExterna = new Agencia();
			this.oficinaExterna = new Oficina();
			
			this.esPromocional = Boolean.FALSE;
			this.boletoPrepagado = Boolean.FALSE;
			this.deliveryVenta = Boolean.FALSE;
			this.deliveryEnElDia = Boolean.FALSE;
			this.pagador = new PagadorPrepagado();
			this.delivery = new PagadorPrepagado();
			
			//modificacion 
			this.transferenciaBanco="";
			this.transferenciaNumero="";
			seleccionar();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.observacionesMultiple = null;

	}
	
	public void venderAsiento(String operacion)
	{
		
		System.out.println("INICIO - venderAsiento");
		System.out.println("Programacion Seleccionada = "+programacionSelec.getIdProgramacion());
			
		TrackingBoleto  track = new TrackingBoleto();
		HistorialPasajero historial = new HistorialPasajero();
		
		historial.setTipoEntrega(this._tipoEntrega);
		historial.setPrepagado(Boolean.FALSE);
		
		try {
			
			//**************** VENTA DEL ASIENTO *********************************
			if (operacion.equals(ConstanteVentas.ESTADO_VENTA))
			{
				System.out.println("INGRESA A ESTADO_VENTA");
				this.empresa.setRUC(this.nroRuc);
                System.out.println("BOLETO RUC: " + this.boletoRuc); //registrando empresa si no lo hay
                System.out.println("EMPRESA ENCONTRADA: " + this.empresaEncontrada);
                
				if(this.boletoRuc)
				{
					try {
                        this.empresa.setRazonSocial(this.empresa.getRazonSocial().trim().toUpperCase());
						if (!this.empresaEncontrada) {
							this.empresa.setFRegistro(new Date());
							this.empresaService.crearEmpresa(this.empresa);
                        } else {
                        	this.empresaService.updateEmpresa(this.empresa);
                        }
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				//capturando datos del prepagado
				if(this.boletoPrepagado)
				{
					this.asientoVenta.setPrepagado(Boolean.TRUE);
					this.asientoVenta.setTipdocprepagado(this.pagador.getTipoDoc());
					this.asientoVenta.setNrodocprepagado(this.pagador.getNroDoc());
					this.asientoVenta.setEstado_delivery(ConstanteVentas.NO_IMPRESO_PREPAGADO);
					historial.setPrepagado(Boolean.TRUE);
				}
				if(this.deliveryVenta)
				{
					this.asientoVenta.setDelivery(Boolean.TRUE);
					this.asientoVenta.setTipdocdelivery(this.delivery.getTipoDoc());
					this.asientoVenta.setNrodocdelivery(this.delivery.getNroDoc());
					this.asientoVenta.setDireccion(this.delivery.getDireccion());
					//nuevos campos agregados
					this.asientoVenta.setEstado_delivery(ConstanteVentas.NO_ENTREGADO_DELIVERY);
					this.asientoVenta.setReferenDelivery(this.delivery.getReferencia());
					this.asientoVenta.setTelefDelivery(this.delivery.getTelefono());
					this.fechaEntregaDelivery = Utiles.agregarFechaAHora(this.programacionSelec.getfSalida(), this.fechaEntregaDelivery);
					this.asientoVenta.setFechaEntregaDelivery(this.fechaEntregaDelivery);
				}

				this.asientoVenta.setEstado(ConstanteVentas.ESTADO_VENTA);
				this.asientoVenta.setFechaventa(new Date());
				//this.asientoVenta.setMonto(this.tienePromocion?this.precioAsientoPromocion :this.precioAsiento);
				this.asientoVenta.setIdpromocion(this.tienePromocion?this.getPromocion().getIdPromocion() :null);
				
				
				this.asientoVenta.setMonto(this.tienePromocion?this.precioAsientoPromocion :this.precioAsiento);
				this.asientoVenta.setMontoReal(this.tienePromocion?this.precioAsientoPromocion :this.precioAsiento);
				System.out.println("Monto" +  this.asientoVenta.getMonto());
				System.out.println("MontoReal" +  this.asientoVenta.getMontoReal());
				//si la venta es procudot de una inhabilitacion
				
				if(this.recuperarInhabilitacion)
				{
					if(this.diferencia.compareTo(new BigDecimal(0.0)) > 0 )
					{
						this.asientoVenta.setMontoReal(this.diferencia);
					}else if(this.diferencia.compareTo(new BigDecimal(0.0)) == 0){
						this.asientoVenta.setMontoReal(this.precioAsiento);
					}
				}
				
				this.asientoVenta.setIdusuarioautorizante(this.tienePromocion?this.promocion.getIdAutorizante():null);
				this.asientoVenta.setTipoventa(ConstanteVentas.VENTA_DIRECTA);
				this.asientoVenta.setSexo(this.persona.getSexo());
				this.asientoVenta.setDocumentoPersona(this.persona.getDni());
				this.asientoVenta.setDocumentoEmpresa(this.empresa.getRUC());
				this.asientoVenta.setObservacion(this.observacion);

				this.asientoVenta.setExterno(this.esOficinaExterna);
				this.asientoVenta.setOficinaId(this.esOficinaExterna ? this.oficinaExterna.getIdOficina() :  this.login.getPv().getIdOficina());
				this.asientoVenta.setPromocional(Boolean.FALSE);
				
				if(this.esPromocional)
				{
					this.asientoVenta.setPromocional(Boolean.TRUE);
					this.asientoVenta.setIdusuarioautorizante(this.promocion.getIdAutorizante());
					this.asientoVenta.setMonto(this.costoBoletoPromocional);
					this.asientoVenta.setIdpromocion(null);
				}
			
				if(this.historialPasajero.getIdHistorialPasajero() != null)
				{
					this.personaEncontrada = Boolean.TRUE;
				}
				
					//Pasajero nuevo
				if(!this.personaEncontrada)
				{
					//build nacimiento: 19510924  
					String anio;
					Integer mes;
					String dia;
					Calendar calendario = Calendar.getInstance();
					calendario.setTime(this.fechaCumpleAnios);
					anio = calendario.get(Calendar.YEAR)+"";
					mes = calendario.get(Calendar.MONTH);
					mes++;
					
					dia = calendario.get(Calendar.DAY_OF_MONTH)+"";
					if(Integer.parseInt(dia) < 10)
					{
						dia= "0" + dia;
					}
					
					StringBuilder str = new StringBuilder();
					str.append(anio);
					
					if(mes < 10)
					{
						str.append("0"+mes);
					}else{
						str.append(mes);
					}
					
					str.append(dia);
					
					this.persona.setNacimiento(str.toString());
					
					this.persona.setTipodocumento(this.persona.getTipodocumento());
					this.persona.setAMaterno(this.persona.getAMaterno().trim().toUpperCase());//poniendo a mayusculas
					this.persona.setAPaterno(this.persona.getAPaterno().trim().toUpperCase());
					this.persona.setNombres(this.persona.getNombres().trim().toUpperCase());
					this.persona.setSexo(this.persona.getSexo());
					this.persona.setDigito(this.persona.getSexo().equals("M") ? "1":"2");//build sexo:
					this.persona.setEmail(this.persona.getEmail().trim().toUpperCase());
					this.persona.setTelefono(this.persona.getTelefono());
					this.persona.setfNacimiento(this.fechaCumpleAnios);
					this.personaService.crearPersona(this.persona);
				
				//Actualizar datos de persona
				} else {
					//poniendo a mayusculas
					this.persona.setAMaterno(this.persona.getAMaterno().trim().toUpperCase());
					this.persona.setAPaterno(this.persona.getAPaterno().trim().toUpperCase());
					this.persona.setNombres(this.persona.getNombres().trim().toUpperCase());
                    this.persona.setEmail(this.persona.getEmail().trim().toUpperCase());
					this.personaService.actualizarPersona(this.persona);                                    
                }
				
				//modificacion
				this.asientoVenta.setTransferenciaBanco(this.transferenciaBanco);
				this.asientoVenta.setTransferenciaNumero(this.transferenciaNumero);
				this.asientoVenta.setVentaOrigen(Boolean.TRUE);
				this.asientoVenta.setSerieBoleto(this._Serie);
				this.asientoVenta.setSerieNumeroBoleto(this._Numero);
				this.asientoVentaService.actualizarVentaAsiento(this.asientoVenta);				
				this.asientoVenta.setPersona(this.persona);
				
				Double montoLiquidacion = new Double(this.precioAsiento.toString());
				
				if(this.deliveryVenta==false && !this.boletoPrepagado && !this.esOficinaExterna)
				{					
					registrarLiquidacionBoleto(montoLiquidacion,this._tipoPago,this._tipoEntrega,this._Serie,Integer.parseInt(this._Numero),"SIMPLE");//registra la liquidacion en la tabla t_liquidacion_venta
				}
				
				this._tipoPago = "EFECTIVO";
				this._tipoEntrega = "OFICINA";
				
				//*** registro de historial pasajero ***
				
				//Valida previamente si el asiento fue reservado por la misma persona que pretende comprar el pasaje
				List<TrackingBoleto> tmpreserva = this.trackBoletoService.buscarReservado(this.asientoVenta.getNumero(),ConstanteVentas.ESTADO_RESERVA, this.asientoVenta.getDocumentoPersona(),this.programacionSelec.getIdProgramacion(), this.programacionSelec.getIdServicio());
				boolean grabarSec = false;
				Secuencia sec= this.secuenciaServices.findbyDescripcion(Constante.SECUENCIA_TRACKING_BOLETO); // obtiene el ultimo codigo de secuencia
				
				if(!tmpreserva.isEmpty()){
					this.intsecuencia=tmpreserva.get(0).getIntsecuencia();
				}else{
					this.intsecuencia= Integer.valueOf(sec.getSecuencia())+1;
					grabarSec=true;
				}
			
				historial.setAsientoventaid(this.asientoVenta.getIdasientoventa());
				historial.setDni(this.persona.getDni());
				historial.setfRegistro(new Date());
				historial.setIdOficina(usuarioLogin.getIdoficina());
				historial.setIdprogramacion(this.programacionSelec.getIdProgramacion());
				historial.setIdservicio(this.programacionSelec.getIdServicio());
				historial.setSerieBoleto(this._Serie);
				historial.setOperacion(operacion);
				if(this.boletoPrepagado || this.esOficinaExterna)
				{ 
					historial.setNumeroBoleto(null);
				}else{
					historial.setNumeroBoleto(Integer.parseInt(this._Numero));
				}
				historial.setNroRuc(this.empresa.getRUC());
				historial.setPiso(this.asientoVenta.getPiso());
				historial.setNroasiento(Integer.parseInt(this.asientoVenta.getNumero()));
				historial.setIdpadre(this.historialPasajero.getIdHistorialPasajero());
				historial.setMonto(this.asientoVenta.getMonto());
				historial.setPuntoVentaId(this.login.getPv().getIdPuntoVenta());

				this.historialPasajeroService.crearHistorialPasajero(historial);
				
				//registrarHistorialPasajero(this.persona.getDni(),this._Serie,Integer.parseInt(this._Numero),this.empresa.getRUC());
				
				//registrar tracking de boleto:
				track.setSerieBoleto(this._Serie);
				//track.setNumeroBoleto(this.asientoVenta.getNumero());
				track.setNumeroBoleto(this._Numero);
				track.setFregistro(new Date());
				track.setIdusuario(this.login.getUsuario().getIdusuario());
				track.setIdasiento(this.asientoVenta.getIdAsiento());
				track.setIdasientoventa(this.asientoVenta.getIdasientoventa());
				track.setEstado(operacion);
				track.setIdprogramacion(this.programacionSelec.getIdProgramacion());
				track.setIdservicio(this.programacionSelec.getIdServicio());
				track.setNrodocumento(this.asientoVenta.getDocumentoPersona());
				track.setNrodocempresa(this.empresa.getRUC() == null ? null: this.empresa.getRUC().trim());
				track.setIntsecuencia(this.intsecuencia);
				track.setMonto(this.asientoVenta.getMonto());
				track.setMontoReal(this.asientoVenta.getMonto());
				track.setPrepagado(Boolean.FALSE);
				track.setPuntoVentaId(this.login.getPv().getIdPuntoVenta());
				if(this.boletoPrepagado){
					track.setPrepagado(Boolean.TRUE);
				}
				this.trackBoletoService.creartrack(track);//creando el track
				
				if (grabarSec == true){					
					sec.setSecuencia(this.intsecuencia);
					this.secuenciaServices.actualizarSecuencia(sec);
				}
				
				//actualizamos el incremento de numero de boleto;
				//this.puntoVentaService.actualizarUltimoBoleto(usuarioLogin.getIdpuntoventa(), Integer.parseInt(this._Numero));
				
				//vene de la consulta la rogramacion seleccionada:  this.programacionSelec.getDesRutaCompartida()
				if (this._rutaCompartida.equals("SI")) 
				{
					this.asientoVentaService.updateAssociatedSeats(this.asientoVenta);//actualizar las uras involucradas
				}else if(this._intCorreEnlace>0){
					this.asientoVentaService.updateAssociatedSeats(this.asientoVenta);
					System.out.println("intCorreEnlace > 0 ");
				}
				
				//actualizamos la el numero de boleto en la tabla t_puntoventa
				System.out.println("actulizando ultimo boleto");
				//###################  actualizando el ultimo numero del pv
				if(!this.boletoPrepagado && !this.esOficinaExterna) //si es boleto prepagado no se actualiza el ultimo nro de boleto del pv
				{
					this.puntoVentaService.actualizarUltimoBoleto(this.puntoVenta.getIdPuntoVenta(),Integer.parseInt(this._Numero));
				}

				List <AsientoVenta> asientoAux = new ArrayList<AsientoVenta>(this.listaAsientosxVender);
				
				this.activaPostergacion = Boolean.FALSE;
				// ACA DEBERIA LIMPIAR LA LISTA DESPUES DE REALIZADO LA VENTA
				//this.listaAsientosxVender = new ArrayList<>();
				
				//######################  Impresion #########################3
				if(this.boletoPrepagado) //mandar boleta de pago de 1 sol
				{					
					this.asientoVentaPrepagados = asientoAux;
					RequestContext context = RequestContext.getCurrentInstance();
					context.addCallbackParam("prepagado", Boolean.TRUE);					

				}else if(this.esOficinaExterna){
					
					//no hay impresion
				}else{
					
					//this.mandarImpresion(asientoAux,Boolean.TRUE);
				}
				
				//actualizando pasajero frecuente
				this.personaService.actualizarPasajeroFrecuente(this.persona.getDni());
				
				this.listaCmbPromociones = new ArrayList<>();
				this.enablePromocion = Boolean.FALSE;
				this.tienePromocion= Boolean.FALSE;
				this.idPromocionSelect = null;
				
				this.boletoRuc = Boolean.FALSE;
				this.rucDigitado = Boolean.FALSE;
				this.nroRuc = "";
				this.empresa = new Empresa();
				
				this.esPromocional = Boolean.FALSE;
				this.listaAgenciasExternas.clear();
				this.listaOficinasExternas.clear();
				this.esOficinaExterna = Boolean.FALSE;
				this.agenciaExterna = new Agencia();
				this.oficinaExterna = new Oficina();
				this.boletoPrepagado = Boolean.FALSE;
				this.deliveryVenta = Boolean.FALSE;
				this.pagador = new PagadorPrepagado();
				this.delivery = new PagadorPrepagado();

				this.cambioServicio = Boolean.FALSE;
				this.boletoReserva = Boolean.FALSE;
				this.login.setAsientoPasajero(new AsientoPasajero());
				this.postergacionRecuperadaEnMes = Boolean.FALSE;
				this.inhabilitacionRecuperadaEnMes = Boolean.FALSE;
				this.recuperarInhabilitacion = Boolean.FALSE;
				this.esOficinaExterna = Boolean.FALSE;
				this.personaNueva = new Persona();
				programacionSelec.setVendidos(programacionSelec.getVendidos() + 1);
				this.estadoBoletoVendido = true;
				
				RequestContext context = RequestContext.getCurrentInstance();
				context.addCallbackParam("esVenta", Boolean.TRUE);
				
				System.out.println("Estado del Boleto: " + this.estadoBoletoVendido);
				System.out.println("Lista de asientos por vender: " + this.listaAsientosxVender.size());
				
				//********************* RESERVAR *************************
			} else if (operacion.equals(ConstanteVentas.ESTADO_RESERVA)) {
				
				System.out.println("INGRESA A ESTADO_RESERVA ");
				
				RequestContext context = RequestContext.getCurrentInstance();
				context.addCallbackParam("esVenta", Boolean.FALSE);
				
				this.asientoVenta.setEstado(ConstanteVentas.ESTADO_RESERVA);
				this.asientoVenta.setFechaventa(new Date());
				this.asientoVenta.setMonto(this.precioAsiento);
				this.asientoVenta.setTipoventa(ConstanteVentas.VENTA_DIRECTA);
				this.asientoVenta.setSexo(this.persona.getSexo());
				this.asientoVenta.setDocumentoPersona(this.persona.getDni());
				this.asientoVenta.setDocumentoEmpresa(this.empresa.getRUC());
				this.asientoVenta.setExterno(this.esOficinaExterna);
				this.asientoVenta.setOficinaId(this.esOficinaExterna ? this.oficinaExterna.getIdOficina() :  this.login.getPv().getIdOficina());

				if(this.esPromocional)
				{
					this.asientoVenta.setPromocional(Boolean.TRUE);
					this.asientoVenta.setIdusuarioautorizante(this.promocion.getIdAutorizante());
				}else{
					this.asientoVenta.setPromocional(Boolean.FALSE);
				}
				//modificacion
				this.asientoVenta.setTransferenciaBanco(this.transferenciaBanco);
				this.asientoVenta.setTransferenciaNumero(this.transferenciaNumero);
				
				this.asientoVenta.setHorareserva(this.fechaLimiteReserva);
				this.asientoVenta.setVentaOrigen(Boolean.TRUE);
				this.asientoVentaService.actualizarVentaAsiento(this.asientoVenta);
				
				if (this._rutaCompartida.equals("SI")) {
					this.asientoVentaService.updateAssociatedSeats(this.asientoVenta);
				}else if(this._intCorreEnlace>0){
					this.asientoVentaService.updateAssociatedSeats(this.asientoVenta);
				}
				
				Secuencia sec= this.secuenciaServices.findbyDescripcion(constantes.SECUENCIA_TRACKING_BOLETO);
				
				historial.setAsientoventaid(this.asientoVenta.getIdasientoventa());
				historial.setDni(this.persona.getDni());
				historial.setfRegistro(new Date());
				historial.setIdOficina(usuarioLogin.getIdoficina());
				historial.setIdprogramacion(this.programacionSelec.getIdProgramacion());
				historial.setIdservicio(this.programacionSelec.getIdServicio());
				historial.setSerieBoleto(this._Serie);
				historial.setOperacion(operacion);
				historial.setNumeroBoleto(Integer.parseInt(this._Numero));
				historial.setNroRuc(this.empresa.getRUC());
				historial.setPiso(this.asientoVenta.getPiso());
				historial.setNroasiento(Integer.parseInt(this.asientoVenta.getNumero()));
				historial.setMonto(this.asientoVenta.getMonto());
				historial.setPuntoVentaId(this.login.getPv().getIdPuntoVenta());
				historial.setFechaCaducidad(this.fechaLimiteReserva);
				
				this.historialPasajeroService.crearHistorialPasajero(historial);
				
				//registrar tracking de boleto:
				track.setSerieBoleto(""); //Cuando es reserva no se genera
				track.setNumeroBoleto(""); //Cuando es reserva no se genera
				track.setFregistro(new Date());
				track.setIdusuario(this.login.getUsuario().getIdusuario());
				track.setIdasiento(this.asientoVenta.getIdAsiento());
				track.setIdasientoventa(this.asientoVenta.getIdasientoventa());
				track.setEstado(operacion);
				track.setIdprogramacion(this.programacionSelec.getIdProgramacion());
				track.setIdservicio(this.programacionSelec.getIdServicio());
				track.setNrodocumento(this.asientoVenta.getDocumentoPersona());
				track.setNrodocempresa(this.empresa.getRUC());
				track.setIntsecuencia(sec.getSecuencia()+1);
				track.setMonto(this.asientoVenta.getMonto());
				track.setMontoReal(this.asientoVenta.getMonto());//no esta contemplado la reserva comoproducto de una recuperacion de postergacion
				track.setFechaCaducidad(this.fechaLimiteReserva);
				this.trackBoletoService.creartrack(track);
				
				PuntoVenta ptv=new PuntoVenta();
				ptv = this.puntoVentaService.getPuntoVentaxUsuario(usuarioLogin.getIdusuario());
				sec.setSecuencia(sec.getSecuencia()+1);
				
				try {
					this.secuenciaServices.actualizarSecuencia(sec);
					ptv.setUltimareserva(Integer.parseInt(this._Numero));
					this.puntoVentaService.actualizarSeriePuntoVenta(ptv);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("paso a resetear");
				this.listaAsientosxVender = new ArrayList<>();
				this.boletoRuc = Boolean.FALSE;
				this.rucDigitado = Boolean.FALSE;
				this.esPromocional = Boolean.FALSE;
				this.listaAgenciasExternas.clear();
				this.listaOficinasExternas.clear();
				this.esOficinaExterna = Boolean.FALSE;
				this.agenciaExterna = new Agencia();
				this.oficinaExterna = new Oficina();
				this.boletoPrepagado = Boolean.FALSE;
				this.deliveryVenta = Boolean.FALSE;
				this.pagador = new PagadorPrepagado();
				this.delivery = new PagadorPrepagado();
				this.postergacionRecuperadaEnMes = Boolean.FALSE;
				this.inhabilitacionRecuperadaEnMes = Boolean.FALSE;
				this.recuperarInhabilitacion = Boolean.FALSE;
				this.esOficinaExterna = Boolean.FALSE;
				
				programacionSelec.setReservados(programacionSelec.getReservados()+ 1);
				
				//******************* LIBERAR INDIVIDUAL **************************
			} else if (operacion.equals("LIBERAR")) {
				
				System.out.println("INGRESA A LIBERAR");
				
				if(!this.asientoSelec.getAsientoVenta().getEstado().equals(ConstanteVentas.ESTADO_RESERVA)){
				this.asientoVenta.setEstado(ConstanteVentas.ESTADO_LIBRE);
				this.asientoVenta.setFechaventa(null);
				this.asientoVenta.setMonto(this.precioAsiento);
				this.asientoVenta.setTipoventa(null);
				this.asientoVenta.setSexo("-");
				this.asientoVenta.setDocumentoPersona(null);
				this.asientoVenta.setDocumentoEmpresa(null);
				this.asientoVenta.setIdusuarioventa(new Integer(0));
				this.asientoVentaService.actualizarVentaAsiento(this.asientoVenta);
				this.listaAsientosxVender = new ArrayList<>();
				if (this._rutaCompartida.equals("SI")) {
					this.asientoVentaService.updateAssociatedSeats(this.asientoVenta);
				}else if(this._intCorreEnlace>0){
					this.asientoVentaService.updateAssociatedSeats(this.asientoVenta);
				}
				actualizarDisponibilidadCancela(this.asientoSelec,this.programacionSelec);
				}
				
				this.listaCmbPromociones = new ArrayList<>();
				this.enablePromocion = Boolean.FALSE;
				this.tienePromocion= Boolean.FALSE;
				this.idPromocionSelect = null;
				
				this.boletoRuc = Boolean.FALSE;
				this.rucDigitado = Boolean.FALSE;
				this.nroRuc = null;
				this.empresa = new Empresa();

				this.listaAgenciasExternas.clear();
				this.listaOficinasExternas.clear();
				this.esOficinaExterna = Boolean.FALSE;
				this.agenciaExterna = new Agencia();
				this.oficinaExterna = new Oficina();
				this.boletoPrepagado = Boolean.FALSE;
				this.deliveryVenta = Boolean.FALSE;
				this.pagador = new PagadorPrepagado();
				this._tipoEntrega = "OFICINA";
				this.delivery = new PagadorPrepagado();
				this.cambioServicio = Boolean.FALSE;
				this.boletoReserva = Boolean.FALSE;
				this.boletoPrepagado = Boolean.FALSE;
				this.esOficinaExterna = Boolean.FALSE;
				this.observacion = null;
				this.postergacionRecuperadaEnMes = Boolean.FALSE;
				this.inhabilitacionRecuperadaEnMes = Boolean.FALSE;
				this.recuperarInhabilitacion = Boolean.FALSE;
				
				//****************** LIBERAR MASIVO****************************
			} else if (operacion.equals("LIBERAR MASIVO")) {
				
				System.out.println("INGRESA A LIBERAR MASIVO");
				for(AsientoVenta asi : this.listaAsientosxVender)
				{
					asi.setEstado(ConstanteVentas.ESTADO_LIBRE);
					asi.setFechaventa(null);
					asi.setMonto(this.precioAsiento);
					asi.setTipoventa(null);
					asi.setSexo("-");
					asi.setRucBoolean(Boolean.FALSE);
					asi.setDocumentoPersona(null);
					asi.setDocumentoEmpresa(null);
					asi.setIdusuarioventa(new Integer(0));
					
					this.asientoVentaService.actualizarVentaAsiento(asi);
					actualizarDisponibilidadCancela(asi.getAsiento(),this.programacionSelec);
					
					this.boletoRuc = Boolean.FALSE;
					this.rucDigitado = Boolean.FALSE;

					this.listaAgenciasExternas.clear();
					this.listaOficinasExternas.clear();
					this.esOficinaExterna = Boolean.FALSE;
					this.agenciaExterna = new Agencia();
					this.oficinaExterna = new Oficina();
					
					if (this._rutaCompartida.equals("SI")) {
						this.asientoVentaService.updateAssociatedSeats(asi);
						//actualizaraAsociadosaLibre(asi);
					}else if(this._intCorreEnlace>0){
						this.asientoVentaService.updateAssociatedSeats(asi);
					}
				}
				
				this.listaCmbPromociones = new ArrayList<>();
				this.enablePromocion = Boolean.FALSE;
				this.tienePromocion= Boolean.FALSE;
				this.idPromocionSelect = null;
				this.dsctoBoletoMultiple = "";
				this.promocion = new Promocion();
				this.esPromocional = Boolean.FALSE;
				this._bandHoraReserva = Boolean.FALSE;
				this.listaAsientosxVender.clear();
				this.boletoPrepagado = Boolean.FALSE;
				this.deliveryVenta = Boolean.FALSE;
				this.deliveryEnElDia = Boolean.FALSE;
				this.pagador = new PagadorPrepagado();
				this._tipoEntrega = "OFICINA";
				this.delivery = new PagadorPrepagado();
				this.postergacionRecuperadaEnMes = Boolean.FALSE;
				this.inhabilitacionRecuperadaEnMes = Boolean.FALSE;
				this.recuperarInhabilitacion = Boolean.FALSE;
				
			} else if (operacion.equals("PROCESO")) {
				System.out.println("INGRESA A PROCESO ");
				this.asientoVenta.setFechaventa(null);
				this.asientoVenta.setEstado(ConstanteVentas.ESTADO_PROCESO);
				this.asientoVenta.setMonto(this.precioAsiento);
				this.asientoVenta.setSexo("-");
				this.asientoVentaService.actualizarVentaAsiento(this.asientoVenta);
				this.asientoVenta.setIdProgramacion(this.programacionSelec.getIdProgramacion());
				this.nroRuc = null;
				
				if (this._rutaCompartida.equals("SI")) {
					System.out.println("entro en ruta compartida si");
					this.asientoVentaService.updateAssociatedSeats(this.asientoVenta); //PROCEDIMIENTO ALMACENADO
					//actualizaraAsociadosaProceso(this.asientoVenta);
				}else if(this._intCorreEnlace>0){
					this.asientoVentaService.updateAssociatedSeats(this.asientoVenta);//PROCEDIMIENTO ALMACENADO
//					actualizaraAsociadosaProceso(this.asientoVenta);
				}
			}else if (operacion.equals(ConstanteVentas.ESTADO_SEPARADO)) {
				
				//reeemplazado pro boton separar
				
				/*
				 System.out.println("INGRESA A ESTADO_SEPARADO");
				
				this.asientoVenta.setEstado(ConstanteVentas.ESTADO_SEPARADO);
				this.asientoVenta.setFechaventa(new Date());
				this.asientoVenta.setMonto(this.precioAsiento);
				this.asientoVenta.setTipoventa(ConstanteVentas.VENTA_DIRECTA);
				this.asientoVenta.setSexo(this.persona.getSexo());
				this.asientoVenta.setDocumentoPersona(this.persona.getDni());
				this.asientoVenta.setDocumentoEmpresa(this.empresa.getRUC());
				this.asientoVenta.setExterno(this.esOficinaExterna);
				this.asientoVenta.setOficinaId(this.esOficinaExterna ? this.oficinaExterna.getIdOficina() :  this.login.getPv().getIdOficina());

				if(this.esPromocional)
				{
					this.asientoVenta.setPromocional(Boolean.TRUE);
					this.asientoVenta.setIdusuarioautorizante(this.promocion.getIdAutorizante());
				}else{
					this.asientoVenta.setPromocional(Boolean.FALSE);
				}
				//modificacion
				this.asientoVenta.setTransferenciaBanco(this.transferenciaBanco);
				this.asientoVenta.setTransferenciaNumero(this.transferenciaNumero);
				
				//this.asientoVenta.setHorareserva(this.fechaLimiteReserva);
				this.asientoVentaService.actualizarVentaAsiento(this.asientoVenta);
				
				if (this._rutaCompartida.equals("SI")) {
					this.asientoVentaService.updateAssociatedSeats(this.asientoVenta);
				}else if(this._intCorreEnlace>0){
					this.asientoVentaService.updateAssociatedSeats(this.asientoVenta);
				}
				
				Secuencia sec= this.secuenciaServices.findbyDescripcion(constantes.SECUENCIA_TRACKING_BOLETO);
				
				//registrar tracking de boleto:
				track.setSerieBoleto(this._Serie);
				track.setNumeroBoleto(this._Numero);
				track.setFregistro(new Date());
				track.setIdusuario(this.login.getUsuario().getIdusuario());
				track.setIdasiento(this.asientoVenta.getIdAsiento());
				track.setIdasientoventa(this.asientoVenta.getIdasientoventa());
				track.setEstado(operacion);
				track.setIdprogramacion(this.programacionSelec.getIdProgramacion());
				track.setIdservicio(this.programacionSelec.getIdServicio());
				track.setNrodocumento(this.asientoVenta.getDocumentoPersona());
				track.setNrodocempresa(this.empresa.getRUC());
				track.setIntsecuencia(sec.getSecuencia()+1);
				this.trackBoletoService.creartrack(track);
				
				PuntoVenta ptv=new PuntoVenta();
				
				ptv = this.puntoVentaService.getPuntoVentaxUsuario(usuarioLogin.getIdpuntoventa());
				
				sec.setSecuencia(sec.getSecuencia()+1);
				this.secuenciaServices.actualizarSecuencia(sec);
				ptv.setUltimareserva(Integer.valueOf(this._Numero));
				try {
					this.puntoVentaService.actualizarSeriePuntoVenta(ptv);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				this.listaAsientosxVender = new ArrayList<>();
				this.boletoRuc = Boolean.FALSE;
				this.rucDigitado = Boolean.FALSE;
				this.esPromocional = Boolean.FALSE;
				this.listaAgenciasExternas.clear();
				this.listaOficinasExternas.clear();
				this.esOficinaExterna = Boolean.FALSE;
				this.agenciaExterna = new Agencia();
				this.oficinaExterna = new Oficina();
				this.boletoPrepagado = Boolean.FALSE;
				this.deliveryVenta = Boolean.FALSE;
				this.pagador = new PagadorPrepagado();
				this.delivery = new PagadorPrepagado();
				
				seleccionar();
				*/
			} 
			
			this._frecuencia = false;
			this._nuevo= false;
			this.pasajeroReg=false;

		} catch (Exception e) {

			e.printStackTrace();
		}
		this.personaEncontrada = Boolean.FALSE;
		this.boletoReserva = Boolean.FALSE;
		
		//modificacion
		this.transferenciaBanco="";
		this.transferenciaNumero="";
		
		//modificacion 09-09-2014
		this.empresa=new Empresa();
		System.out.println("FIN - venderAsiento");
		
	}

	/*
	 * Actualizando las rutas compartidas
	 * 
	 * */
//	public void actualizarEnlace(AsientoVenta asientoVenta) {
//		System.out.println("iniciando actualizacion");
//		try {
//			
//			if(this._tipoVenta.equals("1")){
//				asientoVenta.setEstado(ConstanteVentas.ESTADO_VENTA);
//			}else if(this._tipoVenta.equals("2")){
//				asientoVenta.setEstado(ConstanteVentas.ESTADO_RESERVA);
//			}
//			//1: obtengo todos los servicios relaciondos
//			//2:obtengo las programaciones donde esta dichs servicos,las programciones de la misma fecha de salida
//			//3: actualizo los asiento venta  de dichas programaciones
//			Servicio ser =  this.servicioService.findById(this.programacionSelec.getServicio().getIdServicio());
//			List<Servicio> listaServiciosRutaCompartida= this.servicioService.listaHijosServiciosCompartidos(ser.getIntCorre());
//			
//			for(Servicio serv  : listaServiciosRutaCompartida)
//			{
//				Programacion prog = this.programacionService.programacionRutaCompartida(this.programacionSelec.getfSalida(), serv.getIdServicio(),serv.getIntCorre());
//				
//				if(prog != null)
//				{
//					if(this.programacionSelec.getIdProgramacion().intValue() != prog.getIdProgramacion().intValue())
//					{
//						asientoVenta.setIdProgramacion(prog.getIdProgramacion());
//						this.asientoVentaService.actualizarVentaAsiento(asientoVenta);
//					}
//				}
//
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	public void registrarLiquidacionBoleto(Double montoBoleto, String tipoPago, String tipoEntrega,String serie, Integer numero,String tipoRegistro){
		
		try {
			Double cero = new Double("0.0");
			Liquidacion_Venta liquida_v = new Liquidacion_Venta();
			liquida_v.setTipoDocumento(Constante.TIPO_DOCUMENTO_BOLETO);
			liquida_v.setIdPuntoVenta(usuarioLogin.getIdpuntoventa());
			liquida_v.setfDocumento(new Date());
			liquida_v.setArea("VENTAS");
			liquida_v.setSubTotal(cero);
			liquida_v.setIgv(cero);
			liquida_v.setTotal(montoBoleto);
			liquida_v.setEstado(1);
			liquida_v.setProceso("VENTA");
			if(tipoPago.equals("EFECTIVO")){
				liquida_v.setMovimientoCaja("INGRESO");
				liquida_v.setFormaPago(tipoPago);
				liquida_v.setTotalEfectivo(montoBoleto);
			}else if(tipoPago.equals("TARJETA")){
				liquida_v.setMovimientoCaja("EGRESO");
				liquida_v.setFormaPago(tipoPago);
				liquida_v.setTipoTarjeta(this.tipoTarjeta);
				liquida_v.setTotalEfectivo(montoBoleto);
			}else if(tipoPago.equals("MIXTO")){
				liquida_v.setFormaPago(tipoPago);
				liquida_v.setMovimientoCaja("INGRESO");
				if(tipoRegistro.equals("MULTIPLE")){
					Integer cantidad = this.listaAsientosxVender.size();
					liquida_v.setTotalEfectivo(this.montoEfectivo/cantidad);
					liquida_v.setTotalTarjeta(this.montoTarjeta/cantidad);
					liquida_v.setTipoTarjeta(this.tipoTarjeta);
				}else{
					liquida_v.setTotalEfectivo(this.montoEfectivo);
					liquida_v.setTotalTarjeta(this.montoTarjeta);
				}
				
			}
			liquida_v.setIdUsuario(usuarioLogin.getIdusuario());
			liquida_v.setOrigenPago("OFICINA");
			liquida_v.setFormaEntrega(tipoEntrega);
			liquida_v.setDocumento(serie+"-"+numero);
			System.out.println("el tipo de tarjeta " + liquida_v.getTipoTarjeta());
			this.liquidacion_VentaServices.crearLiquidacion_Venta(liquida_v);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}	

	public void cerrarConsulta() {
		this.estaLibre = Boolean.TRUE;
	}

	/*
	public void seleccionarAsientoVentaVarios() {
		for (Asiento as : this.listaVentaVarios) {
			System.out.println("asiento:"
					+ as.getAsientoVenta().getIdasientoventa());
		}
	}
*/
	public void dataInformativa() {
		int countL = 0;
		int countV = 0;
		int countP = 0;
		int countR = 0;

		for (int i = 0; i < this.listaAsientosxVender.size(); i++) {
			AsientoVenta av = this.listaAsientosxVender.get(i);
			switch (av.getEstado()) {
			case "LIBRE":
				countL++;
				break;
			case "VENTA":
				countV++;
				break;
			case "PROCESO":
				countP++;
				break;
			case "RESERVA":
				countR++;
				break;
			}
		}

		this.capacidad = this.listaAsientosxVender.size();
		this.disponibles = countL;
		//this.reservados = countR;
		//this.totalVendidos = countV;
	}
	
	/*
	 * para ser deprecado
	public void registrarHistorialPasajero(String dni,String serieB, Integer numeroB, String ruc){
		try {
			HistorialPasajero historialPasajero = new HistorialPasajero();
			historialPasajero.setDni(dni);
			historialPasajero.setSerieBoleto(serieB);
			historialPasajero.setNumeroBoleto(numeroB);
			historialPasajero.setIdOficina(usuarioLogin.getIdoficina());
			historialPasajero.setfRegistro(new Date());
			historialPasajero.setNroRuc(ruc);
			this.historialPasajeroService.crearHistorialPasajero(historialPasajero);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/
	
	/**
	 * Metodo accedido
	 * tipoPosterga: abierta y noabierta (esta oculto, funciona solo para abierta)
	 * */
	
	public void postergarBoleto(String tipoPosterga)
	{
		LOG.info("Inicio - postergarBoleto");
		Asiento as = this.asientoSelec;
		this.fechaAbierta = Boolean.FALSE;
		
		
		try {
			DateFormat datehourFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String strFecha = datehourFormat.format(fechaActual);
			this.fechaActual = formatoDelTexto.parse(strFecha);
			
			
			System.out.println("Postergando boleto");
			//hora de salida
			String fecha = Utils.dateAString(this.programacionSelec.getfSalida());
			String horaMin = Utils.timeAString(this.programacionSelec.getHora24());  
			Date fechaHoraSalida = Utils.stringToDateTime(fecha+" "+horaMin);
			
			//fecha y hora de postergacion se hace con 2 horas de anticipacion
			Date date = new Date();//fehca y hora actual
			int horas = Integer.parseInt(this.parametroServices.findParametro_byNombre("HORA_CADUCIDAD_RESERVA"));//horas de anticipancion -- traer el dato de la tabla parametro
		    Calendar calendar = Calendar.getInstance();
		    calendar.setTime(date); 
		    calendar.add(Calendar.HOUR_OF_DAY, horas);//a?adimos las horas formato 24h 
		    Date fechaPostergacion = calendar.getTime(); // Devuelve el objeto Date con las nuevas horas a?adidas
			
		    //claculo d ela fecha y hora limite depostergacion:
		    
		    Calendar calendar2 = Calendar.getInstance();
		    calendar2.setTime(fechaHoraSalida);
		    calendar2.add(Calendar.HOUR_OF_DAY, horas*(-1));
		    this.fechaHoraLimitePostergacion = calendar2.getTime();
	    
		    System.out.println("fechaHoraSalida"+fechaHoraSalida);
		    
		    if(fechaPostergacion.before(fechaHoraSalida))//si la fecha y hora actual + 2 horas  aun es menor que la fehca y hora de salida
		    {
		    	//se acepta la postergacion
		    	this.postergar = Boolean.TRUE;
		    	//calculo de lafecha de caducidad: 6 mese sin fecha abierta
		    	
		    	int meses = 6;
		    	Integer nroDiasLimiteReserva = 0;
		    	 
		    	if(tipoPosterga.equals("abierta")) //caducidad de un año con fecha abierta
		    	{	
		    		System.out.println("Ingreso a tipo de postarga abierta");
		    		 nroDiasLimiteReserva = Integer.parseInt(this.parametroServices.findParametro_byNombre("DIAS_POSTERGACION_FA"));
		    		 
		 	    }else{
		 	    	System.out.println("Ingreso a tipo de postarga");
		 	    	nroDiasLimiteReserva = Integer.parseInt(this.parametroServices.findParametro_byNombre("DIAS_POSTERGACION"));
		 	    }
		    	
		    	meses = nroDiasLimiteReserva/30;
		    	
		    	 
			    Calendar calendario = Calendar.getInstance();
			    calendario.setTime(date); // Configuramos la fecha que se recibe
			    calendario.add(Calendar.MONTH, meses);  // numero de horas a a?adir, o restar en caso de horas<0
			    fechaCaducidad = calendario.getTime(); // Devuelve el objeto Date con las nuevas horas a?adidas
			     
			    this.asientoVenta =  as.getAsientoVenta();
			    try {
			    	System.out.println("Buscando nro de documento");
					this.persona = personaService.findByNroDocumento(this.asientoVenta.getDocumentoPersona());
		
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
		    }else{ //NO SE ACEPTA LA POSTERGACION, muestra fomulario informativo
		    	
		    	this.asientoVenta =  as.getAsientoVenta();
		    	this.asientoVenta.setUsuario(this.usuarioServices.findByIdUsuarioVenta(as.getAsientoVenta().getIdasientoventa()));
		    	try {
					this.asientoVenta.setPersona(this.personaService.findByNroDocumento(as.getAsientoVenta().getDocumentoPersona()));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	AsientoVenta avtmp= new AsientoVenta();
		    	avtmp = this.asientoVentaService.findInfoAsientoVenta(this.asientoVenta.getIdProgramacion(),this.asientoVenta.getOficinaId());
		    	this.asientoVenta.setDesOficina(avtmp.getDesOficina());
				this.asientoVenta.setDesOrigen(avtmp.getDesOrigen());
				this.asientoVenta.setDesDestino(avtmp.getDesDestino());
				this.asientoVenta.setPostergacion(this.postergarService.findByIdAsientoVenta(this.asientoVenta.getIdasientoventa()));
				
				//this.asientoVenta.setFechaPostergacion(fechaPostergacion);
		    	//la fecha de postergacion exede las  dos horas de anticipacion
		    	postergar = Boolean.FALSE;
		    }
		    
		    if(tipoPosterga.equals("abierta"))
		    {
		    	this.fechaAbierta = Boolean.TRUE;
		    }
	    
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Termino - postergarBoleto");
		LOG.info("Termino - postergarBoleto");
	}
	
	
	
	public void realizarPostergacionFechaAbierta()
	{
		LOG.info("Inicio - realizarPostergacionFechaAbierta");
		System.out.println("Inicio - realizarPostergacionFechaAbierta");
		
		TrackingBoleto trac = null;
		/* reemplazado por boton
		this.postergacion = new Postergacion();
		this.postergacion.setDocumentopersona(this.persona.getDni());
		this.postergacion.setEstado("POSTERGADO");
		
		if(this.fechaAbierta)
		{
			this.postergacion.setFechaAbierta(Boolean.TRUE);
		}else{
			this.postergacion.setFechaAbierta(Boolean.FALSE);
			this.postergacion.setFechacaducidad(this.fechaCaducidad);
		}
		
		this.postergacion.setFechapostergacion(this.fechaActual);
		this.postergacion.setIdasientoventa(this.asientoVenta.getIdasientoventa());
		this.postergacion.setSerie(this._Serie);
		this.postergacion.setUsuarioid(this.login.getIdUsuario());
		
		this.postergacion.setServicioid(this.programacionSelec.getIdServicio());
		this.postergacion.setMonto(this.asientoVenta.getMonto());
		this.postergacion.setOrigen(this.programacionSelec.getOrigen());
		this.postergacion.setDestino(this.programacionSelec.getDestino());
		*/
		/* FIN reemplazado por boton*/
		
		try {
			//this.postergarService.crearPostergacion(this.postergacion); /* FIN reemplazado por boton*/
			System.out.println("this.asientoVenta.getIdasientoventa()" + this.asientoVenta.getIdasientoventa());
			this.asientoVentaService.replicarAsientoVenta(this.asientoVenta.getIdasientoventa()); //creando espejo de asientoVenta en la tabla t_asientovenapostergadoFA
			this.asientoVentaService.liberarAsiento(this.asientoVenta.getIdAsiento(), this.asientoVenta.getIdProgramacion(), "LIBRE");//liberando el asiento:
			trac = this.trackBoletoService.findByServicioProgramacionNumeroPersona(this.programacionSelec.getIdServicio(), this.programacionSelec.getIdProgramacion(), this.asientoVenta.getIdasientoventa(), this.persona.getDni()); //obtener el tracking:
			this.asientoVenta.setEstado("LIBRE");
			this.asientoVenta.setSexo("-");
			
			//Esto no entiendo
			List<TrackingBoleto> tmpreserva = this.trackBoletoService.buscarReservado(this.asientoVenta.getNumero(),ConstanteVentas.ESTADO_VENTA, this.asientoVenta.getDocumentoPersona(),this.programacionSelec.getIdProgramacion(), this.programacionSelec.getIdServicio());
			if(!tmpreserva.isEmpty()) {
				this.intsecuencia=tmpreserva.get(0).getIntsecuencia();
				
			}
			
			//Creando nuevo historial del pasajero
			List<HistorialPasajero> historial = this.historialPasajeroService.getHistorialByDniAsientoVentaId(this.persona.getDni(), this.asientoVenta.getIdasientoventa());//recuperando el historial del pasajero
			historial.get(0).setOperacion(ConstanteVentas.ESTADO_POSTERGADO_FA); // Cambiando el historial del pasajero a postergado 
			historial.get(0).setPuntoVentaId(this.login.getPv().getIdPuntoVenta());
			if(this.fechaAbierta){ //Creo que esto no es indispensable
				historial.get(0).setFechaCaducidad(this.fechaCaducidad);
			}
			this.historialPasajeroService.actualizarOperacionPosterga(historial.get(0));  //Agrega registro en la tabla historial pasajero a POSTERGADO
			
			//crando un nuevo tracking
			trac.setSerieBoleto(this._Serie);
			trac.setNumeroBoleto(this._Numero);
			trac.setFregistro(new Date());
			trac.setIdusuario(this.login.getUsuario().getIdusuario());
			trac.setIdasiento(this.asientoVenta.getIdAsiento());
			trac.setIdasientoventa(this.asientoVenta.getIdasientoventa());
			trac.setEstado(ConstanteVentas.ESTADO_POSTERGADO_FA);
			trac.setIdprogramacion(this.programacionSelec.getIdProgramacion());
			trac.setIdservicio(this.programacionSelec.getIdServicio());
			trac.setNrodocumento(this.asientoVenta.getDocumentoPersona());
			trac.setNrodocempresa(this.empresa==null ? null :this.empresa.getRUC());
			trac.setIntsecuencia(this.intsecuencia);
			trac.setMonto(this.asientoVenta.getMonto());
			trac.setMontoReal(this.asientoVenta.getMontoReal());
			if(this.fechaAbierta){
				trac.setFechaCaducidad(this.fechaCaducidad);
			}
			this.trackBoletoService.creartrack(trac);
			//actualizar tracking
			//trac.setEstado("POSTERGADO");
			//trac.setFregistro(new Date());
			//this.trackBoletoService.actualizarTracking(trac);
			
			FacesUtils.showFacesMessage("Se realizó la postergacion correctamente.",3);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Fin - realizarPostergacionFechaAbierta");
		
	}
	
	
	public void limpiarPersona()
	{

		this.activaPostergacion = Boolean.TRUE;

		this.persona = new Persona();
		this.listaPostergacion.clear();
		System.out.println("precio asiento:"+this.precioAsiento);

		
	}
	
	public void buscarPostergacion()
	{
		this.listaPostergacion.clear();
		try {
			this.listaPostergacion = this.postergarService.listaPostergacion(this.persona.getDni().trim());
			System.out.println("precio asiento:"+this.precioAsiento);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void setearPostergacion(Postergacion pst)
	{
		//System.out.println("setenado");
		this.postergacion = pst;
		
		this.login.setPostergacion(pst);
		

		System.out.println("fechas:"+this.postergacion.getIdpostergado());
		
	}
	
	public void realizarActivacion()
	{
		
		System.out.println("realizando activacion");
		try {
			this.postergacion.setEstado("ACTIVADO");
			this.postergacion.setUsuarioactiva(this.login.getIdUsuario());

			this.postergarService.actualizarPostergacion(this.postergacion);

			
			//comprando el costo
			
			if(this.tienePromocion)
			{
				if(this.postergacion.getMonto().compareTo(this.precioAsientoPromocion) < 0) //es menor
				{
					//se establece la diferencia
					this.precioAsientoPromocion = this.precioAsientoPromocion.subtract(this.postergacion.getMonto());
				}
			}else{
				
				if(this.postergacion.getMonto().compareTo(this.precioAsiento) < 0) //es menor
				{
					//se establece la diferencia
					this.precioAsiento = this.precioAsiento.subtract(this.postergacion.getMonto());
				}
				
			}
			
			this.buscarPersonaSola();
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			
		}
		
	}
	
	
	public void actualizarDisponibilidadVenta(Asiento as,Programacion pr)
	{
	
		pr.setDisponibles(pr.getDisponibles()-1);
		
		if(as.getPiso()==1)
		{
			pr.setDisponibleP1(pr.getDisponibleP1()-1);
		}else
		{
			pr.setDisponibleP2(pr.getDisponibleP2()-1);
		}
	
	}
	
	public void actualizarDisponibilidadCancela(Asiento as,Programacion pr)
	{
	
		pr.setDisponibles(pr.getDisponibles()+1);
		
		if(as.getPiso()==1)
		{
			pr.setDisponibleP1(pr.getDisponibleP1()+1);
		}else
		{
			pr.setDisponibleP2(pr.getDisponibleP2()+1);
		}
	
	}
	
	
	public void anularAsiento()
	{
		
		System.out.println("iniciando anulr asiento");
		this.asientoVenta =  this.asientoSelec.getAsientoVenta();
		this.asientoVenta.setAsiento(this.asientoSelec);
		try {
			this.asientoVenta.setPersona(this.personaService.findByNroDocumento(this.asientoVenta.getDocumentoPersona()));
			//boleto con ruc
			if(this.asientoVenta.getDocumentoEmpresa() != null)
			{
				this.asientoVenta.setEmpresa(this.empresaService.findByNroRUC(this.asientoVenta.getDocumentoEmpresa()));
			}
			
			this.asientoVenta.setUsuario(this.usuarioServices.findByIdUsuarioVenta(this.asientoVenta.getIdasientoventa()));
			
			AsientoVenta avtmp = new AsientoVenta();
			
			avtmp = this.asientoVentaService.findInfoAsientoVenta(this.asientoVenta.getIdProgramacion(),this.asientoVenta.getOficinaId());
			
			this.asientoVenta.setDesOficina(avtmp.getDesOficina());
			this.asientoVenta.setDesOrigen(avtmp.getDesOrigen());
			this.asientoVenta.setDesDestino(avtmp.getDesDestino());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//*** 23/09/2015 Guarda la Anulación 
	//*** Libera el asiento, y guarda el historial del pasajero y traking boleto 
	public void registroAnulacion(AsientoVenta asiento)
	{
		this.asientoVenta.setEstado("LIBRE");
		this.asientoVenta.setSexo("-");
		//this.asientoVenta.setHorareserva(null);
		
		try {
			this.asientoVentaService.liberarAsiento(this.asientoVenta.getIdAsiento(), this.programacionSelec.getIdProgramacion(), "LIBRE");
			
			if (this._rutaCompartida.equals("SI")) {
				System.out.println("entro en ruta compartida si");
				this.asientoVentaService.updateAssociatedSeats(this.asientoVenta);
				//actualizaraAsociadosaProceso(this.asientoVenta);
			}else if(this._intCorreEnlace>0){
				this.asientoVentaService.updateAssociatedSeats(this.asientoVenta);
				//actualizaraAsociadosaProceso(this.asientoVenta);
			}
			this.trackingCodigo = this.trackBoletoService.searchByAsientoVenta(this.asientoVenta.getIdasientoventa(),this.asientoVenta.getIdAsiento());
			if(this.trackingCodigo != null){
				this._Serie = this.trackingCodigo.getSerieBoleto(); 
				this._Numero =	(this.trackingCodigo.getNumeroBoleto()) + ""; 
			}
			
			HistorialPasajero historial = new HistorialPasajero();
			historial.setAsientoventaid(this.asientoVenta.getIdasientoventa());
			historial.setDni(this.persona.getDni());
			historial.setfRegistro(new Date());
			historial.setIdOficina(usuarioLogin.getIdoficina());
			historial.setIdprogramacion(this.programacionSelec.getIdProgramacion());
			historial.setIdservicio(this.programacionSelec.getIdServicio());
			historial.setSerieBoleto(this._Serie);
			historial.setNumeroBoleto(Integer.parseInt(this._Numero));
			historial.setOperacion(ConstanteVentas.ESTADO_BOLETO_ANULADO);
			if (this.trackingCodigo.getNrodocempresa()!=null || this.trackingCodigo.getNrodocempresa().equals(""))
			{
				historial.setNroRuc(this.asientoVenta.getDocumentoEmpresa());
			}
			historial.setPiso(this.asientoVenta.getPiso());
			historial.setNroasiento(Integer.parseInt(this.asientoVenta.getNumero()));
			//historial.setIdpadre(this.historialPasajero.getIdHistorialPasajero());
			historial.setMonto(this.asientoVenta.getMonto());
			historial.setPuntoVentaId(this.login.getPv().getIdPuntoVenta());
		
			this.historialPasajeroService.crearHistorialPasajero(historial);
			

			TrackingBoleto track = new TrackingBoleto();
			
			track.setSerieBoleto(this._Serie);
			//track.setNumeroBoleto(this.asientoVenta.getNumero());
			track.setNumeroBoleto(this._Numero);
			track.setFregistro(new Date());
			track.setIdusuario(this.login.getUsuario().getIdusuario());
			track.setIdasiento(this.asientoVenta.getIdAsiento());
			track.setIdasientoventa(this.asientoVenta.getIdasientoventa());
			track.setEstado(ConstanteVentas.ESTADO_BOLETO_ANULADO);
			track.setIdprogramacion(this.asientoVenta.getIdProgramacion());
			track.setIdservicio((Integer)this.servicioService.serviciobyIdProgramacion(this.asientoVenta.getIdProgramacion()).getIdServicio());
			track.setNrodocumento(this.trackingCodigo.getNrodocumento());
			track.setMonto(this.asientoVenta.getMonto());
			track.setMontoReal(this.asientoVenta.getMontoReal());
			
		
			
			if (this.trackingCodigo.getNrodocempresa()!=null || this.trackingCodigo.getNrodocempresa().equals(""))
			{
				track.setNrodocempresa(this.asientoVenta.getDocumentoEmpresa());
			}
			this.trackBoletoService.creartrack(track);
			
		

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void consultarAgenciaExterna()
	{
		System.out.println("ejecuntado consulta g externa");
		try {
			
			if(this.esOficinaExterna)
			{
				this.listaAgenciasExternas = this.agenciaService.findAll();
				this._Serie = null;
				this._Numero =  null;
				for(AsientoVenta av  : this.listaAsientosxVender)
				{
					av.setSerieBoletoMulti("");
					av.setNumeroBoletoMulti(0);
				}
			}else{
				this._Numero = (this.login.getPv().getUltimoBoleto() + 1)+"";
				this._Serie = this.login.getPv().getSerieBoleto();
			}


		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	public void consultarOficinaExterna()
	{
		try {
			if(this.agenciaExterna.getIdagencia() != null && !this.agenciaExterna.getIdagencia().equals(""))
			{
				this.listaOficinasExternas = this.oficinaService.listaOficinasXAgencia(this.agenciaExterna.getIdagencia());	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void buscarPagador()
	{
		System.out.println("inicio buscando pagador prepagado");
		if(this.pagador.getTipoDoc() == null)
		{
			FacesUtils.showFacesMessage("Seleccione un tipo de documento.",1);
		}else if(this.pagador.getTipoDoc().equals("RUC"))
		{
			Empresa emp;
			try {
				emp = this.empresaService.findByNroRUC(this.pagador.getNroDoc().trim());
				if(emp != null)
				{
					this.pagador.setNombre(emp.getRazonSocial());
					this.pagador.setDireccion("----");
					this.pagador.setEmail("----");
					this.pagador.setTelefono("-----");
				}else{
					this.pagador.setNombre("-no encontrado-");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}else if(this.pagador.getTipoDoc().equals("DNI"))
		{
			Persona per;
			try {
				per = this.personaService.findByNroDocumento(this.pagador.getNroDoc().trim());
				if(per != null)
				{
					this.pagador.setDireccion("-----");
					this.pagador.setEmail("-----");
					this.pagador.setNombre(per.getAPaterno()+" "+per.getAMaterno()+" "+per.getNombres());
					this.pagador.setTelefono("-----");
				}else{
					this.pagador.setNombre("-no encontrado-");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("FIN buscando pagador prepagado");
	}	
	
	public void buscarPagadorDelivery()
	{
		if(this.delivery.getTipoDoc() == null)
		{
			FacesUtils.showFacesMessage("Seleccione un tipo de documento para delivery.",1);
		}else if(this.delivery.getTipoDoc().equals("RUC"))
		{
			Empresa emp;
			try {
				emp = this.empresaService.findByNroRUC(this.delivery.getNroDoc().trim());
				if(emp != null)
				{
					this.delivery.setNombre(emp.getRazonSocial());
					this.delivery.setDireccion("----");
					this.delivery.setEmail("----");
					this.delivery.setTelefono("-----");
				}else{
					this.delivery.setNombre("-no encontrado-");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}else if(this.delivery.getTipoDoc().equals("DNI"))
		{
			Persona per;
			try {
				per = this.personaService.findByNroDocumento(this.delivery.getNroDoc().trim());
				if(per != null)
				{
					this.delivery.setDireccion("-----");
					this.delivery.setEmail("-----");
					this.delivery.setNombre(per.getAPaterno()+" "+per.getAMaterno()+" "+per.getNombres());
					this.delivery.setTelefono("-----");
				}else{
					this.delivery.setNombre("-no encontrado-");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}
	

	public void limpiarDocDelivery()
	{
		this.delivery.setNroDoc("");
	}
	
	public void generarCopia()
	{
		if(this.copiaBoleto)
		{
			
			System.out.println("persisitiendo el monto de la copia dle boleto nos e donde"+ this.boletaCopia.getValorCopia());
			this.boletaCopia.setDestino(this.agenciaDestino.getDescripcion());
			this.boletaCopia.setDniPasajero(this.agenciaDestino.getDescripcion());
			this.boletaCopia.setFechaSalida(this.programacionSelec.getfSalidaString());
			this.boletaCopia.setHoraSalida(this.programacionSelec.gethSalida());
			this.boletaCopia.setNombrePasajero(this.persona.getNombres()+" "+this.persona.getAPaterno()+" "+this.persona.getAMaterno());
			this.boletaCopia.setNumeroAsiento(this.asientoSelec.getNumero());
			this.boletaCopia.setNumeroBoleto(this._Serie+"-"+this._Numero);
			this.boletaCopia.setOficinaVenta(this.asientoVenta.getDesOficina());
			this.boletaCopia.setOrigen(this.agenciaOrigen.getDescripcion());
			this.boletaCopia.setRazonSocialPasajero(this.empresa == null ? null: this.empresa.getRazonSocial());
			this.boletaCopia.setResponsableVenta(this.asientoVenta.getCompletoVendedor());
			this.boletaCopia.setRucPasajero(this.empresa == null ? null :this.empresa.getRUC());
			this.boletaCopia.setConcepto("Copia de Boleto de Viaje");
			
			try {
				this.mandarImpresionCopiaBoleto(this.boletaCopia);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
	public void eliminarVentaDeGrupoDeVentaMultiple(AsientoVenta av)
	{
		//liberando
		this.listaAsientosxVender.remove(av);
		av.setEstado(ConstanteVentas.ESTADO_LIBRE);
		av.setFechaventa(null);
		//av.setMonto(this.precioAsiento);
		av.setTipoventa(null);
		av.setSexo("-");
		av.setDocumentoPersona(null);
		av.setDocumentoEmpresa(null);
		try {
			this.asientoVentaService.actualizarVentaAsiento(av);
		} catch (Exception e) {
			e.printStackTrace();
		}
		actualizarDisponibilidadCancela(av.getAsiento(),this.programacionSelec);
	}
	
	
	public void replicarDNI(AsientoVenta av)
	{
		String doc = av.getDocumentoPersona();
		Persona per = av.getPersona();
		for(AsientoVenta asv : this.listaAsientosxVender)
		{
			if(av.getIdasientoventa().intValue() != asv.getIdasientoventa().intValue())
			{
				asv.setDocumentoPersona(doc);
				asv.setPersona(per);
			}
		}
		
	}
	
	public void inhabilitarBoleto()
	{
		try {
			this.asientoVenta.setEstado("LIBRE");
			this.asientoVenta.setSexo("-");
			
			List<TrackingBoleto> tmpreserva = this.trackBoletoService.buscarReservado(this.asientoVenta.getNumero(),ConstanteVentas.ESTADO_RESERVA, this.asientoVenta.getDocumentoPersona(),this.programacionSelec.getIdProgramacion(), this.programacionSelec.getIdServicio());
			boolean grabarSec = false;
			Integer secuencia;
			Secuencia sec= this.secuenciaServices.findbyDescripcion(Constante.SECUENCIA_TRACKING_BOLETO);
			if(!tmpreserva.isEmpty()){
				secuencia = tmpreserva.get(0).getIntsecuencia();
			}else{
				secuencia = Integer.valueOf(sec.getSecuencia())+1;
				grabarSec=true;
			}
			
			List<TrackingBoleto> tracking = this.trackBoletoService.findBySerieAndNumero(this._Serie,this._Numero);
			TrackingBoleto trackingBoleto = tracking.get(0);
			
			trackingBoleto.setFregistro(new Date());
			trackingBoleto.setIdusuario(this.login.getIdUsuario());
			//trackingBoleto.setIdasiento(idasiento);
			//trackingBoleto.setIdasientoventa(idasientoventa);
			trackingBoleto.setEstado(ConstanteVentas.ESTADO_INHABILITADO);
			//trackingBoleto.setIdprogramacion(idprogramacion);
			//trackingBoleto.setIdservicio(idservicio);
			//trackingBoleto.setNrodocumento(nrodocumento);
			//trackingBoleto.setNrodocempresa(nrodocempresa);
			trackingBoleto.setIntsecuencia(secuencia);
			trackingBoleto.setPrepagado(Boolean.FALSE);
			//trackingBoleto.setMonto(monto);
			//trackingBoleto.setMontoReal(montoReal);
			trackingBoleto.setTipoInh(this.inhabilitacion.getTipo());
			trackingBoleto.setMotivoInh(this.inhabilitacion.getMotivo().trim());
			trackingBoleto.setSerieRelacionado(null);
			trackingBoleto.setNumeroRelacionado(null);
			

			//solo cambio de servicio
			//this.login.setSerieBoletoInhabilitado(this._Serie);
			//this.login.setNumeroBoletoInhabilitado(this._Numero);
			
			if(this.inhabilitacion.getTipo().equals("CDATOS") || this.inhabilitacion.getTipo().equals("CPASAJERO"))
			{
				
				if(this.inhabilitacion.getTipo().equals("CPASAJERO"))
				{
					//this.login.setPersonaNueva(this.personaNueva);
					trackingBoleto.setDniNuevoPasajero(this.personaNueva.getDni());
					
				}


			}else if(this.inhabilitacion.getTipo().equals("CSERVICIO") || this.inhabilitacion.getTipo().equals("CSERVICIOPASAJ") || this.inhabilitacion.getTipo().equals("CSERVICIODATOS")){

				if(this.inhabilitacion.getTipo().equals("CSERVICIOPASAJ"))
				{
					//this.login.setPersonaNueva(this.personaNueva);
					//this.login.setCambioServicioPasajero(Boolean.TRUE);
					trackingBoleto.setDniNuevoPasajero(this.personaNueva.getDni());
					
				}
				
			}
			
			this.trackBoletoService.creartrack(trackingBoleto);
			
			//liberando asiento
			this.asientoVentaService.liberarAsiento(this.asientoVenta.getIdAsiento(), this.programacionSelec.getIdProgramacion(), "LIBRE");

		} catch (Exception e) {
			e.printStackTrace();
		}
		this.asientoVenta = new AsientoVenta();
	}
	
	
	public void inhabilitarBoletoDatosPasajeroReembolso()
	{
		
		try {
			//creacion del tracking
			List<TrackingBoleto> tmpreserva = this.trackBoletoService.buscarReservado(this.asientoVenta.getNumero(),ConstanteVentas.ESTADO_RESERVA, this.asientoVenta.getDocumentoPersona(),this.programacionSelec.getIdProgramacion(), this.programacionSelec.getIdServicio());
			boolean grabarSec = false;
			Integer secuencia;
			Secuencia sec= this.secuenciaServices.findbyDescripcion(Constante.SECUENCIA_TRACKING_BOLETO);
			if(!tmpreserva.isEmpty()){
				secuencia = tmpreserva.get(0).getIntsecuencia();
			}else{
				secuencia = Integer.valueOf(sec.getSecuencia())+1;
				grabarSec=true;
			}
			
			//reuperando el nro de serie del boleto a inhabiliar
			List<TrackingBoleto> tracking = this.trackBoletoService.findBySerieAndNumero(this._Serie,this._Numero);
			TrackingBoleto trackingBoleto = tracking.get(0);
			
			trackingBoleto.setFregistro(new Date());
			trackingBoleto.setIdusuario(this.login.getIdUsuario());
			//trackingBoleto.setIdasiento(idasiento);
			//trackingBoleto.setIdasientoventa(idasientoventa);
			trackingBoleto.setEstado(ConstanteVentas.ESTADO_INHABILITADO);
			//trackingBoleto.setIdprogramacion(idprogramacion);
			//trackingBoleto.setIdservicio(idservicio);
			//trackingBoleto.setNrodocumento(nrodocumento);
			//trackingBoleto.setNrodocempresa(nrodocempresa);
			trackingBoleto.setIntsecuencia(secuencia);
			trackingBoleto.setPrepagado(Boolean.FALSE);
			//trackingBoleto.setMonto(monto);
			//trackingBoleto.setMontoReal(montoReal);
			trackingBoleto.setTipoInh(this.inhabilitacion.getTipo());
			trackingBoleto.setMotivoInh(this.inhabilitacion.getMotivo().trim());
			trackingBoleto.setSerieRelacionado(null);
			trackingBoleto.setNumeroRelacionado(null);
			
			
			//solo cambio de servicio
			//this.login.setSerieBoletoInhabilitado(this._Serie);
			//this.login.setNumeroBoletoInhabilitado(this._Numero);

			if(this.inhabilitacion.getTipo().equals("CPASAJERO"))
			{
				//this.login.setPersonaNueva(this.personaNueva);
				trackingBoleto.setDniNuevoPasajero(this.personaNueva.getDni());
					
			}
			
			this.trackBoletoService.creartrack(trackingBoleto);
			
			TrackingBoleto nuevoTrackingBoleto = null;
			
			//si inhabiltaicon tipo es CPasajeo se atualiza el DNI
			if(this.inhabilitacion.getTipo().equals("CPASAJERO"))
			{
				//obteniendo el recien creado
				tracking = this.trackBoletoService.findBySerieAndNumero(this._Serie,this._Numero);
				TrackingBoleto trackingBoletoUltimo = tracking.get(0);
			
				//actualizando el asientoventa
				this.asientoVenta.setDocumentoPersona(this.personaNueva.getDni());
				this.asientoVenta.setSexo(this.personaNueva.getSexo());
				this.asientoVenta.setPersona(this.personaNueva);
				//actualizando
				this.asientoVentaService.actualizarCambioDatosPasajero(this.asientoVenta.getDocumentoPersona(), this.asientoVenta.getDocumentoEmpresa(),
						this.asientoVenta.getSexo(), this.asientoVenta.getIdasientoventa());

				//obteniendo un nuevo nro de serie y boleto
				PuntoVenta ptv = this.puntoVentaService.getPuntoVentaxUsuario(usuarioLogin.getIdusuario());
				this._Numero=String.valueOf((ptv.getUltimoBoleto()+1));	
				this._Serie=ptv.getSerieBoleto();
				
			
				//creando el nuevo trak
				nuevoTrackingBoleto = new TrackingBoleto();
				nuevoTrackingBoleto.setNumeroBoleto(this._Numero);
				nuevoTrackingBoleto.setSerieBoleto(this._Serie);
				nuevoTrackingBoleto.setFregistro(new Date());
				nuevoTrackingBoleto.setIdusuario(this.login.getIdUsuario());
				nuevoTrackingBoleto.setIdasiento(this.asientoVenta.getIdAsiento());
				nuevoTrackingBoleto.setIdasientoventa(this.asientoVenta.getIdasientoventa());
				nuevoTrackingBoleto.setEstado(ConstanteVentas.ESTADO_VENTA);
				nuevoTrackingBoleto.setIdprogramacion(this.asientoVenta.getIdProgramacion());
				nuevoTrackingBoleto.setIdservicio(trackingBoleto.getIdservicio());
				nuevoTrackingBoleto.setNrodocumento(this.asientoVenta.getPersona().getDni());
				
				nuevoTrackingBoleto.setNrodocempresa(this.asientoVenta.getDocumentoEmpresa());
				
				//nuevoTrackingBoleto.setIntsecuencia(secuencia);
				nuevoTrackingBoleto.setPrepagado(trackingBoleto.getPrepagado());
				nuevoTrackingBoleto.setFechaCaducidad(trackingBoleto.getFechaCaducidad());
				nuevoTrackingBoleto.setMonto(trackingBoleto.getMonto());
				nuevoTrackingBoleto.setMontoReal(trackingBoleto.getMontoReal());
				nuevoTrackingBoleto.setTipoInh(null);
				nuevoTrackingBoleto.setMotivoInh(null);
				nuevoTrackingBoleto.setSerieRelacionado(trackingBoleto.getSerieBoleto());
				nuevoTrackingBoleto.setNumeroRelacionado(trackingBoleto.getNumeroBoleto());
				nuevoTrackingBoleto.setDniNuevoPasajero(null);
				nuevoTrackingBoleto.setPuntoVentaId(this.login.getPv().getIdPuntoVenta());
				
				this.trackBoletoService.creartrack(nuevoTrackingBoleto);
				
				//actualizando e anterior:
				trackingBoleto.setSerieRelacionado(this._Serie);
				trackingBoleto.setNumeroRelacionado(this._Numero);
				this.trackBoletoService.actualizarTrackingReferencia(this._Numero, this._Serie, trackingBoletoUltimo.getIdTrackingBoleto());
				
	
			}else if(this.inhabilitacion.getTipo().equals("CDATOS")){//solo cambio de datos
				
				//obteniendo el recien creado
				tracking = this.trackBoletoService.findBySerieAndNumero(this._Serie,this._Numero);
				TrackingBoleto trackingBoletoUltimo = tracking.get(0);
				
				//actualizando el asientoventa
				this.asientoVenta.setPersona(this.persona);
				//this.asientoVenta.setSexo(this.persona.getSexo());
				this.asientoVenta.setEmpresa(this.empresa);
				this.asientoVenta.setDocumentoEmpresa(this.empresa.getRUC());
				//actualizando
				this.asientoVentaService.actualizarCambioDatosPasajero(this.asientoVenta.getDocumentoPersona(), this.asientoVenta.getDocumentoEmpresa(),
						this.asientoVenta.getSexo(), this.asientoVenta.getIdasientoventa());
				
				//obteniendo un nuevo nro de serie y boleto
				PuntoVenta ptv = this.puntoVentaService.getPuntoVentaxUsuario(usuarioLogin.getIdusuario());
				this._Numero=String.valueOf((ptv.getUltimoBoleto()+1));	
				this._Serie=ptv.getSerieBoleto();
				
			
				//creando el nuevo trak
				nuevoTrackingBoleto = new TrackingBoleto();
				nuevoTrackingBoleto.setNumeroBoleto(this._Numero);
				nuevoTrackingBoleto.setSerieBoleto(this._Serie);
				nuevoTrackingBoleto.setFregistro(new Date());
				nuevoTrackingBoleto.setIdusuario(this.login.getIdUsuario());
				nuevoTrackingBoleto.setIdasiento(this.asientoVenta.getIdAsiento());
				nuevoTrackingBoleto.setIdasientoventa(this.asientoVenta.getIdasientoventa());
				nuevoTrackingBoleto.setEstado(ConstanteVentas.ESTADO_VENTA);
				nuevoTrackingBoleto.setIdprogramacion(this.asientoVenta.getIdProgramacion());
				nuevoTrackingBoleto.setIdservicio(trackingBoleto.getIdservicio());
				nuevoTrackingBoleto.setNrodocumento(this.asientoVenta.getPersona().getDni());
				
				nuevoTrackingBoleto.setNrodocempresa(this.asientoVenta.getEmpresa().getRUC());
				
				//nuevoTrackingBoleto.setIntsecuencia(secuencia);
				nuevoTrackingBoleto.setPrepagado(trackingBoleto.getPrepagado());
				nuevoTrackingBoleto.setFechaCaducidad(trackingBoleto.getFechaCaducidad());
				nuevoTrackingBoleto.setMonto(trackingBoleto.getMonto());
				nuevoTrackingBoleto.setMontoReal(trackingBoleto.getMontoReal());
				nuevoTrackingBoleto.setTipoInh(null);
				nuevoTrackingBoleto.setMotivoInh(null);
				nuevoTrackingBoleto.setSerieRelacionado(trackingBoleto.getSerieBoleto());
				nuevoTrackingBoleto.setNumeroRelacionado(trackingBoleto.getNumeroBoleto());
				nuevoTrackingBoleto.setDniNuevoPasajero(null);
				nuevoTrackingBoleto.setPuntoVentaId(this.login.getPv().getIdPuntoVenta());
				
				this.trackBoletoService.creartrack(nuevoTrackingBoleto);
				
				//actualizando e anterior:
				trackingBoleto.setSerieRelacionado(this._Serie);
				trackingBoleto.setNumeroRelacionado(this._Numero);
				this.trackBoletoService.actualizarTrackingReferencia(this._Numero, this._Serie, trackingBoletoUltimo.getIdTrackingBoleto());
				
				
			}else if(this.inhabilitacion.getTipo().equals("CDEVOLUCION")){
				//liberando asiento
				this.asientoVentaService.liberarAsiento(this.asientoVenta.getIdAsiento(), this.programacionSelec.getIdProgramacion(), "LIBRE");
			}


		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void cambiarBoleto()
	{
		try {
			if(!this.personaEncontrada)
			{
				//build nacimiento: 19510924  
				String anio;
				Integer mes;
				String dia;
				Calendar calendario = Calendar.getInstance();
				calendario.setTime(this.fechaCumpleAnios);
				anio = calendario.get(Calendar.YEAR)+"";
				mes = calendario.get(Calendar.MONTH);
				mes++;
				
				dia = calendario.get(Calendar.DAY_OF_MONTH)+"";
				if(Integer.parseInt(dia) < 10)
				{
					dia= "0" + dia;
				}
				
				StringBuilder str = new StringBuilder();
				str.append(anio);
				
				if(mes < 10)
				{
					str.append("0"+mes);
				}else{
					str.append(mes);
				}
				str.append(dia);
				this.personaNueva.setNacimiento(str.toString());
				//build sexo:
				this.personaNueva.setDigito(this.personaNueva.getSexo().equals("M") ? "1":"2");
				//poniendo a mayusculas
				this.personaNueva.setAMaterno(this.personaNueva.getAMaterno().trim().toUpperCase());
				this.personaNueva.setAPaterno(this.personaNueva.getAPaterno().trim().toUpperCase());
				this.personaNueva.setNombres(this.personaNueva.getNombres().trim().toUpperCase());
				this.personaService.crearPersona(this.personaNueva);
			}
			this.asientoVenta.setSexo(this.personaNueva.getSexo());
			this.asientoVenta.setDocumentoPersona(this.personaNueva.getDni());
			this.asientoVenta.setDocumentoEmpresa(null);
			this.asientoVenta.setObservacion(this.observacion);
			this.asientoVentaService.editarVentaAsiento(this.asientoVenta);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.asientoVenta = new AsientoVenta();
	}
	
	public void generarManifiesto(Programacion prog){
		
		RequestContext context = RequestContext.getCurrentInstance();
		// System.out.println("******* SELECT PROGRAMACION "+pr.getIdProgramacion());
		this.listaManifiesto = new ArrayList<>();
		try {
			this.listaManifiesto = this.asientoVentaService.listarManifiestoxProg(prog.getIdProgramacion());
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
					} else {
						av.setPersona(new Persona());
					}
				}
			}

			this.tripulacion_venta = this.tripulacionServices.listarTripulacionxProg(prog.getIdProgramacion());

			this.manifiesto = Boolean.TRUE;
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.imprimirManifiestoPDF();
	}
	
	public void boletoPrepagado()
	{
		System.out.println("iniciand prepagado");
		this.pagador = new PagadorPrepagado();
		this.boletoPrepagado = Boolean.TRUE;
		for(AsientoVenta av : this.listaAsientosxVender)
		{
			PuntoVenta pv = null;
			Integer ultimaBoleta = 0;
			try {
				
				pv = this.puntoVentaService.obtenerPuntoVenta(this.login.getPv().getIdPuntoVenta());
				ultimaBoleta = pv.getUltimaBoleta() + 1;
				this.puntoVentaService.actualizarUltimaBoleta(pv.getIdPuntoVenta(), ultimaBoleta);
				 
				StringBuilder codBoleta = new StringBuilder();

				codBoleta.append(this.login.getPv().getSerieBoleta());
				codBoleta.append("-");
				codBoleta.append("0"+ultimaBoleta);
				av.setCodigoBoleta(codBoleta.toString());
				 
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("fin prepagado");
		
	}
	
	public void setearFormaPago(){
		RequestContext context = RequestContext.getCurrentInstance();
		if(this._tipoPago.equals("TARJETA")||this._tipoPago.equals("MIXTO") || this._tipoPago.equals("TRAN-BANCARIA")){
			context.addCallbackParam("esFormaPago", Boolean.TRUE);
		}else context.addCallbackParam("esFormaPago", Boolean.FALSE);
	}
	
	public void buscarPersonaHistorial()
	{
		try {
			this.personaHistorial = this.personaService.findByNroDocumento(this.persona.getDni());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.historialPasajero(this.personaHistorial);// llama a este metodo
	}
	
	/*  para ser deprecado
	public void historialBoleto(Persona per)
	{
		try {
			this.listaTracking = this.trackBoletoService.findByNroSerieNumeroPersona(per.getDni());
			if(this.listaTracking != null)
			{
				for(TrackingBoleto tra  : this.listaTracking)
				{
					tra.setAsientoVenta(this.asientoVentaService.findById(tra.getIdasientoventa()));
					tra.setPersona(per);
					tra.setProgramacion(this.programacionService.findById(tra.getIdprogramacion()));
					tra.setServicio(this.servicioService.findById(tra.getIdservicio()));
					tra.setAgenciaOrigen(this.agenciaService.findById(tra.getServicio().getOrigen()));
					tra.setAgenciaDestino(this.agenciaService.findById(tra.getServicio().getDestino()));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/
	
	/* 23/09/2015	Evelyn:
	 * Obtiene el historial del pasajero
	 * PENDIENTE DE OPTIMIZAR
	 * */
	public void historialPasajero(Persona per)
	{
		try {
			List<HistorialPasajero> lista= this.historialPasajeroService.getNumberTravel(per.getDni());
			this.listaHistorialPasajero.clear();
			
			if(this.tipoVentaHistorial.equals("VENTA"))
			{
				for(HistorialPasajero  hst : lista)
				{
					hst.setServicio(this.servicioService.findById(hst.getIdservicio()));
					hst.setProgramacion(this.programacionService.findById(hst.getIdprogramacion()));
					hst.setAsientoVenta(this.asientoVentaService.findById(hst.getAsientoventaid()));
					hst.setAgenciaOrigen(this.agenciaService.findById(hst.getServicio().getOrigen()));
					hst.setAgenciaDestino(this.agenciaService.findById(hst.getServicio().getDestino()));
					
					if(hst.getOperacion().equals("VENTA"))
					{
						this.listaHistorialPasajero.add(hst);
					}
				}
			}
			if(this.tipoVentaHistorial.equals("RESERVA"))
			{
				for(HistorialPasajero  hst : lista)
				{
					hst.setServicio(this.servicioService.findById(hst.getIdservicio()));
					hst.setProgramacion(this.programacionService.findById(hst.getIdprogramacion()));
					hst.setAsientoVenta(this.asientoVentaService.findById(hst.getAsientoventaid()));
					hst.setAgenciaOrigen(this.agenciaService.findById(hst.getServicio().getOrigen()));
					hst.setAgenciaDestino(this.agenciaService.findById(hst.getServicio().getDestino()));
					
					if(hst.getOperacion().equals("RESERVA"))
					{
						this.listaHistorialPasajero.add(hst);
					}

				}
			}
			if(this.tipoVentaHistorial.equals("POSTERGADO"))
			{
				for(HistorialPasajero  hst : lista)
				{
					hst.setServicio(this.servicioService.findById(hst.getIdservicio()));
					hst.setProgramacion(this.programacionService.findById(hst.getIdprogramacion()));
					hst.setAsientoVenta(this.asientoVentaService.findById(hst.getAsientoventaid()));
					hst.setAgenciaOrigen(this.agenciaService.findById(hst.getServicio().getOrigen()));
					hst.setAgenciaDestino(this.agenciaService.findById(hst.getServicio().getDestino()));
					
					if(hst.getOperacion().equals("VENTA"))
					{
						break;
					
					}
					if(hst.getOperacion().equals("POSTERGADO") || hst.getOperacion().equals("POSTERGADOFA"))
					{
						this.listaHistorialPasajero.add(hst);
					}

				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println("tama?o d ela ista:"+this.listaHistorialPasajero.size());
		
	}
	
	
	@SuppressWarnings("unchecked")
	public void buscarMovimientoBoleto()
	{
		try {
			this.listaTracking = this.trackBoletoService.findBySerieNumeroBoleto(this.serieBoletoMov, this.numeroBoletoMov);
			
			if(this.listaTracking != null)
			{
				
				for(TrackingBoleto tra  : this.listaTracking)
				{
					tra.setAsientoVenta( this.asientoVentaService.findById(tra.getIdasientoventa()));
					tra.setPersona(this.personaService.findByNroDocumento(tra.getNrodocumento()));
					tra.setProgramacion(this.programacionService.findById(tra.getIdprogramacion()));
					tra.setServicio(this.servicioService.findById(tra.getIdservicio()));
					tra.setAgenciaOrigen(this.agenciaService.findById(tra.getServicio().getOrigen()));
					tra.setAgenciaDestino(this.agenciaService.findById(tra.getServicio().getDestino()));
				}
				
				//el ultimo
				TrackingBoleto ultimoTrack  = this.listaTracking.get(this.listaTracking.size() - 1 );
				
				//si el ultimo es venta o reserva:
				if( ultimoTrack.getEstado().equals("VENTA") || ultimoTrack.getEstado().equals("RESERVA") )
				{
					ultimoTrack.setVerBoleto(Boolean.TRUE);
				}

				//Collections.sort(this.listaTracking, new TrackingBoleto());    
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void movimientoBoleto()
	{
		try {
			this.listaTracking = this.trackBoletoService.findBySerieNumeroBoleto(this.serieBoletoAnul, this.numeroBoletoAnul);
			if(this.listaTracking != null)
			{
				for(TrackingBoleto tra  : this.listaTracking)
				{
					tra.setAsientoVenta( this.asientoVentaService.findById(tra.getIdasientoventa()));
					tra.setPersona(this.personaService.findByNroDocumento(tra.getNrodocumento()));
					tra.setProgramacion(this.programacionService.findById(tra.getIdprogramacion()));
					tra.setServicio(this.servicioService.findById(tra.getIdservicio()));
					tra.setAgenciaOrigen(this.agenciaService.findById(tra.getServicio().getOrigen()));
					tra.setAgenciaDestino(this.agenciaService.findById(tra.getServicio().getDestino()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
	public void activarCambioServicio()
	{
		this.asientoPasajero = new AsientoPasajero();
		this.asientoPasajero.setAsiento(this.asientoSelec);
		this.asientoPasajero.setAsientoVenta(this.asientoVenta);
		this.asientoPasajero.setPasajero(this.persona);
		this.asientoPasajero.setProgramacion(this.programacionSelec);
		this.asientoPasajero.setServicio(this.programacionSelec.getServicio());
		this.login.setAsientoPasajero(this.asientoPasajero);
		
	}
	
	
//liberar todos los asientos que estan en estado 'PROCESO'
	public void liberarAsiento()
	{
		AsientoVenta asientoVe = new AsientoVenta();
		asientoVe.setIdProgramacion(this.programacionSelec.getIdProgramacion());
		asientoVe.setIdusuarioventa(this.login.getIdUsuario());
		asientoVe.setEstado(ConstanteVentas.LIBERAR_X_USUARIO);
		asientoVe.setFechaventa(null);
		asientoVe.setTipoventa(null);
		asientoVe.setSexo("-");
		asientoVe.setDocumentoPersona(null);
		asientoVe.setDocumentoEmpresa(null);
		asientoVe.setfSalida(this.programacionSelec.getfSalida());
		asientoVe.setHorareserva(new Date());
		
		try {
			this.asientoVentaService.liberarVentaAsiento(asientoVe);
			this.listaAsientosxVender.clear();
			
			this.disponibles = this.asientoVentaService.countbyprogramacionAndEstado(this.programacionSelec.getIdProgramacion(),new String("LIBRE"),Boolean.TRUE);
			this.disponiblesP1 = this.asientoVentaService.countbyprogramacionAndEstadoXPiso(this.programacionSelec.getIdProgramacion(), "LIBRE", new Integer(1),Boolean.TRUE);
			this.disponiblesP2 = this.asientoVentaService.countbyprogramacionAndEstadoXPiso(this.programacionSelec.getIdProgramacion(), "LIBRE", new Integer(2),Boolean.TRUE);
			this.programacionSelec.setDisponibles(this.disponibles);
			this.programacionSelec.setDisponibleP1(this.disponiblesP1);
			this.programacionSelec.setDisponibleP2(this.disponiblesP2);
			
			if (this._rutaCompartida.equals("SI")) {
				this.asientoVentaService.updateAssociatedSeats(asientoVe);
				//actualizaraAsociadosaLiberar(asientoVe);
			}else if(this._intCorreEnlace>0){
				this.asientoVentaService.updateAssociatedSeats(asientoVe);
				//actualizaraAsociadosaLiberar(asientoVe);
			}
			this.pintarBus(this.programacionSelec);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	
	public void buscarPersonaPorApellidos()
	{
		try {
		 this.listaPersonasPorApellido  = this.personaService.findByApellidos(this.persona.getAPaterno().trim().toUpperCase(), this.persona.getAMaterno().trim().toUpperCase());
		 System.out.println("listaPersonasPorApellido: "+listaPersonasPorApellido.size());
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

/*
	public void limpiarDialogoClienteVentaMultiple(AsientoVenta asv)
	{
		this.asientoVenta = asv;
		this.persona = new Persona();
		this.listaPersonasPorApellido = new ArrayList<>();
	}
	*/
	
	public void seleccionarPersonaCliente(Persona per)
	{
		try {
			System.out.println("Nueva forma de calcular la edad: "+per.getNacimiento());
			if (per.getNacimiento() != null) {
			    per.setEdad(this.operacionCalculoEdad(new SimpleDateFormat("yyyyMMdd").parse(per.getNacimiento().trim())));
			    if (per.getEdad() < 18) {
			    	this.observacion = "El asiento " + this.asientoVenta.getNumero() + " es menor de edad, debe presentar DNI y carta notarial";
			    }
			} else {
				this.observacion = "";
			}
			
			this.persona = per;
			this.personaEncontrada = Boolean.TRUE;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void pinchar(AsientoVenta av)
	{
		av.setDocumentoPersona(this.persona.getDni());
	}
	
	public void limpiarDialogoCliente()
	{
		this.persona = new Persona();
                this.persona.setTipodocumento(this.tipo_documento);
		this.listaPersonasPorApellido = new ArrayList<>();
		this.listaFiltroPersonasPorApellido = new ArrayList<>();

	
		
	}

	public void historialConsultaBoleto(Persona per)
	{
		try {
			this.listaTracking = this.trackBoletoService.findByNroSerieNumeroPersona(per.getDni());
			if(this.listaTracking != null)
			{
				for(TrackingBoleto tra  : this.listaTracking)
				{
					tra.setAsientoVenta( this.asientoVentaService.findById(tra.getIdasientoventa()));
					tra.setPersona(per);
					tra.setProgramacion(this.programacionService.findById(tra.getIdprogramacion()));
					tra.setServicio(this.servicioService.findById(tra.getIdservicio()));
					tra.setAgenciaOrigen(this.agenciaService.findById(tra.getServicio().getOrigen()));
					tra.setAgenciaDestino(this.agenciaService.findById(tra.getServicio().getDestino()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void mandarImpresion(List<AsientoVenta> listaAV,Boolean valorNominal)
	{
		System.out.println("INICIO - mandarImpresion");
		System.out.println("Cantidad de asientos vendidos: " + listaAV.size() );
		//Bean Datasource			
		Boleto boleto = new Boleto();
		List<Boleto> lista = new ArrayList<>();
		
		String pathJasper="/resources/reports/jasper/formatoboleto.jasper"; 
        String theUserPrinterName = "Virtual_PDF_Printer";//impresora local al servidor de tomcat
//        String theUserPrinterName = "\\\\"+this.login.getPv().getNumeroIP()+"\\"+this.login.getPv().getNombreImpresora(); //servidor produccion
		System.out.println("nombre impresora:"+theUserPrinterName);
		for(AsientoVenta asv : listaAV)
        {
			lista.clear();
			boleto = new Boleto();
        	boleto.setDestino(this.agenciaDestino.getDescripcion());
        	boleto.setDni(asv.getDocumentoPersona());
        	boleto.setFechaEmision(Utils.dateAString(new Date()));
        	boleto.setFechaViaje(this.programacionSelec.getfSalidaString());
        	boleto.setHoraViaje(this.programacionSelec.gethSalida());
        	boleto.setNumeroAsiento(asv.getNumero());
        	boleto.setOrigen(this.agenciaOrigen.getDescripcion());
        	Persona per = null;
        	Empresa emp = null;
        	try {
				per = this.personaService.findByNroDocumento(asv.getDocumentoPersona());
				emp = this.empresaService.findByNroRUC(asv.getDocumentoEmpresa());
			} catch (Exception e) {
				e.printStackTrace();
			}
        	boleto.setPasajero(per.getAPaterno() + " "+per.getAMaterno()+" "+per.getNombres());
        	boleto.setEmpresa(emp==null?" ":emp.getRazonSocial());
        	boleto.setRuc(emp==null?" ":emp.getRUC());
        	boleto.setUsuario(this.login.getUsuario().getLogin());
        	
        	if(valorNominal)
        	{
        		boleto.setTotalLetras(new NumeroALetra().Convertir(asv.getMonto()+"", Boolean.TRUE, "Nuevos Soles"));
            	boleto.setValorTotal(asv.getMonto()+"");
        	}else{
        		boleto.setTotalLetras(new NumeroALetra().Convertir(asv.getMontoReal()+"", Boolean.TRUE, "Nuevos Soles"));
            	boleto.setValorTotal(asv.getMontoReal()+"");

        	}
        	System.out.println(boleto.getDni()+" -"+ boleto.getNumeroAsiento()+ " -"+boleto.getValorTotal());
        	
        	lista.add(boleto);
        	
        	try {
        		//this.imprimirBoletoAPPLE(lista);
        		this.imprimirBoleto(lista,pathJasper,theUserPrinterName);
        	} catch (Exception e) {
				e.printStackTrace();
			}

        }
		System.out.println("FIN - mandarImpresion");
		
	} 
	
	public void mandarImpresionAPPLE()
	{
		System.out.println("Lista de asiento por vender 2: " + this.listaAsientosxVender);
		List <AsientoVenta> listaAV = new ArrayList<AsientoVenta>(this.listaAsientosxVender);
		boolean valorNominal = true;
		System.out.println("INICIO - mandarImpresionAPPLE");
		System.out.println("Cantidad de asientos vendidos: " + listaAV.size() );
		//Bean Datasource			
		Boleto boleto = new Boleto();
		List<Boleto> lista = new ArrayList<>();
		
		String pathJasper="/resources/reports/jasper/formatoboleto.jasper"; 
        String theUserPrinterName = "Virtual_PDF_Printer";//impresora local al servidor de tomcat
//        String theUserPrinterName = "\\\\"+this.login.getPv().getNumeroIP()+"\\"+this.login.getPv().getNombreImpresora(); //servidor produccion
		System.out.println("nombre impresora:"+theUserPrinterName);
		for(AsientoVenta asv : listaAV)
        {
			lista.clear();
			boleto = new Boleto();
        	boleto.setDestino(this.agenciaDestino.getDescripcion());
        	boleto.setDni(asv.getDocumentoPersona());
        	boleto.setFechaEmision(Utils.dateAString(new Date()));
        	boleto.setFechaViaje(this.programacionSelec.getfSalidaString());
        	boleto.setHoraViaje(this.programacionSelec.gethSalida());
        	boleto.setNumeroAsiento(asv.getNumero());
        	boleto.setOrigen(this.agenciaOrigen.getDescripcion());
        	Persona per = null;
        	Empresa emp = null;
        	try {
				per = this.personaService.findByNroDocumento(asv.getDocumentoPersona());
				emp = this.empresaService.findByNroRUC(asv.getDocumentoEmpresa());
			} catch (Exception e) {
				e.printStackTrace();
			}
        	boleto.setPasajero(per.getAPaterno() + " "+per.getAMaterno()+" "+per.getNombres());
        	boleto.setEmpresa(emp==null?" ":emp.getRazonSocial());
        	boleto.setRuc(emp==null?" ":emp.getRUC());
        	boleto.setUsuario(this.login.getUsuario().getLogin());
        	
        	if(valorNominal)
        	{
        		boleto.setTotalLetras(new NumeroALetra().Convertir(asv.getMonto()+"", Boolean.TRUE, "Nuevos Soles"));
            	boleto.setValorTotal(asv.getMonto()+"");
        	}else{
        		boleto.setTotalLetras(new NumeroALetra().Convertir(asv.getMontoReal()+"", Boolean.TRUE, "Nuevos Soles"));
            	boleto.setValorTotal(asv.getMontoReal()+"");

        	}
        	System.out.println(boleto.getDni()+" -"+ boleto.getNumeroAsiento()+ " -"+boleto.getValorTotal());
        	
        	lista.add(boleto);
        	
        	try {
        		this.imprimirBoletoAPPLE(lista);
        		//this.imprimirBoleto(lista,pathJasper,theUserPrinterName);
        	} catch (Exception e) {
				e.printStackTrace();
			}

        }
		System.out.println("FIN - mandarImpresionAPPLE");
		
	} 
	
	public void imprimirBoletoAPPLE(List<Boleto> listab)
	{
		System.out.println("INICIO - imprimirBoletoAPPLE");
		
		try {

			ServletContext servletContext = (ServletContext) (FacesContext.getCurrentInstance().getExternalContext().getContext());
			Map<String, Object> input = new HashMap<String, Object>();
			input.put(JRParameter.REPORT_LOCALE, new Locale("es"));
			input.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);//pagina larga

			String path = ExportarArchivo.getPath("/resources/reports/jasper/formatoboleto.jasper");
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

			byte[] bytes = ExportarArchivo.exportPdf(path, input, listab);

			ExportarArchivo.executePdf(bytes, "boleta.pdf");
			FacesContext.getCurrentInstance().responseComplete();
			
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		this.listaAsientosxVender = new ArrayList<>();		//LIMPIAR LA LISTA DESPUES DE REALIZADO LA VENTA Y LA IMPRESIONA
		System.out.println("FIN - imprimirBoletoAPPLE");
	}
	
	/*
	 * Este metodo esta pendiente de revisar
	 * */
	public void imprimirBoleto(List<Boleto> listab,String pathJasper,String theUserPrinterName)
	{
		System.out.println("Inicio - imprimirBoleto");
		
		JasperPrint jasperPrint;
	    JRPrintServiceExporter  exporter = null;
	    try
	    {
			Map<String, Object> input = new HashMap<String, Object>();
			input.put(JRParameter.REPORT_LOCALE, new Locale("es"));
			input.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);//pagina larga

			//Enviando a ireport
			ServletContext scontext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        	
	        jasperPrint = JasperFillManager.fillReport(scontext.getRealPath(pathJasper), input, new JRBeanCollectionDataSource(listab));

	        PrinterName printerName =new PrinterName(theUserPrinterName, null);   
            PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
            printServiceAttributeSet.add(printerName);
	        
	        //enviando el jasperPrint
	        exporter = new JRPrintServiceExporter(); 
	        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET,printServiceAttributeSet);
	        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE); 
	        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE); 
	        exporter.exportReport(); 
	        
	    }catch (JRException e){
	        System.out.println("Caught exception!!!");	       
	        e.printStackTrace();
	        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.TRUE); 
	        try {   
	            exporter.exportReport();
	        }
	        catch (JRException e2)
	        {
	            e2.printStackTrace();
	        }
	    } 
	    System.out.println("Fin - imprimirBoleto");
	}


	//boleto prepagado
	/*
	public void mandarImpresionBoletaPRepagado(List<AsientoVenta> listaAV)
	{
	
		
		Boleto boleto = new Boleto();
		List<Boleto> lista = new ArrayList<>();
		
		String pathJasper="/resources/reports/jasper/boletaPrepagado.jasper"; 
//        String theUserPrinterName = "TU_Impresora_Amiga";//impresora local
        String theUserPrinterName = "zebra";//impresora local
        //String theUserPrinterName = "\\\\"+this.puntoVenta.getNumeroIP()+"\\epson-fx-890"; //servidor
		
		for(AsientoVenta asv : listaAV)
        {
			
			boleto = new Boleto();
        	boleto.setDestino(this.agenciaDestino.getDescripcion());
        	boleto.setDni(asv.getDocumentoPersona());
        	boleto.setFechaEmision(Utils.dateAString(new Date()));
        	boleto.setFechaViaje(this.programacionSelec.getfSalidaString());
        	boleto.setHoraViaje(this.programacionSelec.gethSalida());
        	boleto.setNumeroAsiento(asv.getNumero());
        	boleto.setOrigen(this.agenciaOrigen.getDescripcion());
        	Persona per = null;
        	Empresa emp = null;
        	try {
				per = this.personaService.findByNroDocumento(asv.getDocumentoPersona());
				emp = this.empresaService.findByNroRUC(asv.getDocumentoEmpresa());
			} catch (Exception e) {
				e.printStackTrace();
			}
        	boleto.setPasajero(per.getAPaterno() + " "+per.getAMaterno()+" "+per.getNombres());
        	boleto.setEmpresa(emp==null?" ":emp.getRazonSocial());
        	boleto.setRuc(emp==null?" ":emp.getRUC());
        	boleto.setTotalLetras(new NumeroALetra().Convertir(asv.getMonto()+"", Boolean.TRUE, "Nuevos Soles"));
        	boleto.setUsuario(this.login.getUsuario().getLogin());
        	boleto.setValorTotal(asv.getMonto()+"");
        	
        	//prepagados
        	boleto.setPagador(this.pagador.getNombre());
        	boleto.setMontoPrepagado(montoPrepagado+"");
        	boleto.setTipoDoc(this.pagador.getTipoDoc());
        	boleto.setNumeroDoc(this.pagador.getNroDoc());
        	boleto.setCodigoBoleta(asv.getCodigoBoleta());
        
        	lista.add(boleto);
        }
		
		this.imprimirBoleto(lista,pathJasper,theUserPrinterName);
		
	}
	*/
	
	
	public void mandarImpresionBoletaPrepagado() throws Exception
	{
		
		try {

			ServletContext servletContext = (ServletContext) (FacesContext.getCurrentInstance().getExternalContext().getContext());
			String p_logo = ExportarArchivo.getPath("/resources/img/logoittsa.jpg");
			Map<String, Object> input = new HashMap<String, Object>();
			input.put("P_LOGO", p_logo);
			input.put(JRParameter.REPORT_LOCALE, new Locale("es"));

			String path = ExportarArchivo.getPath("/resources/reports/jxrml/boletaVentaBoletoPrepagado.jasper");
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

			byte[] bytes = ExportarArchivo.exportPdf(path, input, this.asientoVentaPrepagados);

			ExportarArchivo.executePdf(bytes, "boletaBoletoPrepgado.pdf");
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception e) {

			//depurador.info("ERROR ID==>" + e);
			e.printStackTrace();
		}
		
		
	}
	
	public void mandarReImpresionBoleto(){
	
		
		Boleto boleto = new Boleto();
		List<Boleto> lista = new ArrayList<>();
		
		String pathJasper="/resources/reports/jxrml/repImpresionBoleto.jasper"; 
//        String theUserPrinterName = "TU_Impresora_Amiga";//impresora local
        String theUserPrinterName = "zebra";//impresora local
        //String theUserPrinterName = "\\\\"+this.puntoVenta.getNumeroIP()+"\\epson-fx-890"; //servidor
		
			
			boleto = new Boleto();
        	boleto.setDestino(this.rePrintBoleto.getAgenciaDestino().getDescripcion());
        	boleto.setDni(this.rePrintBoleto.getDni());
        	boleto.setFechaEmision(Utils.dateAString(new Date()));
        	boleto.setFechaViaje(this.rePrintBoleto.getProgramacion().getfSalidaString());
        	boleto.setHoraViaje(this.rePrintBoleto.getProgramacion().gethSalida());
        	boleto.setNumeroAsiento(this.rePrintBoleto.getAsientoVenta().getNumero());
        	boleto.setOrigen(this.rePrintBoleto.getAgenciaOrigen().getDescripcion());
        	Persona per = null;
        	Empresa emp = null;
        	try {
				per = this.personaService.findByNroDocumento(this.rePrintBoleto.getDni());
				//emp = this.empresaService.findByNroRUC(asv.getDocumentoEmpresa());
			} catch (Exception e) {
				e.printStackTrace();
			}
        	boleto.setCostoPasaje(this.rePrintBoleto.getAsientoVenta().getMonto().toString());
        	boleto.setPasajero(per.getAPaterno() + " "+per.getAMaterno()+" "+per.getNombres());
        	boleto.setEmpresa(emp==null?" ":emp.getRazonSocial());
        	boleto.setRuc(emp==null?" ":emp.getRUC());
        	boleto.setTotalLetras(new NumeroALetra().Convertir(new BigDecimal(1)+"", Boolean.TRUE, "Nuevos Soles"));
        	boleto.setUsuario(this.login.getUsuario().getLogin());
        	boleto.setValorTotal(new BigDecimal(1)+"");
        	boleto.setCodBoleto(this.rePrintBoleto.getSerieBoleto()+"-"+this.rePrintBoleto.getNumeroBoleto());
        	
        	
        	//prepagados
//        	boleto.setPagador(this.pagador.getNombre());
//        	boleto.setMontoPrepagado(montoPrepagado+"");
//        	boleto.setTipoDoc(this.pagador.getTipoDoc());
//        	boleto.setNumeroDoc(this.pagador.getNroDoc());
        	boleto.setCodigoBoleta(this.rePrintBoleto.getPuntoVenta().getSerieBoleta()+"-"+this.rePrintBoleto.getPuntoVenta().getUltimaBoleta());
        
        	lista.add(boleto);
        	
        	// actualizando la ultimo numero de boleta
        	try {
        		PuntoVenta pv = new PuntoVenta();
        		pv.setIdPuntoVenta(this.rePrintBoleto.getPuntoVenta().getIdPuntoVenta());
        		pv.setUltimaBoleta(this.rePrintBoleto.getPuntoVenta().getUltimaBoleta()+1);
				puntoVentaService.actualizarUltimaBoletaPuntoVenta(pv);
			} catch (Exception e) {
				e.printStackTrace();
			}
        	
        
		
		this.imprimirBoleto(lista,pathJasper,theUserPrinterName);
		
	}
	
	
	
	public void mandarImpresionCopiaBoleto(CopiaBoleto copia) throws Exception
	{
		List<CopiaBoleto> listaCopia = new ArrayList<>();
		listaCopia.add(copia);
		
		try {

			ServletContext servletContext = (ServletContext) (FacesContext.getCurrentInstance().getExternalContext().getContext());
			String p_logo = ExportarArchivo.getPath("/resources/img/logoittsa.jpg");
			Map<String, Object> input = new HashMap<String, Object>();
			input.put("P_LOGO", p_logo);
			input.put(JRParameter.REPORT_LOCALE, new Locale("es"));

			String path = ExportarArchivo.getPath("/resources/reports/jxrml/boletaVentaCopiaBoleto.jasper");
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

			//byte[] bytes = ExportarArchivo.exportPdf(path, input, listaCopia); //Desgarfa PDF
			//ExportarArchivo.executePdf(bytes, "boletaCopiaBoleto.pdf"); //Desgarfa PDF
			
			byte[] bytes = ExportarArchivo.exportPdfAndSendDefaultPrint(path, input, listaCopia); //Imprime PDF
			ExportarArchivo.executePdfAndOpen(bytes, "boletaCopiaBoleto.pdf"); //Imprime PDF
			
			
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception e) {

			//depurador.info("ERROR ID==>" + e);
			e.printStackTrace();
		}
		
	}
	
	
	
	public void imprimirBoletoPrepagado()
	{
		System.out.println("enviando boleto prepagado");
		this.listaAsientosxVender.add(this.asientoVenta);
		this.mandarImpresion(this.listaAsientosxVender,Boolean.TRUE);
		this.listaAsientosxVender.clear();
		this.asientoVenta = new AsientoVenta();
	}
	
	public void inicioHistorial()
	{
		System.out.println("iniciandlo clean de historeial");
		this.personaHistorial = new Persona();
		this.tipoVentaHistorial = null;
		this.listaTracking = null;
		this.persona= new Persona();
		this.listaHistorialPasajero.clear();
		this.listaHistorialPasajeroDetalle.clear();
		
	}
	
	public void inicioAnularBoletos()
	{
		System.out.println("iniciandlo clean de anulacion de boletos");
		this.serieBoletoAnul = null;
		this.numeroBoletoAnul = null;
		this.trackingBoletoAnul= new TrackingBoleto();
		
	}
	
	public void cambiarVista(String tip)
	{
		this.tipoVista =  tip;
		this.destinoSeleccionado = Boolean.FALSE;
		if(this.tipoVista.equals("ARBOL"))
		{
			this.raiz = new DefaultTreeNode("raiz", null);
			cargaRaiz(this.raiz);
		}else{
			this.agenciaOrigen = new Agencia();
			this.agenciaDestino = new Agencia();

			RequestContext context = RequestContext.getCurrentInstance();
			context.update("dlgDetVistaCalendario");
			context.execute("dlgVistaCalendario.show();");


		}
		
		
	}
	
	public void listarDestinos()
	{
		this.listaAgDestinos = this.listarAgeDestino(this.agenciaOrigen.getIdagencia());
	}
	 public void seleccionarDestino()
	 {
		 this.destinoSeleccionado = Boolean.TRUE;
	 }
	
	 public void seleccionarFechaCalendario()
	 {
		 this.visibleBus = Boolean.FALSE;
		 this.manifiesto = Boolean.FALSE;
		 try {
					this.agenciaOrigen = this.agenciaService.findById(this.agenciaOrigen.getIdagencia());
					this.agenciaDestino = this.agenciaService.findById(this.agenciaDestino.getIdagencia());
					this.fechaString = Utils.dateAString(temp);
					this.fechaSelec = new SimpleDateFormat("yyyy-MM-dd").format(temp);
					prepararProgramacion(this.agenciaOrigen.getIdagencia(),this.agenciaDestino.getIdagencia(), this.fechaSelec);
					
					if(this.listaProgramacion.isEmpty())
					{
						RequestContext.getCurrentInstance().execute("dlgNoProgramacion.show();");
					}
					
			} catch (Exception e) {
					e.printStackTrace();
			} 
	 }

	 
	 /*
	  * para ser deprecado
	 public void irFechaViajeHistorial(TrackingBoleto tkbol)
	 {
		 try {
				this.agenciaOrigen = this.agenciaService.findById(tkbol.getAgenciaOrigen().getIdagencia());
				this.agenciaDestino = this.agenciaService.findById(tkbol.getAgenciaDestino().getIdagencia());
				this.fechaString = tkbol.getProgramacion().getfSalidaString();
				String []ff = tkbol.getProgramacion().getfSalidaString().split("-");
				StringBuilder builder = new StringBuilder();
				builder.append(ff[2]);
				builder.append("-");
				builder.append(ff[1]);
				builder.append("-");
				builder.append(ff[0]);
				this.fechaSelec = builder.toString();
				prepararProgramacion(this.agenciaOrigen.getIdagencia(),this.agenciaDestino.getIdagencia(), this.fechaSelec);
				Programacion prog = this.programacionService.findById(tkbol.getIdprogramacion());
				prog.setServicio(this.servicioService.findById(tkbol.getIdservicio()));
				this.pintarBus(prog);
				//this.visibleBus = Boolean.TRUE;
				this.manifiesto = Boolean.TRUE;
			} catch (Exception e) {
					e.printStackTrace();
			} 
	 }

	 */
	
	 
	 
	 
	 public void activarBoletoPromocional()
	 {
		 try {
				this.listaUsuarioAutorizante = this.usuarioAutorizanteServices.listaUsuarioAutorizanteActivos();
				String value = parametroServices.findParametro_byNombre("BOLETO_PROMOCIONAL");
				value+=".00";
				this.costoBoletoPromocional = new BigDecimal(value);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
	 }
	 
	 public  void activarPromocion(){
		 this.listaCmbPromociones.clear();
		 this.listaCmbPromociones = new ArrayList<>();
		 
		 this.listaCmbPromociones = this.promocionServices.listPromocionesByFiltros(this.programacionSelec.getOrigen(), this.programacionSelec.getDestino());
		 
		 if(!this.enablePromocion){
			 this.listaCmbPromociones.clear();
			 this.listaCmbPromociones = new ArrayList<>();
			 this.dsctoBoletoMultiple = "";
			 this.idPromocionSelect = null;
			 if(this.listaAsientosxVender!=null){
					if(this.listaAsientosxVender.size()>0){
						for (AsientoVenta avs : this.listaAsientosxVender) {
							avs.setMonto(avs.getCostoRealAsiento());
							avs.setIdpromocion(null);
							avs.setIdusuarioautorizante(null);
						}
					}
				}
		 }
	 }
	 
	 public void setPromocionBoleto(String tipoVenta){
		 try {
			this.promocion = this.promocionServices.findById(this.idPromocionSelect);
			this.tienePromocion= Boolean.TRUE;
			BigDecimal precioP1 = BigDecimal.ZERO;
			BigDecimal precioP2 = BigDecimal.ZERO;
			this.dsctoBoletoMultiple = "";
			
			if(this.promocion.getTipo().equals("C")) //cantidad a descontar
			{
				this.dsctoBoletoMultiple = "- S/"+this.promocion.getValorBoleto();
				precioP1 = this.programacionSelec.getServicio().getPrecio1().subtract(new BigDecimal(this.promocion.getValorBoleto())) ;
				precioP2 = this.programacionSelec.getServicio().getPrecio2().subtract(new BigDecimal(this.promocion.getValorBoleto())) ;
				
			}else if(this.promocion.getTipo().equals("F")){ //precio fijo a pagar
				this.dsctoBoletoMultiple = "FIJO S/"+this.promocion.getValorBoleto();
				precioP1 = new BigDecimal(this.promocion.getValorBoleto());
				precioP2 = new BigDecimal(this.promocion.getValorBoleto());
				
			}else if(this.promocion.getTipo().equals("P")){ //porcentaje a descontar
				this.dsctoBoletoMultiple = "- "+this.promocion.getValorBoleto()+"% ";
				precioP1 = this.programacionSelec.getServicio().getPrecio1().multiply(new BigDecimal((1-this.promocion.getValorBoleto()/100)));
				precioP2 = this.programacionSelec.getServicio().getPrecio2().multiply(new BigDecimal((1-this.promocion.getValorBoleto()/100)));

			}
			
			if(tipoVenta.equals("INDIVIDUAL")){

			//actualizando los precios
			if (this.asientoSelec.getPiso().equals(1)) {
				this.asientoVenta.setMonto(this.programacionSelec.getServicio().getPrecio1());
			//	this.precioAsiento = this.asientoVenta.getMonto();
				this.precioAsientoPromocion = precioP1;
			} else {
				this.asientoVenta.setMonto(this.programacionSelec.getServicio().getPrecio2());
			//	this.precioAsiento = this.asientoVenta.getMonto();
				this.precioAsientoPromocion = precioP2;
			}
			
			} else if(tipoVenta.equals("MULTIPLE")){
				if(this.listaAsientosxVender!=null){
					if(this.listaAsientosxVender.size()>0){
						for (AsientoVenta avs : this.listaAsientosxVender) {
							if(avs.getAsiento().getPiso().equals(1)){
								avs.setMonto(precioP1);
							} else{
								avs.setMonto(precioP2);
							}
							avs.setIdpromocion(this.promocion.getIdPromocion());
							avs.setIdusuarioautorizante(this.promocion.getIdAutorizante());
						}
					}
				}
			}
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	 }
	 
	 
	 public void buscarpromocionidaVuelta()
	 {
		 	//buscando promociones asocidas de solo ida-vuelta
		 if(this.idaVuelta)
		 {
			 try {
					this.promocion = this.promocionServices.findByServicioIdaVuelta(this.programacionSelec.getServicio().getIdServicio());
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				if(this.promocion!=null)
				{
					//comparamos la afcehas de vigencia contra la fecha de compra actual
					if(this.fechaActual.after(this.promocion.getfInicio()) && this.fechaActual.before(this.promocion.getfFin()) )
					{
						this.tienePromocion= Boolean.TRUE;
						
						
						if(this.promocion.getTipo().equals("C")) //cantidad a descontar
						{
							this.programacionSelec.getServicio().setPrecio1(this.programacionSelec.getServicio().getPrecio1().subtract(new BigDecimal(this.promocion.getValorBoleto())) );
							this.programacionSelec.getServicio().setPrecio2(this.programacionSelec.getServicio().getPrecio2().subtract(new BigDecimal(this.promocion.getValorBoleto())) );
							
						}else if(this.promocion.getTipo().equals("F")){ //precio fijo a pagar
							
							this.programacionSelec.getServicio().setPrecio1(new BigDecimal(this.promocion.getValorBoleto()));
							this.programacionSelec.getServicio().setPrecio2(new BigDecimal(this.promocion.getValorBoleto()));
							
						}else if(this.promocion.getTipo().equals("P")){ //porcentaje a descontar

							this.programacionSelec.getServicio().setPrecio1(this.programacionSelec.getServicio().getPrecio1().multiply(new BigDecimal((1-this.promocion.getValorBoleto()/100))));
							this.programacionSelec.getServicio().setPrecio2(this.programacionSelec.getServicio().getPrecio2().multiply(new BigDecimal((1-this.promocion.getValorBoleto()/100))));

						}

						//actualizando los precios
						if (this.asientoSelec.getPiso().equals(1)) {
							this.asientoVenta.setMonto(this.programacionSelec.getServicio().getPrecio1());
							this.precioAsiento = this.asientoVenta.getMonto();
							this.precioAsientoPromocion = this.programacionSelec.getServicio().getPrecio1();
						} else {
							this.asientoVenta.setMonto(this.programacionSelec.getServicio().getPrecio2());
							this.precioAsiento = this.asientoVenta.getMonto();
							this.precioAsientoPromocion = this.programacionSelec.getServicio().getPrecio2();
						}



					}else{
						this.tienePromocion= Boolean.FALSE;
						this.noPromocionIdaVuelta = Boolean.TRUE;
					}
				}else{
					this.tienePromocion= Boolean.FALSE;
					this.noPromocionIdaVuelta = Boolean.TRUE;
					this.promocion = new Promocion();
				}

		 }else{
			 this.tienePromocion= Boolean.FALSE;
			 this.noPromocionIdaVuelta = Boolean.FALSE;
		 }

	 }
	 public void check(){
		 System.out.println(" check---ruc " + this.nroRuc);
		 System.out.println("check-----rsocial " + this.empresa.getRazonSocial());
		 this.nroRuc = null;
	 }
	 
	 public void recuperarUltimRuc()
	 {
		 System.out.println("ruc anterior " +this.nroRuc);
		 
		 System.out.println("Valor boletoRuc: "+this.boletoRuc);
		 if(this.boletoRuc)
		 {
			 HistorialPasajero historialPasajero = new HistorialPasajero();
			 if(this.persona != null){
				 
			
			 if(this.persona.getDni()!=null && !this.persona.getDni().equals("")){
				 System.out.println("DNI " +this.persona.getDni());
			 try {
				//lista = this.trackBoletoService.findByPersona(this.asientoVenta.getDocumentoPersona());
				 historialPasajero = this.historialPasajeroService.findByNrocDNI(this.persona.getDni());
				if(historialPasajero != null){
					if(historialPasajero.getNroRuc()!=null && !historialPasajero.getNroRuc().equals(""))
					{
						System.out.println("entro");
						this.setNroRuc(historialPasajero.getNroRuc());
						this.empresa.setRUC(historialPasajero.getNroRuc());
						this.buscarEmpresa();
					}
				}

			} catch (Exception e) {

				e.printStackTrace();
				}
			 }
			 }
		 }
	 }
	 
//		public void actualizaraAsociadosaProceso(AsientoVenta asientoVen){
//			try {
//				//variables
//				List<AsientoVenta> listaAsient = new ArrayList<>();
//				//obtenemos las programaciones asociadas
//				List<Programacion> listaProgram = this.programacionService.obtenerProgramacionesHijoxIdProgram(this.programacionSelec.getIdProgramacion());
//				//obtener los asiento venta asociado a esas programaciones todas sus sub rutas 
//				for(int i=0; i<listaProgram.size(); i++){
//					Programacion program = listaProgram.get(i);
//					AsientoVenta asiv = this.asientoVentaService.obtenerAsientoxProgramacionNumero(program.getIdProgramacion(), asientoVen.getNumero());
//					if(asiv != null){
//						asiv.setIdusuarioventa(asientoVen.getIdusuarioventa());
//						asiv.setFechaventa(null);
//						asiv.setEstado(ConstanteVentas.ESTADO_PROCESO);
//						asiv.setMonto(this.precioAsiento);
//						asiv.setSexo("-");
//						this.asientoVentaService.actualizarVentaAsiento(asiv);
//					}
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
		
		public void actualizarAnulacionAsociados(AsientoVenta asientoVen){
			try {
				//variables
				List<AsientoVenta> listaAsient = new ArrayList<>();
				//obtenemos las programaciones asociadas
				List<Programacion> listaProgram = this.programacionService.obtenerProgramacionesHijoxIdProgram(this.programacionSelec.getIdProgramacion());
				//obtener los asiento venta asociado a esas programaciones todas sus sub rutas 
				for(int i=0; i<listaProgram.size(); i++){
					Programacion program = listaProgram.get(i);
					AsientoVenta asiv = this.asientoVentaService.obtenerAsientoxProgramacionNumero(program.getIdProgramacion(), asientoVen.getNumero());
					if(asiv != null){
						asiv.setEstado("LIBRE");
						asiv.setSexo("-");
						this.asientoVentaService.liberarAsiento(asiv.getIdAsiento(), asiv.getIdProgramacion(), "LIBRE");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	 
//		public void actualizaraAsociadosaLibre(AsientoVenta asientoVent){
//			try {
//				//variables
//				List<AsientoVenta> listaAsient = new ArrayList<>();
//				//obtenemos las programaciones asociadas
//				List<Programacion> listaProgram = this.programacionService.obtenerProgramacionesHijoxIdProgram(this.programacionSelec.getIdProgramacion());
//				//obtener los asiento venta asociado a esas programaciones todas sus sub rutas 
//				for(int i=0; i<listaProgram.size(); i++){
//					Programacion program = listaProgram.get(i);
//					AsientoVenta asiv = this.asientoVentaService.obtenerAsientoxProgramacionNumero(program.getIdProgramacion(), asientoVent.getNumero());
//					if(asiv != null){
//						asiv.setEstado(ConstanteVentas.ESTADO_LIBRE);
//						asiv.setFechaventa(null);
//						asiv.setMonto(this.precioAsiento);
//						asiv.setTipoventa(null);
//						asiv.setSexo("-");
//						asiv.setDocumentoPersona(null);
//						asiv.setDocumentoEmpresa(null);
//						asiv.setIdusuarioventa(new Integer(0));
//						this.asientoVentaService.actualizarVentaAsiento(asiv);
//					}
//				}
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}

//		public void actualizaraAsociadosaLiberar(AsientoVenta FasientoVent){
//			try {
//				//variables
//				List<AsientoVenta> listaAsient = new ArrayList<>();
//				//obtenemos las programaciones asociadas
//				List<Programacion> listaProgram = this.programacionService.obtenerProgramacionesHijoxIdProgram(this.programacionSelec.getIdProgramacion());
//
//				//obtener los asiento venta asociado a esas programaciones todas sus sub rutas 
//
//				for(int i=0; i<listaProgram.size(); i++){
//					Programacion program = listaProgram.get(i);
//						AsientoVenta asiv = new AsientoVenta();
//						asiv.setIdProgramacion(program.getIdProgramacion());
//						asiv.setIdusuarioventa(asientoVent.getIdusuarioventa());
//						this.asientoVentaService.actualizarAsientoxProgramacionLiberar(asiv);
//				}
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}

		public void imprimirManifiestoPDF(){
			
			try {
			
				//generarManifiesto(this.programacionSelec);

				System.out.println("Imprimir manifiesto");
				ServletContext servletContext = (ServletContext) (FacesContext.getCurrentInstance().getExternalContext().getContext());

				
				SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");		
				String fecha = formato.format(new Date());
				
				Integer total = this.listaManifiesto.size();
//				
				String p_logo = ExportarArchivo.getPath("/resources/img/LogoIttsaBus.png");
				
				Map<String, Object> input =new  HashMap<String,Object>();
				input.put("P_LOGO", p_logo);
				input.put("P_FECHA", fecha);
				input.put("P_TOTAL", total.toString());
				
				if(this.tripulacion_venta !=null){
				input.put("P_BUS_NRO", this.tripulacion_venta.getNumeroBus());
				input.put("P_BUS_PLACA", this.tripulacion_venta.getPlacabus());
				input.put("P_BUS_MARCA", this.tripulacion_venta.getMarcaBus());
				input.put("P_PILOTO", this.tripulacion_venta.getPiloto());
				input.put("P_PILOTO_BREV", this.tripulacion_venta.getLicPiloto());
				input.put("P_COPILOTO", this.tripulacion_venta.getCopiloto());
				input.put("P_COPILOTO_BREV", this.tripulacion_venta.getLicCopiloto());
				input.put("P_TERRAMOZA1", this.tripulacion_venta.getTerramoza1());
				input.put("P_TERRAMOZA1_DNI", this.tripulacion_venta.getDniterramoza1());
				input.put("P_TERRAMOZA2", this.tripulacion_venta.getTerramoza2());
				input.put("P_TERRAMOZA2_DNI", this.tripulacion_venta.getDniterramoza2());
				input.put("P_FSALIDA", this.programacionSelec.getfSalida());
				input.put("P_ORIGEN", this.agenciaOrigen.getDescripcion());
				input.put("P_DESTINO", this.agenciaDestino.getDescripcion());
				}
				input.put(JRParameter.REPORT_LOCALE, new Locale("es"));
//				
				String path = ExportarArchivo.getPath("/resources/reports/jxrml/rptManPasajeros.jasper");
				HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
				
				
				byte[] bytes;
				//bytes = ExportarArchivo.exportPdfAndSendDefaultPrint(path, input, this.listaManifiesto); //Imprime PDF
				//ExportarArchivo.executePdfAndOpen(bytes, "ManifiestoPasajeros.pdf"); //Imprime PDF
				
				bytes = ExportarArchivo.exportPdf(path, input, this.listaManifiesto); //Descarga PDF
				ExportarArchivo.executePdf(bytes, "ManifiestoPasajeros.pdf"); //Descarga PDF
				
				FacesContext.getCurrentInstance().responseComplete();
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
		
		
		public void buscarBoletoaAnular(){
			try {
				this.trackingBoletoAnul = new TrackingBoleto();
				System.out.println("SERIE Y BOLETO " + this.serieBoletoAnul +" - "+ this.numeroBoletoAnul);
				this.trackingBoletoAnul = this.trackBoletoService.searchBySerieNumeroBoleto(this.serieBoletoAnul, this.numeroBoletoAnul);
				
				if(this.trackingBoletoAnul != null){
					this.trackingBoletoAnul.setAsientoVenta( this.asientoVentaService.findById(this.trackingBoletoAnul.getIdasientoventa()));
					this.trackingBoletoAnul.setPersona(this.personaService.findByNroDocumento(this.trackingBoletoAnul.getNrodocumento()));
					this.trackingBoletoAnul.setProgramacion(this.programacionService.findById(this.trackingBoletoAnul.getIdprogramacion()));
					this.trackingBoletoAnul.setServicio(this.servicioService.findById(this.trackingBoletoAnul.getIdservicio()));
					this.trackingBoletoAnul.setAgenciaOrigen(this.agenciaService.findById(this.trackingBoletoAnul.getServicio().getOrigen()));
					this.trackingBoletoAnul.setAgenciaDestino(this.agenciaService.findById(this.trackingBoletoAnul.getServicio().getDestino()));
					this.trackingBoletoAnul.setDiastranscurridos(Utiles.fechasDiferenciaEnDias(this.trackingBoletoAnul.getAsientoVenta().getFechaventa(), new Date()));
					if (this.trackingBoletoAnul.getDiastranscurridos()>30){
						this.trackingBoletoAnul.setFlgdias(true);
					}else{
						this.trackingBoletoAnul.setFlgdias(false);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		public void anulacionBoleto(){
			try {
				RequestContext context = RequestContext.getCurrentInstance();
				
				AsientoVenta asiento = this.trackingBoletoAnul.getAsientoVenta();
				asiento.setEstado("LIBRE");
				asiento.setSexo("-");
				
				List<TrackingBoleto> tmpreserva = this.trackBoletoService.buscarReservado(this.asientoVenta.getNumero(),ConstanteVentas.ESTADO_RESERVA, this.asientoVenta.getDocumentoPersona(),this.programacionSelec.getIdProgramacion(), this.programacionSelec.getIdServicio());
				boolean grabarSec = false;
				Secuencia sec= this.secuenciaServices.findbyDescripcion(Constante.SECUENCIA_TRACKING_BOLETO);
				Integer secuenciaInt = null;
				
				if(!tmpreserva.isEmpty()){
					secuenciaInt=tmpreserva.get(0).getIntsecuencia();
				}else{
					secuenciaInt= Integer.valueOf(sec.getSecuencia())+1;
					grabarSec=true;
				}
					
					//this.puntoVenta = this.puntoVentaService.findById(usuarioLogin.getIdpuntoventa());
					this.trackingCodigo = this.trackBoletoService.searchByAsientoVenta(asiento.getIdasientoventa(),asiento.getIdAsiento());
					if(this.trackingCodigo != null){
						this._Serie = this.trackingCodigo.getSerieBoleto(); 
						this._Numero =	(this.trackingCodigo.getNumeroBoleto()) + ""; 
					}
				this.asientoVentaService.liberarAsiento(asiento.getIdAsiento(), asiento.getIdProgramacion(), "LIBRE");
				seleccionar();
				this.trackingBoletoAnul = null;
								
				if (this._rutaCompartida.equals("SI")) {
					actualizarAnulacionAsociados(asiento);
				}else if(this._intCorreEnlace>0){
					actualizarAnulacionAsociados(asiento);
				}
				
				TrackingBoleto track = new TrackingBoleto();
				
				track.setSerieBoleto(this._Serie);
				track.setNumeroBoleto(this._Numero);
				track.setFregistro(new Date());
				track.setIdusuario(this.login.getUsuario().getIdusuario());
				track.setIdasiento(asiento.getIdAsiento());
				track.setIdasientoventa(asiento.getIdasientoventa());
				track.setEstado(ConstanteVentas.ESTADO_BOLETO_ANULADO);
				track.setIdprogramacion(asiento.getIdProgramacion());
				track.setIdservicio((Integer)this.servicioService.serviciobyIdProgramacion(asiento.getIdProgramacion()).getIdServicio());
				track.setNrodocumento(this.trackingCodigo.getNrodocumento());
				track.setPrepagado(Boolean.FALSE);
				track.setIntsecuencia(secuenciaInt);
				if (this.trackingCodigo.getNrodocempresa()!=null){
				track.setNrodocempresa(asiento.getEmpresa().getRUC());
				}
				this.trackBoletoService.creartrack(track);

				context.execute("dlgAnularBoleto.hide();");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		// Seteando el boleto  a reimprimir
		/*
		public void setReprintBoleto(TrackingBoleto trac){
			this.boletoReprint = trac;
			try {
				// obteniendo el punto de venta del usuario
				this.boletoReprint.setPuntoVenta(this.puntoVentaService.findById(this.usuarioLogin.getIdpuntoventa()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		*/
		
		public void setReprintBoleto(HistorialPasajero trac){
			try {
				// obteniendo el punto de venta del usuario
				trac.setPuntoVenta(this.puntoVentaService.findById(this.usuarioLogin.getIdpuntoventa()));
				this.rePrintBoleto = trac;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		
		
		public void actualizarTipoDocumento(AsientoVenta asientoVen){
					
					
					for (int i = 0; i < this.listaAsientosxVender.size(); i++) {
						
						AsientoVenta asien = this.listaAsientosxVender.get(i);
						Persona p = asien.getPersona();
						
						System.out.println("la persona tiene un tipo de documento de " + p.getTipodocumento()); 
						
						
						System.out.println("el objeto asien es "+ asien.getNumero() );
						System.out.println("el asiento que se quiere vender es "+ asientoVen.getNumero() );
						System.out.println("el tipo de documento del asiento es " + asientoVen.getNumero()+" es " +asientoVen.getPersona().getTipodocumento()) ;
						System.out.println("             ") ;
						
						
						
						if(asien.getNumero() == asientoVen.getNumero()){
						
						
								p.setTipodocumento(asientoVen.getPersona().getTipodocumento());
								if(p.getTipodocumento().equals("DNI")){
									asientoVen.getPersona().setTipodocumento("DNI");
									p.setTipodocumento("DNI"); 
								}else{
									if(p.getTipodocumento().equals("PAS")){
										asientoVen.getPersona().setTipodocumento("PAS");
										p.setTipodocumento("PAS"); 
										
									}else{
										asientoVen.getPersona().setTipodocumento("CE");
										p.setTipodocumento("CE"); 
									}
										
								}
							
			
						
							p.setTipodocumento(asientoVen.getPersona().getTipodocumento());
							asientoVen.setDocumentoPersona("");
						//	Persona p = this.personaService.findByNroDocumento(asientoVen.getDocumentoPersona());
						   
							asien.setDocumentoPersona(""); 
							System.out.println("ahora la persona tiene tipo de documento de  " + p.getTipodocumento() );
							System.out.println("             ") ;
							
						}
						
					}
				}
		
		public void buscarRutasCompartidas() {
			
			try {
				this.listaProgramacionCompartidas = new ArrayList<>();
				this.listaProgramacionCompartidas = this.programacionService.obtenerProgramacionesHijoxIdProgramyDescripcion(this.programacionSelec.getIdProgramacion(), this.programacionSelec.getfSalida());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		 
		public void setearHistorialPasajeroSeleccionado(HistorialPasajero hst)
		{
			 System.out.println("setetando:"+hst.getDni());
			 this.historialPasajero = hst;
			 this.historialPasajero.setOperacion("ACT-POSTERGADO");
			 this.historialPasajero.setPuntoVentaId(this.login.getPv().getIdPuntoVenta());
			 this.activaPostergacion = Boolean.TRUE;
			 //this.personaEncontrada = Boolean.TRUE;
			 
			 //actualizando el historial de pasajero
			 try {
				this.historialPasajeroService.actualizarOperacionPosterga(this.historialPasajero);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 //setetando los valores para lanzar el boleto de reserva

		 }
		
		 /*
		 public void consultarAsientosDisponibles()
		 {
			 try {
				this.listaAsientosDisponibles = this.asientoVentaService.listarAsientosDisponibles(this.historialPasajero.getPiso(),this.programacionSelec.getIdProgramacion());
				
				diferencia = new BigDecimal(0.0);
				//hy una diferencia?
				if(this.historialPasajeroSelec.getMonto().compareTo(this.historialPasajero.getPiso().intValue()==1 ? this.programacionSelec.getServicio().getPrecio1() : this.programacionSelec.getServicio().getPrecio2()) != 0)
				{
					diferencia =  this.historialPasajeroSelec.getMonto().subtract(this.historialPasajero.getPiso().intValue()==1 ? this.programacionSelec.getServicio().getPrecio1() : this.programacionSelec.getServicio().getPrecio2());
					
					if(diferencia.compareTo(new BigDecimal(0.0)) < 0)
					{
						diferencia =  diferencia.multiply(new BigDecimal(-1));
					}
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		 */
		
		/*
		 public void activarPostergacion()
		 {
			 
			 System.out.println("activamdo boleto postergacion:");
			 System.out.println("historisl:"+this.historialPasajero.getPiso());
			 System.out.println("historisl:"+this.historialPasajero.getNroasiento());
			 Persona per = null;
			 try {
				per = this.personaService.findByNroDocumento(this.historialPasajero.getDni());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
			 Asiento as = new Asiento();
			 
			 
			 //this.mostrarAsiento(Asiento as);
			
			 //this.mostrarPanelVentas();
			 
			 
		 }
		*/
	/*	
	public void llenarDetalleHistorial(HistorialPasajero hstd)
	{
		System.out.println("llenando:"+hstd.getDni());
		try {
			this.listaHistorialPasajeroDetalle = this.historialPasajeroService.getDetalleHistorialPasajero(hstd.getIdHistorialPasajero());
			
			for(HistorialPasajero  hst : this.listaHistorialPasajeroDetalle)
			{
				hst.setServicio(this.servicioService.findById(hst.getIdservicio()));
				hst.setProgramacion(this.programacionService.findById(hst.getIdprogramacion()));
				hst.setAsientoVenta(this.asientoVentaService.findById(hst.getAsientoventaid()));
				hst.setAgenciaOrigen(this.agenciaService.findById(hst.getServicio().getOrigen()));
				hst.setAgenciaDestino(this.agenciaService.findById(hst.getServicio().getDestino()));
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	*/
	public String generarTransbordo(Programacion prog)
	{
		System.out.println("Inicio - generarTransbordo");
		String outcome = null;
		System.out.println("programacion seleccionada: "+prog.getIdProgramacion());
		int index = this.listaProgramacion.indexOf(prog);
		
		List<Programacion> listaProg = new ArrayList<>();
		
		for(int i=index+1;i<this.listaProgramacion.size();i++)
		{
			listaProg.add(this.listaProgramacion.get(i));
		}
		
		
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.put("programacion", prog);
		flash.put("listaProgramacion", listaProg);
		outcome = "pretty:transbordoBus";
		
		System.out.println("Fin - generarTransbordo");

		return outcome;
	}
		
	
	/*
	public void stringDeliveryTodate()
	{
		System.out.println("la feha es :"+fechaStringEntregaDelivery);
		
		
		String fecha = Utils.dateAString(new Date()); //dd-MM-yyyy
		
		if(fechaStringEntregaDelivery.equals("9-1"))
		{
			fecha = fecha +" "+"10:00:00";//una proximado d elas 10
		}else
		{
			fecha = fecha +" "+"15:00:00";//una proximado d elas 3
		}
		
		this.fechaEntregaDelivery = Utils.stringToDateTime(fecha);//dd-MM-yyyy HH:mm:ss

	}
	*/

	
	

	
	
	
	/*
	public void inicarCambioAsiento()
	{
		try{
			Servicio serv = this.servicioService.findById(this.programacionSelec.getIdServicio());
			
			ClaseServicio clase = this.claseServicioServices.findById(serv.getIdClase());
			
			this.listaAsientosPisoUnoTarget = this.asientoService.findAsientoByClase(clase.getIdclase(), new Integer(1));
			AsientoVenta asv = null;
	
			for (Asiento as : this.listaAsientosPisoUnoTarget) 
			{
				asv = this.asientoVentaService.findAsientoByAsientoAndProgramacion(as.getIdAsiento(), as.getNumero(), this.programacionSelec.getIdProgramacion());
				as.setAsientoVenta(asv);
			}
	
			// generando conf inicial para el piso DOS ida
			if(clase.getNroPisos().compareTo(new Integer(2)) == 0) 
			{
				
				this.listaAsientosPisoDosTarget = this.asientoService.findAsientoByClase(clase.getIdclase(), new Integer(2));
				for (Asiento as : this.listaAsientosPisoDosTarget) 
				{
					asv = this.asientoVentaService.findAsientoByAsientoAndProgramacion(as.getIdAsiento(), as.getNumero(), this.programacionSelec.getIdProgramacion());
					as.setAsientoVenta(asv);
	
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	*/
	
	public void listarAsientosDisponibles()
	{
		try {
			
			this.listaAsientosVentaLibres.clear();
			
			if(this.pisoCambio != null)
			{
				if(this.pisoCambio.intValue() == 1)
				{
					for(Asiento as : this.listaAsientosPisoUnoTarget)
					{

						if(as.getAsientoVenta() != null && as.getAsientoVenta().getEstado().equals("LIBRE"))
						{
							this.listaAsientosVentaLibres.add(as.getAsientoVenta());
						}

					}
				}else{
					for(Asiento as : this.listaAsientosPisoDosTarget)
					{

						if(as.getAsientoVenta() != null && as.getAsientoVenta().getEstado().equals("LIBRE"))
						{
							this.listaAsientosVentaLibres.add(as.getAsientoVenta());
						}
						
					}
				}
			}
			
			
			
			System.out.println("haciendo :"+this.listaAsientosVentaLibres.size());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	public void hacerCambioAsiento()
	{
		try{
			
			List<TrackingBoleto> tmpreserva = this.trackBoletoService.buscarReservado(this.asientoVenta.getNumero(),ConstanteVentas.ESTADO_RESERVA, this.asientoVenta.getDocumentoPersona(),this.programacionSelec.getIdProgramacion(), this.programacionSelec.getIdServicio());
			boolean grabarSec = false;
			Integer secuencia;
			Secuencia sec= this.secuenciaServices.findbyDescripcion(Constante.SECUENCIA_TRACKING_BOLETO);
			if(!tmpreserva.isEmpty()){
				secuencia = tmpreserva.get(0).getIntsecuencia();
			}else{
				secuencia = Integer.valueOf(sec.getSecuencia())+1;
				grabarSec=true;
			}
			//creamos un track
			TrackingBoleto trackingBoleto = new TrackingBoleto();
			
			trackingBoleto.setFregistro(new Date());
			trackingBoleto.setIdusuario(this.login.getIdUsuario());
			trackingBoleto.setEstado(ConstanteVentas.ESTADO_ASIENTO_CAMBIADO);
			trackingBoleto.setIdprogramacion(this.asientoVenta.getIdProgramacion());
			trackingBoleto.setIdservicio(this.programacionSelec.getIdServicio());
			trackingBoleto.setNrodocumento(this.asientoVenta.getDocumentoPersona());
			trackingBoleto.setNrodocempresa(this.asientoVenta.getDocumentoEmpresa());
			trackingBoleto.setIntsecuencia(secuencia);
			trackingBoleto.setPrepagado(Boolean.FALSE);
			trackingBoleto.setMonto(this.asientoVenta.getMonto());
			trackingBoleto.setMontoReal(this.asientoVenta.getMontoReal());
			//trackingBoleto.setTipoInh(this.inhabilitacion.getTipo());
			//trackingBoleto.setMotivoInh(this.inhabilitacion.getMotivo().trim());
			trackingBoleto.setSerieRelacionado(null);
			trackingBoleto.setNumeroRelacionado(null);
			
			trackingBoleto.setSerieBoleto(this._Serie);
			trackingBoleto.setNumeroBoleto(this._Numero);
			
			
			
			//cogemos el nuevo asiento
			for(AsientoVenta asv : this.listaAsientosVentaLibres)
			{
				if(asv.getNumero().equals(this.asientoVentaCambiado.getNumero()))
				{
					asv.setEstado(this.asientoVenta.getEstado());
					asv.setTipoventa(this.asientoVenta.getTipoventa());
					asv.setDocumentoPersona(this.asientoVenta.getDocumentoPersona());
					asv.setDocumentoEmpresa(this.asientoVenta.getDocumentoEmpresa());
					asv.setSexo(this.asientoVenta.getSexo());
					asv.setIdusuarioventa(this.login.getUsuario().getIdusuario());
					asv.setHorareserva(this.asientoVenta.getHorareserva());
					asv.setVentaOrigen(this.asientoVenta.getVentaOrigen());
					asv.setMontoReal(this.asientoVenta.getMontoReal());
					asv.setExterno(this.asientoVenta.getExterno());
					asv.setOficinaId(this.login.getPv().getIdOficina());
					asv.setPromocional(this.asientoVenta.getPromocional());
					this.asientoVentaService.actualizarVentaAsiento(asv);
					trackingBoleto.setIdasiento(asv.getIdAsiento());
					trackingBoleto.setIdasientoventa(asv.getIdasientoventa());
					this.trackBoletoService.creartrack(trackingBoleto);
					//this.asientoSelec.setNumero(asv.getNumero());
					break;
				}
			}
			
			//liberando asiento anterior
			this.asientoVentaService.liberarAsiento(this.asientoVenta.getIdAsiento(), this.programacionSelec.getIdProgramacion(), "LIBRE");
			
			this.asientoVenta.setEstado("LIBRE");
			this.asientoVenta.setSexo("-");
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}

	}
	*/
	public void cancelarVentaDelivery()
	{
		this.oficinaVenta = Boolean.TRUE;
		this.deliveryVenta= Boolean.FALSE; 
	}
	
	public void cancelaPrepagado()
	{
		System.out.println("cancelando preagado");
		this.boletoPrepagado = Boolean.FALSE;
		this.pagador = new PagadorPrepagado();
	}
	
	public void aceptarPrepagado()
	{
		//serie y nro de boleto en blanco
		this._Numero = null;
		this._Serie = null;
	}
	
    /*
	public void buscarTrackingBoleto() 
	{
            try {
                this.listaTrackingBoleto = this.trackBoletoService.findBySerieAndNumero(this.busqBoletoSerie, this.busqBoletoNumero);
            } catch (Exception ex) {
                Logger.getLogger(VentaDirectaMB.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
     */
        
     public void inicioMovimientos() 
     {
            this.serieBoletoMov = "";
            this.numeroBoletoMov = "";
            this.listaTracking = null;
            this.listaFiltroTracking = null;
            this.consultaRapida = Boolean.TRUE;
     }

     public void recuperaAsiento(HistorialPasajero hst)
     {
    	 System.out.println("**** recuperando asiento");
    	 
    	 try {
		    	 this.programacionSelec = this.programacionService.findById(hst.getIdprogramacion());
		    	 Servicio serv = this.servicioService.findById(this.programacionSelec.getIdServicio());
		    	 this.programacionSelec.setServicio(serv);
		    	 
		    	 this._intCorreEnlace = this.programacionSelec.getIntCorreEnlace();

		    	 this.agenciaOrigen = this.agenciaService.findById(this.programacionSelec.getOrigen());
		    	 this.agenciaDestino = this.agenciaService.findById(this.programacionSelec.getDestino());
		    	 
		    	 
		    	 this.asientoVenta = this.asientoVentaService.findById(hst.getAsientoventaid());
		    	 
		    	 //lo  siento nececito esta consulta
		    	 this.asientoVenta = this.asientoVentaService.findEstadoAsientoByAsientoAndProgramacion(this.asientoVenta);
		    	 this.asientoVenta.setPiso(this.asientoSelec.getPiso());
		    	 this.asientoSelec = this.asientoService.findById(this.asientoVenta.getIdAsiento());
		    	 this.asientoSelec.setAsientoVenta(this.asientoVenta);

		    	 this._Serie = hst.getSerieBoleto();
		    	 this._Numero = hst.getNumeroBoleto().toString();
		    	 
		    	 List<Programacion> lstPrg = this.programacionService.findByOrigenDestinoAndFsalida(this.programacionSelec.getOrigen(), this.programacionSelec.getDestino(), this.programacionSelec.getfSalidaString());
		    	 
		    	 for(Programacion prg : lstPrg)
		    	 {
		    		 if(prg.getIdProgramacion().compareTo(this.programacionSelec.getIdProgramacion()) == 0)
		    		 {
		    			 this.programacionSelec.setDesRutaCompartida(prg.getDesRutaCompartida());
		    			 break;
		    		 }
		    	 }
		    	 
		    	 this._rutaCompartida = this.programacionSelec.getDesRutaCompartida();

		    	 
		    	 this.persona = this.personaService.findByNroDocumento(hst.getDni());
		    	 
		    	 if (this.asientoVenta.getDocumentoEmpresa() != null) 
				 {
		    		 this.empresa = this.empresaService.findByNroRUC(hst.getNroRuc());
				 }
		    	 
		    	 
		    	 if(this.asientoVenta.getPromocional())
		    	 {
		    		 //recuperar usuario autorixante
		    		 this.usuarioAutorizante = this.usuarioAutorizanteServices.findById(this.asientoVenta.getIdusuarioautorizante());
		    	 }
		    	 
		    	 //si es prepagado hay que recuperara al  pagador
		    	 if(this.asientoVenta.getPrepagado() != null && this.asientoVenta.getNrodocprepagado() != null && this.asientoVenta.getPrepagado())
		    	 {
		    		 Persona per = this.personaService.findByNroDocumento(this.asientoVenta.getNrodocprepagado());
		    		 
		    		 this.pagador.setDireccion(per.getDireccion());
		    		 this.pagador.setEmail(per.getEmail());
		    		 this.pagador.setNombre(per.getNombres());
		    		 this.pagador.setNroDoc(per.getDni());
		    		 //this.pagador.setReferencia(p);
		    		 this.pagador.setTelefono(per.getTelefono());
		    		 this.pagador.setTipoDoc(per.getTipodocumento());
		    	 }

			} catch (Exception e) {
				e.printStackTrace();
			}
    	 
     }
     
     
     
     public void recuperaAsientoTracking(TrackingBoleto trk)
     {
    	 System.out.println("**** recuperando asiento");
    	 
    	 try {
		    	 this.programacionSelec = this.programacionService.findById(trk.getIdprogramacion());
		    	 Servicio serv = this.servicioService.findById(this.programacionSelec.getIdServicio());
		    	 this.programacionSelec.setServicio(serv);
		    	 
		    	 this._intCorreEnlace = this.programacionSelec.getIntCorreEnlace();
		    	 
		    	 this.agenciaOrigen = this.agenciaService.findById(this.programacionSelec.getOrigen());
		    	 this.agenciaDestino = this.agenciaService.findById(this.programacionSelec.getDestino());
		    	 
		    	 this.asientoVenta = this.asientoVentaService.findById(trk.getIdasientoventa());
		    	 
		    	 //lo  siento nececito esta consulta
		    	 this.asientoVenta = this.asientoVentaService.findEstadoAsientoByAsientoAndProgramacion(this.asientoVenta);
		    	 
		    	 this.asientoVenta.setPiso(this.asientoSelec.getPiso());
		    	 
		    	 
		    	 this.asientoSelec = this.asientoService.findById(this.asientoVenta.getIdAsiento());
		    	 this.asientoSelec.setAsientoVenta(this.asientoVenta);
		    	 this._Serie = trk.getSerieBoleto();
		    	 this._Numero = trk.getNumeroBoleto().toString();
		    	 
		    	 List<Programacion> lstPrg = this.programacionService.findByOrigenDestinoAndFsalida(this.programacionSelec.getOrigen(), this.programacionSelec.getDestino(), this.programacionSelec.getfSalidaString());
		    	 
		    	 for(Programacion prg : lstPrg)
		    	 {
		    		 if(prg.getIdProgramacion().compareTo(this.programacionSelec.getIdProgramacion()) == 0)
		    		 {
		    			 this.programacionSelec.setDesRutaCompartida(prg.getDesRutaCompartida());
		    			 break;
		    		 }
		    	 }
		    	 
		    	 this._rutaCompartida = this.programacionSelec.getDesRutaCompartida();
		    	 
		    	 this.persona = this.personaService.findByNroDocumento(trk.getNrodocumento());
		    	 
		    	 if (this.asientoVenta.getDocumentoEmpresa() != null) 
				 {
		    		 this.empresa = this.empresaService.findByNroRUC(trk.getNrodocempresa());
				 }
		    	 
		    	 
		    	 if(this.asientoVenta.getPromocional())
		    	 {
		    		 //recuperar usuario autorixante
		    		 this.usuarioAutorizante = this.usuarioAutorizanteServices.findById(this.asientoVenta.getIdusuarioautorizante());
		    	 }
		    	 
		    	 //si es prepagado hay que recuperara al  pagador
		    	 if(this.asientoVenta.getPrepagado() != null && this.asientoVenta.getNrodocprepagado() != null && this.asientoVenta.getPrepagado())
		    	 {
		    		 Persona per = this.personaService.findByNroDocumento(this.asientoVenta.getNrodocprepagado());
		    		 
		    		 this.pagador.setDireccion(per.getDireccion());
		    		 this.pagador.setEmail(per.getEmail());
		    		 this.pagador.setNombre(per.getNombres());
		    		 this.pagador.setNroDoc(per.getDni());
		    		 //this.pagador.setReferencia(p);
		    		 this.pagador.setTelefono(per.getTelefono());
		    		 this.pagador.setTipoDoc(per.getTipodocumento());
		    	 }
		    	 
		    	
				
			} catch (Exception e) {
				e.printStackTrace();
			}
    	 
     }
        

     /*################# activar portergacion en el croquis #############################*/
     public void recuperarPostergacion()
     {
    	
    	RequestContext context = RequestContext.getCurrentInstance();
 		if (this.listaAsientosxVender.size() == 1) 
 		{

 			context.execute("wdlgRecuperarPostergado.show()");				

 		} else if (this.listaAsientosxVender.size() != 1) {

 			//dialog de no validez
			context.execute("dlgSeleccionNoValido.show()");
 			
 		}

    	 
     }
     
     
     public void buscarBoletoAPostergar()
  	{
    	 System.out.println("INICIO - buscarBoletoAPostergar");
  		//buscando el boleto reservado en el tracking de boleto:
  		this.trackingBoleto  = null;
  		this.diferenciaPostergacion = Boolean.FALSE;
  		
  		try {
  			System.out.println("Serie: " + this.boletoSerieBuscado+ " nro:"+this.boletoNumeroBuscado );
  			this.trackingBoleto = this.trackBoletoService.findBySerieAndNumero(this.boletoSerieBuscado, this.boletoNumeroBuscado); 
  			System.out.println("this.trackingBoleto :" + this.trackingBoleto.size() + " estado: " + this.trackingBoleto.get(0).getEstado());
  			if(this.trackingBoleto == null || this.trackingBoleto.isEmpty())
  			{
  				FacesUtils.showFacesMessage("No se encontró el boleto.", 1);
  				
  			}else if(this.trackingBoleto.get(0).getEstado().equals("VENTA")){//solo ventas 

  				this.persona = this.personaService.findByNroDocumento(this.trackingBoleto.get(0).getNrodocumento());
  				this.empresa = this.empresaService.findByNroRUC(this.trackingBoleto.get(0).getNrodocempresa());
  				if(this.persona != null)
  				{	this.personaEncontrada = Boolean.TRUE;
  				}else{
  					this.persona = new Persona();
  				}
  				if(this.empresa  != null)
  				{	this.empresaEncontrada = Boolean.TRUE;
  				}else{
  					this.empresa = new Empresa();
  				}
  				
  				
  				//calculo dela diferencia
  				//this.diferencia = new BigDecimal(0.0);
  				
  				BigDecimal montoActual = null;
  				if(this.asientoSelec.getPiso().intValue()==1) //monto piso 1
  				{
  					montoActual = this.programacionSelec.getServicio().getPrecio1();
  				}else{//monto piso 2
  					montoActual = this.programacionSelec.getServicio().getPrecio2();
  				}
  				
  				//si la diferencia es negativa
  				if(this.trackingBoleto.get(0).getMontoReal().compareTo(montoActual) < 0 )
  				{
  					//this.diferencia = this.trackingBoleto.get(0).getMontoReal().subtract(montoActual);
  					//this.diferencia = this.diferencia.multiply(new BigDecimal(-1.0));
  					FacesUtils.showFacesMessage("Existe una diferencia en el monto, esto es una inhabilitacion", 1);
  					this.diferenciaPostergacion = Boolean.TRUE;
  					
  				}else{
  					//this.diferencia = new BigDecimal(0.0);
  					//al asv es el anterior
  					this.asientoVenta = this.asientoVentaService.findById(this.trackingBoleto.get(0).getIdasientoventa());
  					
  				}
  				this.buscarResultado = Boolean.TRUE;  //Agregado por Evelyn para que muestre el resultado
  				
  			}else{
  				
  				FacesUtils.showFacesMessage("No se encontró el boleto.", 1);
  				
  			}
  		} catch (Exception e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
  		
  		System.out.println("FIN - buscarBoletoAPostergar");
  	}
     
     public void realizarPostergacion()
 	{

 		Date fechaActual = this.programacionSelec.getfSalida();
 		AsientoVenta asv = this.listaAsientosxVender.get(0);
 		TrackingBoleto trackingBoleto = null;

 		try {
 			
 			//recuperando el historial y actualizando a postergdo
 			/*
 			List<HistorialPasajero> historial = this.historialPasajeroService.getHistorialByDniAsientoVentaId(this.persona.getDni(), this.asientoVenta.getIdasientoventa());
 			historial.get(0).setOperacion("POSTERGADO");
 			historial.get(0).setPuntoVentaId(this.login.getPv().getIdPuntoVenta());
 			
 			//historial.setfRegistro(new Date());
 			this.historialPasajeroService.actualizarOperacionPosterga(historial.get(0));
 			*/
 			
 			//creando otro historial
 			/*
 			historial
 			historial.setTipoEntrega(this._tipoEntrega);
 			historial.setPrepagado(Boolean.FALSE);
 			

 			historial.setAsientoventaid(this.asientoVenta.getIdasientoventa());
			historial.setDni(this.persona.getDni());
			historial.setfRegistro(new Date());
			historial.setIdOficina(usuarioLogin.getIdoficina());
			historial.setIdprogramacion(this.programacionSelec.getIdProgramacion());
			historial.setIdservicio(this.programacionSelec.getIdServicio());
			historial.setSerieBoleto(this._Serie);
			historial.setOperacion(operacion);
			historial.setNumeroBoleto( this.boletoPrepagado ? null : Integer.parseInt(this._Numero));
			historial.setNroRuc(this.empresa.getRUC());
			historial.setPiso(this.asientoVenta.getPiso());
			historial.setNroasiento(Integer.parseInt(this.asientoVenta.getNumero()));
			historial.setIdpadre(this.historialPasajero.getIdHistorialPasajero());
			historial.setMonto(this.asientoVenta.getMonto());
			historial.setPuntoVentaId(this.login.getPv().getIdPuntoVenta());

			this.historialPasajeroService.crearHistorialPasajero(historial);
			*/
			

			/*
 			List<TrackingBoleto> tmpreserva = this.trackBoletoService.buscarReservado(this.asientoVenta.getNumero(),ConstanteVentas.ESTADO_RESERVA, this.asientoVenta.getDocumentoPersona(),this.programacionSelec.getIdProgramacion(), this.programacionSelec.getIdServicio());
			boolean grabarSec = false;
			Integer secuencia;
			Secuencia sec= this.secuenciaServices.findbyDescripcion(Constante.SECUENCIA_TRACKING_BOLETO);
			if(!tmpreserva.isEmpty()){
				secuencia = tmpreserva.get(0).getIntsecuencia();
			}else{
				secuencia = Integer.valueOf(sec.getSecuencia())+1;
				grabarSec=true;
			}
			*/
			
			//creando el trac de postergado
			//List<TrackingBoleto> listaTr = this.trackBoletoService.findBySerieNumeroBoleto(this.boletoSerieBuscado, this.boletoNumeroBuscado);
			
			trackingBoleto = this.trackingBoleto.get(0);
			//track.setSerieBoleto(this._Serie);
			//track.setNumeroBoleto(this._Numero);
			//track.setNumeroBoleto(this.asientoVenta.getNumero());
			trackingBoleto.setFregistro(new Date());
			//trackingBoleto.setIdusuario(this.login.getIdUsuario());
			//trackingBoleto.setIdasiento(asv.getIdAsiento());
			//trackingBoleto.setIdasientoventa(asv.getIdasientoventa());
			trackingBoleto.setEstado(ConstanteVentas.ESTADO_POSTERGADO);
			//trackingBoleto.setIdprogramacion(idprogramacion);
			//trackingBoleto.setIdservicio(idservicio);
			//trackingBoleto.setNrodocumento(nrodocumento);
			//trackingBoleto.setNrodocempresa(nrodocempresa);
			//trackingBoleto.setIntsecuencia(secuencia);
			trackingBoleto.setPrepagado(Boolean.FALSE);
			//trackingBoleto.setMonto(monto);
			//trackingBoleto.setMontoReal(montoReal);
			//track.setPuntoVentaId(this.login.getPv().getIdPuntoVenta());
			this.trackBoletoService.creartrack(trackingBoleto);
			
			
			//actualizando asientoVenta
			asv.setDocumentoPersona(this.asientoVenta.getDocumentoPersona());
			asv.setDocumentoEmpresa(this.asientoVenta.getDocumentoEmpresa());
			asv.setEstado(ConstanteVentas.ESTADO_VENTA);
			asv.setTipoventa(ConstanteVentas.VENTA_DIRECTA);
			asv.setFechaventa(this.asientoVenta.getFechaventa());
			asv.setSexo(this.asientoVenta.getSexo());
			asv.setIdusuarioventa(this.login.getUsuario().getIdusuario());
			asv.setExterno(this.asientoVenta.getExterno());
			asv.setOficinaId(this.asientoVenta.getOficinaId());
			asv.setPrepagado(this.asientoVenta.getPrepagado());
			asv.setTipdocprepagado(this.asientoVenta.getTipdocprepagado());
			asv.setPromocional(this.asientoVenta.getPromocional());
			asv.setVentaOrigen(this.asientoVenta.getVentaOrigen());
			asv.setMontoReal(this.asientoVenta.getMontoReal());
			//asv.setIdusuarioventa(idusuarioventa);
			//asv.setIdAsiento(idAsiento);
			//asv.setIdProgramacion(idProgramacion);
			//asv.setNumero(numero);
			this.asientoVentaService.actualizarVentaAsiento(asv);
			//liberand el anterior
			this.asientoVentaService.liberarAsiento(this.asientoVenta.getIdAsiento(),this.asientoVenta.getIdProgramacion(), "LIBRE");
			this.asientoVenta.setEstado("LIBRE");
			this.asientoVenta.setSexo("-");
			
			//crando el track de venta
			trackingBoleto = new  TrackingBoleto();
			trackingBoleto.setSerieBoleto(this.boletoSerieBuscado);
			trackingBoleto.setNumeroBoleto(this.boletoNumeroBuscado);
			trackingBoleto.setFregistro(new Date());
			trackingBoleto.setIdusuario(this.login.getIdUsuario());
			trackingBoleto.setIdasiento(asv.getIdAsiento());
			trackingBoleto.setIdasientoventa(asv.getIdasientoventa());
			trackingBoleto.setEstado(ConstanteVentas.ESTADO_VENTA);
			trackingBoleto.setIdprogramacion(asv.getIdProgramacion());
			trackingBoleto.setIdservicio(this.programacionSelec.getServicio().getIdServicio());
			trackingBoleto.setNrodocumento(this.asientoVenta.getDocumentoPersona());
			trackingBoleto.setNrodocempresa(this.asientoVenta.getDocumentoEmpresa());
			//trackingBoleto.setIntsecuencia(secuencia);
			trackingBoleto.setPrepagado(Boolean.FALSE);
			trackingBoleto.setMonto(asv.getMonto());
			trackingBoleto.setMontoReal(asv.getMontoReal());
			trackingBoleto.setPuntoVentaId(this.login.getPv().getIdPuntoVenta());
			this.trackBoletoService.creartrack(trackingBoleto);
			
			//refrescando
			this.seleccionar();
			
			//mandano a ipresion:
			//no hay ipresion e postergaion
 			
 		} catch (Exception e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
 		
 	}
     
  	public void cancelarRecuperarPostergacion()
  	{
  		this.diferenciaPostergacion = Boolean.FALSE;
  		//liberar asientos
  		this.liberarAsiento();	
  	}
     
     
     /*###########################      boleto inhabilitado          ####################################*/
     
     //activar inhabilitaion en el croquis
     public void recuperarInhabilitacion()
     {
    	 this.inhabilitacion = new Inhabilitacion(); 
    	 this.personaNueva = new Persona();
    	 this.empresa = new Empresa();
    	 this.boletoSerieBuscado = null;
    	 this.boletoNumeroBuscado = null;
    	 this.empresaEncontrada = Boolean.TRUE;
    	RequestContext context = RequestContext.getCurrentInstance();
 		if (this.listaAsientosxVender.size() == 1) 
 		{
 			context.execute("wdlgRecuperarInhabilitado.show()");				

 		} else if (this.listaAsientosxVender.size() != 1) {

 			//dialog de no validez
			context.execute("dlgSeleccionNoValido.show()");	
 		}

    	 
     }
     
     
    public void buscarBoletoInhabilitado()
 	{
 		//buscando el boleto reservado en el tracking de boleto:
 		this.trackingBoleto  = null;
 		this.consultaRapida = Boolean.FALSE;
 		this.buscarInhabilitado = Boolean.FALSE;
 		this.inhabilitacion= new Inhabilitacion();
 		try {
 			this.trackingBoleto = this.trackBoletoService.findBySerieNumeroBoleto(this.boletoSerieBuscado,this.boletoNumeroBuscado);
 			
 			if(this.trackingBoleto == null || this.trackingBoleto.isEmpty())
 			{
 				
 				FacesUtils.showFacesMessage("No se encontró el boleto.", 1);
 				
 			}else if(this.trackingBoleto.get(0).getEstado().equals("VENTA")){//solo se inhabilitan las ventas

 				this.persona = this.personaService.findByNroDocumento(this.trackingBoleto.get(0).getNrodocumento().trim());
 				this.empresa = this.empresaService.findByNroRUC(this.trackingBoleto.get(0).getNrodocempresa());
 				this.buscarInhabilitado = Boolean.TRUE;
 				this.servicioInhabilitado = this.servicioService.findById(this.trackingBoleto.get(0).getIdservicio());
 				
 				if(this.persona != null)
 				{
 					this.personaEncontrada = Boolean.TRUE;
 				}else{
 					this.persona = new Persona();
 				}
 				
 				//igual para empresa:
 				
 				if(this.empresa  != null)
 				{
 					this.empresaEncontrada = Boolean.TRUE;
 				}else{
 					this.empresa = new Empresa();
 				}
 				
 				
 				//calculo dela diferencia
 				this.diferencia = new BigDecimal(0.0);
 				
 				BigDecimal montoActual = null;
 				if(this.asientoSelec.getPiso().intValue()==1) //monto piso 1
 				{
 					montoActual = this.programacionSelec.getServicio().getPrecio1();
 				}else{//monto piso 2
 					montoActual = this.programacionSelec.getServicio().getPrecio2();
 				}
 				
 				//si la diferencia es negativa
 				if(this.trackingBoleto.get(0).getMontoReal().compareTo(montoActual) < 0 )
 				{
 					this.diferencia = this.trackingBoleto.get(0).getMontoReal().subtract(montoActual);
 					this.diferencia = this.diferencia.multiply(new BigDecimal(-1.0));
 				}else{
 					this.diferencia = new BigDecimal(0.0);
 				}

 				//el asientoVenta es el anterior
 				this.asientoVenta = this.asientoVentaService.findById(this.trackingBoleto.get(0).getIdasientoventa());
 				
 				this.buscarInhabilitado = Boolean.TRUE;
 				
 			}else{
  				
  				FacesUtils.showFacesMessage("No se encontró el boleto.", 1);
  				
  			}
 		} catch (Exception e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}

 	}
    
    public void calcularMontoBoletoInhabilitacion()
    {
    	this.valorBoletoInhabilitado = new BigDecimal(0.0);
    	this.inhabilitacionRecuperadaEnMes = Boolean.FALSE;
    	
    	//agregando un sol a la diferencia
		if(this.inhabilitacion.getTipo().equals("CSERVICIOPASAJ") || inhabilitacion.getTipo().equals("CPASAJERO"))
		{
			//para el futuro considerar el sol como parametro
			//Parametro para = this.parametroServices.findParametro_byNombre("COSTO_ADMINISTRATIVO");
			this.diferencia = this.diferencia.add(new BigDecimal(1.0));
		}			
		
		this.valorBoletoInhabilitado = this.diferencia;
		
		//verificando si esta dentro del mes(para la impresion de la diferenia o del monto nominal)
		if(fechaActual.after(Utiles.obtenerPrimerDiaDelMes()) && fechaActual.before(Utiles.obtenerUltimoDiaDelMes()))
		{
			this.inhabilitacionRecuperadaEnMes = Boolean.TRUE;
			this.valorBoletoInhabilitado = this.trackingBoleto.get(0).getMontoReal();
		}
    	
    }
    
 	
 	public void realizarInhabilitacion()
 	{
 		
 		this.recuperarInhabilitacion = Boolean.TRUE;
 		//Date fechaActual = this.programacionSelec.getfSalida();
 		AsientoVenta asv = this.listaAsientosxVender.get(0);
 		TrackingBoleto trackingBoleto = null;
 		String serieAux = null;
 		String numeroAux = null;
 		Integer idTrkAux = null;
 		try {
 			
 			//creamos el tracking de inhablitacion
 			
 			//actualizamos el asientoVenta actual
 			
 			//liberamos el asientoVenta anterior
 			
 			//creamos el tracking de venta
 			
 			
 			//y el historial?
 			
 			trackingBoleto = this.trackingBoleto.get(0);
 			
 			serieAux = trackingBoleto.getSerieBoleto();
 			numeroAux = trackingBoleto.getNumeroBoleto();
			//track.setSerieBoleto(this._Serie);
			//track.setNumeroBoleto(this._Numero);
			//track.setNumeroBoleto(this.asientoVenta.getNumero());
			trackingBoleto.setFregistro(new Date());
			//trackingBoleto.setIdusuario(this.login.getIdUsuario());
			//trackingBoleto.setIdasiento(asv.getIdAsiento());
			//trackingBoleto.setIdasientoventa(asv.getIdasientoventa());
			trackingBoleto.setEstado(ConstanteVentas.ESTADO_INHABILITADO);
			trackingBoleto.setTipoInh(this.inhabilitacion.getTipo());
			trackingBoleto.setMotivoInh(this.inhabilitacion.getMotivo().trim().toUpperCase());
			trackingBoleto.setSerieRelacionado(null);
			trackingBoleto.setNumeroRelacionado(null);
			
			if(this.inhabilitacion.getTipo().equals("CSERVICIOPASAJ"))
 			{
 				trackingBoleto.setDniNuevoPasajero(this.personaNueva.getDni());	
 			}
			
			//trackingBoleto.setSerieRelacionado(serieRelacionado);
			//trackingBoleto.setNumeroRelacionado(numeroRelacionado);
			//trackingBoleto.setIdprogramacion(idprogramacion);
			//trackingBoleto.setIdservicio(idservicio);
			//trackingBoleto.setNrodocumento(nrodocumento);
			//trackingBoleto.setNrodocempresa(nrodocempresa);
			//trackingBoleto.setIntsecuencia(secuencia);
			trackingBoleto.setPrepagado(Boolean.FALSE);
			//trackingBoleto.setMonto(monto);
			//trackingBoleto.setMontoReal(montoReal);
			//track.setPuntoVentaId(this.login.getPv().getIdPuntoVenta());
			this.trackBoletoService.creartrack(trackingBoleto);
			
			//obteniendo el id del track insertado
			List<TrackingBoleto> lstTrk = this.trackBoletoService.findBySerieNumeroBoleto(serieAux, numeroAux);
			//el ultimo insertado es el ultimo
			idTrkAux = lstTrk.get(lstTrk.size() - 1).getIdTrackingBoleto();
			
			//actualizando asientoVenta
			
			asv.setEstado(ConstanteVentas.ESTADO_VENTA);
			asv.setTipoventa(this.asientoVenta.getTipoventa());
			asv.setFechaventa(this.asientoVenta.getFechaventa());
			asv.setSexo(this.asientoVenta.getSexo());
			asv.setDocumentoPersona(this.asientoVenta.getDocumentoPersona());
			asv.setDocumentoEmpresa(this.asientoVenta.getDocumentoEmpresa());
			
			if(this.inhabilitacion.getTipo().equals("CSERVICIOPASAJ"))
 			{
				asv.setDocumentoPersona(this.personaNueva.getDni());
				asv.setSexo(this.personaNueva.getSexo());
 				
 			}else if (this.inhabilitacion.getTipo().equals("CSERVICIODATOS"))
 			{
 				
 				//actualizando en la BD
 				this.personaService.actualizarPersona(this.persona);
 				if(this.boletoRuc)
 				{
 					asv.setDocumentoEmpresa(this.empresa.getRUC());
 					if(this.empresaEncontrada)
 					{
 	 	 				this.empresaService.actualizarEmpresa(this.empresa);
 					}else{
 						this.empresaService.crearEmpresa(this.empresa);
 					}
 					
 					
 				}
 				
 				
 			}else if (this.inhabilitacion.getTipo().equals("CSERVICIO"))
 			{
 				
 			}

			asv.setIdusuarioventa(this.login.getUsuario().getIdusuario());
			asv.setExterno(this.asientoVenta.getExterno());
			asv.setOficinaId(this.asientoVenta.getOficinaId());
			asv.setPrepagado(this.asientoVenta.getPrepagado());
			asv.setTipdocprepagado(this.asientoVenta.getTipdocprepagado());
			asv.setPromocional(this.asientoVenta.getPromocional());
			asv.setVentaOrigen(this.asientoVenta.getVentaOrigen());
			asv.setMontoReal(this.valorBoletoInhabilitado);
			
			
			//asv.setIdusuarioventa(idusuarioventa);
			//asv.setIdAsiento(idAsiento);
			//asv.setIdProgramacion(idProgramacion);
			//asv.setNumero(numero);
			this.asientoVentaService.actualizarVentaAsiento(asv);
			//liberand el anterior
			this.asientoVentaService.liberarAsiento(this.asientoVenta.getIdAsiento(),this.asientoVenta.getIdProgramacion(), "LIBRE");
			this.asientoVenta.setEstado("LIBRE");
			this.asientoVenta.setSexo("-");
			
			//crando el track de venta
			trackingBoleto = new  TrackingBoleto();
			
			trackingBoleto.setFregistro(new Date());
			trackingBoleto.setIdusuario(this.login.getIdUsuario());
			trackingBoleto.setIdasiento(asv.getIdAsiento());
			trackingBoleto.setIdasientoventa(asv.getIdasientoventa());
			trackingBoleto.setEstado(ConstanteVentas.ESTADO_VENTA);
			trackingBoleto.setIdprogramacion(asv.getIdProgramacion());
			trackingBoleto.setIdservicio(this.programacionSelec.getServicio().getIdServicio());
			trackingBoleto.setNrodocumento(this.asientoVenta.getDocumentoPersona());
			trackingBoleto.setNrodocempresa(this.asientoVenta.getDocumentoEmpresa());
			//trackingBoleto.setIntsecuencia(secuencia);
			trackingBoleto.setPrepagado(Boolean.FALSE);
			trackingBoleto.setMonto(asv.getMonto());
			trackingBoleto.setMontoReal(this.diferencia);
			trackingBoleto.setPuntoVentaId(this.login.getPv().getIdPuntoVenta());
			
			
			// obteniendo las series de boleto
			this.puntoVenta = this.puntoVentaService.findById(usuarioLogin.getIdpuntoventa());
			if (this.puntoVenta != null) 
			{
				this._Serie = this.puntoVenta.getSerieBoleto();
				this._Numero = (this.puntoVenta.getUltimoBoleto() + 1) + "";
			}
			//actualizando el nro y serie del boleto (trigger)
			//...
			
			trackingBoleto.setSerieBoleto(this._Serie);
			trackingBoleto.setNumeroBoleto(this._Numero);
			
			trackingBoleto.setSerieRelacionado(serieAux);
			trackingBoleto.setNumeroRelacionado(numeroAux);
			
			
			this.trackBoletoService.creartrack(trackingBoleto);
			
			//actualizando el track anterior
			this.trackBoletoService.actualizarTrackingReferencia(this._Numero, this._Serie, idTrkAux);
			
			//actualizando el ultimo  numero 
			this.puntoVentaService.actualizarUltimoBoleto(usuarioLogin.getIdpuntoventa(), Integer.parseInt(this._Numero));
			
			
			//refrescando
			this.seleccionar();
			this.buscarInhabilitado = Boolean.FALSE;
			this.boletoRuc = Boolean.FALSE;
			//mandano  impresion directa
			if(this.inhabilitacionRecuperadaEnMes)//se manda el valor de la diferencia
			{
				this.mandarImpresion(this.listaAsientosxVender,Boolean.FALSE);
				
			}else{//se manda el valor total o nominal
				
				this.mandarImpresion(this.listaAsientosxVender,Boolean.TRUE);
			}
			
			this.listaAsientosxVender.clear();
 		} catch (Exception e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
 		
 	}
 	
 	
 	public void cancelarRecuperarInhabilitado()
 	{
 		this.buscarInhabilitado = Boolean.FALSE;
 		//liberar asientos
 		this.liberarAsiento();
 		
 	}
 	
 	/*####################    Recuperar postergacion con Fecha abierta en croquis      ##########################*/
     
    public void recuperarPostergacionFechaAbierta()
    {
    	this.diferenciaPostergacion = Boolean.FALSE;
    	this.buscarResultado = Boolean.FALSE;
    	RequestContext context = RequestContext.getCurrentInstance();
		if (this.listaAsientosxVender.size() == 1) 
		{
			this.boletoSerieBuscado = null;
			this.boletoNumeroBuscado = null;
			context.execute("wdlgRecuperarPostergadoFA.show()");				

		} else if (this.listaAsientosxVender.size() != 1) {

			//dialog de no validez
			context.execute("dlgSeleccionNoValido.show()");
			
		}
    } 
    
    public void buscarBoletoFechaAbierta()
 	{
    	//buscando el boleto reservado en el tracking de boleto:
 		this.trackingBoleto  = null;
 		this.diferenciaPostergacion = Boolean.FALSE;
 		
 		
 		try {
 			this.trackingBoleto = this.trackBoletoService.findBySerieAndNumero(this.boletoSerieBuscado, this.boletoNumeroBuscado);
 			if(this.trackingBoleto == null || this.trackingBoleto.isEmpty())
 			{
 				
 				FacesUtils.showFacesMessage("No se encontró el boleto.", 1);
 				
 			}else if(this.trackingBoleto.get(0).getEstado().equals("POSTERGADOFA")){//solo postergado con fecha abierta 
 				
 				//validando que no paso el año:
 				Integer nroDias =  Utiles.fechasDiferenciaEnDias(this.trackingBoleto.get(0).getFregistro(), new Date());
 				Integer nroDiasLimite = Integer.parseInt(this.parametroServices.findParametro_byNombre("DIAS_POSTERGACION_FA"));
 				
 				if(nroDias.intValue() <= nroDiasLimite) //esta en fecha
 				{
 					//DIAS_POSTERGACION_FA
 	 				this.persona = this.personaService.findByNroDocumento(this.trackingBoleto.get(0).getNrodocumento());
 	 				this.empresa = this.empresaService.findByNroRUC(this.trackingBoleto.get(0).getNrodocempresa());
 	 				
 	 				if(this.persona != null)
 	 				{
 	 					this.personaEncontrada = Boolean.TRUE;
 	 				}else{
 	 					this.persona = new Persona();
 	 				}
 	 				
 	 				//igual para empresa:
 	 				
 	 				if(this.empresa  != null)
 	 				{
 	 					this.empresaEncontrada = Boolean.TRUE;
 	 				}else{
 	 					this.empresa = new Empresa();
 	 				}
 	 				
 	 				
 	 				//calculo de la diferencia
 	 				//this.diferencia = new BigDecimal(0.0);
 	 				
 	 				BigDecimal montoActual = null;
 	 				if(this.asientoSelec.getPiso().intValue()==1) //monto piso 1
 	 				{
 	 					montoActual = this.programacionSelec.getServicio().getPrecio1();
 	 					System.out.println("Piso 1:" +  montoActual);
 	 				}else{//monto piso 2
 	 					montoActual = this.programacionSelec.getServicio().getPrecio2();
 	 					System.out.println("Piso 2:" +  montoActual);
 	 				}
 	 				System.out.println("this.trackingBoleto.get(0).getMontoReal() : " + this.trackingBoleto.get(0).getMontoReal());
 	 				//si la diferencia es negativa
 	 				if(this.trackingBoleto.get(0).getMontoReal().compareTo(montoActual) < 0 )
 	 				{
 	 					//this.diferencia = this.trackingBoleto.get(0).getMontoReal().subtract(montoActual);
 	 					//this.diferencia = this.diferencia.multiply(new BigDecimal(-1.0));
 	 					FacesUtils.showFacesMessage("Existe una diferencia en el monto, debe inhabilitar el asiento", 1);
 	 					this.diferenciaPostergacion = Boolean.TRUE;
 	 					
 	 				}else{
 	 					//this.diferencia = new BigDecimal(0.0);
 	 					//al asv es el anterior
 	 					this.asientoVenta = this.asientoVentaService.findAsientoVentaFAById(this.trackingBoleto.get(0).getIdasientoventa());
 	 				}
 					
 	 				this.buscarResultado = Boolean.TRUE;
 					
 				}else{//ya caduco
 					
 					FacesUtils.showFacesMessage("El boleto ya caduco.", 1);
 				}
 				

 				
 			}else{
 				
 				FacesUtils.showFacesMessage("No se encontró el boleto.", 1);
 				
 			}
 		} catch (Exception e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}

 	}
    
    
 	
 	public void realizarRecuperacionFechaAbierta()
 	{
 		
		AsientoVenta asv = this.listaAsientosxVender.get(0);
		TrackingBoleto trackingBoleto = null;

		try {
			
			//recuperando el historial y actualizando a postergdo
			/*
			List<HistorialPasajero> historial = this.historialPasajeroService.getHistorialByDniAsientoVentaId(this.persona.getDni(), this.asientoVenta.getIdasientoventa());
			historial.get(0).setOperacion("POSTERGADO");
			historial.get(0).setPuntoVentaId(this.login.getPv().getIdPuntoVenta());
			
			//historial.setfRegistro(new Date());
			this.historialPasajeroService.actualizarOperacionPosterga(historial.get(0));
			*/
			
			//creando otro historial
			/*
			historial
			historial.setTipoEntrega(this._tipoEntrega);
			historial.setPrepagado(Boolean.FALSE);
			

			historial.setAsientoventaid(this.asientoVenta.getIdasientoventa());
			historial.setDni(this.persona.getDni());
			historial.setfRegistro(new Date());
			historial.setIdOficina(usuarioLogin.getIdoficina());
			historial.setIdprogramacion(this.programacionSelec.getIdProgramacion());
			historial.setIdservicio(this.programacionSelec.getIdServicio());
			historial.setSerieBoleto(this._Serie);
			historial.setOperacion(operacion);
			historial.setNumeroBoleto( this.boletoPrepagado ? null : Integer.parseInt(this._Numero));
			historial.setNroRuc(this.empresa.getRUC());
			historial.setPiso(this.asientoVenta.getPiso());
			historial.setNroasiento(Integer.parseInt(this.asientoVenta.getNumero()));
			historial.setIdpadre(this.historialPasajero.getIdHistorialPasajero());
			historial.setMonto(this.asientoVenta.getMonto());
			historial.setPuntoVentaId(this.login.getPv().getIdPuntoVenta());

			this.historialPasajeroService.crearHistorialPasajero(historial);
			*/
			

			/*
			List<TrackingBoleto> tmpreserva = this.trackBoletoService.buscarReservado(this.asientoVenta.getNumero(),ConstanteVentas.ESTADO_RESERVA, this.asientoVenta.getDocumentoPersona(),this.programacionSelec.getIdProgramacion(), this.programacionSelec.getIdServicio());
			boolean grabarSec = false;
			Integer secuencia;
			Secuencia sec= this.secuenciaServices.findbyDescripcion(Constante.SECUENCIA_TRACKING_BOLETO);
			if(!tmpreserva.isEmpty()){
				secuencia = tmpreserva.get(0).getIntsecuencia();
			}else{
				secuencia = Integer.valueOf(sec.getSecuencia())+1;
				grabarSec=true;
			}
			*/

			
			//actualizando asientoVenta
			asv.setDocumentoPersona(this.asientoVenta.getDocumentoPersona());
			asv.setDocumentoEmpresa(this.asientoVenta.getDocumentoEmpresa());
			asv.setEstado(ConstanteVentas.ESTADO_VENTA);
			asv.setTipoventa(ConstanteVentas.VENTA_DIRECTA);
			asv.setSexo(this.asientoVenta.getSexo());
			asv.setIdusuarioventa(this.login.getUsuario().getIdusuario());
			
			asv.setFechaventa(this.asientoVenta.getFechaventa());
			asv.setExterno(this.asientoVenta.getExterno());
			asv.setOficinaId(this.asientoVenta.getOficinaId());
			asv.setPrepagado(this.asientoVenta.getPrepagado());
			asv.setTipdocprepagado(this.asientoVenta.getTipdocprepagado());
			asv.setPromocional(this.asientoVenta.getPromocional());
			asv.setVentaOrigen(this.asientoVenta.getVentaOrigen());
			asv.setMontoReal(this.asientoVenta.getMontoReal());
			//asv.setIdusuarioventa(idusuarioventa);
			//asv.setIdAsiento(idAsiento);
			//asv.setIdProgramacion(idProgramacion);
			//asv.setNumero(numero);
			this.asientoVentaService.actualizarVentaAsiento(asv);
			
			//creando el trac de venta
			
			trackingBoleto = this.trackingBoleto.get(0);
			//track.setSerieBoleto(this._Serie);
			//track.setNumeroBoleto(this._Numero);
			//track.setNumeroBoleto(this.asientoVenta.getNumero());
			trackingBoleto.setFregistro(new Date());
			trackingBoleto.setIdusuario(this.login.getIdUsuario());
			trackingBoleto.setIdasiento(asv.getIdAsiento());
			trackingBoleto.setIdasientoventa(asv.getIdasientoventa());
			trackingBoleto.setEstado(ConstanteVentas.ESTADO_VENTA);
			trackingBoleto.setIdprogramacion(asv.getIdProgramacion());
			trackingBoleto.setIdservicio(this.programacionSelec.getServicio().getIdServicio());
			//trackingBoleto.setNrodocumento(nrodocumento);
			//trackingBoleto.setNrodocempresa(nrodocempresa);
			//trackingBoleto.setIntsecuencia(secuencia);
			trackingBoleto.setPrepagado(Boolean.FALSE);
			//trackingBoleto.setMonto(monto);
			//trackingBoleto.setMontoReal(montoReal);
			trackingBoleto.setPuntoVentaId(this.login.getPv().getIdPuntoVenta());
			this.trackBoletoService.creartrack(trackingBoleto);

			
			this.buscarResultado = Boolean.FALSE;
			//refrescando
			this.seleccionar();
			
			//mandano a ipresion:
			//no hay ipresion e postergaion
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
 	}
 	
 	
 	public void cancelarRecuperarFechaAbierta()
 	{
 		
 		this.diferenciaPostergacion = Boolean.FALSE;
 		this.buscarResultado = Boolean.FALSE;
 		//liberar asientos
 		this.liberarAsiento();
 		
 	}
 	
 	

 	
 	
     
     
	/*
	 * ############################--------gettres and setters---------#####################################
	 */

    public List<TrackingBoleto> getListaTrackingBoleto() {
        return listaTrackingBoleto;
    }

    public void setListaTrackingBoleto(List<TrackingBoleto> listaTrackingBoleto) {
        this.listaTrackingBoleto = listaTrackingBoleto;
    }

    public List<TrackingBoleto> getListaFiltroTrackingBoleto() {
        return listaFiltroTrackingBoleto;
    }

    public void setListaFiltroTrackingBoleto(List<TrackingBoleto> listaFiltroTrackingBoleto) {
        this.listaFiltroTrackingBoleto = listaFiltroTrackingBoleto;
    }
		
	
		
	public TreeNode getRaiz() {
		return raiz;
	}

	public List<Programacion> getListaProgramacionCompartidas() {
		return listaProgramacionCompartidas;
	}

	public void setListaProgramacionCompartidas(
			List<Programacion> listaProgramacionCompartidas) {
		this.listaProgramacionCompartidas = listaProgramacionCompartidas;
	}

	public PagadorPrepagado getDelivery() {
		return delivery;
	}

	public void setDelivery(PagadorPrepagado delivery) {
		this.delivery = delivery;
	}

	public List<AsientoVenta> getListaAsientosxVender() {
		return listaAsientosxVender;
	}

	public void setListaAsientosxVender(List<AsientoVenta> listaAsientosxVender) {
		this.listaAsientosxVender = listaAsientosxVender;
	}

	public void setRaiz(TreeNode raiz) {
		this.raiz = raiz;
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

	public TreeNode getNodoFechaSeleccionado() {
		return nodoFechaSeleccionado;
	}

	public void setNodoFechaSeleccionado(TreeNode nodoFechaSeleccionado) {
		this.nodoFechaSeleccionado = nodoFechaSeleccionado;
	}

	public Agencia getAgenciaOrigen() {
		return agenciaOrigen;
	}

	public void setAgenciaOrigen(Agencia agenciaOrigen) {
		this.agenciaOrigen = agenciaOrigen;
	}

	public Agencia getAgenciaDestino() {
		return agenciaDestino;
	}

	public void setAgenciaDestino(Agencia agenciaDestino) {
		this.agenciaDestino = agenciaDestino;
	}

	public Integer getAnchoBusDos() {
		return anchoBusDos;
	}

	public void setAnchoBusDos(Integer anchoBusDos) {
		this.anchoBusDos = anchoBusDos;
	}

	public ClaseServicio getBusClase() {
		return busClase;
	}

	public void setBusClase(ClaseServicio busClase) {
		this.busClase = busClase;
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

	public Boolean getVisibleBus() {
		return visibleBus;
	}

	public void setVisibleBus(Boolean visibleBus) {
		this.visibleBus = visibleBus;
	}

	public Integer getNroFilas() {
		return nroFilas;
	}

	public void setNroFilas(Integer nroFilas) {
		this.nroFilas = nroFilas;
	}

	public Asiento getAsientoSelec() {
		return asientoSelec;
	}

	public void setAsientoSelec(Asiento asientoSelec) {
		this.asientoSelec = asientoSelec;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public AsientoVenta getAsientoVenta() {
		return asientoVenta;
	}

	public void setAsientoVenta(AsientoVenta asientoVenta) {
		this.asientoVenta = asientoVenta;
	}

	public List<Programacion> getListaProgramacion() {
		return listaProgramacion;
	}

	public void setListaProgramacion(List<Programacion> listaProgramacion) {
		this.listaProgramacion = listaProgramacion;
	}

	public Programacion getProgramacionSelec() {
		return programacionSelec;
	}

	public void setProgramacionSelec(Programacion programacionSelec) {
		this.programacionSelec = programacionSelec;
	}

	public Date getTemp() {
		return temp;
	}

	public void setTemp(Date temp) {
		this.temp = temp;
	}

	public String getFechaSelec() {
		return fechaSelec;
	}

	public void setFechaSelec(String fechaSelec) {
		this.fechaSelec = fechaSelec;
	}

	public String getFechaString() {
		return fechaString;
	}

	public void setFechaString(String fechaString) {
		this.fechaString = fechaString;
	}

	public TipoAsiento getTipoAsientop2() {
		return tipoAsientop2;
	}

	public void setTipoAsientop2(TipoAsiento tipoAsientop2) {
		this.tipoAsientop2 = tipoAsientop2;
	}

	public TipoAsiento getTipoAsientop1() {
		return tipoAsientop1;
	}

	public void setTipoAsientop1(TipoAsiento tipoAsientop1) {
		this.tipoAsientop1 = tipoAsientop1;
	}

	public BigDecimal getPrecioAsiento() {
		return precioAsiento;
	}

	public void setPrecioAsiento(BigDecimal precioAsiento) {
		this.precioAsiento = precioAsiento;
	}

	public Integer getDisponibles() {
		return disponibles;
	}

	public Boolean getPersonaEncontrada() {
		return personaEncontrada;
	}

	public void setPersonaEncontrada(Boolean personaEncontrada) {
		this.personaEncontrada = personaEncontrada;
	}

	public Boolean getEmpresaEncontrada() {
		return empresaEncontrada;
	}

	public void setEmpresaEncontrada(Boolean empresaEncontrada) {
		this.empresaEncontrada = empresaEncontrada;
	}

	public void setDisponibles(Integer disponibles) {
		this.disponibles = disponibles;
	}

	public Integer getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(Integer capacidad) {
		this.capacidad = capacidad;
	}


	public Boolean getBoletoRuc() {
		return boletoRuc;
	}

	public void setBoletoRuc(Boolean boletoRuc) {
//		this.empresa = new Empresa();
//		this.rucDigitado = Boolean.FALSE;
		this.boletoRuc = boletoRuc;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public String getTipoVenta() {
		return tipoVenta;
	}

	public void setTipoVenta(String tipoVenta) {
		this.tipoVenta = tipoVenta;
	}

	public List<Programacion> getListaFiltroProgramacion() {
		return listaFiltroProgramacion;
	}

	public void setListaFiltroProgramacion(
			List<Programacion> listaFiltroProgramacion) {
		this.listaFiltroProgramacion = listaFiltroProgramacion;
	}

	public Boolean getEstaLibre() {
		return estaLibre;
	}

	public void setEstaLibre(Boolean estaLibre) {
		this.estaLibre = estaLibre;
	}

	public Boolean getEditar() {
		return editar;
	}

	public void setEditar(Boolean editar) {
		this.editar = editar;
	}


	public Boolean getDeliveryVenta() {
		return deliveryVenta;
	}

	public void setDeliveryVenta(Boolean deliveryVenta) {
		this.deliveryVenta = deliveryVenta;
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

	public String get_rutaCompartida() {
		return _rutaCompartida;
	}

	public void set_rutaCompartida(String _rutaCompartida) {
		this._rutaCompartida = _rutaCompartida;
	}

	public Usuario getUsuarioLogin() {
		return usuarioLogin;
	}

	public void setUsuarioLogin(Usuario usuarioLogin) {
		this.usuarioLogin = usuarioLogin;
	}

	public String get_tipoPago() {
		return _tipoPago;
	}

	public void set_tipoPago(String _tipoPago) {
		this._tipoPago = _tipoPago;
	}

	public String get_tipoEntrega() {
		return _tipoEntrega;
	}

	public void set_tipoEntrega(String _tipoEntrega) {
		this._tipoEntrega = _tipoEntrega;
	}

	public String get_Serie() {
		return _Serie;
	}

	public void set_Serie(String _Serie) {
		this._Serie = _Serie;
	}

	public String get_Numero() {
		return _Numero;
	}

	public void set_Numero(String _Numero) {
		this._Numero = _Numero;
	}

	public LoginMB getLogin() {
		return login;
	}

	public void setLogin(LoginMB login) {
		this.login = login;
	}

	public Postergacion getPostergacion() {
		return postergacion;
	}

	public void setPostergacion(Postergacion postergacion) {
		this.postergacion = postergacion;
	}

	public Boolean getPostergar() {
		return postergar;
	}

	public void setPostergar(Boolean postergar) {
		this.postergar = postergar;
	}

	public Date getFechaCaducidad() {
		return fechaCaducidad;
	}

	public void setFechaCaducidad(Date fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}

	public Date getFechaActual() {
		return fechaActual;
	}

	public void setFechaActual(Date fechaActual) {
		this.fechaActual = fechaActual;
	}

	public List<Postergacion> getListaPostergacion() {
		return listaPostergacion;
	}

	public void setListaPostergacion(List<Postergacion> listaPostergacion) {
		this.listaPostergacion = listaPostergacion;
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

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	
	public Date getFechaCumpleAnios() {
		return fechaCumpleAnios;
	}

	public void setFechaCumpleAnios(Date fechaCumpleAnios) {
		this.fechaCumpleAnios = fechaCumpleAnios;
	}

	public Boolean getDniDigitado() {
		return dniDigitado;
	}

	public void setDniDigitado(Boolean dniDigitado) {
		this.dniDigitado = dniDigitado;
	}


	public Boolean getRucDigitado() {
		return rucDigitado;
	}

	public void setRucDigitado(Boolean rucDigitado) {
		this.rucDigitado = rucDigitado;
	}


	public String getObservacionesMultiple() {
		return observacionesMultiple;
	}

	public void setObservacionesMultiple(String observacionesMultiple) {
		this.observacionesMultiple = observacionesMultiple;
	}

	public String getNroAsientoAnular() {
		return nroAsientoAnular;
	}

	public void setNroAsientoAnular(String nroAsientoAnular) {
		this.nroAsientoAnular = nroAsientoAnular;
	}

	public List<Agencia> getListaAgenciasExternas() {
		return listaAgenciasExternas;
	}

	public void setListaAgenciasExternas(List<Agencia> listaAgenciasExternas) {
		this.listaAgenciasExternas = listaAgenciasExternas;
	}

	public Agencia getAgenciaExterna() {
		return agenciaExterna;
	}

	public void setAgenciaExterna(Agencia agenciaExterna) {
		this.agenciaExterna = agenciaExterna;
	}

	public List<Oficina> getListaOficinasExternas() {
		return listaOficinasExternas;
	}

	public void setListaOficinasExternas(List<Oficina> listaOficinasExternas) {
		this.listaOficinasExternas = listaOficinasExternas;
	}

	public Oficina getOficinaExterna() {
		return oficinaExterna;
	}

	public void setOficinaExterna(Oficina oficinaExterna) {
		this.oficinaExterna = oficinaExterna;
	}

	public Boolean getEsOficinaExterna() {
		return esOficinaExterna;
	}

	public void setEsOficinaExterna(Boolean esOficinaExterna) {
		this.esOficinaExterna = esOficinaExterna;
	}

	public Boolean getBoletoPrepagado() {
		return boletoPrepagado;
	}

	public void setBoletoPrepagado(Boolean boletoPrepagado) {
		this.boletoPrepagado = boletoPrepagado;
	}

	public PagadorPrepagado getPagador() {
		return pagador;
	}

	public void setPagador(PagadorPrepagado pagador) {
		this.pagador = pagador;
	}

	public Boolean getCopiaBoleto() {
		return copiaBoleto;
	}

	public void setCopiaBoleto(Boolean copiaBoleto) {
		this.copiaBoleto = copiaBoleto;
	}

	public BigDecimal getMontoPrepagado() {
		return montoPrepagado;
	}

	public void setMontoPrepagado(BigDecimal montoPrepagado) {
		this.montoPrepagado = montoPrepagado;
	}

	public Boolean getFechaAbierta() {
		return fechaAbierta;
	}

	public void setFechaAbierta(Boolean fechaAbierta) {
		this.fechaAbierta = fechaAbierta;
	}

	public Persona getPersonaNueva() {
		return personaNueva;
	}

	public void setPersonaNueva(Persona personaNueva) {
		this.personaNueva = personaNueva;
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

	public void setListaFiltroManifiesto(List<AsientoVenta> listaFiltroManifiesto) {
		this.listaFiltroManifiesto = listaFiltroManifiesto;
	}

	public BigDecimal getMontoDelivery() {
		return montoDelivery;
	}

	public void setMontoDelivery(BigDecimal montoDelivery) {
		this.montoDelivery = montoDelivery;
	}

	public String getTipoTarjeta() {
		return tipoTarjeta;
	}

	public void setTipoTarjeta(String tipoTarjeta) {
		this.tipoTarjeta = tipoTarjeta;
	}

	public Double getMontoEfectivo() {
		return montoEfectivo;
	}

	public void setMontoEfectivo(Double montoEfectivo) {
		this.montoEfectivo = montoEfectivo;
	}

	public Double getMontoTarjeta() {
		return montoTarjeta;
	}

	public void setMontoTarjeta(Double montoTarjeta) {
		this.montoTarjeta = montoTarjeta;
	}

	public List<TrackingBoleto> getListaTracking() {
		return listaTracking;
	}

	public void setListaTracking(List<TrackingBoleto> listaTracking) {
		this.listaTracking = listaTracking;
	}

	public TrackingBoleto getTracking() {
		return tracking;
	}

	public void setTracking(TrackingBoleto tracking) {
		this.tracking = tracking;
	}

	public List<TrackingBoleto> getListaFiltroTracking() {
		return listaFiltroTracking;
	}

	public void setListaFiltroTracking(List<TrackingBoleto> listaFiltroTracking) {
		this.listaFiltroTracking = listaFiltroTracking;
	}

	public String getSerieBoletoMov() {
		return serieBoletoMov;
	}

	public void setSerieBoletoMov(String serieBoletoMov) {
		this.serieBoletoMov = serieBoletoMov;
	}

	public String getNumeroBoletoMov() {
		return numeroBoletoMov;
	}

	public void setNumeroBoletoMov(String numeroBoletoMov) {
		this.numeroBoletoMov = numeroBoletoMov;
	}

	public Boolean getAlertaReserva() {
		return alertaReserva;
	}

	public void setAlertaReserva(Boolean alertaReserva) {
		this.alertaReserva = alertaReserva;
	}

	public Date getFechaLimiteReserva() {
		return fechaLimiteReserva;
	}

	public void setFechaLimiteReserva(Date fechaLimiteReserva) {
		this.fechaLimiteReserva = fechaLimiteReserva;
	}

	public Boolean getBoletoReserva() {
		return boletoReserva;
	}

	public void setBoletoReserva(Boolean boletoReserva) {
		this.boletoReserva = boletoReserva;
	}

	public Boolean getEstaListaNegra() {
		return estaListaNegra;
	}

	public void setEstaListaNegra(Boolean estaListaNegra) {
		this.estaListaNegra = estaListaNegra;
	}

	public Boolean getManifiesto() {
		return manifiesto;
	}

	public void setManifiesto(Boolean manifiesto) {
		this.manifiesto = manifiesto;
	}

	public Boolean getCambioServicio() {
		return cambioServicio;
	}

	public void setCambioServicio(Boolean cambioServicio) {
		this.cambioServicio = cambioServicio;
	}

	public AsientoPasajero getAsientoPasajero() {
		return asientoPasajero;
	}

	public void setAsientoPasajero(AsientoPasajero asientoPasajero) {
		this.asientoPasajero = asientoPasajero;
	}

	public Integer get_intCorreEnlace() {
		return _intCorreEnlace;
	}

	public void set_intCorreEnlace(Integer _intCorreEnlace) {
		this._intCorreEnlace = _intCorreEnlace;
	}

	public List<Persona> getListaPersonasPorApellido() {
		return listaPersonasPorApellido;
	}

	public void setListaPersonasPorApellido(List<Persona> listaPersonasPorApellido) {
		this.listaPersonasPorApellido = listaPersonasPorApellido;
	}

	public List<Persona> getListaFiltroPersonasPorApellido() {
		return listaFiltroPersonasPorApellido;
	}

	public void setListaFiltroPersonasPorApellido(
			List<Persona> listaFiltroPersonasPorApellido) {
		this.listaFiltroPersonasPorApellido = listaFiltroPersonasPorApellido;
	}

	public String getNumeroReferenciaTarjeta() {
		return numeroReferenciaTarjeta;
	}

	public void setNumeroReferenciaTarjeta(String numeroReferenciaTarjeta) {
		this.numeroReferenciaTarjeta = numeroReferenciaTarjeta;
	}

	public Boolean getTransBancariaVenta() {
		return transBancariaVenta;
	}

	public void setTransBancariaVenta(Boolean transBancariaVenta) {
		this.transBancariaVenta = transBancariaVenta;
	}

	public Boolean getOficinaVenta() {
		return oficinaVenta;
	}

	public void setOficinaVenta(Boolean oficinaVenta) {
		this.oficinaVenta = oficinaVenta;
	}

	public Boolean getReservaEnElDia() {
		return reservaEnElDia;
	}

	public void setReservaEnElDia(Boolean reservaEnElDia) {
		this.reservaEnElDia = reservaEnElDia;
	}

	public Integer getHoraMaximaReserva() {
		return horaMaximaReserva;
	}

	public void setHoraMaximaReserva(Integer horaMaximaReserva) {
		this.horaMaximaReserva = horaMaximaReserva;
	}

	public Date getFechaMaximaSalida() {
		return fechaMaximaSalida;
	}

	public void setFechaMaximaSalida(Date fechaMaximaSalida) {
		this.fechaMaximaSalida = fechaMaximaSalida;
	}

	public String getTipoVista() {
		return tipoVista;
	}

	public void setTipoVista(String tipoVista) {
		this.tipoVista = tipoVista;
	}

	public Boolean getDestinoSeleccionado() {
		return destinoSeleccionado;
	}

	public void setDestinoSeleccionado(Boolean destinoSeleccionado) {
		this.destinoSeleccionado = destinoSeleccionado;
	}

	public Boolean getEsPromocional() {
		return esPromocional;
	}

	public void setEsPromocional(Boolean esPromocional) {
		this.esPromocional = esPromocional;
	}

	public List<UsuarioAutorizante> getListaUsuarioAutorizante() {
		return listaUsuarioAutorizante;
	}

	public void setListaUsuarioAutorizante(
			List<UsuarioAutorizante> listaUsuarioAutorizante) {
		this.listaUsuarioAutorizante = listaUsuarioAutorizante;
	}

	public Promocion getPromocion() {
		return promocion;
	}

	public void setPromocion(Promocion promocion) {
		this.promocion = promocion;
	}

	public UsuarioAutorizante getUsuarioAutorizante() {
		return usuarioAutorizante;
	}

	public void setUsuarioAutorizante(UsuarioAutorizante usuarioAutorizante) {
		this.usuarioAutorizante = usuarioAutorizante;
	}

	public Boolean getTienePromocion() {
		return tienePromocion;
	}

	public void setTienePromocion(Boolean tienePromocion) {
		this.tienePromocion = tienePromocion;
	}

	public BigDecimal getPrecioAsientoPromocion() {
		return precioAsientoPromocion;
	}

	public void setPrecioAsientoPromocion(BigDecimal precioAsientoPromocion) {
		this.precioAsientoPromocion = precioAsientoPromocion;
	}

	public Boolean getIdaVuelta() {
		return idaVuelta;
	}

	public void setIdaVuelta(Boolean idaVuelta) {
		this.idaVuelta = idaVuelta;
	}

	public Boolean getNoPromocionIdaVuelta() {
		return noPromocionIdaVuelta;
	}

	public void setNoPromocionIdaVuelta(Boolean noPromocionIdaVuelta) {
		this.noPromocionIdaVuelta = noPromocionIdaVuelta;
	}

	public Boolean getActivaPostergacion() {
		return activaPostergacion;
	}

	public void setActivaPostergacion(Boolean activaPostergacion) {
		this.activaPostergacion = activaPostergacion;
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

	public Tripulacion getTripulacion_venta() {
		return tripulacion_venta;
	}

	public void setTripulacion_venta(Tripulacion tripulacion_venta) {
		this.tripulacion_venta = tripulacion_venta;
	}

	public String getSerieBoletoAnul() {
		return serieBoletoAnul;
	}

	public void setSerieBoletoAnul(String serieBoletoAnul) {
		this.serieBoletoAnul = serieBoletoAnul;
	}

	public String getNumeroBoletoAnul() {
		return numeroBoletoAnul;
	}

	public void setNumeroBoletoAnul(String numeroBoletoAnul) {
		this.numeroBoletoAnul = numeroBoletoAnul;
	}

	public TrackingBoleto getTrackingBoletoAnul() {
		return trackingBoletoAnul;
	}

	public void setTrackingBoletoAnul(TrackingBoleto trackingBoletoAnul) {
		this.trackingBoletoAnul = trackingBoletoAnul;
	}

	public TrackingBoleto getBoletoReprint() {
		return boletoReprint;
	}

	public void setBoletoReprint(TrackingBoleto boletoReprint) {
		this.boletoReprint = boletoReprint;
	}

	public Boolean getDeliveryEnElDia() {
		return deliveryEnElDia;
	}

	public void setDeliveryEnElDia(Boolean deliveryEnElDia) {
		this.deliveryEnElDia = deliveryEnElDia;
	}

	public Integer getHoraMaximaDelivery() {
		return horaMaximaDelivery;
	}

	public void setHoraMaximaDelivery(Integer horaMaximaDelivery) {
		this.horaMaximaDelivery = horaMaximaDelivery;
	}

	public Date getFechaLimiteDelivery() {
		return fechaLimiteDelivery;
	}

	public void setFechaLimiteDelivery(Date fechaLimiteDelivery) {
		this.fechaLimiteDelivery = fechaLimiteDelivery;
	}

	public Date getFechaEntregaDelivery() {
		return fechaEntregaDelivery;
	}

	public void setFechaEntregaDelivery(Date fechaEntregaDelivery) {
		this.fechaEntregaDelivery = fechaEntregaDelivery;
	}

	public TrackingBoleto getTrackingCodigo() {
		return trackingCodigo;
	}

	public void setTrackingCodigo(TrackingBoleto trackingCodigo) {
		this.trackingCodigo = trackingCodigo;
	}

	public Integer getTipoItinerario() {
		return tipoItinerario;
	}

	public void setTipoItinerario(Integer tipoItinerario) {
		this.tipoItinerario = tipoItinerario;
	}

	public Boolean get_frecuencia() {
		return _frecuencia;
	}

	public void set_frecuencia(Boolean _frecuencia) {
		this._frecuencia = _frecuencia;
	}

	public Integer get_cantidadViajes() {
		return _cantidadViajes;
	}

	public void set_cantidadViajes(Integer _cantidadViajes) {
		this._cantidadViajes = _cantidadViajes;
	}

	public String get_ultimaFechaViaje() {
		return _ultimaFechaViaje;
	}

	public void set_ultimaFechaViaje(String _ultimaFechaViaje) {
		this._ultimaFechaViaje = _ultimaFechaViaje;
	}

	public List<Persona> getListpersonabuscadas() {
		return listpersonabuscadas;
	}

	public void setListpersonabuscadas(List<Persona> listpersonabuscadas) {
		this.listpersonabuscadas = listpersonabuscadas;
	}

	public Boolean getEnablePromocion() {
		return enablePromocion;
	}

	public void setEnablePromocion(Boolean enablePromocion) {
		this.enablePromocion = enablePromocion;
	}

	public List<Promocion> getListaCmbPromociones() {
		return listaCmbPromociones;
	}

	public void setListaCmbPromociones(List<Promocion> listaCmbPromociones) {
		this.listaCmbPromociones = listaCmbPromociones;
	}

	public Integer getIdPromocionSelect() {
		return idPromocionSelect;
	}

	public void setIdPromocionSelect(Integer idPromocionSelect) {
		this.idPromocionSelect = idPromocionSelect;
	}

	public String getTransferenciaBanco() {
		return transferenciaBanco;
	}

	public void setTransferenciaBanco(String transferenciaBanco) {
		this.transferenciaBanco = transferenciaBanco;
	}

	public String getTransferenciaNumero() {
		return transferenciaNumero;
	}

	public void setTransferenciaNumero(String transferenciaNumero) {
		this.transferenciaNumero = transferenciaNumero;
	}


	public Boolean get_nuevo() {
		return _nuevo;
	}

	public void set_nuevo(Boolean _nuevo) {
		this._nuevo = _nuevo;
	}

	public String get_fechahoy() {
		return _fechahoy;
	}

	public void set_fechahoy(String _fechahoy) {
		this._fechahoy = _fechahoy;
	}

	public String getNroRuc() {
		return nroRuc;
	}
	public String getTipo_documento() {
		return tipo_documento;
	}

	public void setNroRuc(String nroRuc) {
		this.nroRuc = nroRuc;
	}


	public void setTipo_documento(String tipo_documento) {
		this.tipo_documento = tipo_documento;
	}

	public Boolean get_es_dni() {
		return _es_dni;
	}

	public void set_es_dni(Boolean _es_dni) {
		this._es_dni = _es_dni;
	}

	public String getDsctoBoletoMultiple() {
		return dsctoBoletoMultiple;
	}

	public void setDsctoBoletoMultiple(String dsctoBoletoMultiple) {
		this.dsctoBoletoMultiple = dsctoBoletoMultiple;
	}


	public Integer getIntsecuencia() {
		return intsecuencia;
	}

	public void setIntsecuencia(Integer intsecuencia) {
		this.intsecuencia = intsecuencia;
	}

	public BigDecimal getCostoBoletoPromocional() {
		return costoBoletoPromocional;
	}

	public void setCostoBoletoPromocional(BigDecimal costoBoletoPromocional) {
		this.costoBoletoPromocional = costoBoletoPromocional;
	}

	public Boolean get_bandHoraReserva() {
		return _bandHoraReserva;
	}

	public void set_bandHoraReserva(Boolean _bandHoraReserva) {
		this._bandHoraReserva = _bandHoraReserva;
	}

	public Boolean get_isCompartida() {
		return _isCompartida;
	}

	public void set_isCompartida(Boolean _isCompartida) {
		this._isCompartida = _isCompartida;
	}

	public boolean isPasajeroReg() {
		return pasajeroReg;
	}

	public void setPasajeroReg(boolean pasajeroReg) {
		this.pasajeroReg = pasajeroReg;
	}

	public String getFechaCompraBoleto() {
		return fechaCompraBoleto;
	}

	public void setFechaCompraBoleto(String fechaCompraBoleto) {
		this.fechaCompraBoleto = fechaCompraBoleto;
	}

	public String getHoraSalidaCompra() {
		return horaSalidaCompra;
	}

	public void setHoraSalidaCompra(String horaSalidaCompra) {
		this.horaSalidaCompra = horaSalidaCompra;
	}
	//MODIFICACION 09-09-2014
	public String getAuxRUC() {
		return auxRUC;
	}

	public void setAuxRUC(String auxRUC) {
		this.auxRUC = auxRUC;
	}

	public String getAuxRSocial() {
		return auxRSocial;
	}

	public void setAuxRSocial(String auxRSocial) {
		this.auxRSocial = auxRSocial;
	}

	public List<HistorialPasajero> getListaHistorialPasajero() {
		return listaHistorialPasajero;
	}

	public void setListaHistorialPasajero(
			List<HistorialPasajero> listaHistorialPasajero) {
		this.listaHistorialPasajero = listaHistorialPasajero;
	}

	public List<HistorialPasajero> getListaFiltroHistorialPasajero() {
		return listaFiltroHistorialPasajero;
	}

	public void setListaFiltroHistorialPasajero(
			List<HistorialPasajero> listaFiltroHistorialPasajero) {
		this.listaFiltroHistorialPasajero = listaFiltroHistorialPasajero;
	}

	public HistorialPasajero getHistorialPasajero() {
		return historialPasajero;
	}

	public void setHistorialPasajero(HistorialPasajero historialPasajero) {
		this.historialPasajero = historialPasajero;
	}

	public List<Integer> getListaAsientosDisponibles() {
		return listaAsientosDisponibles;
	}

	public void setListaAsientosDisponibles(List<Integer> listaAsientosDisponibles) {
		this.listaAsientosDisponibles = listaAsientosDisponibles;
	}

	public BigDecimal getDiferencia() {
		return diferencia;
	}

	public void setDiferencia(BigDecimal diferencia) {
		this.diferencia = diferencia;
	}

	public List<HistorialPasajero> getListaHistorialPasajeroDetalle() {
		return listaHistorialPasajeroDetalle;
	}

	public void setListaHistorialPasajeroDetalle(
			List<HistorialPasajero> listaHistorialPasajeroDetalle) {
		this.listaHistorialPasajeroDetalle = listaHistorialPasajeroDetalle;
	}
	
	public Integer getAsi_separados() {
		return asi_separados;
	}

	public void setAsi_separados(Integer asi_separados) {
		this.asi_separados = asi_separados;
	}

	public Servicio getServicioCompra() {
		return servicioCompra;
	}

	public void setServicioCompra(Servicio servicioCompra) {
		this.servicioCompra = servicioCompra;
	}

	public HistorialPasajero getRePrintBoleto() {
		return rePrintBoleto;
	}

	public void setRePrintBoleto(HistorialPasajero rePrintBoleto) {
		this.rePrintBoleto = rePrintBoleto;
	}

	public Date getFechaHoraLimitePostergacion() {
		return fechaHoraLimitePostergacion;
	}

	public void setFechaHoraLimitePostergacion(Date fechaHoraLimitePostergacion) {
		this.fechaHoraLimitePostergacion = fechaHoraLimitePostergacion;
	}

	public String getFechaStringEntregaDelivery() {
		return fechaStringEntregaDelivery;
	}

	public void setFechaStringEntregaDelivery(String fechaStringEntregaDelivery) {
		this.fechaStringEntregaDelivery = fechaStringEntregaDelivery;
	}

	public Usuario getUsuarioAsientoVenta() {
		return usuarioAsientoVenta;
	}

	public void setUsuarioAsientoVenta(Usuario usuarioAsientoVenta) {
		this.usuarioAsientoVenta = usuarioAsientoVenta;
	}

	public List<AsientoVenta> getListaAsientosxVenderValidos() {
		return listaAsientosxVenderValidos;
	}

	public void setListaAsientosxVenderValidos(
			List<AsientoVenta> listaAsientosxVenderValidos) {
		this.listaAsientosxVenderValidos = listaAsientosxVenderValidos;
	}

	public List<AsientoVenta> getListaAsientosxVenderNoValidos() {
		return listaAsientosxVenderNoValidos;
	}

	public void setListaAsientosxVenderNoValidos(
			List<AsientoVenta> listaAsientosxVenderNoValidos) {
		this.listaAsientosxVenderNoValidos = listaAsientosxVenderNoValidos;
	}

	public Boolean getBandMultiple() {
		return bandMultiple;
	}

	public void setBandMultiple(Boolean bandMultiple) {
		this.bandMultiple = bandMultiple;
	}

	public CopiaBoleto getBoletaCopia() {
		return boletaCopia;
	}

	public void setBoletaCopia(CopiaBoleto boletaCopia) {
		this.boletaCopia = boletaCopia;
	}

	public List<AsientoVenta> getAsientoVentaPrepagados() {
		return asientoVentaPrepagados;
	}

	public void setAsientoVentaPrepagados(List<AsientoVenta> asientoVentaPrepagados) {
		this.asientoVentaPrepagados = asientoVentaPrepagados;
	}

	public List<TrackingBoleto> getTrackingBoleto() {
		return trackingBoleto;
	}

	public void setTrackingBoleto(List<TrackingBoleto> trackingBoleto) {
		this.trackingBoleto = trackingBoleto;
	}


	public Boolean getPostergacionRecuperadaEnMes() {
		return postergacionRecuperadaEnMes;
	}

	public void setPostergacionRecuperadaEnMes(Boolean postergacionRecuperadaEnMes) {
		this.postergacionRecuperadaEnMes = postergacionRecuperadaEnMes;
	}

	public Inhabilitacion getInhabilitacion() {
		return inhabilitacion;
	}

	public void setInhabilitacion(Inhabilitacion inhabilitacion) {
		this.inhabilitacion = inhabilitacion;
	}

	public Boolean getRecuperarInhabilitacion() {
		return recuperarInhabilitacion;
	}

	public void setRecuperarInhabilitacion(Boolean recuperarInhabilitacion) {
		this.recuperarInhabilitacion = recuperarInhabilitacion;
	}

	public Boolean getInhabilitacionRecuperadaEnMes() {
		return inhabilitacionRecuperadaEnMes;
	}

	public void setInhabilitacionRecuperadaEnMes(
			Boolean inhabilitacionRecuperadaEnMes) {
		this.inhabilitacionRecuperadaEnMes = inhabilitacionRecuperadaEnMes;
	}

	public Boolean getBuscarInhabilitado() {
		return buscarInhabilitado;
	}

	public void setBuscarInhabilitado(Boolean buscarInhabilitado) {
		this.buscarInhabilitado = buscarInhabilitado;
	}

	public Integer getPisoCambio() {
		return pisoCambio;
	}

	public void setPisoCambio(Integer pisoCambio) {
		this.pisoCambio = pisoCambio;
	}

	public List<Asiento> getListaAsientosPisoUnoTarget() {
		return listaAsientosPisoUnoTarget;
	}

	public void setListaAsientosPisoUnoTarget(
			List<Asiento> listaAsientosPisoUnoTarget) {
		this.listaAsientosPisoUnoTarget = listaAsientosPisoUnoTarget;
	}

	public List<Asiento> getListaAsientosPisoDosTarget() {
		return listaAsientosPisoDosTarget;
	}

	public void setListaAsientosPisoDosTarget(
			List<Asiento> listaAsientosPisoDosTarget) {
		this.listaAsientosPisoDosTarget = listaAsientosPisoDosTarget;
	}

	public AsientoVenta getAsientoVentaCambiado() {
		return asientoVentaCambiado;
	}

	public void setAsientoVentaCambiado(AsientoVenta asientoVentaCambiado) {
		this.asientoVentaCambiado = asientoVentaCambiado;
	}

	public List<AsientoVenta> getListaAsientosVentaLibres() {
		return listaAsientosVentaLibres;
	}

	public void setListaAsientosVentaLibres(
			List<AsientoVenta> listaAsientosVentaLibres) {
		this.listaAsientosVentaLibres = listaAsientosVentaLibres;
	}

	public String getTipoVentaHistorial() {
		return tipoVentaHistorial;
	}

	public void setTipoVentaHistorial(String tipoVentaHistorial) {
		this.tipoVentaHistorial = tipoVentaHistorial;
	}

	public Persona getPersonaHistorial() {
		return personaHistorial;
	}

	public void setPersonaHistorial(Persona personaHistorial) {
		this.personaHistorial = personaHistorial;
	}

	public Boolean getConsultaRapida() {
		return consultaRapida;
	}

	public void setConsultaRapida(Boolean consultaRapida) {
		this.consultaRapida = consultaRapida;
	}

	public String getBoletoSerieBuscado() {
		return boletoSerieBuscado;
	}

	public void setBoletoSerieBuscado(String boletoSerieBuscado) {
		this.boletoSerieBuscado = boletoSerieBuscado;
	}

	public String getBoletoNumeroBuscado() {
		return boletoNumeroBuscado;
	}

	public void setBoletoNumeroBuscado(String boletoNumeroBuscado) {
		this.boletoNumeroBuscado = boletoNumeroBuscado;
	}

	public Boolean getDiferenciaPostergacion() {
		return diferenciaPostergacion;
	}

	public void setDiferenciaPostergacion(Boolean diferenciaPostergacion) {
		this.diferenciaPostergacion = diferenciaPostergacion;
	}

	public BigDecimal getValorBoletoInhabilitado() {
		return valorBoletoInhabilitado;
	}

	public void setValorBoletoInhabilitado(BigDecimal valorBoletoInhabilitado) {
		this.valorBoletoInhabilitado = valorBoletoInhabilitado;
	}

	public Servicio getServicioInhabilitado() {
		return servicioInhabilitado;
	}

	public void setServicioInhabilitado(Servicio servicioInhabilitado) {
		this.servicioInhabilitado = servicioInhabilitado;
	}

	public Boolean getBuscarResultado() {
		return buscarResultado;
	}

	public void setBuscarResultado(Boolean buscarResultado) {
		this.buscarResultado = buscarResultado;
	}

	public Integer getNroMovimientosBoleto() {
		return nroMovimientosBoleto;
	}

	public void setNroMovimientosBoleto(Integer nroMovimientosBoleto) {
		this.nroMovimientosBoleto = nroMovimientosBoleto;
	}



	
	
}