import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CreateComponent } from './create/create.component';
import { SelectComponent } from './select/select.component';
import { ListComponent } from './list/list.component';

const routes: Routes = [
  { path: 'select', component: SelectComponent },
  { path: 'list', component: ListComponent },

  { path: '', redirectTo:'menu', pathMatch: 'full' },
	{ path: 'menu', component: CreateComponent },

  { path: '**', redirectTo: 'menu', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
