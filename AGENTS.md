# Repository Guidelines

## Project Structure & Module Organization
- `lib/dlv_koroad.jar`: Core Koroad encryption/verification library used by the Lambda.
- `src/main/java/com/koroad/crypt`: Java sources for the AWS Lambda handler and crypto service.
- `sample/encryptLibTest1.jsp`: Legacy JSP example showing how to encrypt/decrypt payloads with the library.
- `DriverLicenseInformation–VerificationSystemHTTPIntegrationGuide_V1.6.pdf`: Official integration guide; treat as the canonical reference.
- `test_path/*.json`: Sample Lambda event payloads for manual encrypt/decrypt testing.
- New Java or JSP code should live in conventional folders (for example, `src/main/java`, `src/main/webapp`, `src/test/java`) rather than modifying the JAR.

## Build, Test, and Development Commands
- This repository uses Gradle and Serverless to package and deploy a Java11 Lambda.
- `gradle buildZip` – compile sources and build `build/distributions/koroad-crypt-service.zip` used by Serverless.
- `serverless deploy --stage dev` – deploy the current zip as a Lambda function for the `dev` stage.
- To manually exercise the legacy JSP sample, deploy it in a servlet container (e.g., Tomcat) and access `sample/encryptLibTest1.jsp`.

## Coding Style & Naming Conventions
- Use Java 8+ style, 4-space indentation, and UTF‑8 encoding across Java and JSP files.
- Class names: `PascalCase`; methods and variables: `camelCase`; constants: `UPPER_SNAKE_CASE`.
- Name packages under your own root (e.g., `com.example.koroad`) and do not change existing `kr.or.koroad.*` packages.

## Testing Guidelines
- When adding code, create unit tests (e.g., JUnit/Jupiter) under `src/test/java` mirroring the main package structure.
- Name test classes as `<ClassName>Test` and keep tests deterministic and self-contained.
- Ensure encryption/decryption tests never embed real client secrets or production license data.

## Commit & Pull Request Guidelines
- Use clear, imperative commit messages (e.g., `Add ARIA encryption helper`, `Refactor JSP sample`).
- Each pull request should include: purpose summary, key changes, testing performed (commands and results), and any integration notes.
- Reference related tickets or documentation where applicable and attach screenshots or logs if they help reviewers.

## Security & Configuration Tips
- Never commit real `client_secret` values or production identifiers; use obvious placeholders in examples.
- Keep the shipped JAR file unmodified; upgrade it by replacing the file with an official version and documenting the change.
- Before sharing code externally, remove or anonymize any environment-specific configuration from samples.
