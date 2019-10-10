import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProposal } from 'app/shared/model/proposal.model';
import { ProposalService } from './proposal.service';

@Component({
  selector: 'jhi-proposal-delete-dialog',
  templateUrl: './proposal-delete-dialog.component.html'
})
export class ProposalDeleteDialogComponent {
  proposal: IProposal;

  constructor(protected proposalService: ProposalService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.proposalService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'proposalListModification',
        content: 'Deleted an proposal'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-proposal-delete-popup',
  template: ''
})
export class ProposalDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ proposal }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProposalDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.proposal = proposal;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/proposal', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/proposal', { outlets: { popup: null } }]);
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
