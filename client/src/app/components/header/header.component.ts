import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { IonicModule } from '@ionic/angular';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
  standalone: true,
  imports: [IonicModule, CommonModule]
})
export class HeaderComponent {
  
  @Input() title: string = 'Default Title';
  @Input() selectionMode: boolean = false; 
  @Output() selectionModeChange = new EventEmitter<boolean>();
  @Output() deleteSelected = new EventEmitter<void>(); 
  
  selectedItems = new Set<any>(); 

  toggleSelectionMode() {
    this.selectionMode = !this.selectionMode;
    this.selectionModeChange.emit(this.selectionMode);
    if (!this.selectionMode) {
      this.selectedItems.clear();
    }
  }

  deleteItems() {
    this.deleteSelected.emit(); 
    this.selectedItems.clear();
    this.selectionMode = false;
  }
}
