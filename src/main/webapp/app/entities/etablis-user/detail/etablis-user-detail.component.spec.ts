import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EtablisUserDetailComponent } from './etablis-user-detail.component';

describe('Component Tests', () => {
  describe('EtablisUser Management Detail Component', () => {
    let comp: EtablisUserDetailComponent;
    let fixture: ComponentFixture<EtablisUserDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [EtablisUserDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ etablisUser: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(EtablisUserDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EtablisUserDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load etablisUser on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.etablisUser).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
