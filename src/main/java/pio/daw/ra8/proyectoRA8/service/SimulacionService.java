package pio.daw.ra8.proyectoRA8.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import pio.daw.ra8.proyectoRA8.Individuo;
import pio.daw.ra8.util.JPAUtil;

public class SimulacionService {

	private final String path_bbdd = "BBDD_Proyecto_RA8.odb";

	private List<Individuo> listaIndividuos;

	private final long numIndividuos;
	private final BigDecimal saldoInicial;
	private final long numRondas;

	public SimulacionService(long numIndividuos, BigDecimal saldoInicial, long numRondas) {
		listaIndividuos = new ArrayList<>();
		this.numIndividuos = numIndividuos;
		this.saldoInicial = saldoInicial;
		this.numRondas = numRondas;
	}

	public void empezarSimulacion() {
		generaNumeroIndividuos(numIndividuos, saldoInicial);
		EntityManagerFactory emf = JPAUtil.crearEMF(path_bbdd);
		EntityManager em = emf.createEntityManager();

		Individuo receptor;
		Individuo emisor;
		em.getTransaction().begin();
		for (int ronda = 0; ronda < numRondas; ronda++) {
			// ... lógica de la ronda ...

			em.persist(intercambio);
			if ((ronda + 1) % 100 == 0) {
				em.getTransaction().commit();
				em.clear(); // libera la caché del EntityManager
				em.getTransaction().begin();
			}
		}
		// commit final para las rondas restantes
		if (em.getTransaction().isActive()) {
			em.getTransaction().commit();
		}
	}

	public void generaNumeroIndividuos(long numIndividuos, BigDecimal saldoInicial) {
		String nombre = "nombre"; // Me parece irrelevante el crear un nombre o si quiera crear un id porque JPA
									// lo gestiona con @GeneratedValue, como está en el enunciado, pues dejo el
									// nombre
		for (int i = 0; i < numIndividuos; i++) {
			listaIndividuos.add(new Individuo(nombre, saldoInicial));
		}
	}

	public Individuo elegirIndividuo() {
		int randNum = (int) (Math.random() * (listaIndividuos.size() - 1));
		return (listaIndividuos.get(randNum));
	}
}
