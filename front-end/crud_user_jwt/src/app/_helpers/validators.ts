import { AbstractControl, ValidatorFn, ValidationErrors } from '@angular/forms';

export class CustomValidators {

    static requiredWhen(requiredControlName, controlToCheckName): ValidatorFn {
        return (control: AbstractControl): ValidationErrors | null => {
            const required = control.get(requiredControlName);
            const toCheck = control.get(controlToCheckName);
            if (required.value || !toCheck.value) {
                CustomValidators.removeErrors(['required'], required);
                CustomValidators.removeErrors(['required'], toCheck);
                return null;
            }
            CustomValidators.setErrors({ required: true }, required);
            CustomValidators.setErrors({ required: true }, toCheck);
            const errorValue = `${requiredControlName}_Required_Either_${controlToCheckName}`;
            return { [errorValue]: true };
        };
    }

    static validateLargerEndDate(beginningDateFieldName, finalDateFieldName): ValidatorFn {
        return (control: AbstractControl): ValidationErrors | null => {
            const beginField = control.get(beginningDateFieldName);
            const endField = control.get(finalDateFieldName);

            if (beginField.value && endField.value) {
                const beginDate = beginField.value;
                const endDate = endField.value;
                if (beginDate > endDate) {
                    CustomValidators.setErrors({ invalidEndDate: true }, endField);
                    return { [beginField.value]: true };
                }
            }
            CustomValidators.removeErrors(['invalidEndDate'], endField);
            return null;
        };
    }

    static checkLimit(min: number, max: number): ValidatorFn {
      return (c: AbstractControl): { [key: string]: boolean } | null => {
          if (c.value && (isNaN(c.value) || c.value < min || c.value > max)) {
              return { 'range': true };
          }
          return null;
      };
    }

    static validateFieldNamesLargerThanOne(fieldName: string, c: AbstractControl) {
        var field = c.get(fieldName);
        var counter = field.value ? field.value.trimLeft(',').trimRight(',').split(',').length : 0;
        if (counter < 1) {
            field.setErrors({ lessThanOne: true });
        }
    }

    static passwordMatch(c: AbstractControl) {
        if (c.get('password').value !== c.get('passwordConfirmation').value) {
            c.get('passwordConfirmation').setErrors({ passwordMismatch: true });
        }
    }

    static setErrors(error: { [key: string]: any }, control: AbstractControl) {
        control.setErrors({ ...control.errors, ...error });
    }

    static removeErrors(keys: string[], control: AbstractControl) {
        const remainingErrors = keys.reduce((errors, key) => {
            delete errors[key];
            return errors;
        }, { ...control.errors });
        control.setErrors(Object.keys(remainingErrors).length > 0 ? remainingErrors : null);
    }

}
