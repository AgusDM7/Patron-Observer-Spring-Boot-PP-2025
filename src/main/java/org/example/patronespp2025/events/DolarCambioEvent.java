package org.example.patronespp2025.events;


import org.springframework.context.ApplicationEvent;

/**
 * Evento de Spring para notificar cambios en el dólar
 */
public class DolarCambioEvent extends ApplicationEvent {

    private final double valorAnterior;
    private final double valorNuevo;
    private final String tipoMovimiento; // "SUBA" o "BAJA"

    public DolarCambioEvent(Object source, double valorAnterior, double valorNuevo, String tipoMovimiento) {
        super(source);
        this.valorAnterior = valorAnterior;
        this.valorNuevo = valorNuevo;
        this.tipoMovimiento = tipoMovimiento;
    }

    public double getValorAnterior() {
        return valorAnterior;
    }

    public double getValorNuevo() {
        return valorNuevo;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }
}