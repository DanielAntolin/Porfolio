import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';

import { Portfolio } from '../models/portfolio.models';

@Injectable({ providedIn: 'root' })
export class PortfolioService {
  private readonly http = inject(HttpClient);
  private readonly apiBaseUrl =
    window.location.hostname === 'localhost' ? '/api' : 'https://api.danielantolin.com/api';

  getPortfolio(language: 'es' | 'en' = 'es'): Observable<Portfolio> {
    return this.http.get<Portfolio>(`${this.apiBaseUrl}/portfolio`, { params: { lang: language } });
  }
}
