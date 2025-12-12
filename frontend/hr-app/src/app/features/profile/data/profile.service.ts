import {Injectable, signal} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, tap} from "rxjs";
import {getUsername} from "../../../auth/keycloak-init";

export interface EmployeeProfile {
  id: string;
  name: string;
  email: string;
  position: string;
  department: string;
  salary: number; // sensitive
  userName: string;
  type: string
  feedbacks: Feedback[];
}

export interface Feedback {
  id: number;
  text: string;
  author: string;
  date: Date;
}

export interface ProfileResponse {
  content: EmployeeProfile[];
  page: number,
  size: number,
  totalElements: number
  totalPages: number
}

export interface CreateFeedback {
  author: string;
  text: string;
  profileUsername: string;
}

@Injectable({providedIn: 'root'})
export class ProfileService {
  private readonly _profile = signal<EmployeeProfile | null>(null);
  private readonly _profiles = signal<EmployeeProfile[]>([]);
  private readonly _allLoaded = signal(false);
  private readonly _page = signal(0);
  private readonly _loading = signal(false);
  private readonly _size = 10;

  profile = this._profile.asReadonly();
  profiles = this._profiles.asReadonly();
  allLoaded = this._allLoaded.asReadonly();
  loading = this._loading.asReadonly();

  constructor(private readonly http: HttpClient) {
  }

  loadProfile(userName: string | null) {
    this.http.get<EmployeeProfile>(`/profiles/${userName}`)
    .pipe(tap(profile => this._profile.set(profile)))
    .subscribe();
  }

  loadNextPage() {
    if (this._loading() || this._allLoaded()) {
      return;
    }
    this._loading.set(true);
    this.http.get<ProfileResponse>(`/profiles?page=${this._page()}&size=${this._size}`)
    .subscribe(response => {
      const filteredProfiles = response.content.filter(p => p.userName !== getUsername());
      let newProfiles = [...this._profiles(), ...filteredProfiles];
      this._profiles.set(newProfiles);
      this._allLoaded.set(response.content.length < this._size);
      this._page.update(val => val + 1);
      this._loading.set(false);
    });
  }

  createFeedback(feedback: CreateFeedback): Observable<Feedback> {
    return this.http.post<Feedback>(`/feedbacks`, feedback)
    .pipe((tap(newFeedback => {
      const currentProfile = this._profile();
      if (currentProfile) {
        this._profile.set({
          ...currentProfile,
          feedbacks: [...currentProfile.feedbacks, newFeedback]
        });
      }
    })));
  }


  updateProfile(updated: EmployeeProfile) {
    const merged = {...this._profile()!, ...updated};
    return this.http.put<EmployeeProfile>(`/profiles/${merged.userName}`, merged).pipe(
      tap(() => this._profile.set(merged)))
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`/profiles/${id}`);
  }

}

