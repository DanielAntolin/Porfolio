import { DOCUMENT } from '@angular/common';
import { Component, inject, signal } from '@angular/core';
import { toObservable, toSignal } from '@angular/core/rxjs-interop';
import { catchError, of, switchMap } from 'rxjs';

import { PortfolioService } from '../../core/services/portfolio.service';
import { ContactComponent } from '../contact/contact.component';
import { EducationComponent } from '../education/education.component';
import { ExperienceComponent } from '../experience/experience.component';
import { LanguagesComponent } from '../languages/languages.component';
import { ProfileComponent } from '../profile/profile.component';
import { ProjectsComponent } from '../projects/projects.component';
import { SkillsComponent } from '../skills/skills.component';

@Component({
  selector: 'app-terminal-layout',
  standalone: true,
  imports: [
    ProfileComponent,
    ExperienceComponent,
    EducationComponent,
    SkillsComponent,
    ProjectsComponent,
    LanguagesComponent,
    ContactComponent
  ],
  templateUrl: './terminal-layout.component.html',
  styleUrl: './terminal-layout.component.scss'
})
export class TerminalLayoutComponent {
  private readonly portfolioService = inject(PortfolioService);
  private readonly document = inject(DOCUMENT);

  readonly language = signal<'es' | 'en'>(this.getInitialLanguage());
  readonly portfolio = toSignal(
    toObservable(this.language).pipe(
      switchMap((language) => this.portfolioService.getPortfolio(language).pipe(catchError(() => of(null))))
    ),
    { initialValue: null }
  );

  readonly sections = ['profile', 'experience', 'education', 'skills', 'projects', 'languages', 'contact'];

  setLanguage(language: 'es' | 'en'): void {
    this.language.set(language);
    const url = language === 'en' ? 'https://danielantolin.com/?lang=en' : 'https://danielantolin.com/';
    window.history.replaceState({}, '', language === 'en' ? '/?lang=en' : '/');
    this.document.documentElement.lang = language;
    this.updateSeo(language, url);
  }

  private getInitialLanguage(): 'es' | 'en' {
    const language = new URLSearchParams(window.location.search).get('lang') === 'en' ? 'en' : 'es';
    const url = language === 'en' ? 'https://danielantolin.com/?lang=en' : 'https://danielantolin.com/';
    this.document.documentElement.lang = language;
    this.updateSeo(language, url);
    return language;
  }

  private updateSeo(language: 'es' | 'en', url: string): void {
    const isEnglish = language === 'en';
    const title = isEnglish ? 'Daniel Antolín · Software Engineer' : 'Daniel Antolín · Ingeniero de Software';
    const description = isEnglish
      ? 'Portfolio of Daniel Antolín, software engineer specialising in embedded systems, industrial software and cross-platform applications.'
      : 'Portfolio de Daniel Antolín, ingeniero de software especializado en sistemas embebidos, software industrial y aplicaciones multiplataforma.';

    this.document.title = title;
    this.setMeta('name', 'description', description);
    this.setMeta('property', 'og:title', title);
    this.setMeta('property', 'og:description', description);
    this.setMeta('property', 'og:url', url);
    this.setMeta('property', 'og:locale', isEnglish ? 'en_GB' : 'es_ES');
    this.setMeta('name', 'twitter:title', title);
    this.setMeta('name', 'twitter:description', description);
    this.document.querySelector<HTMLLinkElement>('link[rel="canonical"]')?.setAttribute('href', url);
  }

  private setMeta(attribute: 'name' | 'property', key: string, content: string): void {
    this.document.querySelector<HTMLMetaElement>(`meta[${attribute}="${key}"]`)?.setAttribute('content', content);
  }
}
