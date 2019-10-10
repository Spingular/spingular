import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SpingularSharedModule } from 'app/shared/shared.module';
import { NewsletterComponent } from './newsletter.component';
import { NewsletterDetailComponent } from './newsletter-detail.component';
import { NewsletterUpdateComponent } from './newsletter-update.component';
import { NewsletterDeletePopupComponent, NewsletterDeleteDialogComponent } from './newsletter-delete-dialog.component';
import { newsletterRoute, newsletterPopupRoute } from './newsletter.route';

const ENTITY_STATES = [...newsletterRoute, ...newsletterPopupRoute];

@NgModule({
  imports: [SpingularSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    NewsletterComponent,
    NewsletterDetailComponent,
    NewsletterUpdateComponent,
    NewsletterDeleteDialogComponent,
    NewsletterDeletePopupComponent
  ],
  entryComponents: [NewsletterDeleteDialogComponent]
})
export class SpingularNewsletterModule {}
