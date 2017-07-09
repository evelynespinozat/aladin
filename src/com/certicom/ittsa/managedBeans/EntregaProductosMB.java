package com.certicom.ittsa.managedBeans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Almacen;
import com.certicom.ittsa.domain.Kardex;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Oficina;
import com.certicom.ittsa.domain.Programacion;
import com.certicom.ittsa.domain.SalidaDetalle;
import com.certicom.ittsa.services.AgenciaProductoService;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.AlmacenService;
import com.certicom.ittsa.services.KardexService;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.OficinaService;
import com.certicom.ittsa.services.SalidaDetalleService;
import com.certicom.ittsa.services.SalidaService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name = "entregaProductosMB")
@ViewScoped
public class EntregaProductosMB extends GenericBeans {
	
	private Programacion programacion;
	
	private AgenciaService agenciaService;
	private OficinaService oficinaService;
	private AlmacenService almacenService;
	private MenuServices menuServices;
	private SalidaDetalleService salidaDetalleService;
	private AgenciaProductoService agenciaProductoService;
	private KardexService kardexService;
	private SalidaService salidaService;
	
	private List<Agencia> listaAgencias;
	private List<Oficina> listaOficinas;
	private List<Almacen> listAlmacen;
	private List<SalidaDetalle> listLlegadaProductos;
	
	private Integer idAgencia;
	private Integer idOficina;
	private Integer idAlmacen;
	
	private Log log;
	private LogMB logmb;
	
	@PostConstruct
	public void inicia(){
		
		Flash  flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		this.programacion = (Programacion)flash.get("programacion");
		
		this.agenciaService = new AgenciaService();
		this.oficinaService = new OficinaService();
		this.almacenService = new AlmacenService();
		this.menuServices = new MenuServices();
		this.salidaDetalleService = new SalidaDetalleService();
		this.agenciaProductoService = new AgenciaProductoService();
		this.kardexService = new KardexService();
		this.salidaService = new SalidaService();
		
		this.listLlegadaProductos = new ArrayList<SalidaDetalle>();
		
		log = (Log) getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		try {
			int codMenu = menuServices.opcionMenuByPretty("pretty:registroLlegadaBus");
			log.setCod_menu(codMenu);
			this.listaAgencias = this.agenciaService.listaAgenciasActivas();
			this.listLlegadaProductos = this.salidaDetalleService.listLLegadaProductosxProg(this.programacion.getIdProgramacion());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void getOficinasxAgencia(){
		try {
			this.listaOficinas = this.oficinaService.getOficinasxAgencia(this.idAgencia);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void listarAlmacen(){
		try {
			this.listAlmacen = this.almacenService.listAlmacenxOficina(this.idOficina);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String registrarLlegadaProductos(){
		
		String outcome ="";
		try {
		
		Integer cant = 0;
		String productos =	"";
		int cont= 0;
		
		for (int j = 0; j < this.listLlegadaProductos.size(); j++) {
			
			SalidaDetalle sd = this.listLlegadaProductos.get(j);
			System.out.println(this.idAgencia);
			System.out.println(this.idOficina);
			System.out.println(this.idAlmacen);
			System.out.println(sd.getIdProducto());
			
			cant= this.agenciaProductoService.cantProductoxAgeOfi(this.idAgencia,this.idOficina,this.idAlmacen,sd.getIdProducto());
			if(cant ==0){
				productos += " \"" + sd.getDesProducto() + "\" ";
				cont++;
			}
			
		}
		
		if(cont == 0){
			System.out.println("all products registered");
			
			for (int i = 0; i < this.listLlegadaProductos.size(); i++) {
				SalidaDetalle sd = this.listLlegadaProductos.get(i);
				Integer cantConsumida = 0;
					if(sd.getCantLLegada() > 0){
						//actualizando el stock original en la oficina
						this.agenciaProductoService.agregarStockProducto(this.idAgencia, this.idOficina, sd.getIdProducto(), this.idAlmacen, sd.getCantLLegada());
						
						// agregando al kardex
						Kardex k = new Kardex();
						k.setIdProducto(sd.getIdProducto());
						k.setIdagencia(getIdAgencia());
						k.setIdOficina(getIdOficina());
						k.setTipo("REINGRESO");
						k.setIngreso(sd.getCantLLegada());
						k.setFRegistro(new Date());
						k.setIdProgramacion(this.programacion.getIdProgramacion());
						k.setCosto(sd.getCosto());
						k.setCostoUni(sd.getCostoUni());
						k.setDisgregacion(sd.getDisgregacion());
						k.setCantDist(sd.getCantDist());
						k.setCostoxPasj(sd.getCostoxPasj());
						this.kardexService.registrarKardex(k);
					
						cantConsumida = sd.getCantSalida() - sd.getCantLLegada();
						
					}
					// actualizando el estado en SalidaDetalle a 1
					this.salidaDetalleService.actualizarEstadoEntrega(sd.getIDSalDet(), "1",cantConsumida);
			}
			
			this.salidaService.actualizarFlagLLegada(this.programacion.getIdProgramacion(), "1");
			
			
			FacesUtils.showFacesMessage("Llegadas de los producto registradas correctamente.",Constante.INFORMACION);
			outcome ="pretty:registroLlegadaBus";
			
		} else {
			FacesUtils.showFacesMessage("Se debe registrar el/los productos(s)  " +productos + " en la Agencia , Oficina "
					+ "y Almacen respectivo." ,Constante.ERROR );
			outcome = null;
		}
		
		//this.listLlegadaProductos = this.salidaDetalleService.listLLegadaProductosxProg(this.programacion.getIdProgramacion());
		
	} catch (Exception e) {
		e.printStackTrace();
	}
		return outcome;
		
	}
	
	

	public Programacion getProgramacion() {
		return programacion;
	}

	public void setProgramacion(Programacion programacion) {
		this.programacion = programacion;
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

	public void setListaAgencias(List<Agencia> listaAgencias) {
		this.listaAgencias = listaAgencias;
	}

	public void setListaOficinas(List<Oficina> listaOficinas) {
		this.listaOficinas = listaOficinas;
	}

	public void setListAlmacen(List<Almacen> listAlmacen) {
		this.listAlmacen = listAlmacen;
	}


	public List<SalidaDetalle> getListLlegadaProductos() {
		return listLlegadaProductos;
	}

	public void setListLlegadaProductos(List<SalidaDetalle> listLlegadaProductos) {
		this.listLlegadaProductos = listLlegadaProductos;
	}

	public Integer getIdAgencia() {
		return idAgencia;
	}

	public Integer getIdOficina() {
		return idOficina;
	}

	public Integer getIdAlmacen() {
		return idAlmacen;
	}

	public void setIdAgencia(Integer idAgencia) {
		this.idAgencia = idAgencia;
	}

	public void setIdOficina(Integer idOficina) {
		this.idOficina = idOficina;
	}

	public void setIdAlmacen(Integer idAlmacen) {
		this.idAlmacen = idAlmacen;
	}
	
	
	
	
}
