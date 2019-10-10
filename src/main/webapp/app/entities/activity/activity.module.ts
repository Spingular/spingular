import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SpingularSharedModule } from 'app/shared/shared.module';
import { ActivityComponent } from './activity.component';
import { ActivityDetailComponent } from './activity-detail.component';
import { ActivityUpdateComponent } from './activity-update.component';
import { ActivityDeletePopupComponent, ActivityDeleteDialogComponent } from './activity-delete-dialog.component';
import { activityRoute, activityPopupRoute } from './activity.route';

const ENTITY_STATES = [...activityRoute, ...activityPopupRoute];

@NgModule({
  imports: [SpingularSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ActivityComponent,
    ActivityDetailComponent,
    ActivityUpdateComponent,
    ActivityDeleteDialogComponent,
    ActivityDeletePopupComponent
  ],
  entryComponents: [ActivityDeleteDialogComponent]
})
export class SpingularActivityModule {}
