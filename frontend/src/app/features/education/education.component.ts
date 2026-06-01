import { Component, input } from '@angular/core';

import { Education } from '../../core/models/portfolio.models';

@Component({
  selector: 'app-education',
  standalone: true,
  templateUrl: './education.component.html',
  styleUrl: './education.component.scss'
})
export class EducationComponent {
  readonly education = input.required<Education[]>();
}
