package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Medida;
import com.certicom.ittsa.services.MedidaService;
import com.certicom.ittsa.services.MenuServices;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="medidaMB")
@ViewScoped
public class MedidaMB extends GenericBeans implements Serializable{
	private String titulo;
	private Medida medida;
	private List<Medida> listaMedidas;
	private List<Medida> listaFiltroMedidas;
	private Boolean editar;
	
	//services
	private MedidaService medidaSevice;
	private MenuServices menuServices;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	public MedidaMB(){;}
	
	@PostConstruct
	public void incia()
	{
		this.editar = Boolean.FALSE;
	
		this.medidaSevice = new MedidaService();
		this.menuServices=new MenuServices();
		this.medida = new Medida();
		
		 log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		//listando
		try {
			this.listaMedidas = medidaSevice.findAll();
			int codMenu = menuServices.opcionMenuByPretty("pretty:medida");
			log.setCod_menu(codMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cancelar(){
		this.medida = new Medida();
	}

	public void guardarMedida()
	{
		Boolean valido=Boolean.TRUE;
		RequestContext context = RequestContext.getCurrentInstance();  
   	    context.addCallbackParam("esValido", valido );
   	    
		try {
			if(this.editar)
			{
				this.medidaSevice.actualizarMedida(medida);
				 log.setAccion("UPDATE");
	             log.setDescripcion("Se actualiza la medida: " + medida.getDescripcion());
	             logmb.insertarLog(log);
				FacesUtils.showFacesMessage("Medida actualizada correctamente.",Constante.INFORMACION);
				context.update("sms");
			}else
			{
				this.medidaSevice.crearMedida(medida);
				 log.setAccion("INSERT");
	             log.setDescripcion("Se inserta la medida: " + medida.getDescripcion());
	             logmb.insertarLog(log);
				 FacesUtils.showFacesMessage("Medida registrada correctamente.",Constante.INFORMACION);
				 context.update("sms");
			}
			listaMedidas = this.medidaSevice.findAll();
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.medida = new Medida();
		this.editar =  Boolean.FALSE;
		
	}
	
	public void cambiarEstado(Medida med){
		
		   if(med.isEstado()){
			   //System.out.println("es igual a uno se pone a cero");
			   med.setEstado(Boolean.FALSE);
			   //sistem.setInd_activo(new Integer(0));
		   }else{
			   //System.out.println("es igual a cero");
			   med.setEstado(Boolean.TRUE);
			   //sistem.setInd_activo(new Integer(1));
		   }
		   
		   try {
			   this.medidaSevice.actualizarMedida(med);
			   log.setAccion("CHANGE ESTADO");
	           log.setDescripcion("Se cambio el estado de la Medida: " + med.getDescripcion());
	           logmb.insertarLog(log);
			   FacesUtils.showFacesMessage("Estado de medida actualizado correctamente.",Constante.INFORMACION);
		   } catch (Exception e) {
			   System.out.println("Error:"+e.getMessage());
			   e.printStackTrace();
		   }   
	}
	
	public void eliminarMedida()
	{
		try {
			
				this.medidaSevice.eliminarMedida(medida.getCodigoMedida());
				listaMedidas.remove(medida);
				FacesUtils.showFacesMessage("Medida eliminada correctamente.",Constante.INFORMACION);
				
				log.setAccion("DELETE");
				log.setDescripcion("Se elimina la Medida: " + medida.getDescripcion());
				logmb.insertarLog(log);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.medida =  new Medida();
		
	}
	
	public void editarMedida(Medida ag)
	{
		this.editar = Boolean.TRUE;
		this.medida = ag;
		this.titulo = "Modificar medida";
	}
	
	public void nuevaMedida()
	{
		this.editar = Boolean.FALSE;
		this.medida = new Medida();
		this.titulo = "Registrar nueva medida";
	}

	public Medida getMedida() {
		return medida;
	}

	public List<Medida> getListaMedidas() {
		return listaMedidas;
	}

	public List<Medida> getListaFiltroMedidas() {
		return listaFiltroMedidas;
	}

	public Boolean getEditar() {
		return editar;
	}

	public void setMedida(Medida medida) {
		this.medida = medida;
	}

	public void setListaMedidas(List<Medida> listaMedidas) {
		this.listaMedidas = listaMedidas;
	}

	public void setListaFiltroMedidas(List<Medida> listaFiltroMedidas) {
		this.listaFiltroMedidas = listaFiltroMedidas;
	}

	public void setEditar(Boolean editar) {
		this.editar = editar;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	
}

