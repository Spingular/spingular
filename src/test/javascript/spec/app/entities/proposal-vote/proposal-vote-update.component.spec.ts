import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SpingularTestModule } from '../../../test.module';
import { ProposalVoteUpdateComponent } from 'app/entities/proposal-vote/proposal-vote-update.component';
import { ProposalVoteService } from 'app/entities/proposal-vote/proposal-vote.service';
import { ProposalVote } from 'app/shared/model/proposal-vote.model';

describe('Component Tests', () => {
  describe('ProposalVote Management Update Component', () => {
    let comp: ProposalVoteUpdateComponent;
    let fixture: ComponentFixture<ProposalVoteUpdateComponent>;
    let service: ProposalVoteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SpingularTestModule],
        declarations: [ProposalVoteUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProposalVoteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProposalVoteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProposalVoteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProposalVote(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProposalVote();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
