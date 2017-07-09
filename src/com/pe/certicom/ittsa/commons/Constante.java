package com.pe.certicom.ittsa.commons;

public class Constante {
	
	public static final String SESSION_USER = "usuarioEnSesion";
	public static final String SESSION_CONF = "configuracionEnSesion";
	public static final String SESSION_TIPODOC = "tipoDocumentoEnSesion";
	public static final String SESSION_IMAGEN = "imagenEnSesion";
	public static final String SESSION_LOTECLIENTEBD = "loteClienteBDEnSesion";
	public static final String SESSION_PERIODO = "periodoEnSesion";
	public static final String SESSION_INDICE = "indiceEnSesion";
	public static final String SESSION_UR = "urEnSesion";
	public static final String SESSION_LOG = "logEnSesion";
	
	/*****************Estados****************/
	public static final int ESTADO_ANTES_RECIBIDO =0;
	public static final int ESTADO_RECIBIDO =1;
	public static final int ESTADO_CARGOENTREGA = 11;
	public static final int ESTADO_CARGORECIBO =12; //De aqui sales mi stock de los que estan por preparar
	public static final int ESTADO_PREPARADO =2;
	public static final int ESTADO_PREP_DIGITALIZAR =31;
	public static final int ESTADO_DIGITALIZADO =32;
	public static final int ESTADO_PREP_CONTROL_DE_CALIDAD = 41;
	public static final int ESTADO__CONTROL_DE_CALIDAD = 42;
	public static final int ESTADO_PREP_1ERA_DIGITACION = 51;
	public static final int ESTADO_1RA_DIGITACION = 52;
	public static final int ESTADO_PREP_2DA_DIGITACION = 61;
	public static final int ESTADO_2DA_DIGITACION = 62;
	public static final int ESTADO_3RA_DIGITACION = 72;
	
	/***************Indicadores************/
	public static final String IND_DESEMPASTAR ="1";
	public static final boolean IND_ACTUAL = true;
	public static final boolean IND_ACTUAL_FALSE = false;
	public static final boolean IND_PROCESADO = true;
	public static final boolean IND_PROCESADO_FALSE = false;
	public static final int INCID_PREPARACION = 2;
	public static final boolean IND_DIGITADO = true;
	public static final boolean IND_DIGITADO_FALSE = false;
	public static final boolean IND_CULMINADO = true;
	public static final boolean IND_CULMINADO_FALSE = false;
	
	
	/******************Rutas*******************/
	public static final String RUTA_SERVER_IMG_BUSQ = "/imagenesSDD/";
	public static final String RUTA_SERVER_IMG_TD = "/tipoDocumental/";
	public static final String RUTA_SERVER_IMG = "/var/www/html/imagenesSDD/";
	public static final String RUTA_LOCAL_IMG = "/Digitalizacion/";
	
	/******************Conexiones Serv. Imagenes*******************/
	public static final String HOST_SERVER_IMG = "172.17.100.74";//"172.17.100.108";//"172.17.100.74";//
	public static final String USER_SERVER_IMG = "admin";//"root";
	public static final String PASS_SERVER_IMG = "12345";//"123456";//"s0p0rt32013";
	
	/******************Conexiones Serv. App.*******************/
	public static final String HOST_SERVER_APP = "172.17.100.74";//"172.17.100.108";//"172.17.100.74";//
	public static final String USER_SERVER_APP = "admin";
	public static final String PASS_SERVER_APP = "12345";//"123456";//"s0p0rt32013";
	
	/******************Conexion BD y HTTP*******************/
	public static final String RUTAURL = "http://172.17.100.74/html/tipoDocumental/";//"http://172.17.100.108/html/tipoDocumental/";//"http://172.17.100.74/html/tipoDocumental/";//
	public static final String URLBD = "jdbc:postgresql://172.17.100.74:5432/Digitalizacion";//"jdbc:postgresql://172.17.100.108:5432/Digitalizacion";//"jdbc:postgresql://172.17.100.74:5432/Digitalizacion";//
	public static final String USSERBD = "postgres";//
	public static final String PASSBD = "135abc246";//"135abc246";
	
	

	
	/******************Para Configuracion Scanner*******************/
	public static final String PREFIJO_IMG ="Page-";
	
	public static final String EXTENSION_IMG =".jpg";
	public static final String EXTENSION_ZIPEADO =".zip";
	public static final int NRO_DIG_NUMERACION_SCANNER =3;

	/************Para Tipos de Imagenes********/
	public static final int TIPO_IMG_ULTIMA_SUBLOTE =5;
	public static final int TIPO_IMG_DOCUMENTO =4;
	
	/***************Para perfiles****************/
	public static final int ADMINISTRADOR =1;
	public static final int DIGITALIZADOR =2;
	public static final int PREPARADOR =29;
	public static final int DIGITADOR = 8;
	public static final int OPERADOR_CC =10;
	public static final int MONITOR_PREPARACION =5;
	public static final int MONITOR_DIGITALIZACION =6;
	public static final int MONITOR_DIGITACION =7;
	public static final int MONITOR_CC =9;
	
	/*************para tpos de mensaje: will*******/
	public static final Integer ERROR = 1;
	public static final Integer FATAL = 2;
	public static final Integer INFORMACION = 3;
	public static final Integer ADVERTENCIA = 4;
	public static final String  PROCESO_PREPARACION = "PREPARACION"; 
	public static final String  PROCESO_DIGITALIZACION = "DIGITALIZACION"; 
	
	
	/*********** para secuencias ********************/
	public static final String SECUENCIA_TRACKING_BOLETO = "TRACKING_BOLETO";
	
	/*INDICES*/
	public static final Integer COD_TD_INDICE = 1;
	public static final Integer COD_INDICE_APELLIDO_PATERNO = 4;
	public static final Integer COD_INDICE_APELLIDO_MATERNO = 5;
	public static final Integer COD_INDICE_NOMBRE = 6;
	
	// LIQUIDACION CARGO
	public static final String LIQUIDACION_PUNTOVENTA ="PV";
	public static final String LIQUIDACION_OFICINA ="OF";
	
	////////PARA ITTSA
	public static final Double IGV =0.19;
	
	public static final String 	TIPO_DOCUMENTO_BOLETO = "BOLETO";
	public static final String 	MOVIMIENYO_CAJA_INGRESO = "INGRESO";
	public static final String 	MOVIMIENYO_CAJA_EGRESO = "EGRESO";
	
	// DATOS PARA WEB SERVICE
	public static final String WEBSERVICE_RUC = "20132272418";
	public static final String WEBSERVICE_USUARIO = "059933";
	public static final String WEBSERVICE_CLAVE = "n2fAOr";
	public static final String WEBSERVICE_PARTIDA = "000137PNR";
	
	
}


