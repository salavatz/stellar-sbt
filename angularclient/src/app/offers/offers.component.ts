import {Component} from '@angular/core';
import {MyErrorStateMatcher} from "../login/login.component";
import {ActivatedRoute, Router} from "@angular/router";
import {Offer} from "../model/offer";
import {OfferService} from "../service/offer.service";

@Component({
  selector: 'app-offers',
  templateUrl: './offers.component.html',
  styleUrls: ['./offers.component.css']
})
export class OffersComponent {

  offer: Offer;
  matcher = new MyErrorStateMatcher();
  codeAsset: String;
  amountAsset: String;

  constructor(private route: ActivatedRoute, private router: Router, private offerService: OfferService) {
    this.offer = new Offer();
    this.codeAsset = '';
    this.amountAsset = '';
  }

  onSubmitSellingOffer() {
    console.log(this.offer);
    this.offerService.sendSellOffer(this.offer).subscribe();
  }

  onSubmitIssuingAsset() {
    console.log(JSON.stringify({"code": this.codeAsset, "amount": this.amountAsset}));
    this.offerService.submitIssuingAsset(this.codeAsset, this.amountAsset).subscribe();
  }


  gotoUserList() {
    this.router.navigate(['/']);
  }

}
