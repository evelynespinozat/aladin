package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.AgenciaAutoparte;
import com.certicom.ittsa.domain.CompraAutoparte;
import com.certicom.ittsa.domain.CompraDetAutoparte;
import com.certicom.ittsa.domain.KardexAutoparte;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Oficina;
import com.certicom.ittsa.domain.ProveedorAgencia;
import com.certicom.ittsa.services.AgenciaAutoParteService;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.CompraAutoparteService;
import com.certicom.ittsa.services.CompraDetAutoparteService;
import com.certicom.ittsa.services.KardexAutoparteService;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.OficinaService;
import com.certicom.ittsa.services.ProveedorAgenciaService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name = "compraAutoparteMB")
@ViewScoped
public class CompraAutoparteMB extends GenericBeans implements Serializable {
	
	private CompraAutoparte compraAutoparte;
	
	private List<CompraAutoparte> listaCompra;
	private List<CompraAutoparte> listaCompraFilter;
	private List<Agencia> listaAgencias;
	private List<Oficina> listaOficinas;
	private List<ProveedorAgencia> listaProveedor;
	private List<AgenciaAutoparte> listaAgProducto;
	private List<AgenciaAutoparte> listaAgProductoFilter;
	private List<CompraDetAutoparte> listaCompraDet;
	
	private MenuServices menuServices;
	private CompraAutoparteService compraAutoparteService;
	private AgenciaService agenciaService;
	private OficinaService oficinaService;  
	private ProveedorAgenciaService proveedorAgenciaService;
	private AgenciaAutoParteService agenciaAutoParteService;
	private CompraDetAutoparteService compraDetAutoparteService;
	private KardexAutoparteService kardexAutoparteService;
	private Boolean showTC;
	
	
	private Log log;
	private LogMB logMB;
	
