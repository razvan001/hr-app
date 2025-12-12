import {Injectable, signal} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {computed} from '@angular/core';
import {Observable, of, tap} from "rxjs";

export interface EmployeeProfile {
  id: string;
  name: string;
  email: string;
  position: string;
  department: string;
  salary: number; // sensitive
  feedback: string[];
}

@Injectable({providedIn: 'root'})
export class ProfileService {
  baseUrl = '/api/profiles';
  private readonly _profile = signal<EmployeeProfile | null>(null);

  profile = this._profile.asReadonly();

  constructor(private readonly http: HttpClient) {
  }

  loadProfile(userName: string | null) {
    // Replace with actual API
    /*    this.http.get<EmployeeProfile>(`/api/profiles/${id}`).subscribe(profile => {
          this._profile.set(profile);
        });*/
    const mockProfile = {
      id: 'mock-id',
      name: 'Mock User',
      email: 'mock.user@example.com',
      position: 'Developer',
      department: 'IT',
      salary: 200,
      feedback: []
    }
    this._profile.set(
      mockProfile);
  }

  getAll(): Observable<EmployeeProfile[]> {
    return of([{
      id: 'mock-id',
      name: 'Mock User',
      email: 'mock.user@example.com',
      position: 'Developer',
      department: 'IT',
      salary: 200,
      feedback: []
    },
      {
        id: 'mock-id2',
        name: 'Mock User2',
        email: 'mock.user2@example.com',
        position: 'Developer2',
        department: 'IT',
        salary: 200,
        feedback: []
      }] as EmployeeProfile[])
  }


  updateProfile(updated: EmployeeProfile) {
    console.log(updated);
    const merged = {...this._profile()!, ...updated};
    // Replace with actual API
    return this.http.put<EmployeeProfile>(`/api/profiles/${merged.id}`, merged).pipe(
      tap(() => this._profile.set(merged)))
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

}

