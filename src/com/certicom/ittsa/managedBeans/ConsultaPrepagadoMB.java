package com.certicom.ittsa.managedBeans;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.PrinterName;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.AsientoVenta;
import com.certicom.ittsa.domain.Boleto;
import com.certicom.ittsa.domain.Destino;
import com.certicom.ittsa.domain.Empresa;
import com.certicom.ittsa.domain.Oficina;
import com.certicom.ittsa.domain.Persona;
import com.certicom.ittsa.domain.Postergacion;
import com.certicom.ittsa.domain.PuntoVenta;
import com.certicom.ittsa.domain.Usuario;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.AsientoVentaServices;
import com.certicom.ittsa.services.DestinoServices;
import com.certicom.ittsa.services.OficinaService;
import com.certicom.ittsa.services.PersonaServices;
import com.certicom.ittsa.services.PersonalService;
import com.certicom.ittsa.services.PuntoVentaService;
import com.pe.certicom.ittsa.commons.BoletoFilter;
import com.pe.certicom.ittsa.commons.ConstanteVentas;
import com.pe.certicom.ittsa.commons.ExportarArchivo;
import com.pe.certicom.ittsa.commons.NumeroALetra;
import com.pe.certicom.ittsa.commons.Utils;

@ManagedBean(name = "consultaPrepagadoMB")
@ViewScoped
public class ConsultaPrepagadoMB {
	
	private DestinoServices destinoServices;
	private AsientoVentaServices asientoVentaServices;
	private PersonaServices personaServices;
	private AgenciaService agenciaService;
	private PuntoVentaService puntoVentaService;
	private OficinaService oficinaService;
	
	@ManagedProperty(value="#{loginMB}")
	private LoginMB login;
	@ManagedProperty(value="#{loginMB.usuario}")
	private Usuario usuarioLogin;
	
	private List<Agencia> listaOrigen;
	private List<Agencia> listaCmbAgencia;
	private List<Destino> listaDestino;
	private List<AsientoVenta> listaAsientoVenta;
	private List<AsientoVenta> listaAsientoVentaFilter;
	private List<Oficina> listaCmbOficina;

	private AsientoVenta avSelect;
	private PuntoVenta pVentaCounter;
	private BoletoFilter boletoFilter;
	
	private Integer idOrigen;
	private Integer idDestino;
	private Date fechaEnvio;
	private boolean btnImprimir = true;
	private boolean btnPrint = true;
	
	private String codigoBoletoSelect = "";
	private String valueEntregado = "";

	//INICIO PISCOYA
	private String dniPasajero;
	//FIN PISCOYA


