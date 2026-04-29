package pio.daw.ra8.proyectoRA8;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

/**
 * Clase para guardar la información de la simulación
 * No le puse nombre porque opino que es innecesario teniendo el id que se genera sólo.
 * Tiene todos los "getters" y "setters" de todos los campos
 */
@Entity
public class Simulacion {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long		id;

	@Column(nullable = false)
	private String		nombre;	// No lo veo necesario, con el ID basta, lo puse por el enunciado

	@Column(nullable = false)
	private long		numRondas;

	@Column(nullable = false)
	private long		numIndividuos;

	@Column(nullable = false)
	private BigDecimal	saldoInicial;

	@OneToMany(mappedBy = "simulacion", cascade = CascadeType.ALL)
	private List<Individuo> individuos = new ArrayList<>();

	@OneToMany(mappedBy = "simulacion", cascade = CascadeType.ALL)
	private List<Intercambio> intercambios = new ArrayList<>();

	public Simulacion(
		long numRondas,
		long numIndividuos,
		BigDecimal saldoInicial,
		List<Individuo> individuos
	) {
		this.numRondas = numRondas;
		this.numIndividuos = numIndividuos;
		this.saldoInicial = saldoInicial;
		this. individuos = individuos;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public long getNumRondas() {
		return numRondas;
	}

	public void setNumRondas(long numRondas) {
		this.numRondas = numRondas;
	}

	public long getNumIndividuos() {
		return numIndividuos;
	}

	public void setNumIndividuos(long numIndividuos) {
		this.numIndividuos = numIndividuos;
	}

	public BigDecimal getSaldoInicial() {
		return saldoInicial;
	}

	public void setSaldoInicial(BigDecimal saldoInicial) {
		this.saldoInicial = saldoInicial;
	}

	public List<Individuo> getIndividuos() {
		return individuos;
	}

	public void setIndividuos(List<Individuo> individuos) {
		this.individuos = individuos;
	}

	public List<Intercambio> getIntercambios() {
		return intercambios;
	}

	public void setIntercambios(List<Intercambio> intercambios) {
		this.intercambios = intercambios;
	}


}
