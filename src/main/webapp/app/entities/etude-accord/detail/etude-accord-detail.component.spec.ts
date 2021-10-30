import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EtudeAccordDetailComponent } from './etude-accord-detail.component';

describe('Component Tests', () => {
  describe('EtudeAccord Management Detail Component', () => {
    let comp: EtudeAccordDetailComponent;
    let fixture: ComponentFixture<EtudeAccordDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [EtudeAccordDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ etudeAccord: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(EtudeAccordDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EtudeAccordDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load etudeAccord on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.etudeAccord).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
