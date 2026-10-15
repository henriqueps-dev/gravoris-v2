import { Component, inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LandingPage } from '../../landing-page/landing-page';
import { SiteHeaderComponent } from '../../shared/components/site-header/site-header.component';
import { ShowroomComponent } from '../../shared/components/showroom/showroom.component';
import { VehicleCatalogService } from '../../core/services/vehicle-catalog.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [LandingPage, SiteHeaderComponent, ShowroomComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit {
  private readonly router = inject(Router);
  private readonly catalog = inject(VehicleCatalogService);

  ngOnInit(): void {
    this.catalog.resetFilters();
    void this.catalog.loadVitrine();
  }

  scrollToShowroom(): void {
    document.getElementById('vitrine')?.scrollIntoView({ behavior: 'smooth', block: 'start' });
  }

  onSearch(term: string): void {
    void this.router.navigate(['/busca'], { queryParams: { q: term } });
  }
}
