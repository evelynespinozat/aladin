/**
 * HConductor.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pe.gob.mtc.wshr;

public class HConductor  implements java.io.Serializable {
    private java.lang.String tpoDocumento;

    private java.lang.String nroDocumento;

    private java.lang.String fecInicio;

    private java.lang.String horInicio;

    private java.lang.String fecTermino;

    private java.lang.String horTermino;

    public HConductor() {
    }

    public HConductor(
           java.lang.String tpoDocumento,
           java.lang.String nroDocumento,
           java.lang.String fecInicio,
           java.lang.String horInicio,
           java.lang.String fecTermino,
           java.lang.String horTermino) {
           this.tpoDocumento = tpoDocumento;
           this.nroDocumento = nroDocumento;
           this.fecInicio = fecInicio;
           this.horInicio = horInicio;
           this.fecTermino = fecTermino;
           this.horTermino = horTermino;
    }


    /**
     * Gets the tpoDocumento value for this HConductor.
     * 
     * @return tpoDocumento
     */
    public java.lang.String getTpoDocumento() {
        return tpoDocumento;
    }


    /**
     * Sets the tpoDocumento value for this HConductor.
     * 
     * @param tpoDocumento
     */
    public void setTpoDocumento(java.lang.String tpoDocumento) {
        this.tpoDocumento = tpoDocumento;
    }


    /**
     * Gets the nroDocumento value for this HConductor.
     * 
     * @return nroDocumento
     */
    public java.lang.String getNroDocumento() {
        return nroDocumento;
    }


    /**
     * Sets the nroDocumento value for this HConductor.
     * 
     * @param nroDocumento
     */
    public void setNroDocumento(java.lang.String nroDocumento) {
        this.nroDocumento = nroDocumento;
    }


    /**
     * Gets the fecInicio value for this HConductor.
     * 
     * @return fecInicio
     */
    public java.lang.String getFecInicio() {
        return fecInicio;
    }


    /**
     * Sets the fecInicio value for this HConductor.
     * 
     * @param fecInicio
     */
    public void setFecInicio(java.lang.String fecInicio) {
        this.fecInicio = fecInicio;
    }


    /**
     * Gets the horInicio value for this HConductor.
     * 
     * @return horInicio
     */
    public java.lang.String getHorInicio() {
        return horInicio;
    }


    /**
     * Sets the horInicio value for this HConductor.
     * 
     * @param horInicio
     */
    public void setHorInicio(java.lang.String horInicio) {
        this.horInicio = horInicio;
    }


    /**
     * Gets the fecTermino value for this HConductor.
     * 
     * @return fecTermino
     */
    public java.lang.String getFecTermino() {
        return fecTermino;
    }


    /**
     * Sets the fecTermino value for this HConductor.
     * 
     * @param fecTermino
     */
    public void setFecTermino(java.lang.String fecTermino) {
        this.fecTermino = fecTermino;
    }


    /**
     * Gets the horTermino value for this HConductor.
     * 
     * @return horTermino
     */
    public java.lang.String getHorTermino() {
        return horTermino;
    }


    /**
     * Sets the horTermino value for this HConductor.
     * 
     * @param horTermino
     */
    public void setHorTermino(java.lang.String horTermino) {
        this.horTermino = horTermino;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof HConductor)) return false;
        HConductor other = (HConductor) obj;
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
              this.nroDocumento.equals(other.getNroDocumento()))) &&
            ((this.fecInicio==null && other.getFecInicio()==null) || 
             (this.fecInicio!=null &&
              this.fecInicio.equals(other.getFecInicio()))) &&
            ((this.horInicio==null && other.getHorInicio()==null) || 
             (this.horInicio!=null &&
              this.horInicio.equals(other.getHorInicio()))) &&
            ((this.fecTermino==null && other.getFecTermino()==null) || 
             (this.fecTermino!=null &&
              this.fecTermino.equals(other.getFecTermino()))) &&
            ((this.horTermino==null && other.getHorTermino()==null) || 
             (this.horTermino!=null &&
              this.horTermino.equals(other.getHorTermino())));
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
        if (getFecInicio() != null) {
            _hashCode += getFecInicio().hashCode();
        }
        if (getHorInicio() != null) {
            _hashCode += getHorInicio().hashCode();
        }
        if (getFecTermino() != null) {
            _hashCode += getFecTermino().hashCode();
        }
        if (getHorTermino() != null) {
            _hashCode += getHorTermino().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(HConductor.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "HConductor"));
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fecInicio");
        elemField.setXmlName(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "FecInicio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("horInicio");
        elemField.setXmlName(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "HorInicio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fecTermino");
        elemField.setXmlName(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "FecTermino"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("horTermino");
        elemField.setXmlName(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "HorTermino"));
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
