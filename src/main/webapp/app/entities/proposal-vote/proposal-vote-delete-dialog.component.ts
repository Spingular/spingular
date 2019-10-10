import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProposalVote } from 'app/shared/model/proposal-vote.model';
import { ProposalVoteService } from './proposal-vote.service';

@Component({
  selector: 'jhi-proposal-vote-delete-dialog',
  templateUrl: './proposal-vote-delete-dialog.component.html'
})
export class ProposalVoteDeleteDialogComponent {
  proposalVote: IProposalVote;

  constructor(
    protected proposalVoteService: ProposalVoteService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.proposalVoteService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'proposalVoteListModification',
        content: 'Deleted an proposalVote'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-proposal-vote-delete-popup',
  template: ''
})
export class ProposalVoteDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ proposalVote }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProposalVoteDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.proposalVote = proposalVote;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/proposal-vote', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/proposal-vote', { outlets: { popup: null } }]);
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
