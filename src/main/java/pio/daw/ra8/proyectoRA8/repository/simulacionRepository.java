package pio.daw.ra8.proyectoRA8.repository;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.EntityManager;
import pio.daw.ra8.proyectoRA8.Individuo;

/**
 * Clase donde uso consultas JPQL requeridas por el enunciado
 * La clase recibe un EntityManager inicializado en el constructor
 * Tiene varios métodos, varios que son las consultas y otro que las
 * resume y ordena par que salga bonito y separado
 */
public class SimulacionRepository {
	private final EntityManager em;

	public SimulacionRepository(EntityManager em) {
		this.em = em;
	}

	public void resumenConsultas() {
		impIndividuos_OrdenSaldoDescendente();
		System.out.print("\n------------------------------------------\n\n");
		impIndividuo_masRico_masPobre();
		System.out.print("\n------------------------------------------\n\n");
		impSaldoMedioMaxMin();
		System.out.print("\n------------------------------------------\n\n");

	}

	/**
	 * consulta con JPQL para imprimir la lista de los individuos ordenados
	 * por el saldo en orden descendente. Imprime el id y el saldo del individuo.
	 */
	public void impIndividuos_OrdenSaldoDescendente() {
		List<Individuo> listaConsulta = em.createQuery(
				"SELECT i FROM Individuo i ORDER BY i.saldoActual DESC", Individuo.class)
				.getResultList();
		listaConsulta.forEach(p -> System.out.printf("%-5s -> %.2f $%n",
				p.getId(), p.getSaldoActual()));
	}

	/**
	 * Consulta JPQL para imprimir el individuo más rico y el individuo más pobre
	 * con sus respectivos saldos
	 */
	public void impIndividuo_masRico_masPobre() {
		Individuo rico = em.createQuery(
				"SELECT i FROM Individuo i ORDER BY i.saldoActual DESC", Individuo.class)
				.setMaxResults(1)
				.getSingleResult();

		Individuo pobre = em.createQuery(
				"SELECT i FROM Individuo i ORDER BY i.saldoActual ASC", Individuo.class)
				.setMaxResults(1)
				.getSingleResult();

		System.out.printf("Individuo mas rico: %s -> %s%n", rico.getId(), rico.getSaldoActual());
		System.out.printf("Individuo mas pobre: %s -> %s%n", pobre.getId(), pobre.getSaldoActual());
	}

	/**
	 * Consulta JPQL para imprimir los saldos medio, maximo y minimo de todos los
	 * individuos
	 */
	public void impSaldoMedioMaxMin() {
		BigDecimal medio = em.createQuery(
				"SELECT AVG(i.saldoActual) FROM Individuo i", BigDecimal.class)
				.getSingleResult();

		BigDecimal maximo = em.createQuery(
				"SELECT MAX(i.saldoActual) FROM Individuo i", BigDecimal.class)
				.getSingleResult();

		BigDecimal minimo = em.createQuery(
				"SELECT MIN(i.saldoActual) FROM Individuo i", BigDecimal.class)
				.getSingleResult();

		System.out.printf("""
			Saldo medio : %.2f
			Saldo máximo: %s
			Saldo mínimo: %s
				""", medio.doubleValue(), maximo, minimo);
	}

	
}
