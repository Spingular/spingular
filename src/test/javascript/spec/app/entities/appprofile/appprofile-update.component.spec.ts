import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SpingularTestModule } from '../../../test.module';
import { AppprofileUpdateComponent } from 'app/entities/appprofile/appprofile-update.component';
import { AppprofileService } from 'app/entities/appprofile/appprofile.service';
import { Appprofile } from 'app/shared/model/appprofile.model';

describe('Component Tests', () => {
  describe('Appprofile Management Update Component', () => {
    let comp: AppprofileUpdateComponent;
    let fixture: ComponentFixture<AppprofileUpdateComponent>;
    let service: AppprofileService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SpingularTestModule],
        declarations: [AppprofileUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AppprofileUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AppprofileUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AppprofileService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Appprofile(123);
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
        const entity = new Appprofile();
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
