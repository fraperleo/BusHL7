package es.us.test;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.v24.message.ORU_R01;
import ca.uhn.hl7v2.parser.Parser;
import es.us.data.MessageORU;
import es.us.data.SegmentOBX;

public class ParseMessages {

    
      public static void main(String[] args) {
         
    	  String msg = "MSH|^~\\&|^SureSignsVS||||20120214112334||ORU^R01^ORU_R01|US9362303820120214112334|P|2.4|||||||||2.16.840.1.113883.9.2.1\r"
                  + "PID|||PATID1234^5^M11||JONES^WILLIAM^A^III||19610615|M-||C|1200 N ELM STREET^^GREENSBORO^NC^27401-1020|GL|(919)379-1212|(919)271-3434||S||PATID12345001^2^M10|123456789|9-87654^NC\r"
                  + "PV1|||^^|||||||||||||||A|\r"
                  + "ORC|NW|||||||||||||||||US93623038\r"
                  + "OBR||||SPOTCHECK|||20120206174256|||\r"
                  + "OBX||NM|0002-4bb8^SpO2^MDIL||100|0004-0220^%^MDIL|||||F\r"
                  + "OBX||NM|0002-4a06^NBPd^MDIL||69|0004-0f20^mmHg^MDIL|||||F\r";
 	  
    	 
          @SuppressWarnings("resource")
          HapiContext context = new DefaultHapiContext();         
          Parser p = context.getGenericParser();  
          Message hapiMsg = null;
          
          //Usamos el parseador
          try {
			hapiMsg = p.parse(msg);
		} catch (HL7Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          
          //Comprobamos que sea un ORU
          System.out.println(msg.contains("ORU"));
          //Entonces ya podemos parsearlo
          ORU_R01 oruMsg = (ORU_R01) hapiMsg;          
          MessageORU ejemplo = new MessageORU(oruMsg);
          
          
        //Obtenemos datos del paciente                  
          System.out.println("------------------------------------------");
          System.out.println("Nombre: " + ejemplo.getPid().getNombre() );
          System.out.println("Fecha Nacimiento: " + ejemplo.getPid().getNacimiento());
          System.out.println("Sexo: " + ejemplo.getPid().getSexo());
          System.out.println("Direccion: " + ejemplo.getPid().getDireccion() );
          System.out.println("NSS: " + ejemplo.getPid().getNss());
  		
  		//Obtenermos Cabecera MSH

          System.out.println("------------------------------------------");
          System.out.println("Control)	" + ejemplo.getMsh().getControl()  );
          System.out.println("TipoMessage)	" + ejemplo.getMsh().getTipo() );
          System.out.println("Fecha)	"  + ejemplo.getMsh().getFecha() );
          System.out.println("App)	" + ejemplo.getMsh().getAplicacion() );        
          System.out.println("Version ID)	" + ejemplo.getMsh().getVersion() );                    
          
        //Obtenemos OBXs    
          System.out.println("------------------------------------------");
		  for( SegmentOBX obx : ejemplo.getObx()){
			  System.out.println("Ident:   " + obx.getIdTipo());
			  System.out.println("Tipo:   " + obx.getTipo());
			  System.out.println("Valor:   " + obx.getValor());
			  System.out.println("Unid:   " + obx.getUnidad());
	          System.out.println("------------------------------------------");
		  }
 
     }
 
 }
