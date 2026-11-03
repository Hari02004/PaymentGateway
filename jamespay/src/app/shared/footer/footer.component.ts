import { Component } from '@angular/core';

/**
 * Marketing-site footer. Purely presentational - kept as its own
 * standalone component so it can be reused if additional public pages
 * (pricing, docs) are added later.
 */
@Component({
  selector: 'app-footer',
  standalone: true,
  imports: [],
  templateUrl: './footer.component.html',
  styleUrl: './footer.component.css',
})
export class FooterComponent {
  readonly year = new Date().getFullYear();
}
