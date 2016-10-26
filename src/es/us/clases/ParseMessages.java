package es.us.clases;

import java.util.List;
import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.Structure;
import ca.uhn.hl7v2.model.v24.group.ORU_R01_OBSERVATION;
import ca.uhn.hl7v2.model.v24.group.ORU_R01_ORDER_OBSERVATION;
import ca.uhn.hl7v2.model.v24.group.ORU_R01_PATIENT;
import ca.uhn.hl7v2.model.v24.group.ORU_R01_PATIENT_RESULT;
import ca.uhn.hl7v2.model.v24.message.ADT_A01;
import ca.uhn.hl7v2.model.v24.message.ORU_R01;
import ca.uhn.hl7v2.model.v24.segment.MSH;
import ca.uhn.hl7v2.model.v24.segment.OBX;
import ca.uhn.hl7v2.model.v24.segment.PID;
import ca.uhn.hl7v2.parser.EncodingNotSupportedException;
import ca.uhn.hl7v2.parser.Parser;



public class ParseMessages {

    
      public static void main(String[] args) {
         
    	  String msg = "MSH|^~\\&|^SureSignsVS||||20120214112334||ORU^R01^ORU_R01|US9362303820120214112334|P|2.4|||||||||2.16.840.1.113883.9.2.1\r"
                  + "PID|||PATID1234^5^M11||JONES^WILLIAM^A^III||19610615|M-||C|1200 N ELM STREET^^GREENSBORO^NC^27401-1020|GL|(919)379-1212|(919)271-3434||S||PATID12345001^2^M10|123456789|9-87654^NC\r"
                  + "PV1|||^^|||||||||||||||A|\r"
                  + "ORC|NW|||||||||||||||||US93623038\r"
                  + "OBR||||SPOTCHECK|||20120206174256|||\r"
                  + "OBX||NM|0002-4bb8^SpO2^MDIL||100|0004-0220^%^MDIL|||||F\r"
                  + "OBX||NM|0002-4a06^NBPd^MDIL||69|0004-0f20^mmHg^MDIL|||||F\r";
  
          
    	  //System.out.println(msg);    	  
    	 
          HapiContext context = new DefaultHapiContext();         
          Parser p = context.getGenericParser();
  
          Message hapiMsg;
          try {
              //Usamos el parseador
              hapiMsg = p.parse(msg);
          } catch (EncodingNotSupportedException e) {
              e.printStackTrace();
              return;
          } catch (HL7Exception e) {
              e.printStackTrace();
              return;
          }
        
 
          System.out.println("------------------------------------------");
          //Comprobamos que sea un ORU
          System.out.println(msg.contains("ORU"));
          //Entonces ya podemos parsearlo
          ORU_R01 oruMsg = (ORU_R01) hapiMsg;
          
       
		  //Obtenermos Cabecera
          MSH msh = oruMsg.getMSH();          
          
          System.out.println("------------------------------------------");
          
          System.out.println("App)	" + msh.getMsh3_SendingApplication().getHd2_UniversalID() );
          System.out.println("Fecha)	"  + msh.getMsh7_DateTimeOfMessage().getTimeOfAnEvent() );
          System.out.println("TipoMessage)	" + msh.getMsh9_MessageType().getMessageType() + "-" + msh.getMsh9_MessageType().getMsg2_TriggerEvent() );
          System.out.println("Control)	" + msh.getMsh10_MessageControlID().getValue() );
          System.out.println("Version ID)	" + msh.getMsh12_VersionID().getVersionID() );
          
          System.out.println("------------------------------------------");
          
               
          //Obtenemos datos del paciente                  
                  
          PID x = oruMsg.getPATIENT_RESULT().getPATIENT().getPID();
         
          System.out.println(x.getPid3_PatientIdentifierList(0).getID());         
          
          System.out.println("------------------------------------------");  
                   
          System.out.println("Fecha OBR: " + oruMsg.getPATIENT_RESULT().getORDER_OBSERVATION().getOBR().getObr7_ObservationDateTime().getTimeOfAnEvent());  //Hora de observación 
          
            
          //Obtenemos OBXs    
         
           try {
			List<ORU_R01_OBSERVATION> orderObsList = oruMsg.getPATIENT_RESULT().getORDER_OBSERVATION().getOBSERVATIONAll();
			  System.out.println("Num de OBXs: 	" + orderObsList.size()); //Numero de OBX
			  System.out.println("------------------------------------------");		//Imprimimos datos de cada OBX
			  
			  for( ORU_R01_OBSERVATION s : orderObsList){
				  OBX obx0 = s.getOBX();
				  System.out.println("Ident:   " + obx0.getObx3_ObservationIdentifier().getCe1_Identifier());
		          System.out.println("Fecha:   " + obx0.getObx14_DateTimeOfTheObservation().getTimeOfAnEvent());	
		          System.out.println("Tipo:    " + obx0.getObx3_ObservationIdentifier().getCe2_Text());
		          System.out.println("Valor:   " + obx0.getObx5_ObservationValue(0).getData().toString());
		          System.out.println("Unid:    " + obx0.getObx6_Units().getCe2_Text());
		          System.out.println("------------------------------------------");
			  }
			  
		} catch (HL7Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
 
     }
 
 }
