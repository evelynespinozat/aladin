/**
 * Viaje.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pe.gob.mtc.wshr;

public class Viaje  implements java.io.Serializable {
    private java.lang.String nroRuta;

    private int terSalida;

    private int terLlegada;

    private java.lang.String fecSalida;

    private java.lang.String horSalida;

    private java.lang.String fecEstLlegada;

    private java.lang.String horEstLlegada;

    private pe.gob.mtc.wshr.Seguridad seguridad;

    public Viaje() {
    }

    public Viaje(
           java.lang.String nroRuta,
           int terSalida,
           int terLlegada,
           java.lang.String fecSalida,
           java.lang.String horSalida,
           java.lang.String fecEstLlegada,
           java.lang.String horEstLlegada,
           pe.gob.mtc.wshr.Seguridad seguridad) {
           this.nroRuta = nroRuta;
           this.terSalida = terSalida;
           this.terLlegada = terLlegada;
           this.fecSalida = fecSalida;
           this.horSalida = horSalida;
           this.fecEstLlegada = fecEstLlegada;
           this.horEstLlegada = horEstLlegada;
           this.seguridad = seguridad;
    }


    /**
     * Gets the nroRuta value for this Viaje.
     * 
     * @return nroRuta
     */
    public java.lang.String getNroRuta() {
        return nroRuta;
    }


    /**
     * Sets the nroRuta value for this Viaje.
     * 
     * @param nroRuta
     */
    public void setNroRuta(java.lang.String nroRuta) {
        this.nroRuta = nroRuta;
    }


    /**
     * Gets the terSalida value for this Viaje.
     * 
     * @return terSalida
     */
    public int getTerSalida() {
        return terSalida;
    }


    /**
     * Sets the terSalida value for this Viaje.
     * 
     * @param terSalida
     */
    public void setTerSalida(int terSalida) {
        this.terSalida = terSalida;
    }


    /**
     * Gets the terLlegada value for this Viaje.
     * 
     * @return terLlegada
     */
    public int getTerLlegada() {
        return terLlegada;
    }


    /**
     * Sets the terLlegada value for this Viaje.
     * 
     * @param terLlegada
     */
    public void setTerLlegada(int terLlegada) {
        this.terLlegada = terLlegada;
    }


    /**
     * Gets the fecSalida value for this Viaje.
     * 
     * @return fecSalida
     */
    public java.lang.String getFecSalida() {
        return fecSalida;
    }


    /**
     * Sets the fecSalida value for this Viaje.
     * 
     * @param fecSalida
     */
    public void setFecSalida(java.lang.String fecSalida) {
        this.fecSalida = fecSalida;
    }


    /**
     * Gets the horSalida value for this Viaje.
     * 
     * @return horSalida
     */
    public java.lang.String getHorSalida() {
        return horSalida;
    }


    /**
     * Sets the horSalida value for this Viaje.
     * 
     * @param horSalida
     */
    public void setHorSalida(java.lang.String horSalida) {
        this.horSalida = horSalida;
    }


    /**
     * Gets the fecEstLlegada value for this Viaje.
     * 
     * @return fecEstLlegada
     */
    public java.lang.String getFecEstLlegada() {
        return fecEstLlegada;
    }


    /**
     * Sets the fecEstLlegada value for this Viaje.
     * 
     * @param fecEstLlegada
     */
    public void setFecEstLlegada(java.lang.String fecEstLlegada) {
        this.fecEstLlegada = fecEstLlegada;
    }


    /**
     * Gets the horEstLlegada value for this Viaje.
     * 
     * @return horEstLlegada
     */
    public java.lang.String getHorEstLlegada() {
        return horEstLlegada;
    }


    /**
     * Sets the horEstLlegada value for this Viaje.
     * 
     * @param horEstLlegada
     */
    public void setHorEstLlegada(java.lang.String horEstLlegada) {
        this.horEstLlegada = horEstLlegada;
    }


    /**
     * Gets the seguridad value for this Viaje.
     * 
     * @return seguridad
     */
    public pe.gob.mtc.wshr.Seguridad getSeguridad() {
        return seguridad;
    }


    /**
     * Sets the seguridad value for this Viaje.
     * 
     * @param seguridad
     */
    public void setSeguridad(pe.gob.mtc.wshr.Seguridad seguridad) {
        this.seguridad = seguridad;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Viaje)) return false;
        Viaje other = (Viaje) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.nroRuta==null && other.getNroRuta()==null) || 
             (this.nroRuta!=null &&
              this.nroRuta.equals(other.getNroRuta()))) &&
            this.terSalida == other.getTerSalida() &&
            this.terLlegada == other.getTerLlegada() &&
            ((this.fecSalida==null && other.getFecSalida()==null) || 
             (this.fecSalida!=null &&
              this.fecSalida.equals(other.getFecSalida()))) &&
            ((this.horSalida==null && other.getHorSalida()==null) || 
             (this.horSalida!=null &&
              this.horSalida.equals(other.getHorSalida()))) &&
            ((this.fecEstLlegada==null && other.getFecEstLlegada()==null) || 
             (this.fecEstLlegada!=null &&
              this.fecEstLlegada.equals(other.getFecEstLlegada()))) &&
            ((this.horEstLlegada==null && other.getHorEstLlegada()==null) || 
             (this.horEstLlegada!=null &&
              this.horEstLlegada.equals(other.getHorEstLlegada()))) &&
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
        if (getNroRuta() != null) {
            _hashCode += getNroRuta().hashCode();
        }
        _hashCode += getTerSalida();
        _hashCode += getTerLlegada();
        if (getFecSalida() != null) {
            _hashCode += getFecSalida().hashCode();
        }
        if (getHorSalida() != null) {
            _hashCode += getHorSalida().hashCode();
        }
        if (getFecEstLlegada() != null) {
            _hashCode += getFecEstLlegada().hashCode();
        }
        if (getHorEstLlegada() != null) {
            _hashCode += getHorEstLlegada().hashCode();
        }
        if (getSeguridad() != null) {
            _hashCode += getSeguridad().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Viaje.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "Viaje"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nroRuta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "NroRuta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("terSalida");
        elemField.setXmlName(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "TerSalida"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("terLlegada");
        elemField.setXmlName(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "TerLlegada"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fecSalida");
        elemField.setXmlName(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "FecSalida"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("horSalida");
        elemField.setXmlName(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "HorSalida"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fecEstLlegada");
        elemField.setXmlName(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "FecEstLlegada"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("horEstLlegada");
        elemField.setXmlName(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "HorEstLlegada"));
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
