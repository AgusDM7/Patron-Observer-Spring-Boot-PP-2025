package org.example.patronespp2025;

import org.example.patronespp2025.ObservadoresConcretos.BancoObserver;
import org.example.patronespp2025.ObservadoresConcretos.InversorObserver;
import org.example.patronespp2025.SujetoService.DolarService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Aplicación principal que demuestra el patrón Observer con Spring Boot
 * para monitorear cambios en el valor del dólar argentino
 */
@SpringBootApplication
public class PatronesPp2025Application implements CommandLineRunner {

    private final DolarService dolarService;
    private final BancoObserver bancoObserver;
    private final InversorObserver inversorObserver;

    public PatronesPp2025Application(DolarService dolarService,
                                     BancoObserver bancoObserver,
                                     InversorObserver inversorObserver) {
        this.dolarService = dolarService;
        this.bancoObserver = bancoObserver;
        this.inversorObserver = inversorObserver;
    }

    public static void main(String[] args) {
        SpringApplication.run(PatronesPp2025Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n=== SISTEMA DE MONITOREO DEL DÓLAR ARGENTINO ===");
        System.out.println("Patrón Observer + Spring Events");
        System.out.println("Valor inicial del dólar: $" + dolarService.getValorDolar());

        // Suscribir observers tradicionales
        System.out.println("\n--- Suscribiendo Observers ---");
        dolarService.suscribe(bancoObserver);
        dolarService.suscribe(inversorObserver);

        // Los EventListeners de Spring se suscriben automáticamente

        // Simular cambios en el valor del dólar
        System.out.println("\n--- Simulación de Cambios ---");


        // Suba del dólar
        dolarService.actualizarValorDolar(1150.0);

        // Suba más pronunciada
        dolarService.actualizarValorDolar(1350.0);

        // Baja del dólar
        dolarService.actualizarValorDolar(1200.0);

        // Baja más pronunciada
        dolarService.actualizarValorDolar(950.0);

        // Desuscribir un observer
        System.out.println("\n--- Desuscribiendo Banco Observer ---");
        dolarService.unsuscribe(bancoObserver);

        // Último cambio (solo se notificará al inversor y a los EventListeners)
        dolarService.actualizarValorDolar(1050.0);

        System.out.println("\n=== FIN DE LA SIMULACIÓN ===");
    }
}
