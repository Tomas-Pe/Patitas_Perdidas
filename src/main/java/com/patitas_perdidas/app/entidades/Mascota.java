package com.patitas_perdidas.app.entidades;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
public class Mascota {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	private String nombre;
	private String descripcion;
	private String color;
	private String raza;
	private String tamanio;
	private Boolean encontrado;
	// para pasar de string (lo que toma del html a date
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(iso = ISO.DATE)
	private Date fecha;
	private String especie;
	private Boolean alta;
	@Lob
	@Column(columnDefinition = "MEDIUMBLOB")
	private String image;
	private String zona;

	public Mascota() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getRaza() {
		return raza;
	}

	public void setRaza(String raza) {
		this.raza = raza;
	}

	public String getTamanio() {
		return tamanio;
	}

	public void setTamanio(String tamanio) {
		this.tamanio = tamanio;
	}

	public Boolean getEncontrado() {
		return encontrado;
	}

	public void setEncontrado(Boolean encontrado) {
		this.encontrado = encontrado;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getEspecie() {
		return especie;
	}

	public void setEspecie(String especie) {
		this.especie = especie;
	}

	public Boolean getAlta() {
		return alta;
	}

	public void setAlta(Boolean alta) {
		this.alta = alta;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	@Override
	public int hashCode() {
		return Objects.hash(alta, color, descripcion, encontrado, especie, fecha, id, image, nombre, raza, tamanio,
				zona);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mascota other = (Mascota) obj;
		return Objects.equals(alta, other.alta) && Objects.equals(color, other.color)
				&& Objects.equals(descripcion, other.descripcion) && Objects.equals(encontrado, other.encontrado)
				&& Objects.equals(especie, other.especie) && Objects.equals(fecha, other.fecha)
				&& Objects.equals(id, other.id) && Objects.equals(image, other.image)
				&& Objects.equals(nombre, other.nombre) && Objects.equals(raza, other.raza)
				&& Objects.equals(tamanio, other.tamanio) && Objects.equals(zona, other.zona);
	}

	@Override
	public String toString() {
		return "Mascota [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", color=" + color
				+ ", raza=" + raza + ", tamaño=" + tamanio + ", encontrado=" + encontrado + ", fecha=" + fecha
				+ ", especie=" + especie + ", alta=" + alta + ", image=" + image + ", zona=" + zona + "]";
	}

}
