package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.AgenciaProducto;
import com.certicom.ittsa.domain.Compra;
import com.certicom.ittsa.domain.CompraDetalle;
import com.certicom.ittsa.domain.Kardex;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Oficina;
import com.certicom.ittsa.domain.ProveedorAgencia;
import com.certicom.ittsa.services.AgenciaProductoService;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.CompraDetalleService;
import com.certicom.ittsa.services.CompraService;
import com.certicom.ittsa.services.KardexService;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.OficinaService;
import com.certicom.ittsa.services.ProductoService;
import com.certicom.ittsa.services.ProveedorAgenciaService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name = "compraMB")
@ViewScoped
public class CompraMB extends GenericBeans implements Serializable {
	
	private Compra compra;
	
	private List<Compra> listaCompra;
	private List<Compra> listaCompraFilter;
	private List<Agencia> listaAgencias;
	private List<Oficina> listaOficinas;
	private List<ProveedorAgencia> listaProveedor;
	private List<AgenciaProducto> listaAgProducto;
	private List<AgenciaProducto> listaAgProductoFilter;
	private List<CompraDetalle> listaCompraDet;
	
	private MenuServices menuServices;
	private CompraService compraService;
	private AgenciaService agenciaService;
	private OficinaService oficinaService;  
	private ProveedorAgenciaService proveedorAgenciaService;
	private AgenciaProductoService agenciaProductoService;
	private CompraDetalleService compraDetalleService;
	private KardexService kardexService;
	private ProductoService productoService;
	
	private Log log;
	private LogMB logMB;
	
