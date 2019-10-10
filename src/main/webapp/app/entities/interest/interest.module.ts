import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SpingularSharedModule } from 'app/shared/shared.module';
import { InterestComponent } from './interest.component';
import { InterestDetailComponent } from './interest-detail.component';
import { InterestUpdateComponent } from './interest-update.component';
import { InterestDeletePopupComponent, InterestDeleteDialogComponent } from './interest-delete-dialog.component';
import { interestRoute, interestPopupRoute } from './interest.route';

const ENTITY_STATES = [...interestRoute, ...interestPopupRoute];

@NgModule({
  imports: [SpingularSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    InterestComponent,
    InterestDetailComponent,
    InterestUpdateComponent,
    InterestDeleteDialogComponent,
    InterestDeletePopupComponent
  ],
  entryComponents: [InterestDeleteDialogComponent]
})
export class SpingularInterestModule {}
