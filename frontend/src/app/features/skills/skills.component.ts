import { Component, input } from '@angular/core';

import { SkillGroup } from '../../core/models/portfolio.models';

@Component({
  selector: 'app-skills',
  standalone: true,
  templateUrl: './skills.component.html',
  styleUrl: './skills.component.scss'
})
export class SkillsComponent {
  readonly skills = input.required<SkillGroup[]>();
}
