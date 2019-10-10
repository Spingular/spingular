import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SpingularTestModule } from '../../../test.module';
import { ProposalVoteDetailComponent } from 'app/entities/proposal-vote/proposal-vote-detail.component';
import { ProposalVote } from 'app/shared/model/proposal-vote.model';

describe('Component Tests', () => {
  describe('ProposalVote Management Detail Component', () => {
    let comp: ProposalVoteDetailComponent;
    let fixture: ComponentFixture<ProposalVoteDetailComponent>;
    const route = ({ data: of({ proposalVote: new ProposalVote(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SpingularTestModule],
        declarations: [ProposalVoteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProposalVoteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProposalVoteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.proposalVote).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
