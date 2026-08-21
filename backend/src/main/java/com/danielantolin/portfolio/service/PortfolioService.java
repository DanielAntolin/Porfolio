package com.danielantolin.portfolio.service;

import com.danielantolin.portfolio.dto.ContactDto;
import com.danielantolin.portfolio.dto.EducationDto;
import com.danielantolin.portfolio.dto.ExperienceDto;
import com.danielantolin.portfolio.dto.LanguageDto;
import com.danielantolin.portfolio.dto.PortfolioDto;
import com.danielantolin.portfolio.dto.ProfileDto;
import com.danielantolin.portfolio.dto.ProjectDto;
import com.danielantolin.portfolio.dto.SkillGroupDto;
import com.danielantolin.portfolio.dto.SocialLinkDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortfolioService {

    public ProfileDto getProfile() {
        return new ProfileDto(
                "Daniel Antolín",
                "Ingeniero de Software",
                "Profesional orientado a resultados, especializado en sistemas embebidos y software industrial. "
                        + "Combina disciplina, trabajo en equipo y vision estrategica para disenar soluciones robustas "
                        + "que conectan hardware critico con experiencias de usuario intuitivas y multiplataforma.",
                List.of(
                        "Sistemas embebidos y control industrial",
                        "Aplicaciones multiplataforma",
                        "Arquitectura moderna con .NET MAUI",
                        "Interoperabilidad entre hardware y usuario final"
                )
        );
    }

    public List<ExperienceDto> getExperience() {
        return List.of(
                new ExperienceDto(
                        "Balanco",
                        "Ingeniero de Desarrollo de Software / Sistemas Embebidos",
                        "2024 - Actualmente",
                        "Full-time",
                        List.of(
                                "Diseno y despliegue end-to-end de soluciones para sistemas criticos de pesaje y etiquetado industrial.",
                                "Optimizacion de software para sistemas de bajos recursos mediante C++ y ensamblador para procesamiento en tiempo real.",
                                "Implementacion de logica de control con Lua y Yabasic para personalizacion de perifericos industriales."
                        ),
                        List.of("C++", "Ensamblador", "Lua", "Yabasic", "RTOS", "bare-metal")
                ),
                new ExperienceDto(
                        "Escribano Mechanical & Engineering",
                        "Practicas, Ingeniero en Sistemas Embebidos",
                        "2023 - 2024",
                        "Internship",
                        List.of(
                                "Desarrollo de logica de negocio en C++11 aplicando patrones de diseno para mantener el codigo escalable y mantenible."
                        ),
                        List.of("C++11", "Patrones de diseno", "Sistemas embebidos")
                )
        );
    }

    public List<EducationDto> getEducation() {
        return List.of(
                new EducationDto(
                        "Universidad Internacional de La Rioja",
                        "Ingenieria Informatica",
                        "Actualidad",
                        "En curso",
                        "Formacion universitaria actual en informatica."
                ),
                new EducationDto(
                        "Universidad de Alcala de Henares",
                        "Credencial Universitaria Especialista en Software en C++",
                        "2023 - 2024",
                        "Completado",
                        "Especializacion centrada en software en C++."
                )
        );
    }

    public List<SkillGroupDto> getSkills() {
        return List.of(
                new SkillGroupDto(
                        "Backend & Embedded",
                        List.of("C++", "Ensamblador", "RTOS", "bare-metal", "Lua", "Yabasic", "Linux", "Bash")
                ),
                new SkillGroupDto(
                        "Arquitectura & Producto",
                        List.of("Control industrial", "Pesaje y etiquetado", "Soluciones end-to-end", "Diseno mantenible")
                ),
                new SkillGroupDto(
                        "Multiplataforma",
                        List.of(".NET MAUI", "Interoperabilidad hardware-usuario", "Monitorizacion de dispositivos")
                )
        );
    }

    public List<ProjectDto> getProjects() {
        return List.of(
                new ProjectDto(
                        "StripePaymentGateway",
                        "Backend Java para una plataforma de negocio con creacion de productos, checkout seguro con Stripe y documentacion via Swagger.",
                        "Actualizado el 28 de noviembre de 2024",
                        List.of("Java", "Stripe", "Swagger UI", "Webhooks", "REST API"),
                        "https://github.com/DanielAntolin/StripePaymentGateway",
                        "Java",
                        3
                ),
                new ProjectDto(
                        "reactive-gaming-platform-microservices",
                        "Arquitectura reactiva para gaming platform con microservicios, JWT, Spring Cloud Gateway, WebFlux, R2DBC y procesamiento en tiempo real con Kafka Streams.",
                        "Actualizado el 14 de mayo de 2025",
                        List.of("Java", "Spring Cloud Gateway", "Spring Security", "WebFlux", "R2DBC", "PostgreSQL", "Kafka Streams"),
                        "https://github.com/DanielAntolin/reactive-gaming-platform-microservices",
                        "Java",
                        2
                ),
                new ProjectDto(
                        "DanielAntolin",
                        "Repositorio de perfil con presentacion tecnica, stack principal y foco en APIs REST, Android, .NET MAUI y software orientado al rendimiento.",
                        "Actualizado el 10 de abril de 2026",
                        List.of("Markdown", "Java", "Python", ".NET MAUI", "C++", "Assembly"),
                        "https://github.com/DanielAntolin/DanielAntolin",
                        "Markdown",
                        2
                )
        );
    }

    public List<LanguageDto> getLanguages() {
        return List.of(new LanguageDto("Ingles", "B2"));
    }

    public ContactDto getContact() {
        return new ContactDto(
                "trabajosdanidar12@gmail.com",
                "+34 660 236 766",
                "Guadalajara, Espana",
                List.of(
                        new SocialLinkDto("GitHub", "https://github.com/DanielAntolin", "DanielAntolin"),
                        new SocialLinkDto(
                                "LinkedIn",
                                "https://www.linkedin.com/in/dani-antol%C3%ADn-rosales-aa4414295?utm_source=share_via&utm_content=profile&utm_medium=member_android",
                                "Daniel Antolin Rosales"
                        )
                )
        );
    }

    public ProfileDto getProfile(String language) {
        if (!isEnglish(language)) return getProfile();
        return new ProfileDto(
                "Daniel Antolín",
                "Software Engineer",
                "Results-driven professional specialising in embedded systems and industrial software. "
                        + "Combines discipline, teamwork and strategic thinking to design robust solutions "
                        + "that connect critical hardware with intuitive cross-platform user experiences.",
                List.of(
                        "Embedded systems and industrial control",
                        "Cross-platform applications",
                        "Modern architecture with .NET MAUI",
                        "Hardware-to-user interoperability"
                )
        );
    }

    public List<ExperienceDto> getExperience(String language) {
        if (!isEnglish(language)) return getExperience();
        return List.of(
                new ExperienceDto(
                        "Balanco",
                        "Software Development Engineer / Embedded Systems",
                        "2024 - Present",
                        "Full-time",
                        List.of(
                                "End-to-end design and deployment of solutions for critical industrial weighing and labelling systems.",
                                "Optimisation of low-resource software with C++ and assembly for real-time processing.",
                                "Implementation of control logic with Lua and Yabasic to customise industrial peripherals."
                        ),
                        List.of("C++", "Assembly", "Lua", "Yabasic", "RTOS", "bare-metal")
                ),
                new ExperienceDto(
                        "Escribano Mechanical & Engineering",
                        "Intern, Embedded Systems Engineer",
                        "2023 - 2024",
                        "Internship",
                        List.of("Development of C++11 business logic using design patterns to keep code scalable and maintainable."),
                        List.of("C++11", "Design patterns", "Embedded systems")
                )
        );
    }

    public List<EducationDto> getEducation(String language) {
        if (!isEnglish(language)) return getEducation();
        return List.of(
                new EducationDto("Universidad Internacional de La Rioja", "Computer Engineering", "Present", "In progress", "Current university education in computer science."),
                new EducationDto("Universidad de Alcala de Henares", "University Specialist Credential in C++ Software", "2023 - 2024", "Completed", "Specialisation focused on C++ software.")
        );
    }

    public List<SkillGroupDto> getSkills(String language) {
        if (!isEnglish(language)) return getSkills();
        return List.of(
                new SkillGroupDto("Backend & Embedded", List.of("C++", "Assembly", "RTOS", "bare-metal", "Lua", "Yabasic", "Linux", "Bash")),
                new SkillGroupDto("Architecture & Product", List.of("Industrial control", "Weighing and labelling", "End-to-end solutions", "Maintainable design")),
                new SkillGroupDto("Cross-platform", List.of(".NET MAUI", "Hardware-to-user interoperability", "Device monitoring"))
        );
    }

    public List<ProjectDto> getProjects(String language) {
        if (!isEnglish(language)) return getProjects();
        return List.of(
                new ProjectDto("StripePaymentGateway", "Java backend for a business platform with product creation, secure Stripe checkout and Swagger documentation.", "Updated 28 November 2024", List.of("Java", "Stripe", "Swagger UI", "Webhooks", "REST API"), "https://github.com/DanielAntolin/StripePaymentGateway", "Java", 3),
                new ProjectDto("reactive-gaming-platform-microservices", "Reactive gaming-platform architecture with microservices, JWT, Spring Cloud Gateway, WebFlux, R2DBC and real-time Kafka Streams processing.", "Updated 14 May 2025", List.of("Java", "Spring Cloud Gateway", "Spring Security", "WebFlux", "R2DBC", "PostgreSQL", "Kafka Streams"), "https://github.com/DanielAntolin/reactive-gaming-platform-microservices", "Java", 2),
                new ProjectDto("DanielAntolin", "Profile repository presenting the core stack and a focus on REST APIs, Android, .NET MAUI and performance-oriented software.", "Updated 10 April 2026", List.of("Markdown", "Java", "Python", ".NET MAUI", "C++", "Assembly"), "https://github.com/DanielAntolin/DanielAntolin", "Markdown", 2)
        );
    }

    public List<LanguageDto> getLanguages(String language) {
        return isEnglish(language) ? List.of(new LanguageDto("English", "B2")) : getLanguages();
    }

    public ContactDto getContact(String language) {
        if (!isEnglish(language)) return getContact();
        return new ContactDto(
                "trabajosdanidar12@gmail.com",
                "+34 660 236 766",
                "Guadalajara, Spain",
                List.of(
                        new SocialLinkDto("GitHub", "https://github.com/DanielAntolin", "DanielAntolin"),
                        new SocialLinkDto("LinkedIn", "https://www.linkedin.com/in/dani-antol%C3%ADn-rosales-aa4414295?utm_source=share_via&utm_content=profile&utm_medium=member_android", "Daniel Antolin Rosales")
                )
        );
    }

    public PortfolioDto getPortfolio(String language) {
        return new PortfolioDto(
                getProfile(language),
                getExperience(language),
                getEducation(language),
                getSkills(language),
                getProjects(language),
                getLanguages(language),
                getContact(language)
        );
    }

    private boolean isEnglish(String language) {
        return "en".equalsIgnoreCase(language);
    }

    public PortfolioDto getPortfolio() {
        return new PortfolioDto(
                getProfile(),
                getExperience(),
                getEducation(),
                getSkills(),
                getProjects(),
                getLanguages(),
                getContact()
        );
    }
}
