import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { DemandElaborationService } from '../service/demand-elaboration.service';

import { DemandElaborationComponent } from './demand-elaboration.component';

describe('Component Tests', () => {
  describe('DemandElaboration Management Component', () => {
    let comp: DemandElaborationComponent;
    let fixture: ComponentFixture<DemandElaborationComponent>;
    let service: DemandElaborationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [DemandElaborationComponent],
      })
        .overrideTemplate(DemandElaborationComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DemandElaborationComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(DemandElaborationService);

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
      expect(comp.demandElaborations[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.demandElaborations[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalledWith(expect.objectContaining({ sort: ['id,asc'] }));
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenLastCalledWith(expect.objectContaining({ sort: ['name,asc', 'id'] }));
    });

    it('should re-initialize the page', () => {
      // WHEN
      comp.loadPage(1);
      comp.reset();

      // THEN
      expect(comp.page).toEqual(0);
      expect(service.query).toHaveBeenCalledTimes(2);
      expect(comp.demandElaborations[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
