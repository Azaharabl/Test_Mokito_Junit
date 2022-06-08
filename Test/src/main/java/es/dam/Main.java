package es.dam;

import es.dam.controllers.PersonasController;
import es.dam.errors.PersonaException;
import es.dam.models.Persona;
import es.dam.repositories.PersonasRespositoryImpl;
import es.dam.services.PersonasStorageImpl;

import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        System.out.println("Entornos de Desarrollo");
        System.out.println("=====================");
        info();


    }

    private static void info() {
        // Creamos el controlador, debes modificarlo para trabajar correctamente con las dependencias
        PersonasController personasController = new PersonasController(
                new PersonasRespositoryImpl(),
                new PersonasStorageImpl()
        );

        // Recuperamos los datos
        System.out.println("Recuperando datos");
        var list = personasController.restoreData();

        // insertamos los datos
        System.out.println("Insertando datos");
        for (Persona persona1 : list) {
            try {
                personasController.savePersona(persona1);
            } catch (PersonaException e) {
                System.err.println(e.getMessage());
            }
        }

        // obtenemos todas las personas
        System.out.println("Obteniendo todas las personas");
        var personas = personasController.getPersonas();
        personas.forEach(System.out::println);

        // añadimos una persona
        System.out.println("Añadiendo una persona");
        Persona persona = null;
        try {
            persona = personasController.savePersona(new Persona("Examen", 25, "12345678A"));
            System.out.println(persona);
        } catch (PersonaException e) {
            System.err.println(e.getMessage());
        }

        // obtenemos una persona
        System.out.println("Obteniendo una persona");
        try {
            persona = personasController.getPersona(personas.get(0).getId());
            System.out.println(persona);
        } catch (PersonaException e) {
            System.err.println(e.getMessage());
        }

        // modificamos una persona
        System.out.println("Modificando una persona");
        try {
            persona.setEdad(28);
            persona = personasController.updatePersona(persona);
            System.out.println(persona);
        } catch (PersonaException e) {
            System.err.println(e.getMessage());
        }

        System.out.println("Modificando una persona con edad incorrecta");
        try {
            persona.setEdad(8);
            persona = personasController.updatePersona(persona);
            System.out.println(persona);
        } catch (PersonaException e) {
            System.err.println(e.getMessage());
        }


        System.out.println("Modificando una persona con DNI incorrecto");
        try {
            persona.setEdad(29);
            persona.setDni("12345678");
            persona = personasController.updatePersona(persona);
            System.out.println(persona);
        } catch (PersonaException e) {
            System.err.println(e.getMessage());
        }

        // borramos una persona
        System.out.println("Borrando una persona");
        try {
            persona = personasController.deletePersona(persona.getId());
            System.out.println(persona);
        } catch (PersonaException e) {
            System.err.println(e.getMessage());
        }

        // borramos una persona
        System.out.println("Borrando una persona que no existe");
        try {
            persona = personasController.deletePersona(UUID.randomUUID());
            System.out.println(persona);
        } catch (PersonaException e) {
            System.err.println(e.getMessage());
        }

        // Hacemos un backup
        System.out.println("Haciendo un backup");
        personasController.backupData(personasController.getPersonas());
    }
}