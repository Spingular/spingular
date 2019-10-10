import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SpingularSharedModule } from 'app/shared/shared.module';
import { ConfigVariablesComponent } from './config-variables.component';
import { ConfigVariablesDetailComponent } from './config-variables-detail.component';
import { ConfigVariablesUpdateComponent } from './config-variables-update.component';
import { ConfigVariablesDeletePopupComponent, ConfigVariablesDeleteDialogComponent } from './config-variables-delete-dialog.component';
import { configVariablesRoute, configVariablesPopupRoute } from './config-variables.route';

const ENTITY_STATES = [...configVariablesRoute, ...configVariablesPopupRoute];

@NgModule({
  imports: [SpingularSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ConfigVariablesComponent,
    ConfigVariablesDetailComponent,
    ConfigVariablesUpdateComponent,
    ConfigVariablesDeleteDialogComponent,
    ConfigVariablesDeletePopupComponent
  ],
  entryComponents: [ConfigVariablesDeleteDialogComponent]
})
export class SpingularConfigVariablesModule {}
