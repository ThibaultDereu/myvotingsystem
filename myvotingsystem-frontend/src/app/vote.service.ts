import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';

import { Candidate } from './candidate';
import { VoteResults } from './vote-results';
import { MessageService } from './message.service';

@Injectable({
  providedIn: 'root',
})
export class VoteService {
  private url = 'api/votes';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
  };

  constructor(
    private http: HttpClient,
    private messageService: MessageService
  ) {}

  submitVote(electionId: number, candidates: Candidate[]): Observable<any> {
    return this.http.post(
      `${this.url}/${electionId}`,
      candidates,
      this.httpOptions
    );
  }

  getVoteResults(electionId: number): Observable<VoteResults> {
    const url = `${this.url}/${electionId}/results`;
    return this.http.get<VoteResults>(url);
  }
}
