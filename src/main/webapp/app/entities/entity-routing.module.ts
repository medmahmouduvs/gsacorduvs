import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'etablisemnt-parten',
        data: { pageTitle: 'gsacorduvsApp.etablisemntParten.home.title' },
        loadChildren: () => import('./etablisemnt-parten/etablisemnt-parten.module').then(m => m.EtablisemntPartenModule),
      },
      {
        path: 'espace-ac-et-el',
        data: { pageTitle: 'gsacorduvsApp.espaceAcEtEl.home.title' },
        loadChildren: () => import('./espace-ac-et-el/espace-ac-et-el.module').then(m => m.EspaceAcEtElModule),
      },
      {
        path: 'accorde',
        data: { pageTitle: 'gsacorduvsApp.accorde.home.title' },
        loadChildren: () => import('./accorde/accorde.module').then(m => m.AccordeModule),
      },
      {
        path: 'demand-elaboration',
        data: { pageTitle: 'gsacorduvsApp.demandElaboration.home.title' },
        loadChildren: () => import('./demand-elaboration/demand-elaboration.module').then(m => m.DemandElaborationModule),
      },
      {
        path: 'etude-accord',
        data: { pageTitle: 'gsacorduvsApp.etudeAccord.home.title' },
        loadChildren: () => import('./etude-accord/etude-accord.module').then(m => m.EtudeAccordModule),
      },
      {
        path: 'etablis-user',
        data: { pageTitle: 'gsacorduvsApp.etablisUser.home.title' },
        loadChildren: () => import('./etablis-user/etablis-user.module').then(m => m.EtablisUserModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
