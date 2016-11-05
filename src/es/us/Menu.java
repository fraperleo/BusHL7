package es.us;

import java.sql.SQLException;

import es.us.clases.BusStart;
import es.us.test.ParseMessages;


public class Menu {

	public static void main(String[] args) throws InterruptedException, SQLException {
			
		//Probamos a pasear un mensaje de ejemplo
		//ParseMessages.main(null);
		
		//Iniciamos el servidor y enviamos un mesnaje con HapiTestPanel
		BusStart.inicarServidor();
        
	}

}
