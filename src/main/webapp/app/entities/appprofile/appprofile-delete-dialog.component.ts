import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAppprofile } from 'app/shared/model/appprofile.model';
import { AppprofileService } from './appprofile.service';

@Component({
  selector: 'jhi-appprofile-delete-dialog',
  templateUrl: './appprofile-delete-dialog.component.html'
})
export class AppprofileDeleteDialogComponent {
  appprofile: IAppprofile;

  constructor(
    protected appprofileService: AppprofileService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.appprofileService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'appprofileListModification',
        content: 'Deleted an appprofile'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-appprofile-delete-popup',
  template: ''
})
export class AppprofileDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ appprofile }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AppprofileDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.appprofile = appprofile;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/appprofile', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/appprofile', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
