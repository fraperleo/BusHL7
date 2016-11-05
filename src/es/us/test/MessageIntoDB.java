package es.us.test;

import java.sql.SQLException;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.v24.message.ORU_R01;
import ca.uhn.hl7v2.parser.Parser;
import es.us.clases.ConexionBD;
import es.us.clases.Persist;
import es.us.data.MessageORU;
import es.us.data.SegmentOBX;

public class MessageIntoDB {

    
      public static void main(String[] args) throws NumberFormatException, SQLException {
         
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
          
          //Persist.escribeEnBD(ejemplo);
          ConexionBD.conectar();
 
     }
 
 }