	@PostConstruct
	public void inicia() {
		this.asientoVentaServices = new AsientoVentaServices();
		this.destinoServices = new DestinoServices();
		this.personaServices = new PersonaServices();
		this.agenciaService = new AgenciaService();
		this.puntoVentaService = new PuntoVentaService();
		this.oficinaService = new OficinaService();

		this.listaAsientoVenta = new ArrayList<>();
		
		this.avSelect = new AsientoVenta();
		this.boletoFilter = new BoletoFilter();
		this.boletoFilter.setFinicio(new Date());
		this.boletoFilter.setFfin(new Date());
		
		this.fechaEnvio = new Date();
		try {
			this.listaOrigen = agenciaService.listaAgenciasActivas();
			this.listaCmbAgencia = this.listaOrigen;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void listarDestinosxOri() {
		try {
			this.listaDestino = this.destinoServices
					.obtenerDestino(this.boletoFilter.getOrigen());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getOficinasByAgencia(){
		try {
			this.listaCmbOficina = oficinaService.listaOficinasXAgencia(this.boletoFilter.getIdagencia());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void habilitarImprimir(){
		if(this.valueEntregado.equals("ENTREGADO")){
			this.btnPrint = Boolean.FALSE;
		} else{
			this.btnPrint = Boolean.TRUE;
		}
	}
	

	public void listarBoletosPrepagado() 
	{
		System.out.println("1"+boletoFilter.getOrigen());
		System.out.println("2"+boletoFilter.getDestino());
		System.out.println("3"+boletoFilter.getFinicio());
		System.out.println("4"+boletoFilter.getFfin());
		System.out.println("5"+boletoFilter.getEstadoDelivery());
		
		try {
			this.listaAsientoVenta = this.asientoVentaServices.obtenerPrepagadosxFecha(this.boletoFilter);
			
			if(this.listaAsientoVenta.size()>0)
			{
				this.btnImprimir = Boolean.FALSE;
			} else{
				this.btnImprimir = Boolean.TRUE;
			}
			
			for(AsientoVenta asv : this.listaAsientoVenta)
			{
				asv.setPersonaPagador(this.personaServices.findByNroDocumento(asv.getNrodocprepagado()));
				asv.setPersonaCliente(this.personaServices.findByNroDocumento(asv.getDocumentoPersona()));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void imprimirPDF(){
		String par_origen = "",par_destino = "",par_ofi_origen="";
		
		for (int i = 0; i < this.listaOrigen.size(); i++) {
			Agencia a = this.listaOrigen.get(i);
			if(a.getIdagencia() == this.boletoFilter.getOrigen()){
				par_origen = a.getDescripcion();
				break;
			}
		}
		
		for (int j = 0; j < this.listaDestino.size(); j++) {
			Destino d = this.listaDestino.get(j);
			if(d.getDestino() == this.boletoFilter.getDestino()){
				par_destino = d.getDesDestino();
				break;
			}
		}
		
		for (int i = 0; i < this.listaCmbOficina.size(); i++) {
			Oficina o = this.listaCmbOficina.get(i);
			if(o.getIdOficina() == this.boletoFilter.getIdoficina()){
				par_ofi_origen = o.getDireccion();
				break;
			}
		}
		
		
		
		try {
			ServletContext servletContext = (ServletContext) (FacesContext.getCurrentInstance().getExternalContext().getContext());
			
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");		
			String fecha = formato.format(this.boletoFilter.getFinicio());

			SimpleDateFormat formato2 = new SimpleDateFormat("dd/MM/yyyy");		
			String fecha1 = formato.format(this.boletoFilter.getFfin());
			
			Integer total = this.listaAsientoVenta.size();
			
			String p_logo = ExportarArchivo.getPath("/resources/img/logoittsa.jpg");
			System.out.println("logo ruta:"+ p_logo);
			
			Map<String, Object> input =new  HashMap<String,Object>();
			input.put("P_LOGO", p_logo);
			input.put("P_ORIGEN", par_origen);
			input.put("P_DESTINO", par_destino);
			input.put("P_FENVIO", fecha +" - " + fecha1);
			input.put("P_OFICINA_ORIGEN", par_ofi_origen);
			input.put("P_TOTAL", total);
			input.put(JRParameter.REPORT_LOCALE, new Locale("es"));
			//input.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE); // no parte la pagina todo lo mete en un A4
			
			String path = ExportarArchivo.getPath("/resources/reports/jxrml/rptBoletosPrepagado.jasper");
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			
			
			byte[] bytes;
				bytes = ExportarArchivo.exportPdf(path, input, this.listaAsientoVenta);
			ExportarArchivo.executePdf(bytes, "BoletoPrepagados.pdf");
			FacesContext.getCurrentInstance().responseComplete();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void setearPrepagado(AsientoVenta av){
		this.avSelect = av;
		try {
			System.out.println("pv " +av.getIdusuarioventa());
			// obteniendo el punto de venta del usuario logeado
			this.pVentaCounter  = this.puntoVentaService.getPuntoVentaxUsuario(usuarioLogin.getIdpuntoventa());
			this.codigoBoletoSelect = pVentaCounter.getSeriePrepagado()+ "-"+pVentaCounter.getUltimoPrepagado();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void imprimirBoletoPrepagado(){
		Boleto boleto = new Boleto();
		List<Boleto> lista = new ArrayList<>();
		
//		String pathJasper="/resources/reports/jasper/boletos.jasper"; 
		String pathJasper="/resources/reports/jxrml/boletos.jasper"; 
 //       String theUserPrinterName = "TU_Impresora_Amiga";//impresora local
        String theUserPrinterName = "zebra";//impresora local
        //String theUserPrinterName = "\\\\"+this.puntoVenta.getNumeroIP()+"\\epson-fx-890"; //servidor
		
		
			lista.clear();
			boleto = new Boleto();
			boleto.setCodBoleto(this.codigoBoletoSelect);
        	boleto.setDestino(this.avSelect.getDesDestino());
        	boleto.setDni(this.avSelect.getPersonaCliente().getDni());
        	boleto.setFechaEmision(Utils.dateAString(new Date()));
        	boleto.setFechaViaje(Utils.dateAString(this.avSelect.getDesFSalida()));
        	boleto.setHoraViaje(this.avSelect.getDesHsalida());
        	boleto.setNumeroAsiento(this.avSelect.getNumero());
        	boleto.setOrigen(this.avSelect.getDesOrigen());
        	Persona per = null;
        	Empresa emp = null;
        	try {
			//	per = this.personaService.findByNroDocumento(asv.getDocumentoPersona());
        		per = this.avSelect.getPersonaCliente();
			//	emp = this.empresaService.findByNroRUC(asv.getDocumentoEmpresa());
			} catch (Exception e) {
				e.printStackTrace();
			}
        	boleto.setPasajero(per.getAPaterno() + " "+per.getAMaterno()+" "+per.getNombres());
        	boleto.setEmpresa(emp==null?" ":emp.getRazonSocial());
        	boleto.setRuc(emp==null?" ":emp.getRUC());
        	boleto.setTotalLetras(new NumeroALetra().Convertir(this.avSelect.getMonto().subtract(new BigDecimal(1))+"", Boolean.TRUE, "Nuevos Soles"));
        	boleto.setUsuario(this.login.getUsuario().getLogin());
        	boleto.setValorTotal(this.avSelect.getMonto().subtract(new BigDecimal(1))+"");
        	
        	lista.add(boleto);
        	
        	PuntoVenta pventa = new PuntoVenta();
        	pventa.setIdPuntoVenta(usuarioLogin.getIdpuntoventa());
        	pventa.setUltimoPrepagado(this.pVentaCounter.getUltimoPrepagado()+1); 
        	
        	try {
				this.puntoVentaService.actualizarUltimaPrepagadoPuntoVenta(pventa);
				this.avSelect.setEstado_delivery(ConstanteVentas.IMPRESO_PREPAGADO);
				this.asientoVentaServices.actualizarEstadoImpreso(this.avSelect);
				listarBoletosPrepagado();
			} catch (Exception e) {
				e.printStackTrace();
			}
        	
        	this.imprimirBoleto(lista,pathJasper,theUserPrinterName);
	}
	
	
	public void imprimirBoleto(List<Boleto> listab,String pathJasper,String theUserPrinterName)
	{
		
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

		
	}
	
	//INICIO PISCOYA
	public void listarBoletosPrepagadoxDNI() 
	{
		System.out.println("imprimir");
		System.out.println("dni pasajero: "+this.dniPasajero);
		
		try {
			this.listaAsientoVenta = this.asientoVentaServices.obtenerPrepagadosxDNI(this.dniPasajero);
			System.out.println("tamaño de lista:"+listaAsientoVenta.size());
			if(this.listaAsientoVenta.size()>0)
			{
				this.btnImprimir = Boolean.FALSE;
			} else{
				this.btnImprimir = Boolean.TRUE;
			}
			
			for(AsientoVenta asv : this.listaAsientoVenta)
			{
				asv.setPersonaPagador(this.personaServices.findByNroDocumento(asv.getNrodocprepagado()));
				asv.setPersonaCliente(this.personaServices.findByNroDocumento(asv.getDocumentoPersona()));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("PUBLIC VOID listarBoletosPrepagadoxDNI()");
		}
	}
	//FIN PISCOYA
	
	
	// ------------------- SET AN GET

	public List<Agencia> getListaOrigen() {
		return listaOrigen;
	}

	public void setListaOrigen(List<Agencia> listaOrigen) {
		this.listaOrigen = listaOrigen;
	}

	public List<Destino> getListaDestino() {
		return listaDestino;
	}

	public void setListaDestino(List<Destino> listaDestino) {
		this.listaDestino = listaDestino;
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

	public Date getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public List<AsientoVenta> getListaAsientoVenta() {
		return listaAsientoVenta;
	}

	public void setListaAsientoVenta(List<AsientoVenta> listaAsientoVenta) {
		this.listaAsientoVenta = listaAsientoVenta;
	}

	public List<AsientoVenta> getListaAsientoVentaFilter() {
		return listaAsientoVentaFilter;
	}

	public void setListaAsientoVentaFilter(
			List<AsientoVenta> listaAsientoVentaFilter) {
		this.listaAsientoVentaFilter = listaAsientoVentaFilter;
	}

	public boolean isBtnImprimir() {
		return btnImprimir;
	}

	public void setBtnImprimir(boolean btnImprimir) {
		this.btnImprimir = btnImprimir;
	}

	public AsientoVenta getAvSelect() {
		return avSelect;
	}

	public void setAvSelect(AsientoVenta avSelect) {
		this.avSelect = avSelect;
	}

	public LoginMB getLogin() {
		return login;
	}

	public void setLogin(LoginMB login) {
		this.login = login;
	}

	public PuntoVentaService getPuntoVentaService() {
		return puntoVentaService;
	}

	public void setPuntoVentaService(PuntoVentaService puntoVentaService) {
		this.puntoVentaService = puntoVentaService;
	}

	public String getCodigoBoletoSelect() {
		return codigoBoletoSelect;
	}

	public void setCodigoBoletoSelect(String codigoBoletoSelect) {
		this.codigoBoletoSelect = codigoBoletoSelect;
	}

	public Usuario getUsuarioLogin() {
		return usuarioLogin;
	}

	public void setUsuarioLogin(Usuario usuarioLogin) {
		this.usuarioLogin = usuarioLogin;
	}

	public PuntoVenta getpVentaCounter() {
		return pVentaCounter;
	}

	public void setpVentaCounter(PuntoVenta pVentaCounter) {
		this.pVentaCounter = pVentaCounter;
	}

	public BoletoFilter getBoletoFilter() {
		return boletoFilter;
	}

	public void setBoletoFilter(BoletoFilter boletoFilter) {
		this.boletoFilter = boletoFilter;
	}

	public String getValueEntregado() {
		return valueEntregado;
	}

	public void setValueEntregado(String valueEntregado) {
		this.valueEntregado = valueEntregado;
	}
	
	public boolean isBtnPrint() {
		return btnPrint;
	}

	public void setBtnPrint(boolean btnPrint) {
		this.btnPrint = btnPrint;
	}

	public List<Oficina> getListaCmbOficina() {
		return listaCmbOficina;
	}

	public void setListaCmbOficina(List<Oficina> listaCmbOficina) {
		this.listaCmbOficina = listaCmbOficina;
	}

	public List<Agencia> getListaCmbAgencia() {
		return listaCmbAgencia;
	}

	public void setListaCmbAgencia(List<Agencia> listaCmbAgencia) {
		this.listaCmbAgencia = listaCmbAgencia;
	}

	public String getDniPasajero() {
		return dniPasajero;
	}

	public void setDniPasajero(String dniPasajero) {
		this.dniPasajero = dniPasajero;
	}
	
	
	
	
	
	
	
}
