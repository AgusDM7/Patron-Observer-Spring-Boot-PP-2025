package org.example.patronespp2025.ObservadoresConcretos;

import org.example.patronespp2025.interfaces.Observer;
import org.example.patronespp2025.SujetoService.DolarService;
import org.springframework.stereotype.Component;

/**
 * Concrete Observer - Inversor que reacciona a cambios del dólar
 */
@Component
public class InversorObserver implements Observer {

    private final String nombreInversor;
    private final DolarService dolarService;
    private double ultimaCompra;

    public InversorObserver(DolarService dolarService) {
        this.nombreInversor = "Inversor Particular";
        this.dolarService = dolarService;
        this.ultimaCompra = 0.0;
    }

    @Override
    public void update() {
        double valorActual = dolarService.getValorDolar();
        System.out.println("\n[INVERSOR] " + nombreInversor + " analizando - Valor: $" + valorActual);

        // Estrategia de inversión simple
        if (ultimaCompra == 0.0) {
            ultimaCompra = valorActual;
            System.out.println("[INVERSOR] Primera compra registrada: $" + valorActual);
        } else {
            double diferencia = valorActual - ultimaCompra;
            double porcentaje = (diferencia / ultimaCompra) * 100;

            if (porcentaje > 5) {
                System.out.println("[INVERSOR] Ganancia del " + String.format("%.1f", porcentaje) + "% - Considerando venta");
            } else if (porcentaje < -5) {
                System.out.println("[INVERSOR] Pérdida del " + String.format("%.1f", Math.abs(porcentaje)) + "% - Oportunidad de compra");
            }
        }
    }
}