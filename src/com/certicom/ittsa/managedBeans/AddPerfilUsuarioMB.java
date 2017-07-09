package com.certicom.ittsa.managedBeans;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.Perfil;
import com.certicom.ittsa.domain.Usuario;
import com.certicom.ittsa.domain.UsuarioPerfil;
import com.certicom.ittsa.services.PerfilServices;
import com.certicom.ittsa.services.UsuarioPerfilServices;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;

@ManagedBean(name="addPerfilUsuarioMB")
@ViewScoped
public class AddPerfilUsuarioMB {

	private Usuario usuario;
	private List<Perfil> listaPerfil;
	private List<UsuarioPerfil> listaPerfilUsuario;
	private PerfilServices perfilServices;
	private UsuarioPerfilServices usuarioPerfilServices;
	private UsuarioPerfil usuarioPerfil;
	private Perfil perfil;
	private RequestContext context;
	
	
	@PostConstruct
	public void inicia(){
		
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		this.usuario =(Usuario) flash.get("usuario");
		
		this.usuarioPerfilServices = new UsuarioPerfilServices();
		this.perfilServices = new PerfilServices();
		this.perfil = new Perfil();
		this.context = RequestContext.getCurrentInstance();
		
		try {
			listaPerfil = perfilServices.listPerfilActivo();
			System.out.println("tamaño de lista perfiles usuario :"+usuarioPerfilServices.listarPerfilesPorUsuario(this.usuario.getIdusuario()).size());
			listaPerfilUsuario = usuarioPerfilServices.listarPerfilesPorUsuario(this.usuario.getIdusuario());
			for(UsuarioPerfil pu : listaPerfilUsuario){
				System.out.println("Listando: "+pu.getCod_perfil());
			}
		} catch (Exception e) {
			System.out.println("Error :"+ e.getMessage());
			e.printStackTrace();
		}
		

	}
	
	
	public void insertarPerfil(){

		//System.out.println("verificando que no este repetido");
		try {
			this.usuarioPerfil= this.usuarioPerfilServices.buscarPerfilUsuario(this.usuario.getIdusuario(), this.perfil.getCod_perfil());
			if(this.usuarioPerfil!=null){
				FacesUtils.showFacesMessage("Perfil se encuentra ya registrado.", Constante.ADVERTENCIA);
			}else{
				//System.out.println("Ahora si grabamos");
				this.perfil=this.perfilServices.findPerfilPorCodigo(this.perfil.getCod_perfil());
				UsuarioPerfil usrPerfil = new UsuarioPerfil();
				usrPerfil.setCod_perfil(this.perfil.getCod_perfil());
				usrPerfil.setIdusuario(this.usuario.getIdusuario());
				usrPerfil.setFecha_finvig(new Date());
				usrPerfil.setFecha_iniciovig(new Date());
				usrPerfil.setFecha_modif(new Date());
				//usrPerfil.setUsuario_modif(this.login.getLogin());
				//usrPerfil.setUsuario_registro(this.login.getLogin());
				usrPerfil.setFecha_registro(new Date());
				usrPerfil.setInd_activo(Boolean.TRUE);
				this.usuarioPerfilServices.insertUsuarioPeril(usrPerfil);
				this.listaPerfilUsuario=this.usuarioPerfilServices.listarPerfilesPorUsuario(this.usuario.getIdusuario());
			
				FacesUtils.showFacesMessage("Perfil registrado correctamente.", Constante.INFORMACION);
			}
			
			context.update("msgGeneral");
		} catch (Exception e) {
				e.printStackTrace();
		}
			
		
		
	}
	
	
	public void eliminarPerfil(){
		System.out.println("eliminar perfil: "+this.usuarioPerfil.getCod_perfil());
		try{
			this.usuarioPerfilServices.eliminarPerfilUsuario(this.usuario.getIdusuario(),this.usuarioPerfil.getCod_perfil());
			listaPerfilUsuario = this.usuarioPerfilServices.listarPerfilesPorUsuario(this.usuario.getIdusuario());
			FacesUtils.showFacesMessage("Perfil eliminado correctamente.", Constante.INFORMACION);
			RequestContext.getCurrentInstance().update("grSms");
		}catch(Exception e){
			System.out.println("Error :"+e.getMessage());
		}
		
	}
	
	
	/*#######################---getters y setters----------########################*/
	
	public Usuario getUsuario() {
		return usuario;
	}

	public List<Perfil> getListaPerfil() {
		return listaPerfil;
	}

	public List<UsuarioPerfil> getListaPerfilUsuario() {
		return listaPerfilUsuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setListaPerfil(List<Perfil> listaPerfil) {
		this.listaPerfil = listaPerfil;
	}

	public void setListaPerfilUsuario(List<UsuarioPerfil> listaPerfilUsuario) {
		this.listaPerfilUsuario = listaPerfilUsuario;
	}

	public AddPerfilUsuarioMB(){
		
	}


	public UsuarioPerfil getUsuarioPerfil() {
		return usuarioPerfil;
	}


	public void setUsuarioPerfil(UsuarioPerfil usuarioPerfil) {
		this.usuarioPerfil = usuarioPerfil;
	}


	public Perfil getPerfil() {
		return perfil;
	}


	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
	

	
	
}
