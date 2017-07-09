/**
 * ResultParametro.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pe.gob.mtc.wshr;

public class ResultParametro  implements java.io.Serializable {
    private boolean _return;

    private pe.gob.mtc.wshr.Ruta[] rutas;

    private pe.gob.mtc.wshr.Errores[] errores;

    private pe.gob.mtc.wshr.Terminal[] terminales;

    private pe.gob.mtc.wshr.Documento[] documentos;

    public ResultParametro() {
    }

    public ResultParametro(
           boolean _return,
           pe.gob.mtc.wshr.Ruta[] rutas,
           pe.gob.mtc.wshr.Errores[] errores,
           pe.gob.mtc.wshr.Terminal[] terminales,
           pe.gob.mtc.wshr.Documento[] documentos) {
           this._return = _return;
           this.rutas = rutas;
           this.errores = errores;
           this.terminales = terminales;
           this.documentos = documentos;
    }


    /**
     * Gets the _return value for this ResultParametro.
     * 
     * @return _return
     */
    public boolean is_return() {
        return _return;
    }


    /**
     * Sets the _return value for this ResultParametro.
     * 
     * @param _return
     */
    public void set_return(boolean _return) {
        this._return = _return;
    }


    /**
     * Gets the rutas value for this ResultParametro.
     * 
     * @return rutas
     */
    public pe.gob.mtc.wshr.Ruta[] getRutas() {
        return rutas;
    }


    /**
     * Sets the rutas value for this ResultParametro.
     * 
     * @param rutas
     */
    public void setRutas(pe.gob.mtc.wshr.Ruta[] rutas) {
        this.rutas = rutas;
    }


    /**
     * Gets the errores value for this ResultParametro.
     * 
     * @return errores
     */
    public pe.gob.mtc.wshr.Errores[] getErrores() {
        return errores;
    }


    /**
     * Sets the errores value for this ResultParametro.
     * 
     * @param errores
     */
    public void setErrores(pe.gob.mtc.wshr.Errores[] errores) {
        this.errores = errores;
    }


    /**
     * Gets the terminales value for this ResultParametro.
     * 
     * @return terminales
     */
    public pe.gob.mtc.wshr.Terminal[] getTerminales() {
        return terminales;
    }


    /**
     * Sets the terminales value for this ResultParametro.
     * 
     * @param terminales
     */
    public void setTerminales(pe.gob.mtc.wshr.Terminal[] terminales) {
        this.terminales = terminales;
    }


    /**
     * Gets the documentos value for this ResultParametro.
     * 
     * @return documentos
     */
    public pe.gob.mtc.wshr.Documento[] getDocumentos() {
        return documentos;
    }


    /**
     * Sets the documentos value for this ResultParametro.
     * 
     * @param documentos
     */
    public void setDocumentos(pe.gob.mtc.wshr.Documento[] documentos) {
        this.documentos = documentos;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ResultParametro)) return false;
        ResultParametro other = (ResultParametro) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this._return == other.is_return() &&
            ((this.rutas==null && other.getRutas()==null) || 
             (this.rutas!=null &&
              java.util.Arrays.equals(this.rutas, other.getRutas()))) &&
            ((this.errores==null && other.getErrores()==null) || 
             (this.errores!=null &&
              java.util.Arrays.equals(this.errores, other.getErrores()))) &&
            ((this.terminales==null && other.getTerminales()==null) || 
             (this.terminales!=null &&
              java.util.Arrays.equals(this.terminales, other.getTerminales()))) &&
            ((this.documentos==null && other.getDocumentos()==null) || 
             (this.documentos!=null &&
              java.util.Arrays.equals(this.documentos, other.getDocumentos())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        _hashCode += (is_return() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getRutas() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRutas());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRutas(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getErrores() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getErrores());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getErrores(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getTerminales() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTerminales());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTerminales(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDocumentos() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDocumentos());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDocumentos(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ResultParametro.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "ResultParametro"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_return");
        elemField.setXmlName(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "Return"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rutas");
        elemField.setXmlName(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "Rutas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "Ruta"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "Ruta"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errores");
        elemField.setXmlName(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "Errores"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "Errores"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "Errores"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("terminales");
        elemField.setXmlName(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "Terminales"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "Terminal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "Terminal"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("documentos");
        elemField.setXmlName(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "Documentos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "Documento"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "Documento"));
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
