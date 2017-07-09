package com.certicom.ittsa.managedBeans;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
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
import com.certicom.ittsa.domain.Log;
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
import com.certicom.ittsa.services.MenuServices;
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

@ManagedBean(name="reimpresionDocumentosMB")
@ViewScoped
public class ReimpresionDocumentosMB  extends GenericBeans implements Serializable{
	
	private Encomienda encomiendaFilter;
	private Encomienda encomiendaReporte;
	
	private List<Encomienda> listaEncomienda;
	private List<Encomienda> listaEncomiendaFilter;
	private List<Agencia> listaAgeOrigen;
	private List<Destino> listaAgeDestino;
	private List<Oficina> listOficinaOri;
	private List<Oficina> listOficinaDes;
	private List<DetalleEncomienda> listaRptDetalle;
	
	//services
	private EncomiendaServices encomiendaServices;
	private MenuServices menuServices;
	private AgenciaService agenciaService;
	private DestinoServices destinoServices;
	private OficinaService oficinaService;
	private DetalleEncomiendaServices detalleEncomiendaServices;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	@ManagedProperty(value="#{loginMB.usuario}")
	private Usuario usuarioLogin;
	private Persona remitente;
	private TipoDocumentoServices tipoDocServices;
	private List<TipoDocumento> listaTipoDoc;
	private String formaPago;
	

	
	@PostConstruct
	public void inicia()
	{
		this.encomiendaServices = new EncomiendaServices();
		this.menuServices=new MenuServices();
		this.agenciaService = new AgenciaService();
		this.destinoServices = new DestinoServices();
		this.oficinaService = new OficinaService();
		this.detalleEncomiendaServices = new DetalleEncomiendaServices();
		
		this.encomiendaFilter = new Encomienda();
		this.encomiendaFilter.setTipoDocumento("BOLETA");
		this.listaAgeOrigen = new ArrayList<>();
		this.listaAgeDestino = new ArrayList<>();
		this.listOficinaOri = new ArrayList<>();
		this.listOficinaDes = new ArrayList<>();
		
		this.tipoDocServices = new TipoDocumentoServices();
		this.encomiendaReporte= new Encomienda();
		this.listaRptDetalle = new ArrayList<DetalleEncomienda>();
		
		 log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		this.formaPago = "BOLETA";
		

		try {
			this.listaAgeOrigen = this.agenciaService.listaAgenciasActivas();
			this.listaTipoDoc = tipoDocServices.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.remitente = new Persona();
		this.remitente.setTipoPersona("N");
		this.remitente.setIdtipodoc(listaTipoDoc.get(0).getIdtipodoc());
		this.remitente.setEmpresa(new Empresa()); 
		
	}
	
	public void setearEncomienda(Encomienda e){
		this.encomiendaReporte = e;
		
		try {
			listaRptDetalle = detalleEncomiendaServices.findByIdEncomienda(encomiendaReporte.getIdEncomienda());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//listaRptDetalle 
		
		
		System.out.println("Seteamos los valores");

		//imprimirPDF();
		
		
	}
	
	public void imprimirPDF(){
		System.out.println("Entramos a ImprimirPDF");
		
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
			
			input.put("P_COPIA", "Copia de Boleto No:"+this.encomiendaReporte.getComprobante());
			
			input.put("P_TOTAL_COBRADO", this.encomiendaReporte.getTotalCobrado());
			input.put("P_TOTAL_LETRAS", "SON "+ numeroALetra.Convertir(this.encomiendaReporte.getTotalCobrado().toString(), true, "SOLES"));
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
			
			//ver  input.put("P_SUBTOTAL", this.subTotalRpt);
			//ver  input.put("P_IGV", this.IGVRpt);
			input.put("P_COPIA", "Copia de Factura No:"+this.encomiendaReporte.getComprobante());
			input.put("P_TOTAL_COBRADO", this.encomiendaReporte.getTotalCobrado());
			
			input.put("P_TOTAL_LETRAS", "SON "+ numeroALetra.Convertir(this.encomiendaReporte.getTotalCobrado().toString(), true, "SOLES"));
			
			
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
				
				// ver input2.put("P_PLACA", this.busSeleccionado.getPlacaBus());
				//input2.put("P_PLACA", "");
				input2.put("P_TARJ_CIR", "000");
				//ver input2.put("P_LICE_CON", this.busSeleccionado.getLicPiloto()+"/"+this.busSeleccionado.getLicCopiloto());
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
	
	
	public void listarDestinosxOri(){
		try {
			this.listaAgeDestino = this.destinoServices.obtenerDestino(this.encomiendaFilter.getIdOrigen());
			this.listOficinaOri =  this.oficinaService.getOficinasxAgencia(this.encomiendaFilter.getIdOrigen());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void listarOficinasDestino(){
		try {
			this.listOficinaDes =  this.oficinaService.getOficinasxAgencia(this.encomiendaFilter.getIdDestino());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
	public void consultarEncomiendas(){
		try {

			

			System.out.println("forma de pago que entra es  " + this.encomiendaFilter.getTipoDocumento());
			System.out.println("el ruc que entra es   " + this.encomiendaFilter.getRucRemitente());
			System.out.println("el dni que ingresa es    " + this.encomiendaFilter.getDniRemitente());
			
		
			this.listaEncomienda = this.encomiendaServices.listaEncomiendasxOficinasxPersona(this.encomiendaFilter);
		
			
			System.out.println("EL TAMAÑO DE LA LISTA ES " + listaEncomienda.size());
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.encomiendaFilter = new Encomienda();
		
	}
	
	public void limpiarValores(){
		
		System.out.println(" entro el el ajax");
	    if(this.encomiendaFilter.getTipoDocumento() == "BOLETA"){
	    	
	        this.encomiendaFilter.setRucRemitente(null);
	    }else{
	    	 this.encomiendaFilter.setDniRemitente(null);
	    }
		
	}
	
	//set and Get

	public MenuServices getMenuServices() {
		return menuServices;
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

	public List<Agencia> getListaAgeOrigen() {
		return listaAgeOrigen;
	}

	public void setListaAgeOrigen(List<Agencia> listaAgeOrigen) {
		this.listaAgeOrigen = listaAgeOrigen;
	}

	public List<Destino> getListaAgeDestino() {
		return listaAgeDestino;
	}

	public void setListaAgeDestino(List<Destino> listaAgeDestino) {
		this.listaAgeDestino = listaAgeDestino;
	}

	public List<Oficina> getListOficinaOri() {
		return listOficinaOri;
	}

	public void setListOficinaOri(List<Oficina> listOficinaOri) {
		this.listOficinaOri = listOficinaOri;
	}

	public List<Oficina> getListOficinaDes() {
		return listOficinaDes;
	}

	public void setListOficinaDes(List<Oficina> listOficinaDes) {
		this.listOficinaDes = listOficinaDes;
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

	public Encomienda getEncomiendaFilter() {
		return encomiendaFilter;
	}

	public void setEncomiendaFilter(Encomienda encomiendaFilter) {
		this.encomiendaFilter = encomiendaFilter;
	}

	public EncomiendaServices getEncomiendaServices() {
		return encomiendaServices;
	}

	public void setEncomiendaServices(EncomiendaServices encomiendaServices) {
		this.encomiendaServices = encomiendaServices;
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

	public String getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}
	
	
	
}
