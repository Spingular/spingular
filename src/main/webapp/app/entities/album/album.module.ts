import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SpingularSharedModule } from 'app/shared/shared.module';
import { AlbumComponent } from './album.component';
import { AlbumDetailComponent } from './album-detail.component';
import { AlbumUpdateComponent } from './album-update.component';
import { AlbumDeletePopupComponent, AlbumDeleteDialogComponent } from './album-delete-dialog.component';
import { albumRoute, albumPopupRoute } from './album.route';

const ENTITY_STATES = [...albumRoute, ...albumPopupRoute];

@NgModule({
  imports: [SpingularSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [AlbumComponent, AlbumDetailComponent, AlbumUpdateComponent, AlbumDeleteDialogComponent, AlbumDeletePopupComponent],
  entryComponents: [AlbumDeleteDialogComponent]
})
export class SpingularAlbumModule {}
