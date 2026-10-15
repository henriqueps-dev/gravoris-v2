import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ToastContainerComponent } from './shared/components/toast-container/toast-container.component';
import { AuthRequiredModalComponent } from './shared/components/auth-required-modal/auth-required-modal.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, ToastContainerComponent, AuthRequiredModalComponent],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {}
