package es.us.data;

import java.util.ArrayList;
import java.util.List;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.v24.group.ORU_R01_OBSERVATION;
import ca.uhn.hl7v2.model.v24.message.ORU_R01;
import ca.uhn.hl7v2.model.v24.segment.MSH;
import ca.uhn.hl7v2.model.v24.segment.OBX;
import ca.uhn.hl7v2.model.v24.segment.PID;

/* http://hl7-definition.caristix.com:9010/Default.aspx?version=HL7%20v2.5.1&triggerEvent=ORU_R01 */

public class MessageORU {

	private SegmentPID pid;
	private SegmentMSH msh;
	private List<SegmentOBX> obx;

	public MessageORU(ORU_R01 oruMsg) {// Contruimos mensaje
		
		//Creamos datos del paciente                  
        
        PID x = oruMsg.getPATIENT_RESULT().getPATIENT().getPID();        
        this.pid = new SegmentPID(x.getPid5_PatientName(0).getFamilyName().getFn1_Surname() + " " + x.getPid5_PatientName(0).getGivenName(), x.getPid7_DateTimeOfBirth().getTs1_TimeOfAnEvent().toString(), x.getPid8_AdministrativeSex().getValue(), x.getPid11_PatientAddress(0).getXad1_StreetAddress().getSad1_StreetOrMailingAddress() + " - " + x.getPid11_PatientAddress(0).getXad3_City(), x.getPid19_SSNNumberPatient().getValue());
		
		//Creamos cabecera MSH
        MSH msh = oruMsg.getMSH();          
        this.msh = new SegmentMSH(msh.getMsh10_MessageControlID().getValue(), msh.getMsh9_MessageType().getMessageType() + "-" + msh.getMsh9_MessageType().getMsg2_TriggerEvent(), msh.getMsh7_DateTimeOfMessage().getTimeOfAnEvent().toString(), msh.getMsh3_SendingApplication().getHd2_UniversalID().toString(), msh.getMsh12_VersionID().getVersionID().toString());    
        
      //Creamos los OBXs    
        this.obx = new ArrayList<SegmentOBX>();
		List<ORU_R01_OBSERVATION> orderObsList;
		try {
			orderObsList = oruMsg.getPATIENT_RESULT().getORDER_OBSERVATION().getOBSERVATIONAll();
			//System.out.println("Num de OBXs: 	" + orderObsList.size()); //Numero de OBX
			 for( ORU_R01_OBSERVATION s : orderObsList){
				  OBX obx0 = s.getOBX();
				  this.obx.add(new SegmentOBX(obx0.getObx3_ObservationIdentifier().getCe1_Identifier().toString(), obx0.getObx3_ObservationIdentifier().getCe2_Text().toString(), obx0.getObx5_ObservationValue(0).getData().toString(), obx0.getObx6_Units().getCe2_Text().toString()));
			  }
		} catch (HL7Exception e) {
			System.err.println("No venían OBX asociados al mensaje ORU");
		}
		
	}
	
	
	public SegmentPID getPid() {
		return pid;
	}

	public SegmentMSH getMsh() {
		return msh;
	}

	public List<SegmentOBX> getObx() {
		return obx;
	}

}
