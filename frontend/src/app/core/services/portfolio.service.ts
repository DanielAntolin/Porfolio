import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';

import { ContactRequest, EmailVerificationResponse, Portfolio } from '../models/portfolio.models';

@Injectable({ providedIn: 'root' })
export class PortfolioService {
  private readonly http = inject(HttpClient);
  private readonly apiBaseUrl =
    window.location.hostname === 'localhost' ? '/api' : 'https://api.danielantolin.com/api';

  getPortfolio(language: 'es' | 'en' = 'es'): Observable<Portfolio> {
    return this.http.get<Portfolio>(`${this.apiBaseUrl}/portfolio`, { params: { lang: language } });
  }

  sendContact(message: ContactRequest): Observable<void> {
    return this.http.post<void>(`${this.apiBaseUrl}/contact`, message);
  }

  requestEmailVerification(email: string, website: string): Observable<void> {
    return this.http.post<void>(`${this.apiBaseUrl}/contact/verification`, { email, website });
  }

  confirmEmailVerification(email: string, code: string): Observable<EmailVerificationResponse> {
    return this.http.post<EmailVerificationResponse>(`${this.apiBaseUrl}/contact/verification/confirm`, { email, code });
  }
}
