import { Injectable } from "@angular/core";
import { EntityOp, ofEntityOp, ofEntityType } from "@ngrx/data";
import { createEffect } from "@ngrx/effects";
import { tap } from "rxjs";

import { Actions } from '@ngrx/effects';

@Injectable()
export class AddressEffects {


    constructor(private actions$: Actions) {

    }

    ngrxDataEffectForQueryLoad$ = createEffect(
        () => this.actions$.pipe(
            ofEntityType("Address"),
            ofEntityOp([EntityOp.QUERY_ALL_SUCCESS]),
            tap(action =>
                
                console.log("payload", action.payload)

                )
            ),
        { dispatch: false }
    );

}