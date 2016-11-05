package es.us.clases;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.app.Connection;
import ca.uhn.hl7v2.app.ConnectionListener;
import ca.uhn.hl7v2.app.HL7Service;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.v24.message.ORU_R01;
import ca.uhn.hl7v2.parser.Parser;
import ca.uhn.hl7v2.protocol.ReceivingApplication;
import ca.uhn.hl7v2.protocol.ReceivingApplicationException;
import ca.uhn.hl7v2.protocol.ReceivingApplicationExceptionHandler;
import es.us.data.MessageORU;


public class BusStart {
	

	 public static void inicarServidor() throws InterruptedException {
		 			 
		 	/*Vamos a crear un servidor que escuche los mensajes entrantes. Para ello tenemos que seguir los siguientes pasos; definir puertos, y tratar los handlers*/

			int port = 1011; //Indicamos el puerto
	        boolean useTls = false; // No utilizamos certificado
	        @SuppressWarnings("resource")
			HapiContext context = new DefaultHapiContext();
	        HL7Service server = context.newServer(port, useTls);
	  
	        /*El servidor debe tener tantos objetos registrados, en forma de handler, como mensages tipo vayamos a procesar*/

	        ReceivingApplication handler = new ExampleReceiverApplication();  //Creamos el handler
	        //server.registerApplication("ADT", "A01", handler);
	        //server.registerApplication("*", "*", handler);
	        server.registerApplication("ORU", "R01", handler);  //Solo procesaremos ORU-R01 (con más dentro)
	          
	        //A continuación
	        server.registerConnectionListener(new MyConnectionListener());
	        server.setExceptionHandler(new MyExceptionHandler());
	        
	        //Hacemos que el servidor escuche
	        //server.start(); //In background
		    server.startAndWait();	       

	       // Stop server
		    //server.stopAndWait();  //Not in our case	 		 
	    }
	 
	 
	 	 
	 
	  /** Modificamos el listener que nos informa de los datos **/

		public static class MyConnectionListener implements ConnectionListener {

	       public void connectionReceived(Connection theC) {
	          System.out.println("Nueva conexión establecida desde: " + theC.getRemoteAddress().toString());
	       }
	 
	       public void connectionDiscarded(Connection theC) {
	          System.out.println("Conexión perdidad desde: " + theC.getRemoteAddress().toString());
	       }
	       	 
	    }		
		
	    /** Procesar mensajes **/   
	    
	    public static class ExampleReceiverApplication implements ReceivingApplication {
	      
	          
	          public boolean canProcess(Message theIn) {
	              return true;
	          }
	          
	        
	      	@SuppressWarnings("resource")
			public Message processMessage(Message theMessage, Map<String, Object> theMetadata) throws ReceivingApplicationException, HL7Exception {

				String encodedMessage = new DefaultHapiContext().getPipeParser().encode(theMessage);
				System.out.println("Received message:\n" + encodedMessage + "\n");
	
				// TRATAMIENTO DEL MENSAJE AQUÍ
				// ParseMessage & inside, insert into database
				HapiContext context = new DefaultHapiContext();
				Parser p = context.getGenericParser();
				Message hapiMsg = null;
				// Usamos el parseador
				try {
					hapiMsg = p.parse(encodedMessage);
				} catch (HL7Exception e) {
					System.err.println("No se ha podido parsear el mensaje: "
							+ e.getMessage());
				}
	
				// Comprobamos que sea un ORU_R01
				if (encodedMessage.contains("ORU_R01")) {
					// Entonces ya podemos parsearlo con su paseador propio
					ORU_R01 oruMsg = (ORU_R01) hapiMsg;
					try {
						Persist.escribeEnBD(new MessageORU(oruMsg)); //Llamamos al método para que lo introduzca en la base de datos
						System.out.println("Se ha introducido correctamente el mensaje \n");
					} catch (NumberFormatException | SQLException e) {
						System.err.println("No se ha podido introducir en la base de datos el mensaje: "
								+ e.getMessage());
					}
				}
	
				// Enviar el ACK generado automáticamente
				try {
					return theMessage.generateACK();
				} catch (IOException e) {
					throw new HL7Exception(e);
				}

	      	}
	      
      }
	    
	    	    
	     
	    /** Configurar o modificar el manejo de los mensajes **/
		
	    public static class MyExceptionHandler implements ReceivingApplicationExceptionHandler {
	 
	       /**
	        * Process an exception.
	        * 
	        * @param theIncomingMessage
	        *            the incoming message. This is the raw message which was
	        *            received from the external system
	        * @param theIncomingMetadata
	        *            Any metadata that accompanies the incoming message. See {@link ca.uhn.hl7v2.protocol.Transportable#getMetadata()}
	        * @param theOutgoingMessage
	        *            the outgoing message. The response NAK message generated by
	        *            HAPI.
	        * @param theE
	        *            the exception which was received
	        * @return The new outgoing message. This can be set to the value provided
	        *         by HAPI in <code>outgoingMessage</code>, or may be replaced with
	        *         another message. <b>This method may not return <code>null</code></b>.
	        */

	    	public String processException(String theIncomingMessage, Map<String, Object> theIncomingMetadata, String theOutgoingMessage, Exception theE) throws HL7Exception {
		
	           //Modificcar el NACK que envía automáticamente
	          
	    		return theOutgoingMessage;
	       }

	    }

	 
	 
	 
	 
	 

}
