package es.us.data;

/* http://hl7-definition.caristix.com:9010/Default.aspx?version=HL7%20v2.5.1&segment=MSH */

public class SegmentMSH {

	private String control;
	private String tipo;
	private String fecha;
	private String aplicacion;
	private String version;

	public SegmentMSH(String control, String tipo, String fecha, String aplicacion,
			String version) {
		this.control = control;
		this.tipo = tipo;
		this.fecha = fecha;
		this.aplicacion = aplicacion;
		this.version = version;
	}

	public String getControl() {
		return control;
	}

	public String getTipo() {
		return tipo;
	}

	public String getFecha() {
		return fecha;
	}

	public String getAplicacion() {
		return aplicacion;
	}

	public String getVersion() {
		return version;
	}

}
