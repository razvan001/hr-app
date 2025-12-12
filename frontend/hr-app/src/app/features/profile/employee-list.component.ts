import { Component, signal, effect, inject } from '@angular/core';
import { Router } from '@angular/router';
import { toSignal } from '@angular/core/rxjs-interop';
import {ProfileService} from "../services/profile/profile.service";

@Component({
  selector: 'app-employee-list',
  standalone: true,
  templateUrl: './employee-list.component.html',
  styleUrls: ['./employee-list.component.scss']
})
export class EmployeeListComponent {

  private profileService = inject(ProfileService);
  private router = inject(Router);

  // Load employee list as a signal
  employees = toSignal(this.profileService.getAll(), { initialValue: [] });

  // Track loading & error
  loading = signal(false);
  error = signal<string | null>(null);

  deleteEmployee(id: string) {
    // this.loading.set(true);
    // this.error.set(null);
    //
    // this.profileService.delete(id).subscribe({
    //   next: () => {
    //     // Reload employees after delete
    //     this.profileService.getAll().subscribe(list => this.employees.set(list));
    //     this.loading.set(false);
    //   },
    //   error: () => {
    //     this.error.set('Could not delete employee.');
    //     this.loading.set(false);
    //   }
    // });
  }

  editEmployee(id: string) {
    this.router.navigate(['/employees', id, 'edit']);
  }
}
