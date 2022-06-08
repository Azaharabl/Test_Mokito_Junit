package es.dam.services;

import java.util.List;

/**
 * Servicio de Almacenamiento
 * @param <T>
 */
public interface Storage <T>{
    List<T> restore();
    boolean backup(List<T> list);
}
