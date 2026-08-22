# Portfolio de Daniel Antolín

Portfolio personal full stack, bilingüe y autoalojado. La interfaz está construida con Angular y consume una API REST de Spring Boot con la información profesional del portfolio.

**Producción:** [danielantolin.com](https://danielantolin.com) · **API:** [api.danielantolin.com/api/portfolio](https://api.danielantolin.com/api/portfolio) · **Inglés:** [danielantolin.com/?lang=en](https://danielantolin.com/?lang=en)

## Características

- Interfaz editorial con estética de terminal, diseñada en Angular y SCSS.
- Contenido profesional en español e inglés.
- API REST independiente con Spring Boot y Java 21.
- Selector de idioma que actualiza datos, URL y metadatos SEO.
- SEO técnico: canónica, `hreflang`, Open Graph, Twitter Cards, JSON-LD `Person`, `robots.txt` y sitemap.
- Dos contenedores Docker compatibles con ARM64.
- Despliegue autoalojado en Oracle Cloud mediante Coolify, Nginx, Traefik y HTTPS automático.

## Arquitectura

```text
                         ┌────────────────────────────────────────┐
Navegador ──────────────►│ https://danielantolin.com              │
                         │ Angular compilado + Nginx              │
                         └──────────────────┬─────────────────────┘
                                            │ HTTPS / JSON
                                            ▼
                         ┌────────────────────────────────────────┐
                         │ https://api.danielantolin.com/api      │
                         │ Spring Boot + Java 21                  │
                         └────────────────────────────────────────┘

GitHub ──► Coolify ──► Oracle Cloud ARM64 ──► Traefik / Let's Encrypt
```

El frontend y el backend son aplicaciones independientes. No existe base de datos: el contenido se mantiene en el servicio Java del backend. Esto simplifica el despliegue y evita exponer servicios innecesarios.

## Estructura del repositorio

```text
Porfolio/
├── backend/
│   ├── Dockerfile                         # Build Maven y ejecución Java 21
│   ├── pom.xml
│   └── src/main/java/com/danielantolin/portfolio/
│       ├── PortfolioApplication.java      # Arranque de Spring Boot
│       ├── config/CorsConfig.java          # Orígenes web permitidos
│       ├── controller/PortfolioController  # Endpoints HTTP /api
│       ├── dto/                            # Contratos JSON de la API
│       └── service/PortfolioService.java   # Contenido ES/EN del portfolio
├── frontend/
│   ├── Dockerfile                         # Build Angular y Nginx
│   ├── nginx.conf                         # Fallback para rutas SPA
│   ├── proxy.conf.json                    # Proxy local hacia Spring Boot
│   ├── public/
│   │   ├── profile-photo.jpg              # Foto de perfil usada en ES y EN
│   │   ├── robots.txt
│   │   └── sitemap.xml
│   └── src/
│       ├── index.html                     # Metadatos SEO iniciales
│       └── app/
│           ├── core/                      # Modelos TypeScript y servicio HTTP
│           └── features/                  # Layout y secciones visuales
└── README.md
```

## Backend

El backend usa una estructura MVC ligera:

1. `PortfolioController` recibe peticiones bajo `/api`.
2. Lee el parámetro opcional `lang` (`es` por defecto o `en`).
3. `PortfolioService` devuelve el contenido adecuado.
4. Los `record` del directorio `dto` se serializan a JSON automáticamente.

`CorsConfig` permite únicamente peticiones GET desde `localhost:4200`, `danielantolin.com` y `www.danielantolin.com`.

### Endpoints

| Endpoint | Resultado |
| --- | --- |
| `GET /api/portfolio?lang=es` | Toda la información del portfolio en español |
| `GET /api/portfolio?lang=en` | Toda la información del portfolio en inglés |
| `GET /api/profile` | Perfil profesional |
| `GET /api/experience` | Experiencia laboral |
| `GET /api/education` | Formación |
| `GET /api/skills` | Tecnologías y áreas de especialización |
| `GET /api/projects` | Repositorios y proyectos |
| `GET /api/languages` | Idiomas |
| `GET /api/contact` | Contacto y enlaces sociales |

Ejemplo:

```bash
curl -fsS 'http://localhost:8080/api/portfolio?lang=es'
```

## Frontend

El frontend es una aplicación Angular con componentes standalone. `TerminalLayoutComponent` es el contenedor: controla el idioma con signals, solicita el objeto completo de portfolio a `PortfolioService` y reparte cada bloque de datos a su componente (`profile`, `experience`, `education`, `skills`, `projects`, `languages` y `contact`).

En desarrollo, `PortfolioService` usa `/api` y Angular lo redirige al backend con `proxy.conf.json`. En producción usa directamente `https://api.danielantolin.com/api`.

Al cambiar de idioma:

1. Se actualiza el parámetro de URL (`/?lang=en`).
2. Se vuelve a consultar `GET /api/portfolio?lang=en`.
3. Se actualizan idioma del documento, título, descripción, Open Graph y URL canónica.

La foto de perfil se sirve como recurso estático y es la misma en ambos idiomas.

## Desarrollo local

Requisitos:

- Java 21 y Maven 3.9 o superior.
- Node.js 22 y npm 10 o superior.

Iniciar el backend:

```bash
cd backend
mvn spring-boot:run
```

La API estará disponible en <http://localhost:8080/api/portfolio>.

En otra terminal, iniciar el frontend:

```bash
cd frontend
npm install
npm start
```

La web estará disponible en <http://localhost:4200>.

### Validación antes de publicar

```bash
cd frontend
npm run build

cd ../backend
mvn test

cd ..
git diff --check
git status --short
```

No subir `backend/target/`: son artefactos generados por Maven.

## Contenedores y producción

El `backend/Dockerfile` usa una compilación multi-stage: Maven + Temurin 21 para generar el JAR, seguido de una imagen JRE Alpine que expone el puerto `8080`.

El `frontend/Dockerfile` compila Angular con Node 22 y copia el resultado a Nginx 1.27 Alpine en el puerto `80`. `nginx.conf` sirve `index.html` para las rutas que no correspondan a un archivo estático.

En producción, Coolify crea un recurso por aplicación:

| Recurso | Base del repositorio | Puerto | Dominio |
| --- | --- | --- | --- |
| Frontend | `/frontend` | `80` | `danielantolin.com`, `www.danielantolin.com` |
| Backend | `/backend` | `8080` | `api.danielantolin.com` |

Traefik, administrado por Coolify, enruta el tráfico público y emite los certificados HTTPS de Let's Encrypt. El panel de Coolify no se expone a Internet; se accede a él por Tailscale.

## SEO

La web publica los recursos siguientes:

- [`/robots.txt`](https://danielantolin.com/robots.txt), que permite el rastreo y señala el sitemap.
- [`/sitemap.xml`](https://danielantolin.com/sitemap.xml), con las alternativas ES y EN.
- Metadatos `description`, canonical y `hreflang`.
- Metadatos Open Graph y Twitter para previsualizaciones.
- Datos estructurados JSON-LD de tipo `Person`.

Después de un cambio relevante, se puede enviar el sitemap desde Google Search Console para acelerar el descubrimiento de la URL.

## Seguridad y mantenimiento

- No incluir tokens, claves SSH, contraseñas ni certificados en el repositorio.
- La API solo acepta métodos GET y orígenes web explícitamente autorizados.
- Los puertos públicos necesarios son 80 y 443; no publicar el panel Coolify ni el puerto interno de Spring Boot.
- Las copias de seguridad y los datos del servidor se documentan fuera de este repositorio.

## Licencia

Código y contenido pertenecen a Daniel Antolín. No reutilizar la información personal, fotografía o contenido profesional sin autorización.
