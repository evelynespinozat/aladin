/**
 * Vehiculo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pe.gob.mtc.wshr;

public class Vehiculo  implements java.io.Serializable {
    private java.lang.String nroPlaca;

    private pe.gob.mtc.wshr.Seguridad seguridad;

    public Vehiculo() {
    }

    public Vehiculo(
           java.lang.String nroPlaca,
           pe.gob.mtc.wshr.Seguridad seguridad) {
           this.nroPlaca = nroPlaca;
           this.seguridad = seguridad;
    }


    /**
     * Gets the nroPlaca value for this Vehiculo.
     * 
     * @return nroPlaca
     */
    public java.lang.String getNroPlaca() {
        return nroPlaca;
    }


    /**
     * Sets the nroPlaca value for this Vehiculo.
     * 
     * @param nroPlaca
     */
    public void setNroPlaca(java.lang.String nroPlaca) {
        this.nroPlaca = nroPlaca;
    }


    /**
     * Gets the seguridad value for this Vehiculo.
     * 
     * @return seguridad
     */
    public pe.gob.mtc.wshr.Seguridad getSeguridad() {
        return seguridad;
    }


    /**
     * Sets the seguridad value for this Vehiculo.
     * 
     * @param seguridad
     */
    public void setSeguridad(pe.gob.mtc.wshr.Seguridad seguridad) {
        this.seguridad = seguridad;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Vehiculo)) return false;
        Vehiculo other = (Vehiculo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.nroPlaca==null && other.getNroPlaca()==null) || 
             (this.nroPlaca!=null &&
              this.nroPlaca.equals(other.getNroPlaca()))) &&
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
        if (getNroPlaca() != null) {
            _hashCode += getNroPlaca().hashCode();
        }
        if (getSeguridad() != null) {
            _hashCode += getSeguridad().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Vehiculo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "Vehiculo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nroPlaca");
        elemField.setXmlName(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "NroPlaca"));
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
