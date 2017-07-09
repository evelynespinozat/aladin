package com.certicom.ittsa.managedBeans;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.output.OutputException;

import org.apache.commons.io.IOUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.certicom.ittsa.domain.DetalleEncomienda;
import com.certicom.ittsa.domain.Empresa;
import com.certicom.ittsa.domain.Encomienda;
import com.certicom.ittsa.domain.Giro;
import com.certicom.ittsa.domain.HistorialEncomienda;
import com.certicom.ittsa.domain.Liquidacion_Venta;
import com.certicom.ittsa.domain.Oficina;
import com.certicom.ittsa.domain.Persona;
import com.certicom.ittsa.domain.Producto_DetalleEnc;
import com.certicom.ittsa.domain.PuntoVenta;
import com.certicom.ittsa.domain.Tarifa;
import com.certicom.ittsa.domain.Tarifa_Producto;
import com.certicom.ittsa.domain.TipoDocumento;
import com.certicom.ittsa.domain.TrackingEncomienda;
import com.certicom.ittsa.domain.TrackingGiro;
import com.certicom.ittsa.domain.Usuario;
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
import com.certicom.ittsa.services.DetalleEncomiendaServices;
import com.certicom.ittsa.services.EncomiendaServices;
import com.certicom.ittsa.services.FrecuenciaService;
import com.certicom.ittsa.services.GiroServices;
import com.certicom.ittsa.services.HistorialEncomiendaServices;
import com.certicom.ittsa.services.Liquidacion_VentaServices;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.OficinaService;
import com.certicom.ittsa.services.PersonaServices;
import com.certicom.ittsa.services.Producto_DetalleEncService;
import com.certicom.ittsa.services.ProgramacionService;
import com.certicom.ittsa.services.PuntoVentaService;
import com.certicom.ittsa.services.RutaServicioService;
import com.certicom.ittsa.services.ServicioService;
import com.certicom.ittsa.services.TarifaServices;
import com.certicom.ittsa.services.Tarifa_ProductoServices;
import com.certicom.ittsa.services.TipoDocumentoServices;
import com.certicom.ittsa.services.TrackingEncomiendaServices;
import com.certicom.ittsa.services.TrackingGiroServices;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.BarcodePDF417;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;
import com.pe.certicom.ittsa.commons.Utils;

@ManagedBean(name="giroxPagarMB")
@ViewScoped
public class GiroxPagarMB  extends GenericBeans implements Serializable{

	public GiroxPagarMB(){}
	
