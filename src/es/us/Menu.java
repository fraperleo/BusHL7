package es.us;

import es.us.clases.BusStart;
import es.us.clases.ParseMessages;

public class Menu {

	public static void main(String[] args) throws InterruptedException {
			
		//Probamos a pasear un mensaje de ejemplo
		ParseMessages.main(null);
		
		//Iniciamos el servidor y enviamos un mesnaje con HapiTestPanel
		BusStart.inicarServidor();		

	}

}
