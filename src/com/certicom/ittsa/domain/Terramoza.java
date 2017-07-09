package com.certicom.ittsa.domain;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.primefaces.model.StreamedContent;

public class Terramoza {
	
	private Integer idTerramoza;
	private String dNI;
	private String apellidos;
	private String nombres;
	private String telefono;
	private Date fNacimiento;
	private String codigoAnterior;
	private boolean estado;
	private Date fIngreso;
	private String residencia;
	//private String imagen;
	private String estadoString;
	
	private String sexo;
	private String nroCell;
	private String correo;
	private String cargo;
	private String terminal;
	private String ocupacion;
	private String grdoInstruccion;
	private String condicion;
	private byte[] imagen;
	private String sid_ubigeo;
	private StreamedContent archivoFoto;
	
	/**Para reporte***/
	private String licencia = "";
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
    
    private Date FechaCreacion;
    private String usuarioCreacion;
	
	/*  valores de la disponibilidad 
	 1 = disponible 
	 2 = en servicio
	 */
	private Integer disponibilidad;
	
	
	public Terramoza(){
		
	}


	public Integer getDisponibilidad() {
		return disponibilidad;
	}

	public void setDisponibilidad(Integer disponibilidad) {
		this.disponibilidad = disponibilidad;
	}

	public Integer getIdTerramoza() {
		return idTerramoza;
	}
	
	public String getdNI() {
		return dNI;
	}

	public String getApellidos() {
		return apellidos;
	}

	public String getNombres() {
		return nombres;
	}

	public String getTelefono() {
		return telefono;
	}

	public Date getfNacimiento() {
		return fNacimiento;
	}


	public String getCodigoAnterior() {
		return codigoAnterior;
	}


	public boolean isEstado() {
		return estado;
	}


	public Date getfIngreso() {
		return fIngreso;
	}


	public String getResidencia() {
		return residencia;
	}


	public String getSexo() {
		return sexo;
	}


	public String getNroCell() {
		return nroCell;
	}


	public String getCorreo() {
		return correo;
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


	public String getCondicion() {
		return condicion;
	}


	public byte[] getImagen() {
		return imagen;
	}


	public String getSid_ubigeo() {
		return sid_ubigeo;
	}


	public StreamedContent getArchivoFoto() {
		return archivoFoto;
	}


	public void setIdTerramoza(Integer idTerramoza) {
		this.idTerramoza = idTerramoza;
	}


	public void setdNI(String dNI) {
		this.dNI = dNI.toUpperCase();
	}


	public void setApellidos(String apellidos) {
		this.apellidos = apellidos.toUpperCase();
	}


	public void setNombres(String nombres) {
		this.nombres = nombres.toUpperCase();
	}


	public void setTelefono(String telefono) {
		this.telefono = telefono.toUpperCase();
	}


	public void setfNacimiento(Date fNacimiento) {
		this.fNacimiento = fNacimiento;
	}


	public void setCodigoAnterior(String codigoAnterior) {
		this.codigoAnterior = codigoAnterior.toUpperCase();
	}


	public void setEstado(boolean estado) {
		this.estado = estado;
	}


	public void setfIngreso(Date fIngreso) {
		this.fIngreso = fIngreso;
	}


	public void setResidencia(String residencia) {
		this.residencia = residencia.toUpperCase();
	}


	public void setSexo(String sexo) {
		this.sexo = sexo;
	}


	public void setNroCell(String nroCell) {
		this.nroCell = nroCell.toUpperCase();
	}


	public void setCorreo(String correo) {
		this.correo = correo.toUpperCase();
	}


	public void setCargo(String cargo) {
		this.cargo = cargo.toUpperCase();
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


	public void setCondicion(String condicion) {
		this.condicion = condicion.toUpperCase();
	}


	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}


	public void setSid_ubigeo(String sid_ubigeo) {
		this.sid_ubigeo = sid_ubigeo;
	}


	public void setArchivoFoto(StreamedContent archivoFoto) {
		this.archivoFoto = archivoFoto;
	}


	public String getEstadoString() {
		if(isEstado()){
			setEstadoString("Activo");
		}else setEstadoString("Inactivo");
		
		return estadoString;
	}


	public void setEstadoString(String estadoString) {
		this.estadoString = estadoString;
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


	public String getfNacimientoString() {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");		
		fNacimientoString = formato.format(getfNacimiento());
		return fNacimientoString;
	}


	public void setfNacimientoString(String fNacimientoString) {
		this.fNacimientoString = fNacimientoString;
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


	public String getfIngresoString() {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");		
		fIngresoString = formato.format(getfIngreso());
		return fIngresoString;
	}


	public void setfIngresoString(String fIngresoString) {
		this.fIngresoString = fIngresoString;
	}


	public Image getFoto() {
		return new ImageIcon(imagen).getImage();
	}


	public void setFoto(Image foto) {
		this.foto = foto;
	}
	
	public JRDataSource getPapeletasDS(){       
        return new JRBeanCollectionDataSource(this.listaPapeletas,false);     
    }
	
	public List<Papeleta> getListaPapeletas() {
		return listaPapeletas;
	}

	public void setListaPapeletas(List<Papeleta> listaPapeletas) {
		this.listaPapeletas = listaPapeletas;
	}


	public String getLicencia() {
		return licencia;
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