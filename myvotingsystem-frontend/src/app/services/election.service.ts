import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { environment } from '../../environments/environment';
import { Election } from '../models/election';
import { ElectionFull } from '../models/election-full';
import { ElectionCreated } from '../models/election-created';
import { MessageService } from './message.service';

@Injectable({
  providedIn: 'root',
})
export class ElectionService {
  private electionUrl = `${environment.apiUrl}/elections`;

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };

  constructor(
    private http: HttpClient,
    private messageService: MessageService
  ) {}

  getElections(): Observable<Election[]> {
    return this.http.get<Election[]>(this.electionUrl);
  }

  getElection(id: number): Observable<ElectionFull> {
    const url = `${this.electionUrl}/${id}`;
    return this.http.get<ElectionFull>(url);
  }

  createElection(election: ElectionCreated): Observable<ElectionCreated> {
    return this.http
      .post<ElectionCreated>(this.electionUrl, election, this.httpOptions)
      .pipe(catchError(this.handleError<ElectionCreated>()));
  }

  private handleError<T>(result?: T) {
    return (error: any): Observable<T> => {
      this.messageService.printMessage(`An error occurred : ${error.message}`);
      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
}
