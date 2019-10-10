import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SpingularTestModule } from '../../../test.module';
import { AppuserDetailComponent } from 'app/entities/appuser/appuser-detail.component';
import { Appuser } from 'app/shared/model/appuser.model';

describe('Component Tests', () => {
  describe('Appuser Management Detail Component', () => {
    let comp: AppuserDetailComponent;
    let fixture: ComponentFixture<AppuserDetailComponent>;
    const route = ({ data: of({ appuser: new Appuser(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SpingularTestModule],
        declarations: [AppuserDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AppuserDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AppuserDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.appuser).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
