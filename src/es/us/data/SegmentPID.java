package es.us.data;

/* http://hl7-definition.caristix.com:9010/Default.aspx?version=HL7%20v2.5.1&segment=PID */

public class SegmentPID {

	private String nombre;
	private String nacimiento;
	private String sexo;
	private String direccion;
	private String nss;

	public SegmentPID(String nombre, String nacimiento, String sexo, String direccion,
			String nss) {
		this.nombre = nombre;
		this.nacimiento = nacimiento;
		this.sexo = sexo;
		this.direccion = direccion;
		this.nss = nss;
	}

	public String getNombre() {
		return nombre;
	}

	public String getNacimiento() {
		return nacimiento;
	}

	public String getSexo() {
		return sexo;
	}

	public String getDireccion() {
		return direccion;
	}

	public String getNss() {
		return nss;
	}

}
