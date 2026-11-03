import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

/**
 * Root shell component. It only renders the router outlet - navbar/footer
 * are composed per-page so the dashboard can use its own sidebar layout
 * instead of the marketing navbar/footer.
 */
@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent {
  readonly title = 'JamesPay';
}
