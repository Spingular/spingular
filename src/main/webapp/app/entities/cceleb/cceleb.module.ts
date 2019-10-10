import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SpingularSharedModule } from 'app/shared/shared.module';
import { CcelebComponent } from './cceleb.component';
import { CcelebDetailComponent } from './cceleb-detail.component';
import { CcelebUpdateComponent } from './cceleb-update.component';
import { CcelebDeletePopupComponent, CcelebDeleteDialogComponent } from './cceleb-delete-dialog.component';
import { ccelebRoute, ccelebPopupRoute } from './cceleb.route';

const ENTITY_STATES = [...ccelebRoute, ...ccelebPopupRoute];

@NgModule({
  imports: [SpingularSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [CcelebComponent, CcelebDetailComponent, CcelebUpdateComponent, CcelebDeleteDialogComponent, CcelebDeletePopupComponent],
  entryComponents: [CcelebDeleteDialogComponent]
})
export class SpingularCcelebModule {}
