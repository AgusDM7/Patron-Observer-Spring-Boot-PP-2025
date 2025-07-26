package org.example.patronespp2025.ObservadoresSpring;

import org.example.patronespp2025.events.DolarCambioEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * EventListener de Spring - Casa de cambio que escucha eventos del dólar
 */
@Component
public class CasaCambioEventListener {

    private final String nombreCasa = "Casa de Cambio Central";

    /**
     * Escucha eventos de cambio del dólar usando @EventListener
     */
    @EventListener
    public void onDolarCambio(DolarCambioEvent event) {
        System.out.println("\n[CASA CAMBIO] " + nombreCasa + " recibió evento:");
        System.out.println("  - Cambió de $" + event.getValorAnterior() + " a $" + event.getValorNuevo());
        System.out.println("  - Movimiento: " + event.getTipoMovimiento());

        // Lógica específica de casa de cambio
        double diferencia = Math.abs(event.getValorNuevo() - event.getValorAnterior());
        if (diferencia > 50) {
            System.out.println("[CASA CAMBIO] Alerta: Cambio significativo detectado - Ajustando comisiones");
        }

        if (event.getTipoMovimiento().equals("SUBA")) {
            System.out.println("[CASA CAMBIO] Incrementando spread de venta");
        } else {
            System.out.println("[CASA CAMBIO] Reduciendo spread de compra");
        }
    }
}