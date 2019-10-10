import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SpingularSharedModule } from 'app/shared/shared.module';
import { FeedbackComponent } from './feedback.component';
import { FeedbackDetailComponent } from './feedback-detail.component';
import { FeedbackUpdateComponent } from './feedback-update.component';
import { FeedbackDeletePopupComponent, FeedbackDeleteDialogComponent } from './feedback-delete-dialog.component';
import { feedbackRoute, feedbackPopupRoute } from './feedback.route';

const ENTITY_STATES = [...feedbackRoute, ...feedbackPopupRoute];

@NgModule({
  imports: [SpingularSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    FeedbackComponent,
    FeedbackDetailComponent,
    FeedbackUpdateComponent,
    FeedbackDeleteDialogComponent,
    FeedbackDeletePopupComponent
  ],
  entryComponents: [FeedbackDeleteDialogComponent]
})
export class SpingularFeedbackModule {}
