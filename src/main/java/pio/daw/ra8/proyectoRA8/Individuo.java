package pio.daw.ra8.proyectoRA8;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
/**
 * Clase para guardar la información de cada individuo
 * No le puse nombre porque opino que es innecesario teniendo el id que se genera sólo.
 * Tiene todos los "getters" y "setters" de todos los campos
 */
@Entity
public class Individuo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long	id;

	@Column(nullable = false)
	private String	nombre;	// No lo veo necesario, con el ID basta

	@Column(nullable = false)
	private BigDecimal	saldoActual;

	@Column(nullable = false)
	private BigDecimal	saldoInicial;

	@ManyToOne
	private Simulacion simulacion;

	public Individuo(String nombre, BigDecimal saldoInicial, Simulacion simulacion)
	{
		this.nombre = nombre;
		this.saldoInicial = saldoInicial;
		this.saldoActual = saldoInicial;
		this.simulacion = simulacion;
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

	public BigDecimal getSaldoActual() {
		return saldoActual;
	}

	public void setSaldoActual(BigDecimal saldoActual) {
		this.saldoActual = saldoActual;
	}

	public BigDecimal getSaldoInicial() {
		return saldoInicial;
	}

	public void setSaldoInicial(BigDecimal saldoInicial) {
		this.saldoInicial = saldoInicial;
	}

	public Simulacion getSimulacion() {
		return simulacion;
	}

	public void setSimulacion(Simulacion simulacion) {
		this.simulacion = simulacion;
	}

}
