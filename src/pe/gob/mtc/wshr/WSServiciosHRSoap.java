/**
 * WSServiciosHRSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pe.gob.mtc.wshr;

public interface WSServiciosHRSoap extends java.rmi.Remote {

    /**
     * Listado de Parametros por Empresa de Transporte.
     */
    public pe.gob.mtc.wshr.ResultParametro getParametro(pe.gob.mtc.wshr.Parametro oParametro) throws java.rmi.RemoteException;

    /**
     * Validación de Vehículos por Empresa de Transporte.
     */
    public pe.gob.mtc.wshr.ResultVehiculo getVehiculo(pe.gob.mtc.wshr.Vehiculo oVehiculo) throws java.rmi.RemoteException;

    /**
     * Validación de Conductores por Empresa de Transporte.
     */
    public pe.gob.mtc.wshr.ResultConductor getConductor(pe.gob.mtc.wshr.Conductor oConductor) throws java.rmi.RemoteException;

    /**
     * Registro de Viaje por Empresa de Transporte.
     */
    public pe.gob.mtc.wshr.ResultViaje setViaje(pe.gob.mtc.wshr.Viaje oViaje) throws java.rmi.RemoteException;

    /**
     * Registro de Hoja Ruta por Empresa de Transporte.
     */
    public pe.gob.mtc.wshr.ResultHojaRuta setHojaRuta(pe.gob.mtc.wshr.HojaRuta oHojaRuta) throws java.rmi.RemoteException;

    /**
     * Finalización de Hoja Ruta por Empresa de Transporte.
     */
    public pe.gob.mtc.wshr.ResultFinalizar setFinalizar(pe.gob.mtc.wshr.Finalizar oFinalizar) throws java.rmi.RemoteException;
}
