/**
 * HTripulante.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pe.gob.mtc.wshr;

public class HTripulante  implements java.io.Serializable {
    private java.lang.String tpoDocumento;

    private java.lang.String nroDocumento;

    public HTripulante() {
    }

    public HTripulante(
           java.lang.String tpoDocumento,
           java.lang.String nroDocumento) {
           this.tpoDocumento = tpoDocumento;
           this.nroDocumento = nroDocumento;
    }


    /**
     * Gets the tpoDocumento value for this HTripulante.
     * 
     * @return tpoDocumento
     */
    public java.lang.String getTpoDocumento() {
        return tpoDocumento;
    }


    /**
     * Sets the tpoDocumento value for this HTripulante.
     * 
     * @param tpoDocumento
     */
    public void setTpoDocumento(java.lang.String tpoDocumento) {
        this.tpoDocumento = tpoDocumento;
    }


    /**
     * Gets the nroDocumento value for this HTripulante.
     * 
     * @return nroDocumento
     */
    public java.lang.String getNroDocumento() {
        return nroDocumento;
    }


    /**
     * Sets the nroDocumento value for this HTripulante.
     * 
     * @param nroDocumento
     */
    public void setNroDocumento(java.lang.String nroDocumento) {
        this.nroDocumento = nroDocumento;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof HTripulante)) return false;
        HTripulante other = (HTripulante) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.tpoDocumento==null && other.getTpoDocumento()==null) || 
             (this.tpoDocumento!=null &&
              this.tpoDocumento.equals(other.getTpoDocumento()))) &&
            ((this.nroDocumento==null && other.getNroDocumento()==null) || 
             (this.nroDocumento!=null &&
              this.nroDocumento.equals(other.getNroDocumento())));
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
        if (getTpoDocumento() != null) {
            _hashCode += getTpoDocumento().hashCode();
        }
        if (getNroDocumento() != null) {
            _hashCode += getNroDocumento().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(HTripulante.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "HTripulante"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tpoDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "TpoDocumento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nroDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "NroDocumento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
