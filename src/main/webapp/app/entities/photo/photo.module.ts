import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SpingularSharedModule } from 'app/shared/shared.module';
import { PhotoComponent } from './photo.component';
import { PhotoDetailComponent } from './photo-detail.component';
import { PhotoUpdateComponent } from './photo-update.component';
import { PhotoDeletePopupComponent, PhotoDeleteDialogComponent } from './photo-delete-dialog.component';
import { photoRoute, photoPopupRoute } from './photo.route';

const ENTITY_STATES = [...photoRoute, ...photoPopupRoute];

@NgModule({
  imports: [SpingularSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [PhotoComponent, PhotoDetailComponent, PhotoUpdateComponent, PhotoDeleteDialogComponent, PhotoDeletePopupComponent],
  entryComponents: [PhotoDeleteDialogComponent]
})
export class SpingularPhotoModule {}
