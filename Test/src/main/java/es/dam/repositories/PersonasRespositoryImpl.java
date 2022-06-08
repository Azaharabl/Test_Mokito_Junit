package es.dam.repositories;

import es.dam.errors.PersonaException;
import es.dam.models.Persona;

import javax.inject.Inject;
import java.util.*;

/**
 * Implementación del Repositorio for {@link Persona}s.
 */
public class PersonasRespositoryImpl implements PersonasRepository {

    @Inject
    public PersonasRespositoryImpl() {

    }

    HashMap<UUID, Persona> personas = new HashMap<>();

    @Override
    public List<Persona> getAll() {
        return new ArrayList<>(personas.values());
    }

    @Override
    public Optional<Persona> getById(UUID uuid) {
        return Optional.ofNullable(personas.get(uuid));
    }

    @Override
    public Persona save(Persona entity) {
        personas.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Persona update(Persona entity) throws PersonaException {
        if (personas.containsKey(entity.getId())) {
            personas.put(entity.getId(), entity);
            return entity;
        } else {
            throw new PersonaException("Persona no encontrada con id: " + entity.getId());
        }
    }

    @Override
    public void delete(Persona entity) throws PersonaException {
        if (personas.containsKey(entity.getId())) {
            personas.remove(entity.getId());
        } else {
            throw new PersonaException("Persona no encontrada con id: " + entity.getId());
        }
    }

    @Override
    public Optional<Persona> delete(UUID id) {
        return Optional.ofNullable(personas.remove(id));
    }
}

