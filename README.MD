# HR-APP
### Prerequisites
   * Docker Engine (e.g. Docker Desktop)
   * Generate .p12 keystore file for keycloack and place it in infra/certs folder
#### Example command to generate .p12 file:
   keytool -genkeypair -alias keycloak -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore keycloak.p12 -validity 3650
   * Use the following values when prompted:
     * First and Last Name: localhost
     * Organizational Unit: HR-APP
     * Organization: My Company
     * City or Locality: My City
     * State or Province: My State
     * Country Code: US
     * Key password: changeit
     * Store password: changeit

### Running the Application
#### Start infrastructure:
   Navigate to the infra folder
   Run:
   docker compose up -d
#### Keycloak setup:
   Access https://localhost:8443 (Keycloak)
   Login: admin / admin
   Import realm using keycloack.json(infra folder)
#### Access the app:
   Go to http://localhost:4200
   Login with valid Keycloak credentials (from imported realm)
   * Note: username from Keycloak must match the username column in the HR-APP database, user table
   * valid users: manager, employee
#### Architecture Notes
   Backend: Hexagonal architecture (ports/adapters) for testability and domain-driven design
#### Improvements
   * Add error interceptor in Angular app
   * Add pagination for feedbacks
   * Improve packaging on Angular app
   * Add logo to the profile
   * Add polish to feedback using AI tools
   * Enhance backend security (e.g. role-based authorization)
   * Add tests in Angular app
   * Improve UI/UX
#### Features Implemented
   * Users can view their own and other profiles
   * Managers can view salaries and edit profiles
   * Co-workers can add feedback (ordered by date descending)