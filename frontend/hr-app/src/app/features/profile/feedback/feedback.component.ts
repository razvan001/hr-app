import {Component, inject, Input, signal, OnInit} from "@angular/core";
import {CommonModule} from "@angular/common";
import {Feedback, ProfileService} from "../data/profile.service";
import {ReactiveFormsModule, FormGroup, FormBuilder, Validators} from "@angular/forms";
import {getUsername} from "../../../auth/keycloak-init";
import {CustomDatePipe} from "../../../pipes/custom-date.pipe";
import {SuccessModalComponent} from "../../../modals/success/success-modal.component";

@Component({
  selector: 'app-feedback',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, CustomDatePipe, SuccessModalComponent],
  templateUrl: './feedback.component.html',
  styleUrls: ['./feedback.component.scss']
})
export class FeedbackComponent implements OnInit {
  @Input() feedbacks: Feedback[] = [];
  @Input() profileUserName!: string | undefined;

  form!: FormGroup;
  feedbackValue = signal<string>('');
  showSuccessModal = false;

  private fb = inject(FormBuilder);
  private service = inject(ProfileService);

  ngOnInit() {
    this.form = this.fb.group({
      feedback: ['', Validators.required]
    });

    // Use a signal to react to value changes
    this.form.get('feedback')!.valueChanges.subscribe((value: string) => {
      this.feedbackValue.set(value);
    });
  }

  onSubmit() {
    if (this.form.valid && this.profileUserName) {
      const feedbackText = this.form.value.feedback;
      const feedbackPayload = {
        author: getUsername(),
        text: feedbackText,
        profileUsername: this.profileUserName
      };
      this.service.createFeedback(feedbackPayload).subscribe(() => {
        this.form.reset();
        this.feedbackValue.set('');
        this.showSuccessModal = true;
      });
    }
  }

  closeModal() {
    this.showSuccessModal = false;
  }

  showForm(): boolean {
    return !!getUsername() && !!this.profileUserName && getUsername() !== this.profileUserName;
  }
}
