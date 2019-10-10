import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SpingularSharedModule } from 'app/shared/shared.module';
import { AppphotoComponent } from './appphoto.component';
import { AppphotoDetailComponent } from './appphoto-detail.component';
import { AppphotoUpdateComponent } from './appphoto-update.component';
import { AppphotoDeletePopupComponent, AppphotoDeleteDialogComponent } from './appphoto-delete-dialog.component';
import { appphotoRoute, appphotoPopupRoute } from './appphoto.route';

const ENTITY_STATES = [...appphotoRoute, ...appphotoPopupRoute];

@NgModule({
  imports: [SpingularSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AppphotoComponent,
    AppphotoDetailComponent,
    AppphotoUpdateComponent,
    AppphotoDeleteDialogComponent,
    AppphotoDeletePopupComponent
  ],
  entryComponents: [AppphotoDeleteDialogComponent]
})
export class SpingularAppphotoModule {}
