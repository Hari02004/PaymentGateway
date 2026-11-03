import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';

/**
 * Route table for the standalone application.
 * Every page is lazy-loaded via `loadComponent` so the initial bundle only
 * contains the shell; each feature is fetched on demand.
 */
export const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./pages/home/home.component').then((m) => m.HomeComponent),
    title: 'JamesPay | Enterprise Payment Gateway',
  },
  {
    path: 'login',
    loadComponent: () => import('./pages/login/login.component').then((m) => m.LoginComponent),
    title: 'JamesPay | Sign In',
  },
  {
    path: 'register',
    loadComponent: () =>
      import('./pages/register/register.component').then((m) => m.RegisterComponent),
    title: 'JamesPay | Create Account',
  },
  {
    path: 'dashboard',
    loadComponent: () =>
      import('./pages/dashboard/dashboard.component').then((m) => m.DashboardComponent),
    canActivate: [authGuard],
    title: 'JamesPay | Dashboard',
  },
  {
    path: '**',
    redirectTo: '',
  },
];
