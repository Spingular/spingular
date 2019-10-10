import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SpingularSharedModule } from 'app/shared/shared.module';
import { BlogComponent } from './blog.component';
import { BlogDetailComponent } from './blog-detail.component';
import { BlogUpdateComponent } from './blog-update.component';
import { BlogDeletePopupComponent, BlogDeleteDialogComponent } from './blog-delete-dialog.component';
import { blogRoute, blogPopupRoute } from './blog.route';

const ENTITY_STATES = [...blogRoute, ...blogPopupRoute];

@NgModule({
  imports: [SpingularSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [BlogComponent, BlogDetailComponent, BlogUpdateComponent, BlogDeleteDialogComponent, BlogDeletePopupComponent],
  entryComponents: [BlogDeleteDialogComponent]
})
export class SpingularBlogModule {}
