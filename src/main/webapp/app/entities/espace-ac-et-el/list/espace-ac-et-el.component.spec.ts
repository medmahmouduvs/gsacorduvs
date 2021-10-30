import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { EspaceAcEtElService } from '../service/espace-ac-et-el.service';

import { EspaceAcEtElComponent } from './espace-ac-et-el.component';

describe('Component Tests', () => {
  describe('EspaceAcEtEl Management Component', () => {
    let comp: EspaceAcEtElComponent;
    let fixture: ComponentFixture<EspaceAcEtElComponent>;
    let service: EspaceAcEtElService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [EspaceAcEtElComponent],
      })
        .overrideTemplate(EspaceAcEtElComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EspaceAcEtElComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(EspaceAcEtElService);

      const headers = new HttpHeaders().append('link', 'link;link');
      jest.spyOn(service, 'query').mockReturnValue(
        of(
          new HttpResponse({
            body: [{ id: 123 }],
            headers,
          })
        )
      );
    });

    it('Should call load all on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.espaceAcEtEls?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
