# Endpoints y cuerpos de prueba (Postman / curl)

Base URL: `http://localhost:8080`  
Añade `Authorization: Bearer <token>` tras hacer login.

## Autenticación / Roles
- `POST /api/auth/login`  
  Body: `{"username":"admin","password":"Admin123*"}`
- `POST /api/auth/users`  
  Body: `{"username":"user1","password":"Clave123*","roles":["COORDINATOR"]}`
- `POST /api/auth/recover`  
  Body: `{"email":"correo@dominio.com"}`
- `POST /api/auth/logout` (sin body)
- `POST /api/roles`  
  Body: `{"name":"SUPERVISOR","description":"Rol supervisor","permissions":["READ"],"isDefault":false}`
- `PUT /api/roles/{id}`  
  Body: `{"id":"{id}","name":"SUPERVISOR","description":"Desc","permissions":["READ","WRITE"],"isDefault":false}`
- `POST /api/roles/{name}/assign/{username}` (sin body)  
  `POST /api/roles/{name}/revoke/{username}` (sin body)

## Voluntarios / Perfil
- `POST /api/volunteers`  
  Body: `{"fullName":"Ana Perez","email":"ana@test.com","phone":"3001234567","birthDate":"1990-01-01","address":"Calle 1","documentId":"CC123","roleType":"VOLUNTEER","active":true}`
- `PUT /api/volunteers/{id}`  
  Body igual al alta (sin cambiar `documentId`).
- `GET /api/volunteers/{id}` — sin body  
  `GET /api/volunteers/{id}/calendar` — sin body

## Actividades / Asignaciones / Asistencia
- `POST /api/activities`  
  Body: `{"title":"Jornada","description":"Desc","type":"Social","location":"Bogotá","startDate":"2025-12-01T10:00:00","endDate":"2025-12-01T12:00:00","status":"PENDING","requiredVolunteers":5,"coordinatorId":"{UUID}","active":true}`
- `PUT /api/activities/{id}` — mismo body.
- `GET /api/activities/upcoming?from=2025-12-01T00:00:00&to=2025-12-07T23:59:00` — sin body.
- `POST /api/assignments`  
  Body: `{"volunteerId":"{VOL_UUID}","activityId":"{ACT_UUID}","status":"PENDING","startDate":"2025-12-01T10:00:00","endDate":"2025-12-01T12:00:00"}`
- `POST /api/assignments/{id}/cancel`  
  Body (si <24h): `"No puedo asistir"`
- `POST /api/assignments/{id}/attendance`  
  Body: `{"date":"2025-12-01","present":true,"arrivalTime":"10:00:00","departureTime":"12:00:00","performanceLevel":"BUENO","observations":"Sin novedades"}`
- `POST /api/assignments/{id}/attendance/confirm`  
  Body: `{"code":"123456"}`

## Notificaciones / Mensajes
- `POST /api/notifications`  
  Body: `{"channel":"EMAIL","type":"ASSIGNMENT","subject":"Nueva actividad","content":"Hola, tienes una actividad","scheduledAt":null,"maxRetries":3,"personId":"{VOL_ID}","activityId":"{ACT_ID}"}`
- `GET /api/notifications/volunteers/{id}` — sin body.
- `POST /api/messages`  
  Body: `{"activityId":"{ACT_ID}","senderId":"{USER_ID}","receiverId":null,"messageType":"GROUP","content":"Mensaje al grupo"}`
- `GET /api/messages/activity/{activityId}` — sin body.

## Evidencias / Documentos
- `POST /api/documents`  
  Body: `{"documentType":"EVIDENCE","activityId":"{ACT_ID}","personId":"{VOL_ID}","title":"Foto","storagePath":"/uploads/foto.jpg","sizeBytes":204800,"verified":false}`
- `GET /api/documents/activity/{activityId}` — sin body.

## Reportes / Dashboard
- `GET /api/reports/dashboard?from=2025-12-01&to=2025-12-07` — sin body.
- `POST /api/reports/export`  
  Body: `{"reportType":"ACTIVITY","format":"PDF","periodStart":"2025-12-01","periodEnd":"2025-12-07","filters":{"estado":"COMPLETED"}}`
- `POST /api/reports/export/pdf` — mismo body (devuelve bytes).  
  `POST /api/reports/export/excel` — mismo body (devuelve bytes).

## Backups
- `POST /api/backups/run`  
  Body: `{"frequency":"DAILY","encrypted":true}`
- `GET /api/backups` — sin body.
- `POST /api/backups/{id}/restore` — sin body.  
- `GET /api/backups/{id}/verify` — sin body.

## Salud / Docs
- `GET /actuator/health` — sin body.
- `GET /swagger-ui/index.html` — UI interactiva.
