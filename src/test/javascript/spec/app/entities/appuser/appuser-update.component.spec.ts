import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SpingularTestModule } from '../../../test.module';
import { AppuserUpdateComponent } from 'app/entities/appuser/appuser-update.component';
import { AppuserService } from 'app/entities/appuser/appuser.service';
import { Appuser } from 'app/shared/model/appuser.model';

describe('Component Tests', () => {
  describe('Appuser Management Update Component', () => {
    let comp: AppuserUpdateComponent;
    let fixture: ComponentFixture<AppuserUpdateComponent>;
    let service: AppuserService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SpingularTestModule],
        declarations: [AppuserUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AppuserUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AppuserUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AppuserService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Appuser(123);
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
        const entity = new Appuser();
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
