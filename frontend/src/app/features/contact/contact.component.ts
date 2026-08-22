import { Component, input } from '@angular/core';

import { Contact } from '../../core/models/portfolio.models';

@Component({
  selector: 'app-contact',
  standalone: true,
  templateUrl: './contact.component.html',
  styleUrl: './contact.component.scss'
})
export class ContactComponent {
  readonly contact = input.required<Contact>();
  readonly language = input.required<'es' | 'en'>();
}
