import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SpingularTestModule } from '../../../test.module';
import { ProposalUpdateComponent } from 'app/entities/proposal/proposal-update.component';
import { ProposalService } from 'app/entities/proposal/proposal.service';
import { Proposal } from 'app/shared/model/proposal.model';

describe('Component Tests', () => {
  describe('Proposal Management Update Component', () => {
    let comp: ProposalUpdateComponent;
    let fixture: ComponentFixture<ProposalUpdateComponent>;
    let service: ProposalService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SpingularTestModule],
        declarations: [ProposalUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProposalUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProposalUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProposalService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Proposal(123);
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
        const entity = new Proposal();
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
