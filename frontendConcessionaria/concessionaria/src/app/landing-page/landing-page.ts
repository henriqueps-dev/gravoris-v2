import { Component, ElementRef, OnDestroy, OnInit, output, ViewChild } from '@angular/core';

@Component({
  selector: 'app-landing-page',
  standalone: true,
  imports: [],
  templateUrl: './landing-page.html',
  styleUrl: './landing-page.css'
})
export class LandingPage implements OnInit, OnDestroy {
  readonly navigateToStore = output<void>();
  @ViewChild('bgVideo') bgVideo?: ElementRef<HTMLVideoElement>;

  videos: string[] = [
    'assets/videos/koenigsegg.mp4',
    'assets/videos/bugatti.mp4',
    'assets/videos/mclaren.mp4'
  ];

  currentVideoIndex: number = 0;
  currentVideo: string = this.videos[0];

  interval: any;

  ngOnInit(): void {
    this.startVideoRotation();
  }

  startVideoRotation(): void {
    this.interval = setInterval(() => {
      this.changeVideo();
    }, 500);
  }

  changeVideo(): void {
    this.currentVideoIndex++;

    if (this.currentVideoIndex >= this.videos.length) {
      this.currentVideoIndex = 0;
    }

    this.currentVideo = this.videos[this.currentVideoIndex];

    // Ensure the element reloads the new src; changing bindings alone may not.
    queueMicrotask(() => {
      const el = this.bgVideo?.nativeElement;
      if (!el) return;
      el.load();
      // Autoplay can still be blocked in some scenarios; ignore errors.
      void el.play().catch(() => {});
    });
  }

  ngOnDestroy(): void {
    clearInterval(this.interval);
  }
}
