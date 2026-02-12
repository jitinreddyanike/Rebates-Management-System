# Rebates Management API (Spring Boot)

This project demonstrates a professional layered Spring Boot application for rebates management.

## Business Flow
1. External vendor sends a POST request.
2. Controller validates request and passes to service.
3. Service applies business rules and calls DAO.
4. DAO executes a stored-procedure-style DB function.
5. API returns transaction details and rebate output.

## Tech Stack
- Java 17
- Spring Boot 3
- Spring Web, Validation, JDBC
- H2 (in-memory) for local demo
- PostgreSQL procedure script included for production reference

## API
- Method: `POST`
- Path: `/api/v1/rebates/applications`

### Sample Request
```json
{
  "vendorId": "VEND-1001",
  "vendorName": "Acme Supplies",
  "productCode": "PRD-REB-01",
  "quantity": 25,
  "unitPrice": 150.75,
  "rebatePercentage": 12.5,
  "remarks": "Quarterly rebate request"
}
```

### Sample Success Response
```json
{
  "transactionId": "aef2f4b5-fdf1-4607-aed4-006fb56a7b5d",
  "applicationId": 1001,
  "rebateAmount": 470.00,
  "status": "SUCCESS",
  "message": "Rebate application submitted successfully",
  "processedAt": "2026-02-12T16:00:00Z"
}
```

## Run Locally
```bash
mvn spring-boot:run
```

## Package Structure
- `controller` - API endpoints
- `service` / `service.impl` - business logic
- `dao` / `dao.impl` - database interaction
- `dto` - request/DB transport objects
- `vo` - response view object
- `mapper` - DTO/VO mapping
- `exception` - centralized error handling
- `config` - DB function bindings for local environment

## Notes
- Local demo uses H2 alias to simulate a stored procedure.
- Production DB script is at `src/main/resources/db/postgres_stored_procedure.sql`.

## Verify H2 Artifacts From Postman
Use this endpoint to confirm H2 table + stored-procedure alias creation:

- Method: `GET`
- URL: `http://localhost:8080/api/v1/rebates/db/artifacts`

Sample response:
```json
{
  "auditTableExists": true,
  "rebateProcedureExists": true,
  "databaseProduct": "H2",
  "message": "H2 artifacts are available"
}
```

After that, test transaction flow with:
- Method: `POST`
- URL: `http://localhost:8080/api/v1/rebates/applications`

Then verify DB writes using H2 console query:
```sql
SELECT * FROM rebate_application_audit ORDER BY created_at DESC;
```
