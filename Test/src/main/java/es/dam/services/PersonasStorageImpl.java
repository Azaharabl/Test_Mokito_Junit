package es.dam.services;


import es.dam.models.Persona;

import java.util.List;

/**
 * Implementación del Servicio de almacenamiento de {@link Persona}s.
 */
public class PersonasStorageImpl implements PersonasStorage {


    @Override
    public List<Persona> restore() {
        return List.of(
                new Persona("Ana", 20, "12345678A"),
                new Persona("Juan", 22, "87654321B")
        );
    }

    @Override
    public boolean backup(List<Persona> list) {
        list.forEach(System.out::println);
        return true;
    }

}

