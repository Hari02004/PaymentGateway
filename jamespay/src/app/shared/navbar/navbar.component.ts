import { Component, inject, signal } from '@angular/core';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';

/**
 * Marketing-site navbar used on the Home page. Adapts its call-to-action
 * based on whether a session is active, and collapses into a hamburger
 * menu below the tablet breakpoint (handled purely in CSS + this signal).
 */
@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css',
})
export class NavbarComponent {
  private readonly auth = inject(AuthService);
  private readonly router = inject(Router);

  /** Tracks whether the mobile menu is expanded. */
  readonly isMenuOpen = signal(false);

  /** Read-only view of the current session, used to swap CTA buttons. */
  readonly currentUser = this.auth.currentUser;

  toggleMenu(): void {
    this.isMenuOpen.update((open) => !open);
  }

  closeMenu(): void {
    this.isMenuOpen.set(false);
  }

  logout(): void {
    this.auth.logout();
    this.closeMenu();
    this.router.navigate(['/']);
  }
}
