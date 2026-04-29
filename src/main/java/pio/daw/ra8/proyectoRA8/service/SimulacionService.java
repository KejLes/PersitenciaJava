package pio.daw.ra8.proyectoRA8.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import pio.daw.ra8.proyectoRA8.Individuo;
import pio.daw.ra8.proyectoRA8.Intercambio;
import pio.daw.ra8.proyectoRA8.Simulacion;
import pio.daw.ra8.util.JPAUtil;

public class SimulacionService {

	private final String path_bbdd = "BBDD_Proyecto_RA8.odb";

	private List<Individuo> listaIndividuos;

	private final long numIndividuos;
	private final BigDecimal saldoInicial;
	private final long numRondas;

	private Simulacion simulacion;

	public SimulacionService(long numIndividuos, BigDecimal saldoInicial, long numRondas) {
		listaIndividuos = new ArrayList<>();
		this.numIndividuos = numIndividuos;
		this.saldoInicial = saldoInicial;
		this.numRondas = numRondas;
	}

	public void empezarSimulacion() {
		generaNumeroIndividuos(numIndividuos, saldoInicial);
		simulacion = new Simulacion(numRondas, numIndividuos, saldoInicial, listaIndividuos);
		EntityManagerFactory emf = JPAUtil.crearEMF(path_bbdd);
		EntityManager em = emf.createEntityManager();

		Individuo	emisor;
		Individuo	receptor;
		Intercambio	intercambio;

		BigDecimal	importe;

		em.getTransaction().begin();
		for (int ronda = 0; ronda < numRondas; ronda++) {
			// ... lógica de la ronda ...
			emisor = elegirIndividuo();
			receptor = elegirIndividuo();

			//	Conocer el importe random poniendo como tope el saldo del emisor (y redondeando a dos decimales)
			importe = new BigDecimal(Math.random())
				.multiply(emisor.getSaldoActual())
				.setScale(2, RoundingMode.HALF_UP);

			// Establecer el intercambio entre individuos
			emisor.setSaldoActual(emisor.getSaldoActual().subtract(importe));
			receptor.setSaldoActual(receptor.getSaldoActual().add(importe));

			em.persist(intercambio);
			if ((ronda + 1) % 100 == 0) {
				em.getTransaction().commit();
				em.clear(); // libera la caché del EntityManager
				em.getTransaction().begin();
			}
		}
		// commit para las rondas restantes
		if (em.getTransaction().isActive()) {
			em.getTransaction().commit();
		}
		//commit final para el objeto simulacion
		em.getTransaction().begin();
		em.persist(simulacion);
		em.getTransaction().commit();
	}

	public void generaNumeroIndividuos(long numIndividuos, BigDecimal saldoInicial) {
		for (int i = 0; i < numIndividuos; i++) {
			listaIndividuos.add(new Individuo(
				"nombre",
				this.saldoInicial,
				this.simulacion
			));
		}
	}

	public Individuo elegirIndividuo() {
		int randNum = (int) (Math.random() * (listaIndividuos.size() - 1));
		return (listaIndividuos.get(randNum));
	}
}
