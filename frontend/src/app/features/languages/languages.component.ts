import { Component, input } from '@angular/core';

import { Language } from '../../core/models/portfolio.models';

@Component({
  selector: 'app-languages',
  standalone: true,
  templateUrl: './languages.component.html',
  styleUrl: './languages.component.scss'
})
export class LanguagesComponent {
  readonly languages = input.required<Language[]>();
}
