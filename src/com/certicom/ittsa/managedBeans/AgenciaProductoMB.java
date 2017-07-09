package com.certicom.ittsa.managedBeans;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.AgenciaProducto;
import com.certicom.ittsa.domain.Almacen;
import com.certicom.ittsa.domain.Kardex;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Oficina;
import com.certicom.ittsa.domain.Producto;
import com.certicom.ittsa.domain.Usuario;
import com.certicom.ittsa.services.AgenciaProductoService;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.AlmacenService;
import com.certicom.ittsa.services.KardexService;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.OficinaService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="agenciaProductoMB")
@ViewScoped
public class AgenciaProductoMB extends GenericBeans {
	
	//bean principal
	private AgenciaProducto agenciaProducto;
	
	// filtro para kardex
	private AgenciaProducto ageProdFilter;
	
	@ManagedProperty(value="#{loginMB.usuario}")
	private Usuario usuarioLogin;
	
	private AgenciaService agenciaService;
	private OficinaService oficinaService;
	private AlmacenService almacenService;
	private AgenciaProductoService agenciaProductoService;
	private KardexService kardexService;
	
	private MenuServices menuServices;
	
	
	private List<Agencia> listaAgencias;
	private List<Oficina> listaOficinas;
	private List<Almacen> listAlmacen;
	private List<AgenciaProducto> listAgProducto;
	private List<AgenciaProducto> listaAgenProductos;
	private List<AgenciaProducto> listaAgenProductosFilter;
	
	private boolean cmbAgencia = false;
	private boolean cmbOficina = false;
	
	
	private Producto producto;

	//datos Log
    private Log log;
	private LogMB logmb;
	
