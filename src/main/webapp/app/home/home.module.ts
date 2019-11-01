import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SpingularSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';
import { ABOUT_ROUTE } from './home.route';
import { AboutComponent } from '../static/about/about.component';
import { CREDITS_ROUTE } from './home.route';
import { CreditsComponent } from 'app/static/credits/credits.component';
import { ERROR404_ROUTE } from './home.route';
import { ErrorComponent } from 'app/static/error/error.component';
import { HELP_ROUTE } from './home.route';
import { HelpComponent } from 'app/static/help/help.component';
import { PRIVACY_ROUTE } from './home.route';
import { PrivacyComponent } from 'app/static/privacy/privacy.component';
import { TERMS_ROUTE } from './home.route';
import { TermsComponent } from 'app/static/terms/terms.component';
import { CLIENTS_ROUTE } from './home.route';
import { ClientsComponent } from 'app/static/clients/clients.component';
import { COMMINGSOON_ROUTE } from './home.route';
import { CommingsoonComponent } from 'app/static/commingsoon/commingsoon.component';
import { PRICING_ROUTE } from './home.route';
import { PricingComponent } from 'app/static/pricing/pricing.component';

@NgModule({
  imports: [
    SpingularSharedModule,
    RouterModule.forChild([
      HOME_ROUTE,
      ABOUT_ROUTE,
      CREDITS_ROUTE,
      ERROR404_ROUTE,
      HELP_ROUTE,
      PRIVACY_ROUTE,
      TERMS_ROUTE,
      CLIENTS_ROUTE,
      COMMINGSOON_ROUTE,
      PRICING_ROUTE
    ])
  ],
  declarations: [
    HomeComponent,
    AboutComponent,
    CreditsComponent,
    ErrorComponent,
    HelpComponent,
    PrivacyComponent,
    TermsComponent,
    ClientsComponent,
    CommingsoonComponent,
    PricingComponent
  ]
})
export class SpingularHomeModule {}
