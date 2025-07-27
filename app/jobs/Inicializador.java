
  package jobs;

import models.Consulta;
import play.jobs.Job;
import play.jobs.OnApplicationStart;

@OnApplicationStart
public class Inicializador extends Job{

	public void doJob() {
		if (Consulta.count() == 0) {
			Consulta consulta1 = new Consulta("Pediatria", "Maria Clara Teixeira", "01 de agosto de 2025, às 10:00h" );
			Consulta consulta2 = new Consulta("Pediatria", "Maria Clara Teixeira", "01 de agosto de 2025, às 11:00h" );
			Consulta consulta3 = new Consulta("Pediatria", "Maria Clara Teixeira", "01 de agosto de 2025, às 12:00h" );
			Consulta consulta4 = new Consulta("Pediatria", "Maria Clara Teixeira", "01 de agosto de 2025, às 13:00h" );
			consulta1.save();
			consulta2.save();
			consulta3.save();
			consulta4.save();
			
			Consulta consulta5 = new Consulta("Dermatologia", "Manuella Regina", "02 de agosto de 2025, às 08:00h" );
			Consulta consulta6 = new Consulta("Dermatologia", "Manuella Regina", "02 de agosto de 2025, às 09:00h" );
			Consulta consulta7 = new Consulta("Dermatologia", "Manuella Regina", "02 de agosto de 2025, às 10:00h" );
			Consulta consulta8 = new Consulta("Dermatologia", "Manuella Regina", "02 de agosto de 2025, às 11:00h" );
			consulta5.save();
			consulta6.save();
			consulta7.save();
			consulta8.save();
			
			Consulta consulta9 = new Consulta("Oncologia", "Nayara Oliveira", "03 de agosto de 2025, às 14:00h" );
			Consulta consulta10 = new Consulta("Oncologia", "Nayara Oliveira", "03 de agosto de 2025, às 15:00h" );
			Consulta consulta11 = new Consulta("Oncologia", "Nayara Oliveira", "03 de agosto de 2025, às 16:00h" );
			Consulta consulta12 = new Consulta("Oncologia", "Nayara Oliveira", "03 de agosto de 2025, às 17:00h" );
			consulta9.save();
			consulta10.save();
			consulta11.save();
			consulta12.save();
		}
		

	}
}
