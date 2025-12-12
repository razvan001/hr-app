import {Component, signal, effect, inject, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { toSignal } from '@angular/core/rxjs-interop';
import {ProfileService} from "./data/profile.service";
import {environment} from "../../../environments/environment";
import {isManager} from "../../auth/keycloak-init";

@Component({
  selector: 'app-employee-list',
  standalone: true,
  templateUrl: './employee-list.component.html',
  styleUrls: ['./employee-list.component.scss']
})
export class EmployeeListComponent implements OnInit{

  public profileService = inject(ProfileService);
  private router = inject(Router);

  isManager: boolean = isManager();

  constructor() {
    effect(() => this.profileService.profiles(), {})
  }

  ngOnInit(): void {
        this.profileService.loadNextPage();
    }

  // Track loading & error
  loading = this.profileService.loading;
  error = signal<string | null>(null);
  allLoaded = this.profileService.allLoaded;

  deleteEmployee(id: string) {

  }

  goToProfile(userName: string) {
    this.router.navigate(['/profile', userName])
  }

  editEmployee(id: string) {
    this.router.navigate(['/employees', id, 'edit']);
  }

  onScroll(event: any) {
    var threshold = 100;
    var position = event.target.scrollTop + event.target.offsetHeight;
    var height = event.target.scrollHeight;
    if (height - position < threshold) {
      this.profileService.loadNextPage();
    }
  }
}
