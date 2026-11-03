import { Component, inject, signal } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';

interface SummaryCard {
  label: string;
  value: string;
  delta: string;
  trend: 'up' | 'down';
}

interface Transaction {
  id: string;
  merchant: string;
  amount: string;
  method: string;
  date: string;
  status: 'settled' | 'pending' | 'failed';
}

/**
 * Authenticated dashboard shell. Protected by `authGuard`; the API
 * contract in the brief only defines auth endpoints, so the summary
 * cards and transaction table below use representative sample data
 * that a real transactions service would replace.
 */
@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css',
})
export class DashboardComponent {
  private readonly authService = inject(AuthService);
  private readonly router = inject(Router);

  readonly currentUser = this.authService.currentUser;
  readonly isSidebarOpen = signal(false);
  readonly isProfileMenuOpen = signal(false);

  readonly summaryCards: SummaryCard[] = [
    { label: 'Total processed (30d)', value: '$284,912.40', delta: '+12.4%', trend: 'up' },
    { label: 'Pending settlement', value: '$18,204.00', delta: '-3.1%', trend: 'down' },
    { label: 'Successful transactions', value: '2,481', delta: '+8.7%', trend: 'up' },
    { label: 'Failed transactions', value: '37', delta: '-15.2%', trend: 'down' },
  ];

  readonly transactions: Transaction[] = [
    { id: 'TXN-84621', merchant: 'Kestrel Logistics', amount: '$18,204.55', method: 'ACH', date: 'Jul 09, 2026', status: 'settled' },
    { id: 'TXN-84620', merchant: 'Northwind Retail', amount: '$942.10', method: 'Visa •••• 4412', date: 'Jul 09, 2026', status: 'settled' },
    { id: 'TXN-84619', merchant: 'Vela Health', amount: '$5,610.00', method: 'Mastercard •••• 8890', date: 'Jul 08, 2026', status: 'pending' },
    { id: 'TXN-84618', merchant: 'Atlas Freight Co.', amount: '$76,330.40', method: 'Wire', date: 'Jul 08, 2026', status: 'settled' },
    { id: 'TXN-84617', merchant: 'Solstice Studio', amount: '$1,205.99', method: 'Visa •••• 1123', date: 'Jul 07, 2026', status: 'failed' },
    { id: 'TXN-84616', merchant: 'Harbor & Finch', amount: '$3,480.75', method: 'ACH', date: 'Jul 07, 2026', status: 'settled' },
  ];

  toggleSidebar(): void {
    this.isSidebarOpen.update((open) => !open);
  }

  toggleProfileMenu(): void {
    this.isProfileMenuOpen.update((open) => !open);
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  /** Initials shown in the profile avatar when no photo is available. */
  initialsFor(name: string | undefined): string {
    if (!name) {
      return '?';
    }
    return name
      .split(' ')
      .map((part) => part.charAt(0))
      .slice(0, 2)
      .join('')
      .toUpperCase();
  }
}
