# Portfolio Full-Stack

Portfolio personal full-stack basado en el CV real de Daniel Antolín. El backend expone una API REST con Spring Boot y el frontend Angular consume esa API con una interfaz inspirada en terminal moderna, combinando superficies oscuras con la dirección visual cálida definida en [DESIGN.md](/C:/Users/danielantolin.ARG-DC/Documents/Personal/Portafolio/DESIGN.md).

## Fuente de contenido

- CV normalizado a Markdown: [cv.md](/C:/Users/danielantolin.ARG-DC/Documents/Personal/Portafolio/cv.md)
- Guía visual: [DESIGN.md](/C:/Users/danielantolin.ARG-DC/Documents/Personal/Portafolio/DESIGN.md)

## Estructura

```text
Portafolio/
├─ backend/    Spring Boot API
├─ frontend/   Angular app
├─ cv.pdf      CV original
├─ cv.md       CV convertido a Markdown
├─ DESIGN.md   guía de diseño
└─ README.md
```

## API

Base URL backend: `http://localhost:8080`

- `GET /api/profile`
- `GET /api/experience`
- `GET /api/education`
- `GET /api/skills`
- `GET /api/projects`
- `GET /api/languages`
- `GET /api/contact`
- `GET /api/portfolio`

## Ejecutar backend

Requisitos:
- Java 21
- Maven 3.9+

Comandos:

```bash
cd backend
mvn spring-boot:run
```

URL principal:
- API: `http://localhost:8080/api/portfolio`

## Ejecutar frontend

Requisitos:
- Node 22+
- npm 10+

Comandos:

```bash
cd frontend
npm install
npm start
```

URL principal:
- Web: `http://localhost:4200`

Notas:
- El script `npm start` usa `proxy.conf.json` para redirigir `/api` al backend en desarrollo.
- El backend también expone CORS para `http://localhost:4200`.

## Observaciones de contenido

- No se han inventado proyectos ni idiomas porque no aparecen de forma explícita en el CV.
- El perfil de LinkedIn se mantiene sin URL hasta que se confirme la dirección exacta.
