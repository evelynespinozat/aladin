package com.certicom.ittsa.domain;

import java.awt.Image;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.primefaces.model.StreamedContent;

public class Piloto {
	
	private Integer idPiloto;
	private String dNI;
	private String apellidoPaterno;
	private String apellidoMaterno;
	
	private String nombres;
	private String licencia;
	private Date fNacimiento;
	private String codigoAnterior;
	private String telefono;
	private boolean estado;
	private Date fingreso;
	private String residencia;
	
	private String cargo;
	private String terminal;
	private String ocupacion;
	private String grdoInstruccion;
	
	private String nroCell;
	private String correo;
	private String ultimaEmpresa;
	private String ultimoCargo;
	
	private String motivoCese;
	private String estCivil;
	private String nombreEsposa;
	private String dniEsposa;
	private Date fechaNacEsposa;
	private String nombresHijo1;
	private String nombresHijo2;
	private String emergenciaContacto;
    private String sexo;
    private String condicion;
    private String sid_ubigeo;
	private byte[] imagen;
	private boolean asignado;
	
	/**Para Reportes**/
	private String apellidos;
	private String fNacimientoString;
	private String fIngresoString;
	private List<Historial_Laboral> listaHistorial;
	private List<Datos_Familiares> listaDatosF;
	private List<Documento> listaDocumentos;
	private List<Evaluacion> listaEvaluacion;
    private List<Capacitacion> listaCapacitacion;
    private List<Formacion> listaFormacion;
    private List<Incidencia> listaIncidencias;
    private List<Cese> listaCeses;
    private List<Papeleta> listaPapeletas;
	private String departamento;
    private String provincia;
    private String distrito;
    private Image foto;
	
	//para vista
	private StreamedContent archivoFoto;
	
	//para historial Laboral
	private String anioUltimaEmpresa;
	private String modalidadContrato;
	private String nombreUltimoJefe;
	private String remuneracionUltimaEmpresa;
	private String telefonoUltimaEmpresa;
	
	/*  valores de la disponibilidad 
	 1 = disponible 
	 2 = en servicio
	 */
	private Integer disponibilidad;
	
	//28-02-2014
	private boolean flagTemporal;
	
	private Date FechaCreacion;
	private String usuarioCreacion;

	public Piloto(){
		
	}
	
	public boolean isAsignado() {
		return asignado;
	}

	public void setAsignado(boolean asignado) {
		this.asignado = asignado;
	}

	public Integer getDisponibilidad() {
		return disponibilidad;
	}

	public void setDisponibilidad(Integer disponibilidad) {
		this.disponibilidad = disponibilidad;
	}

	public Integer getIdPiloto() {
		return idPiloto;
	}
	
