import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SpingularSharedModule } from 'app/shared/shared.module';
import { AppprofileComponent } from './appprofile.component';
import { AppprofileDetailComponent } from './appprofile-detail.component';
import { AppprofileUpdateComponent } from './appprofile-update.component';
import { AppprofileDeletePopupComponent, AppprofileDeleteDialogComponent } from './appprofile-delete-dialog.component';
import { appprofileRoute, appprofilePopupRoute } from './appprofile.route';

const ENTITY_STATES = [...appprofileRoute, ...appprofilePopupRoute];

@NgModule({
  imports: [SpingularSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AppprofileComponent,
    AppprofileDetailComponent,
    AppprofileUpdateComponent,
    AppprofileDeleteDialogComponent,
    AppprofileDeletePopupComponent
  ],
  entryComponents: [AppprofileDeleteDialogComponent]
})
export class SpingularAppprofileModule {}
