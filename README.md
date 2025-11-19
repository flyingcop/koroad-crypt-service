# koroad-crypt-service

AWS Lambda utility for encrypting and decrypting Koroad driver-license payloads using the official `dlv_koroad.jar` ARIA library.  
This function is designed to be invoked **directly by other backend services or Lambdas**, not via API Gateway.

## Project Structure
- `lib/dlv_koroad.jar` – Koroad-provided encryption/verification library (do not modify).
- `src/main/java/com/koroad/crypt` – Lambda handler, crypto service, and DTOs.
- `sample/encryptLibTest1.jsp` – Legacy JSP sample showing low-level library usage.
- `DriverLicenseInformation–VerificationSystemHTTPIntegrationGuide_V1.6.pdf` – Official protocol guide.

## Build & Deploy
Prerequisites: Java 11, Gradle, Node.js 18+, `serverless` v3, and AWS credentials.

```bash
# Build Lambda artifact
gradle buildZip

# Deploy (KOROAD_CLIENT_SECRET must be set; .env is typically used locally)
export KOROAD_CLIENT_SECRET=your_client_secret_here
serverless deploy --stage dev
```

The Serverless config (`serverless.yml`) packages `build/distributions/koroad-crypt-service.zip` and creates a single Lambda function:
- Logical name: `crypt`
- Handler: `com.koroad.crypt.handler.Handler`

## Runtime Configuration
- `KOROAD_CLIENT_SECRET` (env var) – Koroad client secret string, usually defined in `.env`.  
  The handler derives the ARIA key as `Base64.encode(KOROAD_CLIENT_SECRET.getBytes("UTF-8"))` and passes it to `ARIACipher256`.

## Lambda Request/Response Contract
The same function supports both encrypt and decrypt modes via the `mode` field.

Encrypt request:
```json
{
  "mode": "ENCRYPT",
  "requestId": "req-1234",
  "clientId": "nice-pos-service",
  "payload": { "licenses": [ { "f_license_no": "...", "f_resident_name": "...", "f_resident_date": "...", "f_seq_no": "...", "f_licn_con_code": "...", "f_from_date": "...", "f_to_date": "..." } ] }
}
```

Encrypt response:
```json
{ "mode": "ENCRYPT", "requestId": "req-1234", "status": "SUCCESS", "encryptedBody": "Base64-Encoded-String" }
```

Decrypt request:
```json
{ "mode": "DECRYPT", "requestId": "req-1234", "clientId": "nice-pos-service", "encryptedBody": "Base64-Encoded-String" }
```

Decrypt response:
```json
{ "mode": "DECRYPT", "requestId": "req-1234", "status": "SUCCESS", "payload": { "licenses": [ { "...": "..." } ] } }
```

On errors, `status` is `"ERROR"` and `errorCode`/`errorMessage` are populated. Callers should treat this Lambda as a pure crypto utility and perform Koroad HTTP calls in their own context.
