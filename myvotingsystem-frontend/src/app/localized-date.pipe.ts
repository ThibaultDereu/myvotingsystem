import { Pipe, PipeTransform } from '@angular/core';
import { formatDate } from '@angular/common';
import { TranslateService } from '@ngx-translate/core';

@Pipe({
  name: 'localizedDate',
})
export class LocalizedDatePipe implements PipeTransform {
  constructor(private translateService: TranslateService) {}

  transform(value: Date, format: string): unknown {
    if (!value) {
      return '';
    }
    if (!format) {
      format = 'shortDate';
    }

    return formatDate(value, format, this.translateService.currentLang);
  }
}
