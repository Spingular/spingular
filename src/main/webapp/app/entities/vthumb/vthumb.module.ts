import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SpingularSharedModule } from 'app/shared/shared.module';
import { VthumbComponent } from './vthumb.component';
import { VthumbDetailComponent } from './vthumb-detail.component';
import { VthumbUpdateComponent } from './vthumb-update.component';
import { VthumbDeletePopupComponent, VthumbDeleteDialogComponent } from './vthumb-delete-dialog.component';
import { vthumbRoute, vthumbPopupRoute } from './vthumb.route';

const ENTITY_STATES = [...vthumbRoute, ...vthumbPopupRoute];

@NgModule({
  imports: [SpingularSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [VthumbComponent, VthumbDetailComponent, VthumbUpdateComponent, VthumbDeleteDialogComponent, VthumbDeletePopupComponent],
  entryComponents: [VthumbDeleteDialogComponent]
})
export class SpingularVthumbModule {}
