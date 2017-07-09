package com.certicom.ittsa.managedBeans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.services.AgenciaService;

@ManagedBean(name="diasCroquisMB")
@ViewScoped
public class DiasCroquisMB {

	private Agencia agencia;
	private List<Agencia> listaAgencia;
	private AgenciaService agenciaSevice;
	
	@ManagedProperty(value="#{loginMB}")
	private LoginMB login;

	

	@PostConstruct
	public void inicia()
	{
		this.agenciaSevice = new AgenciaService();
		try {
			this.listaAgencia = agenciaSevice.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void configurarFechaAgencia(Agencia ag)
	{
		System.out.println("congu alfo");
		this.agencia = ag;
		//this.agencia.setDiascroquis(10); //por defceto
	}
	
	public void guardarNrodiasCroquis()
	{
		System.out.println("guardando nro dias croquis");
		
		System.out.println("el spoiner es:"+this.agencia.getDiascroquis());
		try {
			this.agenciaSevice.actualizarAgenciaNroDias(agencia);
			System.out.println("finish actualizacion");
			this.login.getAgencia().setDiascroquis(agencia.getDiascroquis());//actualizando en el login
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	

	/*###################-----getters an s seters--------#####################33*/
	
	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	public List<Agencia> getListaAgencia() {
		return listaAgencia;
	}

	public void setListaAgencia(List<Agencia> listaAgencia) {
		this.listaAgencia = listaAgencia;
	}
	public void setLogin(LoginMB login) {
		this.login = login;
	}
}
