package es.dam.controllers;

import es.dam.controllers.PersonasController;
import es.dam.errors.PersonaException;
import es.dam.models.Persona;
import es.dam.repositories.PersonasRepository;
import es.dam.repositories.PersonasRespositoryImpl;
import es.dam.services.PersonasStorage;
import es.dam.services.PersonasStorageImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
class PersonasControllerTest {

    /**
     * Para hacer test unitarios en el controlador usaremos mokito, ya que si los hicienamos
     * como en el repositorio con Junit estaríamos haciendo pruevas de integración.
     */

    @Mock
    PersonasRepository personasRepository ;

    @Mock
    PersonasStorage stor ;

    @InjectMocks
    PersonasController cont ;

    Persona p = new Persona("Azahara", 29, "53717929B");


    @Test
    void getPersonas() {


        Mockito.when(personasRepository.getAll()).thenReturn(List.of(p));

        var result  =cont.getPersonas();
        System.out.println(result);

        Assertions.assertAll(
                ()-> assertEquals(result.size(), 1),
                ()-> assertTrue(result.stream().anyMatch(x->x==p))
        );

        verify(personasRepository, times(1)).getAll();


    }

    @Test
    void getPersona() throws PersonaException {
        when(personasRepository.getById(p.getId())).thenReturn(Optional.of(p));

        var result1  =cont.getPersona(p.getId());

        Assertions.assertAll(
                ()-> assertEquals(result1,p)
        );
        verify(personasRepository, times(1)).getById(p.getId());

    }


    @Test
    void getPersonaNoExiste() {
        when(personasRepository.getById(p.getId())).thenReturn(Optional.empty());

        var result1  =assertThrows(PersonaException.class, ()-> cont.getPersona(p.getId()));

        Assertions.assertAll(
                ()-> assertEquals(result1.getMessage(),"Persona no encontrada con id: " + p.getId())
        );

    }

    @Test
    void savePersona() throws PersonaException {
        when(personasRepository.save(p)).thenReturn(p);

        var result1  =  cont.savePersona(p);

        Assertions.assertAll(
                ()-> assertEquals(result1 ,p )
        );
        verify(personasRepository, times(1)).save(p);

    }

    @Test
    void updatePersona() throws PersonaException {

        //cambiamos datos de p
        var nuevaEdad = 30;
        var nuevoNombre = "Rosa";
        var nuevoDni = "55555555A";
        p.setDni(nuevoDni);
        p.setNombre(nuevoNombre);
        p.setEdad(nuevaEdad);

        when(personasRepository.update(p)).thenReturn(p);



        var result1  = personasRepository.update(p);

        Assertions.assertAll(
                ()-> assertEquals(result1.getId(),p.getId()),
                ()-> assertEquals(result1.getDni(),nuevoDni),
                ()-> assertEquals(result1.getEdad(),nuevaEdad),
                ()-> assertEquals(result1.getNombre(),nuevoNombre)
        );

        verify(personasRepository, times(1)).update(p);
    }


    //no terminado
    @Test
    void updatePersonaNoExiste() throws PersonaException {


    }

    //no terminado
    @Test
    void deletePersonaNoExiste() throws PersonaException {


    }

    @Test
    void deletePersonaUUID() throws PersonaException {

        when(personasRepository.delete(p.getId())).thenReturn(Optional.of(p));

         var result1 = cont.deletePersona(p.getId());

        Assertions.assertAll(
                ()-> assertEquals(result1,p)
        );

        verify(personasRepository, times(1)).delete(p.getId());

    }

    @Test
    void deletePersonaUUIDNoExiste() {
        when(personasRepository.delete(p.getId())).thenReturn(Optional.empty());

        var result1  =assertThrows(PersonaException.class, ()-> cont.deletePersona(p.getId()));

        Assertions.assertAll(
                ()-> assertEquals(result1.getMessage(),"Persona no encontrada con id: " + p.getId())
        );

        verify(personasRepository, times(1)).delete(p.getId());
    }


    @Test
    void restoreData() {
        when(stor.restore()).thenReturn(List.of(p));

        var result1  =cont.restoreData();

        Assertions.assertAll(
                ()-> assertEquals(result1.size(),1),
                ()-> assertEquals(result1.get(0),p)
        );

        verify(stor, times(1)).restore();


    }

    //no terminado
    @Test
    void backupData() {

    }
}