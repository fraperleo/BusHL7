package es.us.clases;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import es.us.data.MessageORU;
import es.us.data.SegmentMSH;
import es.us.data.SegmentOBX;
import es.us.data.SegmentPID;

public class Persist {

	
	public static void escribeEnBD(MessageORU orumsg) throws NumberFormatException, SQLException{		
		//Nos conectamos a la Base de datos
		ConexionBD.conectar();
        Statement st = ConexionBD.conexion();
        String cadena="";
        
        //Consultamos el identificador del paciente        
        Integer IdNSS = getIdentificadorPaciente(st, orumsg.getPid().getNss());
        if(IdNSS==null){ //Si el id es nulo significa que no existía el paciente y debemos de insertarlo
        	SegmentPID pid = orumsg.getPid();
        	cadena = "INSERT INTO `paciente`(`Nombre`, `FechaNacimiento`, `Sexo`, `Direccion`, `NSS`) VALUES ('" + pid.getNombre() + "', '" + pid.getNacimiento() + "', '" + pid.getSexo() + "', '" + pid.getDireccion() + "', '" + pid.getNss() + "')";
            ConexionBD.consultaActualiza(st, cadena);
            IdNSS = getIdentificadorPaciente(st, pid.getNss());
        }
        
        //Introducimos el segmento msh
        SegmentMSH msh = orumsg.getMsh();
        cadena = "INSERT INTO `msh`(`Control`, `Tipo`, `Fecha`, `Aplicacion`, `Version`, `IdPaciente`) VALUES ('" + msh.getControl() + "','" + msh.getTipo() + "','" + msh.getFecha() + "','" + msh.getAplicacion() + "', '" + msh.getVersion() +"'," + IdNSS +")";
        ConexionBD.consultaActualiza(st, cadena);
        
        //Tenemso que consultar el último msh que hemos introducido
        Integer IdMSH = getMaxIdMSH(st);
        
        //Introducimos los segmentos obx (tantos como haya)
        List<SegmentOBX> obxs = orumsg.getObx();
        obxs.forEach(obx -> {
        	String cadena2;
        	cadena2 = "INSERT INTO `obx`(`IdTipo`, `Tipo`, `Valor`, `Unidad`, `IdMSH`) VALUES ('" + obx.getIdTipo() + "','" + obx.getTipo() + "','" + obx.getValor() + "','" + obx.getUnidad() + "','" + IdMSH +"')";
            ConexionBD.consultaActualiza(st, cadena2);            	
        });
            
        
        //Cerramos la conexion                
        ConexionBD.cerrar(st);
		
	}
	
	private static Integer getIdentificadorPaciente(Statement st, String nss) throws NumberFormatException, SQLException{
		Integer ret=null; //ID Paciente
		String consulta = "SELECT * FROM paciente where `NSS`='" + nss + "'" ;
		ResultSet rs=null;
		rs = ConexionBD.consultaQuery(st, consulta);       
        if(rs != null){
        	while(rs.next()){
        		ret = Integer.valueOf(rs.getString("Identificador"));
        		//System.out.println(ret);
        	}
        }
        ConexionBD.cerrar(rs);		
		return ret;
	}
	
	private static Integer getMaxIdMSH(Statement st) throws NumberFormatException, SQLException {
		Integer ret = null; // Id MSH
		String consulta = "SELECT MAX(`IdMSH`) FROM msh";
		ResultSet rs = null;
		rs = ConexionBD.consultaQuery(st, consulta);

		if (rs != null) {
			while (rs.next()) {
				ret = Integer.valueOf(rs.getString(1));
				System.out.println(ret);
			}
		}
		ConexionBD.cerrar(rs);

		return ret;
	}

}
