# DantulooTravels ✈️

<div align="center">
  <img src="https://angular.io/assets/images/logos/angular/angular.svg" alt="Angular" width="60" height="60"/>
  <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/typescript/typescript-original.svg" alt="TypeScript" width="60" height="60"/>
  <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" alt="Java" width="60" height="60"/>
  <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/spring/spring-original.svg" alt="Spring Boot" width="60" height="60"/>
  <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/docker/docker-original.svg" alt="Docker" width="60" height="60"/>
  <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/terraform/terraform-original.svg" alt="Terraform" width="60" height="60"/>
  <img src="https://upload.wikimedia.org/wikipedia/commons/d/d5/Tailwind_CSS_Logo.svg" alt="Tailwind CSS" width="60" height="60"/>
  <img src="https://developers.google.com/maps/images/maps-icon.svg" alt="Google Maps" width="60" height="60"/>
</div>

<br>

Una aplicación web innovadora que facilita la interacción entre conductores y pasajeros para optimizar el transporte compartido. El sistema permite a los conductores registrar sus vehículos y rutas planificadas, mientras que los pasajeros pueden reservar fácilmente una ruta y viajar con otros pasajeros.

## 🎯 Características Principales

- **🚗 Gestión de Vehículos**: Los conductores pueden registrar y administrar sus vehículos
- **🗺️ Planificación de Rutas**: Creación y gestión de rutas con integración de Google Maps
- **👥 Sistema de Reservas**: Los pasajeros pueden reservar asientos en rutas disponibles
- **💰 Optimización de Costos**: Reducción de gastos de transporte mediante viajes compartidos
- **🌱 Impacto Ambiental**: Contribución a la reducción de emisiones de CO2

## 🚀 Tecnologías

### Frontend
- **Angular 16.2.0** - Framework principal para SPA
- **TypeScript 5.1.3** - Lenguaje de programación tipado
- **Angular Material 16.2.14** - Componentes UI modernos
- **Tailwind CSS 3.4.3** - Framework de estilos utility-first
- **Google Maps API** - Integración de mapas y geolocalización

### Backend
- **Java** - Lenguaje de programación del servidor
- **Spring Boot** - Framework para desarrollo de APIs REST
- **Spring Security** - Autenticación y autorización
- **JPA/Hibernate** - ORM para gestión de base de datos

### DevOps & Infraestructura
- **Docker** - Contenedorización de aplicaciones
- **Terraform** - Infraestructura como código (IaC)
- **CI/CD** - Integración y despliegue continuo

### Testing
- **Jasmine & Karma** - Testing unitario para Angular
- **JUnit** - Testing unitario para Java

## 📋 Prerrequisitos

- **Node.js** (versión 16 o superior)
- **Angular CLI** 16.2.5
- **Java** 11 o superior
- **Docker** y Docker Compose
- **Terraform** (para despliegue en infraestructura)

## 🛠️ Instalación

### Frontend

```bash
# Navegar al directorio del frontend
cd front-end

# Instalar dependencias
npm install

# Ejecutar en modo desarrollo
npm start
```

La aplicación estará disponible en `http://localhost:4200`

### Backend

```bash
# Navegar al directorio del backend
cd back-end

# Compilar y ejecutar
./mvnw spring-boot:run
```

El servidor estará disponible en `http://localhost:8080`

## 🚀 Deployment

### Desarrollo Local con Docker

```bash
# Construir y ejecutar todos los servicios
docker-compose up --build

# Ejecutar en segundo plano
docker-compose up -d
```

### Producción con Terraform

```bash
# Inicializar Terraform
terraform init

# Planificar despliegue
terraform plan

# Aplicar cambios
terraform apply
```

## 🧪 Testing

### Frontend
```bash
cd front-end
npm test              # Tests unitarios
npm run test:coverage # Coverage de tests
npm run e2e           # Tests end-to-end
```

### Backend
```bash
cd back-end
./mvnw test                    # Tests unitarios
./mvnw test -Dspring.profiles.active=test
```

## 🌟 Funcionalidades Clave

### Para Conductores
- Registro y verificación de vehículos
- Creación de rutas con horarios específicos
- Gestión de reservas de pasajeros
- Seguimiento de ganancias y estadísticas

### Para Pasajeros
- Búsqueda de rutas disponibles
- Reserva de asientos en tiempo real
- Historial de viajes
- Sistema de valoraciones y comentarios

<div align="center">
  <br><br>
  <i>Desarrollado con ❤️ por el equipo Felines</i>
</div>
