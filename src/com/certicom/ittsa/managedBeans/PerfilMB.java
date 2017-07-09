package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Menu;
import com.certicom.ittsa.domain.Perfil;
import com.certicom.ittsa.domain.Sistema;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.PerfilServices;
import com.certicom.ittsa.services.SistemaServices;
import com.certicom.ittsa.services.UsuarioPerfilServices;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean
// @SessionScoped
@ViewScoped
public class PerfilMB extends GenericBeans implements Serializable {
	private String titulo;
	private Perfil perfil;
	private List<Perfil> listaPerfil;

	private UsuarioPerfilServices usuarioPerfilServices;
	private PerfilServices perfilServices;
	private MenuServices menuServices;

	private Boolean editar;

	// datos Log
	private Log log;
	private LogMB logmb;

	public PerfilMB() throws Exception {
		;
	}

	@PostConstruct
	public void inicia() {
		perfilServices = new PerfilServices();
		usuarioPerfilServices = new UsuarioPerfilServices();
		menuServices = new MenuServices();

		this.editar = Boolean.FALSE;
		perfil = new Perfil();

		log = (Log) getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		try {
			listaPerfil = perfilServices.listPerfil();
			int codMenu = menuServices.opcionMenuByPretty("pretty:perfiles");
			log.setCod_menu(codMenu);

		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * Desc: metodo que envia un objeto Perfil a otro managebBean
	 * 
	 * @param Objeto
	 *            Perfil
	 * @return: cadena para el gestor de navegacion
	 */
	public String asignarPermisos(Perfil perf) {
		String outcome = null;
		try {
			System.out.println("perfil pasaod:" + perf.getNombre());
			Flash flash = FacesContext.getCurrentInstance()
					.getExternalContext().getFlash();
			flash.put("perfil", perf);
			// return
			// "/faces/control_acceso/asignarPermisos?faces-redirect=true";
			outcome = "pretty:asignaPermisosPerfil";
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
		}
		return outcome;
	}

	/**
	 * @Desc:Cambia de estado
	 * @Auth:Will
	 * @param: Objeto de la clase Perfil
	 */
	public void cambiarEstado(Perfil perf) {

		if (perf.isInd_activo()) {
			// System.out.println("es igual a uno se pone a cero");
			perf.setInd_activo(Boolean.FALSE);
			// sistem.setInd_activo(new Integer(0));
		} else {
			// System.out.println("es igual a cero");
			perf.setInd_activo(Boolean.TRUE);
			// sistem.setInd_activo(new Integer(1));
		}

		try {
			this.perfilServices.updatePerfil(perf);
			log.setAccion("CHANGE ESTADO");
			log.setDescripcion("Se cambio el estado en sistema: "
					+ perfil.getDescripcion());
			logmb.insertarLog(log);
			FacesUtils.showFacesMessage("Estado de perfil actualizado correctamente.",Constante.INFORMACION);
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
		}
	}

	public void newInsert() {
		this.editar = Boolean.FALSE;
		this.perfil = new Perfil();
		this.titulo = "Registrar nuevo perfil";
	}

	public void editarPerfil(Perfil perf) throws Exception {
		this.editar = Boolean.TRUE;
		this.perfil = perf;
		this.titulo = "Modificar perfil";
	}

	/**
	 * Desc:Metodo que edita un perfil
	 * 
	 * @Auth: Will
	 * @throws Exception
	 */
	public void guardarPerfil() {
		RequestContext context = RequestContext.getCurrentInstance();
    	
		if (this.editar) {
			
			try {
				this.perfilServices.updatePerfil(perfil);
				log.setAccion("UPDATE");
				log.setDescripcion("Se actualiza el Perfil: "
						+ perfil.getDescripcion());
				logmb.insertarLog(log);
				FacesUtils.showFacesMessage("Perfil actualizado correctamente.",
						Constante.INFORMACION);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				this.perfil.setInd_activo(Boolean.TRUE);
				perfilServices.insertPerfil(perfil);
				log.setAccion("INSERT");
				log.setDescripcion("Se registro el Perfil: "
						+ perfil.getDescripcion());
				logmb.insertarLog(log);
				FacesUtils.showFacesMessage("Perfil registrado correctamente.",
						Constante.INFORMACION);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// relistando
		try {
			this.listaPerfil = this.perfilServices.listPerfil();
			context.update("msgGeneral");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void newDelete(Perfil perf) throws Exception {
		this.perfil = perf;
	}

	public void deletePerfil() {
		try {

			RequestContext context = RequestContext.getCurrentInstance();
        	
			Integer valor = usuarioPerfilServices.countPerfilxId(perfil.getCod_perfil());
			System.out.println("el valor qeu devuel es  " + valor);
			if (valor > 0) {
				context.execute("dlgOmiso.show()");
			} else {
				perfilServices.deletePerfil(perfil.getCod_perfil());
				log.setAccion("DELETE");
				log.setDescripcion("Se elimina el Perfil de las clase : "
						+ perfil.getDescripcion());
				logmb.insertarLog(log);
				listaPerfil = perfilServices.listPerfil();
				FacesUtils.showFacesMessage("Perfil eliminado correctamente.",
						Constante.INFORMACION);
			}

		} catch (Exception e) {
			System.out.println("Error eliminando perfil:" + e.getMessage());
			FacesUtils
					.showFacesMessage(
							"No se puede eliminar tiene permisos asignados, quite los permisos y vuelva a intentarlo.",
							Constante.ERROR);
		}
	}

	/* ###############-----gettres y setters--------###################### */

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public List<Perfil> getListaPerfil() {
		return listaPerfil;
	}

	public void setListaPerfil(List<Perfil> listaPerfil) {
		this.listaPerfil = listaPerfil;
	}

	public PerfilServices getPerfilServices() {
		return perfilServices;
	}

	public void setPerfilServices(PerfilServices perfilServices) {
		this.perfilServices = perfilServices;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

}