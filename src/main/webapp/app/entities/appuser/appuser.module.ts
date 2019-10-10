import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SpingularSharedModule } from 'app/shared/shared.module';
import { AppuserComponent } from './appuser.component';
import { AppuserDetailComponent } from './appuser-detail.component';
import { AppuserUpdateComponent } from './appuser-update.component';
import { AppuserDeletePopupComponent, AppuserDeleteDialogComponent } from './appuser-delete-dialog.component';
import { appuserRoute, appuserPopupRoute } from './appuser.route';

const ENTITY_STATES = [...appuserRoute, ...appuserPopupRoute];

@NgModule({
  imports: [SpingularSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AppuserComponent,
    AppuserDetailComponent,
    AppuserUpdateComponent,
    AppuserDeleteDialogComponent,
    AppuserDeletePopupComponent
  ],
  entryComponents: [AppuserDeleteDialogComponent]
})
export class SpingularAppuserModule {}