	public String getdNI() {
		return dNI;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public String getNombres() {
		return nombres;
	}

	public String getLicencia() {
		return licencia;
	}

	public Date getfNacimiento() {
		return fNacimiento;
	}

	public String getCodigoAnterior() {
		return codigoAnterior;
	}

	public String getTelefono() {
		return telefono;
	}

	public boolean isEstado() {
		return estado;
	}


	public Date getFingreso() {
		return fingreso;
	}


	public String getResidencia() {
		return residencia;
	}


	public String getCargo() {
		return cargo;
	}


	public String getTerminal() {
		return terminal;
	}


	public String getOcupacion() {
		return ocupacion;
	}


	public String getGrdoInstruccion() {
		return grdoInstruccion;
	}


	public String getNroCell() {
		return nroCell;
	}


	public String getCorreo() {
		return correo;
	}


	public String getUltimaEmpresa() {
		return ultimaEmpresa;
	}


	public String getUltimoCargo() {
		return ultimoCargo;
	}


	public String getMotivoCese() {
		return motivoCese;
	}


	public String getEstCivil() {
		return estCivil;
	}


	public String getNombreEsposa() {
		return nombreEsposa;
	}


	public String getDniEsposa() {
		return dniEsposa;
	}


	public Date getFechaNacEsposa() {
		return fechaNacEsposa;
	}


	public String getNombresHijo1() {
		return nombresHijo1;
	}


	public String getNombresHijo2() {
		return nombresHijo2;
	}


	public String getEmergenciaContacto() {
		return emergenciaContacto;
	}


	public String getSexo() {
		return sexo;
	}


	public String getCondicion() {
		return condicion;
	}


	public String getSid_ubigeo() {
		return sid_ubigeo;
	}


	public byte[] getImagen() {
		return imagen;
	}


	public StreamedContent getArchivoFoto() {
		return archivoFoto;
	}

	public String getAnioUltimaEmpresa() {
		return anioUltimaEmpresa;
	}


	public String getModalidadContrato() {
		return modalidadContrato;
	}


	public String getNombreUltimoJefe() {
		return nombreUltimoJefe;
	}


	public String getRemuneracionUltimaEmpresa() {
		return remuneracionUltimaEmpresa;
	}


	public String getTelefonoUltimaEmpresa() {
		return telefonoUltimaEmpresa;
	}


	public void setIdPiloto(Integer idPiloto) {
		this.idPiloto = idPiloto;
	}


	public void setdNI(String dNI) {
		this.dNI = dNI.toUpperCase();
	}


	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno.toUpperCase();
	}


	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno.toUpperCase();
	}


	public void setNombres(String nombres) {
		this.nombres = nombres.toUpperCase();
	}


	public void setLicencia(String licencia) {
		this.licencia = licencia.toUpperCase();
	}


	public void setfNacimiento(Date fNacimiento) {
		this.fNacimiento = fNacimiento;
	}


	public void setCodigoAnterior(String codigoAnterior) {
		this.codigoAnterior = codigoAnterior.toUpperCase();
	}


	public void setTelefono(String telefono) {
		this.telefono = telefono.toUpperCase();
	}


	public void setEstado(boolean estado) {
		this.estado = estado;
	}


	public void setFingreso(Date fingreso) {
		this.fingreso = fingreso;
	}


	public void setResidencia(String residencia) {
		this.residencia = residencia.toUpperCase();
	}


	public void setCargo(String cargo) {
		this.cargo = cargo;
	}


	public void setTerminal(String terminal) {
		this.terminal = terminal.toUpperCase();
	}


	public void setOcupacion(String ocupacion) {
		this.ocupacion = ocupacion.toUpperCase();
	}


	public void setGrdoInstruccion(String grdoInstruccion) {
		this.grdoInstruccion = grdoInstruccion.toUpperCase();
	}


	public void setNroCell(String nroCell) {
		this.nroCell = nroCell.toUpperCase();
	}


	public void setCorreo(String correo) {
		this.correo = correo.toUpperCase();
	}


	public void setUltimaEmpresa(String ultimaEmpresa) {
		this.ultimaEmpresa = ultimaEmpresa.toUpperCase();
	}


	public void setUltimoCargo(String ultimoCargo) {
		this.ultimoCargo = ultimoCargo.toUpperCase();
	}


	public void setMotivoCese(String motivoCese) {
		this.motivoCese = motivoCese.toUpperCase();
	}


	public void setEstCivil(String estCivil) {
		this.estCivil = estCivil.toUpperCase();
	}


	public void setNombreEsposa(String nombreEsposa) {
		this.nombreEsposa = nombreEsposa.toUpperCase();
	}


	public void setDniEsposa(String dniEsposa) {
		this.dniEsposa = dniEsposa.toUpperCase();
	}


	public void setFechaNacEsposa(Date fechaNacEsposa) {
		this.fechaNacEsposa = fechaNacEsposa;
	}


	public void setNombresHijo1(String nombresHijo1) {
		this.nombresHijo1 = nombresHijo1.toUpperCase();
	}


	public void setNombresHijo2(String nombresHijo2) {
		this.nombresHijo2 = nombresHijo2.toUpperCase();
	}


	public void setEmergenciaContacto(String emergenciaContacto) {
		this.emergenciaContacto = emergenciaContacto.toUpperCase();
	}


	public void setSexo(String sexo) {
		this.sexo = sexo.toUpperCase();
	}


	public void setCondicion(String condicion) {
		this.condicion = condicion.toUpperCase();
	}


	public void setSid_ubigeo(String sid_ubigeo) {
		this.sid_ubigeo = sid_ubigeo;
	}


	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}


	public void setArchivoFoto(StreamedContent archivoFoto) {
		this.archivoFoto = archivoFoto;
	}


	public void setAnioUltimaEmpresa(String anioUltimaEmpresa) {
		this.anioUltimaEmpresa = anioUltimaEmpresa.toUpperCase();
	}


	public void setModalidadContrato(String modalidadContrato) {
		this.modalidadContrato = modalidadContrato.toUpperCase();
	}


	public void setNombreUltimoJefe(String nombreUltimoJefe) {
		this.nombreUltimoJefe = nombreUltimoJefe.toUpperCase();
	}


	public void setRemuneracionUltimaEmpresa(String remuneracionUltimaEmpresa) {
		this.remuneracionUltimaEmpresa = remuneracionUltimaEmpresa.toUpperCase();
	}


	public void setTelefonoUltimaEmpresa(String telefonoUltimaEmpresa) {
		this.telefonoUltimaEmpresa = telefonoUltimaEmpresa.toUpperCase();
	}

	public List<Historial_Laboral> getListaHistorial() {
		return listaHistorial;
	}

	public void setListaHistorial(List<Historial_Laboral> listaHistorial) {
		this.listaHistorial = listaHistorial;
	}

	public List<Datos_Familiares> getListaDatosF() {
		return listaDatosF;
	}

	public void setListaDatosF(List<Datos_Familiares> listaDatosF) {
		this.listaDatosF = listaDatosF;
	}

	public List<Documento> getListaDocumentos() {
		return listaDocumentos;
	}

	public void setListaDocumentos(List<Documento> listaDocumentos) {
		this.listaDocumentos = listaDocumentos;
	}

	public List<Evaluacion> getListaEvaluacion() {
		return listaEvaluacion;
	}

	public void setListaEvaluacion(List<Evaluacion> listaEvaluacion) {
		this.listaEvaluacion = listaEvaluacion;
	}

	public List<Capacitacion> getListaCapacitacion() {
		return listaCapacitacion;
	}

	public void setListaCapacitacion(List<Capacitacion> listaCapacitacion) {
		this.listaCapacitacion = listaCapacitacion;
	}

	public List<Formacion> getListaFormacion() {
		return listaFormacion;
	}

	public void setListaFormacion(List<Formacion> listaFormacion) {
		this.listaFormacion = listaFormacion;
	}

	public List<Incidencia> getListaIncidencias() {
		return listaIncidencias;
	}

	public void setListaIncidencias(List<Incidencia> listaIncidencias) {
		this.listaIncidencias = listaIncidencias;
	}

	public List<Cese> getListaCeses() {
		return listaCeses;
	}

	public void setListaCeses(List<Cese> listaCeses) {
		this.listaCeses = listaCeses;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getDistrito() {
		return distrito;
	}

	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}

	public Image getFoto() {
		if(this.imagen!=null){
			return new ImageIcon(imagen).getImage();
		} else{
			return null;
		}
	}

	public void setFoto(Image foto) {
		this.foto = foto;
	}

	public JRDataSource getHistorialLaboralDS(){       
        return new JRBeanCollectionDataSource(this.listaHistorial,false);    
    }

	
	public JRDataSource getDatosFamDS(){       
        return new JRBeanCollectionDataSource(this.listaDatosF,false);     
    }
	
	public JRDataSource getEvaluacionesDS(){       
        return new JRBeanCollectionDataSource(this.listaEvaluacion,false);     
    }
	
	public JRDataSource getDocumentosDS(){       
        return new JRBeanCollectionDataSource(this.listaDocumentos,false);     
    }
	
	public JRDataSource getFormacionDS(){       
        return new JRBeanCollectionDataSource(this.listaFormacion,false);     
    }
	
	public JRDataSource getCapacitacionesDS(){       
        return new JRBeanCollectionDataSource(this.listaCapacitacion,false);     
    }
	
	public JRDataSource getIncidenciasDS(){       
        return new JRBeanCollectionDataSource(this.listaIncidencias,false);     
    }
	
	public JRDataSource getCesesDS(){       
        return new JRBeanCollectionDataSource(this.listaCeses,false);     
    }
	
	public JRDataSource getPapeletasDS(){       
        return new JRBeanCollectionDataSource(this.listaPapeletas,false);     
    }

	public String getApellidos() {
		apellidos = getApellidoPaterno() + " " + getApellidoMaterno();
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getfNacimientoString() {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");		
		fNacimientoString = formato.format(getfNacimiento());
		return fNacimientoString;
	}

	public void setfNacimientoString(String fNacimientoString) {
		this.fNacimientoString = fNacimientoString;
	}

	public String getfIngresoString() {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");		
		fIngresoString = formato.format(getFingreso());
		return fIngresoString;
	}

	public void setfIngresoString(String fIngresoString) {
		this.fIngresoString = fIngresoString;
	}

	public List<Papeleta> getListaPapeletas() {
		return listaPapeletas;
	}

	public void setListaPapeletas(List<Papeleta> listaPapeletas) {
		this.listaPapeletas = listaPapeletas;
	}

	public boolean isFlagTemporal() {
		return flagTemporal;
	}

	public void setFlagTemporal(boolean flagTemporal) {
		this.flagTemporal = flagTemporal;
	}

	public Date getFechaCreacion() {
		return FechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		FechaCreacion = fechaCreacion;
	}

	public String getUsuarioCreacion() {
		return usuarioCreacion;
	}

	public void setUsuarioCreacion(String usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}
	
	
	
}
