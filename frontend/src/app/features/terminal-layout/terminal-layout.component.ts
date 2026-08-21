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

  readonly language = signal<'es' | 'en'>('es');
  readonly portfolio = toSignal(
    toObservable(this.language).pipe(
      switchMap((language) => this.portfolioService.getPortfolio(language).pipe(catchError(() => of(null))))
    ),
    { initialValue: null }
  );

  readonly sections = ['profile', 'experience', 'education', 'skills', 'projects', 'languages', 'contact'];

  setLanguage(language: 'es' | 'en'): void {
    this.language.set(language);
  }
}
