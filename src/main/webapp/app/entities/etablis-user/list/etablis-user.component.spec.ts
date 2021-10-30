import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { EtablisUserService } from '../service/etablis-user.service';

import { EtablisUserComponent } from './etablis-user.component';

describe('Component Tests', () => {
  describe('EtablisUser Management Component', () => {
    let comp: EtablisUserComponent;
    let fixture: ComponentFixture<EtablisUserComponent>;
    let service: EtablisUserService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [EtablisUserComponent],
      })
        .overrideTemplate(EtablisUserComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EtablisUserComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(EtablisUserService);

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
      expect(comp.etablisUsers?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
