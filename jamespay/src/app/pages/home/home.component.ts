import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { NavbarComponent } from '../../shared/navbar/navbar.component';
import { FooterComponent } from '../../shared/footer/footer.component';

interface FeatureCard {
  icon: string;
  title: string;
  description: string;
}

interface StatItem {
  value: string;
  label: string;
}

interface Testimonial {
  quote: string;
  name: string;
  role: string;
}

interface PricingTier {
  name: string;
  price: string;
  cadence: string;
  description: string;
  features: string[];
  highlighted: boolean;
}

interface TickerEntry {
  merchant: string;
  amount: string;
  status: 'settled' | 'pending';
}

/**
 * Public marketing landing page. Entirely presentational - no forms,
 * no HTTP calls - so it is kept simple with readonly literal data.
 */
@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterLink, NavbarComponent, FooterComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent {
  readonly features: FeatureCard[] = [
    {
      icon: '⚡',
      title: 'Instant settlement',
      description: 'Funds clear to your ledger the moment a payment authorizes, not days later.',
    },
    {
      icon: '🛡️',
      title: 'PCI DSS Level 1',
      description: 'Card data never touches your servers. We carry the compliance burden for you.',
    },
    {
      icon: '🌐',
      title: '135 currencies',
      description: 'Accept and settle in local currency across five continents from one API.',
    },
    {
      icon: '📊',
      title: 'Reconciliation built in',
      description: 'Every transaction maps to a ledger entry automatically - no spreadsheet exports.',
    },
    {
      icon: '🔌',
      title: 'One integration',
      description: 'A single REST API replaces your card processor, payout rails, and FX desk.',
    },
    {
      icon: '🧾',
      title: 'Audit-ready exports',
      description: 'Generate settlement reports your finance team can hand straight to auditors.',
    },
  ];

  readonly stats: StatItem[] = [
    { value: '$4.2B+', label: 'Processed annually' },
    { value: '99.99%', label: 'API uptime' },
    { value: '120ms', label: 'Median authorization time' },
    { value: '6,800+', label: 'Businesses onboarded' },
  ];

  readonly testimonials: Testimonial[] = [
    {
      quote:
        'We moved our entire payout infrastructure to JamesPay in three weeks. Reconciliation that used to take our finance team two days now happens automatically.',
      name: 'Priya Raman',
      role: 'VP of Finance, Kestrel Logistics',
    },
    {
      quote:
        'The interceptor-based auth and clear webhook contracts meant our engineers shipped the integration without a single support ticket.',
      name: 'Daniel Osei',
      role: 'Staff Engineer, Northwind Retail',
    },
    {
      quote:
        'Settlement visibility used to be a black box. Now every stakeholder can see exactly where funds are in real time.',
      name: 'Marta Ibáñez',
      role: 'COO, Vela Health',
    },
  ];

  readonly pricingTiers: PricingTier[] = [
    {
      name: 'Starter',
      price: '$0',
      cadence: '/month + 2.9% + 30¢ per transaction',
      description: 'For teams validating a product before scaling volume.',
      features: ['Up to $50k processed / month', 'Standard settlement (T+2)', 'Email support'],
      highlighted: false,
    },
    {
      name: 'Growth',
      price: '$499',
      cadence: '/month + 1.9% + 20¢ per transaction',
      description: 'For businesses with predictable, growing transaction volume.',
      features: [
        'Unlimited processing volume',
        'Next-day settlement',
        'Dedicated Slack channel',
        'Role-based team access',
      ],
      highlighted: true,
    },
    {
      name: 'Enterprise',
      price: 'Custom',
      cadence: 'volume-based pricing',
      description: 'For regulated, high-volume, multi-entity organizations.',
      features: [
        'Same-day settlement',
        'Dedicated compliance liaison',
        'Custom SLAs & uptime guarantees',
        'On-premise key management options',
      ],
      highlighted: false,
    },
  ];

  /** Feeds the scrolling settlement strip beneath the hero - the page's signature element. */
  readonly tickerEntries: TickerEntry[] = [
    { merchant: 'Kestrel Logistics', amount: '$18,204.55', status: 'settled' },
    { merchant: 'Northwind Retail', amount: '$942.10', status: 'settled' },
    { merchant: 'Vela Health', amount: '$5,610.00', status: 'pending' },
    { merchant: 'Atlas Freight Co.', amount: '$76,330.40', status: 'settled' },
    { merchant: 'Solstice Studio', amount: '$1,205.99', status: 'pending' },
    { merchant: 'Harbor & Finch', amount: '$3,480.75', status: 'settled' },
  ];
}
