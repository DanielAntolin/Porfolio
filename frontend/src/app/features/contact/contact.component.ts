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
  readonly verificationRequested = signal(false);
  readonly verified = signal(false);
  readonly verificationToken = signal('');
  readonly form = this.formBuilder.nonNullable.group({
    name: ['', [Validators.required, Validators.maxLength(100)]],
    email: ['', [Validators.required, Validators.email, Validators.maxLength(254)]],
    message: ['', [Validators.required, Validators.maxLength(4000)]],
    code: ['', [Validators.pattern(/^\d{6}$/)]],
    website: ['', Validators.maxLength(200)]
  });

  submit(): void {
    if (this.form.invalid || this.sending()) {
      this.form.markAllAsTouched();
      return;
    }

    if (!this.verified()) {
      this.requestVerification();
      return;
    }

    this.sending.set(true);
    this.sent.set(false);
    this.failed.set(false);
    const { code, ...message } = this.form.getRawValue();
    this.portfolioService.sendContact({ ...message, verificationToken: this.verificationToken() } as ContactRequest)
      .pipe(finalize(() => this.sending.set(false)))
      .subscribe({
        next: () => {
          this.sent.set(true);
          this.form.reset();
          this.verified.set(false);
          this.verificationRequested.set(false);
          this.verificationToken.set('');
        },
        error: () => this.failed.set(true)
      });
  }

  confirmVerification(): void {
    const { email, code } = this.form.getRawValue();
    if (!email || !/^\d{6}$/.test(code)) return;
    this.sending.set(true);
    this.failed.set(false);
    this.portfolioService.confirmEmailVerification(email, code)
      .pipe(finalize(() => this.sending.set(false)))
      .subscribe({
        next: ({ verificationToken }) => {
          this.verificationToken.set(verificationToken);
          this.verified.set(true);
        },
        error: () => this.failed.set(true)
      });
  }

  private requestVerification(): void {
    const { email, website } = this.form.getRawValue();
    if (!email || this.form.controls.email.invalid) {
      this.form.controls.email.markAsTouched();
      return;
    }
    this.sending.set(true);
    this.failed.set(false);
    this.portfolioService.requestEmailVerification(email, website)
      .pipe(finalize(() => this.sending.set(false)))
      .subscribe({
        next: () => this.verificationRequested.set(true),
        error: () => this.failed.set(true)
      });
  }
}
