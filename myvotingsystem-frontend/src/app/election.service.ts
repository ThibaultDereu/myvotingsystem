import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { Election } from './election';
import { ElectionFull } from './election-full'
import { ElectionCreated } from './election-created';
import { MessageService } from './message.service';

@Injectable({
  providedIn: 'root',
})
export class ElectionService {

  private electionUrl = 'api/elections';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient, private messageService: MessageService) { }


  getElections(): Observable<Election[]> {
    return this.http.get<Election[]>(this.electionUrl);
  }

  getElection(id: number): Observable<ElectionFull> {
    let url = `${this.electionUrl}/${id}`;
    return this.http.get<ElectionFull>(url);
  }

  createElection(election: ElectionCreated): Observable<ElectionCreated> {
    return this.http.post<ElectionCreated>(this.electionUrl, election, this.httpOptions).pipe(
      catchError(this.handleError<ElectionCreated>()));
  }

  private handleError<T>(result?: T) {
    return (error: any): Observable<T> => {
      this.messageService.printMessage(`An error occurred : ${error.message}`);
      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
}
