package com.certicom.ittsa.managedBeans;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRParameter;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.output.OutputException;

import org.apache.commons.io.IOUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.certicom.ittsa.domain.DetalleEncomienda;
import com.certicom.ittsa.domain.DetalleGuiaRemision;
import com.certicom.ittsa.domain.DetalleNotaCobranza;
import com.certicom.ittsa.domain.DistritoCategoria;
import com.certicom.ittsa.domain.Empresa;
import com.certicom.ittsa.domain.Encomienda;
import com.certicom.ittsa.domain.GuiaRemision;
import com.certicom.ittsa.domain.HistorialEncomienda;
import com.certicom.ittsa.domain.Liquidacion_Venta;
import com.certicom.ittsa.domain.NotaCobranza;
import com.certicom.ittsa.domain.Oficina;
import com.certicom.ittsa.domain.Persona;
import com.certicom.ittsa.domain.Producto_DetalleEnc;
import com.certicom.ittsa.domain.Programacion;
import com.certicom.ittsa.domain.PuntoVenta;
import com.certicom.ittsa.domain.Tarifa;
import com.certicom.ittsa.domain.Tarifa_Producto;
import com.certicom.ittsa.domain.TipoDocumento;
import com.certicom.ittsa.domain.TrackingEncomienda;
import com.certicom.ittsa.domain.Usuario;
import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Destino;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.DestinoServices;
import com.certicom.ittsa.services.DetalleEncomiendaServices;
import com.certicom.ittsa.services.DetalleGuiaRemisionService;
import com.certicom.ittsa.services.DetalleNotaCobranzaService;
import com.certicom.ittsa.services.DistritoCategoriaService;
import com.certicom.ittsa.services.EmpresaService;
import com.certicom.ittsa.services.EncomiendaServices;
import com.certicom.ittsa.services.FlotaService;
import com.certicom.ittsa.services.GuiaRemisionService;
import com.certicom.ittsa.services.HistorialEncomiendaServices;
import com.certicom.ittsa.services.Liquidacion_VentaServices;
import com.certicom.ittsa.services.NotaCobranzaService;
import com.certicom.ittsa.services.OficinaService;
import com.certicom.ittsa.services.PersonaServices;
import com.certicom.ittsa.services.Producto_DetalleEncService;
import com.certicom.ittsa.services.ProgramacionService;
import com.certicom.ittsa.services.PuntoVentaService;
import com.certicom.ittsa.services.TarifaServices;
import com.certicom.ittsa.services.Tarifa_ProductoServices;
import com.certicom.ittsa.services.TipoDocumentoServices;
import com.certicom.ittsa.services.TrackingEncomiendaServices;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.ExportarArchivo;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenerateLetraNumber;
import com.pe.certicom.ittsa.commons.GenericBeans;
import com.pe.certicom.ittsa.commons.NumeroALetra;

@ManagedBean(name="encomiendaMB")
@ViewScoped
public class EncomiendaMB  extends GenericBeans implements Serializable{
	
	private Encomienda encomiendaReporte;
	private List<DetalleEncomienda> listaRptDetalle;
	private List<Empresa> listaEmpresasFilter;
	private List<Empresa> listaEmpresas;
	private Double subTotalRpt = 0.0;
	private Double IGVRpt = 0.0;
	private Double TotalRpt = 0.0;
	private Programacion busSeleccionado;
	private SelectItem[] listSINO;  
	private List<Oficina> listaOficinas;
	private OficinaService oficinaService;
	private String totalLetras;
	private boolean flgidBus;
	private Empresa empresa;
	private Boolean bandMismaOficina;
	
	/****************Atributos*******************/
	
	@ManagedProperty(value="#{loginMB.usuario}")
	private Usuario usuarioLogin;
	private Persona remitente;
	private Persona destinatario1;
	private Persona destinatario2;
	private PuntoVenta puntoVenta;
	private Agencia agencia;
	private Tarifa tarifaReparto;
	private Tarifa tarifaBase;
	private Tarifa_Producto tarifa_producto;
	private DetalleEncomienda detalleEncomienda;
	private DetalleEncomienda capturaEncomienda;
	private DetalleEncomienda detEncomiendaCompuesto;
	private Encomienda encomienda;
	private Producto_DetalleEnc producto_detalleEnc;
	private List<Programacion> listaBusGuia;

	private Integer idOficina;
	private Integer idDestino;
	private Integer idTarifaReparto;
	private Boolean bandReparto;
	private Boolean bandContraEntrega;
	private Boolean bandExtraRapido;
	private Integer cantidad;
	private String basePago;
	private Integer basePago2;
	private Boolean bandCrearEncomienda;
	private Double importeTotalEnc;
	private Double importeTotalIGVEnc;
	private Double importeTotalFactEnc;
	private Double precioReparto;
	private Double precioRepartoSolo;
	private Double precioServicio;
	private Double lastPrecioReparto = 0.0;
	private String formaPago;
	private String tipoTarjeta;
	private Double montoEfectivo;
	private Double montoTarjeta;
	private Integer countPrecioReparto=0;
	private String direccionEnvio="";
	private Double importeDetalle;
	private Double importeLastDetalle=0.0;
	private BigDecimal pesoTotalEnc;
	private BigDecimal precioUnit;
	private String aPaternoDestBusqueda;
	private String aMaternoDestBusqueda;
	private String condBusqDestinatario;
	private String condBusqEmpresa;
	private Integer countSubpartesProducto;
	private Integer countDetEnc;
	private Map<DetalleEncomienda, Object> listasSubpartes = new HashMap<DetalleEncomienda, Object>();
	private Map<Integer, Object> detallesEnc = new HashMap<Integer, Object>();
	private Boolean bandAnularEncomienda = Boolean.TRUE;
	private Boolean bandRegEncomienda = Boolean.TRUE;
	private Integer _idBus;
	// variables para el rendered
	private Boolean bandNuevoEncomienda; 
	private Boolean bandRegRenderedEnc; 
	private Encomienda encoTracking;
	
	private String comprobante;
	
	private Double costoDistritoReparto = 0.00; 
	
	private List<Destino> listaAgDestinos;
	private List<TipoDocumento> listaTipoDoc;
	private List<Tarifa_Producto> listaTarifaProducto;
	private List<Tarifa_Producto> listaFilterTarifaProducto;
	private List<DetalleEncomienda> listaDetalleEncomienda;
	private List<DetalleEncomienda> listaFilterDetalleEncomienda;
	private List<Tarifa> listaTarifaReparto;
	private List<HistorialEncomienda> listaHistorial;
	private List<HistorialEncomienda> listaHistorialFilter;
	private List<Persona> listaPosiblesDestinatarios;
	private List<Persona> listaPosiblesDestinatariosFilter;
	private List<Producto_DetalleEnc> listaProductoDetalleEnc;
	private List<Encomienda> listaEncomienda;
	private List<Encomienda> listaEncomiendaFilter;
	private List<DistritoCategoria> listaDistCateg;
	private List<Encomienda> listaTrackEncomienda;
	private List<Encomienda> listaTrackEncomiendaFilter;
	private Integer _idDistritoReparto;
	private String _guiaRemisionCliente;
	
	private AgenciaService agenciaService;
	private DestinoServices destinoService;
	private TipoDocumentoServices tipoDocServices;
	private PersonaServices personaServives;
	private PuntoVentaService puntoVentaServices;
	private Tarifa_ProductoServices tarifa_productoServices;
	private EncomiendaServices encomiendaServices;
	private DetalleEncomiendaServices detalleEncomiendaServices;
	private Producto_DetalleEncService producto_DetalleEncServices;
	private TarifaServices tarifaServices;
	private Liquidacion_VentaServices liquidacion_ventaServices;
	private TrackingEncomiendaServices trackingEncomiendaServices;
	private HistorialEncomiendaServices historialEncomiendaServices;
	private DistritoCategoriaService distritoCategoriaService;
	private EmpresaService empresaService;
	private FlotaService flotaService;
	private GuiaRemisionService guiaRemisionService;
	private DetalleGuiaRemisionService detalleGuiaRemisionService;
	private NotaCobranzaService notaCobranzaService;
	private DetalleNotaCobranzaService detalleNotaCobranzaService;
	private ProgramacionService  programacionService;
	
	public EncomiendaMB(){}
	
