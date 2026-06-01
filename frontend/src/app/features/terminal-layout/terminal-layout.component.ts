import { Component, inject } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { catchError, of } from 'rxjs';

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

  readonly portfolio = toSignal(
    this.portfolioService.getPortfolio().pipe(catchError(() => of(null))),
    { initialValue: null }
  );

  readonly sections = [
    { id: 'profile', label: 'profile' },
    { id: 'experience', label: 'experience' },
    { id: 'education', label: 'education' },
    { id: 'skills', label: 'skills' },
    { id: 'projects', label: 'projects' },
    { id: 'languages', label: 'languages' },
    { id: 'contact', label: 'contact' }
  ];
}
