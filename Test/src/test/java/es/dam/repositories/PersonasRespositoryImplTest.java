package es.dam.repositories;

import es.dam.errors.PersonaException;
import es.dam.models.Persona;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;


import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


class PersonasRespositoryImplTest {

    PersonasRespositoryImpl repository = new PersonasRespositoryImpl();

    Persona p = new Persona("Azahara", 29, "53717929A");




    @Test
    void getAll() {

        repository.save(p);
        var result2  =repository.getAll();

        Assertions.assertAll(
                ()-> assertFalse(result2.size()==0),
                ()-> assertTrue(result2.stream().anyMatch(x->x==p))
        );

    }

    @Test
    void getById() throws PersonaException {
        repository.save(p);
        var result1  =repository.getById(p.getId());

        Assertions.assertAll(
                ()-> assertFalse(result1.isEmpty()),
                ()-> assertEquals(result1.get().getId(),p.getId() )
        );

        //para limpiar repositorio
        repository.delete(p);
    }

    @Test
    void getByIdNoExiste() {


        var result1  = repository.getById(UUID.randomUUID());

        Assertions.assertAll(
                ()-> assertTrue(result1.isEmpty())
        );


    }

    @Test
    void save() throws PersonaException {
        repository.save(p);

        var result1  =repository.getById(p.getId());


        Assertions.assertAll(
                ()-> assertEquals(result1.get() ,p )
        );

        //para limpiar repositorio
        repository.delete(p);
    }

    @Test
    void update() throws PersonaException {
        repository.save(p);

        //cambiamos datos de p
        var nuevaEdad = 30;
        var nuevoNombre = "Rosa";
        var nuevoDni = "55555555A";
        p.setDni(nuevoDni);
        p.setNombre(nuevoNombre);
        p.setEdad(nuevaEdad);

        repository.update(p);
        var result1  =repository.getById(p.getId());

        Assertions.assertAll(
                ()-> assertFalse(result1.isEmpty()),
                ()-> assertEquals(result1.get().getId(),p.getId()),
                ()-> assertEquals(result1.get().getDni(),nuevoDni),
                ()-> assertEquals(result1.get().getEdad(),nuevaEdad),
                ()-> assertEquals(result1.get().getNombre(),nuevoNombre)
        );

        //para limpiar repositorio
        repository.delete(p);
    }

    @Test
    void updateNoExiste() {

        repository.delete(p.getId());
        var result1  = assertThrows(PersonaException.class, ()->repository.update(p));

        Assertions.assertAll(

                ()-> assertEquals(result1.getMessage(), "Persona no encontrada con id: " +p.getId())

        );

    }

    @Test
    void delete() throws PersonaException {
        repository.save(p);
        repository.delete(p);

        var result1  =repository.getById(p.getId());
        var result2 =repository.getAll();

        Assertions.assertAll(
                ()-> assertTrue(result1.isEmpty()),
                ()-> assertFalse(result2.stream().anyMatch(x->x==p))

        );
    }

    @Test
    void deleteNoExiste() throws PersonaException {

        Persona persona = new Persona("Maria", 23, "45445454A");

        var result1  =assertThrows(PersonaException.class,
                ()->repository.delete(persona));


        Assertions.assertAll(
                ()-> assertEquals(result1.getMessage(), "Persona no encontrada con id: " + persona.getId() )

        );
        //para limpiar repositorio


    }

    @Test
    void testDeleteByUuid() {
        repository.save(p);
        repository.delete(p.getId());

        var result1  =repository.getById(p.getId());
        var result2 =repository.getAll();

        Assertions.assertAll(
                ()-> assertTrue(result1.isEmpty()),
                ()-> assertFalse(result2.stream().anyMatch(x->x==p))

        );

    }


}