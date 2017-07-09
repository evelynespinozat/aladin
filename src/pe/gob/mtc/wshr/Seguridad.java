/**
 * Seguridad.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pe.gob.mtc.wshr;

public class Seguridad  implements java.io.Serializable {
    private java.lang.String ruc;

    private java.lang.String usuario;

    private java.lang.String clave;

    private java.lang.String partida;

    public Seguridad() {
    }

    public Seguridad(
           java.lang.String ruc,
           java.lang.String usuario,
           java.lang.String clave,
           java.lang.String partida) {
           this.ruc = ruc;
           this.usuario = usuario;
           this.clave = clave;
           this.partida = partida;
    }


    /**
     * Gets the ruc value for this Seguridad.
     * 
     * @return ruc
     */
    public java.lang.String getRuc() {
        return ruc;
    }


    /**
     * Sets the ruc value for this Seguridad.
     * 
     * @param ruc
     */
    public void setRuc(java.lang.String ruc) {
        this.ruc = ruc;
    }


    /**
     * Gets the usuario value for this Seguridad.
     * 
     * @return usuario
     */
    public java.lang.String getUsuario() {
        return usuario;
    }


    /**
     * Sets the usuario value for this Seguridad.
     * 
     * @param usuario
     */
    public void setUsuario(java.lang.String usuario) {
        this.usuario = usuario;
    }


    /**
     * Gets the clave value for this Seguridad.
     * 
     * @return clave
     */
    public java.lang.String getClave() {
        return clave;
    }


    /**
     * Sets the clave value for this Seguridad.
     * 
     * @param clave
     */
    public void setClave(java.lang.String clave) {
        this.clave = clave;
    }


    /**
     * Gets the partida value for this Seguridad.
     * 
     * @return partida
     */
    public java.lang.String getPartida() {
        return partida;
    }


    /**
     * Sets the partida value for this Seguridad.
     * 
     * @param partida
     */
    public void setPartida(java.lang.String partida) {
        this.partida = partida;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Seguridad)) return false;
        Seguridad other = (Seguridad) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ruc==null && other.getRuc()==null) || 
             (this.ruc!=null &&
              this.ruc.equals(other.getRuc()))) &&
            ((this.usuario==null && other.getUsuario()==null) || 
             (this.usuario!=null &&
              this.usuario.equals(other.getUsuario()))) &&
            ((this.clave==null && other.getClave()==null) || 
             (this.clave!=null &&
              this.clave.equals(other.getClave()))) &&
            ((this.partida==null && other.getPartida()==null) || 
             (this.partida!=null &&
              this.partida.equals(other.getPartida())));
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
        if (getRuc() != null) {
            _hashCode += getRuc().hashCode();
        }
        if (getUsuario() != null) {
            _hashCode += getUsuario().hashCode();
        }
        if (getClave() != null) {
            _hashCode += getClave().hashCode();
        }
        if (getPartida() != null) {
            _hashCode += getPartida().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Seguridad.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "Seguridad"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ruc");
        elemField.setXmlName(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "Ruc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("usuario");
        elemField.setXmlName(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "Usuario"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clave");
        elemField.setXmlName(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "Clave"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("partida");
        elemField.setXmlName(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "Partida"));
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
