# Patron-Observer-Spring-Boot-PP-2025

> Proyecto desarrollado para las **Prácticas Profesionales 2025** de la **Universidad Tecnológica Nacional (UTN)**.  
> Implementa el **Patrón de Diseño Observer** utilizando **Java** y **Spring Boot**, para monitoreo de datos (en este caso fluctuaciones del valor del dolar en Argentina).

---

## 📑 Índice

- [📌 Objetivo del Proyecto](#-objetivo-del-proyecto)
- [⚙️ Tecnologías Utilizadas](#️-tecnologías-utilizadas)
- [📺 Recursos Utilizados](#-recursos-utilizados)
- [📚 Conceptos Aplicados](#-conceptos-aplicados)
- [🧑‍💻 Posible consigna sujerida del ejercicio](#posible-consigna-sujerida-del-ejercicio)
- [🏗️ Estructura del Proyecto](#️-estructura-del-proyecto)
- [🔎 Lógica del Patrón Observer](#-lógica-del-patrón-observer)
  - [🔁 Interfaces](#-interfaces)
  - [📡 Sujeto / Servicio Observable](#-sujeto--servicio-observable)
  - [👥 Observadores Tradicionales](#-observadores-tradicionales)
  - [🌐 Evento personalizado con Spring Boot](#-evento-personalizado-de-cambio-de-dólar-con-spring-boot)
  - [🌐 Observadores con Spring Events](#-observadores-con-spring-events)
- [🚀 Ejecución y Simulación](#-ejecución-y-simulación)
- [🧑‍💻 Autor](#-autor)

---

## 📌 Objetivo del Proyecto

Demostrar la implementación del **patrón Observer**, integrando dos enfoques:

1. 🔁 El patrón Observer clásico (interfaces `Observer` y `Subject`).
2. 📡 Mecanismo de eventos de Spring (`ApplicationEventPublisher` + `@EventListener`).

Simulando un sistema de monitoreo con finalidades academicas de creación de contenido.

---

## ⚙️ Tecnologías Utilizadas

- Java 17+
- Spring Boot 3.x
- Maven
- Arquitectura orientada a eventos (Spring Events)
- Principios SOLID

---

## 📺 Recursos Utilizados

Videos utilizados como base de investigación y comprensión del patrón:

1. [Observer Pattern explicado (Java)](https://www.youtube.com/watch?v=JIN--0m_V7Q&list=WL&index=2)
2. [Observer Pattern aplicado con eventos en Spring](https://www.youtube.com/watch?v=okNAuUkohOU&list=WL&index=3)
3. [Aplicación Spring con eventos personalizados](https://www.youtube.com/watch?v=-VedAXrEc3Q&list=WL&index=4)


---

## 📚 Conceptos aplicados

El patrón Observer es un patrón de diseño comportamental que permite definir una relación de dependencia uno-a-muchos entre objetos.
Cuando un objeto (llamado Subject o Sujeto) cambia su estado, notifica automáticamente a todos los objetos dependientes (llamados Observers o Observadores), sin acoplarse directamente a ellos.

- ✅ Principios SOLID (especialmente OCP y DIP).
- ✅ Bajo acoplamiento entre componentes.
- ✅ Implementación mixta de Observer clásico y Event-Driven de Spring.
- ✅ Desacoplamiento entre emisores y receptores de eventos.

---

## Posible consigna sujerida del ejercicio
> Realizar una aplicación utilizando el framework **Spring Boot**, donde se implemente el **Patrón Observer**, permitiendo simular diferentes entidades (como bancos, casas de cambio e inversores) que reaccionen ante cambios en el valor del dólar. El sistema debe incluir al menos una implementación manual del patrón y otra basada en eventos con `@EventListener`. La simulación deberá ejecutarse desde el método `main()` con comportamientos diferenciados por tipo de observador.

---

## 🏗️ Estructura del Proyecto

- **interfaces/**:  Interfaces del patrón Observer.
- **events/**:  Evento personalizado de cambio de dólar.
- **ObservadoresConcretos/**: Observers tradicionales.
- **ObservadoresSpring/**: Observers basados en eventos de Spring.
- **SujetoService/**: Clase que actúa como el sujeto observable, específicamente el servicio para el dólar.
- **PatronesPp2025Application.java**: La clase principal que inicia la aplicación.


---

## 🔎 Lógica del Patrón Observer

### 🔁 Interfaces

```java
// Observer.java
public interface Observer {
    void update();
}
// Subject.java
public interface Subject {
    void suscribe(Observer observer);
    void unsuscribe(Observer observer);
    void notificarObservers();
}

```
---
## 📡 Sujeto / Servicio Observable
### Clase central que mantiene el estado del dólar y notifica los cambios.
```java
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

     // Actualizar el valor del dólar y notificar cambios
     
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
```
## 👥 Observadores Tradicionales
### 🏦 BancoObserver
```java
// BancoObserver.java
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
```
### 📈 InversorObserver
```java
// InversorObserver.java
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
```
## 🌐 Evento personalizado de cambio de dólar con Spring Boot
### Cumple con la función de notificar cambios en el dólar haciendo uso de ApplicationEvent
```java

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

```
## 🌐 Observadores con Spring Events
### 🏢 Casa de Cambio
```java
// CasaCambioEventListener.java
@Component
public class CasaCambioEventListener {

    private final String nombreCasa = "Casa de Cambio Central";

    // Escucha eventos de cambio del dólar usando @EventListener
 
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

```
### 📊 Mercado Financiero
```java
// MercadoEventListener.java
@Component
public class MercadoEventListener {

    private final String nombreMercado = "Mercado Abierto Electrónico";
    // Escucha eventos de cambio del dólar usando @EventListener

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

```

## 🚀 Ejecución y Simulación
### La clase PatronesPp2025Application ejecuta una simulación completa al iniciar el proyecto.
```java

 public static void main(String[] args) {
        SpringApplication.run(PatronesPp2025Application.class, args);
    }

@Override
public void run(String... args) {
    dolarService.suscribe(bancoObserver);
    dolarService.suscribe(inversorObserver);

    dolarService.actualizarValorDolar(1150.0); // SUBA
    dolarService.actualizarValorDolar(1350.0); // SUBA fuerte
    dolarService.actualizarValorDolar(950.0);  // BAJA fuerte
    System.out.println("\n--- Desuscribiendo Banco Observer ---"); // Desuscribir un observer
    dolarService.unsuscribe(bancoObserver);
    dolarService.actualizarValorDolar(1050.0); // Último cambio (solo se notificará al inversor y a los EventListeners)

        System.out.println("\n=== FIN DE LA SIMULACIÓN ===");
    }
}
```

### 💻 Salida esperada (consola)
```java
=== SISTEMA DE MONITOREO DEL DÓLAR ARGENTINO ===
Patrón Observer + Spring Events
Valor inicial del dólar: $1000.0

--- Suscribiendo Observers ---
Observer suscrito. Total: 1
Observer suscrito. Total: 2

--- Simulación de Cambios ---
=== CAMBIO EN EL DÓLAR ===
Valor anterior: $1000.0
Valor nuevo: $1150.0
Movimiento: SUBA

[BANCO] Banco Nación notificado - Nuevo valor: $1150.0

[INVERSOR] Inversor Particular analizando - Valor: $1150.0
[INVERSOR] Primera compra registrada: $1150.0

[CASA CAMBIO] Casa de Cambio Central recibió evento:
  - Cambió de $1000.0 a $1150.0
  - Movimiento: SUBA

[CASA CAMBIO] Alerta: Cambio significativo detectado - Ajustando comisiones
[CASA CAMBIO] Incrementando spread de venta

[MERCADO] Mercado Abierto Electrónico procesando evento:
 - Variación: 15,00%
[MERCADO] Alta volatilidad detectada - Suspendiendo operaciones por 5 minutos

--- Desuscribiendo Banco Observer ---
Observer desuscrito. Total: 1
```

---



## 🧑‍💻 Autor

**Agustín Adrián Del Monte**  
Estudiante de la **Tecnicatura Universitaria en Programación**  
[Universidad Tecnológica Nacional - UTN]

---


