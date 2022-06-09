package es.dam.controllers;

import es.dam.errors.PersonaException;
import es.dam.models.Persona;
import es.dam.repositories.PersonasRepository;
import es.dam.services.PersonasStorage;
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
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
class PersonasControllerTest {

    /**
     * Para hacer test unitarios en el controlador usaremos mokito, ya que si los hicienamos
     * como en el repositorio con Junit estaríamos haciendo pruevas de integración.
     */


    @Mock
    PersonasRepository repo;

    @Mock
    PersonasStorage stor ;

    @InjectMocks
    PersonasController cont ;

    Persona p = new Persona("Azahara", 29, "53717929B");


    @Test
    void getPersonas() {


        Mockito.when(repo.getAll()).thenReturn(List.of(p));

        var result  =cont.getPersonas();
        System.out.println(result);

        Assertions.assertAll(
                ()-> assertEquals(result.size(), 1),
                ()-> assertTrue(result.stream().anyMatch(x->x==p))
        );

        verify(repo, times(1)).getAll();


    }

    @Test
    void getPersona() throws PersonaException {
        when(repo.getById(p.getId())).thenReturn(Optional.of(p));

        var result1  =cont.getPersona(p.getId());

        Assertions.assertAll(
                ()-> assertEquals(result1,p)
        );
        verify(repo, times(1)).getById(p.getId());

    }


    @Test
    void getPersonaNoExiste() {
        when(repo.getById(p.getId())).thenReturn(Optional.empty());

        var result1  =assertThrows(PersonaException.class, ()-> cont.getPersona(p.getId()));

        Assertions.assertAll(
                ()-> assertEquals(result1.getMessage(),"Persona no encontrada con id: " + p.getId())
        );

    }

    @Test
    void savePersona() throws PersonaException {
        when(repo.save(p)).thenReturn(p);

        var result1  =  cont.savePersona(p);

        Assertions.assertAll(
                ()-> assertEquals(result1 ,p )
        );
        verify(repo, times(1)).save(p);

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

        when(repo.update(p)).thenReturn(p);



        var result1  = repo.update(p);

        Assertions.assertAll(
                ()-> assertEquals(result1.getId(),p.getId()),
                ()-> assertEquals(result1.getDni(),nuevoDni),
                ()-> assertEquals(result1.getEdad(),nuevaEdad),
                ()-> assertEquals(result1.getNombre(),nuevoNombre)
        );

        verify(repo, times(1)).update(p);
    }

    @Test
    void updatePersonaNoExiste() throws PersonaException {

        doThrow(new PersonaException("Persona no encontrada con id: " + p.getId())).when(repo).update(p);
        var result = assertThrows(PersonaException.class, ()-> cont.updatePersona(p));
        assertEquals(result.getMessage(),"Persona no encontrada con id: " + p.getId());

        verify(repo, times(1)).update(p);

    }

    @Test
    void deletePersonaNoExiste() throws PersonaException {
        Mockito.doThrow(new PersonaException("Persona no encontrada con id: " + p.getId())).when(repo).delete(p);
        var result1 =assertThrows(PersonaException.class,() -> cont.deletePersona(p));
        Assertions.assertAll(
                ()-> assertEquals(result1.getMessage(),"Persona no encontrada con id: " + p.getId())
        );
        verify(repo, times(1)).delete(p);
    }

    @Test
    void deletePersonaUUID() throws PersonaException {

        when(repo.delete(p.getId())).thenReturn(Optional.of(p));

         var result1 = cont.deletePersona(p.getId());

        Assertions.assertAll(
                ()-> assertEquals(result1,p)
        );

        verify(repo, times(1)).delete(p.getId());

    }

    @Test
    void deletePersonaUUIDNoExiste() {
        when(repo.delete(p.getId())).thenReturn(Optional.empty());

        var result1  =assertThrows(PersonaException.class, ()-> cont.deletePersona(p.getId()));

        Assertions.assertAll(
                ()-> assertEquals(result1.getMessage(),"Persona no encontrada con id: " + p.getId())
        );

        verify(repo, times(1)).delete(p.getId());
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

    @Test
    void backupData() {
        when(stor.backup(List.of(p))).thenReturn(true);
        cont.backupData(List.of(p));
        verify(stor, times(1)).backup(List.of(p));

    }


    /**
     * una opcion que tenemos que conocer es :
     *
     * doNothing().when(repo.delete(p));
     *
     * se utiliza cuando el repositorio , storege, o la clase @Mock , su metodo devuelve un void.
     * no lo he implementado aquí ya que no ha sido necesario.
     */

}