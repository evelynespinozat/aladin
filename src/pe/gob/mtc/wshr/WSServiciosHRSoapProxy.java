package pe.gob.mtc.wshr;

public class WSServiciosHRSoapProxy implements pe.gob.mtc.wshr.WSServiciosHRSoap {
  private String _endpoint = null;
  private pe.gob.mtc.wshr.WSServiciosHRSoap wSServiciosHRSoap = null;
  
  public WSServiciosHRSoapProxy() {
    _initWSServiciosHRSoapProxy();
  }
  
  public WSServiciosHRSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initWSServiciosHRSoapProxy();
  }
  
  private void _initWSServiciosHRSoapProxy() {
    try {
      wSServiciosHRSoap = (new pe.gob.mtc.wshr.WSServiciosHRLocator()).getWSServiciosHRSoap();
      if (wSServiciosHRSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)wSServiciosHRSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)wSServiciosHRSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (wSServiciosHRSoap != null)
      ((javax.xml.rpc.Stub)wSServiciosHRSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public pe.gob.mtc.wshr.WSServiciosHRSoap getWSServiciosHRSoap() {
    if (wSServiciosHRSoap == null)
      _initWSServiciosHRSoapProxy();
    return wSServiciosHRSoap;
  }
  
  public pe.gob.mtc.wshr.ResultParametro getParametro(pe.gob.mtc.wshr.Parametro oParametro) throws java.rmi.RemoteException{
    if (wSServiciosHRSoap == null)
      _initWSServiciosHRSoapProxy();
    return wSServiciosHRSoap.getParametro(oParametro);
  }
  
  public pe.gob.mtc.wshr.ResultVehiculo getVehiculo(pe.gob.mtc.wshr.Vehiculo oVehiculo) throws java.rmi.RemoteException{
    if (wSServiciosHRSoap == null)
      _initWSServiciosHRSoapProxy();
    return wSServiciosHRSoap.getVehiculo(oVehiculo);
  }
  
  public pe.gob.mtc.wshr.ResultConductor getConductor(pe.gob.mtc.wshr.Conductor oConductor) throws java.rmi.RemoteException{
    if (wSServiciosHRSoap == null)
      _initWSServiciosHRSoapProxy();
    return wSServiciosHRSoap.getConductor(oConductor);
  }
  
  public pe.gob.mtc.wshr.ResultViaje setViaje(pe.gob.mtc.wshr.Viaje oViaje) throws java.rmi.RemoteException{
    if (wSServiciosHRSoap == null)
      _initWSServiciosHRSoapProxy();
    return wSServiciosHRSoap.setViaje(oViaje);
  }
  
  public pe.gob.mtc.wshr.ResultHojaRuta setHojaRuta(pe.gob.mtc.wshr.HojaRuta oHojaRuta) throws java.rmi.RemoteException{
    if (wSServiciosHRSoap == null)
      _initWSServiciosHRSoapProxy();
    return wSServiciosHRSoap.setHojaRuta(oHojaRuta);
  }
  
  public pe.gob.mtc.wshr.ResultFinalizar setFinalizar(pe.gob.mtc.wshr.Finalizar oFinalizar) throws java.rmi.RemoteException{
    if (wSServiciosHRSoap == null)
      _initWSServiciosHRSoapProxy();
    return wSServiciosHRSoap.setFinalizar(oFinalizar);
  }
  
  
}