	@PostConstruct
	public void inicia()
	{
		
		this.totalGirosxPagar = 0.0;
		
		this.agenciaService = new AgenciaService();
		this.destinoService = new DestinoServices();
		this.giroServices = new GiroServices();
		this.liquidacionServices = new Liquidacion_VentaServices();
		this.oficinaService = new OficinaService();
		this.giroFilter = new Giro();
		
		try {
			agencia = this.agenciaService.findById(usuarioLogin.getIdagencia());
			this.listaAgDestinos = destinoService.obtenerDestino(agencia.getIdagencia());
			this.listaAgencias = this.agenciaService.listaAgenciasActivas();	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Date fechaHoy = new Date();
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");		
		this.fechaEntrega = formato.format(fechaHoy);  
	}
	
	
	public void buscarGiros(){
		try {
			this.totalGirosxPagar = 0.0;
			this.listaGiros = this.giroServices.buscarGirosxCobrar(this.giroFilter);
			for(int i = 0; i<this.listaGiros.size(); i++){
				Giro giro = this.listaGiros.get(i);
				if(giro.getEstado() == 1){
					this.totalGirosxPagar = this.totalGirosxPagar + giro.getMontoGiro();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void buscarGirosAntiguo(){
		Utils u = new Utils();	
		try {
			if(getIdOrigen()!=null){
				if(getFechaInicio()!=null && getFechaFin()!=null){
					Date fechaFinal = u.sumaDias(fechaFin, 1);
					this.listaGiros = this.giroServices.findGirosxCobrarByFecha(getIdOrigen(),getIdDestino(), getFechaInicio(), fechaFinal);
					for(Giro g: listaGiros){
						SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");		
						String fRegistro = formato.format(g.getfRegistro());
						g.setfRegistroString(fRegistro); 
					}
				}else{
					this.listaGiros = this.giroServices.findGirosxCobrarByOrig_Dest(getIdOrigen(),getIdDestino());
					for(Giro g: listaGiros){
						SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");		
						String fRegistro = formato.format(g.getfRegistro());
						g.setfRegistroString(fRegistro); 
					}
				}
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void confirmarPago(){
		this.giro.setEstado(2); 
		try {
			this.giroServices.actualizarEstadoGiro(this.giro);
			Liquidacion_Venta liquidacion = this.liquidacionServices.findByDocumento(this.giro.getDocumento());
			liquidacion.setIdPuntoVenta(this.usuarioLogin.getIdpuntoventa());
			liquidacion.setTotalEfectivo(this.giro.getMontoGiro());
			liquidacion.setMovimientoCaja(Constante.MOVIMIENYO_CAJA_EGRESO);
			this.liquidacionServices.crearLiquidacion_Venta(liquidacion);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		buscarGiros();
	}
	
	public void listarDestinosxOri() {
		try {
			// this.listaDestino = new ArrayList<Destino>();
			this.listaDestino = destinoService.obtenerDestino(this.giroFilter.getIdOrigen());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void listarOficinasxAgencia() {
		try {
			this.listaOficinas = this.oficinaService.getOficinasxAgencia(this.giroFilter.getIdDestino());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void setearGiro(Giro g){
		this.giro = g;
		//evaluando si es la oficina correcta
		if(this.giro.getIdOficina() == usuarioLogin.getIdoficina()){
			this._valida1 = true;
			this._valida2 = false;
		}else{
			this._valida1 = false;
			this._valida2 = true;
		}
	}
	
	
	
	
	/****************Atributos*******************/
	
	@ManagedProperty(value="#{loginMB.usuario}")
	private Usuario usuarioLogin;
	private Agencia agencia;
	private Giro giro;
	private Giro giroFilter;

	private Integer idOrigen;
	private Integer idDestino;
	private Date fechaInicio;
	private Date fechaFin;
	private String fechaEntrega;
	private String observacion;
	private Double totalGirosxPagar;
	private boolean _valida1;
	private boolean _valida2;
	
	private List<Destino> listaAgDestinos;
	private List<Giro> listaGiros;
	private List<Giro> listaGirosFilter;
	
	private List<Agencia> listaAgencias;
	private List<Destino> listaDestino;
	private List<Oficina> listaOficinas;
	
	
	private AgenciaService agenciaService;
	private DestinoServices destinoService;
	private GiroServices giroServices;
	private Liquidacion_VentaServices liquidacionServices;
	private OficinaService oficinaService;
	
	
	/*############################-------setter y getter---------##############################3*/
	
	public List<Destino> getListaAgDestinos() {
		return listaAgDestinos;
	}

	public boolean is_valida1() {
		return _valida1;
	}

	public void set_valida1(boolean _valida1) {
		this._valida1 = _valida1;
	}

	public boolean is_valida2() {
		return _valida2;
	}

	public void set_valida2(boolean _valida2) {
		this._valida2 = _valida2;
	}

	public Double getTotalGirosxPagar() {
		return totalGirosxPagar;
	}

	public void setTotalGirosxPagar(Double totalGirosxPagar) {
		this.totalGirosxPagar = totalGirosxPagar;
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

	

	
	public Integer getIdOrigen() {
		return idOrigen;
	}

	public void setIdOrigen(Integer idOrigen) {
		this.idOrigen = idOrigen;
	}

	public Giro getGiro() {
		return giro;
	}

	public void setGiro(Giro giro) {
		this.giro = giro;
	}
	
	public List<Giro> getListaGiros() {
		return listaGiros;
	}

	public void setListaGiros(List<Giro> listaGiros) {
		this.listaGiros = listaGiros;
	}

	public List<Giro> getListaGirosFilter() {
		return listaGirosFilter;
	}

	public void setListaGirosFilter(List<Giro> listaGirosFilter) {
		this.listaGirosFilter = listaGirosFilter;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(String fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Integer getIdDestino() {
		return idDestino;
	}

	public void setIdDestino(Integer idDestino) {
		this.idDestino = idDestino;
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

	public Giro getGiroFilter() {
		return giroFilter;
	}

	public void setGiroFilter(Giro giroFilter) {
		this.giroFilter = giroFilter;
	}

	public List<Oficina> getListaOficinas() {
		return listaOficinas;
	}

	public void setListaOficinas(List<Oficina> listaOficinas) {
		this.listaOficinas = listaOficinas;
	}
	
	
	
}
