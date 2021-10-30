import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DemandElaborationDetailComponent } from './demand-elaboration-detail.component';

describe('Component Tests', () => {
  describe('DemandElaboration Management Detail Component', () => {
    let comp: DemandElaborationDetailComponent;
    let fixture: ComponentFixture<DemandElaborationDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [DemandElaborationDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ demandElaboration: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(DemandElaborationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DemandElaborationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load demandElaboration on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.demandElaboration).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
