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
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.context.RequestContext;

import net.sf.jasperreports.engine.JRParameter;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Destino;
import com.certicom.ittsa.domain.DetalleEncomienda;
import com.certicom.ittsa.domain.DetalleGuiaRemision;
import com.certicom.ittsa.domain.Encomienda;
import com.certicom.ittsa.domain.Flota;
import com.certicom.ittsa.domain.GuiaRemision;
import com.certicom.ittsa.domain.Oficina;
import com.certicom.ittsa.domain.Piloto;
import com.certicom.ittsa.domain.Producto_DetalleEnc;
import com.certicom.ittsa.domain.Programacion;
import com.certicom.ittsa.domain.PuntoVenta;
import com.certicom.ittsa.domain.SubManifiesto;
import com.certicom.ittsa.domain.TrackingEncomienda;
import com.certicom.ittsa.domain.Usuario;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.DestinoServices;
import com.certicom.ittsa.services.DetalleEncomiendaServices;
import com.certicom.ittsa.services.DetalleGuiaRemisionService;
import com.certicom.ittsa.services.EncomiendaServices;
import com.certicom.ittsa.services.FlotaService;
import com.certicom.ittsa.services.GuiaRemisionService;
import com.certicom.ittsa.services.OficinaService;
import com.certicom.ittsa.services.PilotoService;
import com.certicom.ittsa.services.Producto_DetalleEncService;
import com.certicom.ittsa.services.ProgramacionService;
import com.certicom.ittsa.services.PuntoVentaService;
import com.certicom.ittsa.services.SubManifiestoService;
import com.certicom.ittsa.services.TrackingEncomiendaServices;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.ExportarArchivo;
import com.pe.certicom.ittsa.commons.FacesUtils;

@ManagedBean(name = "subManifiestoCargaMB") 
@ViewScoped
public class SubManifiestoCargaMB {

	private Encomienda filterEnc;
	private Programacion progSelected;

	private List<Agencia> listaAgencias;
	private List<Oficina> listaOficinas;
	private List<Destino> listaDestino;
	private List<Encomienda> listaEncomienda;
	private List<Encomienda> listaEncomiendaFilter;
	private List<Encomienda> listaRptEncomienda;
	private List<Encomienda> listaRptGuiaRemision;
	private List<Producto_DetalleEnc> listaRptCodigoBarra;
	
	private PuntoVenta puntoVenta;
	@ManagedProperty(value="#{loginMB.usuario}")
	private Usuario usuarioLogin;
	
	private List<Programacion> listaBuses;
	private List<Programacion> listaBusesFilter;

	private AgenciaService agenciaService;
	private DestinoServices destinoServices;
	private EncomiendaServices encomiendaServices;
	private ProgramacionService programacionService;
	private TrackingEncomiendaServices trackingEncomiendaServices;
	private GuiaRemisionService guiaRemisionService;
	private DetalleGuiaRemisionService detalleGuiaRemisionService;
	private PuntoVentaService puntoVentaService;
	private DetalleEncomiendaServices detalleEncomiendaServices;
	private FlotaService flotaService;
	private PilotoService pilotoService;
	private Producto_DetalleEncService producto_DetalleEncService;
	private SubManifiestoService subManifiestoService;
	private OficinaService oficinaService;
	
	private boolean btnGuardar = false;
	private boolean btnImprimir = true;
	private boolean btnImprimirGuia = false;
	
	private Double totalPeso = 0.0;
	private Integer totalBultos = 0;
	private Double totalImporte = 0.0;
	
	private Double totalPesoSel = 0.0;
	private Integer totalBultosSel = 0;
	private Double totalImporteSel = 0.0;

	private Double totalPesoGral = 0.0;
	private Integer totalBultosGral = 0;
	private Double totalImporteGral = 0.0;
	

