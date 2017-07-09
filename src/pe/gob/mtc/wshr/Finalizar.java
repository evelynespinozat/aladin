/**
 * Finalizar.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pe.gob.mtc.wshr;

public class Finalizar  implements java.io.Serializable {
    private java.lang.String code;

    private java.lang.String fecLlegada;

    private java.lang.String horLlegada;

    private pe.gob.mtc.wshr.Seguridad seguridad;

    public Finalizar() {
    }

    public Finalizar(
           java.lang.String code,
           java.lang.String fecLlegada,
           java.lang.String horLlegada,
           pe.gob.mtc.wshr.Seguridad seguridad) {
           this.code = code;
           this.fecLlegada = fecLlegada;
           this.horLlegada = horLlegada;
           this.seguridad = seguridad;
    }


    /**
     * Gets the code value for this Finalizar.
     * 
     * @return code
     */
    public java.lang.String getCode() {
        return code;
    }


    /**
     * Sets the code value for this Finalizar.
     * 
     * @param code
     */
    public void setCode(java.lang.String code) {
        this.code = code;
    }


    /**
     * Gets the fecLlegada value for this Finalizar.
     * 
     * @return fecLlegada
     */
    public java.lang.String getFecLlegada() {
        return fecLlegada;
    }


    /**
     * Sets the fecLlegada value for this Finalizar.
     * 
     * @param fecLlegada
     */
    public void setFecLlegada(java.lang.String fecLlegada) {
        this.fecLlegada = fecLlegada;
    }


    /**
     * Gets the horLlegada value for this Finalizar.
     * 
     * @return horLlegada
     */
    public java.lang.String getHorLlegada() {
        return horLlegada;
    }


    /**
     * Sets the horLlegada value for this Finalizar.
     * 
     * @param horLlegada
     */
    public void setHorLlegada(java.lang.String horLlegada) {
        this.horLlegada = horLlegada;
    }


    /**
     * Gets the seguridad value for this Finalizar.
     * 
     * @return seguridad
     */
    public pe.gob.mtc.wshr.Seguridad getSeguridad() {
        return seguridad;
    }


    /**
     * Sets the seguridad value for this Finalizar.
     * 
     * @param seguridad
     */
    public void setSeguridad(pe.gob.mtc.wshr.Seguridad seguridad) {
        this.seguridad = seguridad;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Finalizar)) return false;
        Finalizar other = (Finalizar) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.code==null && other.getCode()==null) || 
             (this.code!=null &&
              this.code.equals(other.getCode()))) &&
            ((this.fecLlegada==null && other.getFecLlegada()==null) || 
             (this.fecLlegada!=null &&
              this.fecLlegada.equals(other.getFecLlegada()))) &&
            ((this.horLlegada==null && other.getHorLlegada()==null) || 
             (this.horLlegada!=null &&
              this.horLlegada.equals(other.getHorLlegada()))) &&
            ((this.seguridad==null && other.getSeguridad()==null) || 
             (this.seguridad!=null &&
              this.seguridad.equals(other.getSeguridad())));
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
        if (getCode() != null) {
            _hashCode += getCode().hashCode();
        }
        if (getFecLlegada() != null) {
            _hashCode += getFecLlegada().hashCode();
        }
        if (getHorLlegada() != null) {
            _hashCode += getHorLlegada().hashCode();
        }
        if (getSeguridad() != null) {
            _hashCode += getSeguridad().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Finalizar.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "Finalizar"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("code");
        elemField.setXmlName(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "Code"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fecLlegada");
        elemField.setXmlName(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "FecLlegada"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("horLlegada");
        elemField.setXmlName(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "HorLlegada"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("seguridad");
        elemField.setXmlName(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "Seguridad"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "Seguridad"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
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
