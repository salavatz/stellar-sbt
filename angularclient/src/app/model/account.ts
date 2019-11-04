import {Balance} from "./balance";

export class Account {
  publicKey: string;
  secretKey: string;
  balance: Balance[] = [];
}