	@PostConstruct
	public void init() {
		this.filterEnc = new Encomienda();
		this.filterEnc.setfRegistro(new Date());
		this.progSelected = new Programacion();
		
		this.listaEncomienda = new ArrayList<Encomienda>();
		this.listaRptEncomienda = new ArrayList<Encomienda>();
		this.listaRptGuiaRemision = new ArrayList<Encomienda>();
		this.listaRptCodigoBarra = new ArrayList<Producto_DetalleEnc>();
		this.listaOficinas = new ArrayList<>();
		
		this.agenciaService = new AgenciaService();
		this.destinoServices = new DestinoServices();
		this.encomiendaServices = new EncomiendaServices();
		this.programacionService = new ProgramacionService();
		this.trackingEncomiendaServices = new TrackingEncomiendaServices();
		this.guiaRemisionService = new GuiaRemisionService();
		this.detalleGuiaRemisionService = new DetalleGuiaRemisionService();
		this.detalleEncomiendaServices = new DetalleEncomiendaServices();
		this.puntoVentaService = new PuntoVentaService();
		this.flotaService = new FlotaService();
		this.pilotoService = new PilotoService();
		this.producto_DetalleEncService = new Producto_DetalleEncService();
		this.subManifiestoService = new SubManifiestoService();
		this.oficinaService = new OficinaService();
		try {
			this.listaAgencias = agenciaService.listaAgenciasActivas();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void listarDestinosxOri() {
		try {
			// this.listaDestino = new ArrayList<Destino>();
			this.listaDestino = destinoServices.obtenerDestino(this.filterEnc
					.getIdOrigen());
			
			this.listaOficinas =this.oficinaService.listaOficinasXAgencia(this.filterEnc.getIdOrigen());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void consultarEncomiendas() {
		this.totalBultosGral = 0;
		this.totalPesoGral = 0.0;
		this.totalImporteGral = 0.0;
		
		this.totalBultosSel = 0;
		this.totalPesoSel = 0.0;
		this.totalImporteSel = 0.0;
		
		try {
			this.listaEncomienda = this.encomiendaServices.listarEncomiendasRecibidas(this.filterEnc);
			for (int i = 0; i < this.listaEncomienda.size(); i++) {
				this.totalBultosGral += this.listaEncomienda.get(i).getNroBultos();
				this.totalPesoGral += this.listaEncomienda.get(i).getPesoTotal().doubleValue();
				this.totalImporteGral += this.listaEncomienda.get(i).getTotalCobrado();
			}
			
			listarBusesDisponibles();
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void realizarCalculos(Encomienda e){
		if(e.isSeleccionado()){
			this.totalBultosSel +=e.getNroBultos();
			this.totalImporteSel += e.getTotalCobrado();
			this.totalPesoSel += e.getPesoTotal().doubleValue();
		} else{
			this.totalBultosSel -=e.getNroBultos();
			this.totalImporteSel -= e.getTotalCobrado();
			this.totalPesoSel -= e.getPesoTotal().doubleValue();
		}
	}

	public void crearSubManifiesto() {
		try {
			System.out.println(getUsuarioLogin().getIdpuntoventa());
			this.puntoVenta = this.puntoVentaService.findById(getUsuarioLogin().getIdpuntoventa());
			
			// guardando en t_submanifiesto
			SubManifiesto subM = new SubManifiesto();
			subM.setIdProgramacion(this.progSelected.getIdProgramacion());
			subM.setfRegistro(new Date());
			subM.setUsucrea(getUsuarioLogin().getIdusuario());
			
			this.subManifiestoService.registrarSubManifiesto(subM);
			Integer idSubManifiesto = subManifiestoService.lastSubManifiesto();
			
			for (int i = 0; i < this.listaEncomienda.size(); i++) {
				Encomienda e = this.listaEncomienda.get(i);
				if(e.isSeleccionado()){
					// estado 2 embarcado y seteando la programacion
					this.encomiendaServices.actualizarEstadoEncomienda2(e.getIdEncomienda(), 2,this.progSelected.getIdProgramacion());
					// actualizando el estado anterior en el tracking
					this.trackingEncomiendaServices.actualizarEstadoAnterior(e.getIdEncomienda());
					// agregando el nuevo estado
					
					TrackingEncomienda tke = new TrackingEncomienda();
					tke.setIdEncomienda(e.getIdEncomienda());
					tke.setIdEstado(2);
					tke.setIdBus(this.progSelected.getIdBus());
					tke.setEstadoActual(Boolean.TRUE);
					tke.setIdProgramacion(this.progSelected.getIdProgramacion());
					
					this.trackingEncomiendaServices.crearTrackingEncomienda(tke);
					
					// actualizando el id submanifiesto
					this.encomiendaServices.actualizarSubManEncomienda(idSubManifiesto, e.getIdEncomienda());
					
					//agregando a la lista del reporte
					this.listaRptEncomienda.add(e);
					System.out.println("EL VALOR DE E ES " + e.getTipoDocumento());
					if(e.getTipoDocumento().equals("BOLETA")){
						System.out.println("entro");
						GuiaRemision ge = new GuiaRemision();
						ge.setfEmision(new Date());
						ge.setfRegistro(new Date());
						ge.setIdBus(this.progSelected.getIdBus());
						ge.setIdOrigen(this.filterEnc.getIdOrigen());
						ge.setIdDestino(this.filterEnc.getIdDestino());
						ge.setSerieGuia(this.puntoVenta.getSerieGuiaRemision());
						ge.setNumeroGuia(this.puntoVenta.getUltimaGuia());
						ge.setIdEncomienda(e.getIdEncomienda());
						this.listaRptGuiaRemision.add(e);
						this.guiaRemisionService.crearGuiaRemision(ge);
						
						Integer idGuia = this.guiaRemisionService.findLast();
						List<DetalleEncomienda> listDE = this.detalleEncomiendaServices.findByIdEncomienda(e.getIdEncomienda());
						for (int j = 0; j < listDE.size(); j++) {
							DetalleEncomienda den = listDE.get(j);
							DetalleGuiaRemision dguia = new DetalleGuiaRemision();
							dguia.setIdGuiaRemision(idGuia);
							dguia.setCantidad(den.getCantidad());
							dguia.setDescripcion(den.getDescripcion());
							dguia.setPeso(den.getPeso());
							dguia.setPrecioEnvio(den.getImporte());
							
							this.detalleGuiaRemisionService.crearDetalleGuiaRemision(dguia);
						}
						
					}
					
					
					
				}
			}
			PuntoVenta pv = new PuntoVenta();
			pv.setIdPuntoVenta(usuarioLogin.getIdpuntoventa());
			pv.setUltimaGuia(this.puntoVenta.getUltimaGuia()+1); 
			this.puntoVentaService.actualizarUltimaGuiaRemisionPuntoVenta(pv); 
			
			FacesUtils.showFacesMessage("Se recepcionaron los productos correctamente, proceda a imprimir.",Constante.INFORMACION);
			this.listaEncomienda = this.encomiendaServices.listarEncomiendasRecibidas(this.filterEnc);
			this.btnGuardar = Boolean.TRUE;
			this.btnImprimir = Boolean.FALSE;
			this.btnImprimirGuia = Boolean.FALSE;
			
			this.totalBultosSel = 0;
			this.totalPesoSel = 0.0;
			this.totalImporteSel = 0.0;
			
			this.totalBultosGral = 0;
			this.totalPesoGral = 0.0;
			this.totalImporteGral = 0.0;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void imprimirPDF(){
		try {
			ServletContext servletContext = (ServletContext) (FacesContext.getCurrentInstance().getExternalContext().getContext());
			
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");		
			String fecha = formato.format(filterEnc.getfRegistro());
			
			Integer total = this.listaRptEncomienda.size();
			
			String p_logo = ExportarArchivo.getPath("/resources/img/logoittsa.jpg");
			System.out.println("logo ruta:"+ p_logo);
			
			Map<String, Object> input =new  HashMap<String,Object>();
			input.put("P_LOGO", p_logo);
			input.put("P_OFI_ORIGEN", this.progSelected.getDesOrigen());
			input.put("P_OFI_DESTINO", this.progSelected.getDesDestino());
			input.put("P_NRO_BUS", this.progSelected.getNumeroBus().toString());
			input.put("P_PILOTO", this.progSelected.getNomPiloto());
			input.put("P_COPILOTO", this.progSelected.getNomCopiloto());
			input.put("P_HORA", this.progSelected.gethSalida());
			input.put("P_TOTAL", total.toString());
			input.put("P_FENVIO", fecha);
			input.put(JRParameter.REPORT_LOCALE, new Locale("es"));
			//input.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE); // no parte la pagina todo lo mete en un A4
			
			String path = ExportarArchivo.getPath("/resources/reports/jxrml/rptSubManifiestoCarga.jasper");
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			
			
			byte[] bytes;
				bytes = ExportarArchivo.exportPdf(path, input, this.listaRptEncomienda);
			ExportarArchivo.executePdf(bytes, "SubManifiestoEncomienda.pdf");
			FacesContext.getCurrentInstance().responseComplete();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void imprimirGuiaRemisionPDF(){
		try {
			Piloto piloto = this.pilotoService.findById(this.progSelected.getIdPiloto());
			Piloto copiloto = this.pilotoService.findById(this.progSelected.getIdCopiloto());
			Flota flota = this.flotaService.findById(this.progSelected.getIdBus());
			
			ServletContext servletContext = (ServletContext) (FacesContext.getCurrentInstance().getExternalContext().getContext());
			
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");		
			String fecha = formato.format(new Date());
			
			Map<String, Object> input =new  HashMap<String,Object>();
			input.put("P_OFI_ORIGEN", this.progSelected.getDesOrigen());
			input.put("P_OFI_DESTINO", this.progSelected.getDesDestino());
			input.put("P_DEP_ORIGEN", this.progSelected.getDesOrigen());
			input.put("P_DEP_DESTINO", this.progSelected.getDesDestino());
			
			input.put("P_RZ_EMPRESA_RE", "ITTSA");
			input.put("P_RUC_EMPRESA_RE", "2013227213");
			input.put("P_RZ_EMPRESA_DES", "ITTSA");
			input.put("P_RUC_EMPRESA_DES", "2013227213");
			input.put("P_PLACA", flota.getPlaca());
			input.put("P_TARJ_CIR", flota.gettPropiedad());
			input.put("P_LICE_CON", piloto.getLicencia()+"/"+copiloto.getLicencia());
			input.put("P_FECHA", fecha);
			input.put(JRParameter.REPORT_LOCALE, new Locale("es"));
			//input.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE); // no parte la pagina todo lo mete en un A4
			
			String path = ExportarArchivo.getPath("/resources/reports/jxrml/rptGuiaRemisionBoleta.jasper");
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			
			
			byte[] bytes;
				bytes = ExportarArchivo.exportPdf(path, input, this.listaRptGuiaRemision);
			ExportarArchivo.executePdf(bytes, "guiaRemisionBoleta.pdf");
			FacesContext.getCurrentInstance().responseComplete();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void imprimirCodigoBarraPDF(){
		try {
			ServletContext servletContext = (ServletContext) (FacesContext.getCurrentInstance().getExternalContext().getContext());
			
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");		
			String fecha = formato.format(this.progSelected.getfSalida());
			
			Map<String, Object> input =new  HashMap<String,Object>();
			input.put("P_ORIGEN", this.progSelected.getDesOrigen());
			input.put("P_DESTINO", this.progSelected.getDestino());
			input.put("P_BUS", this.progSelected.getNumeroBus());
			input.put("P_FECHA", fecha);
			input.put("P_HORA", this.progSelected.gethSalida());
			input.put("P_NRO_BULTOS", totalBultos.toString());
			input.put("P_PESO_TOTAL", totalPeso.toString());
			input.put("P_IMPORTE_TOTAL", totalImporte.toString());
			input.put("P_PILOTO", this.progSelected.getNomPiloto());
			input.put("P_COPILOTO", this.progSelected.getNomCopiloto());
			input.put(JRParameter.REPORT_LOCALE, new Locale("es"));
			
			String path = ExportarArchivo.getPath("/resources/reports/jxrml/rptCodigoBarras.jasper");
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			
			
			byte[] bytes;
				bytes = ExportarArchivo.exportPdf(path, input, this.listaRptCodigoBarra);
			ExportarArchivo.executePdf(bytes, "CodigoBarras.pdf");
			FacesContext.getCurrentInstance().responseComplete();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
	
	public void listarBusesDisponibles(){
		try {
			this.listaBuses = this.programacionService.listarBusesProgDisponibles(filterEnc.getfRegistro(), filterEnc.getIdOrigen(), filterEnc.getIdDestino());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void showSubCarga(Programacion p){
		RequestContext context = RequestContext.getCurrentInstance();
		int count = 0;
		this.btnGuardar= Boolean.FALSE;  
		this.btnImprimir = Boolean.TRUE;
		this.btnImprimirGuia = Boolean.TRUE;
		
		if(this.listaEncomienda.size()>0){
			for (int i = 0; i < this.listaEncomienda.size(); i++) {
				if(this.listaEncomienda.get(i).isSeleccionado()){
					count++;
					System.out.println("entro ");
				}
			}
			
			if(count >0){
				context.addCallbackParam("hayEncomiendas", Boolean.TRUE);
				this.progSelected = p;
				this.totalBultos = 0;
				this.totalPeso = 0.0;
				this.totalImporte = 0.0;
				
				this.listaRptCodigoBarra = new ArrayList<Producto_DetalleEnc>();
				for (int i = 0; i < this.listaEncomienda.size(); i++) {
					Encomienda e = this.listaEncomienda.get(i);
					
					if(e.isSeleccionado()){
						this.totalBultos += e.getNroBultos();
						this.totalPeso += e.getPesoTotal().doubleValue();
						this.totalImporte += e.getTotalCobrado();
						//this.totalImporte.setScale(2,BigDecimal.ROUND_UP);
						Double aux=Math.rint(totalImporte*100)/100;
						totalImporte=aux; 
						
						
						try {
							this.listaRptCodigoBarra.addAll(this.producto_DetalleEncService.listarPorIdEncomienda(e.getIdEncomienda()));
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
			}else{
				context.addCallbackParam("hayEncomiendas", Boolean.FALSE);
				FacesUtils.showFacesMessage("Seleccione por lo menos una encomienda.",Constante.ERROR);
				context.update("sms");
			}
			
		} else{
			context.addCallbackParam("hayEncomiendas", Boolean.FALSE);
			FacesUtils.showFacesMessage("Ingrese un Km. final mayor al recorrido actual.",Constante.ERROR);
			context.update("sms");
		}
		
	}
	
	public List<Agencia> getListaAgencias() {
		return listaAgencias;
	}

	public List<Destino> getListaDestino() {
		return listaDestino;
	}

	public void setListaAgencias(List<Agencia> listaAgencias) {
		this.listaAgencias = listaAgencias;
	}

	public void setListaDestino(List<Destino> listaDestino) {
		this.listaDestino = listaDestino;
	}

	public Encomienda getFilterEnc() {
		return filterEnc;
	}

	public void setFilterEnc(Encomienda filterEnc) {
		this.filterEnc = filterEnc;
	}

	public List<Encomienda> getListaEncomienda() {
		return listaEncomienda;
	}

	public AgenciaService getAgenciaService() {
		return agenciaService;
	}

	public DestinoServices getDestinoServices() {
		return destinoServices;
	}

	public void setListaEncomienda(List<Encomienda> listaEncomienda) {
		this.listaEncomienda = listaEncomienda;
	}

	public void setAgenciaService(AgenciaService agenciaService) {
		this.agenciaService = agenciaService;
	}

	public void setDestinoServices(DestinoServices destinoServices) {
		this.destinoServices = destinoServices;
	}

	public List<Encomienda> getListaEncomiendaFilter() {
		return listaEncomiendaFilter;
	}

	public void setListaEncomiendaFilter(List<Encomienda> listaEncomiendaFilter) {
		this.listaEncomiendaFilter = listaEncomiendaFilter;
	}

	public List<Programacion> getListaBuses() {
		return listaBuses;
	}

	public void setListaBuses(List<Programacion> listaBuses) {
		this.listaBuses = listaBuses;
	}

	public List<Programacion> getListaBusesFilter() {
		return listaBusesFilter;
	}

	public void setListaBusesFilter(List<Programacion> listaBusesFilter) {
		this.listaBusesFilter = listaBusesFilter;
	}

	public Programacion getProgSelected() {
		return progSelected;
	}

	public void setProgSelected(Programacion progSelected) {
		this.progSelected = progSelected;
	}

	public boolean isBtnGuardar() {
		return btnGuardar;
	}

	public void setBtnGuardar(boolean btnGuardar) {
		this.btnGuardar = btnGuardar;
	}

	public List<Encomienda> getListaRptEncomienda() {
		return listaRptEncomienda;
	}

	public void setListaRptEncomienda(List<Encomienda> listaRptEncomienda) {
		this.listaRptEncomienda = listaRptEncomienda;
	}

	public boolean isBtnImprimir() {
		return btnImprimir;
	}

	public void setBtnImprimir(boolean btnImprimir) {
		this.btnImprimir = btnImprimir;
	}

	public Double getTotalPeso() {
		return totalPeso;
	}

	public void setTotalPeso(Double totalPeso) {
		this.totalPeso = totalPeso;
	}

	public Integer getTotalBultos() {
		return totalBultos;
	}

	public void setTotalBultos(Integer totalBultos) {
		this.totalBultos = totalBultos;
	}

	public Double getTotalImporte() {
		return totalImporte;
	}

	public void setTotalImporte(Double totalImporte) {
		this.totalImporte = totalImporte;
	}

	public Double getTotalPesoGral() {
		return totalPesoGral;
	}

	public Integer getTotalBultosGral() {
		return totalBultosGral;
	}

	public Double getTotalImporteGral() {
		return totalImporteGral;
	}

	public void setTotalPesoGral(Double totalPesoGral) {
		this.totalPesoGral = totalPesoGral;
	}

	public void setTotalBultosGral(Integer totalBultosGral) {
		this.totalBultosGral = totalBultosGral;
	}

	public void setTotalImporteGral(Double totalImporteGral) {
		this.totalImporteGral = totalImporteGral;
	}

	public Double getTotalPesoSel() {
		return totalPesoSel;
	}

	public Integer getTotalBultosSel() {
		return totalBultosSel;
	}

	public Double getTotalImporteSel() {
		return totalImporteSel;
	}

	public void setTotalPesoSel(Double totalPesoSel) {
		this.totalPesoSel = totalPesoSel;
	}

	public void setTotalBultosSel(Integer totalBultosSel) {
		this.totalBultosSel = totalBultosSel;
	}

	public void setTotalImporteSel(Double totalImporteSel) {
		this.totalImporteSel = totalImporteSel;
	}

	public GuiaRemisionService getGuiaRemisionService() {
		return guiaRemisionService;
	}

	public void setGuiaRemisionService(GuiaRemisionService guiaRemisionService) {
		this.guiaRemisionService = guiaRemisionService;
	}

	public Usuario getUsuarioLogin() {
		return usuarioLogin;
	}

	public void setUsuarioLogin(Usuario usuarioLogin) {
		this.usuarioLogin = usuarioLogin;
	}

	public PuntoVenta getPuntoVenta() {
		return puntoVenta;
	}

	public void setPuntoVenta(PuntoVenta puntoVenta) {
		this.puntoVenta = puntoVenta;
	}

	public boolean isBtnImprimirGuia() {
		return btnImprimirGuia;
	}

	public void setBtnImprimirGuia(boolean btnImprimirGuia) {
		this.btnImprimirGuia = btnImprimirGuia;
	}

	public List<Oficina> getListaOficinas() {
		return listaOficinas;
	}

	public void setListaOficinas(List<Oficina> listaOficinas) {
		this.listaOficinas = listaOficinas;
	}
	

}
