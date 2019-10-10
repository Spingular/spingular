import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SpingularSharedModule } from 'app/shared/shared.module';
import { UrllinkComponent } from './urllink.component';
import { UrllinkDetailComponent } from './urllink-detail.component';
import { UrllinkUpdateComponent } from './urllink-update.component';
import { UrllinkDeletePopupComponent, UrllinkDeleteDialogComponent } from './urllink-delete-dialog.component';
import { urllinkRoute, urllinkPopupRoute } from './urllink.route';

const ENTITY_STATES = [...urllinkRoute, ...urllinkPopupRoute];

@NgModule({
  imports: [SpingularSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    UrllinkComponent,
    UrllinkDetailComponent,
    UrllinkUpdateComponent,
    UrllinkDeleteDialogComponent,
    UrllinkDeletePopupComponent
  ],
  entryComponents: [UrllinkDeleteDialogComponent]
})
export class SpingularUrllinkModule {}
