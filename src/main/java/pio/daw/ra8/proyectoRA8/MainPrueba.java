package pio.daw.ra8.proyectoRA8;

import java.math.BigDecimal;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import pio.daw.ra8.proyectoRA8.service.SimulacionService;

public class MainPrueba {
	public static void main(String[] args) {
		//prueba de em ( individuo, intercambio, simulacion, simulacionService)

		EntityManagerFactory	emf = Persistence.createEntityManagerFactory("BBDD_Proyecto_RA8.odb");
		EntityManager			em = emf.createEntityManager();

		long        numIndividuos = 100;
		BigDecimal  saldoInicial = BigDecimal.valueOf(100.00);
		long        numRondas = 10000;


		SimulacionService ss = new SimulacionService(numIndividuos, saldoInicial, numRondas);
		ss.empezarSimulacion();

		Individuo ind1 = em.find(Individuo.class, 1L);
		Individuo ind2 = em.find(Individuo.class, 2L);
		Individuo ind3 = em.find(Individuo.class, 3L);
		Intercambio int1 = em.find(Intercambio.class, 1L);

		System.out.println("ind1: " + ind1.getSaldoActual());
		System.out.println("ind2: " + ind2.getSaldoActual());
		System.out.println("ind3: " + ind3.getSaldoActual());

		System.out.println("\nint1: " + int1.getNumRonda() + " - " + int1.getImporte());
	}
}
