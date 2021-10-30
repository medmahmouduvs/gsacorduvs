import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AccordeDetailComponent } from './accorde-detail.component';

describe('Component Tests', () => {
  describe('Accorde Management Detail Component', () => {
    let comp: AccordeDetailComponent;
    let fixture: ComponentFixture<AccordeDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [AccordeDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ accorde: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(AccordeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AccordeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load accorde on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.accorde).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
