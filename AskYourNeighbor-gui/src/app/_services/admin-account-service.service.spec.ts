import { TestBed } from '@angular/core/testing';

import { AdminAccountServiceService } from './admin-account-service.service';

describe('AdminAccountServiceService', () => {
  let service: AdminAccountServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AdminAccountServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
