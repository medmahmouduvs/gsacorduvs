import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EspaceAcEtElDetailComponent } from './espace-ac-et-el-detail.component';

describe('Component Tests', () => {
  describe('EspaceAcEtEl Management Detail Component', () => {
    let comp: EspaceAcEtElDetailComponent;
    let fixture: ComponentFixture<EspaceAcEtElDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [EspaceAcEtElDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ espaceAcEtEl: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(EspaceAcEtElDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EspaceAcEtElDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load espaceAcEtEl on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.espaceAcEtEl).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
