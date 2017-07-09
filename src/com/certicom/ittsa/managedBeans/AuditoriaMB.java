package com.certicom.ittsa.managedBeans;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.event.DateSelectEvent;
import org.springframework.beans.factory.ListableBeanFactory;

import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Usuario;
import com.certicom.ittsa.services.LogServices;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.UsuarioServices;
import com.ocpsoft.shade.org.apache.commons.beanutils.PropertyUtils;

@ManagedBean(name="auditoriaMB")
@ViewScoped
public class AuditoriaMB {
	
	private List<Usuario> listaUsuarios;
	private List<String> listaAccion;
	
	@PostConstruct
	public void incia()
	{
		
		logServices = new LogServices();
		this.listaUsuarios = this.usuarioServices.listarUsuariosActivos();
		this.listaAccion = new ArrayList<>();
		this.listaAccion.add("INSERT");
		this.listaAccion.add("DELETE");
		this.listaAccion.add("UPDATE");
		this.listaAccion.add("LOGIN");
		this.listaAccion.add("CHANGE ESTADO");
		
		try {
			this.listaAniosLog = this.logServices.AniosLog();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public AuditoriaMB(){
		logServices = new LogServices();
		usuarioServices = new UsuarioServices();
		menuServices = new MenuServices();
		
	}
	
	public void setearBandRend(AjaxBehaviorEvent ajaxBehaviorEvent) throws Exception{
		bandRend = true;		
	}
	
	public void setearMes(AjaxBehaviorEvent ajaxBehaviorEvent) throws Exception{
		System.out.println("Codigo Mes:" + getMes());
		/*tipo_Documento = new Tipo_Documento();
		tipo_Documento = tipoDocServices.findTipo_Documento(getCod_tipodoc());
		setBean(tipo_Documento);
		Tipo_Documento tipoDocumentoEnSesion = (Tipo_Documento)getSpringBean(Constante.SESSION_TIPODOC);
		PropertyUtils.copyProperties(tipoDocumentoEnSesion, tipo_Documento);	*/	
		parametro = 0;
		valorParametro = "";
		listLog = new ArrayList<>();
	}
	
	public void buscarLog() throws Exception {

		this.listLogfilter = null;
		
		switch (mes) {
		
		case 1:
			if (getParametro() == 0)
				obtenerListEnero();
			else if (getParametro() == 1) {
				listLog = logServices.getLog_Ene_By_Login(getValorParametro().toLowerCase(),this.anio); 
			} else if (getParametro() == 2) {
				listLog = logServices.getLog_Ene_By_Accion(getValorParametro().toUpperCase(),this.anio);
			} else if (getParametro() == 3) {
				listLog = logServices.getLog_Ene_By_OpcionMenu(getValorParametro(),this.anio);
			}
			break;
		case 2: // obtenerListFebrero(); break;
			if (getParametro() == 0)
				obtenerListFebrero();
			else if (getParametro() == 1) {
				listLog = logServices.getLog_Feb_By_Login(getValorParametro().toLowerCase(),this.anio); 
			} else if (getParametro() == 2) {
				listLog = logServices.getLog_Feb_By_Accion(getValorParametro().toUpperCase(),this.anio);
			} else if (getParametro() == 3) {
				listLog = logServices.getLog_Feb_By_OpcionMenu(getValorParametro(),this.anio);
			}
			break;
		case 3: // obtenerListMarzo(); break;
			if (getParametro() == 0)
				obtenerListMarzo();
			else if (getParametro() == 1) {
				
				listLog = logServices.getLog_Mar_By_Login(getValorParametro().toLowerCase(),this.anio);
				
			} else if (getParametro() == 2) {
				listLog = logServices.getLog_Mar_By_Accion(getValorParametro().toUpperCase(),this.anio);
			} else if (getParametro() == 3) {
				listLog = logServices.getLog_Mar_By_OpcionMenu(getValorParametro(),this.anio);
			}
			break;
		case 4: // obtenerListAbril(); break;
			if (getParametro() == 0)
				obtenerListAbril();
			else if (getParametro() == 1) {
				listLog = logServices.getLog_Abr_By_Login(getValorParametro(),this.anio);
			} else if (getParametro() == 2) {
				listLog = logServices.getLog_Abr_By_Accion(getValorParametro().toUpperCase(),this.anio);
			} else if (getParametro() == 3) {
				listLog = logServices.getLog_Abr_By_OpcionMenu(getValorParametro(),this.anio);
			}
			break;
		case 5: // obtenerListMayo(); break;
			if (getParametro() == 0)
				obtenerListMayo();
			else if (getParametro() == 1) {
				listLog = logServices.getLog_May_By_Login(getValorParametro(),this.anio);
			} else if (getParametro() == 2) {
				listLog = logServices.getLog_May_By_Accion(getValorParametro().toUpperCase(),this.anio);
			} else if (getParametro() == 3) {
				listLog = logServices.getLog_May_By_OpcionMenu(getValorParametro(),this.anio);
			}
			break;
		case 6: // obtenerListJunio(); break;
			if (getParametro() == 0)
				obtenerListJunio();
			else if (getParametro() == 1) {
				listLog = logServices.getLog_Jun_By_Login(getValorParametro(),this.anio);
			} else if (getParametro() == 2) {
				listLog = logServices.getLog_Jun_By_Accion(getValorParametro().toUpperCase(),this.anio);
			} else if (getParametro() == 3) {
				listLog = logServices.getLog_Jun_By_OpcionMenu(getValorParametro(),this.anio);
			}
			break;
		case 7: // obtenerListJulio(); break;
			if (getParametro() == 0)
				obtenerListJulio();
			else if (getParametro() == 1) {
				listLog = logServices.getLog_Jul_By_Login(getValorParametro(),this.anio);
			} else if (getParametro() == 2) {
				listLog = logServices.getLog_Jul_By_Accion(getValorParametro().toUpperCase(),this.anio);
			} else if (getParametro() == 3) {
				listLog = logServices.getLog_Jul_By_OpcionMenu(getValorParametro(),this.anio);
			}
			break;
		case 8: // obtenerListAgosto(); break;
			if (getParametro() == 0)
				obtenerListAgosto();
			else if (getParametro() == 1) {
				listLog = logServices.getLog_Ago_By_Login(getValorParametro(),this.anio);
			} else if (getParametro() == 2) {
				listLog = logServices.getLog_Ago_By_Accion(getValorParametro().toUpperCase(),this.anio);
			} else if (getParametro() == 3) {
				listLog = logServices.getLog_Ago_By_OpcionMenu(getValorParametro(),this.anio);
			}
			break;
		case 9: // obtenerListSetiembre(); break;
			if (getParametro() == 0)
				obtenerListSetiembre();
			else if (getParametro() == 1) {
				listLog = logServices.getLog_Set_By_Login(getValorParametro(),this.anio);
			} else if (getParametro() == 2) {
				listLog = logServices.getLog_Set_By_Accion(getValorParametro().toUpperCase(),this.anio);
			} else if (getParametro() == 3) {
				listLog = logServices
						.getLog_Set_By_OpcionMenu(getValorParametro(),this.anio);
			}
			break;
		case 10: // obtenerListOctubre(); break;
			if (getParametro() == 0)
				obtenerListOctubre();
			else if (getParametro() == 1) {
				listLog = logServices.getLog_Oct_By_Login(getValorParametro(),this.anio);
			} else if (getParametro() == 2) {
				listLog = logServices.getLog_Oct_By_Accion(getValorParametro().toUpperCase(),this.anio);
			} else if (getParametro() == 3) {
				listLog = logServices
						.getLog_Oct_By_OpcionMenu(getValorParametro(),this.anio);
			}
			break;
		case 11: // obtenerListNoviembre(); break;
			if (getParametro() == 0)
				obtenerListNoviembre();
			else if (getParametro() == 1) {
				listLog = logServices.getLog_Nov_By_Login(getValorParametro(),this.anio);
			} else if (getParametro() == 2) {
				listLog = logServices.getLog_Nov_By_Accion(getValorParametro().toUpperCase(),this.anio);
			} else if (getParametro() == 3) {
				listLog = logServices
						.getLog_Nov_By_OpcionMenu(getValorParametro(),this.anio);
			}
			break;
		case 12: // obtenerListDiciembre(); break;
			if (getParametro() == 0)
				obtenerListDiciembre();
			else if (getParametro() == 1) {
				listLog = logServices.getLog_Dic_By_Login(getValorParametro().toUpperCase(),this.anio);
			} else if (getParametro() == 2) {
				listLog = logServices.getLog_Dic_By_Accion(getValorParametro().toUpperCase(),this.anio);
			} else if (getParametro() == 3) {
				listLog = logServices.getLog_Dic_By_OpcionMenu(getValorParametro(),this.anio);
			}
			break;
		}

	}
	
	
	
	
	public void obtenerListEnero() throws Exception{
		if(getParametro()==1 && getValorParametro()!=null){
			listLog = logServices.getLog_Ene_By_Login(getValorParametro().toLowerCase(),this.anio);  
		}else if(getParametro()==2 && getValorParametro()!=null){
			listLog = logServices.getLog_Ene_By_Accion(getValorParametro().toUpperCase(),this.anio);
		}else if(getParametro()==3 && getValorParametro()!=null){
			listLog = logServices.getLog_Ene_By_OpcionMenu(getValorParametro(),this.anio);
		}else{
			listLog = logServices.getLog_Ene();
		}
	}
	
	public void obtenerListFebrero() throws Exception{
		if(getParametro()==1 && getValorParametro()!=null){
			listLog = logServices.getLog_Feb_By_Login(getValorParametro().toLowerCase(),this.anio);  
		}else if(getParametro()==2 && getValorParametro()!=null){
			listLog = logServices.getLog_Feb_By_Accion(getValorParametro().toUpperCase(),this.anio);
		}else if(getParametro()==3 && getValorParametro()!=null){
			listLog = logServices.getLog_Feb_By_OpcionMenu(getValorParametro(),this.anio);
		}else{
			listLog = logServices.getLog_Feb();
		}
	}
	
	public void obtenerListMarzo() throws Exception{
		if(getParametro()==1 && getValorParametro()!=null){
			listLog = logServices.getLog_Mar_By_Login(getValorParametro().toLowerCase(),this.anio);  
		}else if(getParametro()==2 && getValorParametro()!=null){
			listLog = logServices.getLog_Mar_By_Accion(getValorParametro().toUpperCase(),this.anio);
		}else if(getParametro()==3 && getValorParametro()!=null){
			listLog = logServices.getLog_Mar_By_OpcionMenu(getValorParametro(),this.anio);
		}else{
			listLog = logServices.getLog_Mar();
		}
	}
	
	public void obtenerListAbril() throws Exception{
		if(getParametro()==1 && getValorParametro()!=null){
			listLog = logServices.getLog_Abr_By_Login(getValorParametro().toLowerCase(),this.anio);  
		}else if(getParametro()==2 && getValorParametro()!=null){
			listLog = logServices.getLog_Abr_By_Accion(getValorParametro().toUpperCase(),this.anio);
		}else if(getParametro()==3 && getValorParametro()!=null){
			listLog = logServices.getLog_Abr_By_OpcionMenu(getValorParametro(),this.anio);
		}else{
			listLog = logServices.getLog_Abr();
		}
	}
	
	public void obtenerListMayo() throws Exception{
		if(getParametro()==1 && getValorParametro()!=null){
			listLog = logServices.getLog_May_By_Login(getValorParametro().toLowerCase(),this.anio);  
		}else if(getParametro()==2 && getValorParametro()!=null){
			listLog = logServices.getLog_May_By_Accion(getValorParametro().toUpperCase(),this.anio);
		}else if(getParametro()==3 && getValorParametro()!=null){
			listLog = logServices.getLog_May_By_OpcionMenu(getValorParametro(),this.anio);
		}else{
			listLog = logServices.getLog_May();
		}
	}
	
	public void obtenerListJunio() throws Exception{
		if(getParametro()==1 && getValorParametro()!=null){
			listLog = logServices.getLog_Jun_By_Login(getValorParametro().toLowerCase(),this.anio);  
		}else if(getParametro()==2 && getValorParametro()!=null){
			listLog = logServices.getLog_Jun_By_Accion(getValorParametro().toUpperCase(),this.anio);
		}else if(getParametro()==3 && getValorParametro()!=null){
			listLog = logServices.getLog_Jun_By_OpcionMenu(getValorParametro(),this.anio);
		}else{
			listLog = logServices.getLog_Jun();
		}
	}
	
	
	public void obtenerListJulio() throws Exception{
		if(getParametro()==1 && getValorParametro()!=null){
			listLog = logServices.getLog_Jul_By_Login(getValorParametro().toLowerCase(),this.anio);  
		}else if(getParametro()==2 && getValorParametro()!=null){
			listLog = logServices.getLog_Jul_By_Accion(getValorParametro().toUpperCase(),this.anio);
		}else if(getParametro()==3 && getValorParametro()!=null){
			listLog = logServices.getLog_Jul_By_OpcionMenu(getValorParametro(),this.anio);
		}else{
			listLog = logServices.getLog_Jul();
		}
	}
	
	public void obtenerListAgosto() throws Exception{
		if(getParametro()==1 && getValorParametro()!=null){
			listLog = logServices.getLog_Ago_By_Login(getValorParametro().toLowerCase(),this.anio);  
		}else if(getParametro()==2 && getValorParametro()!=null){
			listLog = logServices.getLog_Ago_By_Accion(getValorParametro().toUpperCase(),this.anio);
		}else if(getParametro()==3 && getValorParametro()!=null){
			listLog = logServices.getLog_Ago_By_OpcionMenu(getValorParametro(),this.anio);
		}else{
			listLog = logServices.getLog_Ago();
		}
	}
	
	public void obtenerListSetiembre() throws Exception{
		if(getParametro()==1 && getValorParametro()!=null){
			listLog = logServices.getLog_Set_By_Login(getValorParametro().toLowerCase(),this.anio);  
		}else if(getParametro()==2 && getValorParametro()!=null){
			listLog = logServices.getLog_Set_By_Accion(getValorParametro().toUpperCase(),this.anio);
		}else if(getParametro()==3 && getValorParametro()!=null){
			listLog = logServices.getLog_Set_By_OpcionMenu(getValorParametro(),this.anio);
		}else{
			listLog = logServices.getLog_Set();
		}
	}
	
	public void obtenerListOctubre() throws Exception{
		if(getParametro()==1 && getValorParametro()!=null){
			listLog = logServices.getLog_Oct_By_Login(getValorParametro().toLowerCase(),this.anio);  
		}else if(getParametro()==2 && getValorParametro()!=null){
			listLog = logServices.getLog_Oct_By_Accion(getValorParametro().toUpperCase(),this.anio);
		}else if(getParametro()==3 && getValorParametro()!=null){
			listLog = logServices.getLog_Oct_By_OpcionMenu(getValorParametro(),this.anio);
		}else{
			listLog = logServices.getLog_Oct();
		}
	}
	
	public void obtenerListNoviembre() throws Exception{
		if(getParametro()==1 && getValorParametro()!=null){
			listLog = logServices.getLog_Nov_By_Login(getValorParametro().toLowerCase(),this.anio);  
		}else if(getParametro()==2 && getValorParametro()!=null){
			listLog = logServices.getLog_Nov_By_Accion(getValorParametro().toUpperCase(),this.anio);
		}else if(getParametro()==3 && getValorParametro()!=null){
			listLog = logServices.getLog_Nov_By_OpcionMenu(getValorParametro(),this.anio);
		}else{
			listLog = logServices.getLog_Nov();
		}
	}

	public void obtenerListDiciembre() throws Exception{
		if(getParametro()==1 && getValorParametro()!=null){
			listLog = logServices.getLog_Dic_By_Login(getValorParametro().toLowerCase(),this.anio);  
		}else if(getParametro()==2 && getValorParametro()!=null){
			listLog = logServices.getLog_Dic_By_Accion(getValorParametro().toUpperCase(),this.anio);
		}else if(getParametro()==3 && getValorParametro()!=null){
			listLog = logServices.getLog_Dic_By_OpcionMenu(getValorParametro(),this.anio);
		}else{
			listLog = logServices.getLog_Dic();
		}
	}
	
	
	public void dateSelectFI(DateSelectEvent event) {
		fecha_inicio= event.getDate();
	}
	
	public void dateSelectFF(DateSelectEvent event) {
		fecha_fin= event.getDate();
	}
	
	/*Metodo para hacer cambios en la seleccion de las fechas*/
	/*
	public void dateChange(AjaxBehaviorEvent event) throws Exception {
	    System.out.println("File Date: " + fecha_inicio);
	    System.out.println("Hello... I am in DateChange");
	}*/
	
	
	/**Declaración de Variables**/
	
	private LogServices logServices;
	private UsuarioServices usuarioServices;
	private MenuServices menuServices;
	private List<Log> listLog;
	private List<Log> listLogfilter;
	private List<Log> listaAniosLog;
	
	private int option=0;
	private int mes;
	private int anio;
	private Date fecha_inicio;
	private Date fecha_fin;
	private int parametro;
	private String valorParametro;
	private boolean bandRend=false;



	public int getOption() {
		return option;
	}
	
	public int getAnio() {
		return anio;
	}
	public void setAnio(int anio) {
		this.anio = anio;
	}
	public void setOption(int option) {
		this.option = option;
	}


	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	

	public Date getFecha_inicio() {
		return fecha_inicio;
	}


	public void setFecha_inicio(Date fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}


	public Date getFecha_fin() {
		return fecha_fin;
	}


	public void setFecha_fin(Date fecha_fin) {
		this.fecha_fin = fecha_fin;
	}


	public List<Log> getListLog() {
		return listLog;
	}


	public void setListLog(List<Log> listLog) {
		this.listLog = listLog;
	}


	public int getParametro() {
		return parametro;
	}


	public void setParametro(int parametro) {
		this.parametro = parametro;
	}


	public String getValorParametro() {
		return valorParametro;
	}


	public void setValorParametro(String valorParametro) {
		this.valorParametro = valorParametro;
	}

	public boolean isBandRend() {
		return bandRend;
	}

	public void setBandRend(boolean bandRend) {
		this.bandRend = bandRend;
	}

	public List<Log> getListLogfilter() {
		return listLogfilter;
	}

	public void setListLogfilter(List<Log> listLogfilter) {
		this.listLogfilter = listLogfilter;
	}
	public List<Log> getListaAniosLog() {
		return listaAniosLog;
	}
	public void setListaAniosLog(List<Log> listaAniosLog) {
		this.listaAniosLog = listaAniosLog;
	}
	public List<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}
	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}
	public List<String> getListaAccion() {
		return listaAccion;
	}
	public void setListaAccion(List<String> listaAccion) {
		this.listaAccion = listaAccion;
	}
	

}