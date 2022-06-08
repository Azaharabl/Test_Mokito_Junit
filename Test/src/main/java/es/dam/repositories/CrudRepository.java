package es.dam.repositories;

import es.dam.errors.PersonaException;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz del patrón CRUD.
 *
 * @param <T>
 * @param <ID>
 */
public interface CrudRepository<T, ID> {
    List<T> getAll();

    Optional<T> getById(ID id);

    T save(T entity);

    T update(T entity) throws PersonaException;

    void delete(T entity) throws PersonaException;
}