	@PostConstruct
	public void init(){
		this.compra = new Compra();
		
		this.menuServices = new MenuServices();
		this.compraService = new CompraService();
		this.agenciaService = new AgenciaService();
		this.oficinaService = new OficinaService();
		this.proveedorAgenciaService = new ProveedorAgenciaService();
		this.agenciaProductoService = new AgenciaProductoService();
		this.compraDetalleService = new CompraDetalleService();
		this.kardexService = new KardexService();
		this.productoService = new ProductoService();

		
		log = (Log)getSpringBean(Constante.SESSION_LOG);
		logMB = new LogMB();
		
		try {
			int cod_menu = this.menuServices.opcionMenuByPretty("pretty:compra");
			log.setCod_menu(cod_menu);
			this.listaCompra = this.compraService.findAll();
			this.listaAgencias = this.agenciaService.listaAgenciasActivas();
			this.listaCompraDet = new ArrayList<CompraDetalle>();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void getOficinasxAgencia(){
		try {
			this.listaOficinas = this.oficinaService.getOficinasxAgencia(this.compra.getIdagencia());
			this.listaAgProducto = new ArrayList<AgenciaProducto>();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getProveedxOficina(){
		try {
			this.listaProveedor = this.proveedorAgenciaService.listarProveedorxOficina(this.compra.getIdOficina());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actualizarCosto(AgenciaProducto ageProducto){
		try {
			RequestContext context = RequestContext.getCurrentInstance();
			this.productoService.actualizarCostoProducto(ageProducto.getIdProducto(), ageProducto.getCostoUni());
			FacesUtils.showFacesMessage("Costo actualizado correctamente.",Constante.INFORMACION);
			context.update("msgProdAge");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void xxx(AgenciaProducto ag){
		System.out.println("juan pajero");
	}
	
	public void listarProductosxAgencia(){
		try {
			AgenciaProducto filtro = new AgenciaProducto();
			filtro.setIdagencia(this.compra.getIdagencia());
			filtro.setIdOficina(this.compra.getIdOficina());
			filtro.setIdAlmacen(1);
			this.listaAgProducto = this.agenciaProductoService.listarAgenProductos(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void agregarProductoCompra(AgenciaProducto ap){
		int result = 0;
		for (int i = 0; i < listaCompraDet.size(); i++) {
			if(listaCompraDet.get(i).getIdProducto() == ap.getIdProducto()){
				result++;
				break;
			}
		}
		if(result == 0){
			CompraDetalle cd = new CompraDetalle();
			cd.setDesProducto(ap.getDesProducto());
			cd.setDesMedida(ap.getDesMedida());
			cd.setCantIngreso(ap.getCantIngreso());
			cd.setIdProducto(ap.getIdProducto());
			cd.setIdProveedor(this.compra.getIdProveedor());
			listaCompraDet.add(cd);
		}
		
	}
	
	public void agregapd(){
		System.out.println("entro pro fin");
	}
	
	public void removerProductodeLista(Integer IdProducto){
		for (int i = 0; i < listaCompraDet.size(); i++) {
			if(listaCompraDet.get(i).getIdProducto() == IdProducto){
				this.listaCompraDet.remove(i);
				break;
			}
		}
		
	}
	
	public void nuevaCompra(){
		this.compra = new Compra();
		this.listaAgProducto = new ArrayList<AgenciaProducto>();
		this.listaCompraDet = new ArrayList<CompraDetalle>();
		this.listaOficinas = new ArrayList<Oficina>();
		this.listaProveedor = new ArrayList<ProveedorAgencia>();
	}
	
	
	public void registrarCompra(){
		Boolean valido=Boolean.TRUE;
		RequestContext context = RequestContext.getCurrentInstance();  
   	    context.addCallbackParam("esValido", valido );
		
		this.compra.setfRegistro(new Date());
		this.compraService.registrarCompra(this.compra);
		Integer IdCompra = this.compraService.getlastId();
		for (int i = 0; i < this.listaCompraDet.size(); i++) {
			CompraDetalle cd = this.listaCompraDet.get(i);
			cd.setIdCompra(IdCompra);
			cd.setfRegistro(new Date());
			
			this.compraDetalleService.registrarCompraDetalle(cd);
			
			this.agenciaProductoService.agregarStockProducto(this.compra.getIdagencia(), this.compra.getIdOficina(),cd.getIdProducto(),1, cd.getCantIngreso());
			
			Kardex kardex = new Kardex();
			kardex.setIdProducto(cd.getIdProducto());
			kardex.setIdagencia(this.compra.getIdagencia());
			kardex.setIdOficina(this.compra.getIdOficina());
			kardex.setTipo("INGRESO");
			kardex.setFRegistro(new Date());
			kardex.setDocRef(this.compra.getOrdenCompra());
			kardex.setIngreso(cd.getCantIngreso());
			
			this.kardexService.registrarKardex(kardex);
			
		}
		this.listaCompra = this.compraService.findAll();
		log.setAccion("INSERT");
		log.setDescripcion("se registro la compra "+ this.compra.getOrdenCompra());
		logMB.insertarLog(log);
		
		FacesUtils.showFacesMessage("Compra registrada correctamente.",Constante.INFORMACION);
		context.update("sms");
		
		
		
	}

	public Compra getCompra() {
		return compra;
	}

	public List<Compra> getListaCompra() {
		return listaCompra;
	}

	public List<Compra> getListaCompraFilter() {
		return listaCompraFilter;
	}

	public void setCompra(Compra compra) {
		this.compra = compra;
	}

	public void setListaCompra(List<Compra> listaCompra) {
		this.listaCompra = listaCompra;
	}

	public void setListaCompraFilter(List<Compra> listaCompraFilter) {
		this.listaCompraFilter = listaCompraFilter;
	}

	public List<Agencia> getListaAgencias() {
		return listaAgencias;
	}

	public void setListaAgencias(List<Agencia> listaAgencias) {
		this.listaAgencias = listaAgencias;
	}

	public List<Oficina> getListaOficinas() {
		return listaOficinas;
	}

	public void setListaOficinas(List<Oficina> listaOficinas) {
		this.listaOficinas = listaOficinas;
	}

	public List<ProveedorAgencia> getListaProveedor() {
		return listaProveedor;
	}

	public void setListaProveedor(List<ProveedorAgencia> listaProveedor) {
		this.listaProveedor = listaProveedor;
	}

	public List<AgenciaProducto> getListaAgProducto() {
		return listaAgProducto;
	}

	public void setListaAgProducto(List<AgenciaProducto> listaAgProducto) {
		this.listaAgProducto = listaAgProducto;
	}

	public List<AgenciaProducto> getListaAgProductoFilter() {
		return listaAgProductoFilter;
	}

	public void setListaAgProductoFilter(List<AgenciaProducto> listaAgProductoFilter) {
		this.listaAgProductoFilter = listaAgProductoFilter;
	}

	public List<CompraDetalle> getListaCompraDet() {
		return listaCompraDet;
	}

	public void setListaCompraDet(List<CompraDetalle> listaCompraDet) {
		this.listaCompraDet = listaCompraDet;
	}

	
	
	
	
}
