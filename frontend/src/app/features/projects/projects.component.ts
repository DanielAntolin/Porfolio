import { Component, input } from '@angular/core';

import { Project } from '../../core/models/portfolio.models';

@Component({
  selector: 'app-projects',
  standalone: true,
  templateUrl: './projects.component.html',
  styleUrl: './projects.component.scss'
})
export class ProjectsComponent {
  readonly projects = input.required<Project[]>();
}
