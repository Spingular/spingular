import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SpingularSharedModule } from 'app/shared/shared.module';
import { FollowComponent } from './follow.component';
import { FollowDetailComponent } from './follow-detail.component';
import { FollowUpdateComponent } from './follow-update.component';
import { FollowerComponent } from './follower.component';
import { FollowingComponent } from './following.component';
import { FollowDeletePopupComponent, FollowDeleteDialogComponent } from './follow-delete-dialog.component';
import { followRoute, followPopupRoute } from './follow.route';

const ENTITY_STATES = [...followRoute, ...followPopupRoute];

@NgModule({
  imports: [SpingularSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    FollowComponent,
    FollowDetailComponent,
    FollowUpdateComponent,
    FollowDeleteDialogComponent,
    FollowDeletePopupComponent,
    FollowerComponent,
    FollowingComponent
  ],
  entryComponents: [FollowDeleteDialogComponent]
})
export class SpingularFollowModule {}
