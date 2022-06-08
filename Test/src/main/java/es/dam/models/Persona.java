package es.dam.models;

import java.util.UUID;

/**
 * Persona POJO.
 */
public class Persona {
    private UUID id;
    private String nombre;
    private int edad;
    private String dni;


    public Persona(String nombre, int edad, String dni) {
        this.id = UUID.randomUUID();
        this.nombre = nombre;
        this.edad = edad;
        this.dni = dni;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", dni='" + dni + '\'' +
                '}';
    }
}
