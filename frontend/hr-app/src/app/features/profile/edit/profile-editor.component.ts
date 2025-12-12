import {Component, signal, effect, inject, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {EmployeeProfile, ProfileService} from "../data/profile.service";
import {SuccessModalComponent} from "../../../modals/success/success-modal.component";
import {tap} from "rxjs";

@Component({
  selector: 'app-profile-editor',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule, SuccessModalComponent],
  templateUrl: './profile-editor.component.html',
  styleUrls: ['./profile-editor.component.scss']
})
export class ProfileEditorComponent implements OnInit {

  protected profileService = inject(ProfileService);
  private router = inject(Router);
  private route = inject(ActivatedRoute)


  showSuccessModal = false;

  closeModal() {
    this.showSuccessModal = false;
    this.goBack();
  }

  constructor() {
    // Load the profile on component creation
    effect(() => this.profileService.profile(), {})
  }

  profileForm!: FormGroup;

  ngOnInit() {
    // initialize form
    this.profileForm = new FormGroup({
      name: new FormControl('', [Validators.required]),
      email: new FormControl('', [Validators.required, Validators.email]),
      position: new FormControl('', Validators.required),
      department: new FormControl('', Validators.required),
      salary: new FormControl<number | null>(null, Validators.required),
    });

    // Load profile from service
    const id = this.route.snapshot.paramMap.get('id');
    this.profileService.loadProfile(id);
    const profile = this.profileService.profile();
    if (profile) {
      this.profileForm.patchValue({
        name: profile.name,
        email: profile.email,
        position: profile.position,
        department: profile.department,
        salary: profile.salary,
      });
    }

  }

  onCancel() {
    const profile = this.profileService.profile();
    if (profile) {
      this.profileForm.reset({
        name: profile.name,
        email: profile.email,
        position: profile.position,
        department: profile.department,
        salary: profile.salary,
      });
    }
  }

  goBack() {
    this.router.navigate(['/profiles']);
  }

  async onSave() {
    if (this.profileForm.invalid) return;

    const updated: EmployeeProfile = this.profileForm.value;
    if (updated) {
      this.profileService.updateProfile(updated)
      .pipe(
        tap(() => this.showSuccessModal = true))
      .subscribe();
    }
  }
}
