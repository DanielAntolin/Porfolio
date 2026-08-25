import { Component, inject, input, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { finalize } from 'rxjs';

import { Contact, ContactRequest } from '../../core/models/portfolio.models';
import { PortfolioService } from '../../core/services/portfolio.service';

@Component({
  selector: 'app-contact',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './contact.component.html',
  styleUrl: './contact.component.scss'
})
export class ContactComponent {
  private readonly formBuilder = inject(FormBuilder);
  private readonly portfolioService = inject(PortfolioService);

  readonly contact = input.required<Contact>();
  readonly language = input.required<'es' | 'en'>();
  readonly sending = signal(false);
  readonly sent = signal(false);
  readonly failed = signal(false);
  readonly form = this.formBuilder.nonNullable.group({
    name: ['', [Validators.required, Validators.maxLength(100)]],
    email: ['', [Validators.required, Validators.email, Validators.maxLength(254)]],
    message: ['', [Validators.required, Validators.maxLength(4000)]],
    website: ['', Validators.maxLength(200)]
  });

  submit(): void {
    if (this.form.invalid || this.sending()) {
      this.form.markAllAsTouched();
      return;
    }

    this.sending.set(true);
    this.sent.set(false);
    this.failed.set(false);
    this.portfolioService.sendContact(this.form.getRawValue() as ContactRequest)
      .pipe(finalize(() => this.sending.set(false)))
      .subscribe({
        next: () => {
          this.sent.set(true);
          this.form.reset();
        },
        error: () => this.failed.set(true)
      });
  }
}
