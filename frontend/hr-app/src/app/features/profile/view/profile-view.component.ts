import {Component, effect, inject, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import {ActivatedRoute, Router} from '@angular/router';
import {ProfileService} from "../../services/profile/profile.service";

@Component({
  selector: 'app-profile-view',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './profile-view.component.html',
  styleUrls: ['./profile-common.scss']
})
export class ProfileViewComponent implements OnInit {
  public profileService = inject(ProfileService);
  private router = inject(Router);
  private route = inject(ActivatedRoute)

  constructor() {
    effect(() => this.profileService.profile(), {})
  }

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    this.profileService.loadProfile(id);
  }

  edit() { this.router.navigate(['/profile/edit']); }
}
