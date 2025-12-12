import {Component, computed, effect, inject, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ActivatedRoute, Router} from '@angular/router';
import {ProfileService} from "../data/profile.service";
import {FeedbackComponent} from "../feedback/feedback.component";
import {getUsername, isManager} from '../../../auth/keycloak-init';

@Component({
  selector: 'app-profile-view',
  standalone: true,
  imports: [CommonModule, FeedbackComponent],
  templateUrl: './profile-view.component.html',
  styleUrls: ['./profile-view.component.scss']
})
export class ProfileViewComponent implements OnInit {
  public profileService = inject(ProfileService);
  private router = inject(Router);
  private route = inject(ActivatedRoute)

  canSeeSalary = computed(() => {
    return this.profileService.profile()?.userName === getUsername() || isManager();
  })

  constructor() {
    effect(() => this.profileService.profile(), {})
  }

  ngOnInit() {
    let userName = this.route.snapshot.paramMap.get('userName');
    if (!userName) {
      userName = getUsername();
    }

    this.profileService.loadProfile(userName);
  }

  edit() {
    this.router.navigate(['/profile/edit']);
  }
}
