package com.certicom.ittsa.managedBeans;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.AgenciaAutoparte;
import com.certicom.ittsa.domain.AgenciaProducto;
import com.certicom.ittsa.domain.Almacen;
import com.certicom.ittsa.domain.Autoparte;
import com.certicom.ittsa.domain.Kardex;
import com.certicom.ittsa.domain.KardexAutoparte;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Oficina;
import com.certicom.ittsa.domain.Producto;
import com.certicom.ittsa.services.AgenciaAutoParteService;
import com.certicom.ittsa.services.AgenciaProductoService;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.AlmacenService;
import com.certicom.ittsa.services.KardexAutoparteService;
import com.certicom.ittsa.services.KardexService;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.OficinaService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="agenciaAutoparteMB")
@ViewScoped
public class AgenciaAutoparteMB extends GenericBeans {
	
	//bean principal
	private AgenciaAutoparte agenciaAutoparte;
	
	// filtro para kardex
	private AgenciaAutoparte ageAutoFilter;
	
	private AgenciaService agenciaService;
	private OficinaService oficinaService;
	private AlmacenService almacenService;
	private AgenciaAutoParteService agenciaAutoParteService;
	//cambiar
	private KardexAutoparteService kardexAutoparteService;
	
	private MenuServices menuServices;
	
	
	private List<Agencia> listaAgencias;
	private List<Oficina> listaOficinas;
	private List<Almacen> listAlmacen;
	private List<AgenciaAutoparte> listAgProducto;
	private List<AgenciaAutoparte> listaAgenProductos;
	private List<AgenciaAutoparte> listaAgenProductosFilter;
	
	
	private Autoparte autoparte;

	//datos Log
    private Log log;
	private LogMB logmb;
	
