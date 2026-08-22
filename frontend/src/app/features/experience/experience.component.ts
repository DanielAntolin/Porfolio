import { Component, input } from '@angular/core';

import { Experience } from '../../core/models/portfolio.models';

@Component({
  selector: 'app-experience',
  standalone: true,
  templateUrl: './experience.component.html',
  styleUrl: './experience.component.scss'
})
export class ExperienceComponent {
  readonly experience = input.required<Experience[]>();
  readonly language = input.required<'es' | 'en'>();
}
