import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SpingularSharedModule } from 'app/shared/shared.module';
import { CinterestComponent } from './cinterest.component';
import { CinterestDetailComponent } from './cinterest-detail.component';
import { CinterestUpdateComponent } from './cinterest-update.component';
import { CinterestDeletePopupComponent, CinterestDeleteDialogComponent } from './cinterest-delete-dialog.component';
import { cinterestRoute, cinterestPopupRoute } from './cinterest.route';

const ENTITY_STATES = [...cinterestRoute, ...cinterestPopupRoute];

@NgModule({
  imports: [SpingularSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CinterestComponent,
    CinterestDetailComponent,
    CinterestUpdateComponent,
    CinterestDeleteDialogComponent,
    CinterestDeletePopupComponent
  ],
  entryComponents: [CinterestDeleteDialogComponent]
})
export class SpingularCinterestModule {}
