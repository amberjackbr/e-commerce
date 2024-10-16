import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ProvaComponent } from './prova/prova.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { authenticationInterceptor } from './auth/interceptorfnprova';
import { HomeComponent } from './home/home.component';
import { ProductdetailsComponent } from './productdetails/productdetails.component';
import { HeaderComponent } from './header/header.component';
import { SignUpComponent } from './sign-up/sign-up.component';
import { ProductlistComponent } from './productlist/productlist.component';
import { LoginComponent } from './login/login.component';
import { CartComponent } from './cart/cart.component';
import { MypurchasesComponent } from './mypurchases/mypurchases.component';
import { MyproductsComponent } from './myproducts/myproducts.component';
import { AddproductComponent } from './addproduct/addproduct.component';



@NgModule({
  declarations: [
    AppComponent,
    ProvaComponent,
    HomeComponent,
    ProductdetailsComponent,
    HeaderComponent,
    SignUpComponent,
    ProductlistComponent,
    LoginComponent,
    CartComponent,
    MypurchasesComponent,
    MyproductsComponent,
    AddproductComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [
    /*{provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    },*/
    provideHttpClient(withInterceptors([authenticationInterceptor])),
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
