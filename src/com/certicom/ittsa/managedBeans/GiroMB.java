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
import com.certicom.ittsa.services.EmpresaService;
import com.certicom.ittsa.services.EncomiendaServices;
import com.certicom.ittsa.services.FrecuenciaService;
import com.certicom.ittsa.services.GiroServices;
import com.certicom.ittsa.services.HistorialEncomiendaServices;
import com.certicom.ittsa.services.Liquidacion_VentaServices;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.OficinaService;
import com.certicom.ittsa.services.ParametroServices;
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
import com.pe.certicom.ittsa.commons.NumeroALetra;

@ManagedBean(name="giroMB")
@ViewScoped
public class GiroMB  extends GenericBeans implements Serializable{

	public GiroMB(){}
	
	@PostConstruct
	public void inicia()
	{
		this.empresaService = new EmpresaService();
		this.oficinaService = new OficinaService();
		this.agenciaService = new AgenciaService();
		this.destinoService = new DestinoServices();
		this.tipoDocServices = new TipoDocumentoServices();
		this.personaServives = new PersonaServices();
		this.puntoVentaServices = new PuntoVentaService();
		this.tarifa_productoServices = new Tarifa_ProductoServices();
		this.giroServices = new GiroServices();
		this.tarifaServices = new TarifaServices();
		this.liquidacion_ventaServices = new Liquidacion_VentaServices();
		this.trackingGiroServices = new TrackingGiroServices();
		this.historialEncomiendaServices = new HistorialEncomiendaServices();
		this.parametroServices = new ParametroServices();
		
		try {
			agencia = this.agenciaService.findById(usuarioLogin.getIdagencia());
			puntoVenta = this.puntoVentaServices.findById(usuarioLogin.getIdpuntoventa());
			this.documento = puntoVenta.getSerieBoleta()+" - "+puntoVenta.getUltimaBoleta();
			//this.listaAgDestinos = destinoService.obtenerDestino(agencia.getIdagencia());
			this.listaAgenciasDestinos = agenciaService.listaAgenciasActivas();
			this.listaTipoDoc = tipoDocServices.findAll();
			this.listaTarifaProducto = tarifa_productoServices.findByIdAgencia(usuarioLogin.getIdagencia());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
		
		this.bandDestino = Boolean.TRUE;
		this.tarifa = 0.0;
		this.importeIGV = 0.0;
		this.importeTotal = 0.0;
		this.bandContraEntrega = Boolean.FALSE;
		this.formaPago = "E";
		this.giro= new Giro();
		this.tarifaBase = new Tarifa();
	}
	
	public void optenerOficinas(){
		try {
			this.listaOficinas = new ArrayList<>();
			this.listaOficinas = this.oficinaService.getOficinasxAgencia(this.idDestino);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void buscarEmpresaGiro(String val){
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
	
	public void setearDestino(){
		this.bandDestino = Boolean.FALSE;
	}
	
	public void calcularTarifa(){
		try {
			RequestContext context = RequestContext.getCurrentInstance();
			System.out.println("Monto Ingresado: " + this.giro.getMontoGiro());
			String sMonto = parametroServices.findParametro_byNombre("MONTOGIROMAX");
			Double dMonto = new Double(sMonto);
			if(this.giro.getMontoGiro()<=dMonto){
	
				List<Tarifa> listaTarifas = this.tarifaServices.findGirosByTipo(this.agencia.getIdagencia(), "G");
				for(Tarifa t : listaTarifas){
					if(this.giro.getMontoGiro()<= Double.parseDouble(t.getRangoFin().toString()) && this.giro.getMontoGiro()> Double.parseDouble(t.getRangoInicio().toString())){
						this.tarifaBase = t;
					}
				}
					
				if(this.tarifaBase.getPorcentual()){
					if(this.remitente.getTipoPersona().equals("J")){ 
						this.tarifa = this.tarifaBase.getPrecioEnvioNormal()/100 * this.giro.getMontoGiro();
						this.importeIGV = this.tarifa * Constante.IGV;
						this.importeIGV = Math.round(importeIGV*Math.pow(10,2))/Math.pow(10,2);
						this.importeTotal = this.tarifa + this.importeIGV;
					}else{
						this.tarifa = this.tarifaBase.getPrecioEnvioNormal()/100 * this.giro.getMontoGiro();
						this.importeIGV = 0.0;
						this.importeTotal = this.tarifa + this.importeIGV;
					}
						
				}else{
					if(this.remitente.getTipoPersona().equals("J")){ 
						this.tarifa = this.tarifaBase.getPrecioEnvioNormal();
						this.importeIGV = this.tarifa * Constante.IGV;
						this.importeIGV = Math.round(importeIGV*Math.pow(10,2))/Math.pow(10,2);
						this.importeTotal = this.tarifa + this.importeIGV;
					}else{
						this.tarifa = this.tarifaBase.getPrecioEnvioNormal();
						this.importeIGV = 0.0;
						this.importeTotal = this.tarifa + this.importeIGV;
						this.importeTotal.intValue();
					}
				}
	
			}else{
				FacesUtils.showFacesMessage("El monto no puede exceder los S/. "+sMonto+" ("+NumeroALetra.Convertir(sMonto, true, "NUEVOS SOLES")+").",Constante.ERROR);
				//TODO los 5000 debe ser un parámetro configurable. No colocarlo en duro.
				context.update("sms");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void calcularImporteTotal(){
		if(this.remitente.getTipoPersona().equals("J")){ 
			//this.tarifa = this.tarifaBase.getPrecioEnvioNormal()/100 * this.giro.getMontoGiro();
			this.importeIGV = getTarifa() * Constante.IGV;
			this.importeIGV = Math.round(importeIGV*Math.pow(10,2))/Math.pow(10,2);
			this.importeTotal = this.tarifa + this.importeIGV;
		}else{
			//this.tarifa = this.tarifaBase.getPrecioEnvioNormal()/100 * this.giro.getMontoGiro();
			this.importeIGV = 0.0;
			this.importeTotal = getTarifa() + this.importeIGV;
		}
	}
	
	public  int roundDecimaltoInt(double value,int decimalPlaces){
		BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(decimalPlaces, RoundingMode.HALF_UP);
	    return bd.intValue();
	}
	
	public void setearTipoPersona(){
		System.out.println("Tipo Persona Remitente: "+ this.remitente.getTipoPersona());
		if(this.remitente.getTipoPersona().equals("J")){
			this.documento = this.puntoVenta.getSerieFactura()+" - "+this.puntoVenta.getUltimaFactura();
			if(this.importeIGV==0){
				
				this.importeIGV = Constante.IGV * this.tarifa;
				this.importeIGV = Math.round(importeIGV*Math.pow(10,2))/Math.pow(10,2);
				this.importeTotal = this.tarifa + this.importeIGV;
			}
		}else{
			this.documento = puntoVenta.getSerieBoleta()+" - "+puntoVenta.getUltimaBoleta();
			this.importeIGV = 0.0;
			this.importeTotal = this.tarifa;
		}
	}
	
	public void buscarPersona(String busq){
		RequestContext context = RequestContext.getCurrentInstance(); 
		try {
			if(busq.equals("R")){
				//listaHistorial = this.historialEncomiendaServices.findByDNIRemitente(this.remitente.getDni());
				//if(listaHistorial.size()==0){
					Persona p = personaServives.findByNroDocumento(this.remitente.getDni());
					if(p!=null){
						this.remitente.setApellidos(p.getAPaterno()+" "+ p.getAMaterno());
						this.remitente.setNombres(p.getNombres()); 
					}
				/*}else{
					for(HistorialEncomienda h : listaHistorial){
						
						if(h.getRucRemitente()!=null){
							this.documento = this.puntoVenta.getSerieFactura()+" - "+this.puntoVenta.getUltimaFactura();
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
				}*/		
				
			}else if(busq.equals("D1")){
				Persona p = personaServives.findByNroDocumento(this.destinatario1.getDni());
				if(p!=null){
					this.destinatario1.setApellidos(p.getAPaterno()+" "+ p.getAMaterno());
					this.destinatario1.setNombres(p.getNombres()); 
				}
			}else{
				Persona p = personaServives.findByNroDocumento(this.destinatario2.getDni()); 
				if(p!=null){
					this.destinatario2.setApellidos(p.getAPaterno()+" "+ p.getAMaterno());
					this.destinatario2.setNombres(p.getNombres()); 
				}
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void nuevaBusquedaDestinatario(String dest){
		System.out.println("Entro a limpiar la nueva busqueda");		
		this.condBusqDestinatario = dest;
		
		this.aPaternoDestBusqueda = "";
		this.aMaternoDestBusqueda = "";
		this.listaPosiblesDestinatarios = new ArrayList<>();
	}
	
	public void buscarDestinatario(){
		try {
			if(!getaPaternoDestBusqueda().equals("") && !getaMaternoDestBusqueda().equals("")){
				this.listaPosiblesDestinatarios = this.personaServives.findByApellidos(getaPaternoDestBusqueda().toUpperCase(), getaMaternoDestBusqueda().toUpperCase());
			}else if (!getaPaternoDestBusqueda().equals("") && getaMaternoDestBusqueda().equals("")){
				this.listaPosiblesDestinatarios = this.personaServives.findByApellidoPaterno(getaPaternoDestBusqueda());
			}else if (getaPaternoDestBusqueda().equals("") && !getaMaternoDestBusqueda().equals("")){
				this.listaPosiblesDestinatarios = this.personaServives.findByApellidoMaterno(getaMaternoDestBusqueda()); 
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
	
	public void asignarDatos(HistorialEncomienda h){
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
		if(h.getRucRemitente()!=null){
			this.remitente.setTipoPersona("J"); 
		}
	}
	
	public void listarGiros(){
		try {
			//this.listaGiros = this.giroServices.girosxCobrar();
			this.listaGiros = this.giroServices.findAll();
			for(Giro e : listaGiros){
				SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");		
				String fRegistro = formato.format(e.getfRegistro());
				e.setfRegistroString(fRegistro); 
				Agencia origen = this.agenciaService.findById(e.getIdOrigen());
				e.setOrigenString(origen.getDescripcion());
				Agencia destino = this.agenciaService.findById(e.getIdDestino());
				e.setDestinoString(destino.getDescripcion()); 
				//obteniedo la oficina
				Oficina oficina = this.oficinaService.findById(e.getIdOficina());
				if(oficina != null){
					e.setDesOficina(oficina.getNombre());
				}
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void verificarDatosIngreso(){
		RequestContext context = RequestContext.getCurrentInstance();  
		if(getIdDestino()!=null){
			if(this.remitente.getDni()!=null && this.destinatario1.getDni()!=null){
				context.addCallbackParam("esValido", Boolean.TRUE );
				
			}else{
				FacesUtils.showFacesMessage("Ingrese datos del remitente y/o destinatario.",Constante.ERROR);
				context.update("sms");
				context.addCallbackParam("esValido", Boolean.FALSE);
			}			
		}else{
			FacesUtils.showFacesMessage("Seleccione un destino.",Constante.ERROR);
			context.update("sms");
			context.addCallbackParam("esValido", Boolean.FALSE);
		}
	}
	
	public void guardarGiro(){
		RequestContext context = RequestContext.getCurrentInstance();
		
		if(getIdDestino()!=null){
			if(this.remitente.getDni()!=null && this.destinatario1.getDni()!=null){
					try {
						//Encomienda enc = this.encomiendaServices.findLastEncomiendaByPV(usuarioLogin.getIdpuntoventa());						
						
						//this.giro = new Giro();
						this.giro.setIdOrigen(this.agencia.getIdagencia()); 
						this.giro.setIdDestino(getIdDestino());
						this.giro.setIdOficina(getIdOficina());
						this.giro.setIdUsuario(usuarioLogin.getIdusuario());
						this.giro.setIdPuntoVentaOrigen(usuarioLogin.getIdpuntoventa()); 
						this.giro.setfRegistro(new Date());
						this.giro.setTipoPersona(this.remitente.getTipoPersona());
						this.giro.setDniRemitente(this.remitente.getDni());
						this.giro.setApellidosRemitente(this.remitente.getApellidos()); 
						this.giro.setNombresRemitente(this.remitente.getNombres());
						this.giro.setTelefonoRemitente(this.remitente.getTelefono()); 
						if(this.remitente.getTipoPersona().equals("J")){
							this.giro.setRucRemitente(this.remitente.getEmpresa().getRUC()); 
							this.giro.setRazonSocialRemitente(this.remitente.getEmpresa().getRazonSocial()); 
							this.giro.setDocumento(this.puntoVenta.getSerieFactura()+"-" + this.puntoVenta.getUltimaFactura());
							this.giro.setTipoDocumento("FACTURA"); 
						}else{
							this.giro.setTipoDocumento("BOLETA");
							this.giro.setDocumento(this.puntoVenta.getSerieBoleta()+"-" + this.puntoVenta.getUltimaBoleta());
						}	
						this.giro.setDniDestinatario1(this.destinatario1.getDni());
						this.giro.setApellidosDestinatario1(this.destinatario1.getApellidos()); 
						this.giro.setNombresDestinatario1(this.destinatario1.getNombres()); 
						this.giro.setTelefonoDestinatario1(this.destinatario1.getTelefono()); 
						if(this.destinatario1.getTipoPersona().equals("J")){
							this.giro.setRucDestinatario1(this.destinatario1.getEmpresa().getRUC());
							this.giro.setRazonSocialDestinatario1(this.destinatario1.getEmpresa().getRazonSocial()); 
						}		
						this.giro.setDniDestinatario2(this.destinatario2.getDni());
						this.giro.setApellidosDestinatario2(this.destinatario2.getApellidos()); 
						this.giro.setNombresDestinatario2(this.destinatario2.getNombres()); 
						this.giro.setTelefonoDestinatario2(this.destinatario2.getTelefono()); 
						if(this.destinatario2.getTipoPersona()!=null && this.destinatario2.getTipoPersona().equals("J")){
							this.giro.setRucDestinatario2(this.destinatario2.getEmpresa().getRUC());
							this.giro.setRazonSocialDestinatario2(this.destinatario2.getEmpresa().getRazonSocial());
						}	 
						this.giro.setTotalCobrado(getImporteTotal()); 
						this.giro.setEstado(1); //Para indicar que esta recibido
						
						try {
							this.giroServices.crearGiro(giro);
							
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
						
						
						
												
						//////Para guardar la liquidacion////////////					
						registrarLiquidacion(giro);						
						//Para registrar el tracking de la encomienda					
						registrarTracking();
						//Registrar en Historial de Encomienda
						//registrarHistorialEncomienda(giro);
						
						
						puntoVenta = this.puntoVentaServices.findById(usuarioLogin.getIdpuntoventa());
						this.documento = puntoVenta.getSerieBoleta()+" - "+puntoVenta.getUltimaBoleta();	
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					//////Para limpiar los campos y dejar listo para registrar otra encomienda
					limpiarCampos();
					
			}else{
				FacesUtils.showFacesMessage("Ingrese los datos del remitente y/o destinatario(s) correctamente.",Constante.ERROR);
				context.update("sms");
			}
			
		}else{
			FacesUtils.showFacesMessage("Seleccione un destino para la encomienda.",Constante.ERROR);
			context.update("sms");
		}
		
	}
	
	
	public void limpiarCampos(){
		RequestContext context = RequestContext.getCurrentInstance();
		this.giro = new Giro();
		this.remitente = new Persona();
		this.remitente.setTipoPersona("N");
		this.remitente.setEmpresa(new Empresa());
		this.destinatario1 = new Persona();
		this.destinatario1.setTipoPersona("N");
		this.destinatario1.setEmpresa(new Empresa());
		this.destinatario2 = new Persona();
		this.destinatario2.setTipoPersona("N"); 
		this.destinatario2.setEmpresa(new Empresa());
		this.tarifa=0.0;
		this.importeIGV=0.0;
		this.importeTotal=0.0;
		this.idDestino = null;
		this.idOficina = null;
		this.formaPago="E";
		this.bandDestino = Boolean.TRUE;
		this.destinatarioActivar = Boolean.FALSE;
		this.listaTarifaReparto = new ArrayList<>();
		//this.montoEfectivo = new BigDecimal(0);
		//this.montoTarjeta = new BigDecimal(0);
		context.update("formDatosRemitente");
		context.update("formDatosDestinatario1");
		context.update("formDatosDestinatario2");
		context.update("formObservacion");
		context.update("formDestino");
		context.update("pnlDestina1");
	}
	
	
	public void registrarLiquidacion(Giro g){
		Liquidacion_Venta liquidacion = new Liquidacion_Venta();
		liquidacion.setDocumento(g.getDocumento());
		if(this.remitente.getTipoPersona().equals("N")){
			liquidacion.setTipoDocumento("BOLETA"); 
			liquidacion.setSubTotal(this.tarifa); 
			liquidacion.setIgv(0.0); 
			liquidacion.setTotal(this.tarifa); 
			if(getFormaPago().equals("E")){
				liquidacion.setFormaPago("EFECTIVO");
				liquidacion.setTotalEfectivo(liquidacion.getTotal() + this.giro.getMontoGiro());
				}
			////Actualizar el numero de Boleta
			try {
				PuntoVenta pv = new PuntoVenta();
				pv.setIdPuntoVenta(usuarioLogin.getIdpuntoventa());
				pv.setUltimaBoleta(this.puntoVenta.getUltimaBoleta()+1); 
				this.puntoVentaServices.actualizarUltimaBoletaPuntoVenta(pv); 
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			liquidacion.setTipoDocumento("FACTURA"); 
			liquidacion.setSubTotal(this.tarifa); 
			liquidacion.setIgv(Constante.IGV*this.tarifa); 
			liquidacion.setTotal(this.tarifa + (Constante.IGV*this.tarifa)); 
			if(getFormaPago().equals("E")){
				liquidacion.setTotalEfectivo(liquidacion.getTotal() + this.giro.getMontoGiro());
				liquidacion.setFormaPago("EFECTIVO");
				}
			
			////Actualizar el numero de Factura
			
			try {
				PuntoVenta pv = new PuntoVenta();
				pv.setIdPuntoVenta(usuarioLogin.getIdpuntoventa());
				pv.setUltimaFactura(this.puntoVenta.getUltimaFactura()+1); 
				this.puntoVentaServices.actualizarUltimaFacturaPuntoVenta(pv); 
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		liquidacion.setIdPuntoVenta(this.usuarioLogin.getIdpuntoventa()); 
		liquidacion.setfDocumento(new Date()); 
		liquidacion.setArea("CARGO"); 
		liquidacion.setProceso("GIRO");
		liquidacion.setEstado(1); 
		liquidacion.setMovimientoCaja(Constante.MOVIMIENYO_CAJA_INGRESO);
		liquidacion.setIdUsuario(getUsuarioLogin().getIdusuario());
		try {
			this.liquidacion_ventaServices.crearLiquidacion_Venta(liquidacion);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void registrarTracking(){
		
		Giro giro;
		try {
			giro = this.giroServices.findLastGiroByPV(usuarioLogin.getIdpuntoventa());
			TrackingGiro t = new TrackingGiro();
			t.setIdGiro(giro.getIdGiro());
			t.setIdEstado(1); 
			t.setEstadoActual(Boolean.TRUE); 
			this.trackingGiroServices.crearTrackingGiro(t);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	

	
	
	public void listarEncomiendas(){
		try {
			this.listaGiros = this.giroServices.findAll();
			for(Giro g : listaGiros){
				Agencia origen = this.agenciaService.findById(g.getIdOrigen());
				g.setOrigenString(origen.getDescripcion());
				Agencia destino = this.agenciaService.findById(g.getIdDestino());
				g.setDestinoString(destino.getDescripcion()); 
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void setearGiro(Giro e){
		
		RequestContext context = RequestContext.getCurrentInstance();
		this.giro = e;
		this.agencia.setIdagencia(e.getIdOrigen()); 
		this.idDestino = e.getIdDestino(); 
		this.listaOficinas = this.oficinaService.getOficinasxAgencia(this.idDestino);
		this.idOficina = e.getIdOficina(); 
		this.giro.setIdUsuario(e.getIdUsuario());
		this.giro.setIdPuntoVentaOrigen(e.getIdPuntoVentaOrigen()); 
		this.giro.setfRegistro(e.getfRegistro());
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
			try {
				Liquidacion_Venta l = this.liquidacion_ventaServices.findByDocumento(giro.getDocumento());
				this.importeTotal = l.getTotal();
				this.importeIGV = l.getIgv();
				this.tarifa = l.getSubTotal();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
		}else{
			this.importeTotal = Double.parseDouble(e.getTotalCobrado().toString());
			this.importeIGV = 0.0;
			this.tarifa = this.importeTotal;
		}
		this.setDocumento(e.getDocumento());  
		
		this.destinatario1.setDni(e.getDniDestinatario1());
		this.destinatario1.setApellidos(e.getApellidosDestinatario1());
		this.destinatario1.setNombres(e.getApellidosDestinatario1()); 
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
		
		
	
		//this.encomienda.setEstado(1); //Para indicar que esta recibido
		//this.encomienda.setDireccionEnvio(e.getDireccionEnvio());
		
		bandAnularEncomienda = Boolean.FALSE;
		destinatarioActivar = Boolean.TRUE;
		
		context.update("formDestino");
		context.update("formDatosRemitente");
		context.update("formDatosDestinatario1");
		context.update("formDatosDestinatario2");
		context.update("btnAnular");
		context.update("btnActualizar");
		context.update("txtObservacion");
		context.update("pnlDestina1");
		
	}
	
	public void anularGiro(){
		System.out.println("Giro: " + this.giro.getIdGiro());
		System.out.println("Documento Giro: " + this.giro.getDocumento());
		
		try {
			this.giro.setEstado(10);
			this.giroServices.actualizarEstadoGiro(giro);
			Liquidacion_Venta l = this.liquidacion_ventaServices.findByDocumento(this.giro.getDocumento());
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
	
	public void actualizarGiro(){
		this.giro.setIdOrigen(this.agencia.getIdagencia()); 
		this.giro.setIdDestino(getIdDestino()); 
		this.giro.setIdOficina(getIdOficina()); 
		this.giro.setIdUsuario(usuarioLogin.getIdusuario());
		this.giro.setIdPuntoVentaOrigen(usuarioLogin.getIdpuntoventa()); 
		this.giro.setTipoPersona(this.remitente.getTipoPersona());
		this.giro.setDniRemitente(this.remitente.getDni());
		this.giro.setApellidosRemitente(this.remitente.getApellidos());
		this.giro.setNombresRemitente(this.remitente.getNombres()); 
		this.giro.setTelefonoRemitente(this.remitente.getTelefono()); 
		if(this.remitente.getTipoPersona().equals("J")){
			this.giro.setRucRemitente(this.remitente.getEmpresa().getRUC()); 
			this.giro.setRazonSocialRemitente(this.remitente.getEmpresa().getRazonSocial()); 
			this.giro.setDocumento(this.puntoVenta.getSerieFactura()+"-" + this.puntoVenta.getUltimaFactura());
			this.giro.setTipoDocumento("FACTURA"); 
		}else{
			this.giro.setTipoDocumento("BOLETA");
			this.giro.setDocumento(this.puntoVenta.getSerieBoleta()+"-" + this.puntoVenta.getUltimaBoleta());
		}	
		this.giro.setDniDestinatario1(this.destinatario1.getDni());
		this.giro.setApellidosDestinatario1(this.destinatario1.getApellidos());
		this.giro.setNombresDestinatario1(this.destinatario1.getNombres());
		this.giro.setTelefonoDestinatario1(this.destinatario1.getTelefono()); 
		if(this.destinatario1.getTipoPersona().equals("J")){
			this.giro.setRucDestinatario1(this.destinatario1.getEmpresa().getRUC());
			this.giro.setRazonSocialDestinatario1(this.destinatario1.getEmpresa().getRazonSocial()); 
		}		
		this.giro.setDniDestinatario2(this.destinatario2.getDni());
		this.giro.setApellidosDestinatario2(this.destinatario2.getApellidos());
		this.giro.setNombresDestinatario2(this.destinatario2.getNombres()); 
		this.giro.setTelefonoDestinatario2(this.destinatario2.getTelefono()); 
		if(this.destinatario2.getTipoPersona()!=null && this.destinatario2.getTipoPersona().equals("J")){
			this.giro.setRucDestinatario2(this.destinatario2.getEmpresa().getRUC());
			this.giro.setRazonSocialDestinatario2(this.destinatario2.getEmpresa().getRazonSocial());
		}	 
		this.giro.setTotalCobrado(getImporteTotal()); 
		this.giro.setEstado(1); //Para indicar que esta recibido
		
		
		try {
			this.giroServices.actualizarGiro(giro);
			
			puntoVenta = this.puntoVentaServices.findById(usuarioLogin.getIdpuntoventa());
			this.documento = puntoVenta.getSerieBoleta()+" - "+puntoVenta.getUltimaBoleta();	
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		limpiarCampos();
	}
	
	/****************Atributos*******************/
	
	@ManagedProperty(value="#{loginMB.usuario}")
	private Usuario usuarioLogin;
	private Persona remitente;
	private Persona destinatario1;
	private Persona destinatario2;
	private PuntoVenta puntoVenta;
	private Agencia agencia;
	private Tarifa tarifaBase;
	private Tarifa_Producto tarifa_producto;
	private DetalleEncomienda detalleEncomienda;
	private DetalleEncomienda detEncomiendaCompuesto;
	private Giro giro;
	private Producto_DetalleEnc producto_detalleEnc;

	private Integer idDestino;
	private Integer idOficina;
	private Boolean bandContraEntrega;
	private Integer cantidad;
	private Boolean bandDestino;
	private Double tarifa;
	private Double importeIGV;
	private Double importeTotal;
	private String formaPago;
	private String aPaternoDestBusqueda;
	private String aMaternoDestBusqueda;
	private String condBusqDestinatario;
	private Boolean bandAnularEncomienda = Boolean.TRUE;
	private String documento;
	private String observacion;
	private Boolean destinatarioActivar = Boolean.FALSE;
	
	private List<Destino> listaAgDestinos;
	private List<Agencia> listaAgenciasDestinos;
	private List<TipoDocumento> listaTipoDoc;
	private List<Tarifa_Producto> listaTarifaProducto;
	private List<Tarifa_Producto> listaFilterTarifaProducto;
	private List<Tarifa> listaTarifaReparto;
	private List<HistorialEncomienda> listaHistorial;
	private List<Persona> listaPosiblesDestinatarios;
	private List<Giro> listaGiros;
	private List<Giro> listaGirosFilter;
	
	
	private AgenciaService agenciaService;
	private DestinoServices destinoService;
	private TipoDocumentoServices tipoDocServices;
	private PersonaServices personaServives;
	private PuntoVentaService puntoVentaServices;
	private Tarifa_ProductoServices tarifa_productoServices;
	private GiroServices giroServices;
	private TarifaServices tarifaServices;
	private Liquidacion_VentaServices liquidacion_ventaServices;
	private TrackingGiroServices trackingGiroServices;
	private HistorialEncomiendaServices historialEncomiendaServices;
	private OficinaService oficinaService;
	private List<Oficina> listaOficinas;
	private EmpresaService empresaService;
	private ParametroServices parametroServices;
	
	
	/*############################-------setter y getter---------##############################3*/

	public List<Destino> getListaAgDestinos() {
		return listaAgDestinos;
	}

	public Boolean getDestinatarioActivar() {
		return destinatarioActivar;
	}

	public void setDestinatarioActivar(Boolean destinatarioActivar) {
		this.destinatarioActivar = destinatarioActivar;
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
	
	public Giro getGiro() {
		return giro;
	}

	public void setGiro(Giro giro) {
		this.giro = giro;
	}
	
	public Boolean getBandDestino() {
		return bandDestino;
	}

	public void setBandDestino(Boolean bandDestino) {
		this.bandDestino = bandDestino;
	}

	public Producto_DetalleEnc getProducto_detalleEnc() {
		return producto_detalleEnc;
	}

	public void setProducto_detalleEnc(Producto_DetalleEnc producto_detalleEnc) {
		this.producto_detalleEnc = producto_detalleEnc;
	}

	public Double getTarifa() {
		return tarifa;
	}

	public void setTarifa(Double tarifa) {
		this.tarifa = tarifa;
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

	public Boolean getBandAnularEncomienda() {
		return bandAnularEncomienda;
	}

	public void setBandAnularEncomienda(Boolean bandAnularEncomienda) {
		this.bandAnularEncomienda = bandAnularEncomienda;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public Double getImporteIGV() {
		return importeIGV;
	}

	public void setImporteIGV(Double importeIGV) {
		this.importeIGV = importeIGV;
	}

	public Double getImporteTotal() {
		return importeTotal;
	}

	public void setImporteTotal(Double importeTotal) {
		this.importeTotal = importeTotal;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
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

	public List<Agencia> getListaAgenciasDestinos() {
		return listaAgenciasDestinos;
	}

	public void setListaAgenciasDestinos(List<Agencia> listaAgenciasDestinos) {
		this.listaAgenciasDestinos = listaAgenciasDestinos;
	}
	
	

}
