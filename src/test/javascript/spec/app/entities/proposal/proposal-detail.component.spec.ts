import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SpingularTestModule } from '../../../test.module';
import { ProposalDetailComponent } from 'app/entities/proposal/proposal-detail.component';
import { Proposal } from 'app/shared/model/proposal.model';

describe('Component Tests', () => {
  describe('Proposal Management Detail Component', () => {
    let comp: ProposalDetailComponent;
    let fixture: ComponentFixture<ProposalDetailComponent>;
    const route = ({ data: of({ proposal: new Proposal(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SpingularTestModule],
        declarations: [ProposalDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProposalDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProposalDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.proposal).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
