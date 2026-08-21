import { Component, input } from '@angular/core';

import { Profile } from '../../core/models/portfolio.models';

@Component({
  selector: 'app-profile',
  standalone: true,
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss'
})
export class ProfileComponent {
  readonly profile = input.required<Profile>();
  readonly language = input.required<'es' | 'en'>();
}
