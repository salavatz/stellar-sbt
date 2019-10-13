import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {AuthenticationService} from "../service/authentication.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  @Output() public sidenavToggle = new EventEmitter();

  constructor(private authenticationService :AuthenticationService) { }

  ngOnInit() {
  }

  public onToggleSidenav() {
    this.sidenavToggle.emit();
  }
}
