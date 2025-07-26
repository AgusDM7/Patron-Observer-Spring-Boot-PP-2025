package org.example.patronespp2025.interfaces;

/**
 * Interface Observer del patrón Observer
 * Define el método de actualización para los observadores
 */
public interface Observer {

    /**
     * Método llamado cuando el Subject notifica cambios
     */
    void update();
}