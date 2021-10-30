jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { DemandElaborationService } from '../service/demand-elaboration.service';

import { DemandElaborationDeleteDialogComponent } from './demand-elaboration-delete-dialog.component';

describe('Component Tests', () => {
  describe('DemandElaboration Management Delete Component', () => {
    let comp: DemandElaborationDeleteDialogComponent;
    let fixture: ComponentFixture<DemandElaborationDeleteDialogComponent>;
    let service: DemandElaborationService;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [DemandElaborationDeleteDialogComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(DemandElaborationDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DemandElaborationDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(DemandElaborationService);
      mockActiveModal = TestBed.inject(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({})));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        jest.spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.close).not.toHaveBeenCalled();
        expect(mockActiveModal.dismiss).toHaveBeenCalled();
      });
    });
  });
});
