# HR-APP
### Prerequisites
   Docker Engine (e.g. Docker Desktop)
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
   Users can view their own and other profiles
   Managers can view salaries and edit profiles
   Co-workers can add feedback (ordered by date descending)