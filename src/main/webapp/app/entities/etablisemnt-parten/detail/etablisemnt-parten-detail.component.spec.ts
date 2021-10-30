import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EtablisemntPartenDetailComponent } from './etablisemnt-parten-detail.component';

describe('Component Tests', () => {
  describe('EtablisemntParten Management Detail Component', () => {
    let comp: EtablisemntPartenDetailComponent;
    let fixture: ComponentFixture<EtablisemntPartenDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [EtablisemntPartenDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ etablisemntParten: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(EtablisemntPartenDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EtablisemntPartenDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load etablisemntParten on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.etablisemntParten).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