	@PostConstruct
	public void inicia(){
		

		this.agenciaProducto = new AgenciaProducto();
		
		this.ageProdFilter = new AgenciaProducto();
				
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		this.producto =(Producto) flash.get("producto");
		
		
		this.agenciaService = new AgenciaService();
		this.oficinaService = new OficinaService();
		this.almacenService = new AlmacenService();
		this.agenciaProductoService = new AgenciaProductoService();
		this.menuServices = new MenuServices();
		this.kardexService = new KardexService();
		
		log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		
		try {
			int codMenu = menuServices.opcionMenuByPretty("pretty:productos");
			log.setCod_menu(codMenu);
			this.listaAgencias = this.agenciaService.listaAgenciasActivas();
			if(getUsuarioLogin().getIdagencia() != 9){
				this.agenciaProducto.setIdagencia(getUsuarioLogin().getIdagencia());
				this.listaOficinas = this.oficinaService.getOficinasxAgencia(this.agenciaProducto.getIdagencia());
				this.cmbAgencia = Boolean.TRUE;
				this.listAgProducto = this.agenciaProductoService.listOfixProductoxAgencia(this.producto.getIdProducto(),getUsuarioLogin().getIdagencia());
			} else{
			if(this.producto != null){
				this.listAgProducto = this.agenciaProductoService.listOfixProducto(this.producto.getIdProducto());
			}
			
			}
			
		} catch (Exception e) {
			System.out.println("Error :"+ e.getMessage());
			e.printStackTrace();
		}
		

	}
	
	
	public void addProductoToOficina() {
		Boolean valido=Boolean.TRUE;
		RequestContext context = RequestContext.getCurrentInstance();  
   	    context.addCallbackParam("esValido", valido );
   	    
		try {
			//validando que no exista el producto en la oficina agencia y almacen asignao
			
			this.agenciaProducto.setCosto(this.producto.getCosto());
			this.agenciaProducto.setCostoUni(this.producto.getCostoUni());
			this.agenciaProducto.setDisgregacion(this.producto.getDesgregacion());
			this.agenciaProducto.setCantDist(this.producto.getCantDist());
			this.agenciaProducto.setCostoxPasj(this.producto.getCostoxPasj());
				
			this.agenciaProducto.setIdProducto(this.producto.getIdProducto());
			System.out.println(agenciaProducto.getIdProducto());
			int cant  = 0;
			cant = this.agenciaProductoService.getCantProdAgeAlmacen(this.agenciaProducto);

			if(cant == 0){
			this.agenciaProducto.setFecha(new Date());
			this.agenciaProducto.setIdProducto(this.producto.getIdProducto());
			this.agenciaProductoService.crearAgenciaProducto(this.agenciaProducto);
			
			Kardex kardex = new Kardex();
			kardex.setIdagencia(this.agenciaProducto.getIdagencia());
			kardex.setIdOficina(this.agenciaProducto.getIdOficina());
			kardex.setIdProducto(this.agenciaProducto.getIdProducto());
			kardex.setIngreso(this.agenciaProducto.getStock());	
			kardex.setTipo("SALDO INICIAL");
			kardex.setFRegistro(new Date());
			kardex.setCosto(this.agenciaProducto.getCosto());
			kardex.setCostoUni(this.agenciaProducto.getCostoUni());
			kardex.setDisgregacion(this.agenciaProducto.getDisgregacion());
			kardex.setCantDist(this.agenciaProducto.getCantDist());
			kardex.setCostoxPasj(this.agenciaProducto.getCostoxPasj());
			this.kardexService.registrarKardex(kardex);
			
			log.setAccion("INSERT");
			log.setDescripcion("Se agregó el producto " + this.producto.getDescripcion()+ "-" +this.producto.getMedida()
								+ " a la oficina " + this.agenciaProducto.getIdOficina());
			logmb.insertarLog(log);
			
			FacesUtils.showFacesMessage("Producto en la oficina registrado correctamente.",Constante.INFORMACION);
			this.listAgProducto = this.agenciaProductoService.listOfixProducto(this.producto.getIdProducto());
		
			} else {
				FacesUtils.showFacesMessage("Producto se encuentra ya registrado.",Constante.ERROR);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	//	this.agenciaProducto = new AgenciaProducto();
		this.agenciaProducto.setStock(null);
	}
	
	public void listarProductosxAgencia(){
		try {
			this.listaAgenProductos = this.agenciaProductoService.listarAgenProductos(this.agenciaProducto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void getOficinasxAgencia(){
		try {
			this.listaOficinas = this.oficinaService.getOficinasxAgencia(this.agenciaProducto.getIdagencia());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void listarAlmacen(){
		try {
			this.listAlmacen = this.almacenService.listAlmacenxOficina(this.agenciaProducto.getIdOficina());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteOficinaFromProduct(){
		try {
			// falta la validacion
//			Kardex k = new Kardex();
//			k.setIdagencia(this.agenciaProducto.getIdagencia());
//			k.setIdOficina(this.agenciaProducto.getIdOficina());
//			k.setIdProducto(this.agenciaProducto.getIdProducto());
//			
//			List<Kardex> cantKardex = this.kardexService.getCantKardexxProducto(k);
//			
//			if(cantKardex.size()<=1){
				this.agenciaProductoService.deleteOficinaFromProduct(this.agenciaProducto.getId());
	//			this.kardexService.deleteKardexbyProducto(k);
				log.setAccion("DELETE");
				log.setDescripcion("Se eliminó el producto " + this.producto.getDescripcion()+ "-" +this.producto.getMedida()
									+ " de la oficina " + this.agenciaProducto.getDesOficina());
				logmb.insertarLog(log);

				
				FacesUtils.showFacesMessage("Producto de la agencia eliminado correctamente.",Constante.INFORMACION);
				
				this.listAgProducto = this.agenciaProductoService.listOfixProducto(this.producto.getIdProducto());
//			} else {
//				FacesUtils.showFacesMessage("El producto ya tiene ingresos en el kardex de esta oficina.",Constante.ERROR);
//			}
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		//this.agenciaProducto = new AgenciaProducto();
		this.agenciaProducto.setStock(null);
		
	}
	
	public String consultarKardex(AgenciaProducto ap){
		String outcome = null;
		try {
    		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
    		flash.put("agenciaProducto", ap);
    		outcome="pretty:consultaKardex";
		} catch (Exception e) {	
			System.out.println("Error:"+e.getMessage());
			e.printStackTrace();
		}
		return outcome;
	}
	
	public String consolidadoKardex(AgenciaProducto ap){
		String outcome = null;
		try {
    		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
    		flash.put("agenciaProducto", ap);
    		outcome="pretty:KardexConsolidado";
		} catch (Exception e) {	
			System.out.println("Error:"+e.getMessage());
			e.printStackTrace();
		}
		return outcome;
	}
	
	public AgenciaProducto getAgenciaProducto() {
		return agenciaProducto;
	}


	public AgenciaService getAgenciaService() {
		return agenciaService;
	}


	public OficinaService getOficinaService() {
		return oficinaService;
	}


	public AlmacenService getAlmacenService() {
		return almacenService;
	}


	public AgenciaProductoService getAgenciaProductoService() {
		return agenciaProductoService;
	}


	public MenuServices getMenuServices() {
		return menuServices;
	}


	public List<Agencia> getListaAgencias() {
		return listaAgencias;
	}


	public List<Oficina> getListaOficinas() {
		return listaOficinas;
	}


	public List<Almacen> getListAlmacen() {
		return listAlmacen;
	}


	public Producto getProducto() {
		return producto;
	}


	public Log getLog() {
		return log;
	}


	public LogMB getLogmb() {
		return logmb;
	}


	public void setAgenciaProducto(AgenciaProducto agenciaProducto) {
		this.agenciaProducto = agenciaProducto;
	}


	public void setAgenciaService(AgenciaService agenciaService) {
		this.agenciaService = agenciaService;
	}


	public void setOficinaService(OficinaService oficinaService) {
		this.oficinaService = oficinaService;
	}


	public void setAlmacenService(AlmacenService almacenService) {
		this.almacenService = almacenService;
	}


	public void setAgenciaProductoService(
			AgenciaProductoService agenciaProductoService) {
		this.agenciaProductoService = agenciaProductoService;
	}


	public void setMenuServices(MenuServices menuServices) {
		this.menuServices = menuServices;
	}


	public void setListaAgencias(List<Agencia> listaAgencias) {
		this.listaAgencias = listaAgencias;
	}


	public void setListaOficinas(List<Oficina> listaOficinas) {
		this.listaOficinas = listaOficinas;
	}


	public void setListAlmacen(List<Almacen> listAlmacen) {
		this.listAlmacen = listAlmacen;
	}


	public void setProducto(Producto producto) {
		this.producto = producto;
	}


	public void setLog(Log log) {
		this.log = log;
	}


	public void setLogmb(LogMB logmb) {
		this.logmb = logmb;
	}


	public List<AgenciaProducto> getListAgProducto() {
		return listAgProducto;
	}


	public void setListAgProducto(List<AgenciaProducto> listAgProducto) {
		this.listAgProducto = listAgProducto;
	}


	public AgenciaProducto getAgeProdFilter() {
		return ageProdFilter;
	}


	public List<AgenciaProducto> getListaAgenProductos() {
		return listaAgenProductos;
	}


	public void setAgeProdFilter(AgenciaProducto ageProdFilter) {
		this.ageProdFilter = ageProdFilter;
	}


	public void setListaAgenProductos(List<AgenciaProducto> listaAgenProductos) {
		this.listaAgenProductos = listaAgenProductos;
	}


	public List<AgenciaProducto> getListaAgenProductosFilter() {
		return listaAgenProductosFilter;
	}


	public void setListaAgenProductosFilter(
			List<AgenciaProducto> listaAgenProductosFilter) {
		this.listaAgenProductosFilter = listaAgenProductosFilter;
	}


	public Usuario getUsuarioLogin() {
		return usuarioLogin;
	}


	public void setUsuarioLogin(Usuario usuarioLogin) {
		this.usuarioLogin = usuarioLogin;
	}


	public boolean isCmbAgencia() {
		return cmbAgencia;
	}


	public void setCmbAgencia(boolean cmbAgencia) {
		this.cmbAgencia = cmbAgencia;
	}


	public boolean isCmbOficina() {
		return cmbOficina;
	}


	public void setCmbOficina(boolean cmbOficina) {
		this.cmbOficina = cmbOficina;
	}

	
	
	
}
