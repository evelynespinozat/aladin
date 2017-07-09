package com.certicom.ittsa.domain;

import java.util.Date;

public class Perfil implements java.io.Serializable {

    public static final int ADMINISTRADOR = 1;
    public static final int JEFE_COUNTERS_PASAJES = 17;

    private static final long serialVersionUID = 1L;
    private Integer cod_perfil;
    private String nombre;
    private String descripcion;
    private boolean ind_activo;
    private Date fecha_registro;
    private Date fecha_modif;
    private String usuario_registro;
    private String usuario_modif;
    private Integer idusuario;
    // 17/03/2014
    private String liquidacion;

    //ultimo campo agrgado a BD
    private String proceso;

    public Perfil() {

    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Integer getCod_perfil() {
        return cod_perfil;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean isInd_activo() {
        return ind_activo;
    }

    public Date getFecha_registro() {
        return fecha_registro;
    }

    public Date getFecha_modif() {
        return fecha_modif;
    }

    public String getUsuario_registro() {
        return usuario_registro;
    }

    public String getUsuario_modif() {
        return usuario_modif;
    }

    public Integer getIdusuario() {
        return idusuario;
    }

    public String getProceso() {
        return proceso;
    }

    public void setCod_perfil(Integer cod_perfil) {
        this.cod_perfil = cod_perfil;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre.toUpperCase();
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion.toUpperCase();
    }

    public void setInd_activo(boolean ind_activo) {
        this.ind_activo = ind_activo;
    }

    public void setFecha_registro(Date fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public void setFecha_modif(Date fecha_modif) {
        this.fecha_modif = fecha_modif;
    }

    public void setUsuario_registro(String usuario_registro) {
        this.usuario_registro = usuario_registro;
    }

    public void setUsuario_modif(String usuario_modif) {
        this.usuario_modif = usuario_modif;
    }

    public void setIdusuario(Integer idusuario) {
        this.idusuario = idusuario;
    }

    public void setProceso(String proceso) {
        this.proceso = proceso.toUpperCase();
    }

    public String getLiquidacion() {
        return liquidacion;
    }

    public void setLiquidacion(String liquidacion) {
        this.liquidacion = liquidacion;
    }

}
