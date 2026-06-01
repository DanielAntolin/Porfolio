import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';

import { Portfolio } from '../models/portfolio.models';

@Injectable({ providedIn: 'root' })
export class PortfolioService {
  private readonly http = inject(HttpClient);

  getPortfolio(): Observable<Portfolio> {
    return this.http.get<Portfolio>('/api/portfolio');
  }
}
