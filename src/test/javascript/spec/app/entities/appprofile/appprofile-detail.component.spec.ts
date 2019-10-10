import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SpingularTestModule } from '../../../test.module';
import { AppprofileDetailComponent } from 'app/entities/appprofile/appprofile-detail.component';
import { Appprofile } from 'app/shared/model/appprofile.model';

describe('Component Tests', () => {
  describe('Appprofile Management Detail Component', () => {
    let comp: AppprofileDetailComponent;
    let fixture: ComponentFixture<AppprofileDetailComponent>;
    const route = ({ data: of({ appprofile: new Appprofile(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SpingularTestModule],
        declarations: [AppprofileDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AppprofileDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AppprofileDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.appprofile).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
