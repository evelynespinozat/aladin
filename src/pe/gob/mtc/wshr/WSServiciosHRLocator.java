/**
 * WSServiciosHRLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pe.gob.mtc.wshr;

public class WSServiciosHRLocator extends org.apache.axis.client.Service implements pe.gob.mtc.wshr.WSServiciosHR {

    public WSServiciosHRLocator() {
    }


    public WSServiciosHRLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WSServiciosHRLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for WSServiciosHRSoap
    private java.lang.String WSServiciosHRSoap_address = "http://wshr.mtc.gob.pe/wsServiciosHR.asmx";

    public java.lang.String getWSServiciosHRSoapAddress() {
        return WSServiciosHRSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String WSServiciosHRSoapWSDDServiceName = "WSServiciosHRSoap";

    public java.lang.String getWSServiciosHRSoapWSDDServiceName() {
        return WSServiciosHRSoapWSDDServiceName;
    }

    public void setWSServiciosHRSoapWSDDServiceName(java.lang.String name) {
        WSServiciosHRSoapWSDDServiceName = name;
    }

    public pe.gob.mtc.wshr.WSServiciosHRSoap getWSServiciosHRSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WSServiciosHRSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWSServiciosHRSoap(endpoint);
    }

    public pe.gob.mtc.wshr.WSServiciosHRSoap getWSServiciosHRSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            pe.gob.mtc.wshr.WSServiciosHRSoapStub _stub = new pe.gob.mtc.wshr.WSServiciosHRSoapStub(portAddress, this);
            _stub.setPortName(getWSServiciosHRSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWSServiciosHRSoapEndpointAddress(java.lang.String address) {
        WSServiciosHRSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (pe.gob.mtc.wshr.WSServiciosHRSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                pe.gob.mtc.wshr.WSServiciosHRSoapStub _stub = new pe.gob.mtc.wshr.WSServiciosHRSoapStub(new java.net.URL(WSServiciosHRSoap_address), this);
                _stub.setPortName(getWSServiciosHRSoapWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("WSServiciosHRSoap".equals(inputPortName)) {
            return getWSServiciosHRSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "WSServiciosHR");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "WSServiciosHRSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("WSServiciosHRSoap".equals(portName)) {
            setWSServiciosHRSoapEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