	@PostConstruct
	public void init(){
		this.compraAutoparte = new CompraAutoparte();
		
		this.menuServices = new MenuServices();
		this.compraAutoparteService = new CompraAutoparteService();
		this.agenciaService = new AgenciaService();
		this.oficinaService = new OficinaService();
		this.proveedorAgenciaService = new ProveedorAgenciaService();
		this.agenciaAutoParteService = new AgenciaAutoParteService();
		this.compraDetAutoparteService = new CompraDetAutoparteService();
		this.kardexAutoparteService = new KardexAutoparteService();

		this.showTC = false;
		
		log = (Log)getSpringBean(Constante.SESSION_LOG);
		logMB = new LogMB();
		
		try {
			int cod_menu = this.menuServices.opcionMenuByPretty("pretty:compraAutoparte");
			log.setCod_menu(cod_menu);
			this.listaCompra = this.compraAutoparteService.findAll();
			this.listaAgencias = this.agenciaService.listaAgenciasActivas();
			this.listaCompraDet = new ArrayList<CompraDetAutoparte>();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void showTCambio(){
		
		if(this.compraAutoparte.getMoneda().equals("DOLARES")){
			this.showTC = true;
		}else{
			this.showTC = false;
		}
		
	}
	public void getOficinasxAgencia(){
		try {
			this.listaOficinas = this.oficinaService.getOficinasxAgencia(this.compraAutoparte.getIdagencia());
			this.listaAgProducto = new ArrayList<AgenciaAutoparte>();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getProveedxOficina(){
		try {
			this.listaProveedor = this.proveedorAgenciaService.listarProveedorxOficina(this.compraAutoparte.getIdOficina());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void xxx(AgenciaAutoparte ag){
		System.out.println("juan pajero");
	}
	
	public void listarProductosxAgencia(){
		try {
			AgenciaAutoparte filtro = new AgenciaAutoparte();
			filtro.setIdagencia(this.compraAutoparte.getIdagencia());
			filtro.setIdOficina(this.compraAutoparte.getIdOficina());
			filtro.setIdAlmacen(3);
			this.listaAgProducto = this.agenciaAutoParteService.listarAgenProductos(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void agregarProductoCompra(AgenciaAutoparte ap){
		int result = 0;
		for (int i = 0; i < listaCompraDet.size(); i++) {
			if(listaCompraDet.get(i).getIdAutoparte() == ap.getIdAutoparte()){
				result++;
				break;
			}
		}
		double st = 0;
		if(result == 0){
			CompraDetAutoparte cd = new CompraDetAutoparte();
			cd.setDesProducto(ap.getDesProducto());
			cd.setDesMedida(ap.getDesMedida());
			cd.setIdAutoparte(ap.getIdAutoparte());
			cd.setCantIngreso(ap.getCantIngreso());	
			cd.setPrecio(ap.getPrecioUni());
			st = ap.getCantIngreso()*ap.getPrecioUni();
			cd.setSubtotal(roundDecimal(st));
			listaCompraDet.add(cd);
		}
		
	}
	
	private double roundDecimal(double value){
		BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(2, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	public void agregapd(){
		System.out.println("entro pro fin");
	}
	
	public void removerProductodeLista(Integer IdAutoparte){
		for (int i = 0; i < listaCompraDet.size(); i++) {
			if(listaCompraDet.get(i).getIdAutoparte() == IdAutoparte){
				this.listaCompraDet.remove(i);
				break;
			}
		}
		
	}
	
	public void nuevaCompra(){
		this.compraAutoparte = new CompraAutoparte();
		this.listaAgProducto = new ArrayList<AgenciaAutoparte>();
		this.listaCompraDet = new ArrayList<CompraDetAutoparte>();
		this.listaOficinas = new ArrayList<Oficina>();
		this.listaProveedor = new ArrayList<ProveedorAgencia>();
	}
	
	
	public void registrarCompra(){
		Boolean valido=Boolean.TRUE;
		RequestContext context = RequestContext.getCurrentInstance();  
   	    context.addCallbackParam("esValido", valido );
		
		this.compraAutoparte.setfRegistro(new Date());
		this.compraAutoparteService.registrarCompra(this.compraAutoparte);
		Integer IdCompra = this.compraAutoparteService.getlastId();
		for (int i = 0; i < this.listaCompraDet.size(); i++) {
			CompraDetAutoparte cd = this.listaCompraDet.get(i);
			cd.setIdCompra(IdCompra);
			cd.setfRegistro(new Date());
			
			this.compraDetAutoparteService.registrarCompraDetalle(cd);
			
			this.agenciaAutoParteService.agregarStockProducto(this.compraAutoparte.getIdagencia(), this.compraAutoparte.getIdOficina(),cd.getIdAutoparte(),3, cd.getCantIngreso(),cd.getPrecio());
			
			KardexAutoparte kardex = new KardexAutoparte();
			kardex.setIdAutoparte(cd.getIdAutoparte());
			kardex.setIdagencia(this.compraAutoparte.getIdagencia());
			kardex.setIdOficina(this.compraAutoparte.getIdOficina());
			kardex.setTipo("INGRESO");
			kardex.setFRegistro(new Date());
			kardex.setDocRef(this.compraAutoparte.getOrdenCompra());
			kardex.setIngreso(cd.getCantIngreso());
			kardex.setMoneda(this.compraAutoparte.getMoneda());
			kardex.setPrecio(cd.getPrecio());
			// agregar el precio
			
			this.kardexAutoparteService.registrarKardex(kardex);
			
		}
		this.listaCompra = this.compraAutoparteService.findAll();
		log.setAccion("INSERT");
		log.setDescripcion("se registro la compraAutoparte "+ this.compraAutoparte.getOrdenCompra());
		logMB.insertarLog(log);
		
		FacesUtils.showFacesMessage("Compra de autoparte registrada correctamente.",Constante.INFORMACION);
		context.update("sms");
		
		
		
	}

	public CompraAutoparte getCompraAutoparte() {
		return compraAutoparte;
	}

	public List<CompraAutoparte> getListaCompra() {
		return listaCompra;
	}

	public List<CompraAutoparte> getListaCompraFilter() {
		return listaCompraFilter;
	}

	public List<Agencia> getListaAgencias() {
		return listaAgencias;
	}

	public List<Oficina> getListaOficinas() {
		return listaOficinas;
	}

	public List<ProveedorAgencia> getListaProveedor() {
		return listaProveedor;
	}

	public List<AgenciaAutoparte> getListaAgProducto() {
		return listaAgProducto;
	}

	public List<AgenciaAutoparte> getListaAgProductoFilter() {
		return listaAgProductoFilter;
	}

	public List<CompraDetAutoparte> getListaCompraDet() {
		return listaCompraDet;
	}

	public void setCompraAutoparte(CompraAutoparte compraAutoparte) {
		this.compraAutoparte = compraAutoparte;
	}

	public void setListaCompra(List<CompraAutoparte> listaCompra) {
		this.listaCompra = listaCompra;
	}

	public void setListaCompraFilter(List<CompraAutoparte> listaCompraFilter) {
		this.listaCompraFilter = listaCompraFilter;
	}

	public void setListaAgencias(List<Agencia> listaAgencias) {
		this.listaAgencias = listaAgencias;
	}

	public void setListaOficinas(List<Oficina> listaOficinas) {
		this.listaOficinas = listaOficinas;
	}

	public void setListaProveedor(List<ProveedorAgencia> listaProveedor) {
		this.listaProveedor = listaProveedor;
	}

	public void setListaAgProducto(List<AgenciaAutoparte> listaAgProducto) {
		this.listaAgProducto = listaAgProducto;
	}

	public void setListaAgProductoFilter(
			List<AgenciaAutoparte> listaAgProductoFilter) {
		this.listaAgProductoFilter = listaAgProductoFilter;
	}

	public void setListaCompraDet(List<CompraDetAutoparte> listaCompraDet) {
		this.listaCompraDet = listaCompraDet;
	}

	public Boolean getShowTC() {
		return showTC;
	}

	public Log getLog() {
		return log;
	}

	public LogMB getLogMB() {
		return logMB;
	}

	public void setShowTC(Boolean showTC) {
		this.showTC = showTC;
	}

	public void setLog(Log log) {
		this.log = log;
	}

	public void setLogMB(LogMB logMB) {
		this.logMB = logMB;
	}
	
}
