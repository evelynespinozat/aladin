/**
 * WSServiciosHRSoapStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pe.gob.mtc.wshr;

public class WSServiciosHRSoapStub extends org.apache.axis.client.Stub implements pe.gob.mtc.wshr.WSServiciosHRSoap {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[6];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getParametro");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "oParametro"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "Parametro"), pe.gob.mtc.wshr.Parametro.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "ResultParametro"));
        oper.setReturnClass(pe.gob.mtc.wshr.ResultParametro.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "getParametroResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getVehiculo");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "oVehiculo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "Vehiculo"), pe.gob.mtc.wshr.Vehiculo.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "ResultVehiculo"));
        oper.setReturnClass(pe.gob.mtc.wshr.ResultVehiculo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "getVehiculoResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getConductor");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "oConductor"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "Conductor"), pe.gob.mtc.wshr.Conductor.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "ResultConductor"));
        oper.setReturnClass(pe.gob.mtc.wshr.ResultConductor.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "getConductorResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("setViaje");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "oViaje"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "Viaje"), pe.gob.mtc.wshr.Viaje.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "ResultViaje"));
        oper.setReturnClass(pe.gob.mtc.wshr.ResultViaje.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "setViajeResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("setHojaRuta");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "oHojaRuta"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "HojaRuta"), pe.gob.mtc.wshr.HojaRuta.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "ResultHojaRuta"));
        oper.setReturnClass(pe.gob.mtc.wshr.ResultHojaRuta.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "setHojaRutaResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("setFinalizar");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "oFinalizar"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "Finalizar"), pe.gob.mtc.wshr.Finalizar.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "ResultFinalizar"));
        oper.setReturnClass(pe.gob.mtc.wshr.ResultFinalizar.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "setFinalizarResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[5] = oper;

    }

    public WSServiciosHRSoapStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public WSServiciosHRSoapStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public WSServiciosHRSoapStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "ArrayOfDocumento");
            cachedSerQNames.add(qName);
            cls = pe.gob.mtc.wshr.Documento[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "Documento");
            qName2 = new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "Documento");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "ArrayOfErrores");
            cachedSerQNames.add(qName);
            cls = pe.gob.mtc.wshr.Errores[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "Errores");
            qName2 = new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "Errores");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "ArrayOfHConductor");
            cachedSerQNames.add(qName);
            cls = pe.gob.mtc.wshr.HConductor[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "HConductor");
            qName2 = new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "HConductor");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "ArrayOfHTripulante");
            cachedSerQNames.add(qName);
            cls = pe.gob.mtc.wshr.HTripulante[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "HTripulante");
            qName2 = new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "HTripulante");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "ArrayOfRuta");
            cachedSerQNames.add(qName);
            cls = pe.gob.mtc.wshr.Ruta[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "Ruta");
            qName2 = new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "Ruta");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "ArrayOfTerminal");
            cachedSerQNames.add(qName);
            cls = pe.gob.mtc.wshr.Terminal[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "Terminal");
            qName2 = new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "Terminal");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "Conductor");
            cachedSerQNames.add(qName);
            cls = pe.gob.mtc.wshr.Conductor.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "Documento");
            cachedSerQNames.add(qName);
            cls = pe.gob.mtc.wshr.Documento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "Errores");
            cachedSerQNames.add(qName);
            cls = pe.gob.mtc.wshr.Errores.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "Finalizar");
            cachedSerQNames.add(qName);
            cls = pe.gob.mtc.wshr.Finalizar.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "HConductor");
            cachedSerQNames.add(qName);
            cls = pe.gob.mtc.wshr.HConductor.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "HojaRuta");
            cachedSerQNames.add(qName);
            cls = pe.gob.mtc.wshr.HojaRuta.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "HTripulante");
            cachedSerQNames.add(qName);
            cls = pe.gob.mtc.wshr.HTripulante.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "Parametro");
            cachedSerQNames.add(qName);
            cls = pe.gob.mtc.wshr.Parametro.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "ResultConductor");
            cachedSerQNames.add(qName);
            cls = pe.gob.mtc.wshr.ResultConductor.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "ResultFinalizar");
            cachedSerQNames.add(qName);
            cls = pe.gob.mtc.wshr.ResultFinalizar.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "ResultHojaRuta");
            cachedSerQNames.add(qName);
            cls = pe.gob.mtc.wshr.ResultHojaRuta.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "ResultParametro");
            cachedSerQNames.add(qName);
            cls = pe.gob.mtc.wshr.ResultParametro.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "ResultVehiculo");
            cachedSerQNames.add(qName);
            cls = pe.gob.mtc.wshr.ResultVehiculo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "ResultViaje");
            cachedSerQNames.add(qName);
            cls = pe.gob.mtc.wshr.ResultViaje.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "Ruta");
            cachedSerQNames.add(qName);
            cls = pe.gob.mtc.wshr.Ruta.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "Seguridad");
            cachedSerQNames.add(qName);
            cls = pe.gob.mtc.wshr.Seguridad.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "Terminal");
            cachedSerQNames.add(qName);
            cls = pe.gob.mtc.wshr.Terminal.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "Vehiculo");
            cachedSerQNames.add(qName);
            cls = pe.gob.mtc.wshr.Vehiculo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "Viaje");
            cachedSerQNames.add(qName);
            cls = pe.gob.mtc.wshr.Viaje.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public pe.gob.mtc.wshr.ResultParametro getParametro(pe.gob.mtc.wshr.Parametro oParametro) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://wshr.mtc.gob.pe/getParametro");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "getParametro"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {oParametro});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (pe.gob.mtc.wshr.ResultParametro) _resp;
            } catch (java.lang.Exception _exception) {
                return (pe.gob.mtc.wshr.ResultParametro) org.apache.axis.utils.JavaUtils.convert(_resp, pe.gob.mtc.wshr.ResultParametro.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public pe.gob.mtc.wshr.ResultVehiculo getVehiculo(pe.gob.mtc.wshr.Vehiculo oVehiculo) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://wshr.mtc.gob.pe/getVehiculo");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "getVehiculo"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {oVehiculo});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (pe.gob.mtc.wshr.ResultVehiculo) _resp;
            } catch (java.lang.Exception _exception) {
                return (pe.gob.mtc.wshr.ResultVehiculo) org.apache.axis.utils.JavaUtils.convert(_resp, pe.gob.mtc.wshr.ResultVehiculo.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public pe.gob.mtc.wshr.ResultConductor getConductor(pe.gob.mtc.wshr.Conductor oConductor) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://wshr.mtc.gob.pe/getConductor");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "getConductor"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {oConductor});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (pe.gob.mtc.wshr.ResultConductor) _resp;
            } catch (java.lang.Exception _exception) {
                return (pe.gob.mtc.wshr.ResultConductor) org.apache.axis.utils.JavaUtils.convert(_resp, pe.gob.mtc.wshr.ResultConductor.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public pe.gob.mtc.wshr.ResultViaje setViaje(pe.gob.mtc.wshr.Viaje oViaje) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://wshr.mtc.gob.pe/setViaje");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "setViaje"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {oViaje});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (pe.gob.mtc.wshr.ResultViaje) _resp;
            } catch (java.lang.Exception _exception) {
                return (pe.gob.mtc.wshr.ResultViaje) org.apache.axis.utils.JavaUtils.convert(_resp, pe.gob.mtc.wshr.ResultViaje.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public pe.gob.mtc.wshr.ResultHojaRuta setHojaRuta(pe.gob.mtc.wshr.HojaRuta oHojaRuta) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://wshr.mtc.gob.pe/setHojaRuta");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "setHojaRuta"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {oHojaRuta});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (pe.gob.mtc.wshr.ResultHojaRuta) _resp;
            } catch (java.lang.Exception _exception) {
                return (pe.gob.mtc.wshr.ResultHojaRuta) org.apache.axis.utils.JavaUtils.convert(_resp, pe.gob.mtc.wshr.ResultHojaRuta.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public pe.gob.mtc.wshr.ResultFinalizar setFinalizar(pe.gob.mtc.wshr.Finalizar oFinalizar) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://wshr.mtc.gob.pe/setFinalizar");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "setFinalizar"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {oFinalizar});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (pe.gob.mtc.wshr.ResultFinalizar) _resp;
            } catch (java.lang.Exception _exception) {
                return (pe.gob.mtc.wshr.ResultFinalizar) org.apache.axis.utils.JavaUtils.convert(_resp, pe.gob.mtc.wshr.ResultFinalizar.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
