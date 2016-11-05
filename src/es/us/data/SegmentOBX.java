package es.us.data;

/* http://hl7-definition.caristix.com:9010/Default.aspx?version=HL7%20v2.5.1&segment=OBX */

public class SegmentOBX {

	private String idTipo;
	private String tipo;
	private String valor;
	private String unidad;

	public SegmentOBX(String idTipo, String tipo, String valor, String unidad) {
		this.idTipo = idTipo;
		this.tipo = tipo;
		this.valor = valor;
		this.unidad = unidad;
	}

	public String getIdTipo() {
		return idTipo;
	}

	public String getTipo() {
		return tipo;
	}

	public String getValor() {
		return valor;
	}

	public String getUnidad() {
		return unidad;
	}

}
