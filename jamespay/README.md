# JamesPay

Enterprise payment gateway front end built with Angular 20 standalone components.

## Stack

- Angular 20, standalone components only (no NgModules)
- Reactive Forms
- Standalone Angular Router with lazy-loaded pages
- Functional route guard (`authGuard`) and functional HTTP interceptor (`authInterceptor`)
- Strict TypeScript (`strict: true`, no `any`)

## Getting started

```bash
npm install
npm start
```

The app expects a Spring Boot backend at `http://localhost:8080/api/auth` exposing:

- `POST /login` — `{ email, password }` → `{ token, userId, name, email, role }`
- `POST /register` — `{ name, email, phone, password }` → `{ token, userId, name, email, role }`

Update `src/environments/environment.ts` / `environment.prod.ts` to point at a different backend.

## Project structure

```
src/app/
  core/
    services/auth.service.ts      Session management, login/register/logout
    guards/auth.guard.ts          Protects /dashboard
    interceptors/auth.interceptor.ts  Attaches JWT, handles 401
    models/                       LoginRequest, RegisterRequest, AuthResponse
  pages/
    home/                         Public marketing landing page
    login/                        Sign-in form
    register/                     Sign-up form with password strength meter
    dashboard/                    Authenticated overview (guarded)
  shared/
    navbar/                       Marketing site navbar
    footer/                       Marketing site footer
  app.routes.ts
  app.config.ts
```

## Notes on the register form

The register page collects a "Company name" field for onboarding context, but it
is intentionally **not** sent to the backend: the provided API contract for
`POST /register` only accepts `name`, `email`, `phone`, and `password`. If the
backend adds support for it later, extend `RegisterRequest` and the submit
handler in `register.component.ts` together.
