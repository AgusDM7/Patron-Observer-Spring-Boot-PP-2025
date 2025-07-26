package org.example.patronespp2025.interfaces;

/**
 * Interface Subject del patrón Observer
 * Define los métodos para gestionar observadores
 */
public interface Subject {

    /**
     * Suscribir un observador al subject
     */
    void suscribe(Observer observer);

    /**
     * Desuscribir un observador del subject
     */
    void unsuscribe(Observer observer);

    /**
     * Notificar a todos los observadores suscritos
     */
    void notificarObservers();
}