	@PostConstruct
	public void inicia()
	{
		this.flotaService = new FlotaService();
		this.agenciaService = new AgenciaService();
		this.destinoService = new DestinoServices();
		this.tipoDocServices = new TipoDocumentoServices();
		this.personaServives = new PersonaServices();
		this.puntoVentaServices = new PuntoVentaService();
		this.tarifa_productoServices = new Tarifa_ProductoServices();
		this.encomiendaServices = new EncomiendaServices();
		this.detalleEncomiendaServices = new DetalleEncomiendaServices(); 
		this.producto_DetalleEncServices = new Producto_DetalleEncService();
		this.tarifaServices = new TarifaServices();
		this.liquidacion_ventaServices = new Liquidacion_VentaServices();
		this.trackingEncomiendaServices = new TrackingEncomiendaServices();
		this.historialEncomiendaServices = new HistorialEncomiendaServices();
		this.distritoCategoriaService = new DistritoCategoriaService();
		this.empresaService = new EmpresaService();
		this.guiaRemisionService = new GuiaRemisionService();
		this.detalleGuiaRemisionService = new DetalleGuiaRemisionService();
		this.notaCobranzaService = new NotaCobranzaService();
		this.detalleNotaCobranzaService = new DetalleNotaCobranzaService();
		this.programacionService = new ProgramacionService();
		this.oficinaService = new OficinaService();
		this.empresa = new Empresa();
		this.setFlgidBus(false);
		
		this.listaBusGuia = new ArrayList<>();
		this.encomiendaReporte = new Encomienda();
		this.listaRptDetalle = new ArrayList<DetalleEncomienda>();
		this.listaOficinas = new ArrayList<>();
		try {
			agencia = this.agenciaService.findById(usuarioLogin.getIdagencia());
			
			System.out.println("Imprimimos IP Venta:"+usuarioLogin.getIdpuntoventa());
			puntoVenta = this.puntoVentaServices.findById(usuarioLogin.getIdpuntoventa());
			this.comprobante = puntoVenta.getSerieBoleta()+" - "+puntoVenta.getUltimaBoleta();
			this.listaAgDestinos = destinoService.obtenerDestino(agencia.getIdagencia());
			this.listaTipoDoc = tipoDocServices.findAll();
			this.listaTarifaProducto = tarifa_productoServices.findByIdAgencia(usuarioLogin.getIdagencia());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		listSINO = filterSINO();
		
		this.remitente = new Persona();
		this.remitente.setTipoPersona("N");
		this.remitente.setIdtipodoc(listaTipoDoc.get(0).getIdtipodoc());
		this.remitente.setEmpresa(new Empresa()); 
		
		this.destinatario1 = new Persona();
		this.destinatario1.setTipoPersona("N");
		this.destinatario1.setIdtipodoc(listaTipoDoc.get(0).getIdtipodoc());
		this.destinatario1.setEmpresa(new Empresa());
		
		this.destinatario2 = new Persona();
		this.destinatario2.setTipoPersona("N");
		this.destinatario2.setIdtipodoc(listaTipoDoc.get(0).getIdtipodoc());
		this.destinatario2.setEmpresa(new Empresa());
		
		this.bandCrearEncomienda = Boolean.TRUE;
		this.listaDetalleEncomienda = new ArrayList<>();
		this.importeTotalEnc = 0.0;
		this.importeTotalIGVEnc = 0.0;
		this.importeTotalFactEnc = 0.0;
		this.bandReparto = Boolean.FALSE;
		this.bandMismaOficina = Boolean.FALSE;
		this.bandContraEntrega = Boolean.FALSE;
		this.bandExtraRapido = Boolean.FALSE;
		this.formaPago = "E";
		this.tipoTarjeta="V";
		this.montoEfectivo = 0.0;//new BigDecimal(0);
		this.montoTarjeta = 0.0;//new BigDecimal(0);
		this.encomienda= new Encomienda();
		this.tarifaReparto = new Tarifa();
		this.precioRepartoSolo = 0.0;
		this.precioReparto = 0.0;
		this.precioServicio = 0.0;
		this.pesoTotalEnc = new BigDecimal(0.00);
		this.precioUnit = new BigDecimal(0.00);
		this.countDetEnc =0;
		this.tarifaBase = new Tarifa();
		
		this.bandNuevoEncomienda = Boolean.FALSE; 
		this.bandRegRenderedEnc = Boolean.TRUE; 
		
		
		this.encoTracking = new Encomienda();
	}
	
	public void setearAgenciaOrigen(){
		
		if(this.bandMismaOficina){
			//agregando a la lista
			Destino destin = new Destino();
			destin.setOrigen(this.agencia.getIdagencia());
			destin.setDestino(this.agencia.getIdagencia());
			destin.setEstado(Boolean.TRUE);
			destin.setKmDistancia(0);
			destin.setDesDestino(this.agencia.getDescripcion());
			destin.setDesOrigen(this.agencia.getDescripcion());
			this.listaAgDestinos.add(destin);
		}else{
			//retiramos de la lista
			for (int i = 0; i < this.listaAgDestinos.size(); i++) {
				Destino dest = this.listaAgDestinos.get(i);
				if(dest.getOrigen() == this.agencia.getIdagencia() && dest.getDestino() == this.agencia.getIdagencia()){
					this.listaAgDestinos.remove(dest);
				}
			}
		}
		
	}
	
	public void agregarRepartoGrilla(){
		
		Boolean valido=Boolean.TRUE;
		RequestContext context = RequestContext.getCurrentInstance();  
   	    context.addCallbackParam("esRepartoCierre", valido );
		
		String descripReparto = "";
		for(int i=0;i<this.listaDistCateg.size();i++){
			DistritoCategoria dc = this.listaDistCateg.get(i);
			if(dc.getId()==this._idDistritoReparto){
				descripReparto = dc.getDescripcion();
			}
		}
		
		countDetEnc++;
		DetalleEncomienda detalle = new DetalleEncomienda();
		detalle.setCantidad(1);
		detalle.setDescripcion("REPARTO - "+ descripReparto + " - " +this.direccionEnvio);
		detalle.setPrecioKilo(new BigDecimal(0));
		detalle.setPeso(new BigDecimal(0));
		detalle.setPrecioEnvio(new BigDecimal(0));
		detalle.setPrecioEnvioNormal(new Double("0")); 
		detalle.setNroOrden(countDetEnc);
		if(this.bandExtraRapido){
			Double costoT = this.costoDistritoReparto + Double.parseDouble(this.tarifaReparto.getPrecioExtraRapido().toString());
			detalle.setImporte(new BigDecimal(costoT));
		}else{
			detalle.setImporte(new BigDecimal(this.costoDistritoReparto));
		}
		detalle.setTipoReparto(Boolean.TRUE); 
		this.listaDetalleEncomienda.add(detalle);
		this.detallesEnc.put(countDetEnc, detalle);
		
	}
	
	public void marcarContraEntrega(){
		/*
		 Verificar si esta marcado como remitente Natural Destinatario Juridico 
		 */
		if(this.remitente.getTipoPersona().equals("N") && this.bandContraEntrega==true && (this.destinatario1.getTipoPersona().equals("J") || this.destinatario2.getTipoPersona().equals("J"))){
			System.out.println("entro en N TRUE J J ");
			if(this.importeTotalIGVEnc==0.0){
				this.importeTotalIGVEnc =  roundDecimal(this.importeTotalEnc*Constante.IGV);
				this.importeTotalFactEnc = roundDecimal(this.importeTotalIGVEnc + this.importeTotalEnc);
			}
		}else if(this.remitente.getTipoPersona().equals("N") && this.bandContraEntrega==false && (this.destinatario1.getTipoPersona().equals("J") || this.destinatario2.getTipoPersona().equals("J"))){
			if(this.importeTotalIGVEnc>0){
				this.importeTotalIGVEnc = 0.0;
				this.importeTotalFactEnc = (this.importeTotalFactEnc - (this.importeTotalEnc*Constante.IGV));
		   }
		}
	}
	
	public void validarContraEntrega(){
		
		RequestContext context = RequestContext.getCurrentInstance();
		if(this.idDestino == null){
			this.destinatario1.setTipoPersona("N");
			this.destinatario2.setTipoPersona("N");
			FacesUtils.showFacesMessage("Seleccione un destino.",Constante.INFORMACION);
			context.update("sms");
			context.update("formDatosDestinatario1");
			context.update("formDatosDestinatario2");
		}else{
			if(this.remitente.getTipoPersona().equals("N") && this.bandContraEntrega==true && (this.destinatario1.getTipoPersona().equals("J") || this.destinatario2.getTipoPersona().equals("J"))){
				if(this.importeTotalIGVEnc==0.0){
					this.importeTotalIGVEnc =  roundDecimal(this.importeTotalEnc*Constante.IGV);
					this.importeTotalFactEnc = roundDecimal(this.importeTotalIGVEnc + this.importeTotalEnc);
				}
			}else if(this.remitente.getTipoPersona().equals("N") && this.bandContraEntrega==false && (this.destinatario1.getTipoPersona().equals("J") || this.destinatario2.getTipoPersona().equals("J"))){
				if(this.importeTotalIGVEnc>0){
					this.importeTotalIGVEnc = 0.0;
					this.importeTotalFactEnc = (this.importeTotalFactEnc - (this.importeTotalEnc*Constante.IGV));
				}
			}
		}
	}
	
	public void setearOficina(){
		System.out.println("el id de oficina es " + this.idOficina);
		this.encomienda.setIdOficina(this.idOficina);
	}
	
	public void setearTipoPersona(){
		RequestContext context = RequestContext.getCurrentInstance();
		if(this.idDestino == null){
			this.remitente.setTipoPersona("N");
			//encomiendaMB.remitente.tipoPersona
			FacesUtils.showFacesMessage("Seleccione un destino.",Constante.INFORMACION);
			context.update("sms");
			context.update("formDatosRemitente:options");
		}else{
			this.setFlgidBus(false);
			if(this.remitente.getTipoPersona().equals("J")){
				this.comprobante = this.puntoVenta.getSerieFactura()+" - "+this.puntoVenta.getUltimaFactura();
				if(this.importeTotalIGVEnc==0){
					this.importeTotalIGVEnc = roundDecimal(Constante.IGV * this.importeTotalEnc);
					this.importeTotalFactEnc = roundDecimal(this.importeTotalEnc + this.importeTotalIGVEnc);
				}
				//Hacemos Aparecer un popup para eligir el bus
				context.execute("dlgBusquedaBus.show()");
			}else{
				this.comprobante = puntoVenta.getSerieBoleta()+" - "+puntoVenta.getUltimaBoleta();
				this.importeTotalIGVEnc = 0.0;
				this.importeTotalFactEnc = roundDecimal(this.importeTotalEnc);
			}
		
		}
	}
	
	public void setearBusGuia(){
		RequestContext context = RequestContext.getCurrentInstance();
		this.setFlgidBus(true);
		context.execute("dlgBusquedaBus.hide()");
	}
	
	public void buscarPersona(String busq){
		RequestContext context = RequestContext.getCurrentInstance(); 
		try {
			if(busq.equals("R")){
				listaHistorial = this.historialEncomiendaServices.findByDNIRemitente(this.remitente.getDni());
				if(listaHistorial.size()==0){
					this.remitente.getEmpresa().setRUC(null);
					this.remitente.getEmpresa().setRazonSocial(null);
					
					Persona p = personaServives.findByNroDocumento(this.remitente.getDni());
					if(p!=null){
						this.remitente.setApellidos(p.getAPaterno()+" "+ p.getAMaterno());
						this.remitente.setNombres(p.getNombres()); 
					}
				}else{
					for(HistorialEncomienda h : listaHistorial){
						
						if(h.getRucRemitente()!=null){
							this.comprobante = this.puntoVenta.getSerieFactura()+" - "+this.puntoVenta.getUltimaFactura();
						}
						if(h.getRucDestinatario1()!=null){
							this.destinatario1.setTipoPersona("J");
							
						}
						if(h.getRucDestinatario2()!=null){
							this.destinatario2.setTipoPersona("J");
						}
					}
					context.addCallbackParam("esListaEnvios", Boolean.TRUE);
					context.update("formListaEnviosFrec");
					context.update("txtDocumento");
				}		
				
			}else if(busq.equals("D1")){
				this.destinatario1.getEmpresa().setRUC(null);
				this.destinatario1.getEmpresa().setRazonSocial(null);
				Persona p = personaServives.findByNroDocumento(this.destinatario1.getDni());
				if(p!=null){
					this.destinatario1.setApellidos(p.getAPaterno()+" "+ p.getAMaterno());
					this.destinatario1.setNombres(p.getNombres()); 
					this.destinatario1.setDireccion(null); }
			}else{
				this.destinatario2.getEmpresa().setRUC(null);
				this.destinatario2.getEmpresa().setRazonSocial(null);
				Persona p = personaServives.findByNroDocumento(this.destinatario2.getDni()); 
				if(p!=null){
					this.destinatario2.setApellidos(p.getAPaterno()+" "+ p.getAMaterno());
					this.destinatario2.setNombres(p.getNombres()); 
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public SelectItem[] filterSINO(){
		SelectItem[] items ;
			items = new SelectItem[]{new SelectItem("","--Seleccione--"),
									 new SelectItem("SI"),
					 				 new SelectItem("NO")};
			return items;
	}
	
	
	public void buscarEmpresa(String val){
		try {
			if(val.equals("R")){
				Empresa empresa = new Empresa();
				empresa = this.empresaService.findByNroRUC(this.remitente.getEmpresa().getRUC());
				this.remitente.getEmpresa().setRazonSocial(empresa.getRazonSocial());
			}else if(val.equals("D1")){
				Empresa empresa1 = new Empresa();
				System.out.println("entro");
				empresa1 = this.empresaService.findByNroRUC(this.destinatario1.getEmpresa().getRUC());
				this.destinatario1.getEmpresa().setRazonSocial(empresa1.getRazonSocial());
			}else if(val.equals("D2")){
				Empresa empresa2 = new Empresa();
				empresa2 = this.empresaService.findByNroRUC(this.destinatario2.getEmpresa().getRUC());
				this.destinatario2.getEmpresa().setRazonSocial(empresa2.getRazonSocial());
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void nuevaBusquedaDestinatario(String dest){
		this.condBusqDestinatario = dest;
		this.aPaternoDestBusqueda = "";
		this.aMaternoDestBusqueda = "";
//		this.listaPosiblesDestinatariosFilter = new ArrayList<>();
//		this.listaPosiblesDestinatariosFilter.clear();
		this.listaPosiblesDestinatariosFilter = null;
		this.listaPosiblesDestinatarios = new ArrayList<>();
		this.listaPosiblesDestinatarios.clear();
		
	}
	
	public void nuevaBusquedaEmpresa(String dest){
		this.empresa = new Empresa();
		this.condBusqEmpresa = dest;
		this.listaEmpresasFilter=null;
		this.listaEmpresas= new ArrayList<>();
		this.listaEmpresas.clear();
		System.out.println("Entramos a Limpiar");
	}
	
	public void buscarDestinatario(){
		try {
			if(!getaPaternoDestBusqueda().equals("") && !getaMaternoDestBusqueda().equals("")){
				this.listaPosiblesDestinatarios = this.personaServives.findByApellidos(getaPaternoDestBusqueda().toUpperCase(), getaMaternoDestBusqueda().toUpperCase());
			}else if (getaPaternoDestBusqueda()!=null && !getaPaternoDestBusqueda().equals("")){
				this.listaPosiblesDestinatarios = this.personaServives.findByApellidoPaterno(getaPaternoDestBusqueda());
			}else if (getaMaternoDestBusqueda()!=null && !getaMaternoDestBusqueda().equals("")){
				this.listaPosiblesDestinatarios = this.personaServives.findByApellidoMaterno(getaMaternoDestBusqueda()); 
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void buscarEmpresas(){
		try {
			if (this.empresa.getRazonSocial()!=null && !this.empresa.getRazonSocial().equals("")){
				this.listaEmpresas = this.empresaService.findByRazSoc(this.empresa.getRazonSocial());
				System.out.println("Tamaño de la lista : "+this.listaEmpresas.size());
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void asignarDestinatario(Persona p){
		if(this.condBusqDestinatario.equals("D1")){
			this.destinatario1.setApellidos(p.getAPaterno()+" "+p.getAMaterno()); 
			this.destinatario1.setNombres(p.getNombres());
			this.destinatario1.setDni(p.getDni()); 
		}else{
			this.destinatario2.setApellidos(p.getAPaterno()+" "+p.getAMaterno()); 
			this.destinatario2.setNombres(p.getNombres());
			this.destinatario2.setDni(p.getDni()); 
		}
	}
	
	public void asignarEmpresa(Empresa e){
		if(this.condBusqEmpresa.equals("R")){
			this.remitente.setEmpresa(e);;
		}else if(this.condBusqEmpresa.equals("D1")){
			this.destinatario1.setEmpresa(e); 
		}else if(this.condBusqEmpresa.equals("D2")){
			this.destinatario2.setEmpresa(e); 
		}
	}
	
	public void asignarDatos(HistorialEncomienda h){
		try {
		this.remitente.setApellidos(h.getApellidosRemitente());
		this.remitente.setNombres(h.getNombresRemitente()); 
		this.remitente.setTelefono(h.getTelefonoRemitente());
		Empresa er = new Empresa();
		er.setRUC(h.getRucRemitente());
		er.setRazonSocial(h.getRazonSocialRemitente());
		this.remitente.setEmpresa(er);
		this.destinatario1.setDni(h.getDniDestinatario1());
		this.destinatario1.setApellidos(h.getApellidosDestinatario1());
		this.destinatario1.setNombres(h.getNombresDestinatario1());
		this.destinatario1.setTelefono(h.getTelefonoDestinatario1()); 
		Empresa ed1 = new Empresa();
		ed1.setRUC(h.getRucDestinatario1());
		ed1.setRazonSocial(h.getRazonSocialDestinatario1());
		this.destinatario1.setEmpresa(ed1); 
		this.destinatario2.setDni(h.getDniDestinatario2());
		this.destinatario2.setApellidos(h.getApellidosDestinatario2());
		this.destinatario2.setNombres(h.getNombresDestinatario2());
		this.destinatario2.setTelefono(h.getTelefonoDestinatario2()); 
		Empresa ed2 = new Empresa();
		ed2.setRUC(h.getRucDestinatario2());
		ed2.setRazonSocial(h.getRazonSocialDestinatario2());
		this.destinatario2.setEmpresa(ed2);
		this.direccionEnvio = h.getDireccionEnvio();
		if(h.getRucRemitente()!=null){
			this.remitente.setTipoPersona("J"); 
		}
		
		//cargar los datos de reparto y extrarapido
		if(h.getPrecioReparto()>0){
			this.bandReparto = true;
			this.idTarifaReparto = h.getIdTarifa();
			this.direccionEnvio = h.getDireccionEnvio();
			this.precioReparto = h.getPrecioReparto();
			this.tarifaReparto = tarifaServices.findById(h.getIdTarifa());
			//seteamos el destino pues esta enlazado con la tarifa 
			this.idDestino = this.tarifaReparto.getIdDestino();
				if(h.getPrecioExtraRapido()!=null){
					this.bandExtraRapido = true;
					this.costoDistritoReparto = roundDecimal(this.precioReparto - (this.tarifaReparto.getPrecioExtraRapido()).doubleValue());
				}else{
				    this.costoDistritoReparto = roundDecimal(this.precioReparto);
				}
				listarDistritosReparto();
				agregarRepartoGrilla();
				setearTarifaRepartoAsignar();
		}else{
			this.bandReparto = false;
		}
		//llenamos la lista de reparto si fuera de reparto
		
		
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void setearFormaPago(){
		RequestContext context = RequestContext.getCurrentInstance();
		if(getFormaPago().equals("T")||getFormaPago().equals("M")){
			context.addCallbackParam("esFormaPago", Boolean.TRUE);
		}else context.addCallbackParam("esFormaPago", Boolean.FALSE);
	}
	
	public void nuevoProducto(){
		try {
			RequestContext context = RequestContext.getCurrentInstance();
			if(getIdDestino()!=null){
				this.tarifaBase = this.tarifaServices.findTarifaEncomiendaByOrigenDestino(this.agencia.getIdagencia(), idDestino);
				this.detalleEncomienda = new DetalleEncomienda();
				this.tarifa_producto = new Tarifa_Producto();
			//	this.tarifa_producto.setPrecioTotal();
				this.basePago2=4;
				this.cantidad= 1;
				this.tarifa_producto.setDescripcion(""); 
			//	this.tarifa_producto.setPrecioKilo(this.tarifaBase.getPrecioKilo()); 
				context.addCallbackParam("esDestinoNP", Boolean.TRUE);
			}else{
				context.addCallbackParam("esDestinoNP", Boolean.FALSE);
				FacesUtils.showFacesMessage("Seleccione un destino.",Constante.ERROR);
				context.update("sms");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void editarProducto(Tarifa_Producto tar){
		System.out.println("******editando producto");
		System.out.println("*******el id de Destino "+this.agencia.getIdagencia());
		System.out.println("*******el id de Destino "+idDestino);
		try {
			RequestContext context = RequestContext.getCurrentInstance();
			if(getIdDestino()!=null){
				this.tarifaBase = this.tarifaServices.findTarifaEncomiendaByOrigenDestino(this.agencia.getIdagencia(), idDestino);
				this.tarifa_producto = tar;
				this.detalleEncomienda = new DetalleEncomienda();
				this.basePago2=4;
				this.cantidad= 1;
				this.tarifa_producto.setPrecioKilo(this.tarifaBase.getPrecioKilo());
				 double c =this.tarifaBase.getPrecioEnvioNormal(); 
			     BigDecimal b = new BigDecimal(c);
			//	this.tarifa_producto.setTotalPagar(this.tarifa_producto.getPrecioEnvio());
			//	this.precioUnit= this.tarifa_producto.getTotalPagar()/this.cantidad; 
				System.out.println(this.tarifa_producto.getTotalPagar());
				context.addCallbackParam("esDestinoP", Boolean.TRUE);
				System.out.println("el valor de la opcion que entra es " + this.basePago2);
			}else{
				FacesUtils.showFacesMessage("Seleccione un destino.",Constante.ERROR);
				context.update("sms");
				context.addCallbackParam("esDestinoP", Boolean.FALSE);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void verificarDatosIngreso(){
		RequestContext context = RequestContext.getCurrentInstance();  
		if(getIdDestino()!=null){
			if(getIdOficina()!=null){
				if(this.remitente.getDni()!=null && this.destinatario1.getDni()!=null){
					if(this.listaDetalleEncomienda.size()!=0){
						if(this.remitente.getTipoPersona().equals("J")){
							if(this.remitente.getEmpresa().getRUC() != null && !this.remitente.getEmpresa().getRUC().equals("") && this.remitente.getEmpresa().getRazonSocial() != null && !this.remitente.getEmpresa().getRazonSocial().equals("")){
								context.addCallbackParam("esValido", Boolean.TRUE );
							}else{
								FacesUtils.showFacesMessage("Ingrese un RUC y razón social.",Constante.ERROR);
								context.update("sms");
								context.addCallbackParam("esValido", Boolean.FALSE);
							}
						}else{
							context.addCallbackParam("esValido", Boolean.TRUE );
						}
					}else{
						FacesUtils.showFacesMessage("Ingrese productos a enviar como encomienda.",Constante.ERROR);
						context.update("sms");
						context.addCallbackParam("esValido", Boolean.FALSE);
					}	
				}else{
					FacesUtils.showFacesMessage("Ingrese datos del remitente y/o destinatario.",Constante.ERROR);
					context.update("sms");
					context.addCallbackParam("esValido", Boolean.FALSE);
				}
			}else{
				FacesUtils.showFacesMessage("Seleccione una oficina.",Constante.ERROR);
				context.update("sms");
				context.addCallbackParam("esValido", Boolean.FALSE);
			}
		}else{
			FacesUtils.showFacesMessage("Seleccione un destino.",Constante.ERROR);
			context.update("sms");
			context.addCallbackParam("esValido", Boolean.FALSE);
		}
	}
	
	public void guardarEncomienda(){
		RequestContext context = RequestContext.getCurrentInstance();
		
		if(getIdDestino()!=null){
			if(this.remitente.getDni()!=null && this.destinatario1.getDni()!=null){
				if(this.listaDetalleEncomienda.size()!=0){
					try {
						//Encomienda enc = this.encomiendaServices.findLastEncomiendaByPV(usuarioLogin.getIdpuntoventa());						
						this.encomienda = new Encomienda();
						this.encomienda.setIdOrigen(this.agencia.getIdagencia()); 
						this.encomienda.setIdDestino(getIdDestino()); 
						this.encomienda.setIdOficina(getIdOficina());
						this.encomienda.setIdUsuario(usuarioLogin.getIdusuario());
						this.encomienda.setIdPuntoVentaOrigen(usuarioLogin.getIdpuntoventa());
						
						
						this.encomienda.setfRegistro(new Date());
						this.encomienda.setTipoPersona(this.remitente.getTipoPersona());
						this.encomienda.setDniRemitente(this.remitente.getDni());
						this.encomienda.setApellidosRemitente(this.remitente.getApellidos()); 
						this.encomienda.setNombresRemitente(this.remitente.getNombres()); 
						this.encomienda.setTelefonoRemitente(this.remitente.getTelefono()); 
						this.encomienda.setDireccionRemitente(this.remitente.getDireccion());
						if(this.remitente.getTipoPersona().equals("J")){
							this.encomienda.setRucRemitente(this.remitente.getEmpresa().getRUC()); 
							this.encomienda.setRazonSocialRemitente(this.remitente.getEmpresa().getRazonSocial()); 
							this.encomienda.setComprobante(this.puntoVenta.getSerieFactura()+"-" + this.puntoVenta.getUltimaFactura());
							this.encomienda.setTipoDocumento("FACTURA");

						}else{
							this.encomienda.setTipoDocumento("BOLETA");
							this.encomienda.setComprobante(this.puntoVenta.getSerieBoleta()+"-" + this.puntoVenta.getUltimaBoleta());
						}	
						this.encomienda.setDniDestinatario1(this.destinatario1.getDni());
						this.encomienda.setApellidosDestinatario1(this.destinatario1.getApellidos());
						this.encomienda.setNombresDestinatario1(this.destinatario1.getNombres()); 
						this.encomienda.setTelefonoDestinatario1(this.destinatario1.getTelefono()); 
						this.encomienda.setDireccionDestinatario1(this.destinatario1.getDireccion());
						if(this.destinatario1.getTipoPersona().equals("J")){
							this.encomienda.setRucDestinatario1(this.destinatario1.getEmpresa().getRUC());
							this.encomienda.setRazonSocialDestinatario1(this.destinatario1.getEmpresa().getRazonSocial());
						}
						
						this.encomienda.setDniDestinatario2(this.destinatario2.getDni());
						this.encomienda.setApellidosDestinatario2(this.destinatario2.getApellidos());
						this.encomienda.setNombresDestinatario2(this.destinatario2.getNombres()); 
						this.encomienda.setTelefonoDestinatario2(this.destinatario2.getTelefono()); 
						this.encomienda.setDireccionDestinatario2(this.destinatario2.getDireccion());
						if(this.destinatario2.getTipoPersona()!=null && this.destinatario2.getTipoPersona().equals("J")){
							this.encomienda.setRucDestinatario2(this.destinatario2.getEmpresa().getRUC());
							this.encomienda.setRazonSocialDestinatario2(this.destinatario2.getEmpresa().getRazonSocial());
						}	 
						this.encomienda.setTotalCobrado(getImporteTotalFactEnc());
						this.encomienda.setEstado(1); //Para indicar que esta recibido
						this.encomienda.setDireccionEnvio(getDireccionEnvio());
						this.encomienda.setIdTarifa(this.getIdTarifaReparto()); 
						this.encomienda.setPrecioReparto(getPrecioReparto()); 
						
						this.encomienda.setPesoTotal(this.pesoTotalEnc);
						if(this.bandExtraRapido){
							if(tarifaReparto.getPrecioExtraRapido()!=null){
								this.encomienda.setPrecioExtraRapido(tarifaReparto.getPrecioExtraRapido().doubleValue());
								}
						}
						if(this.bandReparto==true && this.bandContraEntrega==true){
							//ESTO ES EGRESO
							this.encomienda.setServicioEntrega("RC");
						}else{
							if(this.bandReparto){
								this.encomienda.setServicioEntrega("R");
							}else if(this.bandContraEntrega){
								this.encomienda.setServicioEntrega("C");
								//ESTO ES EGRESO
							}
						}
						
						try {
							this.encomiendaServices.crearEncomienda(encomienda);
							//Obtener el ultimo id de encomienda
							Encomienda _enco = new Encomienda(); 
							_enco =	this.encomiendaServices.findLastEncomiendaByPV(usuarioLogin.getIdpuntoventa());
							
							
							//registrando la guia de remision
							if(this.remitente.getTipoPersona().equals("J")){
								GuiaRemision guiaRemision = new GuiaRemision();
								guiaRemision.setfEmision(new Date());
								guiaRemision.setfRegistro(new Date());
								guiaRemision.setIdBus(this._idBus);
								guiaRemision.setIdOrigen(this.encomienda.getIdOrigen());
								guiaRemision.setIdDestino(this.encomienda.getIdDestino());
								guiaRemision.setNumeroGuiaCliente(this._guiaRemisionCliente);
							
								//traer la serie y guia remision
								PuntoVenta puntoV = this.puntoVentaServices.findById(usuarioLogin.getIdpuntoventa());
								guiaRemision.setSerieGuia(puntoV.getSerieGuiaRemision());
								guiaRemision.setNumeroGuia(puntoV.getUltimaGuia());
								guiaRemision.setIdEncomienda(_enco.getIdEncomienda());
								this.guiaRemisionService.crearGuiaRemision(guiaRemision);
								//actualizamos ultima guia de remision
								PuntoVenta pv = new PuntoVenta();
								pv.setIdPuntoVenta(usuarioLogin.getIdpuntoventa());
								pv.setUltimaGuia(puntoV.getUltimaGuia()+1);
								this.puntoVentaServices.actualizarUltimaGuiaRemisionPuntoVenta(pv);
								//Obteniedo la guia de remision reistrada 
								Integer idGuiaRe = this.guiaRemisionService.findLast();
								
								//registrando el detalle de la encomienda
								for(int v=0; v<listaDetalleEncomienda.size(); v++ ){
									DetalleEncomienda detEnco = this.listaDetalleEncomienda.get(v);
									DetalleGuiaRemision detGuiaRemi = new DetalleGuiaRemision();
									detGuiaRemi.setCantidad(detEnco.getCantidad());
									detGuiaRemi.setDescripcion(detEnco.getDescripcion());
									detGuiaRemi.setIdGuiaRemision(idGuiaRe);
									detGuiaRemi.setPeso(detEnco.getPeso());
									detGuiaRemi.setPrecioEnvio(detEnco.getImporte());
									this.detalleGuiaRemisionService.crearDetalleGuiaRemision(detGuiaRemi);
								}
								
								
								
							}
							
							//creando la nota de cobranza 
							if(this.formaPago.equals("C")){
								NotaCobranza notaCobranza = new NotaCobranza();
								notaCobranza.setfEmision(new Date());
								notaCobranza.setfRegistro(new Date());
								notaCobranza.setIdOrigen(this.encomienda.getIdOrigen());
								notaCobranza.setIdDestino(this.encomienda.getIdDestino());
								//traer la serie y guia remision
								PuntoVenta puntoVN = this.puntoVentaServices.findById(usuarioLogin.getIdpuntoventa());
								notaCobranza.setSerieNotaCobranza(puntoVN.getSerieNotaCobranza());
								notaCobranza.setNumeroNotaCobranza(puntoVN.getUltimaNotaCobranza());
								notaCobranza.setIdEncomienda(_enco.getIdEncomienda());
								notaCobranza.setEstado("POR PAGAR");
								this.notaCobranzaService.crearNotaCobranza(notaCobranza);
								//actualizamos ultima Nota de Cobranza
								PuntoVenta pv = new PuntoVenta();
								pv.setIdPuntoVenta(usuarioLogin.getIdpuntoventa());
								pv.setUltimaNotaCobranza(puntoVN.getUltimaNotaCobranza()+1);
								this.puntoVentaServices.actualizarUltimaNotaCobranzaPuntoVenta(pv);
								//Obteniedo la guia de remision reistrada 
								Integer idNotaCobranza = this.notaCobranzaService.findLast();
								
								//registrando el detalle de nota de cobranza
								for(int v=0; v<listaDetalleEncomienda.size(); v++ ){
									DetalleEncomienda detEnco = this.listaDetalleEncomienda.get(v);
									DetalleNotaCobranza detNotaCobranza = new DetalleNotaCobranza();
									detNotaCobranza.setCantidad(detEnco.getCantidad());
									detNotaCobranza.setDescripcion(detEnco.getDescripcion());
									detNotaCobranza.setIdNotaCobranza(idNotaCobranza);
									detNotaCobranza.setPeso(detEnco.getPeso());
									detNotaCobranza.setPrecioEnvio(detEnco.getImporte());
									this.detalleNotaCobranzaService.crearDetalleNotaCobranza(detNotaCobranza);
								}
							}
							
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
						
						
						//////Registrar el detalle de la encomienda
						Encomienda enc = this.encomiendaServices.findLastEncomiendaByPV(usuarioLogin.getIdpuntoventa());
						for(int i = 0; i<listaDetalleEncomienda.size();i++){
							DetalleEncomienda d = listaDetalleEncomienda.get(i);
							String barcodeString;
							byte[] barcode;
							if(remitente.getTipoPersona().equals("N")){
								barcode= getBarcode("B"+this.puntoVenta.getSerieBoleta()+" " + this.puntoVenta.getUltimaBoleta()+" "+ new Integer(i+1));
								barcodeString = this.puntoVenta.getSerieBoleta()+" " + this.puntoVenta.getUltimaBoleta()+" "+new Integer(i+1);
							}else{
								barcode= getBarcode("F"+this.puntoVenta.getSerieFactura()+" " + this.puntoVenta.getUltimaFactura()+" "+new Integer(i+1));
								barcodeString = this.puntoVenta.getSerieFactura()+" " + this.puntoVenta.getUltimaFactura()+" "+new Integer(i+1);
							}
							d.setCodigoBarrasString(barcodeString);
							d.setCodigoBarras(barcode);
							d.setIdEncomienda(enc.getIdEncomienda()); 
							this.detalleEncomiendaServices.crearDetalleEncomienda(d);
							DetalleEncomienda de = this.detalleEncomiendaServices.findLastDetalleEncomiendaByIdEnc(enc.getIdEncomienda());
							d.setIdDetalle(de.getIdDetalle());
 							generarCodigosBarraProductos(d); 
							
							///////Guardar cada una de las listas de subpartes de cada producto en caso tuviera
							
							Iterator it = listasSubpartes.keySet().iterator(); 
							while (it.hasNext()) {
								DetalleEncomienda clave = (DetalleEncomienda) it.next();
								if(clave.getDescripcion().equals(d.getDescripcion())){
									listaProductoDetalleEnc = (List<Producto_DetalleEnc>) listasSubpartes.get(clave);
									
									////En caso de que solo sea un producto con subpartes
									if(d.getCantidad()==1){
										for(int j=0; j<listaProductoDetalleEnc.size();j++){
											Producto_DetalleEnc prod = listaProductoDetalleEnc.get(j);									
											prod.setIdDetalle(d.getIdDetalle());
											prod.setIdEncomienda(d.getIdEncomienda()); 
											prod.setCodigoBarrasString(d.getCodigoBarrasString()+ " "+new Integer(i+1) + " " + new Integer(j+1)); 
											byte[] barcode2 = getBarcode(d.getCodigoBarrasString()+ " "+ new Integer(i+1)+" "+ new Integer(j+1)); 
											prod.setCodigoBarras(barcode2); 
											this.producto_DetalleEncServices.crearProducto_DetalleEnc(prod); 										
										}
									}else{/////En caso de que sean n productos con subpartes cada uno
										for(int k=0; k<d.getCantidad();k++){
											for(int j=0; j<listaProductoDetalleEnc.size();j++){
												Producto_DetalleEnc prod = listaProductoDetalleEnc.get(j);									
												prod.setIdDetalle(d.getIdDetalle());
												prod.setIdEncomienda(d.getIdEncomienda());
												prod.setCodigoControl(k+1); 
												prod.setCodigoBarrasString(d.getCodigoBarrasString()+ " "+new Integer(i+1) + " " + new Integer(j+1) + " " + new Integer(k+1)); 
												byte[] barcode2 = getBarcode(d.getCodigoBarrasString()+ " "+ new Integer(i+1)+" "+ new Integer(j+1)+ " " + new Integer(k+1)); 
												prod.setCodigoBarras(barcode2); 
												this.producto_DetalleEncServices.crearProducto_DetalleEnc(prod); 										
											}
										}
										
									}
								}
							}	
							
						}
						
						System.out.println("encomienda Razol Social:"+this.encomienda.getRazonSocialDestinatario1());
						System.out.println("encomienda Direccion:"+this.encomienda.getDireccionRemitente());
						System.out.println("encomienda Telefono:"+this.encomienda.getTelefonoRemitente());
												
						//////Para guardar la liquidacion////////////	
						//if(this.bandContraEntrega==false){
							registrarLiquidacion(encomienda);	
						//}
												
						//Para registrar el tracking de la encomienda					
						registrarTracking();
						//Registrar en Historial de Encomienda
						registrarHistorialEncomienda(enc);
						
						this.pesoTotalEnc = new BigDecimal(0.00);
						this.bandContraEntrega = false;
						puntoVenta = this.puntoVentaServices.findById(usuarioLogin.getIdpuntoventa());
						this.comprobante = puntoVenta.getSerieBoleta()+" - "+puntoVenta.getUltimaBoleta();
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					//pasando los datos a la cabecera y detalle del reporte;
					this.encomiendaReporte = this.encomienda;
					this.encomiendaReporte.setGuiaRemitente(this._guiaRemisionCliente);
					this._guiaRemisionCliente = "";
					this.listaRptDetalle = new ArrayList<DetalleEncomienda>();
					this.listaRptDetalle = this.listaDetalleEncomienda;
					this.subTotalRpt = this.importeTotalEnc;
					this.IGVRpt = this.importeTotalIGVEnc;
					this.TotalRpt = this.importeTotalFactEnc;
					//seteando el bus seleccionado
					if(_idBus!=null){
						this.busSeleccionado = new Programacion();
						for (int l = 0; l <this.listaBusGuia.size() ; l++) {
							Programacion bus = this.listaBusGuia.get(l);
							if(_idBus == bus.getIdBus()){
								this.busSeleccionado = bus;
								break;
							}
						}
					}
					////
					
					//////Para limpiar los campos y dejar listo para registrar otra encomienda
					limpiarCampos();
				}else{
					FacesUtils.showFacesMessage("Ingrese algun producto a enviar como encomienda.",Constante.ERROR);
					context.update("sms");
				}				
			}else{
				FacesUtils.showFacesMessage("Ingrese los datos del remitente y/o destinatario(s) correctamente.",Constante.ERROR);
				context.update("sms");
			}
			
		}else{
			FacesUtils.showFacesMessage("Seleccione un destino para la encomienda.",Constante.ERROR);
			context.update("sms");
		}
		
	}
	
	public void imprimirPDF(){
		try {
			Agencia od = this.agenciaService.findById(this.encomiendaReporte.getIdOrigen());
			this.encomiendaReporte.setDesOrigen(od.getDescripcion());
			od = this.agenciaService.findById(this.encomiendaReporte.getIdDestino());
			this.encomiendaReporte.setDesDestino(od.getDescripcion());
			
			String tipoEntrega = this.encomiendaReporte.getServicioEntrega();
			if(tipoEntrega!=null && (tipoEntrega.equals("C") || tipoEntrega.equals("RC"))){
				this.encomiendaReporte.setDniRemitente(this.encomiendaReporte.getDniDestinatario1());
				this.encomiendaReporte.setNombresRemitente(this.encomiendaReporte.getNombresDestinatario1());
				this.encomiendaReporte.setApellidosRemitente(this.encomiendaReporte.getApellidosDestinatario1());
				
				
				
				if(this.encomiendaReporte.getTipoPersona().equals("N")){
					imprimirBoleta();
				} else if(this.encomiendaReporte.getTipoPersona().equals("J")){
					imprimirFactura();
				}
				
			} else{
				if(this.encomiendaReporte.getTipoPersona().equals("N")){
					imprimirBoleta();
				} else if(this.encomiendaReporte.getTipoPersona().equals("J")){
					imprimirFactura();
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void imprimirBoleta(){
		NumeroALetra numeroALetra = new NumeroALetra();
		try {
			ServletContext servletContext = (ServletContext) (FacesContext.getCurrentInstance().getExternalContext().getContext());
			
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");		
			String fecha = formato.format(this.encomiendaReporte.getfRegistro());

			Map<String, Object> input =new  HashMap<String,Object>();
			input.put("P_REMITENTE", this.encomiendaReporte.getNombresRemitente()+" " +this.encomiendaReporte.getApellidosRemitente());
			input.put("P_DIRECCION_REMITENTE", this.encomiendaReporte.getDireccionRemitente());//PONER LA DIRECCION DEL REMITENTE
			input.put("P_TELEFONO_REMITENTE", this.encomiendaReporte.getTelefonoRemitente());
			input.put("P_DNI_REMITENTE", this.encomiendaReporte.getDniRemitente());
			input.put("P_ORIGEN", this.encomiendaReporte.getDesOrigen());
			input.put("P_FENVIO", fecha);
			input.put("P_DESTINATARIO", this.encomiendaReporte.getNombresDestinatario1()+" " + this.encomiendaReporte.getApellidosDestinatario1());
			input.put("P_DIRECENVIO", this.encomiendaReporte.getDireccionEnvio());
			input.put("P_DNIDESTINO", this.encomiendaReporte.getDniDestinatario1());
			input.put("P_DIRECCION_DESTINATARIO", this.encomiendaReporte.getDireccionDestinatario1());//PONER LA DIRECCION DEL DESTINATARIO 1
			input.put("P_TELEFONO_DESTINATARIO", this.encomiendaReporte.getTelefonoDestinatario1());
			input.put("P_TELEFONO", this.encomiendaReporte.getTelefonoDestinatario1());
			input.put("P_TOTAL_COBRADO", this.TotalRpt);
			input.put("P_TOTAL_LETRAS", "SON "+ numeroALetra.Convertir(this.TotalRpt.toString(), true, "SOLES"));
			input.put("P_DESTINO", this.encomiendaReporte.getDesDestino());
			
			//input.put("P_COMPROBANTE", this.encomiendaReporte.getComprobante());
			input.put(JRParameter.REPORT_LOCALE, new Locale("es"));
			//input.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE); // no parte la pagina todo lo mete en un A4
			
			String path = ExportarArchivo.getPath("/resources/reports/jxrml/rptBoletaEncomienda.jasper");
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			
			
			byte[] bytes;
				bytes = ExportarArchivo.exportPdf(path, input, this.listaRptDetalle);
			ExportarArchivo.executePdf(bytes, "BoletaEncomienda.pdf");
			FacesContext.getCurrentInstance().responseComplete();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	private void imprimirFactura(){
		NumeroALetra numeroALetra = new NumeroALetra();
		
		try {
			ServletContext servletContext = (ServletContext) (FacesContext.getCurrentInstance().getExternalContext().getContext());
			
			String dia="";
			String mes="";
			String anio="";
			
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");		
			String fecha = formato.format(this.encomiendaReporte.getfRegistro());
			
			String mifecha[] =  fecha.split("/");
			dia = mifecha[0];
			mes = mifecha[1];
			anio = mifecha[2];
			
			
			System.out.println("Fecha:"+this.encomiendaReporte.getfRegistro());
			System.out.println("Dia:"+this.encomiendaReporte.getfRegistro().getDay());
			System.out.println("Mes:"+this.encomiendaReporte.getfRegistro().getMonth());
			System.out.println("Anio:"+this.encomiendaReporte.getfRegistro().getYear());

			Map<String, Object> input =new  HashMap<String,Object>();
			input.put("P_REMITENTE", this.encomiendaReporte.getNombresRemitente()+" " +this.encomiendaReporte.getApellidosRemitente());
			input.put("P_DNIREMITE", this.encomiendaReporte.getDniRemitente());
			
			System.out.println("Razol Social:"+this.encomiendaReporte.getRazonSocialDestinatario1());
			System.out.println("Direccion:"+this.encomiendaReporte.getDireccionRemitente());
			System.out.println("Telefono:"+this.encomiendaReporte.getTelefonoRemitente());
			//encomiendaReporte.
			//input.put("P_", this.encomiendaReporte.getDniRemitente());
			//System.out.println();
			
			
			input.put("P_RAZON_SOCIAL", this.encomiendaReporte.getRazonSocialRemitente());
			input.put("P_DIRECCION_REMITENTE", this.encomiendaReporte.getDireccionRemitente());//PONER LA DIRECCION DEL REMITENTE
			input.put("P_TELEFONO_REMITENTE", this.encomiendaReporte.getTelefonoRemitente());
			input.put("P_ORIGEN", this.encomiendaReporte.getDesOrigen());
			
			
			
	        //System.out.println("dia:"+fecha.);
			
			input.put("P_FECHA", fecha);
			input.put("P_DIA", dia);
			input.put("P_MES", mes);
			input.put("P_ANIO", anio);
			
			input.put("P_DESTINATARIO", this.encomiendaReporte.getNombresDestinatario1()+" " + this.encomiendaReporte.getApellidosDestinatario1());
			System.out.println("Direccion Envio:"+this.encomiendaReporte.getDireccionEnvio());
			
			//input.put("P_DIRECNVIO", this.encomiendaReporte.getDireccionEnvio());
			input.put("P_DIRECNVIO", this.encomiendaReporte.getDireccionDestinatario1());
			input.put("P_TELEFONO", this.encomiendaReporte.getTelefonoDestinatario1());
			
			input.put("P_SUBTOTAL", this.subTotalRpt);
			input.put("P_IGV", this.IGVRpt);
			input.put("P_TOTAL_COBRADO", this.TotalRpt);
			
			input.put("P_TOTAL_LETRAS", "SON "+ numeroALetra.Convertir(this.TotalRpt.toString(), true, "SOLES"));
			input.put("P_DESTINO", this.encomiendaReporte.getDesDestino());
		//	input.put("P_COMPROBANTE", this.encomiendaReporte.getComprobante());
			input.put(JRParameter.REPORT_LOCALE, new Locale("es"));
			//input.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE); // no parte la pagina todo lo mete en un A4
			
			String path = ExportarArchivo.getPath("/resources/reports/jxrml/rptFacturaEncomienda.jasper");
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			
			
			byte[] bytes;
				bytes = ExportarArchivo.exportPdf(path, input, this.listaRptDetalle);
			
				// SEGUNO REPORTE
				SimpleDateFormat formato2 = new SimpleDateFormat("dd/MM/yyyy");		
				String fecha2 = formato2.format(new Date());
				
				/*SimpleDateFormat formato2 = new SimpleDateFormat("dd/MM/yyyy");		
				String fecha2 = formato2.format(new Date());*/
				
				/*System.out.println("Fecha2:"+fecha2);
				String mifecha[] =  fecha2.split("/");
				System.out.println("Dia:"+mifecha[0]);
				System.out.println("Mes:"+mifecha[1]);
				System.out.println("Anio:"+mifecha[2]);*/
				
				//Date fechaactual = new Date();
				
				System.out.println();
				
				//String dia = 
				
				Map<String, Object> input2 =new  HashMap<String,Object>();
				input2.put("P_RAZON_SOCIAL_REMITENTE", this.encomiendaReporte.getRazonSocialRemitente());//PONER LA DIRECCION DEL REMITENTE
				input2.put("P_DNI_REMITENTE", this.encomiendaReporte.getDniRemitente());//PONER LA DIRECCION DEL REMITENTE
				input2.put("P_RAZON_SOCIAL_DESTINO", this.encomiendaReporte.getRazonSocialDestinatario1());//PONER LA DIRECCION DEL REMITENTE
				input2.put("P_DNI_DESTINO", this.encomiendaReporte.getDniDestinatario1());
				
				//input2.put("P_DNI_DESTINO", this.encomiendaReporte.getDniDestinatario1());//PONER LA DIRECCION DEL REMITENTE
				//encomiendaReporte.getN
				
				input2.put("P_OFI_ORIGEN", this.encomiendaReporte.getDesOrigen());
				input2.put("P_OFI_DESTINO", this.encomiendaReporte.getDesDestino());
				input2.put("P_DEP_ORIGEN", this.encomiendaReporte.getDesOrigen());
				input2.put("P_DEP_DESTINO", this.encomiendaReporte.getDesDestino());
				System.out.println("Guia Remitente:"+this.encomiendaReporte.getGuiaRemitente());
				//encomiendaReporte.getGui
				input2.put("P_GUIA_REMITENTE", this.encomiendaReporte.getGuiaRemitente());
				
				input2.put("P_RZ_EMPRESA_RE", "ITTSA");
				input2.put("P_RUC_EMPRESA_RE", "2013227213");
				input2.put("P_RZ_EMPRESA_DES", "ITTSA");
				input2.put("P_RUC_EMPRESA_DES", "2013227213");
			    System.out.println("Placa::"+this.busSeleccionado.getPlacaBus());
				
				input2.put("P_PLACA", this.busSeleccionado.getPlacaBus());
				//input2.put("P_PLACA", "");
				input2.put("P_TARJ_CIR", "000");
				input2.put("P_LICE_CON", this.busSeleccionado.getLicPiloto()+"/"+this.busSeleccionado.getLicCopiloto());
				//input2.put("P_FECHA", fecha2);
				input2.put("P_DIA", dia);
				input2.put("P_MES", mes);
				input2.put("P_ANIO", anio);
				
				input2.put("P_TDOC", this.encomiendaReporte.getTipoDocumento());
				input2.put("P_COMPROBANTE", this.encomiendaReporte.getComprobante());
				input2.put(JRParameter.REPORT_LOCALE, new Locale("es"));
				//input.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE); // no parte la pagina todo lo mete en un A4
				
				String path2 = ExportarArchivo.getPath("/resources/reports/jxrml/rptGuiaRemisionFactura.jasper");
				HttpServletResponse response2 = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
				
				
				byte[] bytes2;
					/*for(int i=0; i<listaRptDetalle.size(); i++){
						System.out.println("Peso:"+listaRptDetalle.get(i).getPeso());
					}*/
				
					bytes2 = ExportarArchivo.exportPdf(path2, input2, this.listaRptDetalle);	
				
				byte[] byteFinal = ExportarArchivo.appendFiles(bytes, bytes2);
				
			ExportarArchivo.executePdf(byteFinal, "FacturaGuiaRemisionEnc.pdf");
			FacesContext.getCurrentInstance().responseComplete();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void limpiarCampos(){
		// 
		RequestContext context = RequestContext.getCurrentInstance();
		this.encomienda = new Encomienda();
		this.remitente = new Persona();
		this.remitente.setTipoPersona("N");
		this.remitente.setEmpresa(new Empresa());
		this.destinatario1 = new Persona();
		this.destinatario1.setTipoPersona("N");
		this.destinatario1.setEmpresa(new Empresa());
		this.destinatario2 = new Persona();
		this.destinatario2.setTipoPersona("N"); 
		this.destinatario2.setEmpresa(new Empresa());
		this.listaDetalleEncomienda = new ArrayList<>();
		this.importeTotalEnc=0.0;
		this.importeTotalIGVEnc=0.0;
		this.importeTotalFactEnc=0.0;
		this.idDestino = null;
		this.idOficina = null;
		this.formaPago="E";
		this.tipoTarjeta="V";
		this.bandReparto = Boolean.FALSE;
		this.bandCrearEncomienda = Boolean.TRUE;
		this.bandExtraRapido = false;
		this.direccionEnvio = "";
		this.listaTarifaReparto = new ArrayList<>();
		this.detallesEnc = new HashMap<Integer, Object>();
		this.montoEfectivo = 0.0;
		this.montoTarjeta = 0.0;
		this.costoDistritoReparto = 0.0;
		this.idTarifaReparto = null;
		listaProductoDetalleEnc = new ArrayList<>();
		this.lastPrecioReparto = 0.0;
		this.precioReparto = 0.0;
		this._idBus = null;
		
		context.update("formDatosRemitente");
		context.update("pt:formDatosDestinatario1");
		context.update("pt:formDatosDestinatario2");
		context.update("formDestino");
		context.update("listaDetalleEncomienda");
	}
	
	
	public void actualizarEncomienda(){
		this.encomienda.setIdOrigen(this.agencia.getIdagencia()); 
		this.encomienda.setIdDestino(getIdDestino()); 
		this.encomienda.setIdUsuario(usuarioLogin.getIdusuario());
		this.encomienda.setIdPuntoVentaOrigen(usuarioLogin.getIdpuntoventa()); 
		this.encomienda.setTipoPersona(this.remitente.getTipoPersona());
		this.encomienda.setDniRemitente(this.remitente.getDni());
		this.encomienda.setApellidosRemitente(this.remitente.getApellidos());
		this.encomienda.setNombresRemitente(this.remitente.getNombres()); 
		this.encomienda.setTelefonoRemitente(this.remitente.getTelefono()); 
		if(this.remitente.getTipoPersona().equals("J")){
			this.encomienda.setRucRemitente(this.remitente.getEmpresa().getRUC()); 
			this.encomienda.setRazonSocialRemitente(this.remitente.getEmpresa().getRazonSocial()); 
			this.encomienda.setComprobante(this.puntoVenta.getSerieFactura()+"-" + this.puntoVenta.getUltimaFactura());
			this.encomienda.setTipoDocumento("FACTURA"); 
		}else{
			this.encomienda.setTipoDocumento("BOLETA");
			this.encomienda.setComprobante(this.puntoVenta.getSerieBoleta()+"-" + this.puntoVenta.getUltimaBoleta());
		}	
		this.encomienda.setDniDestinatario1(this.destinatario1.getDni());
		this.encomienda.setApellidosDestinatario1(this.destinatario1.getApellidos()); 
		this.encomienda.setNombresDestinatario1(this.destinatario1.getNombres());
		this.encomienda.setTelefonoDestinatario1(this.destinatario1.getTelefono()); 
		if(this.destinatario1.getTipoPersona().equals("J")){
			this.encomienda.setRucDestinatario1(this.destinatario1.getEmpresa().getRUC());
			this.encomienda.setRazonSocialDestinatario1(this.destinatario1.getEmpresa().getRazonSocial()); 
		}		
		this.encomienda.setDniDestinatario2(this.destinatario2.getDni());
		this.encomienda.setApellidosDestinatario2(this.destinatario2.getApellidos()); 
		this.encomienda.setNombresDestinatario2(this.destinatario2.getNombres());
		this.encomienda.setTelefonoDestinatario2(this.destinatario2.getTelefono()); 
		if(this.destinatario2.getTipoPersona()!=null && this.destinatario2.getTipoPersona().equals("J")){
			this.encomienda.setRucDestinatario2(this.destinatario2.getEmpresa().getRUC());
			this.encomienda.setRazonSocialDestinatario2(this.destinatario2.getEmpresa().getRazonSocial());
		}	 
		this.encomienda.setTotalCobrado(getImporteTotalFactEnc()); 
		this.encomienda.setEstado(1); //Para indicar que esta recibido
		
		
		try {
			this.encomiendaServices.actualizarEncomienda(encomienda);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		limpiarCampos();
	}
	
	private double roundDecimal(double value){
		BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(2, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	public void guardarDetalleEncomienda(){
		System.out.println("entro en este metodo");
		RequestContext context = RequestContext.getCurrentInstance();
		context.addCallbackParam("esAdicional", Boolean.TRUE);
		
		
		this.countDetEnc++;
		this.detalleEncomienda = new DetalleEncomienda();
		//this.detalleEncomienda.setIdEncomienda(e.getIdEncomienda());
		this.detalleEncomienda.setTipoReparto(Boolean.FALSE);
		this.detalleEncomienda.setCantidad(getCantidad());
		this.detalleEncomienda.setDescripcion(tarifa_producto.getDescripcion());
		this.detalleEncomienda.setPrecioKilo(tarifa_producto.getPrecioKilo());
		this.detalleEncomienda.setPeso(tarifa_producto.getPeso());
		this.detalleEncomienda.setBasePago2(basePago2);
		if(this.basePago2 == 4){
			this.detalleEncomienda.setPrecioEnvio(this.precioUnit);
		}else{
			this.detalleEncomienda.setPrecioEnvio(this.tarifa_producto.getPrecioEnvio());
		}
		
		
		this.detalleEncomienda.setPrecioEnvioNormal(this.tarifaBase.getPrecioEnvioNormal()); 
		calcularImporteDetalle();
		this.detalleEncomienda.setImporte((new BigDecimal(importeDetalle)).setScale(2, RoundingMode.HALF_UP));
		this.detalleEncomienda.setNroOrden(countDetEnc); 
		
		System.out.println("Imprimimos el precio de envio:"+detalleEncomienda.getPrecioEnvio());
		
		this.listaDetalleEncomienda.add(detalleEncomienda);
		this.detallesEnc.put(countDetEnc, detalleEncomienda);
		System.out.println(" el tipo de encomienda que entra es "+ this.basePago2 );
		
		
		if(this.remitente.getTipoPersona().equals("J")){
			if(this.basePago2==4 ){
			
				System.out.println("entro en guardar detalle encomienda: persona juridica");
				System.out.println("el valor de constante IGV es "+ Constante.IGV);
				this.importeTotalFactEnc = roundDecimal(this.importeTotalFactEnc +  Double.parseDouble(this.detalleEncomienda.getImporte().toString()));
				this.importeTotalEnc =  roundDecimal(importeTotalFactEnc / (Constante.IGV+1));
				this.importeTotalIGVEnc = roundDecimal(this.importeTotalFactEnc- this.importeTotalEnc);
	
				
			}else{
				System.out.println("entro persona juridica diferente de 4");
				this.importeTotalEnc =  roundDecimal(importeTotalEnc + Double.parseDouble(this.detalleEncomienda.getImporte().toString()));
				this.importeTotalIGVEnc = roundDecimal(Constante.IGV * this.importeTotalEnc);
				this.importeTotalFactEnc = roundDecimal(this.importeTotalEnc + this.importeTotalIGVEnc);
			}

		}else if(this.remitente.getTipoPersona().equals("N") && this.bandContraEntrega==true && (this.destinatario1.getTipoPersona().equals("J") || this.destinatario2.getTipoPersona().equals("J") )){
			System.out.println("entro en esta parte revisar");
			this.importeTotalEnc =  roundDecimal(importeTotalEnc + Double.parseDouble(this.detalleEncomienda.getImporte().toString()));
			this.importeTotalIGVEnc = roundDecimal(Constante.IGV * this.importeTotalEnc);
			this.importeTotalFactEnc = roundDecimal(this.importeTotalEnc + this.importeTotalIGVEnc);
		}else{
			System.out.println("entro en persona natural");
			this.importeTotalEnc = importeTotalEnc + Double.parseDouble(this.detalleEncomienda.getImporte().toString());
			this.importeTotalFactEnc =  this.importeTotalEnc ;
		}
		this.pesoTotalEnc  =this.pesoTotalEnc.add(this.detalleEncomienda.getPeso());
		
		//precio en letras
		GenerateLetraNumber gen = new GenerateLetraNumber();
		this.totalLetras = gen.Convertir(this.importeTotalFactEnc.toString(), "SOLES", true);
		
	}

	public void editarDetalleEncomienda(){
		System.out.println("Ingreso al metodo editar detalle encomienda");
		RequestContext context = RequestContext.getCurrentInstance();
		context.addCallbackParam("esEdit", Boolean.TRUE);
		if(this.detallesEnc.size()>0){

			importeLastDetalle =Double.parseDouble(((DetalleEncomienda) this.detallesEnc.get(this.detalleEncomienda.getNroOrden())).getImporte().toString());
			System.out.println("el importe  importeLastDetalle es  " +importeLastDetalle);
			System.out.println("el importe detalle  es  " +importeDetalle);
			System.out.println("el importe total encomienda importeTotalEnc es  " +importeTotalEnc);
			System.out.println("el importe total encomienda importeTotalFactEnc es  " +importeTotalFactEnc);
			System.out.println("el importe de detalle encomienda es  " + Double.parseDouble(this.detalleEncomienda.getImporte().toString()));
			System.out.println("el que se captura en la lista es  " + Double.parseDouble(this.detalleEncomienda.getImporte().toString()));
			this.detallesEnc.remove(this.detalleEncomienda.getNroOrden());
			calcularImporteDetalle2();
			
			
			if(this.remitente.getTipoPersona().equals("J")){
				System.out.println(" ingreso masivo: persona juridica");
				
				this.importeTotalFactEnc = roundDecimal(this.importeTotalFactEnc -this.importeDetalle + this.importeLastDetalle);
				this.importeTotalEnc = roundDecimal(this.importeTotalFactEnc/ (Constante.IGV +1));
				this.importeTotalIGVEnc = roundDecimal(  this.importeTotalFactEnc-this.importeTotalEnc);
				
			}else{
				System.out.println(" ingreso masivo: persona natural");
				
				this.importeTotalEnc = importeTotalEnc + importeLastDetalle - importeDetalle ;
				this.importeTotalIGVEnc = 0.0;
				this.importeTotalFactEnc = this.importeTotalEnc;
				System.out.println(" EL TOTAL QUE DEBE MOSTRAR ES " + this.importeTotalEnc);
				System.out.println(" EL IMPORTE ES  " + Double.parseDouble(this.detalleEncomienda.getImporte().toString()));
				this.detalleEncomienda.setImporte(new BigDecimal(importeLastDetalle));
			}
		}else{
			
				if(this.remitente.getTipoPersona().equals("J")){
					System.out.println(" ingreso individual: persona juridica");
					if(this.detalleEncomienda.getBasePago2()==4){
						
						System.out.println(" ingreso a BasePago== 4 ");
					   
						System.out.println("el importe es  " +importeLastDetalle);
						System.out.println("el importe detalle es  " +importeDetalle);
						this.importeTotalFactEnc = roundDecimal(  Double.parseDouble(this.detalleEncomienda.getImporte().toString()));
						this.importeTotalEnc =  roundDecimal(importeTotalFactEnc /(Constante.IGV+1) );
						this.importeTotalIGVEnc = roundDecimal(this.importeTotalFactEnc- this.importeTotalEnc);
						
						}else {
							
							System.out.println(" ingreso a BasePago!= 4");
							
							this.importeTotalEnc = importeTotalEnc - importeLastDetalle + Double.parseDouble(this.detalleEncomienda.getImporte().toString());
							this.importeTotalIGVEnc = 0.0;
							this.importeTotalFactEnc = this.importeTotalEnc;
					    }
				}else{
					
					System.out.println(" ingreso individual: persona natural");
					System.out.println("el importe es  " +importeLastDetalle);
					System.out.println("el importe detalle es  " +importeDetalle);
					this.importeTotalEnc = importeTotalEnc - importeLastDetalle + Double.parseDouble(this.detalleEncomienda.getImporte().toString());
					this.importeTotalIGVEnc = 0.0;
					this.importeTotalFactEnc = this.importeTotalEnc;
			
			
				}
				
		}
		this.detalleEncomienda.setImporte(new BigDecimal(importeLastDetalle));
		this.detalleEncomienda.setPrecioEnvio(this.precioUnit);
		this.detallesEnc.put(this.detalleEncomienda.getNroOrden(), this.detalleEncomienda);
		context.update("listaDetalleEncomienda");
	}
	
	
	public void registrarLiquidacion(Encomienda enc){
		Liquidacion_Venta liquidacion = new Liquidacion_Venta();
		liquidacion.setDocumento(enc.getComprobante());
		if(this.remitente.getTipoPersona().equals("N")){
			liquidacion.setTipoDocumento("BOLETA"); 
			liquidacion.setSubTotal(getImporteTotalEnc()); 
			liquidacion.setIgv(0.0); 
			liquidacion.setTotal(getImporteTotalEnc()); 
			if(getFormaPago().equals("E")){
				liquidacion.setFormaPago("EFECTIVO");
				liquidacion.setTotalEfectivo(liquidacion.getTotal());
				}else if(getFormaPago().equals("T")){
					liquidacion.setFormaPago("TARJETA");
					liquidacion.setTotalTarjeta(liquidacion.getTotal());
				}else if(getFormaPago().equals("M")){
					liquidacion.setFormaPago("MIXTO");
					liquidacion.setTotalEfectivo(getMontoEfectivo()); 
					liquidacion.setTotalTarjeta(getMontoTarjeta());
				}else{
					liquidacion.setFormaPago("CREDITO");
					liquidacion.setTotalCredito(liquidacion.getTotal());
				}
			////Actualizar el numero de Boleta
			try {
				PuntoVenta pv = new PuntoVenta();
				pv.setIdPuntoVenta(usuarioLogin.getIdpuntoventa());
				pv.setUltimaBoleta(this.puntoVenta.getUltimaBoleta()+1); 
				this.puntoVentaServices.actualizarUltimaBoletaPuntoVenta(pv); 
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}else{
			liquidacion.setTipoDocumento("FACTURA"); 
			liquidacion.setSubTotal(getImporteTotalEnc()); 
			liquidacion.setIgv(Constante.IGV*getImporteTotalEnc()); 
			liquidacion.setTotal(getImporteTotalEnc() + (Constante.IGV*getImporteTotalEnc())); 
			if(getFormaPago().equals("E")){
				liquidacion.setTotalEfectivo(liquidacion.getTotal());
				liquidacion.setFormaPago("EFECTIVO");
				}else if(getFormaPago().equals("T")){
					liquidacion.setFormaPago("TARJETA");
					liquidacion.setTotalTarjeta(liquidacion.getTotal());
				}else if(getFormaPago().equals("M")){
					liquidacion.setFormaPago("MIXTO");
					liquidacion.setTotalEfectivo(getMontoEfectivo()); 
					liquidacion.setTotalTarjeta((getMontoTarjeta())); 
				}else{
					liquidacion.setFormaPago("CREDITO");
					liquidacion.setTotalCredito(liquidacion.getTotal()); 
				}
			////Actualizar el numero de Factura
			try {
				PuntoVenta pv = new PuntoVenta();
				pv.setIdPuntoVenta(usuarioLogin.getIdpuntoventa());
				pv.setUltimaFactura(this.puntoVenta.getUltimaFactura()+1); 
				this.puntoVentaServices.actualizarUltimaFacturaPuntoVenta(pv); 
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		liquidacion.setIdPuntoVenta(this.usuarioLogin.getIdpuntoventa()); 
		liquidacion.setfDocumento(new Date()); 
		liquidacion.setArea("CARGO"); 
		liquidacion.setProceso("ENCOMIENDA");
		liquidacion.setEstado(1); 
		
		if(this.bandContraEntrega==true){
			if(getFormaPago().equals("E")){
				liquidacion.setMovimientoCaja(Constante.MOVIMIENYO_CAJA_EGRESO);
			}else if(getFormaPago().equals("T")){
				liquidacion.setMovimientoCaja(Constante.MOVIMIENYO_CAJA_EGRESO);
			}else if(getFormaPago().equals("M")){
				liquidacion.setMovimientoCaja(Constante.MOVIMIENYO_CAJA_EGRESO);
			}else{
				liquidacion.setMovimientoCaja(Constante.MOVIMIENYO_CAJA_EGRESO);
			}
		}else{
			if(getFormaPago().equals("E")){
				liquidacion.setMovimientoCaja(Constante.MOVIMIENYO_CAJA_INGRESO);
			}else if(getFormaPago().equals("T")){
				liquidacion.setMovimientoCaja(Constante.MOVIMIENYO_CAJA_EGRESO);
			}else if(getFormaPago().equals("M")){
				liquidacion.setMovimientoCaja(Constante.MOVIMIENYO_CAJA_INGRESO);
			}else{
				liquidacion.setMovimientoCaja(Constante.MOVIMIENYO_CAJA_EGRESO);
			}
		}
			
		liquidacion.setIdUsuario(getUsuarioLogin().getIdusuario());
		try {
			this.liquidacion_ventaServices.crearLiquidacion_Venta(liquidacion);
			if(getFormaPago().equals("C") || this.bandContraEntrega==true){
				liquidacion.setMovimientoCaja(Constante.MOVIMIENYO_CAJA_INGRESO);
				this.liquidacion_ventaServices.crearLiquidacion_Venta(liquidacion);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void registrarTracking(){
		Encomienda enc;
		try {
			enc = this.encomiendaServices.findLastEncomiendaByPV(usuarioLogin.getIdpuntoventa());
			TrackingEncomienda t = new TrackingEncomienda();
			t.setIdEncomienda(enc.getIdEncomienda());
			t.setIdEstado(1); 
			t.setEstadoActual(Boolean.TRUE); 
			this.trackingEncomiendaServices.crearTrackingEncomienda(t);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void registrarHistorialEncomienda(Encomienda enc){
		try {
			HistorialEncomienda h = new HistorialEncomienda();
			h.setDniRemitente(enc.getDniRemitente());
			h.setApellidosRemitente(this.remitente.getApellidos()); 
			h.setNombresRemitente(this.remitente.getNombres()); 
			h.setTelefonoRemitente(enc.getTelefonoRemitente());
			h.setRucRemitente(enc.getRucRemitente()); 
			h.setRazonSocialRemitente(enc.getRazonSocialRemitente()); 
			h.setDniDestinatario1(enc.getDniDestinatario1());
			h.setApellidosDestinatario1(this.destinatario1.getApellidos()); 
			h.setNombresDestinatario1(this.destinatario1.getNombres()); 
			h.setTelefonoDestinatario1(enc.getTelefonoDestinatario1());
			h.setRucDestinatario1(enc.getRucDestinatario1());
			h.setRazonSocialDestinatario1(enc.getRazonSocialDestinatario1()); 
			h.setDniDestinatario2(enc.getDniDestinatario2());
			h.setApellidosDestinatario2(this.destinatario2.getApellidos()); 
			h.setNombresDestinatario2(this.destinatario2.getNombres()); 
			h.setTelefonoDestinatario2(enc.getTelefonoDestinatario2());
			h.setRucDestinatario2(enc.getRucDestinatario2());
			h.setRazonSocialDestinatario2(enc.getRazonSocialDestinatario2());
			h.setDireccionEnvio(enc.getDireccionEnvio()); 
			h.setIdEncomienda(enc.getIdEncomienda()); 
			this.historialEncomiendaServices.crearHistorialEncomienda(h); 			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void calcularImporteDetalle(){
		importeDetalle=0.0;
		if(this.basePago2==1){
			Double precioU = Double.parseDouble(this.tarifa_producto.getPrecioEnvio().toString());
			importeDetalle = precioU * getCantidad();
		}else if(this.basePago2==2){
			Double precioKg = Double.parseDouble(this.tarifa_producto.getPrecioKilo().toString());
			Double pesoU = Double.parseDouble(this.tarifa_producto.getPeso().toString());
			importeDetalle = precioKg * pesoU * getCantidad();
		}else
			if(this.basePago2==3){
			Double precioBase = Double.parseDouble(this.tarifaBase.getPrecioEnvioNormal().toString());
			Double precioKg = Double.parseDouble(this.tarifa_producto.getPrecioKilo().toString());
			Double pesoU = Double.parseDouble(this.tarifa_producto.getPeso().toString());
			importeDetalle = precioKg * pesoU * getCantidad() + precioBase;
		}else{
			importeDetalle= Double.parseDouble(this.tarifa_producto.getTotalPagar().toString());
		}
	}
	
	public void calcularImporteDetalle2(){
		
		if(this.detalleEncomienda.getBasePago2()==1){
			importeDetalle=0.0;
			Double precioU = Double.parseDouble(this.detalleEncomienda.getPrecioEnvio().toString());
			importeDetalle = precioU * detalleEncomienda.getCantidad();
		}else if(this.detalleEncomienda.getBasePago2()==2){
			importeDetalle=0.0;
			Double precioKg = Double.parseDouble(this.detalleEncomienda.getPrecioKilo().toString());
			Double pesoU = Double.parseDouble(this.detalleEncomienda.getPeso().toString());
			importeDetalle = precioKg * pesoU * detalleEncomienda.getCantidad();
		}else{
			
			if(this.detalleEncomienda.getBasePago2()==3){
				importeDetalle=0.0;
			Double precioBase = Double.parseDouble(this.detalleEncomienda.getPrecioEnvioNormal().toString());
			Double precioKg = Double.parseDouble(this.detalleEncomienda.getPrecioKilo().toString());
			Double pesoU = Double.parseDouble(this.detalleEncomienda.getPeso().toString());
			importeDetalle = precioKg * pesoU * detalleEncomienda.getCantidad() + precioBase;
			}else{
				
				//importeDetalle= Double.parseDouble(this.capturaEncomienda.getImporte().toString()); 
                System.out.println("el importe quee vuelve a entrar es " +importeDetalle );
			}
		}
	}
	
	public void capturaDatosEditar(DetalleEncomienda editarEncomienda){
		
		
		//DetalleEncomienda capturaEncomienda = new DetalleEncomienda(); 
		this.capturaEncomienda = editarEncomienda; 
		
		System.out.println(" el importe que inicial que capturo de la vista es " + capturaEncomienda.getImporte());
		importeDetalle= Double.parseDouble(capturaEncomienda.getImporte().toString()); 
		
	}
	
	
	public void generarCodigosBarraProductos(DetalleEncomienda det) throws Exception{	
		for(int i=0; i< det.getCantidad();i++){
			Producto_DetalleEnc producto_detalleEncomienda = new Producto_DetalleEnc();
			producto_detalleEncomienda.setIdDetalle(det.getIdDetalle());
			producto_detalleEncomienda.setIdEncomienda(det.getIdEncomienda()); 
			producto_detalleEncomienda.setCodigoBarrasString(det.getCodigoBarrasString()+ " "+new Integer(i+1)); 
			byte[] barcode = getBarcode(det.getCodigoBarrasString()+ " "+ new Integer(i+1)); 
			producto_detalleEncomienda.setCodigoBarras(barcode); 
			this.producto_DetalleEncServices.crearProducto_DetalleEnc(producto_detalleEncomienda);  
		}
		
	}
	
	public void listarDistritosReparto(){
		//this.direccionEnvio=null;
		this.idTarifaReparto=null;
		//this.bandExtraRapido= Boolean.FALSE;
		RequestContext context = RequestContext.getCurrentInstance();
		this.precioReparto = 0.0;
		if(getIdDestino()!=null){
			context.addCallbackParam("esReparto", Boolean.TRUE);
			if(bandReparto){
				try {
					this.listaTarifaReparto = this.tarifaServices.findRepartoByTipo(this.agencia.getIdagencia(), getIdDestino(), "R");
					this.tarifaReparto = new Tarifa();
					this.listaDistCateg = new ArrayList<DistritoCategoria>();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				if(this.costoDistritoReparto!=null){
					this.listaTarifaReparto = new ArrayList<>();
					if(bandExtraRapido){
						this.importeTotalEnc = this.importeTotalEnc - Double.parseDouble(this.costoDistritoReparto.toString()) - Double.parseDouble(this.tarifaReparto.getPrecioExtraRapido().toString());
					}else{
						this.importeTotalEnc = this.importeTotalEnc - Double.parseDouble(this.costoDistritoReparto.toString());
					}
					
					if(this.remitente.getTipoPersona().equals("J")){
						this.importeTotalIGVEnc = roundDecimal(Constante.IGV * this.importeTotalEnc);
						this.importeTotalFactEnc = roundDecimal(this.importeTotalEnc + this.importeTotalIGVEnc);
					}else{
						this.importeTotalIGVEnc = 0.0;
						this.importeTotalFactEnc = this.importeTotalEnc + this.importeTotalIGVEnc;
					}
					
					for(int x=0; x<this.listaDetalleEncomienda.size();x++){
						DetalleEncomienda det = listaDetalleEncomienda.get(x);
						if(det.isTipoReparto()){
							this.listaDetalleEncomienda.remove(det);
						}
					}
					
					this.tarifaReparto = new Tarifa();
					countPrecioReparto=0;
					context.addCallbackParam("esReparto", Boolean.FALSE);
					this.costoDistritoReparto = 0.0;
					this.bandExtraRapido = false;
				}
				
			}
		}else{
			context.addCallbackParam("esReparto", Boolean.FALSE);
			FacesUtils.showFacesMessage("Seleccione un destino para la encomienda.",Constante.ERROR);
			context.update("sms");
			bandReparto = Boolean.FALSE;
		}
	}
	
	public void setearBandExtraRapido(){
		if(this.tarifaReparto!=null){
			if(this.tarifaReparto.getPrecioExtraRapido()!=null){
		if(bandExtraRapido){
			if(this.tarifaReparto!=null){
				this.precioReparto = this.precioReparto + Double.parseDouble(this.tarifaReparto.getPrecioExtraRapido().toString());
				if(this.remitente.getTipoPersona().equals("J")){
					this.importeTotalEnc = this.importeTotalEnc + Double.parseDouble(this.tarifaReparto.getPrecioExtraRapido().toString());
					this.importeTotalIGVEnc = Constante.IGV * this.importeTotalEnc;
					this.importeTotalFactEnc = this.importeTotalEnc + this.importeTotalIGVEnc;
				}else{
					this.importeTotalEnc = this.importeTotalEnc + Double.parseDouble(this.tarifaReparto.getPrecioExtraRapido().toString());
					this.importeTotalIGVEnc = 0.0;
					this.importeTotalFactEnc = this.importeTotalEnc;
				}
			}
		}
		
		if(!bandExtraRapido){
			this.precioReparto = this.precioReparto - Double.parseDouble(this.tarifaReparto.getPrecioExtraRapido().toString());
			if(this.remitente.getTipoPersona().equals("J")){
				this.importeTotalEnc = this.importeTotalEnc - Double.parseDouble(this.tarifaReparto.getPrecioExtraRapido().toString());
				this.importeTotalIGVEnc = Constante.IGV * this.importeTotalEnc;
				this.importeTotalFactEnc = this.importeTotalEnc + this.importeTotalIGVEnc;
			}else{
				this.importeTotalEnc = this.importeTotalEnc - Double.parseDouble(this.tarifaReparto.getPrecioExtraRapido().toString());
				this.importeTotalIGVEnc = 0.0;
				this.importeTotalFactEnc = this.importeTotalEnc;
			}
			
		}
		
			}
		}
		
	}
	
	public void listarCategoriasReparto(){
		try {
			this.tarifaReparto = tarifaServices.findById(getIdTarifaReparto());
			//adicional
	 		this.listaDistCateg = this.distritoCategoriaService.listarCategoriaxDistrito(this.tarifaReparto.getSid_ubigeo());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void setearTarifaRepartoAsignar(){
		try {
			this.tarifaReparto = tarifaServices.findById(getIdTarifaReparto());
			countPrecioReparto++;
			Double importe_Detalles = 0.0;
			for (int i = 0; i < listaDetalleEncomienda.size(); i++) {
				importe_Detalles +=listaDetalleEncomienda.get(i).getImporte().doubleValue();
			}
			this.importeTotalEnc = importe_Detalles;
			if(bandExtraRapido){
					this.precioReparto = Double.parseDouble(this.costoDistritoReparto.toString())+ Double.parseDouble(this.tarifaReparto.getPrecioExtraRapido().toString());
					if(this.remitente.getTipoPersona().equals("J")){
						this.importeTotalIGVEnc = roundDecimal(Constante.IGV * this.importeTotalEnc);
						this.importeTotalFactEnc = roundDecimal(this.importeTotalEnc + this.importeTotalIGVEnc);
					}else{
						this.importeTotalIGVEnc = 0.0;
						this.importeTotalFactEnc = roundDecimal(this.importeTotalEnc + this.importeTotalIGVEnc);
					}
				}else{
					this.precioReparto = Double.parseDouble(this.costoDistritoReparto.toString());
					if(this.remitente.getTipoPersona().equals("J")){
						this.importeTotalIGVEnc = Constante.IGV * this.importeTotalEnc;
						this.importeTotalFactEnc = roundDecimal(this.importeTotalEnc + this.importeTotalIGVEnc);
					}else{
						this.importeTotalIGVEnc = 0.0;
						this.importeTotalFactEnc = roundDecimal(this.importeTotalEnc + this.importeTotalIGVEnc);
					}
				}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void setearTarifaReparto(){
		try {
			
			for(int i = 0; i<this.listaDistCateg.size();i++){
				DistritoCategoria dc = this.listaDistCateg.get(i);
				if(dc.getId() == this._idDistritoReparto){
					this.costoDistritoReparto = dc.getValor() + dc.getCostoServicio();
					this.precioRepartoSolo = dc.getValor();
					this.precioServicio = dc.getCostoServicio();
				}
				
			}
			
			this.tarifaReparto = tarifaServices.findById(getIdTarifaReparto());
			countPrecioReparto++;
			Double importe_Detalles = 0.0;
			for (int i = 0; i < listaDetalleEncomienda.size(); i++) {
				importe_Detalles +=listaDetalleEncomienda.get(i).getImporte().doubleValue();
			}
			this.importeTotalEnc = importe_Detalles;
			if(bandExtraRapido){
				    this.precioRepartoSolo = Double.parseDouble(this.precioRepartoSolo.toString())+ Double.parseDouble(this.tarifaReparto.getPrecioExtraRapido().toString());
					this.precioReparto = Double.parseDouble(this.costoDistritoReparto.toString())+ Double.parseDouble(this.tarifaReparto.getPrecioExtraRapido().toString());
					if(this.remitente.getTipoPersona().equals("J")){
						this.importeTotalEnc = this.importeTotalEnc + Double.parseDouble(this.costoDistritoReparto.toString())+ Double.parseDouble(this.tarifaReparto.getPrecioExtraRapido().toString());
						this.importeTotalIGVEnc = roundDecimal(Constante.IGV * this.importeTotalEnc);
						this.importeTotalFactEnc = roundDecimal(this.importeTotalEnc + this.importeTotalIGVEnc);
					}else{
						this.importeTotalEnc = this.importeTotalEnc + Double.parseDouble(this.costoDistritoReparto.toString())+ Double.parseDouble(this.tarifaReparto.getPrecioExtraRapido().toString());
						this.importeTotalIGVEnc = 0.0;
						this.importeTotalFactEnc = this.importeTotalEnc + this.importeTotalIGVEnc;
					}
				}else{
					this.precioRepartoSolo = Double.parseDouble(this.precioRepartoSolo.toString());
					this.precioReparto = Double.parseDouble(this.costoDistritoReparto.toString());
					if(this.remitente.getTipoPersona().equals("J")){
						this.importeTotalEnc = this.importeTotalEnc + Double.parseDouble(this.costoDistritoReparto.toString());
						this.importeTotalIGVEnc = Constante.IGV * this.importeTotalEnc;
						this.importeTotalFactEnc = this.importeTotalEnc + this.importeTotalIGVEnc;
					}else{
						this.importeTotalEnc = this.importeTotalEnc + Double.parseDouble(this.costoDistritoReparto.toString());
						this.importeTotalIGVEnc = 0.0;
						this.importeTotalFactEnc = this.importeTotalEnc + this.importeTotalIGVEnc;
					}
				}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void descontarTarifaReparto(){
//		this.bandReparto = Boolean.FALSE;
//		
//		if(bandExtraRapido){
//			this.importeTotalEnc = this.importeTotalEnc - Double.parseDouble(this.costoDistritoReparto.toString())- Double.parseDouble(this.tarifaReparto.getPrecioExtraRapido().toString());
//		}else{
//			this.importeTotalEnc = this.importeTotalEnc - Double.parseDouble(this.costoDistritoReparto.toString());
//		}
//		
//		
//		
//		
//		this.importeTotalFactEnc = this.importeTotalEnc;
//		
//		this.idTarifaReparto = null;
		this.bandReparto = Boolean.FALSE;
		//this.importeTotalEnc = this.importeTotalEnc - Double.parseDouble(this.lastPrecioReparto.toString());
		
		if(bandExtraRapido){
			if(this.remitente.getTipoPersona().equals("J")){
				this.importeTotalEnc = this.importeTotalEnc - Double.parseDouble(this.costoDistritoReparto.toString())- Double.parseDouble(this.tarifaReparto.getPrecioExtraRapido().toString());
				this.importeTotalIGVEnc = roundDecimal(Constante.IGV * this.importeTotalEnc);
				this.importeTotalFactEnc = roundDecimal(this.importeTotalEnc + this.importeTotalIGVEnc);
			}else{
				this.importeTotalEnc = this.importeTotalEnc - Double.parseDouble(this.costoDistritoReparto.toString())- Double.parseDouble(this.tarifaReparto.getPrecioExtraRapido().toString());
				this.importeTotalIGVEnc = 0.0;
				this.importeTotalFactEnc = this.importeTotalEnc + this.importeTotalIGVEnc;
			}
			
			
		}else{
			if(this.remitente.getTipoPersona().equals("J")){
				this.importeTotalEnc = this.importeTotalEnc - Double.parseDouble(this.costoDistritoReparto.toString());
				this.importeTotalIGVEnc = roundDecimal(Constante.IGV * this.importeTotalEnc);
				this.importeTotalFactEnc = roundDecimal(this.importeTotalEnc + this.importeTotalIGVEnc);
			}else{
				this.importeTotalEnc = this.importeTotalEnc - Double.parseDouble(this.costoDistritoReparto.toString());
				this.importeTotalIGVEnc = 0.0;
				this.importeTotalFactEnc = this.importeTotalEnc + this.importeTotalIGVEnc;
			}
			
			
		}
		this.precioReparto = 0.0;
		this.idTarifaReparto = null;
		this.bandExtraRapido = false;
		this.costoDistritoReparto =null;
		
	}
	
	
	public void setearDetalleEncomienda(DetalleEncomienda det){		
		this.detEncomiendaCompuesto = det;
		System.out.println("Detalle Encomienda: " + detEncomiendaCompuesto.getDescripcion());
		this.producto_detalleEnc = new Producto_DetalleEnc();
		this.listaProductoDetalleEnc = new ArrayList<>();
		this.countSubpartesProducto = 0;
		
		Iterator it = listasSubpartes.keySet().iterator(); 
		while (it.hasNext()) {
			DetalleEncomienda clave = (DetalleEncomienda) it.next();
			if(clave.getDescripcion().equals(det.getDescripcion())){
				listaProductoDetalleEnc = (List<Producto_DetalleEnc>) listasSubpartes.get(clave);
			}
		}	
	}
	
	public void agregarDetalle_ProductoDetalleEnc(){
		this.countSubpartesProducto++;
		this.producto_detalleEnc.setIdDetalle(detEncomiendaCompuesto.getIdDetalle()); 
		this.producto_detalleEnc.setIdEncomienda(detEncomiendaCompuesto.getIdEncomienda()); 
		this.producto_detalleEnc.setIdCompuesto(countSubpartesProducto); 
		this.listaProductoDetalleEnc.add(this.producto_detalleEnc);
		this.producto_detalleEnc = new Producto_DetalleEnc();
		
	}
	
	public void eliminarSubParte(Producto_DetalleEnc p){
		this.listaProductoDetalleEnc.remove(p.getIdCompuesto()-1);
	}
	
	
	public void guardarSubpartesProductoTemporal(){
		listasSubpartes.put(detEncomiendaCompuesto, listaProductoDetalleEnc);
	}
	
	private byte[]  getBarcode(String codigo) throws OutputException, BarcodeException, IOException {  
		StreamedContent barcode;
		File barcodeFile = new File(codigo);  
        BarcodeImageHandler.saveJPEG(BarcodeFactory.createCode128(codigo), barcodeFile);  
        barcode = new DefaultStreamedContent(new FileInputStream(barcodeFile), "image/jpeg");  
        
        /********Luego Eliminar**********/
        InputStream is = barcode.getStream();
        byte[] bytes = IOUtils.toByteArray(is);
        /*
        OutputStream out = new FileOutputStream("D:\\Diana\\"+codigo+".jpg");
        out.write(bytes);
        out.flush();
        out.close();
        */
        barcodeFile.deleteOnExit();
		return bytes;
	}
	
	public void eliminarDetalleEncomienda(){
		RequestContext context = RequestContext.getCurrentInstance();
		System.out.println("Ingreso al metodo ELIMINAR encomienda");
			if(this.detallesEnc.size()>0){

				importeLastDetalle =Double.parseDouble(((DetalleEncomienda) this.detallesEnc.get(this.detalleEncomienda.getNroOrden())).getImporte().toString());
				System.out.println("el importe  importeLastDetalle es  " +importeLastDetalle);
				System.out.println("el importe detalle  es  " +importeDetalle);
				System.out.println("el importe total encomienda importeTotalEnc es  " +importeTotalEnc);
				System.out.println("el importe total encomienda importeTotalFactEnc es  " +importeTotalFactEnc);
				System.out.println("el importe de detalle encomienda es  " + Double.parseDouble(this.detalleEncomienda.getImporte().toString()));
				System.out.println("el que se captura en la lista es  " + Double.parseDouble(this.detalleEncomienda.getImporte().toString()));
				this.detallesEnc.remove(this.detalleEncomienda.getNroOrden());
				calcularImporteDetalle2();
				this.detalleEncomienda.setImporte(new BigDecimal(importeDetalle));
				this.detallesEnc.put(this.detalleEncomienda.getNroOrden(), this.detalleEncomienda);
				
				if(this.remitente.getTipoPersona().equals("J")){
					System.out.println(" ingreso masivo: persona juridica");
					
					this.importeTotalFactEnc = roundDecimal(this.importeTotalFactEnc - this.importeLastDetalle);
					this.importeTotalEnc = roundDecimal(this.importeTotalFactEnc/ (Constante.IGV +1));
					this.importeTotalIGVEnc = roundDecimal(  this.importeTotalFactEnc-this.importeTotalEnc);
					
				}else{
					System.out.println(" ingreso masivo: persona natural");
					
					this.importeTotalFactEnc = roundDecimal(this.importeTotalFactEnc - this.importeLastDetalle);
					//this.importeTotalFactEnc = roundDecimal(this.importeTotalFactEnc - this.importeTotalEnc);
					this.importeTotalEnc = roundDecimal(this.importeTotalFactEnc);
					this.importeTotalIGVEnc = 0.0;
		
				}
			}else{
				
					if(this.remitente.getTipoPersona().equals("J")){
						System.out.println(" ingreso individual: persona juridica");
						if(this.detalleEncomienda.getBasePago2()==4){
							
							System.out.println(" ingreso a BasePago== 4 ");
						   
							System.out.println("el importe es  " +importeLastDetalle);
							System.out.println("el importe detalle es  " +importeDetalle);
							this.importeTotalFactEnc = roundDecimal( this.importeTotalFactEnc-this.importeTotalFactEnc);
							this.importeTotalEnc =  roundDecimal(importeTotalFactEnc /(Constante.IGV+1) );
							this.importeTotalIGVEnc = roundDecimal(this.importeTotalFactEnc- this.importeTotalEnc);
							
							}else {
								
								System.out.println(" ingreso a BasePago!= 4");
								
								this.importeTotalFactEnc = this.importeTotalFactEnc - importeLastDetalle ;
								this.importeTotalIGVEnc = 0.0;
								this.importeTotalEnc = this.importeTotalFactEnc;
						    }
					}else{
						
						System.out.println(" ingreso individual: persona natural");
						this.importeTotalEnc = roundDecimal(this.importeTotalEnc - Double.parseDouble(this.detalleEncomienda.getImporte().toString()));
						//this.importeTotalFactEnc = roundDecimal(this.importeTotalFactEnc - this.importeTotalEnc);
						this.importeTotalFactEnc = roundDecimal(this.importeTotalEnc);
				
					}
					
			}
			
		
		this.detallesEnc.remove(this.detalleEncomienda.getNroOrden());
		this.listaDetalleEncomienda = new ArrayList<>();
		Iterator it = detallesEnc.keySet().iterator(); 
		while (it.hasNext()) {
			Integer clave = (Integer) it.next();
			listaDetalleEncomienda.add((DetalleEncomienda) detallesEnc.get(clave));
		}	
		context.update("listaDetalleEncomienda");
		
	}
	
	
	
	
	
	public void listarEncomiendas(){
		try {
			this.listaEncomienda = this.encomiendaServices.findAll();
			for(Encomienda e : listaEncomienda){
				SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");		
				String fRegistro = formato.format(e.getfRegistro());
				e.setfRegistroString(fRegistro); 
				Agencia origen = this.agenciaService.findById(e.getIdOrigen());
				e.setOrigenString(origen.getDescripcion());
				Agencia destino = this.agenciaService.findById(e.getIdDestino());
				e.setDestinoString(destino.getDescripcion()); 
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void setearEncomienda(Encomienda e){
		
		RequestContext context = RequestContext.getCurrentInstance();
		this.encomienda = e;
		try {
			this.agencia.setIdagencia(e.getIdOrigen()); 
			this.idDestino = e.getIdDestino(); 
			this.encomienda.setIdUsuario(e.getIdUsuario());
			this.encomienda.setIdPuntoVentaOrigen(e.getIdPuntoVentaOrigen()); 
			this.encomienda.setfRegistro(e.getfRegistro());
			this.remitente.setTipoPersona(e.getTipoPersona());
			this.remitente.setDni(e.getDniRemitente());
			this.remitente.setApellidos(e.getApellidosRemitente()); 
			this.remitente.setNombres(e.getNombresRemitente()); 
			this.remitente.setTelefono(e.getTelefonoRemitente());
			if(e.getRucRemitente()!=null){
				Empresa er = new Empresa();
				er.setRUC(e.getRucRemitente()); 
				er.setRazonSocial(e.getRazonSocialRemitente()); 
				this.remitente.setEmpresa(er); 
				Liquidacion_Venta l = this.liquidacion_ventaServices.findByDocumento(encomienda.getComprobante());
				if(l!=null){
					this.importeTotalFactEnc = l.getTotal();
					this.importeTotalIGVEnc = l.getIgv();
					this.importeTotalEnc = l.getSubTotal();
				}		
			}else{
				this.importeTotalFactEnc = Double.parseDouble(e.getTotalCobrado().toString());
				this.importeTotalIGVEnc = 0.0;
				this.importeTotalEnc = this.importeTotalFactEnc;
			}
			this.setComprobante(e.getComprobante()); 
			
			this.destinatario1.setDni(e.getDniDestinatario1());
			this.destinatario1.setApellidos(e.getApellidosDestinatario1());
			this.destinatario1.setNombres(e.getNombresDestinatario1()); 
			this.destinatario1.setTelefono(e.getTelefonoDestinatario1()); 
			 
			if(e.getRucDestinatario1()!=null){
				this.destinatario1.setTipoPersona("J");
				Empresa ed1 = new Empresa();
				ed1.setRUC(e.getRucDestinatario1()); 
				ed1.setRazonSocial(e.getRazonSocialDestinatario1()); 
				this.destinatario1.setEmpresa(ed1);  
			}
			
			if(e.getDniDestinatario2()!=null){
				this.destinatario2.setDni(e.getDniDestinatario2());
				this.destinatario2.setApellidos(e.getApellidosDestinatario2());
				this.destinatario2.setNombres(e.getNombresDestinatario2()); 
				this.destinatario2.setTelefono(e.getTelefonoDestinatario2()); 
				 
				if(e.getRucDestinatario2()!=null){
					this.destinatario2.setTipoPersona("J");
					Empresa ed2 = new Empresa();
					ed2.setRUC(e.getRucDestinatario2()); 
					ed2.setRazonSocial(e.getRazonSocialDestinatario2()); 
					this.destinatario2.setEmpresa(ed2);  
				}
			}
			
			if(e.getPrecioReparto()!=null && e.getPrecioReparto()>0){
				this.bandReparto = Boolean.TRUE;
				this.precioReparto = e.getPrecioReparto();
				this.direccionEnvio = e.getDireccionEnvio();
				this.idTarifaReparto = e.getIdTarifa();

				Tarifa t = this.tarifaServices.findById(e.getIdTarifa());
				if(e.getPrecioReparto() == (t.getPrecioEnvioNormal()+Double.parseDouble(t.getPrecioExtraRapido().toString()))){
						this.bandExtraRapido = Boolean.TRUE;
				}
				
			}
			
			if(e.getServicioEntrega()!= null && e.getServicioEntrega().equals("RC")){
				this.bandContraEntrega = Boolean.TRUE;
			}
			
			
			bandAnularEncomienda = Boolean.FALSE;
			this.bandRegRenderedEnc =Boolean.FALSE;  
			this.bandNuevoEncomienda = Boolean.TRUE;
			
			this.listaDetalleEncomienda = this.detalleEncomiendaServices.findByIdEncomienda(e.getIdEncomienda());
			
			
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		
		context.update("formDestino");
		context.update("formDatosRemitente");
		context.update("formDatosDestinatario1");
		context.update("formDatosDestinatario2");
		context.update("btnNuevo");
		context.update("btnRegistrar");
		context.update("btnAnular");
		context.update("btnActualizar");
		context.update("listaDetalleEncomienda");
	}
	
	public void anularEncomienda(){
		System.out.println("Encomienda: " + this.encomienda.getIdEncomienda());
		System.out.println("Comprobante Encomienda: " + this.encomienda.getComprobante());
		
		try {
			this.encomienda.setEstado(10);
			this.encomiendaServices.actualizarEstadoEncomienda(encomienda);
			Liquidacion_Venta l = this.liquidacion_ventaServices.findByDocumento(this.encomienda.getComprobante());
			l.setTotalCredito(0.0);
			l.setTotalEfectivo(0.0);
			l.setTotalTarjeta(0.0);
			this.liquidacion_ventaServices.anularLiquidacion_Venta(l); 
			limpiarCampos();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public void setearDestino(){
		RequestContext context = RequestContext.getCurrentInstance();
		this.bandRegEncomienda = Boolean.FALSE;
		context.update("btnRegistrar");
		
		Double importe_Detalles = 0.0;
		for (int i = 0; i < listaDetalleEncomienda.size(); i++) {
			if(listaDetalleEncomienda.get(i).isTipoReparto()){
				listaDetalleEncomienda.remove(listaDetalleEncomienda.get(i));
			}else{
				importe_Detalles +=listaDetalleEncomienda.get(i).getImporte().doubleValue();
			}
				
		}
		this.importeTotalEnc = importe_Detalles;
		this.costoDistritoReparto = 0.0;
		
		if(bandReparto){
			if(this.remitente.getTipoPersona().equals("J")){
				this.importeTotalIGVEnc = roundDecimal(Constante.IGV * this.importeTotalEnc);
				this.importeTotalFactEnc = roundDecimal(this.importeTotalEnc + this.importeTotalIGVEnc);
			}else{
				this.importeTotalIGVEnc = 0.0;
				this.importeTotalFactEnc = this.importeTotalEnc + this.importeTotalIGVEnc;
			}
			
			this.bandReparto = Boolean.FALSE;
		}
		
		//llenando la lista de flota 
		try {
			
			System.out.println("Agencia de Origen:"+this.agencia.getIdagencia() +"Agencia Destino:"+this.idDestino);
			
			this.listaBusGuia = this.programacionService.listaFlotaAsiganadasProgramacion(this.agencia.getIdagencia(),this.idDestino);
			
			this.listaOficinas = new ArrayList<>();
			this.listaOficinas = this.oficinaService.getOficinasxAgencia(this.idDestino);
			
			
			this.idOficina = this.listaOficinas.get(0).getIdOficina();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void consultarTracking(Encomienda enco){
		try {
			this.listaTrackEncomienda = this.encomiendaServices.listarTrackingEncomiendas(enco);
			this.encoTracking = this.listaTrackEncomienda.get(0);
			} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
	public void calculoPrecioUnit(){
		RequestContext context = RequestContext.getCurrentInstance();
		context.addCallbackParam("esEdit", Boolean.TRUE);
		this.precioUnit = this.tarifa_producto.getTotalPagar().divide(new BigDecimal(this.cantidad), 2, RoundingMode.CEILING); 

	    
	    System.out.println("el precio unitario es " + precioUnit ); 
		
		
	}

	public void calculoPrecioUnitDetalleEncomienda(){
	
		System.out.println("la cantidad es    " + this.detalleEncomienda.getCantidad() ); 
		System.out.println("en detalle encomienda es   " + this.detalleEncomienda.getImporte() );
		this.precioUnit = this.detalleEncomienda.getImporte().divide(new BigDecimal(this.cantidad), 2, RoundingMode.CEILING); 

	 //   this.precioUnit = this.tarifa_producto.getTotalPagar().divideToIntegralValue(new BigDecimal(this.cantidad)); 
	    System.out.println("apaso");
	
		System.out.println("el precio unitario es " + precioUnit ); 
	}

	

	
	
	/*############################-------setter y getter---------##############################*/

	public List<Destino> getListaAgDestinos() {
		return listaAgDestinos;
	}

	public Double getPrecioRepartoSolo() {
		return precioRepartoSolo;
	}

	public void setPrecioRepartoSolo(Double precioRepartoSolo) {
		this.precioRepartoSolo = precioRepartoSolo;
	}

	public Double getPrecioServicio() {
		return precioServicio;
	}

	public void setPrecioServicio(Double precioServicio) {
		this.precioServicio = precioServicio;
	}

	public List<Programacion> getListaBusGuia() {
		return listaBusGuia;
	}

	public void setListaBusGuia(List<Programacion> listaBusGuia) {
		this.listaBusGuia = listaBusGuia;
	}

	public Integer get_idBus() {
		return _idBus;
	}

	public void set_idBus(Integer _idBus) {
		this._idBus = _idBus;
	}

	public void setListaAgDestinos(List<Destino> listaAgDestinos) {
		this.listaAgDestinos = listaAgDestinos;
	}


	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	public Usuario getUsuarioLogin() {
		return usuarioLogin;
	}

	public void setUsuarioLogin(Usuario usuarioLogin) {
		this.usuarioLogin = usuarioLogin;
	}

	public Persona getRemitente() {
		return remitente;
	}

	public void setRemitente(Persona remitente) {
		this.remitente = remitente;
	}

	public List<TipoDocumento> getListaTipoDoc() {
		return listaTipoDoc;
	}

	public void setListaTipoDoc(List<TipoDocumento> listaTipoDoc) {
		this.listaTipoDoc = listaTipoDoc;
	}

	public Persona getDestinatario1() {
		return destinatario1;
	}

	public void setDestinatario1(Persona destinatario1) {
		this.destinatario1 = destinatario1;
	}

	public Persona getDestinatario2() {
		return destinatario2;
	}

	public void setDestinatario2(Persona destinatario2) {
		this.destinatario2 = destinatario2;
	}

	public PuntoVenta getPuntoVenta() {
		return puntoVenta;
	}

	public void setPuntoVenta(PuntoVenta puntoVenta) {
		this.puntoVenta = puntoVenta;
	}

	public List<Tarifa_Producto> getListaTarifaProducto() {
		return listaTarifaProducto;
	}

	public void setListaTarifaProducto(List<Tarifa_Producto> listaTarifaProducto) {
		this.listaTarifaProducto = listaTarifaProducto;
	}

	public List<Tarifa_Producto> getListaFilterTarifaProducto() {
		return listaFilterTarifaProducto;
	}

	public void setListaFilterTarifaProducto(
			List<Tarifa_Producto> listaFilterTarifaProducto) {
		this.listaFilterTarifaProducto = listaFilterTarifaProducto;
	}

	public Integer getIdDestino() {
		return idDestino;
	}

	public void setIdDestino(Integer idDestino) {
		this.idDestino = idDestino;
	}

	public Boolean getBandReparto() {
		return bandReparto;
	}

	public void setBandReparto(Boolean bandReparto) {
		this.bandReparto = bandReparto;
	}

	public Boolean getBandContraEntrega() {
		return bandContraEntrega;
	}

	public void setBandContraEntrega(Boolean bandContraEntrega) {
		this.bandContraEntrega = bandContraEntrega;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Tarifa_Producto getTarifa_producto() {
		return tarifa_producto;
	}

	public void setTarifa_producto(Tarifa_Producto tarifa_producto) {
		this.tarifa_producto = tarifa_producto;
	}

	public DetalleEncomienda getDetalleEncomienda() {
		return detalleEncomienda;
	}

	public void setDetalleEncomienda(DetalleEncomienda detalleEncomienda) {
		this.detalleEncomienda = detalleEncomienda;
	}

	public List<DetalleEncomienda> getListaDetalleEncomienda() {
		return listaDetalleEncomienda;
	}

	public void setListaDetalleEncomienda(
			List<DetalleEncomienda> listaDetalleEncomienda) {
		this.listaDetalleEncomienda = listaDetalleEncomienda;
	}

	public List<DetalleEncomienda> getListaFilterDetalleEncomienda() {
		return listaFilterDetalleEncomienda;
	}

	public void setListaFilterDetalleEncomienda(
			List<DetalleEncomienda> listaFilterDetalleEncomienda) {
		this.listaFilterDetalleEncomienda = listaFilterDetalleEncomienda;
	}

	public Encomienda getEncomienda() {
		return encomienda;
	}

	public void setEncomienda(Encomienda encomienda) {
		this.encomienda = encomienda;
	}

	public Boolean getBandCrearEncomienda() {
		return bandCrearEncomienda;
	}

	public void setBandCrearEncomienda(Boolean bandCrearEncomienda) {
		this.bandCrearEncomienda = bandCrearEncomienda;
	}

	public String getBasePago() {
		return basePago;
	}

	public void setBasePago(String basePago) {
		this.basePago = basePago;
	}

	public Integer getBasePago2() {
		return basePago2;
	}

	public void setBasePago2(Integer basePago2) {
		this.basePago2 = basePago2;
	}

	public Producto_DetalleEnc getProducto_detalleEnc() {
		return producto_detalleEnc;
	}

	public void setProducto_detalleEnc(Producto_DetalleEnc producto_detalleEnc) {
		this.producto_detalleEnc = producto_detalleEnc;
	}

	public Double getImporteTotalEnc() {
		return importeTotalEnc;
	}

	public void setImporteTotalEnc(Double importeTotalEnc) {
		this.importeTotalEnc = importeTotalEnc;
	}

	public List<Tarifa> getListaTarifaReparto() {
		return listaTarifaReparto;
	}

	public void setListaTarifaReparto(List<Tarifa> listaTarifaReparto) {
		this.listaTarifaReparto = listaTarifaReparto;
	}

	public String getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}

	public String getTipoTarjeta() {
		return tipoTarjeta;
	}

	public void setTipoTarjeta(String tipoTarjeta) {
		this.tipoTarjeta = tipoTarjeta;
	}

	public Double getLastPrecioReparto() {
		return lastPrecioReparto;
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

	public void setLastPrecioReparto(Double lastPrecioReparto) {
		this.lastPrecioReparto = lastPrecioReparto;
	}

	public Integer getCountPrecioReparto() {
		return countPrecioReparto;
	}

	public void setCountPrecioReparto(Integer countPrecioReparto) {
		this.countPrecioReparto = countPrecioReparto;
	}

	public Boolean getBandExtraRapido() {
		return bandExtraRapido;
	}

	public void setBandExtraRapido(Boolean bandExtraRapido) {
		this.bandExtraRapido = bandExtraRapido;
	}

	public Tarifa getTarifaReparto() {
		return tarifaReparto;
	}

	public void setTarifaReparto(Tarifa tarifaReparto) {
		this.tarifaReparto = tarifaReparto;
	}

	public Integer getIdTarifaReparto() {
		return idTarifaReparto;
	}

	public void setIdTarifaReparto(Integer idTarifaReparto) {
		this.idTarifaReparto = idTarifaReparto;
	}

	public Double getPrecioReparto() {
		return precioReparto;
	}

	public void setPrecioReparto(Double precioReparto) {
		this.precioReparto = precioReparto;
	}

	public String getDireccionEnvio() {
		return direccionEnvio;
	}

	public void setDireccionEnvio(String direccionEnvio) {
		this.direccionEnvio = direccionEnvio;
	}

	public Double getImporteDetalle() {
		return importeDetalle;
	}

	public void setImporteDetalle(Double importeDetalle) {
		this.importeDetalle = importeDetalle;
	}

	public Tarifa getTarifaBase() {
		return tarifaBase;
	}

	public void setTarifaBase(Tarifa tarifaBase) {
		this.tarifaBase = tarifaBase;
	}

	public List<HistorialEncomienda> getListaHistorial() {
		return listaHistorial;
	}

	public void setListaHistorial(List<HistorialEncomienda> listaHistorial) {
		this.listaHistorial = listaHistorial;
	}
	

	public String getaPaternoDestBusqueda() {
		return aPaternoDestBusqueda;
	}

	public void setaPaternoDestBusqueda(String aPaternoDestBusqueda) {
		this.aPaternoDestBusqueda = aPaternoDestBusqueda;
	}

	public String getaMaternoDestBusqueda() {
		return aMaternoDestBusqueda;
	}

	public void setaMaternoDestBusqueda(String aMaternoDestBusqueda) {
		this.aMaternoDestBusqueda = aMaternoDestBusqueda;
	}

	public List<Persona> getListaPosiblesDestinatarios() {
		return listaPosiblesDestinatarios;
	}

	public void setListaPosiblesDestinatarios(
			List<Persona> listaPosiblesDestinatarios) {
		this.listaPosiblesDestinatarios = listaPosiblesDestinatarios;
	}

	public String getCondBusqDestinatario() {
		return condBusqDestinatario;
	}

	public void setCondBusqDestinatario(String condBusqDestinatario) {
		this.condBusqDestinatario = condBusqDestinatario;
	}

	public DetalleEncomienda getDetEncomiendaCompuesto() {
		return detEncomiendaCompuesto;
	}

	public void setDetEncomiendaCompuesto(DetalleEncomienda detEncomiendaCompuesto) {
		this.detEncomiendaCompuesto = detEncomiendaCompuesto;
	}

	public List<Producto_DetalleEnc> getListaProductoDetalleEnc() {
		return listaProductoDetalleEnc;
	}

	public void setListaProductoDetalleEnc(
			List<Producto_DetalleEnc> listaProductoDetalleEnc) {
		this.listaProductoDetalleEnc = listaProductoDetalleEnc;
	}

	public Integer getCountSubpartesProducto() {
		return countSubpartesProducto;
	}

	public void setCountSubpartesProducto(Integer countSubpartesProducto) {
		this.countSubpartesProducto = countSubpartesProducto;
	}

	public Map<DetalleEncomienda, Object> getListasSubpartes() {
		return listasSubpartes;
	}

	public void setListasSubpartes(Map<DetalleEncomienda, Object> listasSubpartes) {
		this.listasSubpartes = listasSubpartes;
	}

	public Integer getCountDetEnc() {
		return countDetEnc;
	}

	public void setCountDetEnc(Integer countDetEnc) {
		this.countDetEnc = countDetEnc;
	}

	public Map<Integer, Object> getDetallesEnc() {
		return detallesEnc;
	}

	public void setDetallesEnc(Map<Integer, Object> detallesEnc) {
		this.detallesEnc = detallesEnc;
	}

	public Double getImporteLastDetalle() {
		return importeLastDetalle;
	}

	public void setImporteLastDetalle(Double importeLastDetalle) {
		this.importeLastDetalle = importeLastDetalle;
	}

	public List<Encomienda> getListaEncomienda() {
		return listaEncomienda;
	}

	public void setListaEncomienda(List<Encomienda> listaEncomienda) {
		this.listaEncomienda = listaEncomienda;
	}

	public List<Encomienda> getListaEncomiendaFilter() {
		return listaEncomiendaFilter;
	}

	public void setListaEncomiendaFilter(List<Encomienda> listaEncomiendaFilter) {
		this.listaEncomiendaFilter = listaEncomiendaFilter;
	}

	public Boolean getBandAnularEncomienda() {
		return bandAnularEncomienda;
	}

	public void setBandAnularEncomienda(Boolean bandAnularEncomienda) {
		this.bandAnularEncomienda = bandAnularEncomienda;
	}

	public String getComprobante() {
		return comprobante;
	}

	public void setComprobante(String comprobante) {
		this.comprobante = comprobante;
	}

	public Double getImporteTotalIGVEnc() {
		return importeTotalIGVEnc;
	}

	public void setImporteTotalIGVEnc(Double importeTotalIGVEnc) {
		this.importeTotalIGVEnc = importeTotalIGVEnc;
	}

	public Double getImporteTotalFactEnc() {
		return importeTotalFactEnc;
	}

	public void setImporteTotalFactEnc(Double importeTotalFactEnc) {
		this.importeTotalFactEnc = importeTotalFactEnc;
	}

	public Boolean getBandRegEncomienda() {
		return bandRegEncomienda;
	}

	public void setBandRegEncomienda(Boolean bandRegEncomienda) {
		this.bandRegEncomienda = bandRegEncomienda;
	}

	public List<DistritoCategoria> getListaDistCateg() {
		return listaDistCateg;
	}

	public void setListaDistCateg(List<DistritoCategoria> listaDistCateg) {
		this.listaDistCateg = listaDistCateg;
	}

	public Double getCostoDistritoReparto() {
		return costoDistritoReparto;
	}

	public void setCostoDistritoReparto(Double costoDistritoReparto) {
		this.costoDistritoReparto = costoDistritoReparto;
	}

	public Boolean getBandNuevoEncomienda() {
		return bandNuevoEncomienda;
	}

	public void setBandNuevoEncomienda(Boolean bandNuevoEncomienda) {
		this.bandNuevoEncomienda = bandNuevoEncomienda;
	}

	public Boolean getBandRegRenderedEnc() {
		return bandRegRenderedEnc;
	}

	public void setBandRegRenderedEnc(Boolean bandRegRenderedEnc) {
		this.bandRegRenderedEnc = bandRegRenderedEnc;
	}

	public Integer get_idDistritoReparto() {
		return _idDistritoReparto;
	}

	public void set_idDistritoReparto(Integer _idDistritoReparto) {
		this._idDistritoReparto = _idDistritoReparto;
	}

	public List<Encomienda> getListaTrackEncomienda() {
		return listaTrackEncomienda;
	}

	public void setListaTrackEncomienda(List<Encomienda> listaTrackEncomienda) {
		this.listaTrackEncomienda = listaTrackEncomienda;
	}

	public Encomienda getEncoTracking() {
		return encoTracking;
	}

	public void setEncoTracking(Encomienda encoTracking) {
		this.encoTracking = encoTracking;
	}

	public List<Encomienda> getListaTrackEncomiendaFilter() {
		return listaTrackEncomiendaFilter;
	}

	public void setListaTrackEncomiendaFilter(
			List<Encomienda> listaTrackEncomiendaFilter) {
		this.listaTrackEncomiendaFilter = listaTrackEncomiendaFilter;
	}

	public List<Persona> getListaPosiblesDestinatariosFilter() {
		return listaPosiblesDestinatariosFilter;
	}

	public void setListaPosiblesDestinatariosFilter(
			List<Persona> listaPosiblesDestinatariosFilter) {
		this.listaPosiblesDestinatariosFilter = listaPosiblesDestinatariosFilter;
	}

	public String get_guiaRemisionCliente() {
		return _guiaRemisionCliente;
	}

	public void set_guiaRemisionCliente(String _guiaRemisionCliente) {
		this._guiaRemisionCliente = _guiaRemisionCliente;
	}

	public SelectItem[] getListSINO() {
		return listSINO;
	}

	public void setListSINO(SelectItem[] listSINO) {
		this.listSINO = listSINO;
	}

	public List<HistorialEncomienda> getListaHistorialFilter() {
		return listaHistorialFilter;
	}

	public void setListaHistorialFilter(
			List<HistorialEncomienda> listaHistorialFilter) {
		this.listaHistorialFilter = listaHistorialFilter;
	}

	public Integer getIdOficina() {
		return idOficina;
	}

	public void setIdOficina(Integer idOficina) {
		this.idOficina = idOficina;
	}

	public List<Oficina> getListaOficinas() {
		return listaOficinas;
	}

	public void setListaOficinas(List<Oficina> listaOficinas) {
		this.listaOficinas = listaOficinas;
	}

	public String getTotalLetras() {
		return totalLetras;
	}

	public void setTotalLetras(String totalLetras) {
		this.totalLetras = totalLetras;
	}

	public boolean isFlgidBus() {
		return flgidBus;
	}

	public void setFlgidBus(boolean flgidBus) {
		this.flgidBus = flgidBus;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public List<Empresa> getListaEmpresasFilter() {
		return listaEmpresasFilter;
	}

	public void setListaEmpresasFilter(List<Empresa> listaEmpresasFilter) {
		this.listaEmpresasFilter = listaEmpresasFilter;
	}

	public List<Empresa> getListaEmpresas() {
		return listaEmpresas;
	}

	public void setListaEmpresas(List<Empresa> listaEmpresas) {
		this.listaEmpresas = listaEmpresas;
	}

	public String getCondBusqEmpresa() {
		return condBusqEmpresa;
	}

	public void setCondBusqEmpresa(String condBusqEmpresa) {
		this.condBusqEmpresa = condBusqEmpresa;
	}

	public BigDecimal getPrecioUnit() {
		return precioUnit;
	}

	public void setPrecioUnit(BigDecimal precioUnit) {
		this.precioUnit = precioUnit;
	}

	public Boolean getBandMismaOficina() {
		return bandMismaOficina;
	}

	public void setBandMismaOficina(Boolean bandMismaOficina) {
		this.bandMismaOficina = bandMismaOficina;
	}

	public DetalleEncomienda getCapturaEncomienda() {
		return capturaEncomienda;
	}

	public void setCapturaEncomienda(DetalleEncomienda capturaEncomienda) {
		this.capturaEncomienda = capturaEncomienda;
	}
	
	
	
}
