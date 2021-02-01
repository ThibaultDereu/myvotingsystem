import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ElectionListComponent } from './components/election-list/election-list.component';
import { ElectionCreationComponent } from './components/election-creation/election-creation.component';
import { VoteComponent } from './components/vote/vote.component';
import { VoteResultsComponent } from './components/vote-results/vote-results.component';

const routes: Routes = [
  { path: '', redirectTo: '/elections', pathMatch: 'full' },
  { path: 'elections', component: ElectionListComponent },
  { path: 'election-creation', component: ElectionCreationComponent },
  { path: 'vote/:electionId', component: VoteComponent },
  { path: 'vote/:electionId/results', component: VoteResultsComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
