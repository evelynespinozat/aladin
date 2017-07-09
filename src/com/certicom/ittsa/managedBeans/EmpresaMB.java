package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.Empresa;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.services.EmpresaService;
import com.certicom.ittsa.services.MenuServices;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="empresaMB")
@ViewScoped
public class EmpresaMB extends GenericBeans implements Serializable{
	private String titulo;
	private Empresa empresa;
	private List<Empresa> listaEmpresas;
	private List<Empresa> listaFiltroEmpresas;
	private Boolean editar;
	private Boolean blockRUC;
	
	//services
	private EmpresaService empresaService;
	private MenuServices menuServices;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	public EmpresaMB(){;}
	
	@PostConstruct
	public void incia()
	{
		this.editar = Boolean.FALSE;
	
		this.empresaService = new EmpresaService();
		this.menuServices=new MenuServices();
		this.empresa = new Empresa();
		
		 log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		//listando
		try {
			this.listaEmpresas = empresaService.findAllEmpresas();
			int codMenu = menuServices.opcionMenuByPretty("pretty:empresas");
			log.setCod_menu(codMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cancelar(){
		this.empresa = new Empresa();
	}

	public void guardarEmpresa()
	{
		Boolean valido=Boolean.TRUE;
		RequestContext context = RequestContext.getCurrentInstance();  
   	    context.addCallbackParam("esValido", valido );
   	    
		try {
			empresa.setRazonSocial(this.empresa.getRazonSocial().trim());
			if(this.editar)
			{
				this.empresaService.updateEmpresa(empresa);
				 log.setAccion("UPDATE");
	             log.setDescripcion("Se actualiza la empresa: " + empresa.getRazonSocial());
	             logmb.insertarLog(log);
				FacesUtils.showFacesMessage("Empresa actualizada correctamente.",Constante.INFORMACION);
				context.update("sms");
			}else
			{
				this.empresa.setEstado(Boolean.TRUE);
				this.empresaService.crearEmpresa(empresa);
				 log.setAccion("INSERT");
	             log.setDescripcion("Se inserta la empresa: " + empresa.getRazonSocial());
	             logmb.insertarLog(log);
				 FacesUtils.showFacesMessage("Empresa registrada correctamente.",Constante.INFORMACION);
				 context.update("sms");
			}
			listaEmpresas = this.empresaService.findAllEmpresas();
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.empresa = new Empresa();
		this.editar =  Boolean.FALSE;
		
	}
	
	public void cambiarEstado(Empresa emp){
		 
			 emp.setEstado(!emp.isEstado());
		   
		   try {
			   this.empresaService.updateEstadoEmpresa(emp);
			   log.setAccion("CHANGE ESTADO");
	           log.setDescripcion("Se cambio el estado en Empresa: " + emp.getRazonSocial());
	           logmb.insertarLog(log);
			   FacesUtils.showFacesMessage("Estado de empresa actualizado correctamente.",Constante.INFORMACION);
		   } catch (Exception e) {
			   System.out.println("Error:"+e.getMessage());
			   e.printStackTrace();
		   }   
	}
	
	public void eliminarEmpresa()
	{
		try {
			RequestContext context = RequestContext.getCurrentInstance();
	
				this.empresaService.deleteEmpresa(empresa.getRUC());
				FacesUtils.showFacesMessage("Empresa eliminada correctamente.",Constante.INFORMACION);
				
				log.setAccion("DELETE");
				log.setDescripcion("Se elimina la Empresa: " + empresa.getRazonSocial());
				logmb.insertarLog(log);
				
				listaEmpresas = this.empresaService.findAllEmpresas();
				
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.empresa =  new Empresa();
		
	}
	
	public void editarEmpresa(Empresa ag)
	{
		this.editar = Boolean.TRUE;
		this.empresa = ag;
		this.blockRUC = Boolean.TRUE;
		this.titulo = "Modificar empresa";
	}
	
	public void nuevaEmpresa()
	{
		this.editar = Boolean.FALSE;
		this.empresa = new Empresa();
		this.blockRUC = Boolean.FALSE;
		this.titulo = "Registrar nueva empresa";
	}
	
	

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public List<Empresa> getListaEmpresas() {
		return listaEmpresas;
	}

	public void setListaEmpresas(List<Empresa> listaEmpresas) {
		this.listaEmpresas = listaEmpresas;
	}

	public List<Empresa> getListaFiltroEmpresas() {
		return listaFiltroEmpresas;
	}

	public void setListaFiltroEmpresas(List<Empresa> listaFiltroEmpresas) {
		this.listaFiltroEmpresas = listaFiltroEmpresas;
	}

	public Boolean getEditar() {
		return editar;
	}

	public void setEditar(Boolean editar) {
		this.editar = editar;
	}

	public Boolean getBlockRUC() {
		return blockRUC;
	}

	public void setBlockRUC(Boolean blockRUC) {
		this.blockRUC = blockRUC;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	
}

