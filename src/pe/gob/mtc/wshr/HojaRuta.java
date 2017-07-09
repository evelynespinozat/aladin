/**
 * HojaRuta.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pe.gob.mtc.wshr;

public class HojaRuta  implements java.io.Serializable {
    private java.lang.String nroRuta;

    private java.lang.String nroPlaca;

    private int terSalida;

    private int terLlegada;

    private java.lang.String fecSalida;

    private java.lang.String horSalida;

    private java.lang.String fecEstLlegada;

    private java.lang.String horEstLlegada;

    private pe.gob.mtc.wshr.HConductor[] conductores;

    private pe.gob.mtc.wshr.HTripulante[] tripulantes;

    private pe.gob.mtc.wshr.Seguridad seguridad;

    public HojaRuta() {
    }

    public HojaRuta(
           java.lang.String nroRuta,
           java.lang.String nroPlaca,
           int terSalida,
           int terLlegada,
           java.lang.String fecSalida,
           java.lang.String horSalida,
           java.lang.String fecEstLlegada,
           java.lang.String horEstLlegada,
           pe.gob.mtc.wshr.HConductor[] conductores,
           pe.gob.mtc.wshr.HTripulante[] tripulantes,
           pe.gob.mtc.wshr.Seguridad seguridad) {
           this.nroRuta = nroRuta;
           this.nroPlaca = nroPlaca;
           this.terSalida = terSalida;
           this.terLlegada = terLlegada;
           this.fecSalida = fecSalida;
           this.horSalida = horSalida;
           this.fecEstLlegada = fecEstLlegada;
           this.horEstLlegada = horEstLlegada;
           this.conductores = conductores;
           this.tripulantes = tripulantes;
           this.seguridad = seguridad;
    }


    /**
     * Gets the nroRuta value for this HojaRuta.
     * 
     * @return nroRuta
     */
    public java.lang.String getNroRuta() {
        return nroRuta;
    }


    /**
     * Sets the nroRuta value for this HojaRuta.
     * 
     * @param nroRuta
     */
    public void setNroRuta(java.lang.String nroRuta) {
        this.nroRuta = nroRuta;
    }


    /**
     * Gets the nroPlaca value for this HojaRuta.
     * 
     * @return nroPlaca
     */
    public java.lang.String getNroPlaca() {
        return nroPlaca;
    }


    /**
     * Sets the nroPlaca value for this HojaRuta.
     * 
     * @param nroPlaca
     */
    public void setNroPlaca(java.lang.String nroPlaca) {
        this.nroPlaca = nroPlaca;
    }


    /**
     * Gets the terSalida value for this HojaRuta.
     * 
     * @return terSalida
     */
    public int getTerSalida() {
        return terSalida;
    }


    /**
     * Sets the terSalida value for this HojaRuta.
     * 
     * @param terSalida
     */
    public void setTerSalida(int terSalida) {
        this.terSalida = terSalida;
    }


    /**
     * Gets the terLlegada value for this HojaRuta.
     * 
     * @return terLlegada
     */
    public int getTerLlegada() {
        return terLlegada;
    }


    /**
     * Sets the terLlegada value for this HojaRuta.
     * 
     * @param terLlegada
     */
    public void setTerLlegada(int terLlegada) {
        this.terLlegada = terLlegada;
    }


    /**
     * Gets the fecSalida value for this HojaRuta.
     * 
     * @return fecSalida
     */
    public java.lang.String getFecSalida() {
        return fecSalida;
    }


    /**
     * Sets the fecSalida value for this HojaRuta.
     * 
     * @param fecSalida
     */
    public void setFecSalida(java.lang.String fecSalida) {
        this.fecSalida = fecSalida;
    }


    /**
     * Gets the horSalida value for this HojaRuta.
     * 
     * @return horSalida
     */
    public java.lang.String getHorSalida() {
        return horSalida;
    }


    /**
     * Sets the horSalida value for this HojaRuta.
     * 
     * @param horSalida
     */
    public void setHorSalida(java.lang.String horSalida) {
        this.horSalida = horSalida;
    }


    /**
     * Gets the fecEstLlegada value for this HojaRuta.
     * 
     * @return fecEstLlegada
     */
    public java.lang.String getFecEstLlegada() {
        return fecEstLlegada;
    }


    /**
     * Sets the fecEstLlegada value for this HojaRuta.
     * 
     * @param fecEstLlegada
     */
    public void setFecEstLlegada(java.lang.String fecEstLlegada) {
        this.fecEstLlegada = fecEstLlegada;
    }


    /**
     * Gets the horEstLlegada value for this HojaRuta.
     * 
     * @return horEstLlegada
     */
    public java.lang.String getHorEstLlegada() {
        return horEstLlegada;
    }


    /**
     * Sets the horEstLlegada value for this HojaRuta.
     * 
     * @param horEstLlegada
     */
    public void setHorEstLlegada(java.lang.String horEstLlegada) {
        this.horEstLlegada = horEstLlegada;
    }


    /**
     * Gets the conductores value for this HojaRuta.
     * 
     * @return conductores
     */
    public pe.gob.mtc.wshr.HConductor[] getConductores() {
        return conductores;
    }


    /**
     * Sets the conductores value for this HojaRuta.
     * 
     * @param conductores
     */
    public void setConductores(pe.gob.mtc.wshr.HConductor[] conductores) {
        this.conductores = conductores;
    }


    /**
     * Gets the tripulantes value for this HojaRuta.
     * 
     * @return tripulantes
     */
    public pe.gob.mtc.wshr.HTripulante[] getTripulantes() {
        return tripulantes;
    }


    /**
     * Sets the tripulantes value for this HojaRuta.
     * 
     * @param tripulantes
     */
    public void setTripulantes(pe.gob.mtc.wshr.HTripulante[] tripulantes) {
        this.tripulantes = tripulantes;
    }


    /**
     * Gets the seguridad value for this HojaRuta.
     * 
     * @return seguridad
     */
    public pe.gob.mtc.wshr.Seguridad getSeguridad() {
        return seguridad;
    }


    /**
     * Sets the seguridad value for this HojaRuta.
     * 
     * @param seguridad
     */
    public void setSeguridad(pe.gob.mtc.wshr.Seguridad seguridad) {
        this.seguridad = seguridad;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof HojaRuta)) return false;
        HojaRuta other = (HojaRuta) obj;
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
            ((this.nroPlaca==null && other.getNroPlaca()==null) || 
             (this.nroPlaca!=null &&
              this.nroPlaca.equals(other.getNroPlaca()))) &&
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
            ((this.conductores==null && other.getConductores()==null) || 
             (this.conductores!=null &&
              java.util.Arrays.equals(this.conductores, other.getConductores()))) &&
            ((this.tripulantes==null && other.getTripulantes()==null) || 
             (this.tripulantes!=null &&
              java.util.Arrays.equals(this.tripulantes, other.getTripulantes()))) &&
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
        if (getNroPlaca() != null) {
            _hashCode += getNroPlaca().hashCode();
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
        if (getConductores() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getConductores());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getConductores(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getTripulantes() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTripulantes());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTripulantes(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSeguridad() != null) {
            _hashCode += getSeguridad().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(HojaRuta.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "HojaRuta"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nroRuta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "NroRuta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nroPlaca");
        elemField.setXmlName(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "NroPlaca"));
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
        elemField.setFieldName("conductores");
        elemField.setXmlName(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "Conductores"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "HConductor"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "HConductor"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tripulantes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "Tripulantes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "HTripulante"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://wshr.mtc.gob.pe/", "HTripulante"));
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
