package org.example.patronespp2025.SujetoService;

import org.example.patronespp2025.events.DolarCambioEvent;
import org.example.patronespp2025.interfaces.Observer;
import org.example.patronespp2025.interfaces.Subject;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio que monitorea el valor del dólar
 * Implementa el patrón Observer tradicional y usa eventos de Spring
 */
@Service
public class DolarService implements Subject {

    private List<Observer> observers;
    private double valorDolar;
    private final ApplicationEventPublisher eventPublisher;

    public DolarService(ApplicationEventPublisher eventPublisher) {
        this.observers = new ArrayList<>();
        this.valorDolar = 1000.0; // Valor inicial del dólar
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void suscribe(Observer observer) {
        observers.add(observer);
        System.out.println("Observer suscrito. Total: " + observers.size());
    }

    @Override
    public void unsuscribe(Observer observer) {
        observers.remove(observer);
        System.out.println("Observer desuscrito. Total: " + observers.size());
    }

    @Override
    public void notificarObservers() {
        // Notificar usando patrón Observer tradicional
        for (Observer observer : observers) {
            observer.update();
        }
    }

    /**
     * Actualizar el valor del dólar y notificar cambios
     */
    public void actualizarValorDolar(double nuevoValor) {
        double valorAnterior = this.valorDolar;
        this.valorDolar = nuevoValor;

        // Determinar tipo de movimiento
        String tipoMovimiento = nuevoValor > valorAnterior ? "SUBA" : "BAJA";

        System.out.println("\n=== CAMBIO EN EL DÓLAR ===");
        System.out.println("Valor anterior: $" + valorAnterior);
        System.out.println("Valor nuevo: $" + nuevoValor);
        System.out.println("Movimiento: " + tipoMovimiento);

        // Notificar usando Observer tradicional
        notificarObservers();

        // Publicar evento de Spring
        eventPublisher.publishEvent(new DolarCambioEvent(this, valorAnterior, nuevoValor, tipoMovimiento));
    }

    public double getValorDolar() {
        return valorDolar;
    }
}