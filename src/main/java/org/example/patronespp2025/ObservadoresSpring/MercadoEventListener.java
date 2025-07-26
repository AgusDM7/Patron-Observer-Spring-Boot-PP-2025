package org.example.patronespp2025.ObservadoresSpring;

import org.example.patronespp2025.events.DolarCambioEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * EventListener de Spring - Mercado financiero que escucha eventos del dólar
 */
@Component
public class MercadoEventListener {

    private final String nombreMercado = "Mercado Abierto Electrónico";

    /**
     * Escucha eventos de cambio del dólar usando @EventListener
     */
    @EventListener
    public void onDolarCambio(DolarCambioEvent event) {
        System.out.println("\n[MERCADO] " + nombreMercado + " procesando evento:");

        double variacionPorcentual = ((event.getValorNuevo() - event.getValorAnterior()) / event.getValorAnterior()) * 100;

        System.out.println("  - Variación: " + String.format("%.2f", variacionPorcentual) + "%");

        // Análisis de mercado
        if (Math.abs(variacionPorcentual) > 3) {
            System.out.println("[MERCADO] Alta volatilidad detectada - Suspendiendo operaciones por 5 minutos");
        } else if (variacionPorcentual > 1) {
            System.out.println("[MERCADO] Tendencia alcista - Incrementando volumen de operaciones");
        } else if (variacionPorcentual < -1) {
            System.out.println("[MERCADO] Tendencia bajista - Activando mecanismos de contención");
        } else {
            System.out.println("[MERCADO] Variación normal - Operaciones continúan");
        }
    }
}