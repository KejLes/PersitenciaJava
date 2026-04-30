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
	private EntityManagerFactory emf;
	private EntityManager em;

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
		
		emf = JPAUtil.crearEMF(path_bbdd);
		em = emf.createEntityManager();
	}
	
	public void empezarSimulacion() {
		Individuo	emisor;
		Individuo	receptor;
		Intercambio	intercambio;
		BigDecimal	importe;
		
		em.getTransaction().begin();
		simulacion = new Simulacion(numRondas, numIndividuos, saldoInicial);
		generaNumeroIndividuos(numIndividuos, saldoInicial);
		simulacion.setIndividuos(listaIndividuos);
		em.persist(simulacion);
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

			//Crear la información del intercambio
			intercambio = new Intercambio(ronda, importe, emisor, receptor);

			// Cada 100 rondas se guardan los cambios
			em.persist(intercambio);
			if ((ronda + 1) % 100 == 0) {
				em.getTransaction().commit();
				em.getTransaction().begin();
			}
		}
		// commit para las rondas restantes
		if (em.getTransaction().isActive()) {
			em.getTransaction().commit();
		}
	}

	public void generaNumeroIndividuos(long numIndividuos, BigDecimal saldoInicial) {
		Individuo individuo;
		for (int i = 0; i < numIndividuos; i++) {
			individuo = new Individuo(
				"nombre",
				this.saldoInicial,
				this.simulacion);
			em.persist(individuo);
			listaIndividuos.add(individuo);
		}
	}

	public Individuo elegirIndividuo() {
		int randNum = (int) (Math.random() * (this.listaIndividuos.size() - 1));
		return (listaIndividuos.get(randNum));
	}
}