	@PostConstruct
	public void inicia(){

		this.agenciaAutoparte = new AgenciaAutoparte();
		
		this.ageAutoFilter = new AgenciaAutoparte();
				
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		this.autoparte =(Autoparte) flash.get("autoparte");
		
		
		this.agenciaService = new AgenciaService();
		this.oficinaService = new OficinaService();
		this.almacenService = new AlmacenService();
		this.agenciaAutoParteService = new AgenciaAutoParteService();
		this.menuServices = new MenuServices();
		this.kardexAutoparteService = new KardexAutoparteService();
		
		log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		
		try {
			int codMenu = menuServices.opcionMenuByPretty("pretty:autoparte");
			log.setCod_menu(codMenu);
			this.listaAgencias = this.agenciaService.listaAgenciasActivas();
			
			if(this.autoparte != null){
				this.listAgProducto = this.agenciaAutoParteService.listOfixProducto(this.autoparte.getIdAutoparte());
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
			//validando que no exista el autoparte en la oficina agencia y almacen asignao
			
			this.agenciaAutoparte.setIdAutoparte(this.autoparte.getIdAutoparte());
			int cant  = 0;
			cant = this.agenciaAutoParteService.getCantProdAgeAlmacen(this.agenciaAutoparte);

			if(cant == 0){
			this.agenciaAutoparte.setFecha(new Date());
			this.agenciaAutoparte.setIdAutoparte(this.autoparte.getIdAutoparte());
			this.agenciaAutoParteService.crearAgenciaAutoparte(this.agenciaAutoparte);		
			KardexAutoparte kardex = new KardexAutoparte();
			kardex.setIdagencia(this.agenciaAutoparte.getIdagencia());
			kardex.setIdOficina(this.agenciaAutoparte.getIdOficina());
			kardex.setIdAutoparte(this.agenciaAutoparte.getIdAutoparte());
			kardex.setIngreso(this.agenciaAutoparte.getStock());	
			kardex.setTipo("SALDO INICIAL");
			kardex.setFRegistro(new Date());
			this.kardexAutoparteService.registrarKardex(kardex);
			
			log.setAccion("INSERT");
			log.setDescripcion("Se agregó el autoparte " + this.autoparte.getDescripcion()
								+ " a la oficina " + this.agenciaAutoparte.getIdOficina());
			logmb.insertarLog(log);
			
			FacesUtils.showFacesMessage("Autoparte en oficina registrada correctamente.",Constante.INFORMACION);
			this.listAgProducto = this.agenciaAutoParteService.listOfixProducto(this.autoparte.getIdAutoparte());
		
			} else {
				FacesUtils.showFacesMessage("Autoparte se encuentra ya registrada.",Constante.ERROR);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.agenciaAutoparte = new AgenciaAutoparte();
	}
	
	public void listarProductosxAgencia(){
		try {
			this.listaAgenProductos = this.agenciaAutoParteService.listarAgenProductos(this.agenciaAutoparte);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void getOficinasxAgencia(){
		try {
			this.listaOficinas = this.oficinaService.getOficinasxAgencia(this.agenciaAutoparte.getIdagencia());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void listarAlmacen(){
		try {
			this.listAlmacen = this.almacenService.listAlmacenxOficina(this.agenciaAutoparte.getIdOficina());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteOficinaFromProduct(){
		try {
			// falta la validacion
//			Kardex k = new Kardex();
//			k.setIdagencia(this.agenciaAutoparte.getIdagencia());
//			k.setIdOficina(this.agenciaAutoparte.getIdOficina());
//			k.setIdProducto(this.agenciaAutoparte.getIdProducto());
//			
//			List<Kardex> cantKardex = this.kardexService.getCantKardexxProducto(k);
//			
//			if(cantKardex.size()<=1){
				this.agenciaAutoParteService.deleteOficinaFromProduct(this.agenciaAutoparte.getId());
	//			this.kardexService.deleteKardexbyProducto(k);
				log.setAccion("DELETE");
				log.setDescripcion("Se eliminó el autoparte " + this.autoparte.getDescripcion()+ "-"
									+ " de la oficina " + this.agenciaAutoparte.getDesOficina());
				logmb.insertarLog(log);

				
				FacesUtils.showFacesMessage("Autoparte de la agencia eliminada correctamente.",Constante.INFORMACION);
				
				this.listAgProducto = this.agenciaAutoParteService.listOfixProducto(this.autoparte.getIdAutoparte());
//			} else {
//				FacesUtils.showFacesMessage("El producto ya tiene ingresos en el kardex de esta oficina.",Constante.ERROR);
//			}
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.agenciaAutoparte = new AgenciaAutoparte();
		
		
	}
	
	public String consultarKardex(AgenciaAutoparte ap){
		String outcome = null;
		try {
    		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
    		flash.put("agenciaAutoparte", ap);
    		outcome="pretty:consultaKardexAutoparte";
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
    		flash.put("agenciaAutoparte", ap);
    		outcome="pretty:KardexConsolidado";
		} catch (Exception e) {	
			System.out.println("Error:"+e.getMessage());
			e.printStackTrace();
		}
		return outcome;
	}


	public AgenciaAutoparte getAgenciaAutoparte() {
		return agenciaAutoparte;
	}


	public AgenciaAutoparte getAgeAutoFilter() {
		return ageAutoFilter;
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


	public List<AgenciaAutoparte> getListAgProducto() {
		return listAgProducto;
	}


	public List<AgenciaAutoparte> getListaAgenProductos() {
		return listaAgenProductos;
	}


	public List<AgenciaAutoparte> getListaAgenProductosFilter() {
		return listaAgenProductosFilter;
	}


	public Autoparte getAutoparte() {
		return autoparte;
	}


	public void setAgenciaAutoparte(AgenciaAutoparte agenciaAutoparte) {
		this.agenciaAutoparte = agenciaAutoparte;
	}


	public void setAgeAutoFilter(AgenciaAutoparte ageAutoFilter) {
		this.ageAutoFilter = ageAutoFilter;
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


	public void setListAgProducto(List<AgenciaAutoparte> listAgProducto) {
		this.listAgProducto = listAgProducto;
	}


	public void setListaAgenProductos(List<AgenciaAutoparte> listaAgenProductos) {
		this.listaAgenProductos = listaAgenProductos;
	}


	public void setListaAgenProductosFilter(
			List<AgenciaAutoparte> listaAgenProductosFilter) {
		this.listaAgenProductosFilter = listaAgenProductosFilter;
	}


	public void setAutoparte(Autoparte autoparte) {
		this.autoparte = autoparte;
	}
	

	
	
}
