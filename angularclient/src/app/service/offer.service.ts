import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Offer } from "../model/offer";

@Injectable({
  providedIn: 'root'
})
export class OfferService {
  private offerUrl: string;
  private sellOffer: string;
  private buyOffer: string;
  private issueAsset: string;

  constructor(private http: HttpClient) {
    this.offerUrl ='http://localhost:8080/offers/';
    this.sellOffer ='create_sell_offer';
    this.issueAsset = 'create_asset';
    this.buyOffer ='create_buy_offer';
  }

  public sendSellOffer(offer: Offer) {
    return this.http.post<Offer>(this.offerUrl + this.sellOffer, offer);
  }

  public submitIssuingAsset(code: String, amount: String) {
    return this.http.post<JSON>(this.offerUrl + this.issueAsset, JSON.stringify({"code": code, "amount": amount}));
  }
}
