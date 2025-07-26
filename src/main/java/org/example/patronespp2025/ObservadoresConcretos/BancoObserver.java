package org.example.patronespp2025.ObservadoresConcretos;

import org.example.patronespp2025.interfaces.Observer;
import org.example.patronespp2025.SujetoService.DolarService;
import org.springframework.stereotype.Component;

/**
 * Concrete Observer - Banco que reacciona a cambios del dólar
 */
@Component
public class BancoObserver implements Observer {

    private final String nombreBanco;
    private final DolarService dolarService;

    public BancoObserver(DolarService dolarService) {
        this.nombreBanco = "Banco Nación";
        this.dolarService = dolarService;
    }

    @Override
    public void update() {
        double valorActual = dolarService.getValorDolar();
        System.out.println("\n[BANCO] " + nombreBanco + " notificado - Nuevo valor: $" + valorActual);

        // Lógica específica del banco
        if (valorActual > 1200) {
            System.out.println("[BANCO] Activando protocolo de alta volatilidad");
        } else if (valorActual < 800) {
            System.out.println("[BANCO] Oportunidad de compra detectada");
        }
    }
}