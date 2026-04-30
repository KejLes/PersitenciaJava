package pio.daw.ra8.proyectoRA8;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

/**
 * Clase para guardar la información de los intercambios entre los individuos
 * Tiene todos los "getters" y "setters" de todos los campos. Además de toString.
 */
@Entity
public class Intercambio {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long		id;

	@Column(nullable = false)
	private long		numRonda;

	@Column(nullable = false)
	private BigDecimal	importe;

	@Column(nullable = false)
	@ManyToOne
	private Individuo emisor;

	@Column(nullable = false)
	@ManyToOne
	private Individuo receptor;

	public Intercambio(long numRonda, BigDecimal importe, Individuo emisor, Individuo receptor)
	{
		this.numRonda = numRonda;
		this.importe = importe;
		this.emisor = emisor;
		this.receptor = receptor;
	}

	@Override
	public String toString() {
		return (
			"id: " + this.id +
			"\nnombre: " + this.numRonda +
			"\nimporte del intercambio: " + this.importe +
			"\nindividuo emisor del intercambio: " + this.emisor +
			"\nindividuo receptor del intercambio: " + this.receptor
		);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getNumRonda() {
		return numRonda;
	}

	public void setNumRonda(long numRonda) {
		this.numRonda = numRonda;
	}

	public BigDecimal getImporte() {
		return importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	public Individuo getEmisor() {
		return emisor;
	}

	public void setEmisor(Individuo emisor) {
		this.emisor = emisor;
	}

	public Individuo getReceptor() {
		return receptor;
	}

	public void setReceptor(Individuo receptor) {
		this.receptor = receptor;
	}

}
